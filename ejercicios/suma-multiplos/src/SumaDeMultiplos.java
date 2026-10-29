/**
 * Suma de todos los múltiplos de 3 o de 5 menores que un límite.
 * (Project Euler, problema 1: con límite 1000 el resultado es 233168)
 */
public class SumaDeMultiplos {

    /**
     * Versión recursiva (un solo return, sin operador ternario).
     * La suma hasta n es la suma hasta n - 1, más n si n es múltiplo de 3 o de 5.
     * Para "menores que 1000" se llama con n = 999.
     */
    public static int sumaMultiplosDe3o5(int n) {
        int resultado;

        if (n == 0) {
            resultado = 0;                                   // caso base
        } else {
            int aporte = 0;
            if (n % 3 == 0 || n % 5 == 0) {
                aporte = n;                                  // n es múltiplo de 3 o de 5
            }
            resultado = sumaMultiplosDe3o5(n - 1) + aporte;  // caso recursivo
        }

        return resultado;
    }

    /**
     * Versión con ciclo (traducida de la versión naive en Python).
     * Orden: θ(n)
     */
    public static int sumaConCiclo(int limite) {
        int sumaDeMultiplos = 0;
        for (int i = 0; i < limite; i++) {
            if (i % 3 == 0 || i % 5 == 0) {
                sumaDeMultiplos += i;
            }
        }
        return sumaDeMultiplos;
    }

    /**
     * Versión con fórmula (suma de Gauss + inclusión-exclusión).
     * Orden: θ(1)
     */
    public static int sumaConFormula(int limite) {
        int ultimoNumero = limite - 1;   // "menores que limite", no se incluye el límite

        int cantidadMultiplosDe3  = ultimoNumero / 3;
        int cantidadMultiplosDe5  = ultimoNumero / 5;
        int cantidadMultiplosDe15 = ultimoNumero / 15;

        // Fórmula de Gauss: 1 + 2 + ... + k = k(k+1)/2
        int sumaMultiplosDe3  = 3  * cantidadMultiplosDe3  * (cantidadMultiplosDe3  + 1) / 2;
        int sumaMultiplosDe5  = 5  * cantidadMultiplosDe5  * (cantidadMultiplosDe5  + 1) / 2;
        int sumaMultiplosDe15 = 15 * cantidadMultiplosDe15 * (cantidadMultiplosDe15 + 1) / 2;

        // Los múltiplos de 15 están en las dos sumas: se resta una copia
        return sumaMultiplosDe3 + sumaMultiplosDe5 - sumaMultiplosDe15;
    }

    public static void main(String[] args) {
        System.out.println("Recursiva   (menores que 1000): " + sumaMultiplosDe3o5(999)); // 233168
        System.out.println("Con ciclo   (menores que 1000): " + sumaConCiclo(1000));      // 233168
        System.out.println("Con fórmula (menores que 1000): " + sumaConFormula(1000));    // 233168
        System.out.println("Recursiva   (menores que 16):   " + sumaMultiplosDe3o5(15));  // 60
    }
}
