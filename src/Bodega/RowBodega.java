package Bodega;

import ClassAux.EstiloBoton;
import ClassAux.SetBotonIcon;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class RowBodega implements Initializable {
    public HBox rootRow;
    public Label codigo;
    public Label codigo_tela;
    public Label tipo;
    public Label color;
    public Label cantidad;
    public Label precio;
    public Label total;
    public Button btnEliminar;
    public Button btnEditar;
    public Label lblIdBodega;

    public  void setCodigo(String texto){codigo.setText(texto);}
    public  void setCodigo_tela(String texto){codigo_tela.setText(texto);}
    public  void setTipo(String texto){tipo.setText(texto);}
    public  void setColor(String texto){color.setText(texto);}
    public  void setCantidad(String texto){cantidad.setText(texto);}
    public  void setPrecio(String texto){precio.setText(texto);}
    public  void setTotal(String texto){total.setText(texto);}
    public void setAncho(Double ancho){rootRow.setPrefWidth(ancho);}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initButton();
        lblIdBodega.setVisible(false);
        codigo.setVisible(false);
    }



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
