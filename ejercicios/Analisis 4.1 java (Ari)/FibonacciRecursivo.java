public class FibonacciRecursivo {
        public static void main(String[] args) {
            //Se usa long porque los números de Fibonacci crecen 
            //muy rápido y pueden superar el rango de int
        long n = 10;
        long resultado = 0;

        //Para medir el tiempo de ejecución del algoritmo en nanosegundos y milisegundos
        double startTime = System.nanoTime();
        resultado = fibonacci(n);
        double endTime = System.nanoTime();
        double tiempoNanoSegundos = endTime - startTime;

        System.out.println("Tiempo: " + tiempoNanoSegundos+" ns");
        System.out.println("Tiempo: " + tiempoNanoSegundos / 1000000 + " ms");

        System.out.println("Resultado = "+ resultado);
    }

    public static long fibonacci(long n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        // Paso Recursivo
        return fibonacci(n - 1) + fibonacci(n - 2);
    }
}
