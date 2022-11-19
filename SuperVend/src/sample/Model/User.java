package sample.Model;

public class User {
    private String userID;
    private String password;

    public User(String userID, String password) {
        if (userID.matches("^(AD|BA)[\\d]{5}$") && password.matches("^[\\w]{6,}$")) {
            this.userID = userID;
            this.password = password;
        } else {
            throw new IllegalArgumentException("Invalid User");
        }
    }

    public String getUserID() {
        return userID;
    }

    public String getPassword() {
        return password;
    }
}
