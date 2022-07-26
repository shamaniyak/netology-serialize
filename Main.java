import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String products[] = {"Хлеб", "Яблоки", "Молоко", "Морковь", "Конфеты"};
        int prices[] = {60, 130, 80, 20, 300};

        int sales[] = {0, 1, 0, 1, 0}; // 1 - товар по акции, 0 - без акции
        String message[] = new String[5];
        System.out.println("Список возможных товаров для покупки: ");

        for (int i = 0; i < products.length; i++) {
            if (sales[i] == 0) {
                message[i] = " ";
            } else {
                message[i] = "по акции \"3 + 2\"";
            }
            System.out.println((i + 1) + ". " + products[i] + " " + prices[i] + " руб/шт "
                    + message[i]);
        }

        int sumProducts = 0; // общая сумма покупки
        int count[] = new int[5]; // массив для хранения количества продуктов
        int sale[] = new int[5];
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

                    if (productNumber < 0 | productNumber > products.length - 1) {
                        System.out.println("Введен некорректный номер продукта");
                        continue;
                    }
                    int currentPrice = prices[productNumber]; // цена продукта по номеру
                    int sum; // сумма покупки
                    int saleCount;

                    if (productCount == 0) {
                        sumProducts = sumProducts - count[productNumber] * currentPrice;
                        count[productNumber] = 0;
                    } else if (productCount < 0) {
                        if (count[productNumber] == 0) {
                            continue;
                        } else {
                            ///int ostatok = count[productNumber] % 3; ///////////
                            count[productNumber] += productCount;
                            if (count[productNumber] < 0) {
                                count[productNumber] = 0;
                                //sumProducts = 0;
                                continue;
                            }

                            if (sales[productNumber] == 1) {

                                saleCount = count[productNumber] / 3; ///////
                                sale[productNumber] = saleCount; /////
                                ///sum = (productCount - saleCount) * currentPrice; //сумма покупки //////
                                ///sumProducts += sum;
                            } else {
                                saleCount = 0;
                                sale[productNumber] = saleCount;
                                sum = (productCount * currentPrice); // сумма покупки
                                sumProducts += sum;
                            }
                        }
                    } else {
                        int ostatok = count[productNumber] % 3; //////////
                        count[productNumber] += productCount;
                        if (sales[productNumber] == 1) {
                            saleCount = count[productNumber] / 3; ///////
                            sale[productNumber] = saleCount;//////
                            sum = (productCount - saleCount) * currentPrice; //сумма покупки ////
                            sumProducts += sum;
                        } else {
                            saleCount = 0;
                            sale[productNumber] = saleCount;
                            sum = (productCount * currentPrice); // сумма покупки
                            sumProducts += sum;
                        }
                    }
                } else {
                    System.out.println("Введены одна или более двух частей!");
                }
            }
        }
        System.out.println("Ваша корзина: ");

        sumProducts = 0;
        for (int i = 0; i < products.length; i++) {
            if (count[i] > 0) {
                int sum = (count[i] - sale[i]) * prices[i];
                sumProducts += sum;
            }
        }

        for (int i = 0; i < products.length; i++) {
            if (count[i] > 0) {

                System.out.println(products[i] + " " + count[i] + " шт. "
                        + prices[i] + "руб./шт. " + (count[i] - sale[i]) * prices[i] + " руб. в сумме");
            }
        }
        System.out.println("Итого: " + sumProducts + " руб.");
    }
}