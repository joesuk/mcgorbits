#!/bin/bash
# Assemble the levels K = FROM..TO from the slices left by search.sbatch.
#
#   sbatch -c CORES -t TIME levels.sbatch FROM TO [RUNS]
#
# Level K is the union of
#   short  the slices of the search to length 2K+2, and
#   long   the loop insertions of the orbits at level K-1 of length >= 2K-1,
# as in src/fast.py, and is written to RUNS/levels/fast_kK.txt (RUNS is
# $WORK/runs unless given) in the
# format of results/fast_kK.txt. Level FROM-1 is taken from there or from the
# repository's results/. Stops at the first level whose slices are incomplete,
# so it can be rerun as more slices finish. A line per level goes to
# RUNS/levels/summary.txt: K orbits primitive short long seconds.

set -euo pipefail
FROM=$1
TO=$2
WORK=${SCRATCH:-/scratch/$USER}/mcgorbits
RUNS=${3:-$WORK/runs}
LEVELS=$RUNS/levels
BIN=$RUNS/bin/curves.$SLURM_JOB_ID
mkdir -p "$LEVELS" "$RUNS/bin"
export LC_ALL=C

trap 'rm -f "$BIN" "$LEVELS"/*.tmp' EXIT
g++ -O3 -march=native -std=c++17 -fopenmp "$WORK/repo/src/cpp/curves.cpp" -o "$BIN"
export OMP_NUM_THREADS=${SLURM_CPUS_PER_TASK:-1}

level() {  # path of level $1, copied from the repository if it is there
    if [ ! -s "$LEVELS/fast_k$1.txt" ] && [ -s "$WORK/repo/results/fast_k$1.txt" ]; then
        cp "$WORK/repo/results/fast_k$1.txt" "$LEVELS/fast_k$1.txt"
    fi
    echo "$LEVELS/fast_k$1.txt"
}

for K in $(seq "$FROM" "$TO"); do
    [ -s "$LEVELS/fast_k$K.txt" ] && continue
    below=$(level $((K - 1)))
    [ -s "$below" ] || { echo "level $((K - 1)) is missing"; exit 1; }
    dir=$RUNS/short/k$K
    N=$(ls "$dir" 2>/dev/null | sed -n 's/^[0-9]*\.of\.\([0-9]*\)\.info$/\1/p' | sort -u)
    if [ "$(echo "$N" | wc -w)" != 1 ] || [ "$(ls "$dir"/*.of."$N".info | wc -l)" != "$N" ]; then
        echo "k=$K: slices incomplete, stopping"
        exit 0
    fi
    SECONDS=0
    cat "$dir"/*.of."$N".txt | sort -u > "$LEVELS/short_k$K.tmp"
    awk -v k="$K" 'length($0) >= 2 * k - 1' "$below" | "$BIN" children "$K" > "$LEVELS/long_k$K.tmp"
    cat "$LEVELS/short_k$K.tmp" "$LEVELS/long_k$K.tmp" | awk '{print length($0), $0}' |
        sort -k1,1n -k2,2 -u | cut -d' ' -f2 > "$LEVELS/fast_k$K.tmp"
    line="$K $("$BIN" stats < "$LEVELS/fast_k$K.tmp" | cut -d' ' -f1,2)"
    line="$line $(wc -l < "$LEVELS/short_k$K.tmp") $(wc -l < "$LEVELS/long_k$K.tmp") $SECONDS"
    mv "$LEVELS/fast_k$K.tmp" "$LEVELS/fast_k$K.txt"
    echo "$line" | tee -a "$LEVELS/summary.txt"
done
