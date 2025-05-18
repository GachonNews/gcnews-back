@echo off
rem ===========================================
rem  각 데이터 디렉터리 삭제
rem ===========================================

set DATA_DIRS=crawling-mysql-data strike-mysql-data user-info-mysql-data article-mysql-data summary-mysql-data quiz-mysql-data

for %%D in (%DATA_DIRS%) do (
    echo =========================
    echo %%D 삭제 중...
    rmdir /s /q "%%D"
)

echo =========================
echo 모든 데이터 폴더 삭제가 종료되었습니다.
pause
