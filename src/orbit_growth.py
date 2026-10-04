"""How many curves of each word length lie in one orbit (Chas, arXiv:1608.02846).

For an orbit of unoriented curves let c(l) be the number of its classes of
word length l. Chas conjectures that for every orbit

    c(l) = 2 [ phi((l + j_1)/k_1) + ... + phi((l + j_n)/k_n) ]      (Conjecture 4)

for large l, with phi Euler's function taken as 0 off the positive integers.
The orbit then has growth coefficient p = 1/k_1^2 + ... + 1/k_n^2, and

    the sum of p over the primitive orbits with K self-intersections is
    within 1 of the number of those orbits.                          (Conjecture 5)

This script counts c(l) directly, searches for a formula of that shape for
each orbit, and compares the sum of the p with the number of orbits.

The count is a search outward from the minimal words of the orbit: by
Whitehead's peak reduction every word of the orbit is reached from a minimal
one through words that never get shorter, so nothing of length <= CAP is
missed by discarding longer words.

Usage: python3 orbit_growth.py K [CAP]
"""

import multiprocessing
import os
import sys
from collections import deque
from fractions import Fraction
from math import gcd, pi

from filling import representatives
from freegroup import (WHITEHEAD, apply, canonical, inverse, minimal_orbit,
                       primitive_root, self_intersection)

MAX_K = 12       # largest denominator k_i tried
MIN_SHIFT = -60  # smallest shift j_i tried
TAIL = 30        # a formula must hold on at least this many lengths below CAP


def phi(n):
    return sum(gcd(i, n) == 1 for i in range(1, n + 1))


def unoriented(w):
    return min(canonical(w), canonical(inverse(w)))


def counts_by_length(w, cap):
    """c[l] for l <= cap, for the orbit of the unoriented curve w."""
    seen = {unoriented(u) for u in minimal_orbit(w)}
    queue = deque(seen)
    while queue:
        u = queue.popleft()
        for auto, _ in WHITEHEAD:
            v = apply(auto, u)
            if len(v) <= cap:
                v = unoriented(v)
                if v not in seen:
                    seen.add(v)
                    queue.append(v)
    c = [0] * (cap + 1)
    for u in seen:
        c[len(u)] += 1
    return c


class _TooHard(Exception):
    pass


def _search(residual, candidates, max_terms, budget):
    """Choose at most max_terms candidates, with repetition, summing to residual.

    residual maps l to a value; each candidate is ((k, j), {l: value}) with
    all values positive. Some chosen term must account for the smallest l
    still unmatched, so only candidates that are non-zero there are tried.
    Returns None if there is no such choice or the budget runs out.
    """
    nodes = 0

    def search(terms, last_first, last_index):
        nonlocal nodes
        nodes += 1
        if nodes > budget:
            raise _TooHard
        first = next((l for l in residual if residual[l]), None)
        if first is None:
            return terms
        if len(terms) == max_terms:
            return None
        for i, (name, t) in enumerate(candidates):
            if first not in t or (first == last_first and i < last_index):
                continue
            if all(residual[l] >= v for l, v in t.items()):
                for l, v in t.items():
                    residual[l] -= v
                found = search(terms + [name], first, i)
                for l, v in t.items():
                    residual[l] += v
                if found is not None:
                    return found
        return None

    try:
        return search([], None, 0)
    except _TooHard:
        return None


# Shifts tried, the most terms allowed, and the search budget. Multiples of 4
# come first: every formula in Chas's tables has that shape, a loop round the
# puncture costing four letters.
FAMILIES = (
    (lambda k: range(0, MIN_SHIFT - 1, -4), 12, 300000),
    (lambda k: range(k, MIN_SHIFT - 1, -1), 6, 100000),
)


def fit(c, start, family):
    """Fewest terms (k, j) with c[l] = 2 sum phi((l + j)/k) for start <= l < len(c)."""
    shifts, max_terms, budget = family
    cap = len(c) - 1
    if any(c[l] % 2 for l in range(start, cap + 1)):
        return None
    residual = {l: c[l] // 2 for l in range(start, cap + 1)}
    candidates = []
    for k in range(1, MAX_K + 1):
        for j in shifts(k):
            t = {l: phi((l + j) // k) for l in residual if (l + j) % k == 0 and l + j >= k}
            if t:
                candidates.append(((k, j), t))
    for limit in range(1, max_terms + 1):
        terms = _search(dict(residual), candidates, limit, budget)
        if terms is not None:
            return terms
    return None


def analyse(args):
    w, cap = args
    c = counts_by_length(w, cap)
    for family in FAMILIES:
        for start in range(len(w), cap - TAIL + 1):
            terms = fit(c, start, family)
            if terms is not None:
                return w, c, start, terms
    return w, c, None, None


def show(terms):
    def one(k, j, n):
        inside = "l" + (f"{j:+d}" if j else "")
        inside = inside if k == 1 else f"({inside})/{k}"
        return f"{2 * n}phi({inside})"
    grouped = {}
    for t in terms:
        grouped[t] = grouped.get(t, 0) + 1
    return " + ".join(one(k, j, n) for (k, j), n in sorted(grouped.items()))


def main():
    K = int(sys.argv[1])
    cap = int(sys.argv[2]) if len(sys.argv) > 2 else 60
    boundary = unoriented("abAB")
    reps = [w for w in representatives(K)
            if primitive_root(w)[1] == 1 and unoriented(w) != boundary]
    with multiprocessing.Pool(os.cpu_count()) as pool:
        rows = pool.map(analyse, [(w, cap) for w in reps])
    total = Fraction(0)
    unfitted = 0
    print(f"{K} self-intersections, {len(reps)} primitive orbits, lengths up to {cap}")
    for w, c, start, terms in sorted(rows, key=lambda r: (len(r[0]), r[0])):
        estimate = sum(c) * pi ** 2 / (6 * cap ** 2)
        if terms is None:
            unfitted += 1
            print(f"  {w:22s} NO FORMULA FOUND   classes {sum(c)}, crude p ~ {estimate:.2f}")
            continue
        p = sum(Fraction(1, k * k) for k, _ in terms)
        total += p
        print(f"  {w:22s} p = {str(p):6s} c(l) = {show(terms)}   for l >= {start}")
    print(f"sum of p = {total} = {float(total):.4f}; orbits = {len(reps)}; "
          f"difference = {float(total) - len(reps):+.4f}"
          + (f"; {unfitted} orbit(s) without a formula" if unfitted else ""))


if __name__ == "__main__":
    main()
