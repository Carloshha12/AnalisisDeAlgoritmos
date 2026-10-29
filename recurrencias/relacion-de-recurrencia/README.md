# Relación de recurrencia de una solución recursiva

Análisis de Algoritmos 2026 — UNCo, Facultad de Informática.

## Enunciado

Resolver de forma recursiva el problema y expresar la relación de recurrencia
asociada a la solución recursiva obtenida.

Problema: sumar todos los múltiplos de 3 o de 5 **menores que** un límite
(con límite 1000 el resultado es 233168).

## Cómo ejecutarlo

```bash
javac -d out src/*.java
java -cp out SumaRecursiva
```

## Solución recursiva

La suma hasta n es la suma hasta n − 1, más n si n es múltiplo de 3 o de 5.

```java
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
```

Para "menores que 1000" se llama con `n = 999`.

## Relación de recurrencia

Se define **T(n)** como la cantidad de operaciones que realiza `sumaMultiplosDe3o5(n)`.

- **Caso base (n = 0):** una comparación, una asignación y el `return`. Es una cantidad fija de operaciones: **c₀**.
- **Caso recursivo (n > 0):**
  - Comparaciones, módulos, asignaciones y la suma: una cantidad fija de operaciones, **c**.
  - Una llamada recursiva con tamaño n − 1: **T(n − 1)**.

```
T(n) = c₀              si n = 0
T(n) = T(n − 1) + c    si n > 0
```

## Resolución por expansión

```
T(n) = T(n−1) + c
     = T(n−2) + 2c
     = T(n−3) + 3c
     = …
     = T(0) + n·c
     = c₀ + n·c
```

Por lo tanto **T(n) ∈ θ(n)**.

## Receta general para este tipo de ejercicio

1. Mirar el **caso base**: da T(caso base) = constante.
2. Contar **cuántas llamadas recursivas** hay y **con qué tamaño**: cada una aporta un término T(n−1), T(n−2), T(n/2), etc.
3. Ver cuánto cuesta **el resto del trabajo** en cada llamada: una constante c, un ciclo n, dos ciclos anidados n², etc.
4. Sumar todo: **T(n) = (llamadas recursivas) + (trabajo propio)**.

Ejemplo: Fibonacci recursivo hace 2 llamadas (con n−1 y n−2) y trabajo constante,
por lo tanto T(n) = T(n−1) + T(n−2) + c.
