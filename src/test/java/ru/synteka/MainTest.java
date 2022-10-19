package ru.synteka;

import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class MainTest {

    @Test
    public void testMain1() {
        Main main = new Main();
        List<String> expect = List.of("������:?",
                "�����:����� 3�1.5",
                "������ �����:������",
                "����� ��� ����:������ ��� ����");
        Main.saveListToFile(main.reassembleListFromFile("data/input1.txt"), "data/output1.txt");
        List<String> result = Main.getLineListFromFile("data/output1.txt");
        assertEquals(expect, result);
    }

    @Test
    public void testMain2() {
        Main main = new Main();
        List<String> expect = List.of("����� � ���������:������");
        Main.saveListToFile(main.reassembleListFromFile("data/input2.txt"), "data/output2.txt");
        List<String> result = Main.getLineListFromFile("data/output2.txt");
        assertEquals(expect, result);
    }

    @Test
    public void testMain3() {
        Main main = new Main();
        List<String> expect = List.of("����� � ���������:�������� ��� ������", "��������:?");
        Main.saveListToFile(main.reassembleListFromFile("data/input3.txt"), "data/output3.txt");
        List<String> result = Main.getLineListFromFile("data/output3.txt");
        result.forEach(System.out::println);
        assertEquals(expect, result);
    }
}