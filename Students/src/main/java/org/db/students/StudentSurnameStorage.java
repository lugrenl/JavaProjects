package org.db.students;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class StudentSurnameStorage {

    private TreeMap<String, Set<Long>> surnamesTreeMap = new TreeMap<>();

    public void studentCreated(Long id, String surname) {
        Set <Long> existingIds = surnamesTreeMap.getOrDefault(surname, new HashSet<>());
        existingIds.add(id);
        surnamesTreeMap.put(surname, existingIds);
    }

    public void studentDeleted(Long id, String surname) {
        surnamesTreeMap.get(surname).remove(id);
        if (surnamesTreeMap.get(surname).isEmpty()) {
            surnamesTreeMap.remove(surname);
        }
    }

    public void studentUpdated(Long id, String oldSurname, String newSurname) {
        studentDeleted(id, oldSurname);
        studentCreated(id, newSurname);
    }

    /**
     * Данный метод возвращает уникальные идентификаторы студентов,
     * чьи фамилии меньше или равны переданной.
     * @param surname
     * @return set
     */
    public Set<Long> getStudentsBySurnamesLessOrEqualThan(String surname) {
        Set <Long> res = surnamesTreeMap.headMap(surname, true)
                .values()
                .stream()
                .flatMap(longs -> longs.stream())
                .collect(Collectors.toSet());

        return res;
    }

    /**
     * Данный метод возвращает уникальные идентификаторы студентов,
     * чьи фамилии больше или равны переданной.
     * @param surname
     * @return set
     */
    public Set<Long> getStudentsBySurnamesGreaterOrEqualThan(String surname) {
        Set <Long> res = surnamesTreeMap.tailMap(surname, true)
                .values()
                .stream()
                .flatMap(longs -> longs.stream())
                .collect(Collectors.toSet());

        return res;
    }

    /**
     * Данный метод возвращает уникальные идентификаторы студентов,
     * чьи фамилии находятся в диапазоне переданных.
     * @param surname1 Фамилия студента с которого начинается поиск, включительно
     * @param surname2 Фамилия студента на котором заканчивается поиск, включительно
     * @return set
     */
    public Set<Long> getStudentsBySurnamesFromTo(String surname1, String surname2) {
        Set <Long> res = surnamesTreeMap.subMap(surname1, true, surname2, true)
                .values()
                .stream()
                .flatMap(longs -> longs.stream())
                .collect(Collectors.toSet());

        return res;
    }

    /**
     * Данный метод возвращает уникальные идентификаторы студентов,
     * чьи фамилии совпадают с переданной.
     * @param surname
     * @return set
     */
    public Set<Long> getStudentsBySurname(String surname) {
        if (!surnamesTreeMap.containsKey(surname)) {
            return new HashSet<>();
        }
        Set <Long> res = new HashSet<>(surnamesTreeMap.get(surname));
        return res;
    }
}
