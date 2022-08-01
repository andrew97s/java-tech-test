#!/usr/bin/env bash

backup_start_time=$[$(date +%s%N)/1000000]

backup_home=/home/za_backups/gitlab

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
# 1 提交当前docker镜像文件
# 2. 保存镜像文件
# 3. 删除备份产生的镜像
# 4. 备份文件夹授权给备份账号
docker commit gitlab-14-0-12 gitlab-backup:latest \
&& docker save gitlab-backup:latest>"/home/za_backups/gitlab/gitlab-backup$(date "+%Y%m%d").tar" \
&& docker rmi gitlab-backup:latest \
&& chown -R za_backups $backup_home

backup_end_time=$[$(date +%s%N)/1000000]

echo "backup finished in $((backup_end_time - backup_start_time)) ms"