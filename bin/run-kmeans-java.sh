#!/usr/bin/env bash

DATA_PATH=/home/shuangfu/code/kmeans-test/
EPSILON=1e-6
MAX_ITER=20
K=5
work_path=$(cd `dirname $0`; pwd)
cd $work_path

if [ -f "${work_path}/config.properties" ]; then
    source $work_path/config.properties
fi

echo '---------args---------'
echo "DATA_PATH:"${DATA_PATH}
echo "EPSILON:"${EPSILON}
echo "MAX_ITER:"${MAX_ITER}
echo "K:"${K}
echo '----------end---------'

function run(){
    path=$1
    echo $path
    #java -XX:-UseGCOverheadLimit -Xmx10G -Xms10G -Xmn1G -Xss512m \
    # -cp ${work_path}/kmeans-test-1.0-SNAPSHOT-jar-with-dependencies.jar com.github2013.ml.AppKMeans \
    java -cp ${work_path}/ml-java-1.0-SNAPSHOT-jar-with-dependencies.jar com.github2013.ml.AppKMeans \
        ${THREAD_SIZE} \
        ${MAX_ITER} \
        ${EPSILON} \
        ${K} \
        ${path}
}

paths=""
count=0
log_path=${work_path}/kmeans-java-result.log
echo "=======result[`date +"%Y-%m-%d %H:%M:%S"`]=======" > ${log_path}
echo "file_row_count,type,spend_time(s)" >> ${log_path}
cd $DATA_PATH
for p in `ls *.txt*`; do
    file_count=`wc -l ${p}|cut -d' ' -f 1`
    ((count=count+file_count))
    echo "Shell:Total lines count:"$count
    paths="${paths},${DATA_PATH}/${p}"
    start_time=`date +%s`
    run ${paths:1}
    end_time=`date +%s`
    ((spend_time=$end_time-$start_time))
    echo "$count,java,$spend_time" >> ${log_path}
done

echo "--------------------------end-----------------------------"
