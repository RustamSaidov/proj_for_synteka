package ru.synteka;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class Main {
    public static final List<String> pretextList = getLineListFromFile("data/pretext.txt")
            .stream()
            .map(String::toLowerCase)
            .collect(Collectors.toList());
    ;
    public static final String SEPARATOR = ":";

    public static void main(String[] args) {
        Main main = new Main();
        List<String> log = main.filter("data/input1.txt");
        lineBylineOutput(log);
        save(log, "data/output1.txt");
    }


    public List<String> filter(String file) {
        List<String> firstList = new ArrayList<>();
        List<String> secondList = new ArrayList<>();
        List<String> resultList = new ArrayList<>();
        List<String> leftList = new ArrayList<>();
        List<String> rightList = new ArrayList<>();
        boolean flag = false;
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            for (String line = in.readLine(); line != null; line = in.readLine()) {
                if (isNumeric(line) && !flag) {
                    flag = true;
                    firstList = separateSublist(in, line);
                } else if (isNumeric(line) && flag) {
                    secondList = separateSublist(in, line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


//        if (firstList.size() > secondList.size()) {
//            leftList = firstList;
//            rightList = secondList;
//        } else {
//            leftList = secondList;
//            rightList = firstList;
//        }
//
//        if (leftList.size() == 1 && rightList.size() == 1) {
//            resultList.add(leftList.get(0) + SEPARATOR + rightList.get(0));
//        } else
//            for (int i = 0; i < leftList.size(); i++) {
//                boolean matchedIsFinded = false;
//                String sumLine = "";
//                Double indexOfSimilarity = 0.0;
//                for (int j = 0; j < rightList.size(); j++) {
//                    List<String> listFromTheLeftLine = getlistFromTheLine(leftList, i);
//                    List<String> listFromTheRightLine = getlistFromTheLine(rightList, j);
//
//                    boolean match = isAnyWordMatchedInTwoLists(listFromTheLeftLine, listFromTheRightLine);
//                    double similarity = findSimilarity(leftList.get(i), rightList.get(j));
//                    if (match || similarity>0.5) {
//                        resultList.add(leftList.get(i) + SEPARATOR + rightList.get(j));
//                        matchedIsFinded = true;
//                    }
//                }
//                if(!matchedIsFinded){
//                    resultList.add(leftList.get(i) + SEPARATOR + "?");
//                }
//            }
//        return resultList;


        if (firstList.size() == 1 && secondList.size() == 1) {
            resultList.add(firstList.get(0) + SEPARATOR + secondList.get(0));
        } else {
            List<Integer> usedLinesFromSL = new ArrayList<>();
            for (int i = 0; i < firstList.size(); i++) {
                boolean matchedIsFinded = false;
                for (int j = 0; j < secondList.size(); j++) {
                    List<String> listFromTheLeftLine = getlistFromTheLine(firstList, i);
                    List<String> listFromTheRightLine = getlistFromTheLine(secondList, j);

                    boolean match = isAnyWordMatchedInTwoLists(listFromTheLeftLine, listFromTheRightLine);
                    double indexOfSimilarity = findSimilarity(firstList.get(i), secondList.get(j));
                    if (match || indexOfSimilarity > 0.8) {
                        resultList.add(firstList.get(i) + SEPARATOR + secondList.get(j));
                        matchedIsFinded = true;
                        usedLinesFromSL.add(j);
                    }
                }
                if (!matchedIsFinded) {
                    resultList.add(firstList.get(i) + SEPARATOR + "?");
                }
            }
            for (int s = 0; s < secondList.size(); s++) {
                if (!usedLinesFromSL.contains(s)) {
                    resultList.add(secondList.get(s) + SEPARATOR + "?");
                }
            }
        }
        return resultList;
    }


    private boolean isAnyWordMatchedInTwoLists(List<String> listFromTheLeftLine, List<String> listFromTheRightLine) {
        Boolean match = false;
        for (String wordFromLL : listFromTheLeftLine) {
            for (String wordFromRL : listFromTheRightLine) {
                if (wordFromLL.equals(wordFromRL)) {
                    match = true;
                }
            }
        }
        return match;
    }

    private List<String> getlistFromTheLine(List<String> list, int i) {
        return Arrays.stream(list.get(i).split(" "))
                .filter(word -> !pretextList.contains(word))
                .collect(Collectors.toList());
    }

    private List<String> separateSublist(BufferedReader in, String line) throws IOException {
        int nOfStr = Integer.parseInt(line);
        List<String> sublist = new ArrayList<>();
        for (int i = 0; i < nOfStr; i++) {
            line = in.readLine();
            sublist.add(line);
        }
        return sublist;
    }

    public static void lineBylineOutput(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    public static double findSimilarity(String x, String y) {
        if (x == null && y == null) {
            return 1.0;
        }
        if (x == null || y == null) {
            return 0.0;
        }
        return StringUtils.getJaroWinklerDistance(x, y);
    }

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    public static List<String> getLineListFromFile(String file) {
        List<String> lineList = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            for (String line = in.readLine(); line != null; line = in.readLine()) {
                lineList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineList;
    }

    public static void save(List<String> log, String file) {
        try (PrintWriter out = new PrintWriter(
                new BufferedOutputStream(
                        new FileOutputStream(file)
                ))) {
            for (int i = 0; i < log.size(); i++) {
                out.println(log.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
