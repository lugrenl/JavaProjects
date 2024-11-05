package org.example.service;

import org.example.model.Answer;
import org.example.model.Question;
import org.example.model.Result;
import org.springframework.stereotype.Service;

@Service
public class ResultParser {
    private static final String LINE_SEPARATOR = " - ";

    /**
     * Распарсить строку из файла в {@link Result}.
     * @param line
     * @return Result
     */
    public Result parseResult(String line) {
        String[] resultParts = line.split(LINE_SEPARATOR);
        Question question = Question.fromCode(resultParts[0]);
        Answer answer = Answer.fromCode(resultParts[1]);

        return new Result(question, answer);
    }
}
