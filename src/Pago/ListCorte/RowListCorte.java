package Pago.ListCorte;

import ClassAux.EstiloBoton;
import ClassAux.SetBotonIcon;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class RowListCorte implements Initializable {

    public Label corte;
    public Label estilo;
    public Label cantidad;

    public Label rollo;
    public Label fecha;
    public Label estado;

    public Button btnEliminar;
    public Button btnEditar;
    public HBox rootRow;
    public Label nombreestilo;
    public Label lblidestilo;

    public void setCorte(String texto) {corte.setText(String.valueOf(texto));
    }

    public void setEstilo(int texto) {estilo.setText(String.valueOf(texto)); }
    public void setNombreestilo(String texto) {nombreestilo.setText(String.valueOf(texto)); }

    public void setCantidad(int texto) {
        cantidad.setText(String.valueOf(texto));
    }
    public void setCant_rollo(int texto) {
        rollo.setText(String.valueOf(texto));
    }

    public void setFecha(String texto) {
        fecha.setText(texto);
    }


    public void setEstado(String texto) {
        estado.setText(texto);
        estado.setPadding(new Insets(5));
        if (texto.equals("Activo")){
            estado.setStyle(EstiloBoton.Activo());
            initButton();

        }else{
            estado.setStyle(EstiloBoton.NoActivo());
            initButton();
        }

    }
    public void initialize(URL url, ResourceBundle resourceBundle) {

//esta funcion es para iniciar los botones y sus respectivos iconos
        initButton();
        lblidestilo.setVisible(false);
        estilo.setVisible(false);

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
