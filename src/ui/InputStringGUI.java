package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import model.CFG;

import java.util.Arrays;

public class InputStringGUI {

    CFG cfg;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TextField stringTextField;

    @FXML
    private GridPane gridPane;

    public InputStringGUI (CFG cfg) {
        this.cfg = cfg;
    }

    @FXML
    void test(ActionEvent event) {
        String string = stringTextField.getText();
        if (check(string)) {
            if (cfg.cykAlgorithm(string)) {
                //generateTable(string);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("Great!");
                alert.setContentText("The string is achieved by the grammar.");

                alert.showAndWait();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Wrong Format");
            alert.setContentText("The string is in a wrong format. Please fix it.");
            alert.showAndWait();
        }
    }

/*    private void generateTable(String s) {
        for (int j = 0; j < cfg.getCykTable().length; j++) {
            for (int i = 0; i < cfg.getCykTable()[j].length; i++) {
                gridPane.add(new Label("" + (i + 1)), i + 2, 1);
                gridPane.add(new Label("" + (i + 1)), 1 , i + 2);
                gridPane.add(new Label(cfg.getCykTable()[i][j] + ""), j + 2, i + 2);
            }
        }
        for (int i = 0; i < s.length(); i++) {
            System.out.println(gridPane.getColumnConstraints().get(i + 2));
            gridPane.getColumnConstraints().get(i + 2).setPrefWidth(30);
            gridPane.getRowConstraints().get(i + 2).setPrefHeight(30);
        }
    }*/

    private boolean check(String string) {
        String symbols = Arrays.toString(cfg.getSymbols());
        return string.matches("[" + symbols + "]+") && !string.isEmpty();
    }

}
