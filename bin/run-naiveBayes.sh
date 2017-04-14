#!/usr/bin/env bash

#HAPPY=./data/happy_large.txt
#HAPPY_TEST=./data/happy_large_test_all.txt
#SAD=./data/sad_large.txt
#SAD_TEST=./data/sad_large_test_all.txt

HAPPY=./data/sentiment_cn/1000000_pos.txt
HAPPY_TEST=./data/sentiment_cn/test_pos.txt
SAD=./data/sentiment_cn/1000000_neg.txt
SAD_TEST=./data/sentiment_cn/test_neg.txt

TYPE=multinomial
#TYPE=bernoulli
LOW_PROP=0.7

function run(){
    path=$1
    echo $path
    #java -XX:-UseGCOverheadLimit -Xmx10G -Xms10G -Xmn1G -Xss512m \
    # -cp ${work_path}/kmeans-test-1.0-SNAPSHOT-jar-with-dependencies.jar com.github2013.ml.AppKMeans \
    java -cp ./ml-java-1.0-SNAPSHOT-jar-with-dependencies.jar com.github2013.ml.AppNaiveBayes \
        ${HAPPY} \
        ${HAPPY_TEST} \
        ${SAD} \
        ${SAD_TEST} \
        ${TYPE} \
        ${LOW_PROP}
}


run
echo "--------------------------end-----------------------------"
