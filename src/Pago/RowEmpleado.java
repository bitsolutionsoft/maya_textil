package Pago;

import ClassAux.EstiloBoton;
import ClassAux.SetBotonIcon;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class RowEmpleado  implements Initializable {

    public Label codigo;
    public Label nombre;
    public Label dpi;
    public Label telefono;
    public Label estado;

    public Button btnEliminar;
    public Button btnEditar;
    public HBox rootRow;
    public Label lblcodigo;

    public void setCodigo(int texto) {
        codigo.setText(String.valueOf(texto));
    }

    public void setTelefono(int texto) {telefono.setText(String.valueOf(texto));
    }

    public void setDpi(String texto) {
        dpi.setText(String.valueOf(texto));
    }

    public void setNombre(String texto) {
        nombre.setText(texto);
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
        btnEditar.setVisible(false);
        btnEliminar.setVisible(false);
        codigo.setVisible(false);
        dpi.setVisible(false);
        telefono.setVisible(false);
        estado.setVisible(false);
       lblcodigo.setVisible(false);
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
