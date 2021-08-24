package Usuario.DAO;

public class Usuario {
    int codigo;
    String nombre, apellido, usuario, pass, dpi;
    public Usuario(){}
    public Usuario(int codigo, String nombre, String apellido, String usuario, String pass, String dpi){

        this.codigo=codigo;
        this.nombre=nombre;
        this.apellido=apellido;

        this.usuario=usuario;
        this.pass=pass;
        this.dpi=dpi;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }
}
