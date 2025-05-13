#!/bin/bash
for dir in api-gateway eureka-server crawling-server article-server quiz-server strike-server summary-server user-info-server; do
    echo "========================="
    echo "$dir에 Gradle Wrapper 추가 중..."
    cd $dir
    gradle wrapper
    cd ..
done