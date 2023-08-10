package com.andrew.leetcode.meduim;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 54-螺旋矩阵
 * <br/>
 * 原题见<a href="https://leetcode.cn/problems/spiral-matrix/">leetcode_54</a>
 *
 * @author tongwenjin
 * @since 2023/8/9
 */
public class Leetcode54 {

    public static void main(String[] args) {
        int[][] matrix = new int[3][4];
        matrix[0] = new int[]{1, 2, 3, 4};
        matrix[1] = new int[]{5, 6, 7, 8};
        matrix[2] = new int[]{9, 10,11, 12};

        List<Integer> integers = new Leetcode54().spiralOrder(matrix);

        System.out.println(integers);
    }

    public List<Integer> spiralOrder(int[][] matrix) {

        List<Integer> result = new ArrayList<>();

        int maxX = matrix.length;
        int maxY = matrix[0].length;
        final int NULL = 101;

        // right->down->left->top
        String rotation = "right";
        int x = 0, y = 0;

        result.add(matrix[x][y]);
        matrix[x][y] = NULL;

        while (true) {
            if (result.size() == matrix.length * matrix[0].length) {
                break;
            }
            switch (rotation) {
                case "right": {
                    // 尝试右移
                    if (y + 1 < maxY && matrix[x][y+1] != NULL) {
                        result.add(matrix[x][++y]);
                        matrix[x][y] = NULL;
                    } else {
                        rotation = "down";
                    }
                    continue;
                }
                case "down": {
                    // 尝试下移
                    if (x + 1 < maxX && matrix[x+1][y] != NULL) {
                        result.add(matrix[++x][y]);
                        matrix[x][y] = NULL;
                    } else {
                        rotation = "left";
                    }
                    continue;
                }
                case "left": {
                    // 尝试左移
                    if (y - 1 >= 0 && matrix[x][y-1] != NULL) {
                        result.add(matrix[x][--y]);
                        matrix[x][y] = NULL;
                    } else {
                        rotation = "top";
                    }
                    continue;
                }
                case "top": {
                    // 尝试上移
                    if (x - 1 >= 0 && matrix[x-1][y] != NULL) {
                        result.add(matrix[--x][y]);
                        matrix[x][y] = NULL;
                    } else {
                        rotation = "right";
                    }
                    continue;
                }
            }


        }

        return result;
    }
}
