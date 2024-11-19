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
        Funciones funciones = new Funciones();

        // Intervalo y número de subdivisiones
        BigDecimal a = BigDecimal.ONE;
        BigDecimal b = BigDecimal.TWO;
        int n = 1000;

        // Expresión de la función
        String expresion = "cos(x)";

        // Calcular la integral
        BigDecimal resultado = funciones.inte(a, b, n, expresion);
        System.out.println("Resultado de la integral: " + resultado);
    }

}
