package Pago.DAO;

public class Boletas {
    String nombre;
    public Boletas(){

    }
    public Boletas(String nombre){
        this.nombre=nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
