import java.util.Objects;
import java.util.stream.Stream;

/**
 * Представляет собой вариант, выбранный пользователем
 */

public enum Action {

    EXIT(0, false, false),
    SAVE(1, true, true),
    FIND(2, true, false),
    DELETE(3, true, false),
    ERROR(-1, false, false);

    private final Integer code;
    private final boolean requireAdditionalData;
    private final boolean requireContent;

    Action(Integer code, boolean requireAdditionalData, boolean requireContent) {
        this.code = code;
        this.requireAdditionalData = requireAdditionalData;
        this.requireContent = requireContent;
    }

    public static Action fromCode(Integer code) {
        return Stream.of(Action.values())
                .filter(action -> Objects.equals(action.getCode(), code))
                .findFirst()
                .orElseGet(() -> {
                    System.out.println("Неизвестное действие: " + code);
                    return Action.ERROR;
                });
    }

    public Integer getCode() {
        return code;
    }

    public boolean isRequireAdditionalData() {
        return requireAdditionalData;
    }

    public boolean isRequireContent() {
        return requireContent;
    }
}
