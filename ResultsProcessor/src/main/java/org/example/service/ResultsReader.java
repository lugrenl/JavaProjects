package org.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.example.model.Result;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResultsReader {
    @Value("${file.path}")
    private String path;
    private final ResultParser resultParser;

    @Autowired
    public ResultsReader(ResultParser resultParser) {
        this.resultParser = resultParser;
    }

    /**
     * Загрузить результаты из файла.
     * </p>
     * Результаты хранятся в формате: Иван Иванов, М, 10 км, 55:20
     * </p>
     *@return List
     */
    public List<Result> readFromFile() {
        try {
            Path filePath = new ClassPathResource(path).getFile().toPath();
            return Files.lines(filePath)
                    .map(resultParser::parseResult)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
