/**
 * Sucesión de Fibonacci: F(0) = 0, F(1) = 1, F(n) = F(n-1) + F(n-2)
 * 0, 1, 1, 2, 3, 5, 8, 13, 21, ...
 *
 * Se usa long porque los valores crecen muy rápido
 * (con long se puede calcular hasta F(92) sin desborde).
 */
public class Fibonacci {

    /**
     * Versión iterativa.
     * Avanza guardando solo los dos últimos valores de la sucesión.
     *
     * Tiempo: un ciclo de n - 1 vueltas con trabajo constante → T(n) = c·n → θ(n)
     * Espacio: θ(1)
     */
    public static long fibonacciIterativo(int n) {
        long resultado;

        if (n <= 1) {
            resultado = n;                     // F(0) = 0, F(1) = 1
        } else {
            long anterior = 0;                 // F(i-2)
            long actual = 1;                   // F(i-1)
            for (int i = 2; i <= n; i++) {
                long siguiente = anterior + actual;
                anterior = actual;
                actual = siguiente;
            }
            resultado = actual;
        }

        return resultado;
    }

    /**
     * Versión recursiva (directa de la definición).
     *
     * Recurrencia del tiempo:
     *   T(n) = c0                       si n <= 1
     *   T(n) = T(n-1) + T(n-2) + c      si n > 1
     *
     * Ecuación característica de la parte homogénea: x² - x - 1 = 0
     * Raíces: φ = (1 + √5)/2 ≈ 1,618   y   ψ = (1 - √5)/2 ≈ -0,618
     * → T(n) ∈ θ(φⁿ)  (exponencial)
     */
    public static long fibonacciRecursivo(int n) {
        long resultado;

        if (n <= 1) {
            resultado = n;                                          // caso base
        } else {
            resultado = fibonacciRecursivo(n - 1) + fibonacciRecursivo(n - 2);  // dos llamadas
        }

        return resultado;
    }

    public static void main(String[] args) {
        for (int n = 0; n <= 10; n++) {
            System.out.println("F(" + n + ") = " + fibonacciIterativo(n)
                    + "   (recursivo: " + fibonacciRecursivo(n) + ")");
        }
    }
}
