package Pago.DAO;

public class DetallePago {
    int iddetalle, idpago, idempleado, idoperacion, cantidad;
    float precio,descuento,total;
    String estado,nombre,fecha,idcorte;

    public DetallePago(){}
    public DetallePago(int iddetalle, int idpago, int idempleado, int idoperacion, int cantidad, float precio, float descuento, float total, String estado){

        this.iddetalle=iddetalle;
        this.idpago=idpago;
        this.idempleado=idempleado;
        this.idoperacion=idoperacion;
        this.cantidad=cantidad;
        this.precio=precio;
        this.descuento=descuento;
        this.total=total;
        this.estado=estado;

    }

    public int getIddetalle() {
        return iddetalle;
    }

    public void setIddetalle(int iddetalle) {
        this.iddetalle = iddetalle;
    }

    public int getIdpago() {
        return idpago;
    }

    public void setIdpago(int idpago) {
        this.idpago = idpago;
    }

    public int getIdempleado() {
        return idempleado;
    }

    public void setIdempleado(int idempleado) {
        this.idempleado = idempleado;
    }

    public int getIdoperacion() {
        return idoperacion;
    }

    public void setIdoperacion(int idoperacion) {
        this.idoperacion = idoperacion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public String getIdcorte(){return idcorte;}
    public void setIdcorte(String idcorte){this.idcorte=idcorte;}
}
