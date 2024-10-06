import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ClassTrue {
    static void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);
        Field modifierField = Field.class.getDeclaredField("modifiers");
        modifierField.setAccessible(true);

        /**
         * Предполагая, что SecurityManager не мешает вам сделать это, вы можете использовать setAccessible,
         * чтобы обойти private и
         *
         * сбросить модификатор, чтобы избавиться от final,
         * и фактически изменить частное статическое поле final.
         *
         * Примитивные логические значения true и false в main автоматически упаковываются в
         * ссылочный тип Boolean «константы» Boolean.TRUE и Boolean.FALSE
         *
         * Рефлексия используется для изменения общедоступного статического final Boolean.
         *
         * field.getModifiers () & ~ Modifier.FINAL
         *
         * отключает бит, соответствующий Modifier.FINAL из field.getModifiers().
         * & - это поразрядное "И", а ~ - поразрядное дополнение.
         */

        modifierField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, newValue);
    }

    public static void main(String[] args) throws Exception {
        setFinalStatic(Boolean.class.getField("FALSE"), true);


        System.out.format("ВСЕ у нас %s", 2==5);

        System.out.println();

        System.out.format("ВСЕ у нас %s", 5==5);

    }
}
