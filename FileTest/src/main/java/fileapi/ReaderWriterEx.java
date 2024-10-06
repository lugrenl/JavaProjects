package fileapi;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Reader и Writer - то аналоги InputStream и OutputStream,
 * но в отличие от них, они работают не с байтами, а с символами.
 * Иногда их ешё называют символьными потоками, в противовес InputStream и OutputStream - байтовым потокам.
 */

public class ReaderWriterEx {
    public static void main(String[] args) throws IOException {
        //создаём список для хранения строк
        List<String> list = new ArrayList<>();

        //открываем файл
        File file = new File("src\\main\\resources\\document.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));

        //пока файл не пуст - читаем из него
        while (reader.ready()) {
            list.add(reader.readLine());
        }
        //закрываем файл
        reader.close();


        // ИЛИ
        File file2 = new File("src\\main\\resources\\document.txt");

        List<String> list2 = Files.readAllLines(file2.toPath(), Charset.defaultCharset());

        System.out.println(list2.size());

        //записываем в файл
        File file3 = new File("src\\main\\resources\\result.txt");
        Files.write(file3.toPath(), list2, Charset.defaultCharset());

//        FileWriter writer = new FileWriter(file3); //true - дописать в файл
//        for (String line : list) {
//            writer.write(line + "\n");
//        }
//        writer.close();
    }
}

