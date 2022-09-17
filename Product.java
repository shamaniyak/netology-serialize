import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private double price;
    // true - товар по акции, false - без акции
    private boolean promo;
    private int count = 0;

    public Product(String name, double price, boolean promo) {
        this.name = name;
        this.price = price;
        this.promo = promo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isPromo() {
        return promo;
    }

    public void setPromo(boolean promo) {
        this.promo = promo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void add(int count) {
        this.count += count;
        if(this.count < 0)
            this.count = 0;
    }

    public double getSum() {
        int sale = count / 3;
        return (count - sale) * price;
    }

    @Override
    public String toString() {
        return getName() + " " + getPrice() + " " + isPromo() + " " + getCount();
    }
}
