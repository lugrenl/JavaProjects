import java.util.Arrays;
import java.util.Scanner;

public class Calculator {

    public static void main(String[] args) {

        int idx = 0;
        double[] results = new double[3];

        while (idx < results.length) {

            Scanner reader = new Scanner(System.in);

            System.out.print("\nВведите 2 числа: ");

            double first = reader.nextDouble();
            double second = reader.nextDouble();

            System.out.print("Введите оператор: (+, -, *, /): ");
            char operator = reader.next().charAt(0);

            double result = 0.0;

            switch (operator) {
                case '+':
                    result = first + second;
                    break;

                case '-':
                    result = first - second;
                    break;

                case '*':
                    result = first * second;
                    break;

                case '/':
                    result = first / second;
                    break;

                default:
                    System.out.printf("Введите корректный оператор");
                    continue;
            }
            System.out.printf("%.1f %c %.1f = %.1f", first, operator, second, result);
            results[idx] = result;
            idx++;
            System.out.println("\nВы хотите закончить расчёты? Если да, введите - 'выход', если нет - введите любой символ:");
            String answer = reader.next();
            if (answer.equalsIgnoreCase("выход")) { break; }
        }
        if (idx == results.length) { System.out.println("\nМассив переполнен!"); }
        System.out.println("\nРасчёт окончен!");
        System.out.println(Arrays.toString(results));

    }
}

/*
 *     ДЗ
 *     1. добавьте массив для сохранения результатов размерностью 10
 *     если результатов стало больше мы завершаем работы, информируя пользователя и распечатывая результаты
 *
 *     2. поместите код в цикл для возможности использования без постоянного запуска программы.
 *     Для выхода пусть буду слова "выход"
 *     T.е. пользователь ввел выход - мы просто выходим, сохраняя результат в массиве результатов и выводим массив на консоль.
 *
 *
 */

