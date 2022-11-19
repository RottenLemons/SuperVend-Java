package sample.Model;

import java.util.Comparator;

public class CartSorter implements Comparator<Product> {

    @Override
    public int compare(Product o1, Product o2) {
        String code1 = o1.getProductID().substring(0, 3);
        String code2 = o2.getProductID().substring(0 ,3);
        if (code1.equals(code2)) {
            if (o1.getSize().equals(o2.getSize())) {
                return o1.getName().compareTo(o2.getName());
            } else {
                return o1.getSize().compareTo(o2.getSize());
            }
        } else {
            return code1.compareTo(code2);
        }
    }
}
