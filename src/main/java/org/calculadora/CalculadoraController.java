package org.calculadora;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculadoraController {

    private static int cantDecimales = 3; // Cantidad de decimales a mostrar

    public static void setCantDecimales(int cantDecimales) {
        CalculadoraController.cantDecimales = cantDecimales;
    }

    public static int getCantDecimales() {
        return cantDecimales;
    }

    private String StringExpresion = "";
    private String StringTemp = "";

    // Bandera para verificar si se evaluó una expresión
    private boolean flagResult = false;

    // Método para adjuntar Strings
    private String AppendText(String string, String append){
        return string +
                append;
    }

    // Método para asignar números
    private void AsignarNum(String num){
        StringTemp = AppendText(FieldTemp.getText(), num);
        FieldTemp.setText(StringTemp);
    }

    // Método para asignar un símbolo
    private void AsignarSim(String sim){
        StringExpresion = FieldExpresion.getText();
        StringTemp = FieldTemp.getText();
        if(flagResult) {
            StringExpresion = StringTemp;
        }
        StringTemp = AppendText(StringTemp, sim);
        FieldTemp.setText(StringTemp);
        FieldExpresion.setText(StringExpresion);
        flagResult = false;
    }

    // Text field de la calculadora
    @FXML
    private TextField FieldExpresion;
    @FXML
    private TextField FieldTemp;

    // Eventos de clíck para los botones de números
    @FXML
    protected void ButtonZeroClick(){
        AsignarNum("0");
    }
    @FXML
    protected void ButtonOneClick(){
        AsignarNum("1");
    }
    @FXML
    protected void ButtonTwoClick(){
        AsignarNum("2");
    }
    @FXML
    protected void ButtonThreeClick(){
        AsignarNum("3");
    }
    @FXML
    protected void ButtonFourClick(){
        AsignarNum("4");
    }
    @FXML
    protected void ButtonFiveClick(){
        AsignarNum("5");
    }
    @FXML
    protected void ButtonSixClick(){
        AsignarNum("6");
    }
    @FXML
    protected void ButtonSevenClick(){
        AsignarNum("7");
    }
    @FXML
    protected void ButtonEightClick(){
        AsignarNum("8");
    }
    @FXML
    protected void ButtonNineClick(){
        AsignarNum("9");
    }

    // Eventos de clíck para los botones de símbolos
    @FXML
    protected void ButtonSumClick(){
        AsignarSim("+");
    }
    @FXML
    protected void ButtonSubClick(){
        AsignarSim("-");
    }
    @FXML
    protected void ButtonProdClick(){
        AsignarSim("*");
    }
    @FXML
    protected void ButtonDivClick(){
        AsignarSim("/");
    }

    @FXML
    protected void ButtonOpenPClick(){
        AsignarSim("(");
    }
    @FXML
    protected void ButtonClosePClick(){
        AsignarSim(")");
    }
    @FXML
    protected void ButtonPointClick(){
        AsignarNum(".");
    }

    // Botón de igual
    @FXML
    protected void ButtonResultClick(){
        flagResult = true;
        StringExpresion = FieldExpresion.getText();
        StringTemp = FieldTemp.getText();
        // Evaluar la expresión dada por el usuario
        ExpresionMatematica expresion = new ExpresionMatematica(StringTemp);
        BigDecimal resultado = expresion.evaluar();
        String resultadoStr = resultado.setScale(cantDecimales, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
        StringExpresion = StringTemp+" = ";
        StringTemp = resultadoStr;
        FieldExpresion.setText(StringExpresion);
        FieldTemp.setText(StringTemp);
    }

    // Eventos de botones de funciones hechas con series
    @FXML
    protected void ButtonCosClick(){
        AsignarNum("cos(");
    }
    @FXML
    protected void ButtonSinClick(){
        AsignarNum("sin(");
    }
    @FXML
    protected void ButtonSqrtClick(){
        AsignarNum("sqrt(");
    }
    @FXML
    protected void ButtonLnClick(){
        AsignarNum("ln(");
    }
    @FXML
    protected void ButtoneClick(){
        AsignarNum("exp(");
    }
    @FXML
    protected void ButtonaClick(){
        AsignarNum("^");
    }
    @FXML
    protected void ButtonXCuadClick(){
        AsignarNum("^2");
    }

    // Botones de constantes
    @FXML
    protected void ButtonPiClick(){
        AsignarNum("π");
    }
    @FXML
    protected void ButtoneSClick(){
        AsignarNum("e");
    }

    // Botón de limpiar y remover
    @FXML
    protected void ButtonClearAllClick(){
        StringExpresion = "";
        StringTemp = "";
        FieldExpresion.setText(StringExpresion);
        FieldTemp.setText(StringTemp);
    }
    @FXML
    protected void ButtonRemoveCharClick(){
        StringTemp = FieldTemp.getText();
        if (!StringTemp.isEmpty()) {
            StringTemp = StringTemp.substring(0, StringTemp.length() - 1);
        }
        FieldTemp.setText(StringTemp);
    }

    // Menu de opciones
    @FXML
    protected void ButtonCloseAppClick(){
        Stage stage = (Stage) FieldExpresion.getScene().getWindow();
        stage.close();
    }
    @FXML
    protected void ButtonEditClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Calculadora.class.getResource("Parametros.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Calculadora Científica");
        stage.setScene(scene);
        stage.show();
    }













}
