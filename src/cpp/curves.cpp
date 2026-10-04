// Fast versions of the hot loops in freegroup.py, bruteforce.py and fast.py.
//
// Words are strings over a, b, A, B (A = a^-1, B = b^-1), read as cyclic words
// in F(a, b) = pi_1(punctured torus). Orbits are those of unoriented curves
// under all homeomorphisms. The Python code is the reference: every function
// here mirrors one there and is tested against it (test_native.py).
//
//   curves si                 words on stdin -> "word SI" per line
//   curves search K LMAX      minimal words of length <= LMAX with SI = K
//   curves reps K LMAX        one representative per orbit among those words
//   curves filter K           words on stdin -> those minimal with SI = K
//   curves keys               minimal words on stdin -> one representative
//                             per orbit met
//   curves children K         orbit representatives on stdin -> the orbits
//                             with SI = K got from them by inserting one
//                             loop round the puncture (fast.loop_insertions)
//
// The number of threads is taken from OMP_NUM_THREADS.

#include <algorithm>
#include <climits>
#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <iostream>
#include <string>
#include <unordered_set>
#include <vector>

using std::string;
using std::vector;

static const int MAXLEN = 256;

// Counterclockwise order of the edges at a vertex of the Cayley tree.
static inline int code(char c) {
    switch (c) {
        case 'a': return 0;
        case 'b': return 1;
        case 'A': return 2;
        default: return 3;
    }
}
static const char LETTER[4] = {'a', 'b', 'A', 'B'};
static inline int inv(int c) { return (c + 2) & 3; }
static inline char inv_letter(char c) { return c ^ 0x20; }

static string inverse(const string& w) {
    string out(w.rbegin(), w.rend());
    for (char& c : out) c = inv_letter(c);
    return out;
}

static string cyclic_reduce(const string& w) {
    string st;
    st.reserve(w.size());
    for (char c : w) {
        if (!st.empty() && st.back() == inv_letter(c)) st.pop_back();
        else st.push_back(c);
    }
    size_t i = 0, j = st.size();
    while (j - i >= 2 && st[i] == inv_letter(st[j - 1])) { ++i; --j; }
    return st.substr(i, j - i);
}

// Least rotation in ASCII order (A < B < a < b), as freegroup.canonical.
static string canonical(const string& w) {
    size_t n = w.size();
    if (n == 0) return w;
    string d = w + w;
    size_t best = 0;
    for (size_t i = 1; i < n; ++i)
        if (d.compare(i, n, d, best, n) < 0) best = i;
    return d.substr(best, n);
}

static string unoriented(const string& w) {
    string c = canonical(w), d = canonical(inverse(w));
    return c < d ? c : d;
}

// ---- self-intersection number ------------------------------------------

// Lifts (x, i) and (u, j) leave the base vertex along the same edge; walk to
// the end of their common segment and compare sides.
static inline bool crosses(const int* x, const int* u, int L, int i, int j) {
    int f = x[i];
    int p1 = inv(x[(i + L - 1) % L]), p2 = inv(u[(j + L - 1) % L]);
    int t = 1;
    while (x[(i + t) % L] == u[(j + t) % L]) ++t;
    int q1 = x[(i + t) % L], q2 = u[(j + t) % L];
    int back = inv(x[(i + t - 1) % L]);
    int tail = ((p1 - f) & 3) - ((p2 - f) & 3);
    int head = ((q1 - back) & 3) - ((q2 - back) & 3);
    return (tail > 0) == (head > 0);
}

