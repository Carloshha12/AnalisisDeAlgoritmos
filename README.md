# Análisis de Algoritmos 2026

Trabajos prácticos y ejercicios de la materia **Análisis de Algoritmos**
— Universidad Nacional del Comahue, Facultad de Informática.

**Autor:** Carlos Gabriel Habiague

## Ejercicios

| Proyecto | Descripción |
|---|---|
| [fibonacci-tiempos](ejercicios/fibonacci-tiempos) | Fibonacci iterativo vs. recursivo: recurrencias, ecuación característica y comparación empírica de tiempos con archivo CSV |
| [suma-multiplos](ejercicios/suma-multiplos) | Suma de múltiplos de 3 o 5: solución recursiva y su relación de recurrencia (más versiones con ciclo y con fórmula) |

## Estructura del repositorio

```
analisis-de-algoritmos/
├── README.md               ← este índice
├── .gitignore
├── _plantilla/             ← copiar esta carpeta para empezar un proyecto nuevo
└── ejercicios/
    └── <proyecto>/
        ├── README.md       ← enunciado, explicación, resultados y conclusiones
        ├── src/            ← código fuente .java
        └── ...             ← archivos de salida (csv, txt)
```

Cada proyecto es independiente: tiene su propio `src/` y se compila y ejecuta desde su carpeta.

## Cómo ejecutar un proyecto

```bash
cd ejercicios/fibonacci-tiempos
javac -d out src/*.java
java -cp out PruebaTiempos
```

## Cómo agregar un proyecto nuevo

1. Copiar `_plantilla/` dentro de `ejercicios/` y renombrarla. Por ejemplo: `ejercicios/burbuja-vs-insercion/`.
2. Escribir el código en `src/` y completar el `README.md` del proyecto.
3. Agregar una fila a la tabla **Ejercicios** de este README.
4. Subir los cambios:

```bash
git add .
git commit -m "Agrega proyecto <nombre>"
git push
```
