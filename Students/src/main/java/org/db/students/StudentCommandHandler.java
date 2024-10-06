package org.db.students;

import java.util.Map;

public class StudentCommandHandler {

    private final StudentStorage studentStorage = new StudentStorage();

    public void processCommand(Command command) {
        Action action = command.getAction();
        switch (action) {
            case CREATE:
                processCreateCommand(command);
                break;
            case UPDATE:
                processUpdateCommand(command);
                break;
            case DELETE:
                processDeleteCommand(command);
                break;
            case STATS_BY_COURSE:
                processStatsByCourse(command);
                break;
            case STATS_BY_CITY:
                processStatsByCity(command);
                break;
            case SEARCH:
                processSearchCommand(command);
                break;
            default:
                System.out.println("Действие " + action + " не поддерживается");
                break;
        }

        System.out.println("Обработка команды. "
                + "Действие " + command.getAction().name() +
                ", данные:  " + command.getData());
    }

    private void processSearchCommand(Command command) {
        String data = command.getData();
        String[] dataArray = data.split(",");

        if (dataArray.length > 2) {
            System.out.println("Неверное количество данных");
            return;
        }

        if (dataArray.length == 0) {
            System.out.println("Вывод всех студентов");
            studentStorage.printAll();
            return;
        }

        // Проверяем, что в фамилии только буквы
        for (String surname : dataArray) {
            if (!surname.matches("\\D+")) {
                System.out.println("В фамилии не должно быть цифр");
                return;
            }
        }

        try {
        if (dataArray.length == 1) {
            String surname = dataArray[0].trim();
            System.out.println("Вывод всех студентов по фамилии " + surname);
            studentStorage.searchBySurname(surname);

        } else if (!dataArray[0].isBlank() && !dataArray[1].isBlank()) {
            String surname1 = dataArray[0].trim();
            String surname2 = dataArray[1].trim();
            System.out.println("Вывод всех студентов в диапазоне фамилий от " + surname1 + " до " + surname2);
            studentStorage.searchBySurnames(surname1, surname2);

        } else if (dataArray[0].isBlank() && !dataArray[1].isBlank()) {
            String surname2 = dataArray[1].trim();
            System.out.println("Вывод всех студентов с начала списка до фамилии " + surname2);
            studentStorage.searchToSurname(surname2);

        } else if (!dataArray[0].isBlank() && dataArray[1].isBlank()) {
            String surname1 = dataArray[0].trim();
            System.out.println("Вывод всех студентов с фамилии " + surname1 + " и до конца списка");
            studentStorage.searchFromSurname(surname1);

        } else {
            System.out.println("Неверный формат ввода данных");
        }

        } catch (IllegalArgumentException e) {
            System.out.println("Неверный формат ввода данных");
        }
    }

    private void processStatsByCourse(Command command) {
        Map<String, Long> data = studentStorage.getCountByCourse();
        studentStorage.printMap(data);
    }

    private void processStatsByCity(Command command) {
        Map<String, Long> data = studentStorage.getCountByCity();
        studentStorage.printMap(data);
    }

    public void processCreateCommand(Command command) {
        String data = command.getData();
        String[] dataArray = data.split(",");

        if (dataArray.length != 5) {
            System.out.println("Неверное количество данных");
            return;
        }

        // уберём пробелы в начале и в конце строки, которые могут быть оставлены по ошибке
        for (int i = 0; i < dataArray.length; i++)
            dataArray[i] = dataArray[i].trim();

        if (dataArray[0].isBlank() || dataArray[1].isBlank() || dataArray[2].isBlank()
                || dataArray[3].isBlank() || dataArray[4].isBlank()) {
            System.out.println("Не все данные заполнены");
            return;
        }

        if (!dataArray[4].matches("\\d+")) {
            System.out.println("Возраст должен быть числом");
            return;
        }

        if ((Integer.parseInt(dataArray[4]) < 5) || (Integer.parseInt(dataArray[4]) > 100)) {
            System.out.println("Возраст должен быть в рамках от 5 до 100 лет");
            return;
        }

        if (!dataArray[0].matches("\\D+") || !dataArray[1].matches("\\D+") || !dataArray[3].matches("\\D+")) {
            System.out.println("В имени, фамилии или названии города не должно быть цифр");
            return;
        }

        Student student = new Student();
        student.setSurname(dataArray[0]);
        student.setName(dataArray[1]);
        student.setCourse(dataArray[2]);
        student.setCity(dataArray[3]);
        student.setAge(Integer.valueOf(dataArray[4]));

        studentStorage.createStudent(student);
        studentStorage.printAll();
    }

    public void processUpdateCommand(Command command) {
        String data = command.getData();
        String[] dataArray = data.split(",");

        if (dataArray.length != 6) {
            System.out.println("Неверное количество данных");
            return;
        }

        // уберём пробелы в начале и в конце строки, которые могут быть оставлены по ошибке
        for (int i = 0; i < dataArray.length; i++)
            dataArray[i] = dataArray[i].trim();

        if (dataArray[0].isBlank() || dataArray[1].isBlank() || dataArray[2].isBlank() || dataArray[3].isBlank()
                || dataArray[4].isBlank() || dataArray[5].isBlank()) {
            System.out.println("Не все данные заполнены");
            return;
        }

        Long id;
        try {
            id = Long.valueOf(dataArray[0]);
        } catch (NumberFormatException ex) {
            System.out.println("Не удалось распознать идентификатор студента");
            return;
        }

        if (!dataArray[5].matches("\\d+")) {
            System.out.println("Возраст должен быть числом");
            return;
        }

        if ((Integer.parseInt(dataArray[5]) < 5) || (Integer.parseInt(dataArray[5]) > 100)) {
            System.out.println("Возраст должен быть в рамках от 5 до 100 лет");
            return;
        }

        if (!dataArray[1].matches("\\D+") || !dataArray[2].matches("\\D+") || !dataArray[4].matches("\\D+")) {
            System.out.println("В имени, фамилии или названии города не должно быть цифр");
            return;
        }

        Student student = new Student();
        student.setSurname(dataArray[1]);
        student.setName(dataArray[2]);
        student.setCourse(dataArray[3]);
        student.setCity(dataArray[4]);
        student.setAge(Integer.valueOf(dataArray[5]));

        studentStorage.updateStudent(id, student);
        studentStorage.printAll();
    }

    public void processDeleteCommand(Command command) {
        String data = command.getData();
        Long id;
        try {
            id = Long.valueOf(data);
        } catch (NumberFormatException ex) {
            System.out.println("Не удалось распознать идентификатор студента");
            return;
        }
        studentStorage.deleteStudent(id);
        studentStorage.printAll();
    }
}
