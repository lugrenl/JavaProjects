package org.example.service;

import org.example.datastore.ScoreMap;
import org.example.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
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
     * Собирает данные из файла и возвращает их в виде списка <Result>.
     * @param path
     * @throws IOException
     * @return List
     */
    private List<Result> readData(String path) throws IOException {
        Path filePathToKeys = new ClassPathResource(path).getFile().toPath();
        return resultsReader.readFromFile(filePathToKeys);
    }

    /**
     * Подсчёт итогового балла.
     * @return int
     */
    public int getScore() {
        int score = 0;
        List<Result> keys;
        List<Result> answers;

        try {
            keys = readData(keysPath);
            answers = readData(answersPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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