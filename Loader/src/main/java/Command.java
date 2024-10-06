public class Command {

    private final Action action;
    private String data;
    private String content;

    public Command(Action action) {
        this.action = action;
    }

    public Command(Action action, String data) {
        this.action = action;
        this.data = data;
    }

    public Command(Action action, String data, String content) {
        this.action = action;
        this.data = data;
        this.content = content;
    }

    public Action getAction() {
        return action;
    }

    public String getData() {
        return data;
    }

    public String getContent() {
        return content;
    }
}
