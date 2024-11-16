package org.calculadora;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class main {


    /*
    public static void main(String[] args) {
        funciones f1 = new funciones();
        MathContext mc = MathContext.DECIMAL128;
        //MathContext mc = new MathContext(200, RoundingMode.UP);
        //System.out.println("Valor igual a: " + f1.e(BigDecimal.valueOf(2), 10000));
        //System.out.println("Valor igual a: " + f1.factorial(200));
        //System.out.println("Valor de cos(x) igual a: " + f1.cos(BigDecimal.valueOf(1), 100));
        //System.out.println("El valor de ln de 1 es:" + f1.ln(BigDecimal.TWO, 1000000));
        //System.out.println(funciones.ln1(BigDecimal.valueOf(9), mc));
        //System.out.println(f1.a_elevado(BigDecimal.valueOf(2),BigDecimal.valueOf(3), 100));
        //System.out.println(f1.sqrt(BigDecimal.valueOf(2)));
        //System.out.println(f1.a_elevado(BigDecimal.valueOf(2), BigDecimal.valueOf(1000), 1000));
    }

     */




    public static void main(String[] args) {
        Map<String, Double> variables = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nIngrese una expresión matemática (ejemplo: sin(x) + cos(x)):");
        String expresionUsuario = scanner.nextLine();

        try {
            ExpresionMatematica expresion = new ExpresionMatematica(expresionUsuario);
            // System.out.println("Ingrese el valor de x:");
            // double x = scanner.nextDouble();
            // variables.put("x", x);

            double resultado = expresion.evaluar(variables);
            System.out.printf("Resultado: %.4f%n", resultado);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }




}
