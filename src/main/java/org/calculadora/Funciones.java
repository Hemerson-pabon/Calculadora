package org.calculadora;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;


public class Funciones {

    //MathContext mc = new MathContext(200, RoundingMode.UP);
    MathContext mc = MathContext.DECIMAL128;

    public void setMc(MathContext mc) {
        this.mc = mc;
    }

    public BigDecimal factorial(int x){
        BigDecimal result = BigDecimal.ONE;
        for (int i = 1; i <= x; i++){
            result = result.multiply(BigDecimal.valueOf(i), mc);
        }
        return result;
    }

    public BigDecimal lnF(BigDecimal x) {
        // Validar entrada
        if (x.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ArithmeticException("Logaritmo natural no definido para números menores o iguales a 0");
        }

        // Configurar contexto matemático

        // Si x = 1, ln(x) = 0
        if (x.compareTo(BigDecimal.ONE) == 0) {
            return BigDecimal.ZERO;
        }

        // Para valores cercanos a 1, usar serie de Taylor directamente
        if (x.subtract(BigDecimal.ONE).abs().compareTo(new BigDecimal("0.5")) <= 0) {
            return lnTaylorSeries(x.subtract(BigDecimal.ONE), mc);
        }

        // Para otros valores, usar la transformación ln(x) = ln(a * 10^n) = ln(a) + n*ln(10)
        int exponent = 0;
        BigDecimal normalized = x;

        // Normalizar el número entre 0.1 y 1
        while (normalized.compareTo(BigDecimal.ONE) >= 0) {
            normalized = normalized.divide(BigDecimal.TEN, mc);
            exponent++;
        }
        while (normalized.compareTo(new BigDecimal("0.1")) < 0) {
            normalized = normalized.multiply(BigDecimal.TEN);
            exponent--;
        }

        // Calcular ln(normalized) usando serie de Taylor
        BigDecimal lnNormalized = lnTaylorSeries(normalized.subtract(BigDecimal.ONE), mc);

        // Añadir el término n*ln(10)
        BigDecimal ln10 = new BigDecimal("2.302585092994045684017991454684364207601101488628772976033327900967572609677352480235997205089598298341967784042286248633409525465082806756666287369098781689482907208325554680843799894826233198528393505308965369442489048785324288416170039");
        return lnNormalized.add(ln10.multiply(new BigDecimal(exponent), mc), mc);
    }

    private static BigDecimal lnTaylorSeries(BigDecimal x, MathContext mc) {
        BigDecimal result = BigDecimal.ZERO;
        BigDecimal term = x;
        BigDecimal power = x;
        int n = 1;

        // Sumar términos hasta alcanzar la precisión deseada
        while (term.abs().compareTo(new BigDecimal("1E-" + mc.getPrecision())) > 0) {
            result = result.add(term, mc);
            n++;
            power = power.multiply(x, mc);
            term = power.divide(new BigDecimal(n), mc);
            term = term.multiply(new BigDecimal((n % 2 == 0) ? -1 : 1), mc);
        }

        return result;
    }



    /*
    FUNCIONES DE LA TAREA
    */

    public BigDecimal e(BigDecimal x, int n){
        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal term;
        for (int i = 0; i < n; i++){
            term = (x.pow(i)).divide(factorial(i), mc);
            sum = sum.add(term);
        }
        return sum;
    }

    public BigDecimal sin(BigDecimal x, int n){
        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal term;
        for (int i = 0; i < n; i++){
            term = (BigDecimal.valueOf(-1).pow(i)).multiply((x.pow((2*i)+1)).divide(factorial((2*i)+1), mc));
            sum = sum.add(term);
        }
        return sum;
    }
    public BigDecimal cos(BigDecimal x, int n){
        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal term;

        for (int i = 0; i < n; i++){
            term = (BigDecimal.valueOf(-1).pow(i).multiply((x.pow(2*i)).divide(factorial((2*i)), mc)));
            sum = sum.add(term);
        }
        return sum;
    }

    public BigDecimal ln(BigDecimal x, int n){
        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal term;
        for (int i = 1; i < n; i++){
            term = (BigDecimal.valueOf(-1).pow(i+1)).multiply( ((x.subtract(BigDecimal.ONE)).pow(i)).divide(BigDecimal.valueOf(i), mc));
            sum = sum.add(term);
        }
        return sum;
    }

    public BigDecimal a_elevado(BigDecimal a ,BigDecimal x, int n){
        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal term;
        for (int i = 0; i < n; i++){
            term = ((lnF(a).pow(i,mc)).divide(factorial(i), mc)).multiply(x.pow(i,mc),mc);
            sum = sum.add(term);
        }
        return sum;
    }

    public BigDecimal sqrt(BigDecimal x) {
        if (x.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ArithmeticException("La raíz cuadrada no está definida para números negativos");
        }

        // Caso especial x = 0 o x =
        if (x.compareTo(BigDecimal.ZERO) == 0 || x.compareTo(BigDecimal.ONE) == 0) {
            return x;
        }
        int precision = 128;
        BigDecimal TWO = new BigDecimal(2);

        // Normalizar x a un número entre 1 y 100
        int scale = 0;
        BigDecimal normalizedX = x;

        while (normalizedX.compareTo(new BigDecimal("100")) >= 0) {
            normalizedX = normalizedX.movePointLeft(2);
            scale++;
        }
        while (normalizedX.compareTo(BigDecimal.ONE) < 0) {
            normalizedX = normalizedX.movePointRight(2);
            scale--;
        }

        // Aproximación inicial
        BigDecimal guess = BigDecimal.valueOf(Math.sqrt(normalizedX.doubleValue()));

        // Iteración de Newton
        BigDecimal lastGuess;
        do {
            lastGuess = guess;
            // guess = (guess + normalizedX/guess) / 2
            guess = guess.add(normalizedX.divide(guess, mc)).divide(TWO, mc);
        } while (guess.subtract(lastGuess).abs().compareTo(
                new BigDecimal("1E-" + precision)) > 0);

        // Ajustar la escala
        if (scale != 0) {
            guess = guess.multiply(BigDecimal.TEN.pow(scale, mc));
        }

        // Ajustar a la precisión deseada
        return guess.round(new MathContext(precision, RoundingMode.HALF_UP));
    }

    public BigDecimal inte(BigDecimal a, BigDecimal b, int n, String expresion) {
        // Convertir los límites a double
        double aDouble = a.doubleValue();
        double bDouble = b.doubleValue();

        // Calcular el tamaño del subintervalo
        double h = (bDouble - aDouble) / n;

        // Preparar la expresión con Exp4j
        Expression expression = new ExpressionBuilder(expresion)
                .variable("x") // Variable a evaluar
                .build();

        // Inicializar la suma
        double suma = 0.0;

        try {
            // Evaluar los extremos
            expression.setVariable("x", aDouble);
            suma += expression.evaluate() / 2.0;

            expression.setVariable("x", bDouble);
            suma += expression.evaluate() / 2.0;

            // Evaluar los puntos intermedios
            for (int i = 1; i < n; i++) {
                double xi = aDouble + i * h;
                expression.setVariable("x", xi);
                suma += expression.evaluate();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al evaluar la expresión: " + e.getMessage(), e);
        }

        // Convertir el resultado a BigDecimal antes de devolverlo
        return BigDecimal.valueOf(suma * h);
    }


}

