package ru.synteka.utils;

import lombok.Data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ru.synteka.utils.ListOperations.*;

@Data
public class ListFromFile {

    public List<String> reassemble(String file) {
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
}
