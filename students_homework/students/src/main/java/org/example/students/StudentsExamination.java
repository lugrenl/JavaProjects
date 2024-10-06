package org.example.students;

import org.example.students.exceptions.ItemNotFoundException;
import java.util.*;


public class StudentsExamination implements Examination {

    private static final int INITIAL_CAPACITY = 256;
    private final Map<NameAndSubject, Integer> examinations = new LinkedHashMap<>(INITIAL_CAPACITY);
    private final LRUCache<String, Double> cache = new LRUCache<>(2);


    @Override
    public void addScore(Score score) {
        NameAndSubject nameAndSubject = new NameAndSubject(score.name(), score.subject());
        examinations.put(nameAndSubject, score.score());
    }

    @Override
    public Score getScore(String name, String subject) throws ItemNotFoundException {
        NameAndSubject nameAndSubject = new NameAndSubject(name, subject);
        if (examinations.containsKey(nameAndSubject)) {
            return new Score(name, subject, examinations.get(nameAndSubject));
        }
        throw new ItemNotFoundException(name + ' ' + subject);
    }

    @Override
    public void putAllItems(List<Score> items) {
        for (Score item : items) {
            addScore(item);
        }
    }

    @Override
    public Score removeItem(String name, String subject) throws ItemNotFoundException {
        NameAndSubject nameAndSubject = new NameAndSubject(name, subject);
        if (examinations.containsKey(nameAndSubject)) {
            return new Score(name, subject, examinations.remove(nameAndSubject));
        }
        throw new ItemNotFoundException(name + ' ' + subject);
    }

    @Override
    public boolean containsItem(Score score) {
        NameAndSubject nameAndSubject = new NameAndSubject(score.name(), score.subject());
        if (examinations.containsKey(nameAndSubject)) {
            return true;
        }
        return false;
    }

    private double getAverageForSubjectCompute(String subject) {
        double average = 0;
        int count = 0;
        for (Map.Entry<NameAndSubject, Integer> entry : examinations.entrySet()) {
            if (entry.getKey().subject().equals(subject)) {
                average += entry.getValue();
                count++;
            }
        }
        if (count == 0) {
            return 0;
        }
        return average / count;
    }

    @Override
    public double getAverageForSubject(String subject) {
        return cache.computeIfAbsent(subject, this::getAverageForSubjectCompute);
    }

    @Override
    public Set<String> multipleSubmissionsStudentNames() {
        Set<String> tempNames = new HashSet<>();
        Set<String> result = new HashSet<>();
        for(Map.Entry<NameAndSubject, Integer> entry : examinations.entrySet()) {
            if (tempNames.contains(entry.getKey().name())) {
                result.add(entry.getKey().name());
            } else {
                tempNames.add(entry.getKey().name());
            }
        }
        return result;
    }

    @Override
    public Set<String> lastFiveStudentsWithExcellentMarkOnAnySubject() {
        Set<String> result = new LinkedHashSet<>();
        List<String> scores = new ArrayList<>();

        if (examinations.isEmpty()) {
            return result;
        }

        for (Map.Entry<NameAndSubject, Integer> entry : examinations.entrySet()) {
            if (entry.getValue() == 5) {
                scores.add(entry.getKey().name());
            }
        }

        Collections.reverse(scores);
        for (String score : scores) {
            result.add(score);
            if (result.size() == 5) {
                break;
            }
        }
        return result;
    }

    @Override
    public Map<NameAndSubject, Integer> getAllItems() {
        return new LinkedHashMap<>(examinations);
    }

    @Override
    public Collection<Score> getAllScores() {
        List<Score> allScores = new ArrayList<>();
        for (Map.Entry<NameAndSubject, Integer> entry : examinations.entrySet()) {
            allScores.add(new Score(entry.getKey().name(), entry.getKey().subject(), entry.getValue()));
        }
        return allScores;
    }
}
