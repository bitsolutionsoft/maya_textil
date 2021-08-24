package Login;

import ClassAux.AlertDialog;
import ClassAux.Util;
import Menu.MenuController;
import Usuario.DAO.DataUsuario;
import Usuario.DAO.Usuario;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerLogin implements Initializable {
    public Button btnprueba;
    public ImageView logoimg;
    public TextField txtUsuario;
    public PasswordField txtPass;
    encriptar encriptar=new encriptar();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        logoimg.setImage(new Image("/Img/logo.png"));

        btnprueba.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
             usuarioEncontrado();
            }
        });

        txtPass.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER){
                    usuarioEncontrado();
                }
            }
        });
    }

    public void usuarioEncontrado(){
        if (returnUsuario()!=null){
            DataUsuario dataUsuario=new DataUsuario();
            ArrayList <Usuario> datosUsuario=new ArrayList<>( dataUsuario.viewUsuario(returnUsuario(),"viewuser"));

            if (datosUsuario.size()>0){
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Menu/Menu.fxml"));
                    Parent root = fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setMaximized(true);
                    MenuController menuController=fxmlLoader.getController();
                    menuController.pasarDatosUsuario(datosUsuario);
                    stage.show();
                    Stage cerrar = (Stage) btnprueba.getScene().getWindow();
                    cerrar.close();
                }catch (IOException e){
                    e.printStackTrace();
                }

            }else{
                AlertDialog aler=new AlertDialog();
                aler.alert("Error al ingresar","Ingrese el usuario y la contraseña correcta");
            }

        }
    }
    public Usuario returnUsuario(){
        Usuario usuario=new Usuario();
        if (txtUsuario.getText().isEmpty()){
            Util.Error("Error","Ingrese su nombre de usuario");
            return  null;
        }else{
            usuario.setUsuario(txtUsuario.getText().trim());
            if (txtPass.getText().isEmpty()){
                Util.Error("Error","Ingrese su contraseña");
                return null;
            }else {
                usuario.setCodigo(0);
                usuario.setNombre("");
                usuario.setApellido("");
                usuario.setPass(encriptar.getMD5(txtPass.getText().trim()));
                usuario.setDpi("");
                return usuario;
            }
        }

    }
    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }
}

//usuario y pass_______  oscaga         12345
