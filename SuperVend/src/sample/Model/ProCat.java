package sample.Model;

import javafx.beans.property.SimpleStringProperty;

public class ProCat {
    private final SimpleStringProperty nameString = new SimpleStringProperty("");
    private final SimpleStringProperty codeString = new SimpleStringProperty("");

    public ProCat() {
        this("","");
    }

    public ProCat(String nameString, String codeString) {
        setCodeString(codeString);
        setNameString(nameString);
    }

    public String getNameString() {
        return nameString.get();
    }

    public SimpleStringProperty nameStringProperty() {
        return nameString;
    }

    public String getCodeString() {
        return codeString.get();
    }

    public SimpleStringProperty codeStringProperty() {
        return codeString;
    }

    public void setNameString(String nameString) {
        this.nameString.set(nameString);
    }

    public void setCodeString(String codeString) {
        this.codeString.set(codeString);
    }
}
