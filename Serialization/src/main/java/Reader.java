import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Reader {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream =
                new FileInputStream("/tmp/car.ser");

        ObjectInputStream objectInputStream =
                new ObjectInputStream(fileInputStream);

        Car car = (Car) objectInputStream.readObject();

        System.out.println(car.getName() + "\n" +
                car.getYear() + "\n" +
                car.getEngine().getHp());

    }
}
