import java.util.ArrayList;
import java.util.List;

public class AlgoritmoPrincipal {


    
    public void medirRiesgo() {
        List<Cliente> clientes = new ArrayList<>();
        for (Cliente c: clientes) {
            for (Activo a: c.getListaActivos()) {
            
                if ((c.getPerfilInversion() == PerfilRiesgo.CONSERVADOR)) {
                    System.out.println("Perfil de riesgo: Conservador");
                
                    if (a.getRiesgo() <= 0.2 && a.getRetornoEsperado() >= 0.1) {
                        System.out.println("Activo apto para perfil conservador: " + a.getTicker());
                    } 
                
                    else {
                        System.out.println("Activo no apto para perfil conservador: " + a.getTicker());
                        c.listaActivos.remove(a);  
                    }
                }

                else if (
                    (c.getPerfilInversion() == PerfilRiesgo.CONSERVADORMODERADO)) {
                    System.out.println("Perfil de riesgo: Conservador Moderado");
                
                    if (a.getRiesgo() <= 0.3 && a.getRetornoEsperado() >= 0.12) {
                        System.out.println("Activo apto para perfil conservador moderado: " + a.getTicker());
                    } 
                
                    else {
                        System.out.println("Activo no apto para perfil conservador moderado: " + a.getTicker());
                        c.listaActivos.remove(a);  
                    }
                } 
            
                else if (c.getPerfilInversion() == PerfilRiesgo.MODERADO) {
                    System.out.println("Perfil de riesgo: Moderado");
                
                    if (a.getRiesgo() <= 0.4 && a.getRetornoEsperado() >= 0.14) {
                        System.out.println("Activo apto para perfil moderado: " + a.getTicker());
                    } 
                
                    else {
                        System.out.println("Activo no apto para perfil moderado: " + a.getTicker());
                        c.listaActivos.remove(a);  
                    }
                } 
        
                else if (c.getPerfilInversion() == PerfilRiesgo.AGRESIVOMODERADO) {
                    System.out.println("Perfil de riesgo: Agresivo Moderado");
                
                    if (a.getRiesgo() <= 0.5 && a.getRetornoEsperado() >= 0.16) {
                        System.out.println("Activo apto para perfil agresivo moderado: " + a.getTicker());
                    } 
                
                    else {
                        System.out.println("Activo no apto para perfil agresivo moderado: " + a.getTicker());
                        c.listaActivos.remove(a);  
                    }
                } 
            
                else {
                    System.out.println("Perfil de riesgo: Agresivo");
                
                    if (a.getRiesgo() <= 0.6 && a.getRetornoEsperado() >= 0.18) {
                        System.out.println("Activo apto para perfil agresivo: " + a.getTicker());
                    } 
                
                    else {
                        System.out.println("Activo no apto para perfil agresivo: " + a.getTicker());
                        c.listaActivos.remove(a);  
                    }

                }
            }
        }
    }
}
