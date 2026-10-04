"""Draw one curve from each orbit, in minimal position, on the torus.

The torus is the square with opposite sides glued and the puncture at the
corners. Reading a word left to right, a leaves through the right side and
comes back in on the left, b leaves through the top and comes back in at the
bottom, and A, B do the reverse.

Where a strand meets a side is taken from hyperbolic geometry: the surface is
given a metric as a one-holed torus, the word is realised as its closed
geodesic, and the crossings with each side are placed in the order in which
the geodesic makes them. Geodesics cross minimally, and straight chords in a
square cross exactly when their endpoints alternate, so the picture has
exactly SI(w) crossings. That count is asserted for every curve drawn.

A power v^n has no geodesic in general position, so it is drawn as n parallel
copies of v, each pushed a little further to the left, joined end to end.

Usage: python3 draw.py K [OUTPUT.svg]
"""

import cmath
import math
import os
import sys

from bruteforce import count_orbits, minimal_words
from freegroup import cyclic_reduce, minimal_orbit, primitive_root, self_intersection

# A one-holed torus: a and b translate by L_A and L_B along axes through the
# centre of the disc meeting at angle THETA, and the square is the region
# between the perpendiculars to each axis at SHIFT - L/2 and SHIFT + L/2.
# Every such surface has a symmetry (the hyperelliptic involution) fixing the
# centre and the midpoints of the unshifted sides, and symmetric curves cross
# themselves exactly at those points; the shifts keep them off the sides.
L_A, L_B, THETA = 2.2, 2.6, math.radians(80)
_AXIS = {"a": (L_A, 0.12, 1), "b": (L_B, 0.08, cmath.exp(1j * THETA))}
assert sum(math.acos(math.tanh(l / 2 - shift)) for l, shift, _ in _AXIS.values()) \
    < min(THETA, math.pi - THETA), "the four sides of the square must be disjoint"


# Isometries of the unit disc as pairs (u, v) for z -> (u z + v) / (v* z + u*).
def _translation(axis, distance):
    return (math.cosh(distance / 2), math.sinh(distance / 2) * _AXIS[axis][2])


def _generator(letter):
    axis = letter.lower()
    return _translation(axis, _AXIS[axis][0] if letter.islower() else -_AXIS[axis][0])


def _compose(m, n):
    (u1, v1), (u2, v2) = m, n
    return (u1 * u2 + v1 * v2.conjugate(), u1 * v2 + v1 * u2.conjugate())


def _act(m, z):
    u, v = m
    return (u * z + v) / (v.conjugate() * z + u.conjugate())


def _axis_endpoints(m):
    u, v = m
    r = math.sqrt(u.real ** 2 - 1)
    return (1j * u.imag + r) / v.conjugate(), (1j * u.imag - r) / v.conjugate()


def crossing_heights(w):
    """For each letter of the primitive word w, where its geodesic crosses the
    side it leaves through: signed distance along the side from the axis of
    the generator, positive to the left of the generator's direction. The two
    glued sides share this coordinate."""
    heights = []
    for i, letter in enumerate(w):
        m = (1, 0)
        for c in w[i:] + w[:i]:
            m = _compose(m, _generator(c))
        # Move the side onto the diameter perpendicular to the real axis; in
        # the Klein model the geodesic is then a straight chord.
        axis = letter.lower()
        length, shift, direction = _AXIS[axis]
        back = _translation(axis, -shift - (length / 2 if letter.islower() else -length / 2))
        p, q = (_act(back, z) / direction for z in _axis_endpoints(m))
        y = p.imag - p.real * (q.imag - p.imag) / (q.real - p.real)
        heights.append(math.atanh(y))
    return heights


def _side_point(letter, f):
    """Point at fraction f along the side crossed by letter (unit square, y up)."""
    return {"a": (1, f), "A": (0, f), "b": (1 - f, 1), "B": (1 - f, 0)}[letter]


def _perimeter(p):
    x, y = p
    if y == 0:
        return x
    if x == 1:
        return 1 + y
    if y == 1:
        return 3 - x
    return 4 - y


