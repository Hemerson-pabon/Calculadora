package org.calculadora;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;
import java.math.BigDecimal;

// Clase principal para usar el analizador
public class ExpresionMatematica {

    private static int n = 1000;
    private String expresion;

    // Set para el valor de n de las series de potencias
    public static void setN(int n) {
        ExpresionMatematica.n = n;
    }

    public static int getN() {
        return n;
    }

    private static final Funciones funciones = new Funciones();

    ExpresionMatematica(String expresion){
        this.expresion = expresion;
    }



    public BigDecimal evaluar(){
        Expression expression = new ExpressionBuilder(expresion)
                .function(sin)
                .function(cos)
                .function(exp)
                .function(ln)
                .function(pow)
                .function(sqrt)
                .build()
                .setVariable("e", 2.718281828459045)
                .setVariable("π", 3.141592653589793);
        double result = expression.evaluate();
        return BigDecimal.valueOf(result);
    }

    // Crear las funciones personalizadas

    Function sin = new Function("sin", 1) {
        @Override
        public double apply(double... args) {
            return funciones.sin(BigDecimal.valueOf(args[0]), n).doubleValue();
        }
    };

    Function cos = new Function("cos", 1) {
        @Override
        public double apply(double... args) {
            return funciones.cos(BigDecimal.valueOf(args[0]), n).doubleValue();
        }
    };

    Function exp = new Function("exp", 1) {
        @Override
        public double apply(double... args) {
            return funciones.e(BigDecimal.valueOf(args[0]), n).doubleValue();
        }
    };

    Function ln = new Function("ln", 1) {
        @Override
        public double apply(double... args) {
            return funciones.lnF(BigDecimal.valueOf(args[0])).doubleValue();
        }
    };

    Function pow = new Function("pow", 2) {
        @Override
        public double apply(double... args) {
            return funciones.a_elevado(BigDecimal.valueOf(args[0]), BigDecimal.valueOf(args[1]), n ).doubleValue();
        }
    };

    Function sqrt = new Function("sqrt", 1) {
        @Override
        public double apply(double... args) {
            return funciones.sqrt(BigDecimal.valueOf(args[0])).doubleValue();
        }
    };


}
