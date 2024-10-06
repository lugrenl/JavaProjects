package org.db.students;

public class Command {

    private final Action action;
    private String data;

    public Command(Action action) {
        this.action = action;
    }

    public Command(Action action, String data) {
        this.action = action;
        this.data = data;
    }

    public Action getAction() {
        return action;
    }

    public String getData() {
        return data;
    }
}
