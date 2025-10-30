import java.util.Map;

public class Activo {
    private String ticker; 
    private double retornoEsperado;
    private double riesgo;
    private double montoMinimo; 
    private String tipoActivo;
    private String sectorEconomico;
    private Map<String, Double> historial; // Historial de precios u otros datos relevantes


    public Activo(String ticker, double retornoEsperado, double riesgo, double montoMinimo, String tipoActivo, String sectorEconomico) {
        this.ticker = ticker;
        this.retornoEsperado = retornoEsperado;
        this.riesgo = riesgo;
        this.montoMinimo = montoMinimo;
        this.tipoActivo = tipoActivo;
        this.sectorEconomico = sectorEconomico;
    }


    public String getTicker() { return ticker; }
    public double getRetornoEsperado() { return retornoEsperado; }
    public double getRiesgo() { return riesgo; }
    public double getMontoMinimo() { return montoMinimo; }
    public String getTipoActivo() { return tipoActivo; }
    public String getSectorEconomico() { return sectorEconomico; }
    public Map<String, Double> getHistorial() { return historial; }


    public String toString() {
        return "Activo{" +
                "ticker='" + ticker + '\'' +
                ", retornoEsperado=" + retornoEsperado +
                ", riesgo=" + riesgo +
                ", montoMinimo=" + montoMinimo +
                ", tipoActivo='" + tipoActivo + '\'' +
                ", sectorEconomico='" + sectorEconomico + '\'' +
                '}';
    }
}
