import java.util.*;

/**
 * Evaluador.java
 * Evalúa portafolios según las consignas del TP:
 * - Calcula retorno total y riesgo ajustado por correlación.
 * - Verifica restricciones: riesgo, retorno, presupuesto, cantidad, preferencias.
 */
public class Evaluador {

    /** Mapea cada perfil de riesgo a valores máximos y mínimos */
    public static class PerfilValores {
        public final double riesgoMaximo;
        public final double retornoMinimo;
        public PerfilValores(double riesgoMaximo, double retornoMinimo) {
            this.riesgoMaximo = riesgoMaximo;
            this.retornoMinimo = retornoMinimo;
        }
    }

    public static PerfilValores valoresParaPerfil(PerfilRiesgo perfil) {
        switch (perfil) {
            case CONSERVADOR: return new PerfilValores(20.0, 10.0);
            case CONSERVADORMODERADO: return new PerfilValores(30.0, 12.0);
            case MODERADO: return new PerfilValores(40.0, 14.0);
            case AGRESIVOMODERADO: return new PerfilValores(50.0, 16.0);
            case AGRESIVO: return new PerfilValores(60.0, 18.0);
            default: return new PerfilValores(40.0, 14.0);
        }
    }

    // =====================
    // MÉTODOS PRINCIPALES
    // =====================

    /** Retorno promedio de los activos */
    public static double calcularRetornoEsperado(List<Activo> activos) {
        if (activos == null || activos.isEmpty()) return 0.0;
        double suma = 0.0;
        for (Activo a : activos) suma += a.getRetornoEsperado();
        return suma / activos.size();
    }

    /** Calcula el riesgo total del portafolio usando matriz de correlaciones */
    public static double calcularRiesgoPortafolio(List<Activo> activos, double[][] correlaciones, Map<String,Integer> tickersIndex) {
        if (activos == null || activos.isEmpty()) return 0.0;
        int n = activos.size();
        double[] sigma = new double[n];
        for (int i = 0; i < n; i++) sigma[i] = activos.get(i).getRiesgo();
        double[] w = new double[n];
        Arrays.fill(w, 1.0 / n);

        double var = 0.0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                String ti = activos.get(i).getTicker();
                String tj = activos.get(j).getTicker();
                Integer idx_i = tickersIndex.get(ti);
                Integer idx_j = tickersIndex.get(tj);
                double corr = 0.0;
                if (idx_i != null && idx_j != null && correlaciones != null) {
                    corr = correlaciones[idx_i][idx_j];
                }
                var += w[i] * w[j] * sigma[i] * sigma[j] * corr;
            }
        }
        return Math.sqrt(Math.max(var, 0.0));
    }

    /** Evalúa si el portafolio cumple las restricciones */
    public static ResultadoEvaluacion evaluarPortafolio(PortafolioPrincipal p,
                                                        Cliente cliente,
                                                        double[][] correlaciones,
                                                        Map<String,Integer> tickersIndex) {
        ResultadoEvaluacion res = new ResultadoEvaluacion();
        List<Activo> activos = p.getActivos();
        res.setActivosEvaluados(activos);

        int n = activos.size();
        if (n < 3 || n > 6) {
            res.addMensaje("Cantidad de activos inválida: " + n + " (debe ser 3 a 6)");
        }

        double costoTotal = p.getCostoTotal();
        if (costoTotal > cliente.getMontoMaximo()) {
            res.addMensaje(String.format("Costo total %.2f supera monto máximo %.2f",
                    costoTotal, (double) cliente.getMontoMaximo()));
        }

        double retorno = calcularRetornoEsperado(activos);
        double riesgo = calcularRiesgoPortafolio(activos, correlaciones, tickersIndex);
        res.setRetornoEsperado(retorno);
        res.setRiesgoTotal(riesgo);
        res.setCostoTotal(costoTotal);

        PerfilValores pv = valoresParaPerfil(cliente.getPerfilInversion());
        double riesgoMax = pv.riesgoMaximo;
        double retornoMin = Math.max(cliente.getRetornoMinimoDeseado(), pv.retornoMinimo);

        if (riesgo > riesgoMax)
            res.addMensaje(String.format("Riesgo %.2f supera el máximo permitido %.2f", riesgo, riesgoMax));
        if (retorno < retornoMin)
            res.addMensaje(String.format("Retorno %.2f menor al mínimo deseado %.2f", retorno, retornoMin));

        // Verificar preferencias (sector / tipo)
        Map<String, Double> sectores = new HashMap<>();
        Map<String, Double> tipos = new HashMap<>();
        for (Activo a : activos) {
            sectores.merge(a.getSectorEconomico(), 1.0, Double::sum);
            tipos.merge(a.getTipoActivo(), 1.0, Double::sum);
        }
        sectores.replaceAll((k, v) -> v / n * 100);
        tipos.replaceAll((k, v) -> v / n * 100);

        double tolerancia = 5.0; // % de tolerancia
        for (Preferencias pref : cliente.getPreferencias()) {
            String cat = pref.getCategoria().toLowerCase();
            String nombre = pref.getNombre();
            double deseado = pref.getPorcentaje();
            double actual = cat.equals("sector") ? sectores.getOrDefault(nombre, 0.0): tipos.getOrDefault(nombre, 0.0);
            if (Math.abs(actual - deseado) > tolerancia) {
                res.addMensaje(String.format("Preferencia '%s' no respetada: %.2f%% (deseado %.2f%%)",
                        nombre, actual, deseado));
            }
        }

        res.setValido(res.getMensajes().isEmpty());
        return res;
    }

    // =====================
    // CLASE AUXILIAR RESULTADO
    // =====================
    public static class ResultadoEvaluacion {
        private boolean valido = true;
        private List<String> mensajes = new ArrayList<>();
        private List<Activo> activosEvaluados = new ArrayList<>();
        private double retornoEsperado = 0.0;
        private double riesgoTotal = 0.0;
        private double costoTotal = 0.0;

        public void addMensaje(String m) { mensajes.add(m); valido = false; }
        public List<String> getMensajes() { return mensajes; }
        public boolean isValido() { return valido; }
        public void setValido(boolean v) { valido = v; }

        public void setActivosEvaluados(List<Activo> a) { activosEvaluados = a; }
        public List<Activo> getActivosEvaluados() { return activosEvaluados; }

        public void setRetornoEsperado(double r) { retornoEsperado = r; }
        public double getRetornoEsperado() { return retornoEsperado; }

        public void setRiesgoTotal(double r) { riesgoTotal = r; }
        public double getRiesgoTotal() { return riesgoTotal; }

        public void setCostoTotal(double c) { costoTotal = c; }
        public double getCostoTotal() { return costoTotal; }
    }
}
