import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class Ocurrencia implements Serializable {
    private int cantidad;
    private Map<String, Integer> mapa; 

    public Ocurrencia(int cantidad) {
        this.cantidad = cantidad;
        this.mapa = new TreeMap<>();
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Map<String, Integer> getMapa() {
        return mapa;
    }

    public void setMapa(Map<String, Integer> mapa) {
        this.mapa = mapa;
    }

    public void agregarRuta(String ruta, Integer cantidad) {
        mapa.put(ruta, cantidad);
    }

    public void mostrarOcurrencias() {
        System.out.println("Cantidad: " + cantidad);
        for (Map.Entry<String, Integer> entry : mapa.entrySet()) {
            System.out.println("Ruta: " + entry.getKey() + ", Cantidad: " + entry.getValue());
        }
    }
}
