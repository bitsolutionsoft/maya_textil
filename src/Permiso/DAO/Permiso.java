package Permiso.DAO;

public class Permiso {
    int codigo, codigoUsuario,codigoModulo, acceso;
    String nombre;

    public Permiso() {
    }

    public Permiso(int codigo, int codigoUsuario, int codigoModulo, int acceso) {
        this.codigo = codigo;
        this.codigoUsuario = codigoUsuario;
        this.codigoModulo = codigoModulo;
        this.acceso = acceso;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(int codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public int getCodigoModulo() {
        return codigoModulo;
    }

    public void setCodigoModulo(int codigoModulo) {
        this.codigoModulo = codigoModulo;
    }

    public int getAcceso() {
        return acceso;
    }

    public void setAcceso(int acceso) {
        this.acceso = acceso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
