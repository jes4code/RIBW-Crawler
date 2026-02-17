
import java.util.Map;
import java.util.TreeMap;

public class buscador {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("ERROR. Utilizar: >java buscador palabra");
            return;
        }

        buscador b = new buscador();
        b.realizarBusqueda(args[0]);
    }


    public void realizarBusqueda(String palabra){
        TreeMap<String, Ocurrencia> h = GestionarObjeto.loadObject("diccionario.ser");
        if (h != null) {
            System.out.println("Diccionario cargado correctamente");
            System.out.println("Realizando búsqueda...");
            if(h.get(palabra) != null){
                Ocurrencia p= h.get(palabra);
                Map<String,Integer> mapa =p.getMapa();
                System.out.println("Palabra: '" + palabra + "' - Frecuencia: " + h.get(palabra).getCantidad() );
                for (Map.Entry<String, Integer> entry : mapa.entrySet()) {
                    System.out.println("Ruta: " + entry.getKey() + ", Cantidad: " + entry.getValue());
                }            
            } else {
                System.out.println("Palabra no encontrada");
            }
        }
    }
}
