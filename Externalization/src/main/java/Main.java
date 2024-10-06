import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        //externalization
        externalizator();

        //deexternalization
        deexternalizator();

    }

    public static void externalizator() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\lugre\\Desktop\\save.ser");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        UserInfo userInfo = new UserInfo("Ivan", "Ivanov", "Ivan Ivanov's passport data");

        objectOutputStream.writeObject(userInfo);

        objectOutputStream.close();
    }

    public static void deexternalizator() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\lugre\\Desktop\\save.ser");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);


        UserInfo userInfo = (UserInfo) objectInputStream.readObject();
        System.out.println(userInfo);

        objectInputStream.close();
    }
}
