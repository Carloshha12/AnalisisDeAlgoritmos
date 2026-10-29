# Fibonacci — iterativo vs. recursivo

Análisis de Algoritmos 2026 — UNCo, Facultad de Informática.

## Enunciado

a) Implementar una solución iterativa y una recursiva en Java.

b) Comparar empíricamente los tiempos de ejecución de ambos algoritmos: registrar el tiempo
de cada solución en nanosegundos, probar varios casos y hacer una tabla comparativa.
Se puede hacer un programa de test para preconfigurar las pruebas y crear un archivo de
salida con el tiempo registrado de cada ejecución.

Sucesión de Fibonacci: F(0) = 0, F(1) = 1, F(n) = F(n−1) + F(n−2) → 0, 1, 1, 2, 3, 5, 8, 13, …

## Archivos

| Archivo | Contenido |
|---|---|
| `src/Fibonacci.java` | Las dos soluciones: `fibonacciIterativo` y `fibonacciRecursivo` |
| `src/PruebaTiempos.java` | Programa de test: mide ambas versiones con varios n y genera la tabla |
| `resultados.csv` | Tiempo de **cada** ejecución (algoritmo, n, repetición, ns, resultado) |

## Cómo ejecutarlo

```bash
javac -d out src/*.java
java -cp out Fibonacci        # muestra F(0) a F(10) con ambas versiones
java -cp out PruebaTiempos    # corre las pruebas y genera resultados.csv (tarda unos segundos)
```

Si los acentos se ven mal en la consola: `java -Dstdout.encoding=UTF-8 -cp out PruebaTiempos`.

## Análisis teórico

### Iterativo

Un ciclo de n − 1 vueltas con trabajo constante en cada una:
T(n) = c·n → **θ(n)**. Espacio: solo dos variables → **θ(1)**.

### Recursivo

Cada llamada hace dos llamadas recursivas (con n − 1 y n − 2) y trabajo constante:

```
T(n) = c₀                        si n ≤ 1
T(n) = T(n − 1) + T(n − 2) + c   si n > 1
```

La parte homogénea T(n) − T(n−1) − T(n−2) = 0 tiene ecuación característica

```
x² − x − 1 = 0   →   φ = (1 + √5)/2 ≈ 1,618    ψ = (1 − √5)/2 ≈ −0,618
```

Como |ψ| < 1, su término tiende a 0 y domina φⁿ. La constante c solo agrega un término
constante a la solución. Por lo tanto **T(n) ∈ θ(φⁿ)**: tiempo **exponencial**.

El motivo es que la recursión recalcula los mismos valores muchas veces:
para calcular F(5) se calcula F(3) dos veces, F(2) tres veces, y así.

## Cómo funciona `PruebaTiempos`

### 1. Configuración

```java
static final int[] VALORES_N = {5, 10, 15, 20, 25, 30, 35, 40};
static final int REPETICIONES = 10;
static final int CALENTAMIENTO = 200;
static final String ARCHIVO_SALIDA = "resultados.csv";
```

- `VALORES_N`: los valores de n a probar. No conviene pasar de 40–45: la versión recursiva
  tarda más de medio segundo con n = 40 y se multiplica por ≈ 1,6 con cada n siguiente.
- `REPETICIONES`: cuántas veces se mide cada caso.
- `CALENTAMIENTO`: cuántas veces se ejecuta cada versión antes de empezar a medir.

### 2. Medición de un tiempo

```java
long inicio = System.nanoTime();
resultadoRecursivo = Fibonacci.fibonacciRecursivo(n);
tiemposRecursivo[r] = System.nanoTime() - inicio;
```

Funciona como un cronómetro: lectura antes, ejecución, lectura después y resta.
El valor de `nanoTime()` por sí solo no significa nada; importa la diferencia.

### 3. Calentamiento

Java no ejecuta el código a máxima velocidad desde el principio: al arrancar lo interpreta,
y cuando detecta que un método se usa mucho lo compila a código de máquina (compilador JIT).
Por eso ambas versiones se ejecutan 200 veces **sin medir** antes de empezar.

### 4. Repeticiones y mediana

