public class StringPractice {
    public static void main(String[] args) {

        String text = "Домашнее задание. 1. В тексте, который вы видите на этом изображении, посчитайте " +
                "количество букв 'е' в каждом слове. 2. Напишите регулярное выражение для проверки телефона в " +
                "международном формате. 3. С помощью регулярного выражения напишите функцию удаления всех букв " +
                "из пробелов из текста.";

        // Задание 1.
        // Заменим все НЕ символы "е" на "" с помощью регулярного выражения и посчитаем количество букв в каждом слове
        System.out.println("---");
        System.out.println("Задание 1:");

        String[] strMassive = text.split(" ");

        for (String word : strMassive){
            int eCount = word.replaceAll("[^е]", "").length();
            System.out.println("В словe ->" + word + "<- содержится букв 'e' в количестве " + eCount + " шт.");
        }

        System.out.println("---");

        // Задание 2.
        // Зададим правильный формат номера в международном формате в виде +9 (999) 999-99-99
        String goodNumber = new String("+7 (987) 654-32-10");
        String badNumber = new String("82374511");
        String regexPhoneNumber = new String("^[+][0-9] [(][0-9]{3}[)] [0-9]{3}[-][0-9]{2}[-][0-9]{2}$");

        boolean goodNumberRes = goodNumber.matches(regexPhoneNumber);
        boolean badNumberRes = badNumber.matches(regexPhoneNumber);

        System.out.println("Задание 2:");
        System.out.println("Соответствует ли правильный номер регулярному выражению? " + goodNumberRes);
        System.out.println("Соответствует ли ошибочный номер регулярному выражению? " + badNumberRes);
        System.out.println("---");

        //Задание 3.
        // Заменим все буквы и пробелы на ""
        String notLit = text.replaceAll("[A-Za-zА-Яа-яёЁ ]", "");
        System.out.println("Задание 3:");
        System.out.println("После удаления букв и пробелов текст выглядит вот так: " + notLit);
        System.out.println("---");
    }
}