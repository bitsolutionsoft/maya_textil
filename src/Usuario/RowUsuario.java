package Usuario;

import ClassAux.EstiloBoton;
import ClassAux.SetBotonIcon;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class RowUsuario  implements Initializable {
    public Label codigo;
    public Label nombre;
    public Label usuario;
    public Label apellido;
    public Label dpi;


    public Button btnEliminar;
    public Button btnEditar;
    public Button btnUsuario;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        iniciarBoton();
    }

    public  void setCodigo(String texto){codigo.setText(texto);}
    public void setNombre(String texto) { nombre.setText(texto); }
    public void setApellido(String texto) {apellido.setText(texto);}
    public void setUsuario(String texto){usuario.setText(texto);}
    public void setDpi(String texto){dpi.setText(texto);}

    public void iniciarBoton(){
        btnEditar.setGraphic(SetBotonIcon.icono(EstiloBoton.editImg()));
        btnEditar.setStyle(EstiloBoton.Boton());
        btnEditar.setPrefHeight(35);
        btnEditar.setPrefWidth(20);
        btnEliminar.setGraphic(SetBotonIcon.icono(EstiloBoton.deleteImg()));
        btnEliminar.setStyle(EstiloBoton.Boton());
        btnEliminar.setPrefHeight(35);
        btnEliminar.setPrefWidth(20);
        btnUsuario.setGraphic(SetBotonIcon.icono(EstiloBoton.accessImg()));
        btnUsuario.setStyle(EstiloBoton.Boton());
        btnUsuario.setPrefHeight(35);
        btnUsuario.setPrefWidth(20);
    }
}
