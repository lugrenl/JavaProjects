package org.db.students;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class StudentStorage {

    private final Map<Long, Student> studentStorageMap = new HashMap<>();
    private StudentSurnameStorage studentSurnameStorage = new StudentSurnameStorage();
    private Long currentId = 0L;

    /**
     * Создание данных о студенте
     * @param student данные о студенте
     * @return сгенерированный уникальный идентификатор студента
     */
    public Long createStudent(Student student) {
        Long nextId = getNextId();
        studentStorageMap.put(nextId, student);
        studentSurnameStorage.studentCreated(nextId, student.getSurname());
        return nextId;
    }

    /**
     * Обновление данных о студенте
     * @param id идентификатор студента
     * @param student данные студента
     * @return true если данные были обновлены, false если студент не был найден
     */
    public boolean updateStudent(Long id, Student student) {
        if (!studentStorageMap.containsKey(id)) {
            return false;
        } else {
            studentStorageMap.put(id, student);
            String oldSurname = studentStorageMap.get(id).getSurname();
            String newSurname = student.getSurname();
            studentSurnameStorage.studentUpdated(id, oldSurname, newSurname);
            return true;
        }
    }

    /**
     * Удаляет данные о студенте
     * @param id идентификатор студента
     * @return true если студент был удалён, false если студент не был найден по идентификатору
     */
    public boolean deleteStudent(Long id) {
        Student removed = studentStorageMap.remove(id);
        if (removed != null) {
            String surname = removed.getSurname();
            studentSurnameStorage.studentDeleted(id, surname);
        }
        return removed != null;
    }

    public Long getNextId() {
        currentId = currentId + 1;
        return currentId;
    }

    public void printAll() {
        System.out.println(studentStorageMap);
    }

    public void printMap(Map<String, Long> data) {
        data.entrySet().forEach(e -> System.out.println(e.getKey() + " " + e.getValue()));
    }

    public Map<String, Long> getCountByCourse() {
        Map<String, Long> res = studentStorageMap.values().stream()
                .collect(Collectors.toMap(
                        student -> student.getCourse(),
                        student -> 1L,
                        (count1, count2) -> count1 + count2
                ));
        return res;
    }

    public Map<String, Long> getCountByCity() {
        Map<String, Long> res = studentStorageMap.values().stream()
                .collect(Collectors.toMap(
                        student -> student.getCity(),
                        student -> 1L,
                        (count1, count2) -> count1 + count2
                ));
        return res;
    }

    public void searchBySurname(String surname) {
        Set<Long> students = studentSurnameStorage.getStudentsBySurname(surname);
        for (Long studentId : students) {
            Student student = studentStorageMap.get(studentId);
            System.out.println(student);
        }
    }

    public void searchBySurnames(String surname1, String surname2) {
        Set<Long> students = studentSurnameStorage.getStudentsBySurnamesFromTo(surname1, surname2);
        for (Long studentId : students) {
            Student student = studentStorageMap.get(studentId);
            System.out.println(student);
        }
    }

    public void searchFromSurname(String surname) {
        Set<Long> students = studentSurnameStorage.getStudentsBySurnamesGreaterOrEqualThan(surname);
        for (Long studentId : students) {
            Student student = studentStorageMap.get(studentId);
            System.out.println(student);
        }
    }

    public void searchToSurname(String surname) {
        Set<Long> students = studentSurnameStorage.getStudentsBySurnamesLessOrEqualThan(surname);
        for (Long studentId : students) {
            Student student = studentStorageMap.get(studentId);
            System.out.println(student);
        }
    }
}
