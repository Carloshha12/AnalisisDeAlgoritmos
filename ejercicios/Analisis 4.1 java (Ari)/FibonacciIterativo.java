public class FibonacciIterativo {
    
    public static void main(String[] args) {
        //Se usa long porque los números de Fibonacci crecen 
        //muy rápido y pueden superar el rango de int
        long n = 10;
        long a = 0, b = 1, resultado = 0;

        double startTime = System.nanoTime();

        if (n == 0) {
            resultado = 0;
        } else if(n == 1) {
            resultado = 1;
        }else{
            for (int i = 2; i <= n; i++) {
                resultado = a + b;
                a = b;
                b = resultado;
            }
        }
// Aca se mide el tiempo de ejecución del algoritmo en nanosegundos y milisegundos
        double endTime = System.nanoTime();
        double tiempoNanoSegundos = endTime - startTime;

        System.out.println("Tiempo: " + tiempoNanoSegundos+" ns");
        System.out.println("Tiempo: " + tiempoNanoSegundos / 1000000 + " ms");

        System.out.println("Resultado = "+ resultado);
    }
}

