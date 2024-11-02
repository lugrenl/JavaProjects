package org.example.service;

import org.example.model.Distance;
import org.example.model.Gender;
import org.example.model.Person;
import org.example.model.Result;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class ResultParser {
    private static final String LINE_SEPARATOR = ",";
    private static final String TIME_SEPARATOR = ":";

    /**
     * Распарсить строку из файла в {@link Result}.
     * </p>
     * Результаты хранятся в формате: Иван Иванов, М, 10 км, 55:20
     * </p>
     *@param line
     *@return Result
     */
    public Result parseResult(String line) {
        String[] resultParts = line.split(LINE_SEPARATOR);
        String name = resultParts[0];
        Gender gender = Gender.of(resultParts[1]);
        Distance distance = Distance.of(resultParts[2]);
        Duration time = parseTime(resultParts[3]);

        Person person = new Person(name, gender);

        return new Result(person, distance, time);

    }

    /**
     * Распарсить строку MM:SS в {@link Duration}.
     * </p>
     * Предполагаем, что все спортсмены уложились в один час в целях упрощения парсинга.
     </p>
     *@param time
     @return Duration
     */
    private Duration parseTime(String time) {
        String[] timeParts = time.split(TIME_SEPARATOR);
        // Переводим в секунды
        int totalSeconds = Integer.parseInt(timeParts[0]) * 60 + Integer.parseInt(timeParts[1]);
        return Duration.ofSeconds(totalSeconds);
    }
}
