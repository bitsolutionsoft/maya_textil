package Informe.DAO;

public class ResumenPago {
    int idpago;
    String idcorte;
    float total,subtotal;
    String nombre;
    int tipo;

    public ResumenPago() {
    }

    public ResumenPago(int idpago, String idcorte, float total, float subtotal, String nombre, int tipo) {
        this.idpago = idpago;
        this.idcorte = idcorte;
        this.total = total;
        this.subtotal = subtotal;
        this.nombre = nombre;
        this.tipo = tipo;
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

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
