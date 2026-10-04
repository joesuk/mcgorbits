#!/bin/bash
# One slice of the short-word search at level K, for NYU's Torch cluster.
#
#   sbatch [--array=0-(N-1)] -c CORES -t TIME search.sbatch K N [RUNS]
#
# Runs "curves reps K 2K+2 I N" with I the array index (0 without --array) and
# leaves the orbit representatives found in
#   RUNS/short/kK/I.of.N.txt      (RUNS is $WORK/runs unless given)
# and one line "K I N visited seconds cores host" in I.of.N.info. A slice that
# already has its output does nothing, so an array can simply be resubmitted.
# Use submit.sh, which also sets the log file and the account.

set -euo pipefail
K=$1
N=$2
I=${SLURM_ARRAY_TASK_ID:-0}
WORK=${SCRATCH:-/scratch/$USER}/mcgorbits
RUNS=${3:-$WORK/runs}
OUT=$RUNS/short/k$K
BIN=$RUNS/bin/curves.$SLURM_JOB_ID
mkdir -p "$OUT" "$RUNS/bin"
if [ -s "$OUT/$I.of.$N.info" ]; then
    echo "slice $I of $N at k=$K already done"
    exit 0
fi

trap 'rm -f "$BIN" "$OUT/$I.of.$N.tmp" "$OUT/$I.of.$N.err"' EXIT
g++ -O3 -march=native -std=c++17 -fopenmp "$WORK/repo/src/cpp/curves.cpp" -o "$BIN"
export OMP_NUM_THREADS=${SLURM_CPUS_PER_TASK:-1}
echo "k=$K slice $I of $N on $(hostname) with $OMP_NUM_THREADS threads, $(date)"
SECONDS=0
"$BIN" reps "$K" $((2 * K + 2)) "$I" "$N" > "$OUT/$I.of.$N.tmp" 2> "$OUT/$I.of.$N.err"
mv "$OUT/$I.of.$N.tmp" "$OUT/$I.of.$N.txt"
echo "$K $I $N $(cat "$OUT/$I.of.$N.err") $SECONDS $OMP_NUM_THREADS $(hostname)" > "$OUT/$I.of.$N.info"
echo "done: $(wc -l < "$OUT/$I.of.$N.txt") representatives, $(cat "$OUT/$I.of.$N.info")"
