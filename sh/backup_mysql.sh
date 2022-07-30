#!/usr/bin/env bash
 #mysqldump --all-databases > /home/za_backups/mysql/backup$(date "+%Y%m%d").sql && chown -R za_backups /home/za_backups/mysql/


 #rm -rf /home/za_backups/gitlab/ && docker commit gitlab-14-0-12 gitlab-backup:latest && docker save gitlab-backup:latest>/home/soft/gitlab/backup/gitlab-backup$(date "+%Y%m%d").tar

# home directory
#gitlab_backup_home=/home/za_backups/gitlab
gitlab_backup_home=/home/zhian/backups/gitlab

# 最大保存备份文件数量 3 个
max_reserved_file_count=3

# 当前已备份文件数量
current_file_count=$(find ${gitlab_backup_home} -type f | wc -l)


# 删除多余的备份文件
if [ $current_file_count -gt $max_reserved_file_count ]; then
    echo 'too many file exists,try to delete extra file !'
fi

echo "$current_file_count"