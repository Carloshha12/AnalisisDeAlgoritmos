import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Programa de prueba: compara empíricamente los tiempos de la versión
 * iterativa y la recursiva de SumaDeMultiplos.
 *
 * - Prueba varios tamaños de entrada (límites).
 * - Repite cada medición varias veces y se queda con la mediana,
 *   para que un valor aislado (por ejemplo, el recolector de basura) no distorsione.
 * - Hace un "calentamiento" previo para que la JVM compile el código antes de medir.
 * - Guarda cada ejecución individual en resultados.csv y muestra una tabla resumen.
 */
public class PruebaTiempos {

    static final int[] LIMITES = {10, 100, 1_000, 5_000, 10_000, 50_000, 100_000, 1_000_000};
    static final int REPETICIONES = 30;
    static final int CALENTAMIENTO = 200;
    static final String ARCHIVO_SALIDA = "resultados.csv";

    public static void main(String[] args) throws IOException {
        calentar();

        try (PrintWriter archivo = new PrintWriter(new FileWriter(ARCHIVO_SALIDA))) {
            archivo.println("algoritmo,limite,repeticion,tiempo_ns,resultado");

            System.out.println("Tiempos (mediana de " + REPETICIONES + " ejecuciones)");
            System.out.println("-------------------------------------------------------------------------");
            System.out.printf("%-10s | %-16s | %-16s | %-10s | %-10s%n",
                    "Límite", "Iterativa (ns)", "Recursiva (ns)", "Rec / Iter", "Resultado");
            System.out.println("-------------------------------------------------------------------------");

            for (int limite : LIMITES) {
                long[] tiemposIterativa = new long[REPETICIONES];
                long[] tiemposRecursiva = new long[REPETICIONES];
                long resultadoIterativa = 0;
                long resultadoRecursiva = 0;
                boolean desbordePila = false;

                for (int r = 0; r < REPETICIONES; r++) {
                    long inicio = System.nanoTime();
                    resultadoIterativa = SumaDeMultiplos.sumaIterativa(limite);
                    tiemposIterativa[r] = System.nanoTime() - inicio;
                    archivo.println("iterativa," + limite + "," + (r + 1) + ","
                            + tiemposIterativa[r] + "," + resultadoIterativa);
                }

                for (int r = 0; r < REPETICIONES && !desbordePila; r++) {
                    try {
                        long inicio = System.nanoTime();
                        resultadoRecursiva = SumaDeMultiplos.sumaRecursiva(limite - 1);
                        tiemposRecursiva[r] = System.nanoTime() - inicio;
                        archivo.println("recursiva," + limite + "," + (r + 1) + ","
                                + tiemposRecursiva[r] + "," + resultadoRecursiva);
                    } catch (StackOverflowError e) {
                        desbordePila = true;
                        archivo.println("recursiva," + limite + "," + (r + 1) + ",,StackOverflowError");
                    }
                }

                long medianaIterativa = mediana(tiemposIterativa);

                if (desbordePila) {
                    System.out.printf("%-10d | %-16d | %-16s | %-10s | %-10d%n",
                            limite, medianaIterativa, "StackOverflow", "-", resultadoIterativa);
                } else {
                    long medianaRecursiva = mediana(tiemposRecursiva);
                    double proporcion = (double) medianaRecursiva / medianaIterativa;
                    System.out.printf("%-10d | %-16d | %-16d | %-10.2f | %-10d%n",
                            limite, medianaIterativa, medianaRecursiva, proporcion, resultadoIterativa);

                    if (resultadoIterativa != resultadoRecursiva) {
                        System.out.println("  ¡ATENCIÓN! Los resultados no coinciden para límite " + limite);
                    }
                }
            }
            System.out.println("-------------------------------------------------------------------------");
        }

        System.out.println("Detalle de cada ejecución guardado en " + ARCHIVO_SALIDA);
    }

    /** Ejecuta ambas versiones muchas veces antes de medir, para que la JVM las optimice. */
    static void calentar() {
        for (int i = 0; i < CALENTAMIENTO; i++) {
            SumaDeMultiplos.sumaIterativa(5_000);
            SumaDeMultiplos.sumaRecursiva(4_999);
        }
    }

    static long mediana(long[] valores) {
        long[] copia = Arrays.copyOf(valores, valores.length);
        Arrays.sort(copia);
        return copia[copia.length / 2];
    }
}
