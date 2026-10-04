"""Cyclic words in F(a, b) = pi_1(punctured torus).

Words are strings over a, b, A, B with A = a^-1 and B = b^-1. A free homotopy
class of closed curves is a cyclically reduced word up to rotation.

Three things live here:
  * self_intersection: minimal self-intersection number of a class,
  * Whitehead's algorithm: minimal words and a canonical orbit representative
    under Out(F2), which is the extended mapping class group of the punctured
    torus (Dehn-Nielsen-Baer),
  * small helpers (reduction, primitive roots, canonical rotations).

Nothing here is ported from the Java library; it is written from the
definitions so that it can serve as an independent check on it.
"""

from collections import deque

LETTERS = "abAB"
# Counterclockwise order of the four edges leaving any vertex of the Cayley
# tree, as embedded in the universal cover of the punctured torus.
CODE = {"a": 0, "b": 1, "A": 2, "B": 3}


def inverse(w):
    return w[::-1].swapcase()


def free_reduce(w):
    out = []
    for c in w:
        if out and out[-1] == c.swapcase():
            out.pop()
        else:
            out.append(c)
    return "".join(out)


def cyclic_reduce(w):
    w = free_reduce(w)
    i, j = 0, len(w) - 1
    while i < j and w[i] == w[j].swapcase():
        i += 1
        j -= 1
    return w[i:j + 1]


def canonical(w):
    """Least rotation of a cyclically reduced word (ASCII order A < B < a < b)."""
    if not w:
        return w
    d = w + w
    n = len(w)
    return min(d[i:i + n] for i in range(n))


