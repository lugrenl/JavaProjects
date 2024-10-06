import java.util.Scanner;

/*
    Приложение "Загрузчик файлов":
    Для запускать операции ввести номер операции и нажать ENTER.

    Операция "Сохранить файл":
    Пользователь вводит имя файла, директорию через запятую. Например -> test_file.txt,C:\test
    На следующей строке необходимо ввести текст, который будет записан в файл.
    Программа должна выполнить следующие действия:
    Провести сохранение файла, вернуть размер сохраненного файла и время записи.
    При необходимости перезаписать сохраненный файл.

    Операция "Найти файл":
    Пользователь вводит имя файла и директорию через запятую. Например -> test_file.txt,C:\test
    Программа производит поиск файла и возвращает пользователю содержимое в случае успеха.

    Операция "Удалить файл":
    Пользователь вводит имя файла и директорию через запятую. Например -> test_file.txt,C:\test
    Программа производит удаление файла по указанному пути.
 */

public class Loader {
    public static void main(String[] args) {

        LoaderCommandHandler LOADER_COMMAND_HANDLER = new LoaderCommandHandler();

        while (true) {
            // выводим список вариантов действий
            printMessage();
            // считываем выбранный вариант
            Command command = readCommand();
            if (command.getAction() == Action.EXIT) {
                return;
            } else if (command.getAction() == Action.ERROR) {
                System.out.println("Неизвестное действие");
            } else {
                // выполняем действие
                LOADER_COMMAND_HANDLER.processCommand(command);
            }
        }
    }

    public static void printMessage() {
        System.out.println("--------------------------");
        System.out.println("0. Выход");
        System.out.println("1. Сохранить файл");
        System.out.println("2. Найти файл");
        System.out.println("3. Удалить файл");
        System.out.println("--------------------------");
    }

    private static Command readCommand() {
        Scanner scanner = new Scanner(System.in);
        try {
            String code = scanner.nextLine();
            Integer actionCode = Integer.valueOf(code);
            Action action = Action.fromCode(actionCode);

            if (action.isRequireAdditionalData()) {
                String data = scanner.nextLine();
                if (action.isRequireContent()) {
                    String content = scanner.nextLine();
                    return new Command(action, data, content);
                } else { return new Command(action, data); }
            } else { return new Command(action);}

        } catch (Exception ex) {
            System.out.println("Проблема обработки ввода " + ex);
            return new Command(Action.ERROR);
        }
    }
}
