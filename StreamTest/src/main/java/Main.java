package org.example;

import java.util.List;
import java.util.Optional;


public class Main {
    public static void main(String[] args) {

        List<Integer> intListSorted = List.of(1, 1, 2, 3, 4, 4, 5, 5, 6, 7, 8, 9, 9, 10);
        List<Integer> intList = List.of(1, 5, 8, 7, 4, 2, 3, 6, 9, 10);
        List<String> stringList = List.of("Андрей", "Дмитрий", "Алексей", "Александр", "Максим", "Сергей", "Антон");

        // Удаление всех повторяющихся элементов из списка
        List<Integer> uniqueIntList = intListSorted.stream().distinct().toList();
        System.out.println("Список уникальных элементов " + uniqueIntList);

        //Подсчет количества строк в списке, которые начинаются с определенной буквы
        long countA = stringList.stream().filter(s -> s.startsWith("А")).count();
        System.out.println("Количество слов в списке начинающихся с буквы 'А' = " + countA);

        //Поиск второго по величине элемента в списке целых чисел используя оператор findFirst
        intList.stream()
                .sorted()
                .skip(1)
                .findFirst()
                .ifPresent(integer -> System.out.println("Второй по величине элемент = " + integer));
        // или
        Optional<Integer> secondMax = intList.stream()
                .sorted()
                .skip(1)
                .findFirst();
        secondMax.ifPresent(integer -> System.out.println("Второй по величине элемент = " + integer));

    }
}