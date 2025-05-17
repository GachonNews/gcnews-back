#!/bin/bash
set -eu  # 에러나면 바로 중단, 미정의 변수 에러

for dir in api-gateway eureka-server crawling-server article-server quiz-server strike-server summary-server user-info-server; do
    echo "========================="
    echo "$dir 에 Gradle Wrapper 추가 중..."

    cd "$dir"

    # gradlew가 없으면 시스템에 설치된 gradle 사용 (gradlew가 있으면 실행)
    if [ -f "./gradlew" ]; then
        chmod +x ./gradlew
        ./gradlew wrapper
    else
        gradle wrapper
    fi

    cd ..
done
