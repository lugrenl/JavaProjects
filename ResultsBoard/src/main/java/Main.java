import java.util.List;

public class Main {

    public static void main(String[] args) {

        ResultsBoard resultsBoard = new ResultsBoard();

        StudentScore studentScore = new StudentScore("Иван", 3.5f);
        StudentScore studentScore2 = new StudentScore("Сергей", 5.0f);
        StudentScore studentScore3 = new StudentScore("Марьяна", 4.1f);
        StudentScore studentScore4 = new StudentScore("Анна", 3.5f);
        StudentScore studentScore5 = new StudentScore("Александр", 2.8f);
        StudentScore studentScore6 = new StudentScore("Алексей", 3.8f);
        StudentScore studentScore7 = new StudentScore("Александр", 4.8f);
        StudentScore studentScore8 = new StudentScore("Сергей", 3.9f);
        StudentScore studentScore9 = new StudentScore("Марьяна", 4.5f);
        StudentScore studentScore10 = new StudentScore("Дмитрий", 4.5f);
        StudentScore studentScore11 = new StudentScore("Антон", 4.4f);

        List<StudentScore> students = List.of(studentScore, studentScore2, studentScore3, studentScore4, studentScore5,
                studentScore6, studentScore7, studentScore8, studentScore9, studentScore10, studentScore11);

        resultsBoard.addAllStudents(students);
        List<String> top3 = resultsBoard.top3();

        System.out.println(resultsBoard);
        System.out.println(top3);
    }
}
