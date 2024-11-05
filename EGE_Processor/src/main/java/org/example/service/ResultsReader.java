package org.example.service;

import org.example.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResultsReader {
    private final ResultParser resultParser;

    @Autowired
    public ResultsReader(ResultParser resultParser) {
        this.resultParser = resultParser;
    }

    /**
     * Чтение данных из файла.
     * @param filePath
     * @return List
     */
    public List<Result> readFromFile(Path filePath) {
        try {
            return Files.lines(filePath)
                    .map(resultParser::parseResult)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
