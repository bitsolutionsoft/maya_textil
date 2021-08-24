package Corte.DAO;

public class Rollos {

    int idrollo ;
    String idcorte,codigo_tela,color,tipo;
    int idetela ;
    int cantidad ;

    public Rollos() {
    }

    public Rollos(int idrollo, String idcorte, int idetela, int cantidad) {
        this.idrollo = idrollo;
        this.idcorte = idcorte;
        this.idetela = idetela;
        this.cantidad = cantidad;
    }

    public int getIdrollo() {
        return idrollo;
    }

    public void setIdrollo(int idrollo) {
        this.idrollo = idrollo;
    }

    public String getIdcorte() {
        return idcorte;
    }

    public void setIdcorte(String idcorte) {
        this.idcorte = idcorte;
    }

    public int getIdetela() {
        return idetela;
    }

    public void setIdetela(int idetela) {
        this.idetela = idetela;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getCodigo_tela() {
        return codigo_tela;
    }

    public void setCodigo_tela(String codigo_tela) {
        this.codigo_tela = codigo_tela;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
