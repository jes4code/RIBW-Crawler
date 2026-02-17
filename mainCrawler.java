import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class mainCrawler {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n***INSTRUCCIONES DE USO***");
            System.out.println("Este programa permitira buscar las Ocurrencias de una palabra dado un archivo diccionario.ser ");
            System.out.println("creado a partir del nombre de un directorio o fichero seleccionado.");
            System.out.println("[1] - Buscar Ocurrencias");
            System.out.println("[2] - Crear Diccionario y buscar Ocurrencias");
            System.out.println("[3] - Salir");
            System.out.print("Seleccione una opcion: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Entrada no válida. Por favor, ingrese un número.");
                scanner.next();
            }
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    boolean isFinished = false;
                    while (!isFinished) {
                        System.out.print("Ingrese la palabra a buscar: ");
                        String palabra = scanner.next();
                        buscador b = new buscador();
                        b.realizarBusqueda(palabra);
                        System.out.print("Desea buscar otra palabra? (s/n): ");
                        String respuesta = scanner.next();
                        if (respuesta.equals("n")) {
                            isFinished = true;
                        }
                    }
                    break;
                case 2:
                    System.out.println("Introduce el nombre del directorio/fichero a procesar: ");
                    String nombreDirectorio = scanner.next();
                    File directorio = new File(nombreDirectorio);
                    if(!directorio.exists()){
                        System.out.println("El directorio/fichero no existe.");
                        break;
                    }
                    else {
                        procesarDirectoriosIterativo(nombreDirectorio);
                        boolean isFinished2 = false;
                        while (!isFinished2) {
                            System.out.print("Ingrese la palabra a buscar: ");
                            String palabra = scanner.next();
                            buscador b = new buscador();
                            b.realizarBusqueda(palabra);
                            System.out.print("Desea buscar otra palabra? (s/n): ");
                            String respuesta = scanner.next();
                            if (respuesta.equals("n")) {
                                isFinished2 = true;
                            }
                        }
                    }
                    break;
                case 3:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 3);

        scanner.close();
    }

    /*
     * Método que procesa los directorios de forma iterativa
     */
    public static void procesarDirectoriosIterativo(String directorioInicial) {
        Queue<String> cola = new LinkedList<>();
        cola.add(directorioInicial); 

        while (!cola.isEmpty()) {
            String directorio = cola.poll(); 
            File dir = new File(directorio);
            System.out.println("Entrando en directorio: " + dir.getAbsolutePath());

            File[] listaFicheros = dir.listFiles(); 
            System.out.println("NUMERO DE ARCHIVOS : " + listaFicheros.length);
            if (listaFicheros.length == 0) {
                System.out.println("Este directorio está vacío: " + dir.getAbsolutePath());
            }

            for (File archivo : listaFicheros) {
                if (archivo.isFile() && archivo.canRead()) { 
                    System.out.println(" Leyendo archivo: " + archivo.getAbsolutePath());

                    try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                        String linea;
                        while ((linea = br.readLine()) != null) {
                            System.out.println(linea);
                        }
                        System.out.println("\n Ejecutando SalvarObjeto con: " + archivo.getAbsolutePath() + "\n");
                        GestionarObjeto.main(new String[]{archivo.getAbsolutePath()});
                    } catch (IOException e) {
                        System.err.println(" Error al leer el archivo: " + archivo.getAbsolutePath());
                        e.printStackTrace();
                    }
                }
            }

            for (File subdirectorio : listaFicheros) {
                if (subdirectorio.isDirectory()) {
                    cola.add(subdirectorio.getAbsolutePath());
                }
            }
        }
    }
}
