package org.example.datastore;

import org.example.model.Question;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class ScoreMap {
    private final Map<Question, Integer> scoreTable;

    public ScoreMap(@Value("#{${scoreTable}}") Map<Question, Integer> scoreTable) {
        this.scoreTable = scoreTable;
    }

    public int getScore(Question question) {
        return scoreTable.getOrDefault(question, 0);
    }
}
