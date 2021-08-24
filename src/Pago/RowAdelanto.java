package Pago;

import ClassAux.EstiloBoton;
import ClassAux.SetBotonIcon;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class RowAdelanto implements Initializable {

    public Label idadelanto;
    public Label idempleado;
    public Label concepto;
    public Label cantidad;
    public Label fecha;

    public Button btnEliminar;
    public Button btnEditar;
    public HBox rootRow;
    public ImageView estado;

    public void setIdadelanto(int texto) {
        idadelanto.setText(String.valueOf(texto));
    }

    public void setIdempleado(int texto) {idempleado.setText(String.valueOf(texto));
    }

    public void setConcepto(String texto) {
        concepto.setText(String.valueOf(texto));
    }

    public void setCantidad(String texto) {
        cantidad.setText(texto);
    }
    public void setFecha(String texto) {
        fecha.setText(texto);
    }
    public void setEstado(String texto){
        if (texto.equals("Cancelado")){
            estado.setImage(new Image("/Img/check.png"));
        }else {
            estado.setImage(new Image("/Img/nocheck.png"));
        }

    }
    public void initialize(URL url, ResourceBundle resourceBundle) {

//esta funcion es para iniciar los botones y sus respectivos iconos
        initButton();

    }
    public void setAncho(Double ancho){rootRow.setPrefWidth(ancho);}


    //colocar icono, estilo y tamaño de los botones
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
