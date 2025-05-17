#!/bin/bash
for dir in api-gateway eureka-server crawling-server article-server strike-server quiz-server summary-server user-info-server; do
    echo "========================="
    echo "$dir 빌드 중..."
    cd $dir
    ./gradlew clean build
    cd ..
done


