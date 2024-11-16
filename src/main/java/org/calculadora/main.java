package org.calculadora;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class main {

    private static final int DECIMAL_PLACES = 10; // Cantidad de decimales a mostrar/calcular

    public static void main(String[] args) {
        Map<String, BigDecimal> variables = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nIngrese una expresión matemática (ejemplo: sin(x) + cos(x)):");
        String expresionUsuario = scanner.nextLine();

        try {
            ExpresionMatematica expresion = new ExpresionMatematica(expresionUsuario);
            BigDecimal resultado = expresion.evaluar(variables);

            // Formato personalizado para mostrar más decimales
            String resultadoStr = resultado.setScale(DECIMAL_PLACES, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
            System.out.println("Resultado: " + resultadoStr);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


}
