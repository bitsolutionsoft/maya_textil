package Usuario;

import ClassAux.Formato;
import ClassAux.Util;
import Login.encriptar;
import Usuario.DAO.DataUsuario;
import Usuario.DAO.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class FormUsuario implements Initializable {

    public TextField txtCodigo;
    public TextField txtNombre;
    public TextField txtApellido;
    public TextField txtUsuario;
    public TextField txtPass;
    public TextField txtDpi;
    public Label labelTitulo;
    private String accion="new";

    public Button btnIngresarCliente;
    Login.encriptar encriptar=new encriptar();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Formato formato=new Formato();
        formato.entero(txtDpi,13);
    }
    public void registrarUsuario(ActionEvent actionEvent) {
        //Usuario usuario=new Usuario(0,txtNombre.getText(),txtApellido.getText())
        if (returnUsuario()!=null){
            DataUsuario dataUsuario=new DataUsuario();
            if (dataUsuario.crudUsuario(returnUsuario(),accion)){
                limpiar();
            }
        }
    }
    public void limpiar(){
        txtCodigo.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtUsuario.setText("");
        txtPass.setText("");
        txtDpi.setText("");
    }
    public Usuario returnUsuario(){
        Usuario usuario=new Usuario();
        if (txtCodigo.getText().isEmpty()){
            usuario.setCodigo(0);
        }else
        {
            usuario.setCodigo(Integer.parseInt(txtCodigo.getText()));
        }

        if (txtNombre.getText().isEmpty()){
            Util.Error("Error","El campo nombre se encuentra vacio, ingrese el nombre");
            return  null;
        }else {
            usuario.setNombre(txtNombre.getText());
            if (txtApellido.getText().isEmpty()){
                Util.Error("Error","El campo apellido se encuentra vacio, ingrese el apellido");
                return  null;
            }else{
                usuario.setApellido(txtApellido.getText());
                if (txtUsuario.getText().isEmpty()){
                    Util.Error("Error","El campo nombre de usuario se encuentra vacío, ingrese el usuario");
                    return  null;
                }else{
                    usuario.setUsuario(txtUsuario.getText());
                    if (txtPass.getText().isEmpty()){
                        Util.Error("Error","El campo contraseña se encuentra vacío, favor de ingresar la contraseña");
                        return  null;
                    }else{
                        usuario.setPass(encriptar.getMD5(txtPass.getText()));
                        if (txtDpi.getText().isEmpty()){
                            Util.Error("Error","El campo de DPI se encuentra vacía, favor de llenar");
                            return  null;
                        }else{
                            usuario.setDpi(txtDpi.getText());
                            return usuario;
                        }

                    }
                }
            }

        }
    }
public void pasarUsuario(Usuario usuario){
        if (usuario !=null){
            accion="update";
            labelTitulo.setText("Modificar datos del usuario");
            txtCodigo.setEditable(false);
            txtCodigo.setText(String.valueOf(usuario.getCodigo()));
            txtNombre.setText(usuario.getNombre());
            txtApellido.setText(usuario.getApellido());
            txtUsuario.setText(usuario.getUsuario());
            //txtPass.setText();
            txtDpi.setText(usuario.getDpi());
            btnIngresarCliente.setText("Actualizar usuario");

        }
}

}
