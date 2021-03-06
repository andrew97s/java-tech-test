#!/usr/bin/env bash

backup_start_time=$[$(date +%s%N)/1000000]

# home directory
backup_home=/home/za_backups/mysql

# 最大保存备份文件数量 3 个
max_reserved_file_count=3

# 当前已备份文件数量
current_file_count=$(find ${backup_home} -type f | wc -l)

# 删除多余的备份文件
if [ "$current_file_count" -gt $max_reserved_file_count ]; then

    echo 'too many file exists,try to delete extra file !'

    find $backup_home -maxdepth 1 -mindepth 1 -type f | while read backup_file; do
      if [[ $(find "$backup_file" -mtime +100 -print) ]]; then
        echo "deleting $backup_file"
        rm -rf "$backup_file"
      fi
    done
fi

# 执行备份脚本
# 1 mysql 全量数据库dump (todo 全量备份适用于小数据量 快速备份的场景 ， 后续采用增量备份)
# 2. 备份文件夹授权给备份账号

mysqldump --all-databases > "$backup_home/mysql$(date "+%Y%m%d").sql" \
&& chown -R za_backups $backup_home

backup_end_time=$[$(date +%s%N)/1000000]

echo "backup finished in $((backup_end_time - backup_start_time)) ms"