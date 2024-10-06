import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Напишите класс, который бы на основе рефлексии создавал новые экземпляры DZ, обращался бы к их методам
 * getSumInteger и getSumFromList
 * и выводил результаты в консоль
 */

public class DZ_Solution {

    private int i;
    private String s;
    private List<Double> list = new ArrayList<>();

    private DZ_Solution() {
    }

    public DZ_Solution(Integer i, String s) {
        this.i = i;
        this.s = s;
    }

    public DZ_Solution(Integer i, String s, List<Double> list) {
        this.i = i;
        this.s = s;
        this.list = list;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public List<Double> getList() {
        return list;
    }

    public void setList(List<Double> list) {
        this.list = list;
    }

    private int getSumInteger(DZ_Solution dz1, DZ_Solution dz2) {
        return dz1.getI() + dz2.getI();
    }

    private List<Double> getSumFromList(DZ_Solution dz1, DZ_Solution dz2) {
        return Stream.concat(dz1.getList().stream(), dz2.getList().stream()).collect(Collectors.toList());
    }

}

class ReflectionDZ {
    public static void main(String[] args) throws NoSuchMethodException, ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {

        Constructor constructor = DZ_Solution.class.getConstructor(Integer.class, String.class); // DZ_Solution(int i, String s)

        DZ_Solution builder = (DZ_Solution) constructor.newInstance(1, "string");
        DZ_Solution builder2 = (DZ_Solution) constructor.newInstance(2, "string");

        // просто получение информации

        Constructor[] constructors = DZ_Solution.class.getConstructors();
        Field[] fields = DZ_Solution.class.getFields();
        Method[] methods = DZ_Solution.class.getDeclaredMethods();

        // просто получение информации


        /**
         * Доступ к закрытым методам осуществляется с помощью методов Class.getDeclaredMethod(String name, Class[] parameterTypes) или Class.getDeclaredMethods().
         */


        Method getSumInteger = DZ_Solution.class.getDeclaredMethod("getSumInteger", new Class[]{DZ_Solution.class, DZ_Solution.class});
        getSumInteger.setAccessible(true);
        int sumInteger = (Integer) getSumInteger.invoke(builder, builder, builder2);
        System.out.println(sumInteger);


        Constructor constructor2 = DZ_Solution.class.getConstructor(Integer.class, String.class, List.class); // DZ_Solution(Integer i, String s, List<Double> list)

        DZ_Solution builder3 = (DZ_Solution) constructor2.newInstance(1, "string", Arrays.asList(1.2, 45.6, 33, 9));
        DZ_Solution builder4 = (DZ_Solution) constructor2.newInstance(1, "string", Arrays.asList(1.3, 45.7, 34, 10));
        DZ_Solution builder5 = (DZ_Solution) constructor2.newInstance(1, "string", Arrays.asList(1.4, 45.8, 35, 11));


        Method getSumFromList = DZ_Solution.class.getDeclaredMethod("getSumFromList", new Class[]{DZ_Solution.class, DZ_Solution.class});
        getSumFromList.setAccessible(true);
        List<Double> doubles = (List<Double>) getSumFromList.invoke(builder3, builder4, builder5);
        List<Double> doubles2 = (List<Double>) getSumFromList.invoke(builder4, builder3, builder5);
        System.out.println(doubles);
        System.out.println(doubles2);
    }

}
