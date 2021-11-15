package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import model.CFG;

public class ProductionsGUI {

    CFG cfg;
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
    void nextPage(ActionEvent event) {

    }

    public void initialize() {
        fillTable();
    }

    private void fillTable() {
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
        gridPane.setHalignment(newLabel, HPos.CENTER);

    }
}
