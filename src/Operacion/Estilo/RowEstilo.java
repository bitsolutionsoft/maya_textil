package Operacion.Estilo;

import ClassAux.EstiloBoton;
import ClassAux.SetBotonIcon;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class RowEstilo implements Initializable {
    public Label lblCodigo;
    public Label codigo;
    public Label nombre;
    public Label estado;
    public Button btnEliminar;
    public Button btnEditar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblCodigo.setVisible(false);
        codigo.setVisible(false);
        initButton();
    }
    public void setCodigo(String texto){codigo.setText(texto);}
    public void setNombre(String texto){nombre.setText(texto);}
    public void setEstado(String texto){
        estado.setText(texto);
        estado.setPadding(new Insets(5));
        if (texto.equals("Activo")) {
            estado.setStyle(EstiloBoton.Activo());
            initButton();
        }else{
            estado.setStyle(EstiloBoton.NoActivo());
            initButton();
        }
    }
    public void initButton(){
        btnEditar.setGraphic(SetBotonIcon.icono(EstiloBoton.editImg()));
        btnEditar.setStyle(EstiloBoton.Boton());
        btnEditar.setPrefHeight(35);
        btnEditar.setPrefWidth(20);
        btnEliminar.setGraphic(SetBotonIcon.icono(EstiloBoton.deleteImg()));
        btnEliminar.setStyle(EstiloBoton.Boton());
        btnEliminar.setPrefHeight(35);
        btnEliminar.setPrefWidth(20);
    }
}
