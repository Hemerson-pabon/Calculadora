package org.calculadora;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;
import java.math.BigDecimal;

public class ExpresionMatematica {

    private static int n = 1000; // Número de divisiones por defecto
    private String expresion;

    // Constructor
    public ExpresionMatematica(String expresion) {
        this.expresion = expresion;
    }

    // Métodos para configurar 'n'
    public static void setN(int n) {
        ExpresionMatematica.n = n;
    }

    public static int getN() {
        return n;
    }

    // Instancia de Funciones matemáticas
    private static final Funciones funciones = new Funciones();

    // Evaluar una expresión directamente o con la función 'inte'
    public BigDecimal evaluar() {
        Expression expression = new ExpressionBuilder(expresion)
                .function(sin)
                .function(cos)
                .function(exp)
                .function(ln)
                .function(pow)
                .function(sqrt)
                .function(inte)
                .variables("e", "π")
                .build()
                .setVariable("e", 2.718281828459045)
                .setVariable("π", 3.141592653589793);

        double result = expression.evaluate();
        return BigDecimal.valueOf(result);
    }

    // Funciones personalizadas

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
            return funciones.a_elevado(BigDecimal.valueOf(args[0]), BigDecimal.valueOf(args[1]), n).doubleValue();
        }
    };

    Function sqrt = new Function("sqrt", 1) {
        @Override
        public double apply(double... args) {
            return funciones.sqrt(BigDecimal.valueOf(args[0])).doubleValue();
        }
    };

    Function inte = new Function("integ", 2) {
        @Override
        public double apply(double... args) {
            BigDecimal a = BigDecimal.valueOf(args[0]);
            BigDecimal b = BigDecimal.valueOf(args[1]);
            String expresion = "e^((-x^2)/2)";
            return funciones.inte(a, b, n, expresion).doubleValue();
        }
    };

}
