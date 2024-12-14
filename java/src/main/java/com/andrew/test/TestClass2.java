package com.andrew.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 *
 *
 * @author andrew
 * @since 2023/2/2
 * */
public class TestClass2 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String newspaper = sc.nextLine();

            String letter = sc.nextLine();

            String[] words = newspaper.split(" ");

            // 字符排序
            List<String> sortedWords = doSort(words);

            String[] letterWords = letter.split(" ");

            List<String> sortedLetterWords = doSort(letterWords);

            boolean letterAllMatches = true;
            // 加密信 的所有字符必须被包含
            for (String letterWord : sortedLetterWords) {

                // 不包含指定字符串 则返回 false
                if (!sortedWords.remove(letterWord)) {
                    System.out.println(false);
                    letterAllMatches = false;
                    break;
                }
            }

            if (letterAllMatches) {
                System.out.println(true);
            }
        }


    }

    private static List<String> doSort(String[] words) {
        List<String> result =  new ArrayList<String>();

        for (int i = 0; i < words.length; i++) {
            String currentWord = words[i];

            if (currentWord.length() > 0) {
                char[] chars = currentWord.toCharArray();

                // 字符排序
                for (int j = 0; j < chars.length; j++) {
                    int minAsc = chars[j];
                    int minIndex = j;

                    for (int k = j+1; k < chars.length; k++) {
                        if (chars[k] < minAsc) {
                            minAsc = chars[k];
                            minIndex = k;
                        }
                    }

                    // 比较交换
                    if (minIndex != j) {
                        char ch = chars[j];
                        chars[j] = (char) minAsc;
                        chars[minIndex] = ch;
                    }
                }

                result.add(new String(chars));
            }
        }

        return  result;

    }
}
