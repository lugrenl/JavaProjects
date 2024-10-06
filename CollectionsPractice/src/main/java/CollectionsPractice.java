import java.util.*;

public class CollectionsPractice {
    public static void main(String[] args) {

        // Task #1
        Map<Integer, String> passportsAndNames = new HashMap<>();

        passportsAndNames.put(238743, "Лидия Аркадьевна Бубликова");
        passportsAndNames.put(295483, "Иван Михайлович Серебряков");
        passportsAndNames.put(936254, "Сергей Леонидович Сушков");
        passportsAndNames.put(448240, "Алексей Андреевич Ермаков");
        passportsAndNames.put(231009, "Максим Олегович Архаров");

        Map<String, Integer> namesAndPassports;
        namesAndPassports = switchEntries(passportsAndNames);
        System.out.println(namesAndPassports);

        //Task #2
        testArrays();
        /*
        1000 get запросов к ArrayList выполняются от 0мс до 1мс
        1000 get запросов к LinkedList выполняются от 1400мс до 1500мс
        Это связано, в первую очередь, с разницей в структурах данных, так как
        в ArrayList мы получаем элемент по индексу за O(1),
        а в LinkedList от O(1) до O(n/2) так как в худшем случае на нужно будет пройти
        до середины массива перебирая элементы, чтобы найти нужный индекс.
        */

        //Task #3
        ArrayList<String> stringArrayList = new ArrayList<>();

        stringArrayList.add("Первый");
        stringArrayList.add("Первый");
        stringArrayList.add("Я");
        stringArrayList.add("Второй");
        stringArrayList.add("Повторяю");
        stringArrayList.add("Я");
        stringArrayList.add("Второй");

        delDuplicates(stringArrayList);
        System.out.println("Строковый массив без дубликатов = " + stringArrayList);
    }

    public static Map<String, Integer> switchEntries(Map<Integer, String> inputMap) {

        Map<String, Integer> newMap = new HashMap<>();

        for (Map.Entry<Integer, String> entry : inputMap.entrySet()) {
            newMap.put(entry.getValue(), entry.getKey());
        }
        return newMap;
    }

    public static void testArrays() {

        int arraySize = 1000000;
        int iterationCount = 1000;
        Random random = new Random();
        ArrayList<Integer> testArrayList = new ArrayList<>(arraySize);
        LinkedList<Integer> testLinkedList = new LinkedList<>();

        for (int i = 0; i < arraySize; i++) {
            testArrayList.add(random.nextInt(arraySize));
            testLinkedList.add(random.nextInt(arraySize));
        }

        // Test ArrayList
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < iterationCount; i++) {
            testArrayList.get(random.nextInt(arraySize));
        }
        long endTime = System.currentTimeMillis();
        System.out.println("ArrayList get time = " + (endTime-startTime));

        // Test LinkedList
        startTime = System.currentTimeMillis();
        for (int i = 0; i < iterationCount; i++) {
            testLinkedList.get(random.nextInt(arraySize));
        }
        endTime = System.currentTimeMillis();
        System.out.println("LinkedList get time = " + (endTime-startTime));
    }

    public static void delDuplicates(ArrayList<String> inputStringArray) {

        LinkedHashSet<String> stringHashSet = new LinkedHashSet<>(inputStringArray);
        inputStringArray.clear();
        inputStringArray.addAll(stringHashSet);
    }
}
