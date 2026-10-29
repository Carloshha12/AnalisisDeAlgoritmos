/**
 * Solución recursiva de la suma de múltiplos de 3 o de 5 menores que un límite,
 * escrita con una única salida (un solo return) y sin operador ternario.
 *
 * Relación de recurrencia del tiempo de ejecución:
 *   T(0) = c0
 *   T(n) = T(n - 1) + c   si n > 0      →  T(n) ∈ θ(n)
 */
public class SumaRecursiva {

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

    public static void main(String[] args) {
        // "Menores que 1000" → se llama con n = 999
        System.out.println("Suma de múltiplos de 3 o 5 menores que 1000: " + sumaMultiplosDe3o5(999));
        // Ejemplo chico para verificar a mano: 3 + 5 + 6 + 9 + 10 + 12 + 15 = 60
        System.out.println("Suma de múltiplos de 3 o 5 menores que 16:   " + sumaMultiplosDe3o5(15));
    }
}
