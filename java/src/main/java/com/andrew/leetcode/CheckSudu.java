package com.andrew.leetcode;

import java.util.HashMap;
import java.util.Map;

/*
 * 数独检测
 *
 * @author andrew
 * @since 2023/2/1
 * */
public class CheckSudu {

    public static void main(String[] args) {
        char[][] board = new char[9][9];
        /*
        * '5','3','.','.','7','.','.','.','.'
        * '6','.','.','1','9','5','.','.','.'
        * '.','9','8','.','.','.','.','6','.'
        * '8','.','.','.','6','.','.','.','3'
        * '4','.','.','8','.','3','.','.','1'
        * '7','.','.','.','2','.','.','.','6'
        * '.','6','.','.','.','.','2','8','.'
        * '.','.','.','4','1','9','.','.','5'
        * '.','.','.','.','8','.','.','7','9'
        * 
        * */
        board[0] = new char[]{'5','3','.','.','7','.','.','.','.'};
        board[1] = new char[]{'6','.','.','1','9','5','.','.','.'};
        board[2] = new char[]{'.','9','8','.','.','.','.','6','.'};
        board[3] = new char[]{'8','.','.','.','6','.','.','.','3'};
        board[4] = new char[]{'4','.','.','8','.','3','.','.','1'};
        board[5] = new char[]{'7','.','.','.','2','.','.','.','6'};
        board[6] = new char[]{'.','6','.','.','.','.','2','8','.'};
        board[7] = new char[]{'.','.','.','4','1','9','.','.','5'};
        board[8] = new char[]{'.','.','.','.','8','.','.','7','9'};

        System.out.println(new CheckSudu().isValidSudoku(board));
    }

    public boolean isValidSudoku(char[][] board) {
        // 检查每行是否有重复数字
        for (int i = 0; i < board.length; i++) {
            char[] currentRow = board[i];


            Map<String , Integer> map = new HashMap<>();

            for (char c : currentRow) {
                if (c != '.' && map.get(c +"") != null) {
                    return false;
                }
                map.put(c+"" , 1);
            }
        }

        // 检查每列是否存在重复字符
        for (int i = 0; i < 9; i++) {
            char[] currentColumn = new char[9];

            for (int j = 0; j < 9; j++) {
                currentColumn[j] = board[j][i];
            }

            Map<String , Integer> map = new HashMap<>();

            for (char c : currentColumn) {
                if (c != '.' && map.get(c +"") != null) {
                    return false;
                }
                map.put(c+"" , 1);
            }
        }

        // 检查九宫格是否存在重复字符

        int startX = 0;
        int startY = 0;

        for (int i = 0; i < 3; i++) {

            for (int j = 0; j < 3; j++) {

                char[] currentSquare = new char[9];
                int index =0;

                int pointX= startX;
                int pointY= startY;
                for (int m = 0; m < 3; m++) {

                    for (int n = 0; n < 3; n++) {

                        currentSquare[index++] = board[pointX][pointY];

                        pointY++;
                    }
                    pointY = startY;

                    pointX++;
                }

                // 判断九宫格是否存在重复数字

                Map<String , Integer> map = new HashMap<>();

                for (char c : currentSquare) {
                    if (c != '.' && map.get(c +"") != null) {
                        return false;
                    }
                    map.put(c+"" , 1);
                }

                startY+=3;
            }

            startY = 0;
            startX+=3;
        }

        return true;
    }
}
