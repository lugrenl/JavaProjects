import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Stable<Horse> stable = new Stable<Horse>();
        Stable<Animal> bigStable = new Stable<Animal>();

        Pony pony = new Pony();
        Mustang mustang = new Mustang();
        Stalion stalion = new Stalion();

        List<Mustang> wildMustangs = new ArrayList<>();
        wildMustangs.add(new Mustang());
        wildMustangs.add(new Mustang());

        stable.addAnimal(pony);
        stable.addAnimal(mustang);
        stable.addAnimal(stalion);

        bigStable.addAnimal(pony);
        bigStable.addAnimal(mustang);
        bigStable.addAnimal(stalion);

        stable.addAllAnimals(wildMustangs);

        System.out.println(stable.getAllAnimals());

        moveStable(stable, bigStable);

        bigStable.addAnimal(pony);
        bigStable.addAnimal(mustang);
        bigStable.addAnimal(stalion);

        bigStable.addAllAnimals(wildMustangs);

        System.out.println(bigStable.getAllAnimals());
    }

    public static <T> void moveStable(Stable<? extends Animal> source, Stable<? super Animal> destination) {
        destination.addAllAnimals(source.getAllAnimals());
    }
}

