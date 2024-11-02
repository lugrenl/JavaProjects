package org.example.model;

import java.time.Duration;

public record Result(Person person, Distance distance, Duration time) {

    public boolean hasGender(Gender gender) {
        return person.gender() == gender;
    }

    public boolean hasDistance(Distance distance) {
        return this.distance == distance;
    }
}
