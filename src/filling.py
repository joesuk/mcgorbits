"""Split the orbits with K self-intersections by the subsurface they fill.

A curve on the punctured torus is one of

  annular   a power of a simple curve or of the curve round the puncture,
  pants     disjoint from exactly one simple closed curve, so it lives in the
            pair of pants left when the torus is cut along that curve,
  filling   crossed by every simple closed curve.

If a curve misses the simple curve a, its word lies in the subgroup generated
by a and bAB, which shows as the letters b and B alternating round the word.
Such words are already Whitehead-minimal, so an orbit is non-filling exactly
when one of its minimal words has that shape.

Reads the orbit representatives from ../results/kK.txt if bruteforce.py has
been run with its output saved there, and runs the search otherwise.

Usage: python3 filling.py K [K ...]
"""

import os
import sys

from bruteforce import count_orbits, minimal_words
from freegroup import minimal_orbit, primitive_root, self_intersection

KINDS = ("annular", "pants", "filling")


def misses_a(w):
    """Whether the word lies in <a, bAB> up to conjugacy."""
    bs = [c for c in w if c in "bB"]
    return all(bs[i] != bs[i - 1] for i in range(len(bs)))


def kind(w):
    root, _ = primitive_root(w)
    if self_intersection(root) == 0:
        return "annular"
    return "pants" if any(misses_a(u) for u in minimal_orbit(w)) else "filling"


def representatives(k):
    here = os.path.dirname(os.path.abspath(__file__))
    path = os.path.join(here, "..", "results", f"k{k}.txt")
    if os.path.exists(path):
        with open(path) as f:
            return [line.strip() for line in f if line.startswith("    ")]
    words, _ = minimal_words(k, 4 * (k + 1), os.cpu_count())
    return list(count_orbits(words))


def split(k):
    counts = dict.fromkeys(KINDS, 0)
    for w in representatives(k):
        counts[kind(w)] += 1
    return counts


def main():
    print(f"{'k':>3} {'annular':>8} {'pants':>8} {'filling':>8} {'total':>8}")
    for k in map(int, sys.argv[1:]):
        counts = split(k)
        print(f"{k:>3} {counts['annular']:>8} {counts['pants']:>8} {counts['filling']:>8} "
              f"{sum(counts.values()):>8}")


if __name__ == "__main__":
    main()
