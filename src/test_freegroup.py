"""Checks for freegroup.py and bruteforce.py. Run: python3 test_freegroup.py"""

import itertools
import random
import unittest

from bruteforce import count_orbits, minimal_words, new_crossings
from draw import chords, crossings
from filling import kind
from freegroup import (CODE, LETTERS, WHITEHEAD, apply, canonical, cyclic_reduce,
                       intersection, inverse, is_minimal, orbit_key, primitive_root,
                       self_intersection)

# Values from Crisp et al., Butler-Carton-Kraft and Chas (arXiv:1608.02846).
KNOWN_SI = {
    "a": 0, "ab": 0, "aab": 0, "abAB": 0,
    "aabAB": 1, "abaB": 1, "aabb": 1,
    "aaabb": 2, "aabAAB": 2, "aabaB": 2, "aabABabAB": 2, "abaBabAB": 2,
    "aaabAB": 2, "aaa": 2, "abABabABabAB": 2,
    "aaaabb": 3, "aaabaB": 3, "aabaaB": 3, "aabbAB": 3, "aabABabABabAB": 3,
    "aabAbaBAb": 3, "abaBAbAB": 3, "aaabAAB": 3, "aaaabAB": 3, "aabaBAbaB": 3,
    "aabaBabAB": 3, "aabAABabAB": 3, "abaBAbaBAbaB": 3, "aaabABabAB": 3, "aaaa": 3,
    "aaaaa": 4, "aabABabABabABabAB": 4,
}

# Orbits of primitive unoriented curves under all homeomorphisms.
KNOWN_PRIMITIVE_ORBITS = {0: 2, 1: 2, 2: 6, 3: 14}


def random_word(rng, lo, hi):
    while True:
        w = cyclic_reduce("".join(rng.choice(LETTERS) for _ in range(rng.randint(lo, hi))))
        if w:
            return w


class SelfIntersection(unittest.TestCase):
    def test_known_values(self):
        for w, si in KNOWN_SI.items():
            self.assertEqual(self_intersection(w), si, w)

    def test_invariant_under_automorphisms_and_inversion(self):
        rng = random.Random(0)
        for _ in range(500):
            w = random_word(rng, 1, 14)
            u = w
            for _ in range(rng.randint(1, 5)):
                u = apply(rng.choice(WHITEHEAD)[0], u)
            if len(u) <= 60:
                self.assertEqual(self_intersection(w), self_intersection(u), (w, u))
            self.assertEqual(self_intersection(w), self_intersection(inverse(w)), w)

    def test_cap_agrees_with_exact_value(self):
        rng = random.Random(1)
        for _ in range(500):
            w = random_word(rng, 1, 14)
            si = self_intersection(w)
            for cap in range(6):
                capped = self_intersection(w, cap=cap)
                self.assertTrue(capped == si if si <= cap else capped > cap, (w, cap))


def simple_curves(max_length):
    """Every cyclic word of a simple closed curve other than the boundary."""
    found = {canonical("a")}
    queue = [canonical("a")]
    while queue:
        u = queue.pop()
        for phi, _ in WHITEHEAD:
            v = canonical(apply(phi, u))
            if len(v) <= max_length and v not in found:
                found.add(v)
                queue.append(v)
    return found


class Intersection(unittest.TestCase):
    def test_simple_curves_meet_in_the_determinant(self):
        def homology(w):
            return w.count("a") - w.count("A"), w.count("b") - w.count("B")
        curves = simple_curves(7)
        for s in curves:
            self.assertEqual(self_intersection(s), 0, s)
            self.assertEqual(intersection(s, "abAB"), 0, s)
            for t in curves:
                (p, q), (r, u) = homology(s), homology(t)
                self.assertEqual(intersection(s, t), abs(p * u - q * r), (s, t))

    def test_symmetric_and_invariant(self):
        rng = random.Random(6)
        for _ in range(500):
            w, u = random_word(rng, 1, 10), random_word(rng, 1, 10)
            phi = rng.choice(WHITEHEAD)[0]
            self.assertEqual(intersection(w, u), intersection(u, w))
            self.assertEqual(intersection(w, u), intersection(inverse(w), u))
            self.assertEqual(intersection(w, u), intersection(apply(phi, w), apply(phi, u)))

    def test_filling_curves_are_those_meeting_every_simple_curve(self):
        curves = simple_curves(9)
        for k in range(1, 5):
            words, _ = minimal_words(k, 4 * (k + 1))
            for w in count_orbits(words):
                missed = sum(intersection(w, s) == 0 for s in curves)
                # a pants curve misses one simple curve, counted with both orientations
                expected = {"filling": 0, "pants": 2}.get(kind(w))
                if expected is not None:
                    self.assertEqual(missed, expected, w)


class Orbits(unittest.TestCase):
    def test_key_is_invariant(self):
        rng = random.Random(2)
        for _ in range(300):
            w = random_word(rng, 1, 12)
            u = w
            for _ in range(rng.randint(1, 5)):
                u = apply(rng.choice(WHITEHEAD)[0], u)
            if len(u) > 60:
                continue
            self.assertEqual(orbit_key(w, unoriented=False), orbit_key(u, unoriented=False))
            self.assertEqual(orbit_key(w), orbit_key(inverse(u)))

    def test_orientation_preserving_key(self):
        rng = random.Random(3)
        positive = [phi for phi, det in WHITEHEAD if det == 1]
        for _ in range(300):
            w = random_word(rng, 1, 12)
            u = w
            for _ in range(4):
                u = apply(rng.choice(positive), u)
            if len(u) <= 60:
                self.assertEqual(orbit_key(w, orientation_preserving=True),
                                 orbit_key(u, orientation_preserving=True))

    def test_key_is_minimal_length(self):
        self.assertEqual(len(orbit_key("abaBABa")), 6)
        self.assertEqual(orbit_key("aab"), orbit_key("b"))


class Search(unittest.TestCase):
    def test_forced_crossings_bound_si_from_below(self):
        rng = random.Random(4)
        for _ in range(1000):
            w = random_word(rng, 2, 16)
            x = [CODE[c] for c in w]
            forced = sum(new_crossings(x, t) for t in range(1, len(x)))
            self.assertLessEqual(forced, self_intersection(w), w)

    def test_pruned_search_matches_naive_enumeration(self):
        lmax = 9
        naive = {k: set() for k in range(5)}
        for n in range(1, lmax + 1):
            for letters in itertools.product(LETTERS, repeat=n):
                w = "".join(letters)
                if cyclic_reduce(w) == w and canonical(w) == w:
                    si = self_intersection(w, cap=4)
                    if si <= 4 and is_minimal(w):
                        naive[si].add(w)
        for k in range(5):
            found, _ = minimal_words(k, lmax)
            self.assertEqual(len(found), len(set(found)))
            self.assertEqual(set(found), naive[k])

    def test_counts_from_the_literature(self):
        for k, expected in KNOWN_PRIMITIVE_ORBITS.items():
            words, _ = minimal_words(k, 4 * (k + 1))
            reps = count_orbits(words)
            primitive = [r for r in reps if primitive_root(r)[1] == 1]
            self.assertEqual(len(primitive), expected, k)


class Drawing(unittest.TestCase):
    def test_picture_has_exactly_si_crossings(self):
        rng = random.Random(5)
        for _ in range(300):
            w = random_word(rng, 1, 14)
            if rng.random() < 0.15:
                w *= rng.randint(2, 3)
            self.assertEqual(len(crossings(chords(w))), self_intersection(w), w)


if __name__ == "__main__":
    unittest.main()
