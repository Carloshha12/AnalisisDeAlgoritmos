import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Programa de prueba: compara empíricamente los tiempos de la versión
 * iterativa y la recursiva de Fibonacci.
 *
 * - Prueba varios valores de n.
 * - Repite cada medición varias veces y se queda con la mediana.
 * - Hace un "calentamiento" previo para que la JVM compile el código antes de medir.
 * - Guarda cada ejecución individual en resultados.csv y muestra una tabla resumen.
 */
public class PruebaTiempos {

    static final int[] VALORES_N = {5, 10, 15, 20, 25, 30, 35, 40};
    static final int REPETICIONES = 10;
    static final int CALENTAMIENTO = 200;
    static final String ARCHIVO_SALIDA = "resultados.csv";

    public static void main(String[] args) {
        PrintWriter flujoDeSalida = null;

        try {
            // 1 y 2: crear el flujo de salida y relacionarlo con un PrintWriter (abrir el archivo)
            flujoDeSalida = new PrintWriter(new FileOutputStream(ARCHIVO_SALIDA));
        } catch (FileNotFoundException e) {
            System.out.println("No se pudo crear el archivo " + ARCHIVO_SALIDA);
            System.exit(1);
        }

        calentar();

        // 3: escribir en el archivo
        flujoDeSalida.println("algoritmo,n,repeticion,tiempo_ns,resultado");

        System.out.println("Tiempos (mediana de " + REPETICIONES + " ejecuciones, en nanosegundos)");
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.printf("%-4s | %-14s | %-16s | %-12s | %-14s | %-10s%n",
                "n", "Iterativo", "Recursivo", "Rec / Iter", "Rec(n)/Rec(n-5)", "F(n)");
        System.out.println("-----------------------------------------------------------------------------------");

        long medianaRecursivaAnterior = 0;

        for (int n : VALORES_N) {
            long[] tiemposIterativo = new long[REPETICIONES];
            long[] tiemposRecursivo = new long[REPETICIONES];
            long resultadoIterativo = 0;
            long resultadoRecursivo = 0;

            for (int r = 0; r < REPETICIONES; r++) {
                long inicio = System.nanoTime();
                resultadoIterativo = Fibonacci.fibonacciIterativo(n);
                tiemposIterativo[r] = System.nanoTime() - inicio;
                flujoDeSalida.println("iterativo," + n + "," + (r + 1) + ","
                        + tiemposIterativo[r] + "," + resultadoIterativo);
            }

            for (int r = 0; r < REPETICIONES; r++) {
                long inicio = System.nanoTime();
                resultadoRecursivo = Fibonacci.fibonacciRecursivo(n);
                tiemposRecursivo[r] = System.nanoTime() - inicio;
                flujoDeSalida.println("recursivo," + n + "," + (r + 1) + ","
                        + tiemposRecursivo[r] + "," + resultadoRecursivo);
            }

            long medianaIterativo = mediana(tiemposIterativo);
            long medianaRecursivo = mediana(tiemposRecursivo);
            double recSobreIter = (double) medianaRecursivo / medianaIterativo;

            String crecimiento = "-";
            if (medianaRecursivaAnterior > 0) {
                crecimiento = String.format("%.2f", (double) medianaRecursivo / medianaRecursivaAnterior);
            }

            System.out.printf("%-4d | %-14d | %-16d | %-12.1f | %-14s | %-10d%n",
                    n, medianaIterativo, medianaRecursivo, recSobreIter, crecimiento, resultadoIterativo);

            if (resultadoIterativo != resultadoRecursivo) {
                System.out.println("  ¡ATENCIÓN! Los resultados no coinciden para n = " + n);
            }

            medianaRecursivaAnterior = medianaRecursivo;
        }

        System.out.println("-----------------------------------------------------------------------------------");
        System.out.printf("Referencia: φ^5 = %.2f (crecimiento teórico de la recursiva cada 5 valores de n)%n",
                Math.pow((1 + Math.sqrt(5)) / 2, 5));

        // 4: cerrar el flujo (se guardan los cambios en el archivo)
        flujoDeSalida.close();
        System.out.println("Detalle de cada ejecución guardado en " + ARCHIVO_SALIDA);
    }

    /** Ejecuta ambas versiones muchas veces antes de medir, para que la JVM las optimice. */
    static void calentar() {
        for (int i = 0; i < CALENTAMIENTO; i++) {
            Fibonacci.fibonacciIterativo(40);
            Fibonacci.fibonacciRecursivo(15);
        }
    }

    static long mediana(long[] valores) {
        long[] copia = Arrays.copyOf(valores, valores.length);
        Arrays.sort(copia);
        return copia[copia.length / 2];
    }
}
