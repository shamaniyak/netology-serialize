import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Basket implements Serializable {
    private final Product[] products;
    private transient double sumProducts = 0; // общая сумма покупки

    public Basket(Product[] products) {
        this.products = products;
    }

    public void addToCart(int productNumber, int amount) {
        if (productNumber < 0 || productNumber >= products.length) {
            System.out.println("Введен некорректный номер продукта");
            return;
        }

        // если ввели ноль, то убираем продукт из корзины, иначе добавляем количество продукта
        if (amount == 0) {
            products[productNumber].setCount(0);
        } else {
            products[productNumber].add(amount);
        }
    }

    public void printCart() {
        System.out.println("Ваша корзина: ");
        // считаем общую сумму и печатаем количество и сумму каждого товара
        for (Product product : products) {
            if (product.getCount() > 0) {
                double sum = product.getSum();
                sumProducts += sum;
                System.out.println(product.getName() + " " + product.getCount() + " шт. "
                        + product.getPrice() + "руб./шт. " + sum + " руб. в сумме");
            }
        }
        // печатаем общую сумму
        System.out.println("Итого: " + sumProducts + " руб.");
    }

    public void saveTxt(File file) {
        try(PrintWriter writer = new PrintWriter(file))
        {
            // идем по массиву продуктов и записываем каждый продукта в файл
            for (Product product : products) {
                writer.println(product.toString());
            }
            writer.flush();
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static Basket loadFromTxtFile(File file) {
        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            List<Product> productList = new ArrayList<>();
            // читаем построчно продукты
            String sProduct;
            while((sProduct = reader.readLine()) != null) {
                // разобьем строку, получив информацию о продукте
                String[] productInfo = sProduct.split(" ");
                String name = productInfo[0];
                double price = Double.parseDouble(productInfo[1]);
                boolean promo = Boolean.parseBoolean(productInfo[2]);
                int amount = Integer.parseInt(productInfo[3]); // кол-во продукта
                var p = new Product(name, price, promo);
                p.setCount(amount);
                productList.add(p);
            }
            var products = productList.toArray(new Product[0]);
            return new Basket(products);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public  void saveBin(File file) {
        try(ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(file)))
        {
            writer.writeObject(this);
            writer.flush();
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static Basket loadFromBinFile(File file) {
        Basket basket = null;
        try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream(file)))
        {
            basket = (Basket) reader.readObject();
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return basket;
    }
}
