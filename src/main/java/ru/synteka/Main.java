package ru.synteka;

import ru.synteka.utils.ListFromFile;

import java.util.List;
import java.util.Scanner;

import static ru.synteka.utils.FileOperations.saveListToFile;

public class Main {
    public static void main(String[] args) {
        System.out.println("Enter full path to file input.txt:");
        Scanner sc = new Scanner(System.in);
        String path = sc.nextLine();
        List<String> reassembledList = new ListFromFile().reassemble(path);
        saveListToFile(reassembledList, "data/output1.txt");
    }
}
