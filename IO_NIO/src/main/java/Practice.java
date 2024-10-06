import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class Practice {
    public static void main(String[] args) {
        final String fileName = "test.txt";
        write(fileName);
        read(fileName);
    }

    private static void write(String fileName) {
        Path filePath = Paths.get(fileName);

        // создание файла и получение канала для записи
        try (FileChannel fileChannel = FileChannel.open(filePath,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE)) {

            ByteBuffer buffer = ByteBuffer.allocate(1024);

            while (true) {
                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine();
                // String input = System.console().readLine();
                if (input.isEmpty()) {
                    break;
                }
                buffer.clear();
                buffer.put(input.getBytes());
                buffer.flip();
                while (buffer.hasRemaining()) {
                    fileChannel.write(buffer);
                }
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void read(String fileName) {
        Path filePath = Paths.get(fileName);
        try (FileChannel fileChannel = FileChannel.open(filePath, StandardOpenOption.READ)) {
            long fileSize = fileChannel.size(); // получение размера файла
            ByteBuffer buffer = ByteBuffer.allocate((int) fileSize); // создание буфера для чтения
            while (fileChannel.read(buffer) > 0) {
                buffer.flip(); // перевод буфера в режим чтения
                fileChannel.read(buffer); // считывание данных
                buffer.clear();
                // преобразование байтов в строку и вывод на консоль
                System.out.println(STR."The result is \{new String(buffer.array())}");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}