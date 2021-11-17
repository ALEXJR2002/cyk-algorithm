package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.CFG;

import java.io.IOException;

public class CFG_GUI {

    CFG cfg;
    ProductionsGUI productionsGUI;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private TextField variablesTF;

    @FXML
    private TextField symbolsTF;

    @FXML
    private TextField initialSymbolTF;

    public CFG_GUI () {
    }

    @FXML
    void nextPage(ActionEvent event) throws IOException {
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

            cfg = new CFG(variables, symbols, initialSymbol);
            productionsGUI = new ProductionsGUI (cfg);


            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("interface/productions.fxml"));
            fxmlLoader.setController(productionsGUI);
            Parent root = fxmlLoader.load();
            mainPane.getChildren().setAll(root);

        }
    }

    /** Checks if some input has empty fields
     * @return true if exists an input with empty field
     */
    private boolean hasEmptyFields () {
        String variables = variablesTF.getText();
        String symbols = symbolsTF.getText();
        String initialSymbol = initialSymbolTF.getText();

        return variables.isEmpty() || symbols.isEmpty() || initialSymbol.isEmpty();
    }

    /** Checks if the variables input is uppercase
     * @return true if variales input is uppercase, false otherwise
     */
    private boolean isUppercase () {
        String variables = variablesTF.getText();
        String uppercaseVariables = variables.toUpperCase();

        return variables.equals(uppercaseVariables);
    }

    /** Checks if symbols input is lowercase.
     * @return true if symbols input is lowercase, false otherwise.
     */
    private boolean isLowercase () {
        String symbols = symbolsTF.getText();
        String lowercaseSymbols = symbols.toLowerCase();

        return symbols.equals(lowercaseSymbols);
    }

    /** Checks if the initial symbol is a grammar variable.
     * @return true is the initial symbol is a grammar variable, false otherwise.
     */
    private boolean isVariable () {
        String variables = variablesTF.getText();
        String initialSymbol = initialSymbolTF.getText();

        int length = initialSymbol.length();

        return variables.contains(initialSymbol) && length == 1;
    }

    /** Checks if exists an input with spaces.
     * @return true if exists an input with spaces, false otherwise.
     */
    private boolean containsSpaces () {
        String space = " ";
        boolean variables = variablesTF.getText().contains(space);
        boolean symbols = symbolsTF.getText().contains(space);

        return variables || symbols;
    }

}
