package Operacion.DAO;

public class Operacion {
    int codigo, codigoTipo, codigoEstilo;
    String nombre;
    float precio, variacion;

    public Operacion() {
    }

    public Operacion(int codigo, int codigoTipo, int codigoEstilo, String nombre, float precio, float variacion) {
        this.codigo = codigo;
        this.codigoTipo = codigoTipo;
        this.codigoEstilo = codigoEstilo;
        this.nombre = nombre;
        this.precio = precio;
        this.variacion = variacion;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigoTipo() {
        return codigoTipo;
    }

    public void setCodigoTipo(int codigoTipo) {
        this.codigoTipo = codigoTipo;
    }

    public int getCodigoEstilo() {
        return codigoEstilo;
    }

    public void setCodigoEstilo(int codigoEstilo) {
        this.codigoEstilo = codigoEstilo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public float getVariacion() {
        return variacion;
    }

    public void setVariacion(float variacion) {
        this.variacion = variacion;
    }
}
