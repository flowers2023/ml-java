#!/usr/bin/env bash
mvn clean compile assembly:single && \
rm -rf bin/ml-java-1.0-SNAPSHOT-jar-with-dependencies.jar && \
cp target/ml-java-1.0-SNAPSHOT-jar-with-dependencies.jar bin/ && \
scp -P12598 -r ./bin/*.jar root@sgy:/root/test/ && \
echo "jar包更新成功!!!"
#scp -P12598 -r ../data root@sgy:/root/test/ && \
