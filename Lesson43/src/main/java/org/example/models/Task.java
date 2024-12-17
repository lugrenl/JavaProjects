package org.example.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Getter
@Setter
public class Task {
    private Integer id = UUID.randomUUID().hashCode();
    private String title;
    private Boolean finished;
    private LocalDateTime createdDate = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);

    public Task(String title, Boolean finished) {
        this.title = title;
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", finished=" + finished +
                ", createdDate=" + createdDate +
                '}';
    }
}
