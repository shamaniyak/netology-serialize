import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Product[] products = {
                new Product("Хлеб", 60, false),
                new Product("Яблоки", 130, true),
                new Product("Молоко", 80, false),
                new Product("Морковь", 20, true),
                new Product("Конфеты", 300, false)
        };

        printProducts(products);

        while (true) {
            System.out.println("Выберите товар и количество или введите end");
            String input = scanner.nextLine();
            if ("end".equals(input)) {
                break;
            } else {
                String[] parts = input.split(" ");
                if (parts.length == 2) {
                    int productNumber = 0;
                    int productCount = 0;
                    try {
                        productNumber = Integer.parseInt(parts[0]) - 1; //номер продукта со сканера
                        productCount = Integer.parseInt(parts[1]); // кол-во продукта со сканера
                    } catch (NumberFormatException e) {
                        System.out.println("Введены нечисловые данные!");
                    }

                    if (productNumber < 0 || productNumber > products.length - 1) {
                        System.out.println("Введен некорректный номер продукта");
                        continue;
                    }

                    // если ввели ноль, то убираем продукт из корзины, иначе добавляем количество продукта
                    if (productCount == 0) {
                        products[productNumber].setCount(0);
                    } else {
                        products[productNumber].add(productCount);
                    }
                } else {
                    System.out.println("Введены одна или более двух частей!");
                }
            }
        }

        printBasket(products);
    }

    private static void printBasket(Product[] products) {
        System.out.println("Ваша корзина: ");

        double sumProducts = 0; // общая сумма покупки

        // считаем общую сумму и печатаем количество и сумму каждого товара
        for (int i = 0; i < products.length; i++) {
            if (products[i].getCount() > 0) {
                double sum = products[i].getSum();
                sumProducts += sum;
                System.out.println(products[i].getName() + " " + products[i].getCount() + " шт. "
                        + products[i].getPrice() + "руб./шт. " + sum + " руб. в сумме");
            }
        }
        // печатаем общую сумму
        System.out.println("Итого: " + sumProducts + " руб.");
    }

    private static void printProducts(Product[] products) {
        System.out.println("Список возможных товаров для покупки: ");
        for (int i = 0; i < products.length; i++) {
            String message;
            if (!products[i].isPromo()) {
                message = " ";
            } else {
                message = "по акции \"3 + 2\"";
            }
            System.out.println((i + 1) + ". " + products[i].getName() + " " + products[i].getPrice() + " руб/шт "
                    + message);
        }
    }
}