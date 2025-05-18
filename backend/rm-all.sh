#!/bin/bash
set -eu

for dir in crawling article quiz strike summary user-info; do
    echo "========================="
    echo "$dir 의 mysql data 삭제."
    sudo rm -rf $dir-mysql-data
    sudo rm -rf $dir-mysql-init
done
