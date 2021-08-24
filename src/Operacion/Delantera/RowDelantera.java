package Operacion.Delantera;

import ClassAux.EstiloBoton;
import ClassAux.SetBotonIcon;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.LineNumberInputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class RowDelantera implements Initializable {
    public Label codigo;
    public Label nombre;
    public Label precio;
    public Label variacion;
    public Button btnEliminar;
    public Button btnEditar;
    public Label lblCodigo;
    public HBox rootRow;

    public void setCodigo(String texto){codigo.setText(texto);}
    public  void setNombre(String texto) {nombre.setText(texto);}
    public  void setPrecio(String texto){precio.setText(texto);}
    public void setVariacion(String texto){variacion.setText(texto);}

    public void setAncho(Double ancho){rootRow.setPrefWidth(ancho);}
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblCodigo.setVisible(false);
        codigo.setVisible(false);
        initButton();
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
