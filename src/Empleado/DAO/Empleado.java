package Empleado.DAO;

public class Empleado {
    int codigo, telefono;
    String nombre, apellido, usuario, estado, dpi;
    public Empleado(){}
    public Empleado(int codigo, String nombre, String apellido, String dpi, int telefono, String estado){

        this.codigo=codigo;
        this.nombre=nombre;
        this.apellido=apellido;
        this.dpi=dpi;
        this.telefono=telefono;
        this.estado=estado;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDpi() { return dpi; }

    public void setDpi(String dpi) { this.dpi = dpi; }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
