package org.calculadora;

import java.math.BigDecimal;
import java.util.Map;

// Clase principal para usar el analizador
public class ExpresionMatematica {
    private String expresion;

    public ExpresionMatematica(String expresion) {
        this.expresion = expresion;
    }

    public BigDecimal evaluar(Map<String, BigDecimal> variables) {
        return AnalizadorExpresiones.evaluarExpresion(expresion, variables);
    }

    public String getExpresion() {
        return expresion;
    }

    @Override
    public String toString() {
        return expresion;
    }
}
