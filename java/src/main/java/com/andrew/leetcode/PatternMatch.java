package com.andrew.leetcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * 字符串匹配
 *
 * @author andrew
 * @since 2023/2/1
 * */
public class PatternMatch {

    public static void main(String[] args) {
        System.out.println((int)'a');
        System.out.println((int)'z');
    }



    public boolean isMatch(String source, String pattern) {
        if (source.equals(pattern)) {
            return true;
        } else {

            char[] patternChars = pattern.toCharArray();

            List<Joker> jokers = new ArrayList<>();

            for (int i = 0; i < patternChars.length; i++) {
                char ch = patternChars[i];
                if(ch == '.') {
                    if(i < patternChars.length-1 && patternChars[i+1] == '*') {
                        jokers.add(new Joker(2 , i));
                    } else {
                        jokers.add(new Joker(0, i));
                    }
                } else if (ch == '*') {
                    if (i > 0 && patternChars[i - 1] != '.' ) {
                        jokers.add(new Joker(1 , i));
                    }
                }
            }

            return false;
        }
    }

    private boolean doCombo(boolean[] marks ,List<Joker> jokers , Set<String> comboPattern , String source) {

        for (String s : comboPattern) {
            if (s.equals(source)) {
                return true;
            }
        }

        for (int i = 0; i < marks.length; i++) {
            if (!marks[i]) {

                marks[i] = true;
                Joker joker = jokers.get(i);
                // 任意字符
                if (joker.type == 0) {

                    HashSet<String> newComboPatterns = new HashSet<>(comboPattern.size()*26);

                    for (String pattern : comboPattern) {
                        char[] chars = pattern.toCharArray();

                        for (int j = 97; j < 123 ; j++) {
                            chars[joker.startIndex] = (char) j;
                            String newPattern = new String(chars);

                            if (newPattern.equals(source)) {
                                return true;
                            }
                            newComboPatterns.add(newPattern);
                        }
                    }

                    comboPattern = newComboPatterns;
                }
                // 可变长度字符
                else if (joker.type == 1) {
                    HashSet<String> newComboPatterns = new HashSet<>();

                    for (String pattern : comboPattern) {
                        char[] chars = pattern.toCharArray();

                        while (true) {

                        }
                    }

                    comboPattern = newComboPatterns;
                }
                // 可变长度任意字符
                else if (joker.type == 2) {

                }

            }
        }

        return false;
    }

    public class Joker {

        // 0 代表； 普通 . （任意字符）
        // 1 代表； 普通 * （可变数量字符字符）
        // 2 代表；  .* （可变数量任意字符字符）
        public int type ;

        public int startIndex;

        public Joker(int type , int startIndex ) {
            this.type = type;
            this.startIndex = startIndex;
        }

    }
}
