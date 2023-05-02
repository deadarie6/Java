import java.util.Scanner; // импорт класса сканер
import java.io.IOException; // класс исключение при возникновении ошибки

public class Calculator {

    public static void main(String[] args) throws Exception {
        Scanner keyboard = new Scanner(System.in); // воспринимает ввод чисел с клавиатуры

        String inputNum = keyboard.nextLine(); // присваивание чисел переменной
        String[] expressionArray = parse(inputNum); // преобразование строки в числа

        System.out.println(calculate(expressionArray)); // ответ

        keyboard.close();
    }

    private static String[] parse(String expression) {
        String[] inputStrings = expression.split(" "); // массив выражения с разделителем
        try {
            if (inputStrings.length != 3) {
                throw new IOException(); // выбрасывает исключение при некорректном вводе операции
            }

            return inputStrings;
        } catch (IOException exception) {
            throw new RuntimeException("Некорректный ввод");
        }
    }

    public static String calculate(String[] expressionTokens) throws Exception {
        boolean romanNumber = false; // метод проверки системы счисления и корректность ввода
        if(romanNum(expressionTokens[0]) && romanNum(expressionTokens[2])) {
            romanNumber = true; // работа только с римскими цифрами
        } else if(romanNum(expressionTokens[0]) || romanNum(expressionTokens[2])) { // исключение
            throw new RuntimeException("Оба числа должны быть в одной системе счисления");
        }

        int a = parseNum(expressionTokens[0]);
        int b = parseNum(expressionTokens[2]);
        String operator = expressionTokens[1]; // преобразование ввода чисел и операций

        if (((a < 1) || (b < 1)) || ((a > 10) || (b > 10))) {
            throw new Exception("Некорректный ввод"); // принимает на вход числа от 1 до 10 включительно
        }

        Integer result = null; // переменная возвращает результат

        switch (operator) { // применение операторов
            case "+" -> result = (a + b);
            case "-" -> result = (a - b);
            case "*" -> result = (a * b);
            case "/" -> result = (a / b);
            default -> System.out.println("Допустимые только знаки (+, -, *, /)");
        }

        if (romanNumber && result < 1) { // только положительные числа
            throw new Exception("в римской системе нет отрицательных чисел");
        }

        if (romanNumber) {
            return arabicToRoman(result);
        } else {
            return Integer.toString(result); // вывод ответа в соответствии с введенными числами
        }
    }

    private static int parseNum(String num) { // преобразование целого числа в системе счисления
        try {
            return Integer.parseInt(num);
        } catch (NumberFormatException exception) { // ошибка, некорректный ввод
            if(romanNum(num)) {
                return romanToArabic(num);
            } else {
                throw new RuntimeException("Допустимы только целые значения");
            }
        }
    }

    private static boolean romanNum(String num) { // выражение проверки валидности римских чисел
        return num.matches("^M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$");
    }

    static final String[] Roman1before10 = new String[] {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
    static final String[] RomanFull = new String[] {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC", "C"};

    private static int romanToArabic(String sense) {
        for (int i = 0; i < Roman1before10.length; i++) {
            if (Roman1before10[i].equals(sense)) {
                return i;
            }
        }
        return 0;
    }

    static String arabicToRoman(int sense) {
        return RomanFull[sense / 10] + Roman1before10[sense % 10];
    }
}