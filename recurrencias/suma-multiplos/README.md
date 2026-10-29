# Suma de múltiplos de 3 o 5 — iterativo vs. recursivo

Análisis de Algoritmos 2026 — UNCo, Facultad de Informática.

Problema: sumar todos los múltiplos de 3 o de 5 **menores que** un límite
(Project Euler 1; con límite 1000 el resultado es **233168**).

## Archivos

| Archivo | Contenido |
|---|---|
| `src/SumaDeMultiplos.java` | Las dos soluciones: `sumaIterativa` y `sumaRecursiva` |
| `src/PruebaTiempos.java` | Programa de test: mide ambas versiones con varios límites y genera la tabla |
| `resultados.csv` | Tiempo de **cada** ejecución (algoritmo, límite, repetición, ns, resultado) |

## Cómo ejecutarlo

```bash
javac -d out src/*.java
java -cp out SumaDeMultiplos     # muestra el resultado con límite 1000
java -cp out PruebaTiempos       # corre las pruebas y genera resultados.csv
```

Si los acentos se ven mal en la consola: `java -Dfile.encoding=UTF-8 -cp out PruebaTiempos`.

## Relaciones de recurrencia (tiempo)

**Iterativa:** un ciclo de n vueltas con trabajo constante → T(n) = c·n → **θ(n)**.

**Recursiva:**

```
T(0) = c0
T(n) = T(n - 1) + c     si n > 0
```

Por expansión: T(n) = c·n + c0 → **θ(n)**.

## Metodología de medición

- Tiempo medido con `System.nanoTime()`.
- 200 ejecuciones de calentamiento antes de medir (para que la JVM compile el código con JIT).
- 30 repeticiones por caso; en la tabla se informa la **mediana**, que es menos sensible a valores aislados.
- Se verifica que ambas versiones den el mismo resultado.

## Cómo funciona `PruebaTiempos`

### 1. Configuración

```java
static final int[] LIMITES = {10, 100, 1_000, 5_000, 10_000, 50_000, 100_000, 1_000_000};
static final int REPETICIONES = 30;
static final int CALENTAMIENTO = 200;
static final String ARCHIVO_SALIDA = "resultados.csv";
```

- `LIMITES`: tamaños de entrada a probar (los `_` son solo separadores visuales: `1_000` = 1000).
- `REPETICIONES`: cuántas veces se mide cada caso.
- `CALENTAMIENTO`: cuántas veces se ejecuta cada versión antes de empezar a medir.

Para probar otros tamaños alcanza con modificar el arreglo `LIMITES`.

### 2. Medición de un tiempo

```java
long inicio = System.nanoTime();
resultadoIterativa = SumaDeMultiplos.sumaIterativa(limite);
tiemposIterativa[r] = System.nanoTime() - inicio;
```

Funciona como un cronómetro: se toma una lectura antes, se ejecuta el algoritmo, se toma
otra lectura después y se restan. El valor de `nanoTime()` por sí solo no significa nada;
lo que importa es la diferencia entre las dos lecturas.

### 3. Calentamiento

Java no ejecuta el código a máxima velocidad desde el principio: al arrancar lo interpreta,
y cuando detecta que un método se usa mucho lo compila a código de máquina (compilador JIT).
Si se midiera sin calentar, las primeras mediciones serían las del código lento.
Por eso se ejecutan ambas versiones 200 veces **sin medir** antes de empezar.
En `resultados.csv` se ve el efecto: la primera ejecución con límite 10 tardó 681 ns y la cuarta 181 ns.

### 4. Repeticiones y mediana

Mientras corre el programa, la computadora hace otras tareas (sistema operativo, recolector
de basura de Java, otras aplicaciones) que pueden meter un salto en una medición aislada.
Por eso cada caso se mide 30 veces, se ordenan los tiempos y se toma **el del medio**.
Se usa la mediana y no el promedio porque un único valor anómalo arrastra el promedio,
pero casi no mueve la mediana.

### 5. Manejo del desborde de pila

```java
try {
    ...sumaRecursiva(limite - 1)...
} catch (StackOverflowError e) {
    desbordePila = true;
    ...
}
```

Con límites grandes, la versión recursiva hace tantas llamadas que se queda sin pila.
El `catch` evita que el programa se corte: registra el desborde en el CSV, deja de intentar
ese límite y continúa con el siguiente.

### 6. Archivo de salida

```java
try (PrintWriter archivo = new PrintWriter(new FileWriter(ARCHIVO_SALIDA))) {
    archivo.println("algoritmo,limite,repeticion,tiempo_ns,resultado");
    ...
}
```

- `FileWriter` abre el archivo y `PrintWriter` permite escribir con `println`, como en consola.
- El `try (...)` (try-with-resources) cierra el archivo automáticamente al terminar, aunque ocurra un error.
- Cada línea tiene sus valores separados por comas (formato CSV), que se puede abrir en Excel o Google Sheets.

### 7. Tabla en consola

`printf` imprime con formato: `%-10d` es un entero alineado a la izquierda en 10 caracteres,
y `%.2f` un decimal con 2 cifras. Así las columnas quedan alineadas.

### Flujo completo

```
calentar
para cada límite:
    medir la iterativa 30 veces  → guardar cada tiempo en el CSV
    medir la recursiva 30 veces  → guardar cada tiempo en el CSV (o StackOverflowError)
    calcular las medianas        → imprimir una fila de la tabla
    verificar que ambas versiones den el mismo resultado
```

## Resultados (mediana de 30 ejecuciones, en nanosegundos)

| Límite | Iterativa (ns) | Recursiva (ns) | Recursiva / Iterativa |
|---:|---:|---:|---:|
| 10 | 176 | 200 | 1,14 |
| 100 | 260 | 616 | 2,37 |
| 1.000 | 1.323 | 7.100 | 5,37 |
| 5.000 | 4.661 | 36.885 | 7,91 |
| 10.000 | 9.109 | 74.598 | 8,19 |
| 50.000 | 46.549 | StackOverflowError | — |
| 100.000 | 92.764 | StackOverflowError | — |
| 1.000.000 | 985.979 | StackOverflowError | — |

> Los valores exactos dependen de la máquina; lo que se repite es la tendencia.

## Conclusiones

1. **Ambas son θ(n):** al multiplicar el límite por 10, el tiempo de las dos crece
   aproximadamente 10 veces (por ejemplo, la iterativa pasa de 9.109 ns con 10.000
   a 92.764 ns con 100.000).
2. **Mismo orden, distinta constante:** la recursiva es entre 5 y 8 veces más lenta
   para entradas medianas. Cada llamada tiene un costo extra (crear el marco en la
   pila, pasar parámetros, retornar) que el `for` no tiene. El análisis asintótico
   no muestra esa diferencia porque queda escondida en la constante `c`.
3. **Límite práctico de la recursión:** cada llamada ocupa memoria en la pila, así
   que el espacio también es θ(n). Con el tamaño de pila por defecto de la JVM, a
   partir de unas decenas de miles de llamadas se produce `StackOverflowError`.
   La versión iterativa usa espacio θ(1) y no tiene ese problema.
4. Para entradas muy chicas (límite 10) la diferencia es casi nula, porque domina
   el costo fijo de la medición.
