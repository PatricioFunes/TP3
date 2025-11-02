import java.util.*;

public class Main {
    public static void main(String[] args) {

        // 1️⃣ CARGA DE DATOS DESDE LOS CSV (usando rutas absolutas)
        List<Activo> activos = CSVLoader.cargarActivos(getRutaActivos());
        CSVLoader.CorrelacionesData corrData = CSVLoader.cargarCorrelaciones(getRutaCorrelaciones());

        if (activos.isEmpty() || corrData.matriz.length == 0) {
            System.out.println("No se pudieron cargar los datos. Verifica las rutas de los CSV.");
            return;
        }

        // 2️⃣ CREACIÓN DE CLIENTE DE PRUEBA
        Cliente cliente = new Cliente(PerfilRiesgo.MODERADO, 100000, 3, 14.0);

        // Preferencias del cliente (opcional)
        cliente.getPreferencias().add(new Preferencias("sector", "Tecnologia", 30.0));
        cliente.getPreferencias().add(new Preferencias("sector", "Salud", 20.0));
        cliente.getPreferencias().add(new Preferencias("tipo", "Accion", 50.0));

        // Ejemplo de activos que el cliente no quiere
        cliente.getActivosProhibidos().add("YPF");

        // 3️⃣ CREAR UN PORTAFOLIO DE PRUEBA
        PortafolioPrincipal portafolio = new PortafolioPrincipal(0, 0, 0);

        // Elegimos algunos activos arbitrarios
        portafolio.getActivos().add(activos.get(0));
        portafolio.getActivos().add(activos.get(1));
        portafolio.getActivos().add(activos.get(2));
        portafolio.getActivos().add(activos.get(3));

        // Calcular costo total (sumando montos mínimos)
        double costo = 0;
        for (Activo a : portafolio.getActivos()) costo += a.getMontoMinimo();
        portafolio.setCostoTotal(costo);

        // 4️⃣ EVALUAR EL PORTAFOLIO
        Evaluador.ResultadoEvaluacion resultado = Evaluador.evaluarPortafolio(
                portafolio, cliente, corrData.matriz, corrData.indices
        );

        // 5️⃣ MOSTRAR RESULTADOS
        System.out.println("\n==============================");
        System.out.println("   RESULTADO DE EVALUACIÓN");
        System.out.println("==============================");
        System.out.println("Válido: " + resultado.isValido());
        System.out.printf("Retorno esperado: %.2f%%\n", resultado.getRetornoEsperado());
        System.out.printf("Riesgo total: %.2f%%\n", resultado.getRiesgoTotal());
        System.out.printf("Costo total: $%.2f\n", resultado.getCostoTotal());
        System.out.println("------------------------------");
        System.out.println("Activos en el portafolio:");
        for (Activo a : portafolio.getActivos()) {
            System.out.println(" - " + a.getTicker() + " (" + a.getSectorEconomico() + ")");
        }
        if (!resultado.getMensajes().isEmpty()) {
            System.out.println("------------------------------");
            System.out.println("Observaciones:");
            for (String msg : resultado.getMensajes()) {
                System.out.println(" ⚠️ " + msg);
            }
        }
        System.out.println("==============================\n");
    }

    // MÉTODOS AUXILIARES PARA LAS RUTAS ABSOLUTAS
    private static String getRutaActivos() {
        // Cambiá estas rutas según tu PC o archivo
        return "C:\\Users\\Usuario\\Documents\\UADE\\Portafolio\\Portafolio de inversiones\\activos_financieros_60.csv";
    }
    private static String getRutaCorrelaciones() {
        // Cambiá estas rutas según tu PC o archivo
        return "C:\\Users\\Usuario\\Documents\\UADE\\Portafolio\\Portafolio de inversiones\\correlaciones_60.csv";
    }
}
