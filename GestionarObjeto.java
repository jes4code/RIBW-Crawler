import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;
import java.util.List;
import java.util.StringTokenizer;

public class GestionarObjeto {

    public static void main(String args[]) {
        if (args.length < 1) {
            System.out.println("ERROR. Utilizar: >java GestionarObjeto nombre_archivo");
            return;
        }

        GestionarObjeto go = new GestionarObjeto();
        TreeMap<String, Ocurrencia> hFichero = go.procesarArchivo(args[0]);
        if (hFichero != null) {
            System.out.println("Procesado archivo: " );
        }
        TreeMap<String, Ocurrencia> loadedHfichero = GestionarObjeto.loadObject("diccionario.ser");
        if (loadedHfichero != null) {
            System.out.println("Cargado archivo: " );
        }
    }

    /*
     * Método que procesa un fichero de texto y devuelve una TreeMap con las palabras y su frecuencia
     */
    public TreeMap<String, Ocurrencia> procesarArchivo(String fichero) {
        System.out.println("Iniciando procesamiento del archivo: " + fichero);
        
        // Verificar si diccionario.ser existe
        File archivoSerializado = new File("diccionario.ser");
        TreeMap<String, Ocurrencia> hFichero;
        File file = new File(fichero);
        String rutaCompleta = file.getAbsolutePath();
    
        if (archivoSerializado.exists()) {
            System.out.println("diccionario.ser encontrado. Intentando cargar...");
            hFichero = GestionarObjeto.loadObject("diccionario.ser");
        } else {
            System.out.println("diccionario.ser no encontrado. Creando nuevo TreeMap...");
            hFichero = new TreeMap<>();
        }
    
        try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {
            String linea;
            System.out.println("Archivo abierto correctamente. Procesando líneas...");
            
            while ((linea = br.readLine()) != null) {
                System.out.println("Leyendo línea: " + linea);
    
                StringTokenizer st = new StringTokenizer(linea, " ,.:;(){}!°?\t''%/|[]<=>&#+*$-¨^~");
                while (st.hasMoreTokens()) {
                    String palabra = st.nextToken().toLowerCase();
                    if(hFichero.get(palabra) !=null){
                        Ocurrencia ocurrencia = hFichero.get(palabra);
                        ocurrencia.setCantidad(ocurrencia.getCantidad() + 1);
                        if(ocurrencia.getMapa().get(rutaCompleta) != null){
                            ocurrencia.getMapa().put(rutaCompleta, ocurrencia.getMapa().get(rutaCompleta) + 1);
                        } else {
                            ocurrencia.getMapa().put(rutaCompleta, 1);
                        }
                        hFichero.put(palabra, ocurrencia);
                    } else {
                        Ocurrencia ocurrencia = new Ocurrencia(1);
                        ocurrencia.setCantidad(1);
                        ocurrencia.agregarRuta(rutaCompleta, 1);
                        hFichero.put(palabra, ocurrencia);
                    }
                    System.out.println("Palabra añadida: " + palabra);
                    System.out.println("Ruta: " + rutaCompleta);
                }
            }
    
            System.out.println(" Guardando diccionario...");
            this.guardarObject(hFichero);
            this.crearFicheroSalida(hFichero);
            System.out.println("Procesamiento terminado.");
    
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + fichero);
            return null;
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + fichero);
            return null;
        }
    
        return hFichero;
    }
    

   private void guardarObject(Object obj) {
        if (!(obj instanceof Serializable)) {
            System.out.println("Error: el objeto no es serializable.");
            return;
        }
        try (FileOutputStream fos = new FileOutputStream("diccionario.ser");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(obj);
            System.out.println("Objeto guardado en diccionario.ser");
        } catch (IOException e) {
            System.out.println("Error al serializar: " + e.getMessage());
        }
    }

    /*
     * Método que crea un fichero de salida y escribe la estructura ordenada de la TreeMap
     */
    private void crearFicheroSalida(TreeMap<String, Ocurrencia> hFichero) {
        List<String> claves = new ArrayList<>(hFichero.keySet());
        Collections.sort(claves);

        try (PrintWriter pr = new PrintWriter(new FileWriter("salida.txt"))) {
            for (String clave : claves) {
                pr.println(clave + " : " + hFichero.get(clave).getCantidad()  );
            }
            System.out.println("\nCreando el fichero de salida salida.txt\n");
            System.out.println("Estructura de la TreeMap guardada en salida.txt");
        } catch (IOException e) {
            System.out.println("Error al escribir el fichero de salida: " + e.getMessage());
        }
    }

    /*
     * Método que carga una TreeMap desde un fichero diccionario.ser
     */
    public static TreeMap<String, Ocurrencia> loadObject(String diccionario) {
        try {
             FileInputStream fis = new FileInputStream(diccionario);
             try (ObjectInputStream ois = new ObjectInputStream(fis)) {
                 @SuppressWarnings("unchecked")
                 TreeMap<String, Ocurrencia> h = (TreeMap<String, Ocurrencia>) ois.readObject();
                 return h;
             }
         }
         catch (Exception e) { System.out.println(e); }
         return null;
    }

}
