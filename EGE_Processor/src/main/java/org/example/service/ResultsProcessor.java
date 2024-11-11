package org.example.service;

import org.example.datastore.ScoreMap;
import org.example.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ResultsProcessor {
    @Value("${keys.path}")
    private String keysPath;
    @Value("${answers.path}")
    private String answersPath;
    private final ResultsReader resultsReader;
    private final ScoreMap scoreTable;

    @Autowired
    public ResultsProcessor(ResultsReader resultsReader, ScoreMap scoreTable) {
        this.scoreTable = scoreTable;
        this.resultsReader = resultsReader;
    }

    /**
     * Подсчёт итогового балла.
     * @return int
     */
    public int getScore() {
        int score = 0;
        List<Result> keys = resultsReader.readFromFile(keysPath);
        List<Result> answers = resultsReader.readFromFile(answersPath);

        for (Result key : keys) {
            for (Result answer : answers) {
                if (Objects.equals(key.question(), answer.question())) {
                    if (Objects.equals(key.answer(), answer.answer())) {
                        score += scoreTable.getScore(key.question());
                    } else {
                        score += 0;
                    }
                }
            }
        }
        return score;
    }
}