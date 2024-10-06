import java.util.ArrayList;
import java.util.List;

public class Stable<T> {
    private final List<T> stable = new ArrayList<T>();

    public T addAnimal(T t) {
        stable.add(t);
        return t;
    }

    public List<T> getAllAnimals() {
        return stable;
    }

    public void addAllAnimals(List<? extends T> list) {
        stable.addAll(list);
    }

}
