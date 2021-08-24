package Corte.DAO;

public class Corte {
    int idestilo, cantidad, cant_rollo;
    String idcorte, estado, fecha_corte,nombre;


    public Corte(){}
    public Corte(String idcorte, int idestilo, int cantidad, String fecha_corte, int cant_rollo, String estado){

        this.idcorte=idcorte;
        this.idestilo=idestilo;
        this.cantidad=cantidad;
        this.fecha_corte=fecha_corte;
        this.cant_rollo=cant_rollo;
        this.estado=estado;
    }

    public int getIdestilo() {
        return idestilo;
    }

    public void setIdestilo(int idestilo) {
        this.idestilo = idestilo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCant_rollo() {
        return cant_rollo;
    }

    public void setCant_rollo(int cant_rollo) {
        this.cant_rollo = cant_rollo;
    }

    public String getIdcorte() {
        return idcorte;
    }

    public void setIdcorte(String idcorte) {
        this.idcorte = idcorte;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha_corte() {
        return fecha_corte;
    }

    public void setFecha_corte(String fecha_corte) {
        this.fecha_corte = fecha_corte;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
