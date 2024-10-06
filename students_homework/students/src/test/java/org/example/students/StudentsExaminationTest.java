package org.example.students;

import org.example.students.exceptions.ItemNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class StudentsExaminationTest {

    private static StudentsExamination examination;

    @BeforeAll
    static void beforeAll() {
        examination = new StudentsExamination();
    }

    @Test
    void putAndGetScore() throws ItemNotFoundException {
        Score score1 = new Score("Сергей Пугачёв", "Математика", 2);
        Score score2 = new Score("Сергей Пугачёв", "Математика", 5);
        Score score3 = new Score("Сергей Пугачёв", "Математика", 3);
        examination.addScore(score1);
        examination.addScore(score2);
        examination.addScore(score3);

        Score actual = examination.getScore(score3.name(), score3.subject());
        Assertions.assertEquals(score3, actual);
        System.out.println(actual);
    }

    @Test
    void getScore() {
        Assertions.assertThrows(ItemNotFoundException.class, () -> examination.getScore("Сергей Пугачёв", "Физика"));
    }

    @Test
    void getAverageForSubject() {
        Score score1 = new Score("Сергей Пугачёв", "Математика", 5);
        Score score2 = new Score("Дмитрий Кузовлёв", "Физика", 4);
        Score score3 = new Score("Алексей Соломатин", "История", 3);
        Score score4 = new Score("Артём Игнатов", "История", 5);
        Score score5 = new Score("Сергей Песков", "История", 4);
        List<Score> scores = List.of(score1, score2, score3, score4, score5);
        examination.putAllItems(scores);

        double expected = (double) (score3.score() + score4.score() + score5.score()) / 3;

        Assertions.assertEquals(expected, examination.getAverageForSubject("История"));

    }

    @Test
    void callManyTimesForRepeatingRequests() {
        Score score1 = new Score("Сергей Пугачёв", "Математика", 5);
        Score score2 = new Score("Дмитрий Кузовлёв", "Математика", 4);
        Score score3 = new Score("Алексей Соломатин", "Математика", 3);
        Score score4 = new Score("Артём Игнатов", "История", 5);
        Score score5 = new Score("Сергей Песков", "История", 4);
        List<Score> scores = List.of(score1, score2, score3, score4, score5);
        examination.putAllItems(scores);

        examination.getAverageForSubject("Математика");
        examination.getAverageForSubject("Математика");
        examination.getAverageForSubject("Математика");
        //Assertions.assertEquals(2, examination.calls);

        //analytics.getAggregationByCategoryAndPlace(request3);
        //Assertions.assertEquals(3, storage.calls);

        //analytics.getAggregationByCategoryAndPlace(request1);
        //Assertions.assertEquals(3, storage.calls);

        //analytics.getAggregationByCategoryAndPlace(request2);
        //Assertions.assertEquals(4, storage.calls);
    }

    @Test
    void multipleSubmissionsStudentNames() {
        Score score1 = new Score("Сергей Пугачёв", "Математика", 5);
        Score score2 = new Score("Дмитрий Кузовлёв", "Физика", 4);
        Score score3 = new Score("Алексей Соломатин", "История", 3);
        Score score4 = new Score("Артём Игнатов", "История", 5);
        Score score5 = new Score("Сергей Песков", "История", 4);
        Score score6 = new Score("Артём Игнатов", "Физика", 5);
        Score score7 = new Score("Дмитрий Кузовлёв", "История", 4);
        List<Score> scores = List.of(score1, score2, score3, score4, score5, score6, score7);
        examination.putAllItems(scores);

        Set<String> expected = new HashSet<>(List.of(score2.name(), score4.name()));

        Assertions.assertEquals(expected, examination.multipleSubmissionsStudentNames());

    }

    @Test
    void lastFiveStudentsWithExcellentMarkOnAnySubject() {
        Score score1 = new Score("Сергей Пугачёв", "Математика", 5);
        Score score2 = new Score("Дмитрий Кузовлёв", "Физика", 5);
        Score score3 = new Score("Алексей Соломатин", "История", 5);
        Score score4 = new Score("Артём Игнатов", "История", 5);
        Score score5 = new Score("Сергей Песков", "История", 4);
        Score score6 = new Score("Артём Игнатов", "Физика", 5);
        Score score7 = new Score("Дмитрий Кузовлёв", "История", 4);
        Score score8 = new Score("Дмитрий Кузовлёв", "Математика", 5);
        Score score9 = new Score("Сергей Шершнев", "Биология", 4);
        Score score10 = new Score("Дмитрий Яковлев", "Физкультура", 5);
        Score score11 = new Score("Марлон Брэнсон", "Физкультура", 5);
        List<Score> scores = List.of(score1, score2, score3, score4, score5, score6, score7, score8, score9, score10, score11);
        examination.putAllItems(scores);

        Set<String> expected = new LinkedHashSet<>(List.of(score11.name(), score10.name(), score8.name(), score6.name(), score3.name(), score2.name()));
        Set<String> result = examination.lastFiveStudentsWithExcellentMarkOnAnySubject();

        Assertions.assertEquals(5, result.size());
        Assertions.assertEquals(expected, result);
        System.out.println(result);
        System.out.println(expected);
    }

    @Test
    void getAllScores() {
        Score score1 = new Score("Сергей Пугачёв", "Математика", 5);
        Score score2 = new Score("Дмитрий Кузовлёв", "Физика", 4);
        Score score3 = new Score("Алексей Соломатин", "История", 3);
        Score score4 = new Score("Артём Игнатов", "История", 5);
        Score score5 = new Score("Сергей Песков", "История", 4);
        Score score6 = new Score("Артём Игнатов", "Физика", 5);
        Score score7 = new Score("Дмитрий Кузовлёв", "История", 4);
        Score score8 = new Score("Дмитрий Кузовлёв", "История", 5);
        List<Score> scores = List.of(score1, score2, score3, score4, score5, score6, score7, score8);
        examination.putAllItems(scores);

        Collection<Score> result = examination.getAllScores();

        Assertions.assertEquals(7, result.size());
        System.out.println(result);

    }

    @Test
    void putAllItems() {
        Score score1 = new Score("Сергей Пугачёв", "Математика", 5);
        Score score2 = new Score("Дмитрий Кузовлёв", "Физика", 4);
        Score score3 = new Score("Алексей Соломатин", "История", 3);
        List<Score> scores = List.of(score1, score2, score3);
        examination.putAllItems(scores);

        Assertions.assertTrue(examination.containsItem(score1));
        Assertions.assertTrue(examination.containsItem(score2));
        Assertions.assertTrue(examination.containsItem(score3));


    }

    @Test
    void getAllItems() {
        Score score1 = new Score("Сергей Пугачёв", "Математика", 5);
        Score score2 = new Score("Дмитрий Кузовлёв", "Физика", 4);
        Score score3 = new Score("Алексей Соломатин", "История", 3);
        Score score4 = new Score("Артём Игнатов", "История", 5);
        Score score5 = new Score("Сергей Песков", "История", 4);
        Score score6 = new Score("Артём Игнатов", "Физика", 5);
        Score score7 = new Score("Дмитрий Кузовлёв", "История", 4);
        Score score8 = new Score("Дмитрий Кузовлёв", "История", 5);
        List<Score> scores = List.of(score1, score2, score3, score4, score5, score6, score7, score8);
        examination.putAllItems(scores);

        Map<NameAndSubject, Integer> expected = examination.getAllItems();

        Assertions.assertEquals(7, expected.size());
        System.out.println(expected);
    }

    @Test
    void removeItem() throws ItemNotFoundException {
        Score score1 = new Score("Сергей Пугачёв", "Математика", 5);
        Score score2 = new Score("Дмитрий Кузовлёв", "Физика", 4);
        Score score3 = new Score("Алексей Соломатин", "История", 3);

        examination.addScore(score1);
        examination.addScore(score2);
        examination.addScore(score3);

        Score removedItem = examination.removeItem("Сергей Пугачёв", "Математика");

        Assertions.assertFalse(examination.containsItem(score1));
        Assertions.assertTrue(examination.containsItem(score2));
        Assertions.assertTrue(examination.containsItem(score3));
        Assertions.assertEquals(score1, removedItem);

        Assertions.assertThrows(ItemNotFoundException.class, () -> examination.removeItem("Артём Игнатов", "Физика"));

    }
}