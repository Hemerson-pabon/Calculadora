package org.calculadora;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class CalculadoraController {


    private String StringExpresion = "";
    private String StringTemp = "";

    // Bandera para ver si se debe resetear el field temporal
    private boolean flagStringExpresion = false;
    private boolean flagResult = false;
    boolean flagSim = false;



    private String AppendText(String string, String append){
        StringBuilder builder = new StringBuilder();
        builder.append(string);
        builder.append(append);
        return builder.toString();
    }

    // Método para asignar numeros

    private void AsignarNum(String num){
        if (flagStringExpresion){
            //StringExpresion = StringTemp;
            StringTemp = num;
        }else {
            StringTemp = AppendText(FieldTemp.getText(), num);
        }
        flagStringExpresion = false;
        FieldTemp.setText(StringTemp);
    }

    private void AsignarSim(String simbol){

        if(flagResult){
            StringExpresion = FieldTemp.getText();
            StringTemp = "";
            FieldExpresion.setText(StringExpresion);
            FieldTemp.setText(StringTemp);
            flagResult = false;
        }
        if (flagSim){
            StringExpresion = FieldExpresion.getText();
            StringTemp = FieldTemp.getText();
            StringExpresion = AppendText(StringExpresion, StringTemp);

            Map<String, BigDecimal> variables = new HashMap<>();
            ExpresionMatematica expresion = new ExpresionMatematica(StringExpresion);
            BigDecimal resultado = expresion.evaluar(variables);
            String resultadoStr = resultado.setScale(4, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
            FieldExpresion.setText(resultadoStr);
            FieldTemp.setText("");
            flagSim = false;
            System.out.println("Entro a esta bandera");
        }
        StringTemp = FieldTemp.getText();
        StringExpresion = FieldExpresion.getText();


        if (Objects.equals(StringExpresion, "")){
            StringExpresion = AppendText(StringExpresion,StringTemp);
            StringExpresion = AppendText(StringExpresion,simbol);
        }else {
            StringExpresion = AppendText(StringExpresion,simbol);
        }

        FieldExpresion.setText(StringExpresion);
        flagStringExpresion = true;

    }


    // Text field donde va la expresion a calcular

    @FXML
    private TextField FieldExpresion;
    @FXML
    private TextField FieldTemp;


    private TextField FieldOperacion;


    // Evento de click para los botones de numeros



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

    // Eventos de clíck para los botones de simbolos

    @FXML
    protected void ButtonSumClick(){
        AsignarSim("+");
        flagSim = true;
    }
    @FXML
    protected void ButtonSubClick(){
        AsignarSim("-");
        flagSim = true;
    }
    @FXML
    protected void ButtonProdClick(){
        AsignarSim("*");
        flagSim = true;
    }
    @FXML
    protected void ButtonDivClick(){
        AsignarSim("/");
        flagSim = true;
    }

    @FXML
    protected void ButtonOpenPClick(){
        AsignarNum("(");
    }
    @FXML
    protected void ButtonClosePClick(){
        AsignarNum(")");
    }


    @FXML
    protected void ButtonPointClick(){
        AsignarNum(".");
        flagSim = true;
    }

    // Boton de igual
    @FXML
    protected void ButtonResultClick(){
        if (flagSim){
            StringExpresion = FieldExpresion.getText();
            StringTemp = FieldTemp.getText();
            StringExpresion = AppendText(StringExpresion, StringTemp);

            Map<String, BigDecimal> variables = new HashMap<>();
            ExpresionMatematica expresion = new ExpresionMatematica(StringExpresion);
            BigDecimal resultado = expresion.evaluar(variables);
            String resultadoStr = resultado.setScale(4, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
            FieldExpresion.setText(resultadoStr);
            FieldTemp.setText("");
            flagSim = false;
        }

        ArrayList<String> chars = new ArrayList<>(Arrays.asList("+","-","*","/"));
        for (String cha : chars ){
            if(!StringExpresion.isEmpty() && StringExpresion.charAt(StringExpresion.length() - 1) == cha.charAt(0) ){
                StringExpresion = AppendText(StringExpresion, StringTemp);
                FieldExpresion.setText(StringExpresion);
            }
        }

        Map<String, BigDecimal> variables = new HashMap<>();
        ExpresionMatematica expresion = new ExpresionMatematica(FieldExpresion.getText());
        BigDecimal resultado = expresion.evaluar(variables);
        String resultadoStr = resultado.setScale(4, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
        FieldTemp.setText(resultadoStr);
        flagResult = true;
        flagSim = true;
    }

    // Eventos de botones de funciones hechas con series

    @FXML
    protected void ButtonCosClick(){

    }
    @FXML
    protected void ButtonSinClick(){

    }
    @FXML
    protected void ButtonSqrtClick(){

    }
    @FXML
    protected void ButtonLnClick(){

    }
    @FXML
    protected void ButtoneClick(){

    }
    @FXML
    protected void ButtonaClick(){}


    // Boton de limpiar y remover

    @FXML
    protected void ButtonClearAllClick(){
        StringExpresion = "";
        StringTemp = "";
        FieldExpresion.setText(StringExpresion);
        FieldTemp.setText(StringTemp);
        flagSim = false;
        flagResult = false;
    }

    @FXML
    protected void ButtonRemoveCharClick(){
        StringTemp = FieldTemp.getText();
        if (!StringTemp.isEmpty()) {
            StringTemp = StringTemp.substring(0, StringTemp.length() - 1);
        }
        FieldTemp.setText(StringTemp);
    }














}
