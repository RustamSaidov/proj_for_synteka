package ru.synteka.utils;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static ru.synteka.utils.FileOperations.getLineListFromFile;

@Data
public class ListOperations {
    public static final List<String> pretextList = getLineListFromFile("data/pretext.txt")
            .stream()
            .map(String::toLowerCase)
            .collect(Collectors.toList());
    public static final String SEPARATOR = ":";

    public static boolean isAnyWordMatchedInTwoLists(List<String> listFromTheLeftLine, List<String> listFromTheRightLine) {
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

    public static List<String> getListFromTheLine(List<String> list, int i) {
        return Arrays.stream(list.get(i).split(" "))
                .filter(word -> !pretextList.contains(word))
                .collect(Collectors.toList());
    }

    public static List<String> separateSublist(BufferedReader in, String line) throws IOException {
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
}
