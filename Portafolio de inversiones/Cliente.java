import java.util.List;
import java.util.ArrayList;


public class Cliente {
    private final PerfilRiesgo perfilInversion;
    private final int montoMaximo; // Presupuesto
    private final int plazoEsperado;
    private final double retornoMinimoDeseado;
    final List<Activo> listaActivos; 
    private final List<Preferencias> preferencias; // Sector económico y Tipo de activo


    public Cliente(PerfilRiesgo perfilInversion, int montoMaximo, int plazoEsperado, double retornoMinimoDeseado) {
        this.perfilInversion = perfilInversion;
        this.montoMaximo = montoMaximo;
        this.plazoEsperado = plazoEsperado;
        this.retornoMinimoDeseado = retornoMinimoDeseado;
        this.listaActivos = new ArrayList<>();
        this.preferencias = new ArrayList<>();
    }



    public PerfilRiesgo getPerfilInversion() { return perfilInversion; }
    public int getMontoMaximo() { return montoMaximo; }
    public int getPlazoEsperado() { return plazoEsperado; }
    public double getRetornoMinimoDeseado() { return retornoMinimoDeseado;}
    public List<Activo> getListaActivos() { return listaActivos; }
    public List<Preferencias> getPreferencias() { return preferencias; }


    public String toString() {
        return "Cliente{" +
                "perfilInversion='" + perfilInversion + '\'' +
                ", montoMaximo=" + montoMaximo +
                ", plazoEsperado=" + plazoEsperado +
                ", retornoMinimoDeseado=" + retornoMinimoDeseado +
                ", listaActivos=" + listaActivos.toString() +
                ", preferencias=" + preferencias.toString() +
                '}';
    }






}
