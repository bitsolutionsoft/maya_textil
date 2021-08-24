package Informe.DAO;

public class Bodega {

    int  idBodega ;
    String  codigo_tela;
    String tipo ;
    String  colores ;
    int  cantidad;
    float  precio,
            total;

    public Bodega() {
    }

    public Bodega(int idBodega, String codigo_tela, String tipo, String colores, int cantidad, float precio, float total) {

        this.idBodega = idBodega;
        this.codigo_tela = codigo_tela;
        this.tipo = tipo;
        this.colores = colores;
        this.cantidad = cantidad;
        this.precio = precio;
        this.total = total;
    }

    public int getIdBodega() {
        return idBodega;
    }

    public void setIdBodega(int idBodega) {
        this.idBodega = idBodega;
    }

    public String getCodigo_tela() {
        return codigo_tela;
    }

    public void setCodigo_tela(String codigo_tela) {
        this.codigo_tela = codigo_tela;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getColores() {
        return colores;
    }

    public void setColores(String colores) {
        this.colores = colores;
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

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
