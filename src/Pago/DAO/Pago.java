package Pago.DAO;

public class Pago {

    int idpago;
    String idcorte, fecha_pago;
    float total;
    public Pago(){}
    public Pago(int idpago, String idcorte, String fecha_pago, float total){


        this.idpago=idpago;
        this.idcorte=idcorte;
        this.fecha_pago=fecha_pago;
        this.total=total;
    }

    public int getIdpago() {
        return idpago;
    }

    public void setIdpago(int idpago) {
        this.idpago = idpago;
    }

    public String getIdcorte() {
        return idcorte;
    }

    public void setIdcorte(String idcorte) {
        this.idcorte = idcorte;
    }

    public String getFecha_pago() {
        return fecha_pago;
    }

    public void setFecha_pago(String fecha_pago) {
        this.fecha_pago = fecha_pago;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
