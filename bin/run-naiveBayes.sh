#!/usr/bin/env bash

HAPPY=./data/happy.txt
HAPPY_TEST=./data/happy_test.txt
SAD=./data/sad.txt
SAD_TEST=./data/sad_test.txt

function run(){
    path=$1
    echo $path
    #java -XX:-UseGCOverheadLimit -Xmx10G -Xms10G -Xmn1G -Xss512m \
    # -cp ${work_path}/kmeans-test-1.0-SNAPSHOT-jar-with-dependencies.jar com.github2013.ml.AppKMeans \
    java -cp ./ml-java-1.0-SNAPSHOT-jar-with-dependencies.jar com.github2013.ml.AppNaiveBayes \
        ${HAPPY} \
        ${HAPPY_TEST} \
        ${SAD} \
        ${SAD_TEST}
}

run
echo "--------------------------end-----------------------------"
