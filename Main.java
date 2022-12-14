import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Список продуктов, которые могут быть в корзине
        Product[] products = {
                new Product("Хлеб", 60, false),
                new Product("Яблоки", 130, true),
                new Product("Молоко", 80, false),
                new Product("Морковь", 20, true),
                new Product("Конфеты", 300, false)
        };
        // Создать корзину и инициализировать списком продуктов
        Basket basket = new Basket(products);
        // Настройки
        File configFile = new File("config.xml");
        Config config = new Config();
        config.loadFromXml(configFile);
        // Загрузить корзину из файла
        //File basketTxtFile = new File("basket.txt");
        //File basketBinFile = new File("basket.bin");
        //File basketJsonFile = new File("basket.json");
        if(config.getLoad().enabled) {
            File file = new File(config.getLoad().fileName);
            if (file.exists()) {
                Basket basket1;
                if(config.getLoad().format.compareTo("json") == 0)
                    basket1 = Basket.loadFromJsonFile(file);
                else
                    basket1 = Basket.loadFromTxtFile(file);
                if (basket1 != null)
                    basket = basket1;
                else
                    System.out.println("basket1 is null");
            }
        }

        File saveFile = new File(config.getSave().fileName);

        printProducts(products);

        while (true) {
            System.out.println("Выберите товар и количество или введите end");
            String input = scanner.nextLine();
            if ("end".equals(input)) {
                break;
            } else {
                String[] parts = input.split(" ");
                if (parts.length == 2) {
                    int productNumber;
                    int productCount;
                    try {
                        productNumber = Integer.parseInt(parts[0]) - 1; //номер продукта со сканера
                        productCount = Integer.parseInt(parts[1]); // кол-во продукта со сканера
                        // Добавить в корзину
                        basket.addToCart(productNumber, productCount);
                        // Сохранить корзину
                        if(config.getSave().enabled) {
                            if(config.getSave().format.compareTo("json") == 0)
                                basket.saveJson(saveFile);
                            else
                                basket.saveTxt(saveFile);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Введены нечисловые данные!");
                    }
                } else {
                    System.out.println("Введены одна или более двух частей!");
                }
            }
        }

        basket.printCart();
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