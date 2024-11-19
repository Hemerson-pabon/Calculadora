package org.calculadora;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ParametrosController {

    @FXML
    private void initialize() {
        FieldN.setText(String.valueOf(ExpresionMatematica.getN()));
        FieldCantDecimales.setText(String.valueOf(CalculadoraController.getCantDecimales()));
    }

    @FXML
    private TextField FieldCantDecimales;
    @FXML
    private TextField FieldN;

    @FXML
    protected void ButtonSaveClick() {
        try {
            int CantDecimales = Integer.parseInt(FieldCantDecimales.getText());
            int N = Integer.parseInt(FieldN.getText());
            ExpresionMatematica.setN(N);
            CalculadoraController.setCantDecimales(CantDecimales);
            Stage stage = (Stage) FieldCantDecimales.getScene().getWindow();
            stage.close();
        } catch (NumberFormatException e) {
            System.err.println("Error: Por favor, ingrese números válidos.");
        }
    }
}
