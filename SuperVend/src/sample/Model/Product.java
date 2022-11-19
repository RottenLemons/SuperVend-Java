package sample.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Product {
    private String productID;
    private String name;
    private String description;
    private String brand;
    private double price;
    private int temp;
    private String size;
    private String country;
    private Date expiry;
    private double weight;
    private ArrayList<String> images;

    public Product(String productID, String name, String description, String brand, double price, int temp, String size, String country, String expiry, double weight, ArrayList<String> images) throws ParseException {

        if (productID.matches("^[A-Z]{2}[\\d]{5}$")) {
            this.productID = productID;
            this.name = name;
            this.description = description;
            this.brand = brand;
            if (price < 50) {
                this.price = price;
            } else {
                throw new IllegalArgumentException("Too much cash");
            }
            this.temp = temp;
            this.size = size;
            this.country = country;
            this.expiry = new SimpleDateFormat("dd-MM-yyyy").parse(expiry);
            this.weight = weight;
            this.images = images;
        } else {
            throw new IllegalArgumentException("Invalid Product");
        }
    }

    public String getProductID() {
        return productID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getBrand() {
        return brand;
    }

    public double getPrice() {
        return price;
    }

    public int getTemp() {
        return temp;
    }

    public String getSize() {
        return size;
    }

    public String getCountry() {
        return country;
    }

    public String getExpiry() {
        return new SimpleDateFormat("dd-MM-yyyy").format(expiry);
    }

    public double getWeight() {
        return weight;
    }

    public ArrayList<String> getImages() {
        return images;
    }
}
