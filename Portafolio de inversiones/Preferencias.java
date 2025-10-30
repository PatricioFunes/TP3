public class Preferencias {
    private String categoria; // Categoria se refiere a si es un tipo de accion o el sector economico
    private String nombre; 
    private double porcentaje;


    public Preferencias(String categoria, String nombre, double porcentaje) {
        this.categoria = categoria;
        this.nombre = nombre;
        this.porcentaje = porcentaje;
    }

    public String getCategoria() { return categoria; }
    public String getNombre() { return nombre; }
    public double getPorcentaje() { return porcentaje; }

    public String toString() {
        return "Preferencias{" +
                "categoria='" + categoria + '\'' +
                ", nombre='" + nombre + '\'' +
                ", porcentaje=" + porcentaje +
                '}';
    }

}