def primitive_root(w):
    """Return (v, n) with w = v^n as cyclic words and v primitive."""
    n = len(w)
    for d in range(1, n + 1):
        if n % d == 0 and w[:d] * (n // d) == w:
            return w[:d], n // d
    return w, 1


def self_intersection(w, cap=None):
    """Minimal self-intersection number of the free homotopy class of w.

    Lifts of the curve are the axes of the conjugates of w in the Cayley tree.
    The tree sits in the plane with edges leaving each vertex in the cyclic
    order a, b, A, B, so two axes cross iff they arrive at their common segment
    on opposite sides from how they leave it. The self-intersection number of
    a primitive class is the number of crossing pairs of axes up to the action
    of F2; for w = v^n it is n^2 SI(v) + n - 1.

    If cap is given, counting stops early and some value > cap is returned as
    soon as the answer is known to exceed cap.
    """
    w = cyclic_reduce(w)
    if not w:
        return 0
    v, n = primitive_root(w)
    if n > 1:
        return n * n * self_intersection(v) + n - 1

    L = len(w)
    x = [CODE[c] for c in w]
    y = [(c + 2) % 4 for c in reversed(x)]  # the inverse word
    limit = None if cap is None else 2 * cap + 1
    total = 0

    def crosses(u, i, j, t_start):
        # Axes (x, i) and (u, j) leave the base vertex along the same edge.
        # Walk to the end of their common segment and compare sides.
        f = x[i]
        p1 = (x[i - 1] + 2) % 4
        p2 = (u[j - 1] + 2) % 4
        t = t_start
        while x[(i + t) % L] == u[(j + t) % L]:
            t += 1
        q1 = x[(i + t) % L]
        q2 = u[(j + t) % L]
        back = (x[(i + t - 1) % L] + 2) % 4
        tail = (p1 - f) % 4 - (p2 - f) % 4
        head = (q1 - back) % 4 - (q2 - back) % 4
        return (tail > 0) == (head > 0)

    # Each unordered pair of axes is found twice in each of the loops below.
    for i in range(L):
        p1 = (x[i - 1] + 2) % 4
        f1 = x[i]
        for j in range(L):
            # second axis reads w forwards
            if j != i:
                p2 = (x[j - 1] + 2) % 4
                if p1 != p2:
                    f2 = x[j]
                    if f1 == f2:
                        if crosses(x, i, j, 1):
                            total += 1
                    elif p1 != f2 and p2 != f1 and (f1 - p1) % 4 == 2:
                        # the axes meet in a single vertex and go straight
                        # through it in the two different directions
                        total += 1
            # second axis reads w backwards; axes meeting in a single vertex
            # were already counted above
            if y[j] == f1 and (y[j - 1] + 2) % 4 != p1:
                if crosses(y, i, j, 1):
                    total += 1
        if limit is not None and total > limit:
            return total // 2
    return total // 2


def intersection(w, u):
    """Minimal number of intersections between curves in the classes of w and u.

    Counted like self_intersection: pairs made of a lift of w and a lift of u
    that cross, up to the action of F2. For powers the count multiplies. If w
    and u are powers of the same curve this returns 2 SI(root) per pair of
    sheets, the number of crossings of a curve with a pushed-off copy.
    """
    w, u = cyclic_reduce(w), cyclic_reduce(u)
    if not w or not u:
        return 0
    (w, m), (u, n) = primitive_root(w), primitive_root(u)
    if canonical(u) in (canonical(w), canonical(inverse(w))):
        return 2 * m * n * self_intersection(w)
    x = [CODE[c] for c in w]
    total = 0
    for forward, z in ((True, [CODE[c] for c in u]), (False, [CODE[c] for c in inverse(u)])):
        for i in range(len(x)):
            p1 = (x[i - 1] + 2) % 4
            f = x[i]
            for j in range(len(z)):
                p2 = (z[j - 1] + 2) % 4
                if p1 == p2:
                    continue
                if z[j] != f:
                    # a crossing at a single vertex; count it once, not once
                    # for each direction of u
                    if forward and p1 != z[j] and p2 != f and (f - p1) % 4 == 2:
                        total += 1
                    continue
                t = 1
                while x[(i + t) % len(x)] == z[(j + t) % len(z)]:
                    t += 1
                back = (x[(i + t - 1) % len(x)] + 2) % 4
                tail = (p1 - f) % 4 - (p2 - f) % 4
                head = (x[(i + t) % len(x)] - back) % 4 - (z[(j + t) % len(z)] - back) % 4
                if (tail > 0) == (head > 0):
                    total += 1
    return m * n * total


def _lift(images):
    """Extend a map on {a, b} to all four letters."""
    phi = dict(images)
    for g in "ab":
        phi[g.upper()] = inverse(phi[g])
    return phi


def _det(phi):
    def ab(w):
        return (w.count("a") - w.count("A"), w.count("b") - w.count("B"))
    (p, q), (r, s) = ab(phi["a"]), ab(phi["b"])
    return p * s - q * r


def _whitehead_automorphisms():
    autos = []
    # Type 1: signed permutations of the basis.
    for x in "aA":
        for y in "bB":
            autos.append(_lift({"a": x, "b": y}))
            autos.append(_lift({"a": y, "b": x}))
    type1 = len(autos)
    # Type 2: multiply one generator by the other on the right or the left.
    for g, h in (("a", "b"), ("b", "a")):
        for m in (h, h.upper()):
            autos.append(_lift({g: g + m, h: h}))
            autos.append(_lift({g: m + g, h: h}))
    return [(phi, _det(phi)) for phi in autos], type1


WHITEHEAD, _N_TYPE1 = _whitehead_automorphisms()
_TYPE2 = [phi for phi, _ in WHITEHEAD[_N_TYPE1:]]


def apply(phi, w):
    return cyclic_reduce("".join(phi[c] for c in w))


def is_minimal(w):
    n = len(w)
    return all(len(apply(phi, w)) >= n for phi in _TYPE2)


def minimize(w):
    """A shortest word in the Out(F2)-orbit of w (Whitehead's theorem)."""
    w = cyclic_reduce(w)
    improved = True
    while improved:
        improved = False
        for phi in _TYPE2:
            u = apply(phi, w)
            if len(u) < len(w):
                w, improved = u, True
    return w


def minimal_orbit(w, unoriented=True, orientation_preserving=False):
    """All minimal words (in canonical rotation) in the orbit of the class of w.

    The orbit is taken under Out(F2) (all homeomorphisms), or under its index
    two subgroup of orientation-preserving mapping classes if
    orientation_preserving is set. With unoriented set, w and w^-1 are the
    same curve.

    By Whitehead's peak reduction, two minimal words are in the same orbit iff
    they are joined by Whitehead automorphisms passing only through minimal
    words, so a search of that finite set decides the question. The same holds
    for the automorphism joining them, which is what makes it legitimate to
    track the determinant along the search.
    """
    start = canonical(minimize(w))
    n = len(start)
    seen = {(start, 1)}
    queue = deque(seen)
    while queue:
        u, sign = queue.popleft()
        nxt = []
        for phi, det in WHITEHEAD:
            v = apply(phi, u)
            if len(v) == n:
                nxt.append((canonical(v), sign * det))
        if unoriented:
            nxt.append((canonical(inverse(u)), sign))
        for state in nxt:
            if state not in seen:
                seen.add(state)
                queue.append(state)
    if orientation_preserving:
        return {u for u, sign in seen if sign == 1}
    return {u for u, _ in seen}


def orbit_key(w, **kw):
    """Canonical representative of the orbit of the class of w.

    Takes the same options as minimal_orbit.
    """
    return min(minimal_orbit(w, **kw))
