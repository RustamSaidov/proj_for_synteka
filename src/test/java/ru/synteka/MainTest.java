package ru.synteka;

import org.junit.Test;
import org.w3c.dom.ls.LSOutput;

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
        Main.save(main.filter("data/input1.txt"),"data/output1.txt");
        List<String> result = Main.getLineListFromFile("data/output1.txt");
        assertEquals(expect, result);
    }

    @Test
    public void testMain2() {
        Main main = new Main();
        List<String> expect = List.of("����� � ���������:������");
        Main.save(main.filter("data/input2.txt"),"data/output2.txt");
        List<String> result = Main.getLineListFromFile("data/output2.txt");
        assertEquals(expect, result);
    }

    @Test
    public void testMain3() {
        Main main = new Main();
        List<String> expect = List.of("����� � ���������:�������� ��� ������", "��������:?");
        Main.save(main.filter("data/input3.txt"),"data/output3.txt");
        List<String> result = Main.getLineListFromFile("data/output3.txt");
        result.forEach(System.out::println);
        assertTrue(expect.equals(result));
    }
}