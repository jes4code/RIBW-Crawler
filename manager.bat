@echo off
setlocal

:: Definir nombres de archivos generados
set OUTPUT_FILES=diccionario.ser salida.txt
set CLASS_FILES=*.class

if "%1"=="" (
    echo Uso: compilar.bat [build^|run^|clean^|all]
    exit /b
)

:: Opción para compilar
if "%1"=="build" (
    echo Compilando archivos Java...
    javac mainCrawler.java GestionarObjeto.java buscador.java Ocurrencia.java
    if %ERRORLEVEL% NEQ 0 (
        echo Error en la compilacion.
        pause
        exit /b
    )
    echo Compilacion exitosa.
    exit /b
)

:: Opción para ejecutar
if "%1"=="run" (
    echo Ejecutando mainCrawler...
    java mainCrawler
    exit /b
)


if "%1"=="buscar" (
    echo Ejecutando buscador con argumento: %2
    java buscador %2
    exit /b
)


:: Opción para limpiar archivos generados
if "%1"=="clean" (
    echo Limpiando archivos...
    del /Q %CLASS_FILES% %OUTPUT_FILES%
    echo Limpieza completada.
    exit /b
)

:: Opción para compilar, limpiar y ejecutar todo
if "%1"=="all" (
    call %0 clean
    call %0 build
    call %0 run
    exit /b
)

:: Mensaje si la opción no es válida
echo Opción no reconocida. Usa: build, run, clean o all.
exit /b


