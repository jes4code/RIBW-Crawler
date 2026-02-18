# RIBW Crawler

Crawler web en Java que recopila información de un conjunto de URLs y genera un diccionario de ocurrencias de términos, junto con un fichero de salida legible.  
Este proyecto forma parte de mis prácticas universitarias de **Recuperación de Información y Búsqueda en la Web**.

## Características

- Implementado en **Java** (probado con JDK 25).
- Ejecución guiada mediante un script `manager.bat`.
- Generación de:
  - `diccionario.ser`: diccionario serializado con la información procesada.
  - `salida.txt`: resumen legible con los resultados.
- Módulo de búsqueda para consultar términos específicos en el diccionario generado.

## Requisitos

- Java JDK 17 o superior (recomendado JDK 25).
- Sistema operativo Windows (para usar `manager.bat` tal cual).
- Consola/terminal (CMD o PowerShell).

## Uso

Sitúate en la carpeta del proyecto donde esté `manager.bat` y ejecuta los comandos desde la terminal.

## Ejemplo de ejecución

Cuando ejecutas `manager.bat run`, verás algo así:

```bash
Ejecutando mainCrawler...

***INSTRUCCIONES DE USO***
Este programa permitira buscar las Ocurrencias de una palabra dado un archivo diccionario.ser 
creado a partir del nombre de un directorio o fichero seleccionado.
[1] - Buscar Ocurrencias
[2] - Crear Diccionario y buscar Ocurrencias
[3] - Salir
Seleccione una opcion:
```

### Compilar
```bash
manager.bat build
```

Compila todos los archivos `.java` y genera los `.class`.  
Si hay errores de compilación, se muestran en la consola.

### Ejecutar el crawler
```bash
manager.bat run
```

Ejecuta `mainCrawler`, procesa las fuentes configuradas en el código y genera:

- `diccionario.ser`
- `salida.txt`

### Ejecutar todo (clean + build + run)
```bash
manager.bat all
```

1. Limpia archivos generados (`.class`, `diccionario.ser`, `salida.txt` si existen).  
2. Compila el proyecto.  
3. Ejecuta el crawler.

### Búsqueda de términos

Una vez generado el diccionario, puedes buscar un término concreto:

```bash
manager.bat buscar termino
```

**Ejemplos:**
```bash
manager.bat buscar "java"
manager.bat buscar "crawler"
```

### Limpiar archivos generados
```bash
manager.bat clean
```

Elimina los `.class` y los archivos de salida para dejar el proyecto limpio.

## Notas técnicas

- El proyecto está pensado como aplicación de consola, sin interfaz gráfica.
- El diccionario se guarda en formato serializado (`diccionario.ser`) para poder reutilizarlo sin tener que volver a rastrear todas las fuentes.
- `Ocurrencia.java` define la estructura de los elementos que se almacenan en el diccionario (por ejemplo, término, documento, frecuencia, etc.).
- `GestionarObjeto.java` centraliza la lógica de lectura/escritura de objetos y organización de los datos.