def chords(w):
    """Straight chords of the unit square, one per letter, drawing w."""
    w = cyclic_reduce(w)
    v, n = primitive_root(w)
    heights = crossing_heights(v)
    # Sort the crossings of each pair of sides; pass j of a power is pushed to
    # the left of the direction of travel.
    fraction = {}
    for pair in ("aA", "bB"):
        on_pair = [t for t in range(len(w)) if w[t] in pair]
        on_pair.sort(key=lambda t: (heights[t % len(v)],
                                    (t // len(v)) * (1 if w[t].islower() else -1)))
        gaps = [heights[s % len(v)] - heights[t % len(v)]
                for t, s in zip(on_pair, on_pair[1:]) if s % len(v) != t % len(v)]
        assert all(g > 1e-9 for g in gaps), f"{w}: two crossings coincide on a side"
        for rank, t in enumerate(on_pair):
            fraction[t] = (rank + 1) / (len(on_pair) + 1)
    out = []
    for t in range(len(w)):
        entry = _side_point(w[t - 1].swapcase(), fraction[(t - 1) % len(w)])
        out.append((entry, _side_point(w[t], fraction[t])))
    return out


def crossings(segments):
    """Points where two of the chords cross."""
    points = []
    for i, (p1, p2) in enumerate(segments):
        lo, hi = sorted((_perimeter(p1), _perimeter(p2)))
        for q1, q2 in segments[:i]:
            if (lo < _perimeter(q1) < hi) != (lo < _perimeter(q2) < hi):
                d1 = (p2[0] - p1[0], p2[1] - p1[1])
                d2 = (q2[0] - q1[0], q2[1] - q1[1])
                s = ((q1[0] - p1[0]) * d2[1] - (q1[1] - p1[1]) * d2[0]) / (d1[0] * d2[1] - d1[1] * d2[0])
                points.append((p1[0] + s * d1[0], p1[1] + s * d1[1]))
    return points


def display_form(orbit):
    """The minimal word of an orbit with the fewest inverse letters."""
    def rotations(w):
        return (w[i:] + w[:i] for i in range(len(w)))
    order = "abAB".index
    return min((r for w in orbit for r in rotations(w)),
               key=lambda r: (sum(c.isupper() for c in r), [order(c) for c in r]))


def orbit_representatives(k):
    words, _ = minimal_words(k, 4 * (k + 1))
    reps = [display_form(minimal_orbit(r)) for r in count_orbits(words)]
    return sorted(reps, key=lambda w: (len(w), ["abAB".index(c) for c in w]))


SIZE, PAD, LABEL, COLUMNS, HEADER = 150, 22, 30, 6, 64


def _cell(w, index, ox, oy):
    segments = chords(w)
    dots = crossings(segments)
    assert len(dots) == self_intersection(w), f"{w}: drew {len(dots)} crossings"

    def px(p):
        return f"{ox + p[0] * SIZE:.1f},{oy + (1 - p[1]) * SIZE:.1f}"

    out = [f'<rect x="{ox}" y="{oy}" width="{SIZE}" height="{SIZE}" class="square"/>']
    for cx, cy, start in ((0, 0, 0), (1, 0, 90), (1, 1, 180), (0, 1, 270)):
        # quarter disc inside the square at each corner: the puncture
        a0, a1 = math.radians(start), math.radians(start + 90)
        x, y = ox + cx * SIZE, oy + cy * SIZE
        out.append(f'<path d="M{x},{y} L{x + 9 * math.cos(a0):.1f},{y + 9 * math.sin(a0):.1f} '
                   f'A9,9 0 0 1 {x + 9 * math.cos(a1):.1f},{y + 9 * math.sin(a1):.1f} Z" class="hole"/>')
    for p, q in segments:
        out.append(f'<path d="M{px(p)} L{px(q)}" class="curve"/>')
    for p in dots:
        out.append(f'<circle cx="{px(p).split(",")[0]}" cy="{px(p).split(",")[1]}" r="2.2" class="dot"/>')
    out.append(f'<text x="{ox + SIZE / 2}" y="{oy + SIZE + 17}" class="word">{index}. {w}</text>')
    return out


def svg(k):
    reps = orbit_representatives(k)
    primitive = sum(primitive_root(w)[1] == 1 for w in reps)
    rows = -(-len(reps) // COLUMNS)
    width = COLUMNS * (SIZE + PAD) + PAD
    height = HEADER + rows * (SIZE + LABEL + PAD)
    out = [
        f'<svg xmlns="http://www.w3.org/2000/svg" width="{width}" height="{height}" '
        f'viewBox="0 0 {width} {height}">',
        "<style>"
        ".square{fill:#fff;stroke:#888;stroke-width:1}"
        ".hole{fill:#bbb}"
        ".curve{fill:none;stroke:#1f4e9c;stroke-width:1.5;stroke-linecap:round}"
        ".dot{fill:#d1342f}"
        "text{font-family:DejaVu Sans Mono,Menlo,Consolas,monospace;fill:#222}"
        ".word{font-size:11px;text-anchor:middle}"
        ".title{font-size:15px;font-weight:bold}"
        ".note{font-size:11px;fill:#555}"
        "</style>",
        f'<rect width="{width}" height="{height}" fill="#fafafa"/>',
        f'<text x="{PAD}" y="26" class="title">{k} self-intersection{"" if k == 1 else "s"}: '
        f'{len(reps)} orbits of unoriented curves, {primitive} primitive</text>',
        f'<text x="{PAD}" y="46" class="note">Opposite sides are glued; the puncture is at the '
        'corners. a exits right and re-enters left, b exits top and re-enters bottom.</text>',
    ]
    for n, w in enumerate(reps):
        ox = PAD + (n % COLUMNS) * (SIZE + PAD)
        oy = HEADER + (n // COLUMNS) * (SIZE + LABEL + PAD)
        out += _cell(w, n + 1, ox, oy)
    out.append("</svg>")
    return "\n".join(out) + "\n"


def main():
    k = int(sys.argv[1])
    here = os.path.dirname(os.path.abspath(__file__))
    path = sys.argv[2] if len(sys.argv) > 2 else os.path.join(here, "..", "figures", f"k{k}.svg")
    os.makedirs(os.path.dirname(os.path.abspath(path)), exist_ok=True)
    with open(path, "w") as f:
        f.write(svg(k))
    print("wrote", os.path.normpath(path))


if __name__ == "__main__":
    main()
