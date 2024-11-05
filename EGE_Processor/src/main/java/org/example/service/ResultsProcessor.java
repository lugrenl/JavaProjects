package org.example.service;

import org.example.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.NoneNestedConditions;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
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


    public List<Result> getFastest() {
        return Collections.emptyList();
    }
}
