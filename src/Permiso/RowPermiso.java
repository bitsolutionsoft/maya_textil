package Permiso;

import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class RowPermiso implements Initializable {
    public Label lblCodigo;
    public Label codigo;
    public Label nombre;
    public CheckBox cboxAcceso;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    lblCodigo.setVisible(false);
    codigo.setVisible(false);
    }

    public void setCodigo(String texto) {
        codigo.setText(texto);
    }

    public void setNombre(String texto) {
        nombre.setText(texto);
    }

    public void setCboxAcceso(int texto) {
       if (texto==0){
           cboxAcceso.setSelected(false);
       }
       if (texto==1){
           cboxAcceso.setSelected(true);
       }
    }

}
