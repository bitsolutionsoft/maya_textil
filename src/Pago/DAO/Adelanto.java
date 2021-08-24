package Pago.DAO;

public class Adelanto {
    int idadelanto, idempleado;
    String estado;
    String concepto;


    String fecha;
    float cantidad;
    public Adelanto(){
    }
    public Adelanto(int idadelanto, int idempleado, float cantidad, String concepto, String estado){
        this.idadelanto=idadelanto;
        this.idempleado=idempleado;
        this.cantidad=cantidad;
        this.concepto=concepto;

        this.estado=estado;
    }

    public int getIdadelanto() {
        return idadelanto;
    }

    public void setIdadelanto(int idadelanto) {
        this.idadelanto = idadelanto;
    }

    public int getIdempleado() {
        return idempleado;
    }

    public void setIdempleado(int idempleado) {
        this.idempleado = idempleado;
    }



    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }


}
