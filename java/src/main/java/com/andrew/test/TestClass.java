package com.andrew.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * test
 *
 * @author andrew
 * @since 2023/2/2
 * */
public class TestClass {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);


        while (sc.hasNextLine()) {
            String str = sc.nextLine();

            // 初始化参数
            List<Integer> processors = new ArrayList<>();
            List<Integer> processorsG1 = new ArrayList<>();
            List<Integer> processorsG2 = new ArrayList<>();

            for (int i = 0; i < str.length(); i++) {
                char ch = str.charAt(i);

                if (ch >= 48 && ch <= 57) {
                    int processIndex = Integer.parseInt(ch + "");

                    if (processIndex > 3) {
                        processorsG2.add(processIndex);
                    } else {
                        processorsG1.add(processIndex);
                    }

                    processors.add(Integer.valueOf(ch + ""));
                }
            }

            // [[0],[1]]

            int applyCount = Integer.parseInt(sc.nextLine());
            int sizeG1 = processorsG1.size();
            int sizeG2 = processorsG2.size();

            if (applyCount > sizeG1 && applyCount > sizeG2) {
                System.out.println(doCombo(null, 1));
            }
            else if (applyCount == 1) {

                String result1 = "";
                String result2 = "";


                if (sizeG1 == sizeG2) {
                    if (sizeG1 == 1) {
                        result1 = doCombo(processorsG1 , 1);
                    }else if (sizeG1 == 3) {
                        result1 = doCombo(processorsG1 , 1);
                    }  else if (sizeG1 == 2) {
                        result1 =doCombo(processorsG1,1);
                    }  else if (sizeG1 == 4) {
                        result1 = doCombo(processorsG1,1);
                    }

                    if (sizeG2 == 1) {
                        result2 =doCombo(processorsG2 , 1);
                    }else if (sizeG2 == 3) {
                        result2 =doCombo(processorsG2 , 1);
                    }else if (sizeG2 == 2) {
                        result2 =doCombo(processorsG2,1);
                    }else if (sizeG2 == 4) {
                        result2 =doCombo(processorsG2,1);
                    }

                    if (!result1.equals("[]") && !result2.equals("[]")) {
                        System.out.println(result1.substring(0, result1.length()-1)+ ", "+ result2.substring(1));
                    }
                    else if (result1.equals("[]") && !result2.equals("[]")) {
                        System.out.println(result2);
                    }
                    else if (!result1.equals("[]") && result2.equals("[]")) {
                        System.out.println(result1);
                    }
                    else if (result1.equals("[]") && result2.equals("[]")) {
                        System.out.println("[]");
                    }
                } else {
                    String result = "";
                    if (sizeG1 == 1) {
                        result = doCombo(processorsG1 , 1);
                    }
                    else if (sizeG2 == 1) {
                        result =doCombo(processorsG2 , 1);
                    }
                    else if (sizeG1 == 3) {
                        result = doCombo(processorsG1 , 1);
                    }
                    else if (sizeG2 == 3) {
                        result =doCombo(processorsG2 , 1);
                    }

                    else if (sizeG1 == 2) {
                        result =doCombo(processorsG1,1);
                    }
                    else if (sizeG2 == 2) {
                        result =doCombo(processorsG2,1);
                    }
                    else if (sizeG1 == 4) {
                        result = doCombo(processorsG1,1);
                    }
                    else if (sizeG2 == 4) {
                        result =doCombo(processorsG2,1);
                    }

                    System.out.println(result);
                }


            }
            // 相同链路 2 > 4 > 3
            else if (applyCount == 2) {
                if (sizeG1 == sizeG2) {
                    String result1 ="[]";
                    String result2 ="[]";
                    if (sizeG1 == 2) {
                        result1 =doCombo(processorsG1,2);
                    } else if (sizeG1 == 4) {
                        result1 =doCombo(processorsG1,2);
                    }  else if (sizeG1 == 3) {
                        result1 =doCombo(processorsG1,2);
                    }

                    if (sizeG2 == 2) {
                        result2 =doCombo(processorsG2,2);
                    }
                    else if (sizeG2 == 4) {
                        result2 =doCombo(processorsG2,2);
                    }
                    else if (sizeG2 == 3) {
                        result2 =doCombo(processorsG2,2);
                    }

                    if (!result1.equals("[]") && !result2.equals("[]")) {
                        System.out.println(result1.substring(0, result1.length()-1) + ", "+ result2.substring(1));
                    }
                    else if (result1.equals("[]") && !result2.equals("[]")) {
                        System.out.println(result2);
                    }
                    else if (!result1.equals("[]") && result2.equals("[]")) {
                        System.out.println(result1);
                    }
                    else if (result1.equals("[]") && result2.equals("[]")) {
                        System.out.println("[]");
                    }
                } else {
                    String result = "";
                    if (sizeG1 == 2) {
                        result =doCombo(processorsG1,2);
                    }
                    if (sizeG2 == 2) {
                        result =doCombo(processorsG2,2);
                    }
                    else if (sizeG1 == 4) {
                        result =doCombo(processorsG1,2);
                    }
                    else if (sizeG2 == 4) {
                        result =doCombo(processorsG2,2);
                    }
                    else if (sizeG1 == 3) {
                        result =doCombo(processorsG1,2);
                    }
                    else if (sizeG2 == 3) {
                        result =doCombo(processorsG2,2);
                    }

                    System.out.println(result);

                }


            } else if (applyCount == 4) {
                if (processorsG1.size() == 4) {
                    System.out.println(doCombo(processorsG1, 4));
                } else if (processorsG2.size() == 4) {
                    System.out.println(doCombo(processorsG2, 4));
                }
            } else if (applyCount == 8) {
                doCombo(processors,8);
            }
        }


    }

    private static String doCombo(List<Integer> processors, int split) {
        if (processors == null || processors.size() == 0) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder("[");

        if (split == 1) {

            if (processors.size() == 1) {
                sb.append("[" + processors.get(0) + "]");
            } else if (processors.size() == 2) {
                sb.append("[" + processors.get(0) + "]");
                sb.append(", [" + processors.get(1) + "]");
            } else if (processors.size() == 3) {
                sb.append("[" + processors.get(0) + "]");
                sb.append(", [" + processors.get(1) + "]");
                sb.append(", [" + processors.get(2) + "]");
            } else if (processors.size() == 4) {
                sb.append("[" + processors.get(0) + "]");
                sb.append(", [" + processors.get(1) + "]");
                sb.append(", [" + processors.get(2) + "]");
                sb.append(", [" + processors.get(3) + "]");
            }
        }
        else if (split == 2) {
            if (processors.size() == 2) {
                sb.append("[");
                for (int i = 0; i < processors.size(); i++) {
                    if (i == 0) {
                        sb.append(processors.get(i));
                    } else {
                        sb.append(", " + processors.get(i));
                    }
                }
                sb.append("]");
            }
            if (processors.size() == 3) {
                sb.append("[" + processors.get(0) + ", " + processors.get(1) + "]");
                sb.append(", [" + processors.get(0) + ", " + processors.get(2) + "]");
                sb.append(", [" + processors.get(1) + ", " + processors.get(2) + "]");
            }
            if (processors.size() == 4) {
                sb.append("[" + processors.get(0) + ", " + processors.get(1) + "]");
                sb.append(", [" + processors.get(0) + ", " + processors.get(2) + "]");
                sb.append(", [" + processors.get(1) + ", " + processors.get(2) + "]");
                sb.append(", [" + processors.get(1) + ", " + processors.get(3) + "]");
                sb.append(", [" + processors.get(2) + ", " + processors.get(3) + "]");
            }

        }
        else {
            for (int i = 0; i < processors.size(); i++) {
                if (i == 0) {
                    sb.append("[" + processors.get(i));
                } else {
                    sb.append(", " + processors.get(i));
                }
            }
            sb.append("]");
        }

        sb.append("]");

        return sb.toString();
    }
}
