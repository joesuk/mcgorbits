"""Basepoint analysis on the punctured torus (port of the Java pipeline).

A curve with k self-intersections is cut into k+1 simple loops through one
basepoint. The input is

  * simple loop arrangements: k+1 loop words and the cyclic order in which
    their 2(k+1) ends meet a small circle round the basepoint (i is the end
    where loop i leaves, -i the end where it returns),
  * basepoint diagrams: a pairing of the 2(k+1) positions on that circle,
    saying how the ends are joined inside it.

Every arrangement, diagram and rotation of the ends gives a closed walk, hence
a word; the words with self-intersection number exactly k are kept and sorted
into orbits.

Usage: python3 basepoint.py K [--loops FILE] [--diagrams FILE] [--java-compat]
"""

import argparse
import os
from collections import Counter

from freegroup import (canonical, cyclic_reduce, inverse, orbit_key,
                       primitive_root, self_intersection)

SIMPLE_LOOPS = {"abAB", "a", "b", "ab", "abA", "baB"}


def _lines(path):
    with open(path) as f:
        return ["".join(line.split()) for line in f]


def read_diagrams(path, k):
    """Each diagram is 2(k+1) lines: line i holds the position joined to i."""
    size = 2 * (k + 1)
    nums = [int(s) for s in _lines(path) if s]
    if len(nums) % size:
        raise ValueError(f"{path}: {len(nums)} entries, not a multiple of {size}")
    diagrams = [nums[i:i + size] for i in range(0, len(nums), size)]
    for d in diagrams:
        if sorted(d) != list(range(size)) or any(d[d[i]] != i or d[i] == i for i in range(size)):
            raise ValueError(f"{path}: {d} is not a pairing of 0..{size - 1}")
    return diagrams


def read_arrangements(path, k, java_compat=False):
    """Return (arrangements, problems).

    An arrangement is k+1 lines of loop words followed by one line with the
    order of the ends. Malformed blocks are reported and skipped. The Java
    reader instead stops at the first block it cannot handle and silently
    drops the rest of the file; java_compat reproduces that.
    """
    lines = [s for s in _lines(path) if s]
    n = k + 1
    ends = sorted(list(range(1, n + 1)) + [-i for i in range(1, n + 1)])
    arrangements, problems = [], []
    for start in range(0, len(lines), n + 1):
        block = lines[start:start + n + 1]
        number = start // (n + 1) + 1
        loops = block[:n]
        try:
            ordering = [int(s) for s in block[n].split(",")]
        except (IndexError, ValueError):
            ordering = None
        if ordering is None or sorted(ordering) != ends:
            problems.append(f"arrangement {number}: bad ordering {block[n:]}")
        elif not set(loops) <= SIMPLE_LOOPS:
            problems.append(f"arrangement {number}: unknown loop in {loops}")
        else:
            arrangements.append((loops, ordering))
            continue
        if java_compat:
            problems.append(f"java-compat: stopped reading at arrangement {number}")
            break
    return arrangements, problems


def traverse(diagram, ordering, k):
    """Loops met, in order and with sign, starting along loop 1."""
    path = [1]
    current = 1
    for _ in range(k):
        current = ordering[diagram[ordering.index(-current)]]
        path.append(current)
    return path


def words_from(diagram, loops, ordering, k):
    """One word per rotation of the ends that gives a single closed curve."""
    size = len(ordering)
    for r in range(size):
        rotated = [ordering[(i - r) % size] for i in range(size)]
        path = traverse(diagram, rotated, k)
        if len(set(path)) < len(path):
            continue  # more than one component
        yield cyclic_reduce("".join(
            loops[i - 1] if i > 0 else inverse(loops[-i - 1]) for i in path))


def analyze(diagrams, arrangements, k):
    """All cyclic words with self-intersection k, and a tally of every SI seen."""
    found = set()
    tally = Counter()
    for diagram in diagrams:
        for loops, ordering in arrangements:
            for w in words_from(diagram, loops, ordering, k):
                si = self_intersection(w)
                tally[si] += 1
                if si == k:
                    found.add(canonical(w))
                    found.add(canonical(inverse(w)))
    return found, tally


def orbits(words, **kw):
    """Map orbit representative -> the given words in that orbit."""
    out = {}
    for w in sorted(words, key=lambda w: (len(w), w)):
        out.setdefault(orbit_key(w, **kw), []).append(w)
    return out


def main():
    here = os.path.dirname(os.path.abspath(__file__))
    data = os.path.join(here, "..", "output")
    p = argparse.ArgumentParser(description=__doc__.split("\n")[0])
    p.add_argument("k", type=int)
    p.add_argument("--loops")
    p.add_argument("--diagrams")
    p.add_argument("--java-compat", action="store_true",
                   help="stop reading arrangements at the first malformed one")
    args = p.parse_args()
    k = args.k
    loops = args.loops or os.path.join(data, f"si{k}.txt")
    diagrams = read_diagrams(args.diagrams or os.path.join(data, f"bp{k}.txt"), k)
    arrangements, problems = read_arrangements(loops, k, args.java_compat)
    for problem in problems:
        print("WARNING:", problem)

    words, tally = analyze(diagrams, arrangements, k)
    print(f"{len(diagrams)} diagram(s), {len(arrangements)} arrangement(s)")
    print("self-intersection numbers of the curves produced:", dict(sorted(tally.items())))
    print(f"{len(words)} cyclic words with self-intersection {k} (both orientations)")
    reps = orbits(words)
    primitive = [r for r in reps if primitive_root(r)[1] == 1]
    print(f"{len(reps)} orbits, {len(primitive)} primitive:")
    for r in sorted(reps, key=lambda w: (len(w), w)):
        print(" ", r)


if __name__ == "__main__":
    main()
