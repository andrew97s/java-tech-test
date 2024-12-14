package com.andrew.leetcode;

import java.util.ArrayList;
import java.util.List;

/*
 * 整数转罗马数字
 *
 * @author andrew
 * @since 2023/2/1
 * */
public class TransferToRoman {

    public static void main(String[] args) {
        System.out.println(new TransferToRoman().intToRoman(3));
    }

    public String intToRoman(int num) {
        String numberStr = num + "";

        StringBuilder sb = new StringBuilder(numberStr);
        char[] reverseChars = sb.reverse().toString().toCharArray();

        int bit = 1;

        /*
        *   I             1
            V             5
            X             10
            L             50
            C             100
            D             500
            M             1000
        * */
        List<String> combo = new ArrayList<>();
        for (int i = 0; i < reverseChars.length; i++) {
            int intValue = Integer.parseInt(reverseChars[i] + "");

            //
            if (intValue != 0) {

                if (bit == 1) {
                    switch (intValue) {
                        case 1 : combo.add("I");break;
                        case 2 : combo.add("II");break;
                        case 3 : combo.add("III");break;
                        case 4 : combo.add("IV");break;
                        case 5 : combo.add("V");break;
                        case 6 : combo.add("VI");break;
                        case 7 : combo.add("VII");break;
                        case 8 : combo.add("VIII");break;
                        case 9 : combo.add("IX");break;
                        default:;
                    }
                } else if (bit == 2) {
                    switch (intValue) {
                        case 1 : combo.add("X");break;
                        case 2 : combo.add("XX");break;
                        case 3 : combo.add("XXX");break;
                        case 4 : combo.add("XL");break;
                        case 5 : combo.add("L");break;
                        case 6 : combo.add("LX");break;
                        case 7 : combo.add("LXX");break;
                        case 8 : combo.add("LXXX");break;
                        case 9 : combo.add("XC");break;
                        default:;
                    }
                }
                else if (bit == 3) {
                    switch (intValue) {
                        case 1 : combo.add("C");break;
                        case 2 : combo.add("CC");break;
                        case 3 : combo.add("CCC");break;
                        case 4 : combo.add("CD");break;
                        case 5 : combo.add("D");break;
                        case 6 : combo.add("DC");break;
                        case 7 : combo.add("DCC");break;
                        case 8 : combo.add("DCCC");break;
                        case 9 : combo.add("CM");break;
                        default:;
                    }
                }
                else if (bit == 4) {
                    switch (intValue) {
                        case 1 : combo.add("M");break;
                        case 2 : combo.add("MM");break;
                        case 3 : combo.add("MMM");break;
                        default:;
                    }
                }

            }

            bit++;
        }

        StringBuilder result = new StringBuilder();
        for (int i = combo.size() -1; i > -1; i--) {
            result.append(combo.get(i));
        }

        return result.toString();
    }
}
