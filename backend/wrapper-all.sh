#!/bin/bash
set -eu  # 에러나면 바로 중단, 미정의 변수 에러

for dir in api-gateway eureka-server crawling-server article-server quiz-server strike-server summary-server user-info-server; do
    echo "========================="
    echo "$dir 의 Gradle Wrapper를 초기화하고 다시 생성합니다..."

    cd "$dir"

    # 기존 wrapper 파일/디렉터리 완전 삭제
    rm -f gradlew gradlew.bat
    rm -rf gradle/wrapper

    # 시스템 gradle로 새 wrapper 생성 (여기서 gradle이 PATH에 있어야 함)
    gradle wrapper --gradle-version 8.7

    # (원하면 gradlew에 실행권한 추가)
    chmod +x gradlew

    cd ..
done
