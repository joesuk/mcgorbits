#!/bin/bash
# Submit jobs on Torch (run on the login node, from anywhere).
#
#   submit.sh search K N CORES TIME [MAXRUNNING]   N slices of the search at level K
#   submit.sh levels FROM TO CORES TIME [JOBIDS]   assemble levels, after the given
#                                                  colon-separated jobs have ended
#
# No partition is named: the cluster picks it from the time limit (over 6 h
# goes to the 128-core cs nodes).

set -euo pipefail
WORK=${SCRATCH:-/scratch/$USER}/mcgorbits
HERE=$WORK/repo/cluster
LOGS=$WORK/runs/logs
mkdir -p "$LOGS"
COMMON="--parsable --account=torch_pr_1008_general --mem=8G"

case $1 in
search)
    K=$2 N=$3 CORES=$4 TIME=$5 MAX=${6:-$3}
    sbatch $COMMON -J "mcg-k$K" -c "$CORES" -t "$TIME" --array="0-$((N - 1))%$MAX" \
        -o "$LOGS/search-k$K-%a.of.$N-%A.out" "$HERE/search.sbatch" "$K" "$N"
    ;;
levels)
    FROM=$2 TO=$3 CORES=$4 TIME=$5
    sbatch $COMMON -J "mcg-levels" -c "$CORES" -t "$TIME" ${6:+--dependency=afterany:$6} \
        -o "$LOGS/levels-$FROM-$TO-%j.out" "$HERE/levels.sbatch" "$FROM" "$TO"
    ;;
*)
    sed -n '2,9p' "$0"
    exit 2
    ;;
esac
