package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class CFG_GUI {

    @FXML
    private TextField variablesTF;

    @FXML
    private TextField symbolsTF;

    @FXML
    private TextField initialSymbolTF;

    @FXML
    void nextPage(ActionEvent event) {
        if (hasEmptyFields()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Empty Fields");
            alert.setContentText("Looks like some of the fields are empty. Check them carefully to proceed.");

            alert.showAndWait();
        }else if (!isUppercase()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Wrong Format");
            alert.setContentText("Your variables should be in uppercase. Please fix it.");

            alert.showAndWait();
        }else if (!isLowercase()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Wrong Format");
            alert.setContentText("Your symbols should be in lowercase. Please fix it.");

            alert.showAndWait();
        }else if (!isVariable()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Not Variable");
            alert.setContentText("Your initial symbol is not a variable. Please fix it.");

            alert.showAndWait();
        }else if (containsSpaces()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Input contains spaces");
            alert.setContentText("Looks like some of the fields contains spaces. The input should not contains spaces.");

            alert.showAndWait();
        }else {
            String variables = variablesTF.getText();
            String symbols = symbolsTF.getText();
            char initialSymbol = initialSymbolTF.getText().charAt(0);
        }
    }

    private boolean hasEmptyFields () {
        String variables = variablesTF.getText();
        String symbols = symbolsTF.getText();
        String initialSymbol = initialSymbolTF.getText();

        return variables.isEmpty() || symbols.isEmpty() || initialSymbol.isEmpty();
    }

    private boolean isUppercase () {
        String variables = variablesTF.getText();
        String uppercaseVariables = variables.toUpperCase();

        return variables.equals(uppercaseVariables);
    }

    private boolean isLowercase () {
        String symbols = symbolsTF.getText();
        String lowercaseSymbols = symbols.toLowerCase();

        return symbols.equals(lowercaseSymbols);
    }

    private boolean isVariable () {
        String variables = variablesTF.getText();
        String initialSymbol = initialSymbolTF.getText();

        int length = initialSymbol.length();

        return variables.contains(initialSymbol) && length == 1;
    }

    private boolean containsSpaces () {
        String space = " ";
        boolean variables = variablesTF.getText().contains(space);
        boolean symbols = symbolsTF.getText().contains(space);

        return variables || symbols;
    }

}
