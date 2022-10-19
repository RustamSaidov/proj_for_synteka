package ru.synteka;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class Main {
    public static final List<String> pretextList = getLineListFromFile("data/pretext.txt")
            .stream()
            .map(String::toLowerCase)
            .collect(Collectors.toList());
    public static final String SEPARATOR = ":";

    public List<String> reassembleListFromFile(String file) {
        List<String> firstList = new ArrayList<>();
        List<String> secondList = new ArrayList<>();
        List<String> resultList = new ArrayList<>();
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

        if (firstList.size() == 1 && secondList.size() == 1) {
            resultList.add(firstList.get(0) + SEPARATOR + secondList.get(0));
        } else {
            List<Integer> usedLinesFromSL = new ArrayList<>();
            for (int i = 0; i < firstList.size(); i++) {
                boolean matchedIsFound = false;
                for (int j = 0; j < secondList.size(); j++) {
                    List<String> listFromTheLeftLine = getListFromTheLine(firstList, i);
                    List<String> listFromTheRightLine = getListFromTheLine(secondList, j);

                    boolean match = isAnyWordMatchedInTwoLists(listFromTheLeftLine, listFromTheRightLine);
                    if (match) {
                        resultList.add(firstList.get(i) + SEPARATOR + secondList.get(j));
                        matchedIsFound = true;
                        usedLinesFromSL.add(j);
                    }
                }
                if (!matchedIsFound) {
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
        boolean match = false;
        for (String wordFromLL : listFromTheLeftLine) {
            for (String wordFromRL : listFromTheRightLine) {
                if (wordFromLL.equals(wordFromRL) || findSimilarity(wordFromLL, wordFromRL) > 0.8) {
                    match = true;
                }
            }
        }
        return match;
    }

    private List<String> getListFromTheLine(List<String> list, int i) {
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

    public static double findSimilarity(String x, String y) {
        x = x.toLowerCase(Locale.ROOT);
        y = y.toLowerCase(Locale.ROOT);
        if (x == null && y == null) {
            return 1.0;
        }
        if (x == null || y == null) {
            return 0.0;
        }
        return StringUtils.getJaroWinklerDistance(x, y);
    }

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
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

    public static void saveListToFile(List<String> log, String file) {
        try (PrintWriter out = new PrintWriter(
                new BufferedOutputStream(
                        new FileOutputStream(file)
                ))) {
            for (String s : log) {
                out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        List<String> reassembledList = main.reassembleListFromFile("data/input1.txt");
        saveListToFile(reassembledList, "data/output1.txt");
    }
}