// x primitive and cyclically reduced. With cap >= 0, stops early and returns
// some value > cap once the answer is known to exceed cap.
static int si_primitive(const int* x, int L, int cap) {
    int y[MAXLEN];
    for (int i = 0; i < L; ++i) y[i] = inv(x[L - 1 - i]);
    long limit = cap < 0 ? LONG_MAX : 2L * cap + 1;
    long total = 0;
    for (int i = 0; i < L; ++i) {
        int p1 = inv(x[(i + L - 1) % L]), f1 = x[i];
        for (int j = 0; j < L; ++j) {
            if (j != i) {
                int p2 = inv(x[(j + L - 1) % L]);
                if (p1 != p2) {
                    int f2 = x[j];
                    if (f1 == f2) {
                        if (crosses(x, x, L, i, j)) ++total;
                    } else if (p1 != f2 && p2 != f1 && ((f1 - p1) & 3) == 2) {
                        ++total;
                    }
                }
            }
            if (y[j] == f1 && inv(y[(j + L - 1) % L]) != p1)
                if (crosses(x, y, L, i, j)) ++total;
        }
        if (total > limit) return (int)(total / 2);
    }
    return (int)(total / 2);
}

// w cyclically reduced. SI(v^n) = n^2 SI(v) + n - 1.
static int self_intersection(const int* x, int L, int cap) {
    if (L == 0) return 0;
    for (int d = 1; d < L; ++d) {
        if (L % d) continue;
        bool periodic = true;
        for (int i = d; i < L && periodic; ++i) periodic = x[i] == x[i - d];
        if (periodic) {
            int n = L / d;
            return n * n * si_primitive(x, d, -1) + n - 1;
        }
    }
    return si_primitive(x, L, cap);
}

static int self_intersection(const string& w, int cap) {
    int x[MAXLEN];
    for (size_t i = 0; i < w.size(); ++i) x[i] = code(w[i]);
    return self_intersection(x, (int)w.size(), cap);
}

// ---- Whitehead automorphisms -------------------------------------------

struct Auto {
    string image[128];  // indexed by letter
};

static vector<Auto> type1, type2;

static void add_auto(vector<Auto>& out, const string& a, const string& b) {
    Auto phi;
    phi.image[(int)'a'] = a;
    phi.image[(int)'b'] = b;
    phi.image[(int)'A'] = inverse(a);
    phi.image[(int)'B'] = inverse(b);
    out.push_back(phi);
}

static void init_autos() {
    for (const char* x : {"a", "A"})
        for (const char* y : {"b", "B"}) {
            add_auto(type1, x, y);
            add_auto(type1, y, x);
        }
    for (const char* m : {"b", "B"}) {
        add_auto(type2, string("a") + m, "b");
        add_auto(type2, string(m) + "a", "b");
    }
    for (const char* m : {"a", "A"}) {
        add_auto(type2, "a", string("b") + m);
        add_auto(type2, "a", string(m) + "b");
    }
}

static string act(const Auto& phi, const string& w) {
    string out;
    out.reserve(2 * w.size());
    for (char c : w) out += phi.image[(int)c];
    return cyclic_reduce(out);
}

// Length of the cyclically reduced image, without building strings.
static int image_length(const Auto& phi, const char* w, int n) {
    char st[2 * MAXLEN];
    int top = 0;
    for (int i = 0; i < n; ++i)
        for (char c : phi.image[(int)w[i]]) {
            if (top && st[top - 1] == inv_letter(c)) --top;
            else st[top++] = c;
        }
    int i = 0, j = top;
    while (j - i >= 2 && st[i] == inv_letter(st[j - 1])) { ++i; --j; }
    return j - i;
}

static bool is_minimal(const char* w, int n) {
    for (const Auto& phi : type2)
        if (image_length(phi, w, n) < n) return false;
    return true;
}

// All minimal words (unoriented canonical form) in the orbit of the minimal
// word start; returns the least.
static string explore_orbit(const string& start, std::unordered_set<string>& seen) {
    size_t n = start.size();
    vector<string> queue;
    queue.push_back(unoriented(start));
    seen.insert(queue[0]);
    string best = queue[0];
    for (size_t head = 0; head < queue.size(); ++head) {
        string u = queue[head];
        for (const vector<Auto>* family : {&type1, &type2})
            for (const Auto& phi : *family) {
                string v = act(phi, u);
                if (v.size() != n) continue;
                v = unoriented(v);
                if (seen.insert(v).second) {
                    if (v < best) best = v;
                    queue.push_back(v);
                }
            }
    }
    return best;
}

