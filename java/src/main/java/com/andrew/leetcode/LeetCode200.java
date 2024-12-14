package com.andrew.leetcode;

import java.util.ArrayList;
import java.util.List;

/*
 * 岛屿问题 - leetcode 200
 *
 * 主要通过DFS 算法 深度优先搜索算法实现
 *
 * @author andrew
 * @since 2023/1/31
 * */
public class LeetCode200 {

    public int numIslands(char[][] grid) {


        int isLandCount = 0;
        for (int i = 0; i < grid.length; i++) {

            for (int j = 0; j < grid[0].length; j++) {

                Position position = new Position(i, j);

                if (grid[i][j] == '1') {
                    ++isLandCount;
                    checkPosition(grid, position);
                }
            }
        }

        return isLandCount;
    }

    public static void checkPosition(char[][] grid, Position position) {


        // 判断点位是否存在已知岛屿
        grid[position.x][position.y] = '0';

        // 向上移动
        if (position.y > 0 && grid[position.x][position.y - 1] == '1') {
            checkPosition(grid, new Position(position.x, position.y - 1));
        }

        // 向下移动
        if (position.y + 1 < grid[0].length && grid[position.x][position.y + 1] == '1') {
            checkPosition(grid, new Position(position.x, position.y + 1));
        }

        // 向左移动
        if (position.x > 0 && grid[position.x - 1][position.y] == '1') {
            checkPosition(grid, new Position(position.x - 1, position.y));
        }

        // 向右移动
        if (position.x + 1 < grid.length && grid[position.x + 1][position.y] == '1') {
            checkPosition(grid, new Position(position.x + 1, position.y));
        }
    }

    public static class Island {
        public List<Position> positionList = new ArrayList<>();
    }

    public static class Position {
        public int x;
        public int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
