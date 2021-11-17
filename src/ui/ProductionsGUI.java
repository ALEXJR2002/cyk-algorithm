package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import model.CFG;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class ProductionsGUI {

    CFG cfg;
    TextField [][] textFields;

    private final double variablesPrefWidth = 22;
    private final double productionsPrefWidth = 247;
    private final double variablesPrefHeight = 25;
    private final double productionsPrefHeight = 25;
    private final String productionsPromptText = "AB|a";
    private final Pos alignment = Pos.CENTER;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private GridPane gridPane;

    @FXML
    private RowConstraints gridPaneRow;

    @FXML
    private TextField variable;

    @FXML
    private TextField production;

    @FXML
    private Label arrowLabel;

    public ProductionsGUI (CFG cfg) {
        this.cfg = cfg;
    }

    @FXML
    void nextPage(ActionEvent event) throws IOException {
        if (hasEmptyFields()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Empty Fields");
            alert.setContentText("Looks like some of the fields are empty. Check them carefully to proceed.");

            alert.showAndWait();
        }else if (isWrongFormat()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Wrong Format");
            alert.setContentText("Some variables or productions are in wrong format. Please fix it.");
            alert.showAndWait();
        }else {
            assignProductions();
            if (cfg.isCNF()){
                InputStringGUI inputStringGUI = new InputStringGUI(cfg);
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("interface/input-string.fxml"));
                fxmlLoader.setController(inputStringGUI);
                Parent root = fxmlLoader.load();
                anchorPane.getChildren().setAll(root);
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Wrong Grammar");
                alert.setContentText("Your grammar is not in Chomsky Normal Form. Please fix it.");
                alert.showAndWait();
            }
        }
    }

    private void assignProductions() {
        HashMap<Character, String> productions = new HashMap<>();
        for (int i = 0; i < textFields.length; i++) {
            char variable = textFields[i][0].getText().charAt(0);
            String stringProduction = textFields[i][1].getText();
            productions.put(variable, stringProduction);
        }
        cfg.setProductions(productions);
    }

    private boolean isWrongFormat() {
        String variables = Arrays.toString(cfg.getVariables());
        String symbols = Arrays.toString(cfg.getSymbols());
        boolean isWrongFormat = false;
        for (int i = 0; i < textFields.length && !isWrongFormat; i++) {
            String variableString = textFields[i][0].getText();
            String productionString = textFields[i][1].getText();

            isWrongFormat = !variableString.matches("[" + variables + "]+") || !productionString.matches("[" + variables + symbols + "|" + "]+");
        }
        return isWrongFormat;
    }

    private boolean hasEmptyFields() {
        boolean hasEmptyField = false;
        for (int i = 0; i < textFields.length && !hasEmptyField; i++) {
            String variableString = textFields[i][0].getText();
            String productionString = textFields[i][1].getText();

            hasEmptyField = variableString.isEmpty() || productionString.isEmpty();
        }
        return hasEmptyField;
    }


    public void initialize() {
        fillTable();
    }

    private void fillTable() {
        textFields = new TextField[cfg.getVariables().length][2];
        char [] variablesArray = cfg.getVariables();
        for (int i = 0; i < variablesArray.length; i++) {
            createProduction(variablesArray[i], i);
        }
    }

    private void createProduction(char character, int i) {
        TextField newVariable = new TextField(character + "");
        TextField newProduction = new TextField();
        Label newLabel = new Label("→");
        newVariable.setPrefWidth(variablesPrefWidth);
        newVariable.setPrefHeight(variablesPrefHeight);
        newProduction.setPrefWidth(productionsPrefWidth);
        newProduction.setPrefHeight(productionsPrefHeight);
        newProduction.setPromptText(productionsPromptText);
        newVariable.setAlignment(alignment);
        newProduction.setAlignment(alignment);

        gridPane.addRow(i + 1, newVariable, newLabel, newProduction);
        textFields[i][0] = newVariable;
        textFields[i][1] = newProduction;
        gridPane.setHalignment(newLabel, HPos.CENTER);

    }


}
