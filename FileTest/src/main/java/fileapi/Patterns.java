package fileapi;

import java.io.*;

public class Patterns {
    public static void main(String[] args) throws IOException {

        // передаём объект InputStream в конструктор InputStreamReader
        // в качестве InputStream мы используем уже ставшую привычной переменную System.in
        // мы передаём объект "адаптируемого" класса "внутрь", то есть в конструктор класса адаптера
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);

        /*
         * Есть класс OutputStream, который умеет записывать только байты; есть абстрактный класс Writer, который умеет работать с символами,
         * и есть два несовместимых интерфейса.
         *
         * Эту проблему вновь успешно решает паттерн Адаптер
         * При помощи класса OutputStreamWriter мы легко "адаптируем" два интерфейса классов
         * Writer и OutputStreamWriter друг другу. И, получив байтовый поток OutputStream в конструктор,
         * с помощью OutputStreamWriter мы, тем не менее, можем записывать символы, а не байты
         */
        OutputStreamWriter streamWriter = new OutputStreamWriter(new FileOutputStream("src\\main\\resources\\newFile.txt"));
        streamWriter.write(32144);
        streamWriter.close();

        /*
        * InputStream - абстрактный компонент.
        * Конкретные компоненты: FileInputStream, StringBufferInputStream, ByteArrayInputStream, ObjectInputStream и др.
        *
        * Абстрактный декоратор - FileInputStream, его потомки - конкретные декораторы:
        *
        * BufferedInputStream - буферизирует ввод для повышения производительности
        * и дополняет интерфейс новым методом readline() для построчного чтения символьных данных.
        *
        * LineNumberInputStream - добавляет возможность подсчёта строк в процессе чтения данных.
        */
    }
}
