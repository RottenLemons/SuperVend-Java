package sample.Model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Security{

    private ArrayList<User> users = new ArrayList<>(2);
    private String user;

    public Security(String filename) {
        loginDB(filename);
    }

    public void loginDB(String filename) {

        try {
            File file = new File(filename);
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String[] user = sc.nextLine().split(",");
                users.add(new User(user[0], user[1]));
            }

            sc.close();
        } catch (IOException e) {
            System.out.println("File Error");
            e.printStackTrace();
        }
    }

    public int authenticate(String userID, String password) {

        for (User userIn: users) {
            if (userIn.getUserID().equals(userID) && userIn.getPassword().equals(password)) {
                user = userIn.getUserID();
                if (userID.matches("^AD[\\d]{5}$")) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }

        return -1;
    }

    public void nullinator() {
        user = null;
    }
}
