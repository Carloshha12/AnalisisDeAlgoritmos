/**
 * Suma de todos los múltiplos de 3 o de 5 menores que un límite.
 * (Project Euler, problema 1: con límite 1000 el resultado es 233168)
 *
 * Se usa long porque para límites grandes la suma no entra en un int.
 */
public class SumaDeMultiplos {

    /**
     * Versión iterativa.
     * Recorre los números de 1 a limite - 1 y acumula los que son múltiplos de 3 o de 5.
     * Tiempo: T(n) = c · n  →  θ(n)
     */
    public static long sumaIterativa(int limite) {
        long suma = 0;
        for (int i = 1; i < limite; i++) {
            if (i % 3 == 0 || i % 5 == 0) {
                suma = suma + i;
            }
        }
        return suma;
    }

    /**
     * Versión recursiva.
     * La suma hasta n es la suma hasta n - 1, más n si n es múltiplo de 3 o de 5.
     * Para "menores que limite" se llama con n = limite - 1.
     *
     * Recurrencia del tiempo:
     *   T(0) = c0
     *   T(n) = T(n - 1) + c   si n > 0     →  θ(n)
     */
    public static long sumaRecursiva(int n) {
        long resultado;

        if (n <= 0) {
            resultado = 0;                                   // caso base
        } else {
            long aporte = 0;
            if (n % 3 == 0 || n % 5 == 0) {
                aporte = n;                                  // n es múltiplo de 3 o de 5
            }
            resultado = sumaRecursiva(n - 1) + aporte;       // caso recursivo
        }

        return resultado;
    }

    public static void main(String[] args) {
        int limite = 1000;
        System.out.println("Iterativa (límite " + limite + "): " + sumaIterativa(limite));
        System.out.println("Recursiva (límite " + limite + "): " + sumaRecursiva(limite - 1));
    }
}
