"""Python front end to cpp/curves.cpp, the compiled versions of the hot loops.

The program is built on first use (g++ with OpenMP) into build/curves and
rebuilt when the source changes. available() says whether that worked; callers
fall back to the Python implementations when it did not.
"""

import os
import shutil
import subprocess

HERE = os.path.dirname(os.path.abspath(__file__))
SOURCE = os.path.join(HERE, "cpp", "curves.cpp")
BINARY = os.path.join(HERE, "build", "curves")


def available():
    """Build the program if needed; False if there is no compiler."""
    if os.path.exists(BINARY) and os.path.getmtime(BINARY) >= os.path.getmtime(SOURCE):
        return True
    if shutil.which("g++") is None:
        return False
    os.makedirs(os.path.dirname(BINARY), exist_ok=True)
    subprocess.run(["g++", "-O3", "-march=native", "-std=c++17", "-fopenmp",
                    SOURCE, "-o", BINARY], check=True)
    return True


def _run(args, words=None, processes=None):
    if not available():
        raise RuntimeError("no C++ compiler found")
    env = dict(os.environ)
    if processes:
        env["OMP_NUM_THREADS"] = str(processes)
    done = subprocess.run([BINARY] + [str(a) for a in args],
                          input=None if words is None else "\n".join(words) + "\n",
                          capture_output=True, text=True, check=True, env=env)
    return done


def self_intersections(words):
    """Map each word to its self-intersection number."""
    out = _run(["si"], words).stdout.split()
    return dict(zip(out[::2], map(int, out[1::2])))


def minimal_words(k, lmax, processes=None):
    """As bruteforce.minimal_words: (words, prefixes visited)."""
    done = _run(["search", k, lmax], processes=processes)
    return done.stdout.split(), int(done.stderr)


def orbit_representatives(k, lmax, processes=None):
    """One representative per orbit with a minimal word of length <= lmax."""
    return _run(["reps", k, lmax], processes=processes).stdout.split()


def minimal_with_si(words, k, processes=None):
    """The given words that are minimal and have self-intersection number k."""
    return _run(["filter", k], words, processes).stdout.split()


def orbit_keys(words):
    """One representative per orbit among the given minimal words."""
    return _run(["keys"], words).stdout.split()


def loop_insertions(parents, k, processes=None):
    """Orbits with k self-intersections got by adding a loop to the given orbits."""
    return _run(["children", k], parents, processes).stdout.split()
