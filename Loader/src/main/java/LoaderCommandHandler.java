import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;

public class LoaderCommandHandler {

    public void processCommand(Command command) {
        Action action = command.getAction();

        switch (action) {
            case SAVE:
                processSaveCommand(command);
                break;
            case FIND:
                processFindCommand(command);
                break;
            case DELETE:
                processDeleteCommand(command);
                break;
            default:
                System.out.println("Действие " + action + " не поддерживается");
                break;
        }

        System.out.println("Обработка команды. "
                + "Действие " + command.getAction().name() +
                ", данные:  " + command.getData());
    }

    private void processSaveCommand(Command command) {
        File newFile = getFile(command);
        if (newFile == null) {
            System.out.println("Некорректные входные данные");
            return;
        }

        if (command.getContent() == null) {
            System.out.println("Не поступил текст на сохранение");
            return;
        }

        String content = command.getContent();

        File directory = new File(newFile.getParent());
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (created) {
                System.out.println("Директория " + newFile.getParent() + " создана");
            } else {
                System.out.println("Директория " + newFile.getParent() + " не создана");
                return;
            }
        }

        if (newFile.exists()) {
            System.out.println("Файл " + newFile.getName() + " уже существует и будет перезаписан");
        } else {
            try {
                boolean created = newFile.createNewFile();
                if (created) {
                    System.out.println("Файл " + newFile.getName() + " создан");
                } else {
                    System.out.println("Файл " + newFile.getName() + " не создан");
                    return;
                }
            } catch (IOException e) {
                System.out.println("Ошибка создания файла");
                return;
            }
        }

        try(FileWriter writer = new FileWriter(newFile)) {
            writer.write(content);
            System.out.println("Файл " + newFile.getName() + " записан");
            System.out.println("Размер сохранённого файла: " + content.length());

            String myDate = dateFormatter(newFile.lastModified());
            System.out.println("Время записи файла: " + newFile + "\t" + myDate);
        } catch (IOException e) {
            System.out.println("Ошибка записи данных в файл ");
        }
    }

    private void processFindCommand(Command command) {
        File newFile = getFile(command);
        if (newFile == null) {
            System.out.println("Некорректные входные данные");
            return;
        }

        if (newFile.exists()) {
            try {
                List<String> content = Files.readAllLines(newFile.toPath(), Charset.defaultCharset());
                System.out.println("Содержимое искомого файла: ");
                System.out.println(content);
            } catch (IOException e) {
                System.out.println("Ошибка чтения данных из файла ");
            }
        } else {
            System.out.println("По указанному пути поиска файл не найден");
        }
    }

    private void processDeleteCommand(Command command) {
        File newFile = getFile(command);
        if (newFile == null) {
            System.out.println("Некорректные входные данные");
            return;
        }

        if (newFile.exists()) {
            boolean deleted = newFile.delete();
            if (deleted) {
                System.out.println("Файл " + newFile.getName() + " удален");
            } else {
                System.out.println("Файл " + newFile.getName() + " не удален");
            }
        } else {
            System.out.println("По указанному пути файл для удаления не найден");
        }
    }

    private String dateFormatter(long time) {
        Date date = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    private File getFile(Command command) {
        if(command.getData() == null) { return null; }

        String data = command.getData();
        String[] dataArray = data.split(",");

        if (dataArray.length != 2) { return null; }

        File newFile = new File(dataArray[1], dataArray[0]);
        System.out.println("Путь к искомому файлу: " + newFile.getAbsolutePath());

        return newFile;
    }
}
