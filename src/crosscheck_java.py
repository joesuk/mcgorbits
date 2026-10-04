"""Compare freegroup.self_intersection with the original Java library.

Needs a JDK and the compiled library under ../output/bin. Checks every
cyclic word up to length 10 and 20000 random longer ones.

Usage: python3 crosscheck_java.py
"""

import itertools
import os
import random
import subprocess
import tempfile

from freegroup import LETTERS, canonical, cyclic_reduce, self_intersection

HERE = os.path.dirname(os.path.abspath(__file__))
LIBRARY = os.path.join(HERE, "..", "output", "bin")


def main():
    rng = random.Random(1)
    words = set()
    for n in range(1, 11):
        for letters in itertools.product(LETTERS, repeat=n):
            w = "".join(letters)
            if cyclic_reduce(w) == w and canonical(w) == w:
                words.add(w)
    exhaustive = len(words)
    while len(words) < exhaustive + 20000:
        w = cyclic_reduce("".join(rng.choice(LETTERS) for _ in range(rng.randint(11, 40))))
        if w and rng.random() < 0.1:
            w *= rng.randint(2, 4)
        if w:
            words.add(w)
    words = sorted(words)

    with tempfile.TemporaryDirectory() as build:
        subprocess.run(["javac", "-cp", LIBRARY, "-d", build, os.path.join(HERE, "SIRef.java")],
                       check=True)
        out = subprocess.run(["java", "-cp", build + os.pathsep + LIBRARY, "SIRef"],
                             input="\n".join(words) + "\n", capture_output=True,
                             text=True, check=True).stdout
    java = {w: int(si) for w, si, _ in (line.split() for line in out.splitlines())}
    bad = [(w, java[w], self_intersection(w)) for w in words if java[w] != self_intersection(w)]
    print(f"{len(words)} words ({exhaustive} exhaustive up to length 10), "
          f"largest SI {max(java.values())}, {len(bad)} mismatches")
    for row in bad[:20]:
        print("  word, java, python:", row)


if __name__ == "__main__":
    main()
