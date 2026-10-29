# Análisis de Algoritmos 2026

Trabajos prácticos y ejercicios de la materia **Análisis de Algoritmos**
— Universidad Nacional del Comahue, Facultad de Informática.

**Autor:** Carlos Gabriel Habiague

## Proyectos

| Tema | Proyecto | Descripción |
|---|---|---|
| Recurrencias | [suma-multiplos](recurrencias/suma-multiplos) | Suma de múltiplos de 3 o 5: versión iterativa vs. recursiva, recurrencia y comparación empírica de tiempos |
| Recurrencias | [relacion-de-recurrencia](recurrencias/relacion-de-recurrencia) | Solución recursiva con un solo `return`, cómo se obtiene su relación de recurrencia y su resolución por expansión |

## Estructura del repositorio

```
analisis-de-algoritmos/
├── README.md               ← este índice
├── .gitignore
├── _plantilla/             ← copiar esta carpeta para empezar un proyecto nuevo
└── <tema>/
    └── <proyecto>/
        ├── README.md       ← enunciado, explicación, resultados y conclusiones
        ├── src/            ← código fuente .java
        └── ...             ← archivos de salida (csv, txt)
```

Cada proyecto es independiente: tiene su propio `src/` y se compila y ejecuta desde su carpeta.

## Cómo ejecutar un proyecto

```bash
cd recurrencias/suma-multiplos
javac -d out src/*.java
java -cp out PruebaTiempos
```

## Cómo agregar un proyecto nuevo

1. Copiar `_plantilla/` dentro del tema que corresponda (crear la carpeta del tema si no existe)
   y renombrarla. Por ejemplo: `ordenamiento/burbuja-vs-insercion/`.
2. Escribir el código en `src/` y completar el `README.md` del proyecto.
3. Agregar una fila a la tabla **Proyectos** de este README.
4. Subir los cambios:

```bash
git add .
git commit -m "Agrega proyecto <nombre>"
git push
```
