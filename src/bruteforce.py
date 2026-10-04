"""Exhaustive count of mapping class group orbits by self-intersection number.

No basepoint diagrams or loop arrangements are needed: every cyclically
reduced word of length <= LMAX is examined, the Whitehead-minimal ones with
self-intersection number K are kept, and they are sorted into orbits.

Every orbit contains a Whitehead-minimal word, so the search finds exactly the
orbits whose shortest words have length <= LMAX. By the basepoint lemma of
Crisp et al., a curve with k self-intersections is a product of k+1 simple
loops through one point, and in a suitable basis each of those is one of
a, b, ab, abA, baB, abAB; so every orbit has a word of length <= 4(k+1), and
LMAX = 4(K+1) is exhaustive. That is the default.

The search is a depth-first walk over prefixes. A crossing of two lifts is
decided by the letters along their common segment and one letter either side,
so once those letters lie inside a prefix the crossing is present in every
word extending it. A prefix that already forces more than K crossings is
abandoned, which keeps the tree small.

Usage: python3 bruteforce.py K [LMAX] [PROCESSES]

All CPU cores are used unless PROCESSES is given.
"""

import multiprocessing
import os
import sys
from collections import defaultdict

from freegroup import CODE, is_minimal, minimal_orbit, primitive_root, self_intersection

ALPHABET = "ABab"  # ASCII order, so that prenecklaces match freegroup.canonical
SPLIT_DEPTH = 10


def new_crossings(x, t):
    """Crossings of lifts of the linear word x[0..t] first forced by x[t].

    x is a list of letter codes. Each unordered pair of lifts is reported
    once, at the moment the last letter deciding it is appended.
    """
    count = 0
    xt = x[t]
    # Both lifts read x forwards; the later one leaves the common segment at t,
    # the earlier one at e.
    for e in range(1, t):
        if x[e] == xt:
            continue
        m = 0
        while m < e and x[e - 1 - m] == x[t - 1 - m]:
            m += 1
        if m == e:
            continue  # the common segment runs off the front of the prefix
        if m == 0:
            p1 = (x[e - 1] + 2) % 4
            p2 = (x[t - 1] + 2) % 4
            if p1 != xt and p2 != x[e] and (x[e] - p1) % 4 == 2:
                count += 1
            continue
        i, j = e - m, t - m
        f = x[i]
        back = (x[e - 1] + 2) % 4
        tail = ((x[i - 1] + 2) - f) % 4 - ((x[j - 1] + 2) - f) % 4
        head = (x[e] - back) % 4 - (xt - back) % 4
        if (tail > 0) == (head > 0):
            count += 1
    # The lifts run in opposite directions along the common segment: the block
    # starting at i is the inverse of the block ending at t-1.
    for i in range(1, t):
        if (x[i - 1] + 2) % 4 == xt:
            continue
        m = 0
        while x[i + m] == (x[t - 1 - m] + 2) % 4:
            m += 1
        if m == 0:
            continue
        f = x[i]
        back = (x[i + m - 1] + 2) % 4
        tail = ((x[i - 1] + 2) - f) % 4 - (xt - f) % 4
        head = (x[i + m] - back) % 4 - ((x[t - m - 1] + 2) - back) % 4
        if (tail > 0) == (head > 0):
            count += 1
    return count


def _search(k, lmax, prefix, p, forced, frontier_depth=None):
    """Search below one prefix.

    prefix is a prenecklace whose longest Lyndon prefix has length p and which
    forces the given number of crossings. Returns the minimal words found, the
    number of prefixes visited, and, if frontier_depth is set, the unexplored
    (prefix, p, forced) states at that depth instead of searching below them.
    """
    found = []
    frontier = []
    letters = list(prefix) + [""] * (lmax - len(prefix))
    codes = [CODE[c] for c in prefix] + [0] * (lmax - len(prefix))
    visited = 0

    def extend(t, p, forced):
        nonlocal visited
        if t == frontier_depth and t < lmax:
            frontier.append(("".join(letters[:t]), p, forced))
            return
        visited += 1
        if t % p == 0 and letters[t - 1] != letters[0].swapcase():
            w = "".join(letters[:t])
            # Few words of small self-intersection are minimal, so test that first.
            if is_minimal(w) and self_intersection(w, cap=k) == k:
                found.append(w)
        if t == lmax:
            return
        prev = letters[t - 1].swapcase()
        for c in ALPHABET:
            if c < letters[t - p] or c == prev:
                continue
            letters[t] = c
            codes[t] = CODE[c]
            f = forced + new_crossings(codes, t)
            if f <= k:
                extend(t + 1, p if c == letters[t - p] else t + 1, f)

    extend(len(prefix), p, forced)
    return found, visited, frontier


def _search_task(args):
    found, visited, _ = _search(*args)
    return found, visited


def minimal_words(k, lmax, processes=1):
    """Whitehead-minimal cyclic words of length <= lmax with SI exactly k.

    Words are returned in canonical rotation, sorted. Also returns the number
    of prefixes visited. With processes > 1 the tree is cut at depth
    SPLIT_DEPTH and the subtrees are shared out among that many workers.
    """
    found = []
    visited = 0
    tasks = []
    for c in ALPHABET:
        words, n, frontier = _search(k, lmax, c, 1, 0, SPLIT_DEPTH if processes > 1 else None)
        found += words
        visited += n
        tasks += [(k, lmax) + state for state in frontier]
    if tasks:
        with multiprocessing.Pool(processes) as pool:
            for words, n in pool.imap_unordered(_search_task, tasks):
                found += words
                visited += n
    return sorted(found), visited


CONVENTIONS = [
    ("all homeomorphisms, unoriented curves", dict(unoriented=True, orientation_preserving=False)),
    ("all homeomorphisms, oriented curves", dict(unoriented=False, orientation_preserving=False)),
    ("orientation-preserving, unoriented curves", dict(unoriented=True, orientation_preserving=True)),
    ("orientation-preserving, oriented curves", dict(unoriented=False, orientation_preserving=True)),
]


def count_orbits(words, **kw):
    """Map orbit representative -> length of its minimal words."""
    reps = {}
    seen = set()
    for w in words:
        if w not in seen:
            orbit = minimal_orbit(w, **kw)
            seen |= orbit
            reps[min(orbit)] = len(w)
    return reps


def main():
    k = int(sys.argv[1])
    lmax = int(sys.argv[2]) if len(sys.argv) > 2 else 4 * (k + 1)
    processes = int(sys.argv[3]) if len(sys.argv) > 3 else os.cpu_count()
    words, visited = minimal_words(k, lmax, processes)
    print(f"k = {k}, all words up to length {lmax}: {len(words)} minimal classes "
          f"({visited} prefixes visited)")
    for name, kw in CONVENTIONS:
        reps = count_orbits(words, **kw)
        primitive = [r for r in reps if primitive_root(r)[1] == 1]
        by_length = defaultdict(int)
        for r, n in reps.items():
            by_length[n] += 1
        print(f"\n{name}: {len(reps)} orbits, {len(primitive)} primitive")
        print("  orbits by length of shortest word:", dict(sorted(by_length.items())))
        if kw == CONVENTIONS[0][1]:
            for r in sorted(reps, key=lambda w: (len(w), w)):
                print("   ", r)


if __name__ == "__main__":
    main()
