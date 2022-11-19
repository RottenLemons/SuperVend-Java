package sample.Model;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

public class ProductCategory extends Products {

    private ArrayList<Product> prodCat = new ArrayList<>(2);
    private String name;
    private String code;

    public ProductCategory(String filename, String name, String code) {
        super(filename);
        this.name = name;
        this.code = code;
        productCat();
    }

    public void productCat() {
        for (Product item: products) {
            if (item.getProductID().matches("^" + code + "[\\d]{5}$")) {
                prodCat.add(item);
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<Product> getProdCat() {
        return prodCat;
    }

    public Product getProduct(String productID) {
        if (productID.matches("^" + code + "[\\d]{5}$")) {
            for (Product item: products) {
                if (item.getProductID().equals(productID)) {
                    return item;
                }
            }
            throw new NoSuchElementException("Product Not Found");
        } else {
            throw new InputMismatchException("Wrong ProductCode");
        }
    }
}
