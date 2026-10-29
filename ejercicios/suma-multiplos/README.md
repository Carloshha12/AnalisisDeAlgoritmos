# Suma de múltiplos de 3 o 5 — solución recursiva y relación de recurrencia

Análisis de Algoritmos 2026 — UNCo, Facultad de Informática.

## Enunciado

Resolver de forma recursiva el problema y expresar la relación de recurrencia
asociada a la solución recursiva obtenida.

Problema: sumar todos los múltiplos de 3 o de 5 **menores que** 1000
(Project Euler, problema 1). Resultado: **233168**.

## Cómo ejecutarlo

```bash
javac -d out src/*.java
java -cp out SumaDeMultiplos
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

```
T(n) = c₀              si n = 0
T(n) = T(n − 1) + c    si n > 0
```

### Resolución por expansión

```
T(n) = T(n−1) + c = T(n−2) + 2c = … = T(0) + n·c = c₀ + n·c
```

Por lo tanto **T(n) ∈ θ(n)**.

## Otras versiones (incluidas en el mismo archivo)

### Con ciclo — θ(n)

Traducción a Java de la versión original en Python: recorre los números menores
que el límite y suma los múltiplos de 3 o de 5. Un número como el 15 se suma una
sola vez, porque el `if` es una única decisión.

### Con fórmula — θ(1)

1. **Cantidad de múltiplos:** `999 / 3 = 333` (división entera). Ídem para 5 (199) y 15 (66).
2. **Suma sin recorrerlos**, con Gauss 1 + 2 + … + k = k(k+1)/2:
   - múltiplos de 3: 3 · 333·334/2 = 166833
   - múltiplos de 5: 5 · 199·200/2 = 99500
   - múltiplos de 15: 15 · 66·67/2 = 33165
3. **Inclusión-exclusión:** los múltiplos de 15 están en las dos primeras sumas,
   así que se resta su suma una vez: 166833 + 99500 − 33165 = **233168**.

Se usa `limite - 1` porque el enunciado pide los múltiplos **menores que** 1000;
como 1000 es múltiplo de 5, incluirlo daría 234168.

## Comparación

| Versión | Orden de tiempo | Espacio |
|---|---|---|
| Recursiva | θ(n) | θ(n) (una llamada apilada por cada n) |
| Con ciclo | θ(n) | θ(1) |
| Con fórmula | θ(1) | θ(1) |