// One representative per orbit among the given minimal words. The
// representative is the least word of the orbit's minimal set in canonical
// rotation, taking the lesser of a word and its inverse, as freegroup.orbit_key.
static vector<string> orbit_keys(const vector<string>& words) {
    std::unordered_set<string> seen;
    vector<string> keys;
    for (const string& w : words) {
        string u = unoriented(w);
        if (seen.count(u)) continue;
        keys.push_back(explore_orbit(u, seen));
    }
    std::sort(keys.begin(), keys.end());
    keys.erase(std::unique(keys.begin(), keys.end()), keys.end());
    return keys;
}

// ---- exhaustive search ---------------------------------------------------

// Crossings of lifts of the linear word x[0..t] first forced by x[t]; mirrors
// bruteforce.new_crossings.
static int new_crossings(const int* x, int t) {
    int count = 0;
    int xt = x[t];
    for (int e = 1; e < t; ++e) {
        if (x[e] == xt) continue;
        int m = 0;
        while (m < e && x[e - 1 - m] == x[t - 1 - m]) ++m;
        if (m == e) continue;
        if (m == 0) {
            int p1 = inv(x[e - 1]), p2 = inv(x[t - 1]);
            if (p1 != xt && p2 != x[e] && ((x[e] - p1) & 3) == 2) ++count;
            continue;
        }
        int i = e - m, j = t - m;
        int f = x[i];
        int back = inv(x[e - 1]);
        int tail = ((inv(x[i - 1]) - f) & 3) - ((inv(x[j - 1]) - f) & 3);
        int head = ((x[e] - back) & 3) - ((xt - back) & 3);
        if ((tail > 0) == (head > 0)) ++count;
    }
    for (int i = 1; i < t; ++i) {
        if (inv(x[i - 1]) == xt) continue;
        int m = 0;
        while (x[i + m] == inv(x[t - 1 - m])) ++m;
        if (m == 0) continue;
        int f = x[i];
        int back = inv(x[i + m - 1]);
        int tail = ((inv(x[i - 1]) - f) & 3) - ((xt - f) & 3);
        int head = ((x[i + m] - back) & 3) - ((inv(x[t - m - 1]) - back) & 3);
        if ((tail > 0) == (head > 0)) ++count;
    }
    return count;
}

static const char ORDER[5] = "ABab";  // ASCII order, so prenecklaces match canonical()

struct Task {
    string prefix;
    int p, forced;
};

struct Search {
    int k, lmax, frontier_depth;
    char letters[MAXLEN];
    int codes[MAXLEN];
    long visited = 0;
    vector<string> found;
    vector<Task>* frontier = nullptr;

    // letters[0..t) is a prenecklace whose longest Lyndon prefix has length p
    void extend(int t, int p, int forced) {
        if (frontier && t == frontier_depth && t < lmax) {
            frontier->push_back({string(letters, t), p, forced});
            return;
        }
        ++visited;
        if (t % p == 0 && letters[t - 1] != inv_letter(letters[0]))
            if (is_minimal(letters, t) && self_intersection(codes, t, k) == k)
                found.emplace_back(letters, t);
        if (t == lmax) return;
        char prev = inv_letter(letters[t - 1]);
        for (int r = 0; r < 4; ++r) {
            char c = ORDER[r];
            if (c < letters[t - p] || c == prev) continue;
            letters[t] = c;
            codes[t] = code(c);
            int f = forced + new_crossings(codes, t);
            if (f <= k) extend(t + 1, c == letters[t - p] ? p : t + 1, f);
        }
    }

    void start(const Task& task) {
        int t = (int)task.prefix.size();
        for (int i = 0; i < t; ++i) {
            letters[i] = task.prefix[i];
            codes[i] = code(letters[i]);
        }
        extend(t, task.p, task.forced);
    }
};

