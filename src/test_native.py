"""Checks that cpp/curves.cpp agrees with the Python reference code.

Run: python3 test_native.py
"""

import random
import unittest

import native
from bruteforce import count_orbits, minimal_words
from fast import loop_insertions
from freegroup import (LETTERS, canonical, cyclic_reduce, is_minimal, orbit_key,
                       self_intersection)


def random_word(rng, lo, hi):
    while True:
        w = cyclic_reduce("".join(rng.choice(LETTERS) for _ in range(rng.randint(lo, hi))))
        if w:
            return w


@unittest.skipUnless(native.available(), "no C++ compiler")
class Native(unittest.TestCase):
    def test_self_intersection(self):
        rng = random.Random(0)
        words = set()
        while len(words) < 4000:
            w = random_word(rng, 1, 30)
            words.add(w * rng.randint(2, 4) if rng.random() < 0.1 else w)
        got = native.self_intersections(sorted(words))
        for w in words:
            self.assertEqual(got[w], self_intersection(w), w)

    def test_search(self):
        for k in range(5):
            for lmax in (9, 4 * (k + 1)):
                words, visited = minimal_words(k, lmax)
                self.assertEqual(native.minimal_words(k, lmax), (words, visited), (k, lmax))

    def test_orbit_representatives(self):
        for k in range(5):
            words, _ = minimal_words(k, 4 * (k + 1))
            self.assertEqual(native.orbit_representatives(k, 4 * (k + 1)),
                             sorted(count_orbits(words)), k)

    def test_filter(self):
        rng = random.Random(1)
        words = sorted({canonical(random_word(rng, 2, 12)) for _ in range(4000)})
        for k in range(1, 6):
            expected = [w for w in words if is_minimal(w) and self_intersection(w) == k]
            self.assertEqual(native.minimal_with_si(words, k), expected, k)

    def test_orbit_keys(self):
        for k in (3, 4):
            words, _ = minimal_words(k, 4 * (k + 1))
            rng = random.Random(k)
            sample = rng.sample(words, 60)
            self.assertEqual(native.orbit_keys(sample), sorted({orbit_key(w) for w in sample}))

    def test_loop_insertions(self):
        for k in (3, 4, 5):
            words, _ = minimal_words(k - 1, 4 * k)
            parents = sorted(count_orbits(words))
            expected = set().union(*(loop_insertions((w, k)) for w in parents))
            self.assertEqual(native.loop_insertions(parents, k), sorted(expected), k)


if __name__ == "__main__":
    unittest.main()
