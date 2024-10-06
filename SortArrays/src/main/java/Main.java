import java.util.Arrays;

public class Main {
    static int max_size = 2;
    static int size = 0;
    static int[] data = new int[max_size];

    public static void main(String[] args) {
        add(4);
        add(3);
        add(1);
        add(5);
        add(7);
        print(); // должно вывести 4,3,1,5
        sort();
        print(); // должно вывести 1,3,4,5
    }

    public static void sort() {
        for (int i = 0; i < size - 1; i++) {
            int minElementIndex = i;
            for (int j = i + 1; j < size; j++) {
                if (data[minElementIndex] > data[j]) {
                    minElementIndex = j;
                }
            }

            if (minElementIndex != i) {
                int temp = data[i];
                data[i] = data[minElementIndex];
                data[minElementIndex] = temp;
            }
        }
    }

    public static void print() {
        System.out.print("[");
        for (int i = 0; i < size; i++) {
            System.out.print(" " + data[i]);
        }
        System.out.println(" ]");
    }

    public static void add(int value) {
        if (size >= data.length) {
            max_size *= 2;
            data = Arrays.copyOf(data, max_size);
        }
        data[size] = value;
        size++;
    }
}
