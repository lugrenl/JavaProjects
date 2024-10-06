import java.util.Objects;

public final class StudentScore implements Comparable<StudentScore> {
    private final String name;
    private final Float score;

    public StudentScore(String name, Float score) {
        this.name = name;
        this.score = score;
    }

    public String name() {
        return name;
    }

    public Float score() {
        return score;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (StudentScore) obj;
        return Objects.equals(this.name, that.name) &&
                Objects.equals(this.score, that.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, score);
    }

    @Override
    public String toString() {
        return "StudentScore[" +
                "name=" + name + ", " +
                "score=" + score + ']';
    }

    @Override
    public int compareTo(StudentScore o) {
        if (Objects.equals(this.score, o.score)) {
            return this.name.compareTo(o.name);
        }
        return this.score.compareTo(o.score);
    }
}
