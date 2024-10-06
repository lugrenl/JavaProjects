package fileapi;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class InputOutputEx {
    public static void main(String[] args) throws IOException
    {
        //создаём поток чтения байт из файла
        FileInputStream inputStream = new FileInputStream("src\\main\\resources\\data.txt");
        //создаём поток записи байт в файл
        FileOutputStream outputStream = new FileOutputStream("src\\main\\resources\\result.txt");

        byte[] buffer = new byte[1000];
        while(inputStream.available() > 0) //пока есть ещё непрочитанные байты
        {
            //прочитать очередной блок байт в переменную buffer и реальное количество в count
            int count = inputStream.read(buffer);
            outputStream.write(buffer, 0, count); // записать блок (часть блока) во второй поток

        }

        inputStream.close(); // закрываем оба потока
        outputStream.close();
    }
}
