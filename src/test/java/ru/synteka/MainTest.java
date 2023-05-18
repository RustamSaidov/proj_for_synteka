package ru.synteka;

import org.junit.Test;
import ru.synteka.utils.ListFromFile;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static ru.synteka.utils.FileOperations.getLineListFromFile;
import static ru.synteka.utils.FileOperations.saveListToFile;

public class MainTest {

    @Test
    public void testMain1() {
        List<String> expect = List.of("гвоздь:?",
                "шуруп:шуруп 3х1.5",
                "краска синяя:краска",
                "ведро для воды:корыто для воды");
        saveListToFile(new ListFromFile().reassemble("data/input1.txt"), "data/output1.txt");
        List<String> result = getLineListFromFile("data/output1.txt");
        assertEquals(expect, result);
    }

    @Test
    public void testMain2() {
        List<String> expect = List.of("Бетон с присадкой:Цемент");
        saveListToFile(new ListFromFile().reassemble("data/input2.txt"), "data/output2.txt");
        List<String> result = getLineListFromFile("data/output2.txt");
        assertEquals(expect, result);
    }

    @Test
    public void testMain3() {
        List<String> expect = List.of("Бетон с присадкой:присадка для бетона", "доставка:?");
        saveListToFile(new ListFromFile().reassemble("data/input3.txt"), "data/output3.txt");
        List<String> result = getLineListFromFile("data/output3.txt");
        assertEquals(expect, result);
    }
}