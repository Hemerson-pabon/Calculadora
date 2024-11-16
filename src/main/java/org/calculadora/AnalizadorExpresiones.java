package org.calculadora;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class AnalizadorExpresiones {
    private static final Map<String, BiFunction<Double, Double, Double>> OPERADORES = new HashMap<>();
    private static final Map<String, Function<Double, Double>> FUNCIONES = new HashMap<>();
    private static final funciones FP = new funciones();

    static {
        // Definir operadores básicos
        OPERADORES.put("+", (a, b) -> a + b);
        OPERADORES.put("-", (a, b) -> a - b);
        OPERADORES.put("*", (a, b) -> a * b);
        OPERADORES.put("/", (a, b) -> a / b);

        // Definir funciones matemáticas usando las funciones propias de la clase `funciones`
        FUNCIONES.put("sin", (x) -> FP.sin(BigDecimal.valueOf(x), 50).doubleValue());
        FUNCIONES.put("cos", (x) -> FP.cos(BigDecimal.valueOf(x), 50).doubleValue());
        FUNCIONES.put("sqrt", (x) -> FP.sqrt(BigDecimal.valueOf(x)).doubleValue());
        FUNCIONES.put("exp", (x) -> FP.e(BigDecimal.valueOf(x), 50).doubleValue());
        FUNCIONES.put("ln", (x) -> FP.ln(BigDecimal.valueOf(x), 50).doubleValue());
    }

    public static double evaluarExpresion(String expresion, Map<String, Double> variables) {
        // Eliminar espacios y convertir a minúsculas para uniformidad
        expresion = expresion.replaceAll("\\s+", "").toLowerCase();

        try {
            return evaluarRecursivo(expresion, variables);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al evaluar la expresión: " + e.getMessage());
        }
    }

    private static double evaluarRecursivo(String expresion, Map<String, Double> variables) {
        // Eliminar paréntesis externos si existen
        expresion = eliminarParentesisExternos(expresion);

        // Evaluar funciones matemáticas primero
        for (String nombreFuncion : FUNCIONES.keySet()) {
            if (expresion.startsWith(nombreFuncion + "(")) {
                int finParentesis = encontrarParentesisCierre(expresion, nombreFuncion.length());
                String argumentoStr = expresion.substring(nombreFuncion.length() + 1, finParentesis);

                // Evaluar el argumento de forma recursiva
                double argumento = evaluarRecursivo(argumentoStr, variables);

                // Verificar si hay más operaciones después de la función
                String restoExpresion = finParentesis < expresion.length() - 1 ?
                        expresion.substring(finParentesis + 1) : "";

                if (restoExpresion.isEmpty()) {
                    // Si no hay más operaciones, devolver el resultado de la función
                    return FUNCIONES.get(nombreFuncion).apply(argumento);
                } else {
                    // Si hay más operaciones, evaluar el resto de la expresión
                    // Convertir el resultado de la función a string y concatenar con el resto
                    expresion = String.valueOf(FUNCIONES.get(nombreFuncion).apply(argumento)) + restoExpresion;
                }
            }
        }

        // Buscar operadores de menor precedencia primero (suma y resta)
        for (int i = expresion.length() - 1; i >= 0; i--) {
            char c = expresion.charAt(i);
            if (c == '+' || c == '-') {
                if (i == 0 || expresion.charAt(i - 1) == '(' || expresion.charAt(i - 1) == '^' ||
                        expresion.charAt(i - 1) == '*' || expresion.charAt(i - 1) == '/') {
                    continue; // Es un signo unario
                }
                String izquierda = expresion.substring(0, i);
                String derecha = expresion.substring(i + 1);
                BiFunction<Double, Double, Double> operador = OPERADORES.get(String.valueOf(c));
                return operador.apply(
                        evaluarRecursivo(izquierda, variables),
                        evaluarRecursivo(derecha, variables)
                );
            }
        }

        // Buscar operadores de multiplicación y división
        for (int i = expresion.length() - 1; i >= 0; i--) {
            char c = expresion.charAt(i);
            if (c == '*' || c == '/') {
                String izquierda = expresion.substring(0, i);
                String derecha = expresion.substring(i + 1);
                BiFunction<Double, Double, Double> operador = OPERADORES.get(String.valueOf(c));
                return operador.apply(
                        evaluarRecursivo(izquierda, variables),
                        evaluarRecursivo(derecha, variables)
                );
            }
        }

        // Buscar operador de potencia
        for (int i = 0; i < expresion.length(); i++) {
            if (expresion.charAt(i) == '^') {
                String base = expresion.substring(0, i);
                String exponente = expresion.substring(i + 1);
                return FP.a_elevado(
                        BigDecimal.valueOf(evaluarRecursivo(base, variables)),
                        BigDecimal.valueOf(evaluarRecursivo(exponente, variables)),
                        50
                ).doubleValue();
            }
        }

        // Evaluar números y variables
        if (expresion.matches("-?\\d+(\\.\\d+)?")) {
            return Double.parseDouble(expresion);
        } else if (variables.containsKey(expresion)) {
            return variables.get(expresion);
        } else {
            throw new IllegalArgumentException("Variable no definida o expresión inválida: " + expresion);
        }
    }

    private static String eliminarParentesisExternos(String expresion) {
        while (expresion.startsWith("(") && expresion.endsWith(")")) {
            int finParentesis = encontrarParentesisCierre(expresion, 0);
            if (finParentesis == expresion.length() - 1) {
                expresion = expresion.substring(1, expresion.length() - 1);
            } else {
                break;
            }
        }
        return expresion;
    }

    private static int encontrarParentesisCierre(String expresion, int inicio) {
        int contador = 0;
        for (int i = inicio; i < expresion.length(); i++) {
            if (expresion.charAt(i) == '(') {
                contador++;
            }
            if (expresion.charAt(i) == ')') {
                contador--;
                if (contador == 0) {
                    return i;
                }
            }
        }
        throw new IllegalArgumentException("Paréntesis no balanceados");
    }
}