static vector<string> minimal_words(int k, int lmax, long& visited) {
    vector<Task> tasks;
    Search top;
    top.k = k;
    top.lmax = lmax;
    top.frontier_depth = 12;
    top.frontier = &tasks;
    for (int r = 0; r < 4; ++r) top.start({string(1, ORDER[r]), 1, 0});
    vector<string> found = top.found;
    visited = top.visited;

    #pragma omp parallel
    {
        Search local;
        local.k = k;
        local.lmax = lmax;
        local.frontier_depth = 0;
        #pragma omp for schedule(dynamic, 1) nowait
        for (size_t i = 0; i < tasks.size(); ++i) local.start(tasks[i]);
        #pragma omp critical
        {
            found.insert(found.end(), local.found.begin(), local.found.end());
            visited += local.visited;
        }
    }
    std::sort(found.begin(), found.end());
    return found;
}

// ---- command line ----------------------------------------------------------

static const char* LOOPS[8] = {"abAB", "bABa", "ABab", "BabA", "baBA", "aBAb", "BAba", "AbaB"};

static vector<string> read_words() {
    vector<string> words;
    string w;
    while (std::cin >> w) words.push_back(w);
    return words;
}

static void print(const vector<string>& words) {
    string out;
    for (const string& w : words) {
        out += w;
        out += '\n';
    }
    fwrite(out.data(), 1, out.size(), stdout);
}

int main(int argc, char** argv) {
    init_autos();
    string mode = argc > 1 ? argv[1] : "";
    if (mode == "si") {
        for (const string& w : read_words())
            printf("%s %d\n", w.c_str(), self_intersection(cyclic_reduce(w), -1));
    } else if ((mode == "search" || mode == "reps") && argc == 4) {
        long visited = 0;
        vector<string> words = minimal_words(atoi(argv[2]), atoi(argv[3]), visited);
        fprintf(stderr, "%ld\n", visited);
        print(mode == "search" ? words : orbit_keys(words));
    } else if (mode == "filter" && argc == 3) {
        int k = atoi(argv[2]);
        vector<string> words = read_words();
        vector<char> keep(words.size(), 0);
        #pragma omp parallel for schedule(dynamic, 64)
        for (size_t i = 0; i < words.size(); ++i) {
            const string& w = words[i];
            keep[i] = is_minimal(w.data(), (int)w.size()) && self_intersection(w, k) == k;
        }
        vector<string> out;
        for (size_t i = 0; i < words.size(); ++i)
            if (keep[i]) out.push_back(words[i]);
        print(out);
    } else if (mode == "keys") {
        print(orbit_keys(read_words()));
    } else if (mode == "children" && argc == 3) {
        int k = atoi(argv[2]);
        vector<string> parents = read_words();
        std::unordered_set<string> survivors;
        #pragma omp parallel
        {
            std::unordered_set<string> local;
            #pragma omp for schedule(dynamic, 1) nowait
            for (size_t r = 0; r < parents.size(); ++r) {
                std::unordered_set<string> orbit;
                explore_orbit(parents[r], orbit);
                for (const string& u : orbit)
                    for (size_t i = 0; i < u.size(); ++i)
                        for (const char* loop : LOOPS) {
                            string w = u.substr(0, i) + loop + u.substr(i);
                            if (cyclic_reduce(w).size() != w.size()) continue;
                            if (is_minimal(w.data(), (int)w.size()) && self_intersection(w, k) == k)
                                local.insert(unoriented(w));
                        }
            }
            #pragma omp critical
            survivors.insert(local.begin(), local.end());
        }
        print(orbit_keys(vector<string>(survivors.begin(), survivors.end())));
    } else {
        fprintf(stderr, "usage: curves si | search K LMAX | reps K LMAX | filter K | keys | children K\n");
        return 2;
    }
    return 0;
}
