package org.calculadora;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class CalculadoraViewController {
    @FXML
    private Button menuButton, logaritmoNaturalButton, potenciaNButton, senoButton, cosenoButton, tangenteButton,
            raizButton, fraccionSimpleButton, exponencialButton, numEulerButton, numPiButton, negativoButton, allClearButton,
            clearButton, divisionButton, productoButton, restaButton, sumaButton, totalButton, decimalPointButton,
            zeroButton, oneButton, twoButton, threeButton, fourButton, fiveButton, sixButton, sevenButton, eightButton, nineButton;

    @FXML
    private Label actualOperationLabel, beforeOperationLabel;

    @FXML
    private Button secondButton, raizTriButton, potenciaNTriButton, senoTriButton, cosenoTriButton, tangenteTriButton,
            fraccionSimpleTriButton, secanteTriButton, cosecanteTriButton, cotangenteTriButton, sumaTriButton,
            sevenTriButton, fourTriButton, oneTriButton, decimalPointTriButton, menosTriButton, eigthTriButton,
            fiveTriButton, twoTriButton, zeroTriButton, divisionTriButton, nineTriButton, sixTriButton,
            threeTriButton, totalTriButton, multiplicacionTriButton, answerTriButton, clearTriButton, allClearTriButton;

    @FXML
    private Button basicButton, trigonometricButton, integralesButton;

    @FXML
    private Pane basicPane, trigonometricPane;

    // Métodos para cambiar entre panes
    @FXML
    void basicClick() {
        basicPane.setVisible(true);
        basicPane.toFront();
    }

    @FXML
    void trigonometricClick() {
        trigonometricPane.setVisible(true);
        trigonometricPane.toFront();
    }

    @FXML
    void integralesClick() {
        // Agregar lógica para el panel de integrales (falta su diseño)
    }

    // Métodos de cálculo
    @FXML
    void logaritmoNaturalClick() {
        // Lógica para calcular el logaritmo natural
    }

    @FXML
    void raizClick() {
        // Lógica para calcular la raíz cuadrada
    }

    @FXML
    void allClearClick() {
        actualOperationLabel.setText("");
        beforeOperationLabel.setText("");
    }

    @FXML
    void divisionClick() {
        appendOperator("/");
    }

    @FXML
    void productClick() {
        appendOperator("*");
    }

    @FXML
    void restaClick() {
        appendOperator("-");
    }

    @FXML
    void sumaClick() {
        appendOperator("+");
    }

    @FXML
    void totalClick() {
        // Lógica para calcular el total de la operación
    }

    @FXML
    void decimalPointClick() {
        appendNumber(".");
    }

    // Manejo de números
    @FXML
    void zeroClick() { appendNumber("0"); }

    @FXML
    void oneClick() { appendNumber("1"); }

    @FXML
    void twoClick() { appendNumber("2"); }

    @FXML
    void threeClick() { appendNumber("3"); }

    @FXML
    void fourClick() { appendNumber("4"); }

    @FXML
    void fiveClick() { appendNumber("5"); }

    @FXML
    void sixClick() { appendNumber("6"); }

    @FXML
    void sevenClick() { appendNumber("7"); }

    @FXML
    void eightClick() { appendNumber("8"); }

    @FXML
    void nineClick() { appendNumber("9"); }

    @FXML
    void clearClick() {
        actualOperationLabel.setText("");
    }

    @FXML
    void potenciaNClick() {
        appendOperator("^");
    }

    @FXML
    void senoClick() {
        appendOperator("sin");
    }

    @FXML
    void cosenoClick() {
        appendOperator("cos");
    }

    @FXML
    void tangenteClick() {
        appendOperator("tan");
    }

    @FXML
    void exponencialClick() {
        appendOperator("e^");
    }

    @FXML
    void numEulerClick() {
        appendNumber("e");
    }

    @FXML
    void numPiClick() {
        appendNumber("π");
    }

    // Métodos trigonométricos adicionales
    @FXML
    void secondClick() {
        // Alternar entre funciones trigonométricas normales y avanzadas
    }

    @FXML
    void raizTriClick() {
        appendOperator("√");
    }

    @FXML
    void potenciaNTriClick() {
        appendOperator("^");
    }

    @FXML
    void fraccionSimpleTriClick() {
        appendOperator("1/x");
    }

    @FXML
    void secanteTriClick() {
        appendOperator("sec");
    }

    @FXML
    void cosecanteTriClick() {
        appendOperator("csc");
    }

    @FXML
    void cotangenteTriClick() {
        appendOperator("cot");
    }

    @FXML
    void answerTriClick() {
        // Agregar lógica para obtener la respuesta previa
    }

    @FXML
    void allClearTriButton() {
        actualOperationLabel.setText("");
        beforeOperationLabel.setText("");
    }

    @FXML
    void clearTriClick() {
        actualOperationLabel.setText("");
    }
    @FXML
    void fraccionSimpleClick(){

    }

    @FXML
    void eigthClick(){

    }



    // Métodos de utilidad
    private void appendNumber(String number) {
        actualOperationLabel.setText(actualOperationLabel.getText() + number);
    }

    private void appendOperator(String operator) {
        String current = actualOperationLabel.getText();
        if (!current.isEmpty() && !isLastCharacterOperator(current)) {
            actualOperationLabel.setText(current + " " + operator + " ");
        }
    }

    private boolean isLastCharacterOperator(String text) {
        return text.endsWith("+") || text.endsWith("-") || text.endsWith("*") || text.endsWith("/") || text.endsWith("^");
    }
}
