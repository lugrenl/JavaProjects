package org.example.service;

import org.example.model.Distance;
import org.example.model.Gender;
import org.example.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResultsProcessor {
    private final ResultsReader resultsReader;

    @Autowired
    public ResultsProcessor(ResultsReader resultsReader) {
        this.resultsReader = resultsReader;
    }

    /**
     * Позволяет определить N самых быстрых мужчин или женщин на дистанции 5 или 10 км.
     * </p>
     * Пример запроса - найти 3 самых быстрых женщин на дистанции 10 км.
     * </p>
     *@param gender
     *@param distance
     *@param limit
     *@return List
     */
    public List<Result> getFastest(Gender gender, Distance distance, int limit) {
        List<Result> results = resultsReader.readFromFile();
        return results.stream()
                .filter(result -> result.hasGender(gender) && result.hasDistance(distance))
                .sorted(Comparator.comparing(Result::time))
                .limit(limit)
                .collect(Collectors.toList());
    }
}
