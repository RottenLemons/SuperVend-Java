package sample.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Inventory {
    private ArrayList<ProductCategory> cats = new ArrayList<>(3);
    private ArrayList<ArrayList<Object>> inventoryList = new ArrayList<>(3);
    private String  filename1;
    private String filename2;
    private String filename3;

    public Inventory(String filename1, String filename2, String filename3) {
        this.filename1 = filename1;
        this.filename2 = filename2;
        this.filename3 = filename3;
        codeReader();
        inventoryReader();
    }

    private void inventoryReader() {
        try {
            File file = new File(filename3);
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String[] inventory = sc.nextLine().split(",");
                ArrayList<Object> subList = new ArrayList<>(2);
                subList.add(inventory[1]);
                subList.add(Integer.parseInt(inventory[2]));
                inventoryList.add(subList);
            }
        } catch (IOException ex) {
            System.out.println("File Error");
            ex.printStackTrace();
        }
    }



    private void codeReader() {
        try {
            File file = new File(filename1);
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String[] category = sc.nextLine().split(",");
                cats.add(new ProductCategory(filename2 ,category[1], category[0]));
            }

            sc.close();

        } catch (IOException ex) {
            System.out.println("File Error");
            ex.printStackTrace();
        }
    }

    public void add(String productID, int quantity) {
        ArrayList<Object> list = new ArrayList<>(2);
        list.add(productID);
        list.add(quantity);
        inventoryList.add(list);
    }

    public void delete(String productID) {
        for (int i = 0; i < inventoryList.size(); i++) {
            if (inventoryList.get(i).get(0).equals(productID)) {
                inventoryList.remove(i);
                return;
            }
        }
        updateInv();
    }

    public void updateQuantity(String productID, int quantity) {
        for (int i = 0; i < inventoryList.size(); i++) {
            if (inventoryList.get(i).get(0).equals(productID)) {
                inventoryList.get(i).set(1, quantity);
            }
        }
        updateInv();
    }

    public void updateQuantity(String productID, String newID ,int quantity) {
        for (int i = 0; i < inventoryList.size(); i++) {
            if (inventoryList.get(i).get(0).equals(productID)) {
                inventoryList.get(i).set(1, quantity);
                inventoryList.get(i).set(0, newID);
            }
        }
        updateInv();
    }

    public void updateInv() {
        try {
            File file = new File(filename3);
            PrintWriter printWriter = new PrintWriter(file);

            for (ArrayList<Object> list: inventoryList) {
                printWriter.println(((String) list.get(0)).substring(0,2) + "," + list.get(0) + "," + list.get(1));
            }

            printWriter.close();

        } catch (FileNotFoundException e) {
            System.out.println("File Error");
            e.printStackTrace();
        }
    }

    public void deleteCat(String code) {
        for (int i = 0; i < cats.size(); i++) {
            if (cats.get(i).getCode().equals(code)) {
                cats.remove(i);
                updateCode();
                return;
            }
        }

        throw new NoSuchElementException("No Code");
    }


    public void addCat(String name, String code) {
        cats.add(new ProductCategory(filename2, name, code));
        updateCode();
    }

    public void editCat(String name, String code) {
        for (int i = 0; i < cats.size(); i++) {
            if (cats.get(i).getCode().equals(code)) {
                cats.set(i, new ProductCategory(filename2, name, code));
            }

        }
        updateCode();
    }

    public void updateCode() {
        try {
            File file = new File(filename1);
            PrintWriter printWriter = new PrintWriter(file);

            for (ProductCategory productCategory: cats) {
                printWriter.println(productCategory.getCode() + "," + productCategory.getName());
            }

            printWriter.close();

        } catch (FileNotFoundException e) {
            System.out.println("File Error");
            e.printStackTrace();
        }
    }

    public ArrayList<ProductCategory> getCats() {
        return cats;
    }

    public ProductCategory getProductCode(String code) {
        for (int i = 0; i < cats.size(); i++) {
            if (cats.get(i).getCode().equals(code)) {
                return cats.get(i);
            }
        }

        throw new NoSuchElementException("Code Not Found");
    }

    public ArrayList<ArrayList<Object>> getInventoryList() {
        return inventoryList;
    }

    public int getQuantity(Product product) {
        for (ArrayList<Object> in: inventoryList) {
            if (product.getProductID().equals(in.get(0))) {
                return (int) in.get(1);
            }
        }
        return 0;
    }
}
