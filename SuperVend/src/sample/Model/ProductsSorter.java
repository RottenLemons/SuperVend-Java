package sample.Model;

import java.util.Comparator;

public class ProductsSorter implements Comparator<Product>  {

    @Override
    public int compare(Product o1, Product o2) {
        if (o1.getSize().equals(o2.getSize())) {
            return o1.getName().compareTo(o2.getName());
        } else {
            return o1.getSize().compareTo(o2.getSize());
        }
    }
}