Mientras corre el programa la computadora hace otras tareas (sistema operativo, recolector
de basura, otras aplicaciones) que pueden alterar una medición aislada. Cada caso se mide
10 veces, se ordenan los tiempos y se toma **el del medio**. La mediana, a diferencia del
promedio, casi no se mueve por un valor anómalo.

### 5. Archivo de salida (como en el apunte de Wrappers y Archivos)

```java
PrintWriter flujoDeSalida = null;
try {
    flujoDeSalida = new PrintWriter(new FileOutputStream(ARCHIVO_SALIDA));   // crear y abrir el flujo
} catch (FileNotFoundException e) {
    System.out.println("No se pudo crear el archivo " + ARCHIVO_SALIDA);
    System.exit(1);
}
flujoDeSalida.println("algoritmo,n,repeticion,tiempo_ns,resultado");    // escribir
...
flujoDeSalida.close();                                                   // cerrar
```

Sigue los cuatro pasos: crear el flujo (`FileOutputStream`), relacionarlo con un escritor
(`PrintWriter`), escribir con `println` y cerrar con `close()`. La apertura va en un
`try/catch` porque puede lanzar `FileNotFoundException`. Cada línea tiene los valores
separados por comas (CSV), que se abre directamente en Excel o Google Sheets.

### 6. Tabla en consola

`printf` imprime con formato (`%-14d`: entero alineado a la izquierda en 14 caracteres;
`%.2f`: decimal con 2 cifras). La columna **Rec(n)/Rec(n−5)** divide el tiempo recursivo
de cada fila por el de la fila anterior, para comparar el crecimiento real con el teórico φ⁵.

### Flujo completo

```
abrir el archivo
calentar
para cada n:
    medir el iterativo 10 veces  → guardar cada tiempo en el CSV
    medir el recursivo 10 veces  → guardar cada tiempo en el CSV
    calcular las medianas        → imprimir una fila de la tabla
    verificar que ambas versiones den el mismo resultado
cerrar el archivo
```

## Resultados (mediana de 10 ejecuciones, en nanosegundos)

| n | Iterativo (ns) | Recursivo (ns) | Recursivo / Iterativo | Rec(n) / Rec(n−5) | F(n) |
|---:|---:|---:|---:|---:|---:|
| 5 | 121 | 160 | 1,3 | — | 5 |
| 10 | 146 | 490 | 3,4 | 3,06 | 55 |
| 15 | 126 | 3.405 | 27 | 6,95 | 610 |
| 20 | 186 | 36.407 | 196 | 10,69 | 6.765 |
| 25 | 149 | 422.767 | 2.837 | 11,61 | 75.025 |
| 30 | 158 | 4.539.936 | 28.734 | 10,74 | 832.040 |
| 35 | 175 | 51.620.974 | 294.977 | 11,37 | 9.227.465 |
| 40 | 180 | 610.203.447 | 3.390.019 | 11,82 | 102.334.155 |

Referencia teórica: φ⁵ ≈ **11,09**.

> Los valores exactos dependen de la máquina; lo que se repite es la tendencia.

## Conclusiones

1. **La versión recursiva es exponencial.** Cada vez que n aumenta en 5, su tiempo se
   multiplica por ≈ 11, que coincide con el valor teórico φ⁵ ≈ 11,09 obtenido con la
   ecuación característica. Con n = 40 tarda más de medio segundo; con n = 50 tardaría
   más de un minuto.
2. **La versión iterativa es lineal**, pero con valores de n tan chicos (≤ 40) hace tan pocas
   operaciones que el tiempo medido queda dominado por el costo fijo de la medición
   (≈ 150 ns), y por eso casi no cambia entre filas.
3. **La diferencia crece sin límite:** con n = 5 la recursiva es apenas 1,3 veces más lenta;
   con n = 40, más de 3 millones de veces. Comparar un algoritmo θ(n) con uno θ(φⁿ)
   no es una cuestión de constantes sino de orden de crecimiento.
4. Para n pequeños (casos base cercanos) la diferencia es casi nula: el análisis
   asintótico describe el comportamiento para n grande.
5. Las dos versiones dan el mismo resultado en todos los casos probados.
