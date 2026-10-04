"""Orbit counts level by level, each level using the one below.

bruteforce.py searches every word up to length 4(K+1). Most of that range is
spent on long words, and the orbits found there are not new: they are orbits
with K-1 self-intersections with one more loop round the puncture, which
costs four letters and one crossing. So for K >= 2 the orbits with K
self-intersections are found in two parts:

  short  shortest words of length <= 2K+2: exhaustive search, as before;
  long   shortest words of length >= 2K+3: take each orbit at level K-1 whose
         shortest words have length >= 2K-1 and insert a loop abAB (in any
         rotation and direction) at any place in any of its shortest words,
         keeping the results that are minimal and have exactly K crossings.

The short part is a proof-backed search. The long part rests on an
observation: that every orbit with shortest word of length >= 2K+3 arises
this way. That has been checked against the full exhaustive search for
2 <= K <= 10, where the two methods give identical sets of orbits; it is not
proved. Every orbit reported is verified individually, so if the observation
ever failed the count would be too small, never too large.

Levels are cached in ../results/fast_kK.txt.

The work is done by the compiled program cpp/curves.cpp when a C++ compiler is
available (see native.py), and by the Python reference code otherwise.

Usage: python3 fast.py K [--verify]

With --verify, each level is also computed by the full exhaustive search to
length 4(K+1) and compared; that is the test of the observation above.
"""

import multiprocessing
import os
import sys
import time

import native
from bruteforce import count_orbits, minimal_words
from freegroup import (canonical, cyclic_reduce, is_minimal, minimal_orbit, orbit_key,
                       primitive_root, self_intersection)

RESULTS = os.path.join(os.path.dirname(os.path.abspath(__file__)), "..", "results")
LOOPS = ("abAB", "bABa", "ABab", "BabA", "baBA", "aBAb", "BAba", "AbaB")


def loop_insertions(args):
    """Orbits with k self-intersections got by adding a loop to the orbit of rep."""
    rep, k = args
    candidates = set()
    for u in minimal_orbit(rep):
        for i in range(len(u)):
            for loop in LOOPS:
                w = u[:i] + loop + u[i:]
                if cyclic_reduce(w) == w:
                    candidates.add(canonical(w))
    return {orbit_key(w) for w in candidates
            if self_intersection(w, cap=k) == k and is_minimal(w)}


def search(k, lmax, processes):
    """Orbit representatives with a minimal word of length <= lmax."""
    if native.available():
        return set(native.orbit_representatives(k, lmax, processes))
    words, _ = minimal_words(k, lmax, processes)
    return set(count_orbits(words))


def level(k, processes):
    """Orbit representatives (unoriented curves, all homeomorphisms) at level k."""
    path = os.path.join(RESULTS, f"fast_k{k}.txt")
    if os.path.exists(path):
        with open(path) as f:
            return f.read().split()
    if k < 2:
        reps = search(k, 4 * (k + 1), processes)
    else:
        below = [w for w in level(k - 1, processes) if len(w) >= 2 * k - 1]
        reps = search(k, 2 * k + 2, processes)
        if native.available():
            reps |= set(native.loop_insertions(below, k, processes))
        else:
            with multiprocessing.Pool(processes) as pool:
                for found in pool.imap_unordered(loop_insertions, [(w, k) for w in below]):
                    reps |= found
    return save(path, reps)


def save(path, reps):
    reps = sorted(reps, key=lambda w: (len(w), w))
    os.makedirs(RESULTS, exist_ok=True)
    with open(path, "w") as f:
        f.write("\n".join(reps) + "\n")
    return reps


def exhaustive(k, processes, compute):
    """Orbit representatives at level k by full search to length 4(k+1), or None.

    Uses what bruteforce.py or an earlier call saved under ../results; otherwise
    runs the search if compute is set.
    """
    path = os.path.join(RESULTS, f"k{k}.txt")
    if os.path.exists(path):
        with open(path) as f:
            return {line.strip() for line in f if line.startswith("    ")}
    path = os.path.join(RESULTS, f"exhaustive_k{k}.txt")
    if os.path.exists(path):
        with open(path) as f:
            return set(f.read().split())
    if not compute:
        return None
    return set(save(path, search(k, 4 * (k + 1), processes)))


def main():
    args = [a for a in sys.argv[1:] if not a.startswith("--")]
    verify = "--verify" in sys.argv
    top = int(args[0])
    processes = os.cpu_count()
    print(f"{'k':>3} {'orbits':>8} {'primitive':>10} {'seconds':>8}  full exhaustive search")
    for k in range(top + 1):
        start = time.time()
        reps = level(k, processes)
        elapsed = time.time() - start
        primitive = sum(primitive_root(w)[1] == 1 for w in reps)
        start = time.time()
        known = exhaustive(k, processes, verify)
        check = "-" if known is None else (
            f"same orbits ({time.time() - start:.1f} s)" if known == set(reps) else "DIFFERENT")
        print(f"{k:>3} {len(reps):>8} {primitive:>10} {elapsed:>8.1f}  {check}", flush=True)


if __name__ == "__main__":
    main()
