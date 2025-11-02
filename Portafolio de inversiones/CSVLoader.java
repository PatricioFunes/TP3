import java.io.*;
import java.util.*;

/**
 * CSVLoader.java
 * 
 * Carga los archivos CSV del TP:
 * - activos_financieros_60.csv → lista de Activo
 * - correlaciones_60.csv → matriz de correlaciones + mapa de índices
 */
public class CSVLoader {

    //RUTAS ABSOLUTAS (cambiar en caso de tener que usar otras rutas o usar desde otra compu)
    private static final String RUTA_ABSOLUTA_ACTIVOS =
        "C:\\Users\\Usuario\\Documents\\UADE\\Portafolio\\Portafolio de inversiones\\activos_financieros_60.csv";  // ← CAMBIÁ ESTA
    private static final String RUTA_ABSOLUTA_CORRELACIONES =
        "C:\\Users\\Usuario\\Documents\\UADE\\Portafolio\\Portafolio de inversiones\\correlaciones_60.csv";        // ← CAMBIÁ ESTA

    /** Carga el archivo de activos financieros */
    public static List<Activo> cargarActivos(String rutaArchivo) {
        List<Activo> activos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea = br.readLine(); // Leer encabezado
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] partes = linea.split(",");

                // Aseguramos que haya al menos las 6 columnas requeridas
                if (partes.length < 6) continue;

                String ticker = partes[0].trim();
                double retorno = Double.parseDouble(partes[1].trim());
                double riesgo = Double.parseDouble(partes[2].trim());
                double inversionMin = Double.parseDouble(partes[3].trim());
                String tipo = partes[4].trim();
                String sector = partes[5].trim();

                Activo a = new Activo(ticker, retorno, riesgo, inversionMin, tipo, sector);
                activos.add(a);
            }
            System.out.println("Se cargaron " + activos.size() + " activos desde " + rutaArchivo);
        } catch (Exception e) {
            System.err.println("Error al cargar activos: " + e.getMessage());
        }
        return activos;
    }

    /**
     * Carga el archivo de correlaciones.
     * Retorna un objeto con la matriz y el mapa de índices.
     */
    public static CorrelacionesData cargarCorrelaciones(String rutaArchivo) {
        List<String> tickers = new ArrayList<>();
        List<double[]> filas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String encabezado = br.readLine();
            if (encabezado == null) throw new RuntimeException("Archivo vacío: " + rutaArchivo);

            // Leer los nombres de columnas
            String[] columnas = encabezado.split(",");
            for (int i = 1; i < columnas.length; i++) {
                tickers.add(columnas[i].trim());
            }

            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] partes = linea.split(",");
                double[] valores = new double[tickers.size()];
                for (int i = 1; i < partes.length; i++) {
                    valores[i - 1] = Double.parseDouble(partes[i].trim());
                }
                filas.add(valores);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar correlaciones: " + e.getMessage());
        }

        // Crear matriz y mapa de índices
        int n = tickers.size();
        double[][] matriz = new double[n][n];
        for (int i = 0; i < n && i < filas.size(); i++) {
            matriz[i] = filas.get(i);
        }

        Map<String, Integer> mapa = new HashMap<>();
        for (int i = 0; i < tickers.size(); i++) {
            mapa.put(tickers.get(i), i);
        }

        System.out.println("Se cargó matriz de correlaciones con " + n + " activos.");
        return new CorrelacionesData(matriz, mapa);
    }

    /** Clase auxiliar para devolver la matriz y los índices */
    public static class CorrelacionesData {
        public final double[][] matriz;
        public final Map<String, Integer> indices;

        public CorrelacionesData(double[][] matriz, Map<String, Integer> indices) {
            this.matriz = matriz;
            this.indices = indices;
        }
    }

    // =====================
    // PRUEBA RÁPIDA
    // =====================
    public static void main(String[] args) {
        // 🔧 USO DIRECTO DE LAS RUTAS ABSOLUTAS
        List<Activo> activos = cargarActivos(RUTA_ABSOLUTA_ACTIVOS);
        CorrelacionesData correlaciones = cargarCorrelaciones(RUTA_ABSOLUTA_CORRELACIONES);

        System.out.println("Primer activo: " + (activos.isEmpty() ? "ninguno" : activos.get(0)));
        System.out.println("Cantidad de correlaciones: " + correlaciones.matriz.length);
    }
}
