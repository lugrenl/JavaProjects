import java.util.ArrayList;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

public class ResultsBoard {

    private final NavigableSet<StudentScore> resultsBoard = new TreeSet<>();

    public void addStudent(String name, Float score) {
        resultsBoard.add(new StudentScore(name, score));
    }

    public List<String> top3 () {
        List<String> result = new ArrayList<>(3);

        if (resultsBoard.isEmpty()) {
            return result;
        }

        StudentScore last = resultsBoard.last();
        result.add(last.name());

        StudentScore secondLast = resultsBoard.lower(last);
        if (secondLast != null) {
            result.add(secondLast.name());
        }

        StudentScore thirdLast = resultsBoard.lower(secondLast);
        if (thirdLast != null) {
            result.add(thirdLast.name());
        }

        return result;
    }

    public void addAllStudents(List<String> names, List<Float> scores) {
        for (int i = 0; i < names.size(); i++) {
            addStudent(names.get(i), scores.get(i));
        }
    }

    public void addAllStudents(List<StudentScore> studentScores) {
        resultsBoard.addAll(studentScores);
    }

    @Override
    public String toString() {
        return "ResultsBoard{" +
                "resultsBoard=" + resultsBoard +
                '}';
    }
}
