package ru.synteka;

import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class MainTest {

    @Test
    public void testMain1() {
        Main main = new Main();
        List<String> expect = List.of("гвоздь:?",
                "шуруп:шуруп 3х1.5",
                "краска синяя:краска",
                "ведро для воды:корыто для воды");
        Main.saveListToFile(main.reassembleListFromFile("data/input1.txt"), "data/output1.txt");
        List<String> result = Main.getLineListFromFile("data/output1.txt");
        assertEquals(expect, result);
    }

    @Test
    public void testMain2() {
        Main main = new Main();
        List<String> expect = List.of("Бетон с присадкой:Цемент");
        Main.saveListToFile(main.reassembleListFromFile("data/input2.txt"), "data/output2.txt");
        List<String> result = Main.getLineListFromFile("data/output2.txt");
        assertEquals(expect, result);
    }

    @Test
    public void testMain3() {
        Main main = new Main();
        List<String> expect = List.of("Бетон с присадкой:присадка для бетона", "доставка:?");
        Main.saveListToFile(main.reassembleListFromFile("data/input3.txt"), "data/output3.txt");
        List<String> result = Main.getLineListFromFile("data/output3.txt");
        result.forEach(System.out::println);
        assertEquals(expect, result);
    }
}