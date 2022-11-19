package sample.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Products {
    protected ArrayList<Product> products = new ArrayList<>(6);
    private String filename;

    public Products(String filename) {
        this.filename = filename;
        productReader();
    }

    private void productReader() {
        try {
            File file = new File(this.filename);
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String[] product = sc.nextLine().split(",");
                if (product.length < 11) {
                    break;
                }
                ArrayList<String> images = new ArrayList<>(2);

                for (int i = 10; i < product.length; i++) {
                    images.add(product[i]);
                }
                products.add(new Product(product[0], product[1], product[2], product[3], Double.parseDouble(product[4]), Integer.parseInt(product[5]), product[6], product[7], product[8], Double.parseDouble(product[9]), images));
            }

            sc.close();

        } catch (IOException | ParseException e) {
            System.out.println("File Error");
            e.printStackTrace();
        }
    }

    private void productUpdater() {
        try {
            File file = new File(this.filename);
            PrintWriter printWriter = new PrintWriter(file);

            for (Product item: products) {
                printWriter.print(item.getProductID() + "," + item.getName() + "," + item.getDescription() + "," + item.getBrand() + "," + item.getPrice() + "," + item.getTemp() + "," + item.getSize() + "," + item.getCountry() + "," + item.getExpiry() + "," + item.getWeight());

                for (String link: item.getImages()) {
                    printWriter.print("," + link    );
                }

                printWriter.println();
            }

            printWriter.close();

        } catch (FileNotFoundException e) {
            System.out.println("File Error");
            e.printStackTrace();
        }
    }

    public void delete(String productID) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductID().equals(productID)) {
                products.remove(i);
                Collections.sort(products, new ProductsSorter());
                productUpdater();
                return;
            }
        }

        throw new NoSuchElementException("No Product");
    }


    public void add(String productID, String name, String description, String brand, double price, int temp, String size, String country, String expiry, double weight, ArrayList<String> images) throws ParseException {
        products.add(new Product(productID, name, description, brand, price, temp, size, country,expiry, weight, images));
        Collections.sort(products, new ProductsSorter());
        productUpdater();
    }

    public void edit(Product product, String productID, String name, String description, String brand, double price, int temp, String size, String country, String expiry, double weight, ArrayList<String> images) throws ParseException {
        ArrayList<String> im = new ArrayList<>();
        for (String image: images) {
            im.add(image);
        }

        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductID().equals(product.getProductID())) {
                products.set(i, new Product(productID, name, description, brand, price, temp, size, country,expiry, weight, im));
            }
        }

        Collections.sort(products, new ProductsSorter());
        productUpdater();
    }
}