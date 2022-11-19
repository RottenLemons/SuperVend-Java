package sample.Model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.*;

public class ShoppingCart {
    private ArrayList<Product> shoppingCart = new ArrayList<>(10);
    private String filename;

    public ShoppingCart(String filename) {
        this.filename = filename;
    }

    public void add(ArrayList<Product> products) {
        if (shoppingCart.size() + products.size() <= 10) {
            for (Product product: products) {
                add(product);
            }
        } else {
            throw new ArrayStoreException("Cannot add more in cart");
        }
        Collections.sort(shoppingCart, new CartSorter());
    }

    public ArrayList<Product> getShoppingCart() {
        return shoppingCart;
    }

    public void add(Product product) {
        if (shoppingCart.size() < 10) {
            shoppingCart.add(product);
        } else {
            throw new ArrayStoreException("Cannot add more in cart");
        }
        Collections.sort(shoppingCart, new CartSorter());
    }

    public void delete(Product product) {
        for (int i = 0; i < shoppingCart.size(); i++) {
            if (shoppingCart.get(i).getProductID().equals(product.getProductID())) {
                shoppingCart.remove(i);
                break;
            }
        }

        Collections.sort(shoppingCart, new CartSorter());
    }

    public void remove(Product product) {
        for (int i = 0; i < shoppingCart.size(); i++) {
            if (shoppingCart.get(i).getProductID().equals(product.getProductID())) {
                shoppingCart.remove(i);
            }
        }

        Collections.sort(shoppingCart, new CartSorter());
    }

    public void clearAll() {
        shoppingCart.clear();
    }

    public double sum() {
        double sum = 0;

        for (Product product: shoppingCart) {
            sum += product.getPrice();
        }

        return sum;
    }

    public ArrayList<ArrayList<Product>> bagging() {
        ArrayList<ArrayList<Product>> bags = new ArrayList<ArrayList<Product>>(4);

        for (Product product: shoppingCart) {

            if (bags.isEmpty()) {
                ArrayList<Product> bag = new ArrayList<>(1);
                bag.add(product);
                bags.add(bag);
            } else {
                boolean in = false;
                for (ArrayList<Product> bag: bags) {
                    if (bag.get(0).getTemp() < 0 && product.getTemp() < 0) {
                        in = isIn(product, in, bag);
                    } else if (bag.get(0).getTemp() > 20 && product.getTemp() > 20) {
                        in = isIn(product, in, bag);
                    } else if ((bag.get(0).getTemp() <= 20 && product.getTemp() <= 20) && (bag.get(0).getTemp() >= 0 && product.getTemp() >= 0)) {
                        in = isIn(product, in, bag);
                    }
                }

                if (!in) {
                    ArrayList<Product> anotherBag = new ArrayList<>(4);
                    anotherBag.add(product);
                    bags.add(anotherBag);

                }
            }
        }

        return bags;
    }

    private boolean isIn(Product product, boolean in, ArrayList<Product> bag) {
        int sum = 0;
        for (Product pros: bag) {
            if (pros.getSize().equals("S")) {
                sum += 1;
            } else if (pros.getSize().equals("M")) {
                sum += 2;
            } else {
                sum += 4;
            }
        }

        if (product.getSize().equals("S")) {
            sum += 1;
        } else if (product.getSize().equals("M")) {
            sum += 2;
        } else {
            sum += 4;
        }

        if (sum <= 8) {
            bag.add(product);
            in = true;
        }
        return in;
    }

    public double returnCash(double cash){
        return cash - sum();
    }

    public void updateTransaction() {
        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(filename, true));
            LocalDateTime dateTime = LocalDateTime.now();
            Inventory in = new Inventory("src/sample/Model/Resource/Data Files/ProductCode.csv", "src/sample/Model/Resource/Data Files/Product.csv", "src/sample/Model/Resource/Data Files/Inventory.csv");
            Random random = new Random();
            String code = "";
            for (int i = 0; i < 5; i++) {
                char s = (char) (random.nextInt(26) + 65);
                code += s;
            }

            for (Product product: shoppingCart) {
                int quantiy = 0;
                for (ArrayList<Object> list: in.getInventoryList()) {
                    if (list.get(0).equals(product.getProductID())) {
                        quantiy = (int) list.get(1) - 1;
                    }
                }
                in.updateQuantity(product.getProductID(), quantiy);

                printWriter.println(code + "," + dateTime.toLocalDate() + "," + dateTime.toLocalTime() + "," + product.getProductID() + String.format(",%.2f,", product.getPrice()) + quantiy);
            }

            in.updateInv();
            printWriter.close();
            clearAll();
        } catch (IOException ex) {
            System.out.println("File Error");
            ex.printStackTrace();
        }
    }
}
