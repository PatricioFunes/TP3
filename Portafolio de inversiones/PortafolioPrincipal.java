import java.util.List;
import java.util.ArrayList;





public class PortafolioPrincipal {
    private List<Activo> Activos;
    private double retornoTotalEsperado;
    private double riesgoTotal;
    private double costoTotal; 
    

    public PortafolioPrincipal(double retornoTotalEsperado, double riesgoTotal, double costoTotal) {
        this.Activos = new ArrayList<>();
        this.retornoTotalEsperado = retornoTotalEsperado;
        this.riesgoTotal = riesgoTotal;
        this.costoTotal = costoTotal;
    }



    public List<Activo> getActivos() { return Activos; }
    public double getRetornoTotalEsperado() { return retornoTotalEsperado; }
    public double getRiesgoTotal() { return riesgoTotal; }
    public double getCostoTotal() { return costoTotal; }

    public void setActivos(List<Activo> activos) { this.Activos = activos; }
    public void setRetornoTotalEsperado(double r) { this.retornoTotalEsperado = r; }
    public void setRiesgoTotal(double r) { this.riesgoTotal = r; }
    public void setCostoTotal(double c) { this.costoTotal = c; }

    
}


