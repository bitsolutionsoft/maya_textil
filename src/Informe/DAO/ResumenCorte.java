package Informe.DAO;

public class ResumenCorte {

    int idestilo,cantidad,cant_rollo, idpago;
    String idcorte,fecha_corte, nombre;
    float total;

    public ResumenCorte() {
    }
    public ResumenCorte(int idpago, String idcorte, int idestilo, String nombre, int cantidad,String fecha_corte, int cant_rollo, float total) {
        this.idcorte = idcorte;
        this.idestilo = idestilo;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.fecha_corte = fecha_corte;
        this.cant_rollo = cant_rollo;
        this.total = total;
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

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getIdpago() {
        return idpago;
    }

    public void setIdpago(int idpago) {
        this.idpago = idpago;
    }
}
