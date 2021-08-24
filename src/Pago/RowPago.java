package Pago;

import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class RowPago implements Initializable {
    public Label codigo;
    public Label nombre;
    public Label cantidad;
    public Label precio;
    public Label total;
    
    public ImageView estadoPago;
    public Label btnEditar;
    public Label btnEliminar;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        codigo.setVisible(false);
    }
    public void setCodigo(String texto){codigo.setText(texto);}
    public void setNombre(String texto){nombre.setText(texto);}
    public void setCantidad(String texto){cantidad.setText(texto);}
    public void setPrecio(String texto){precio.setText(texto);}
    public void setTotal(String texto){total.setText(texto);}
    public void setEstadoPago(String texto){
        if (texto.equals("Cancelado")){
           estadoPago.setImage(new Image("/Img/check.png"));
        }else {
            estadoPago.setImage(new Image("/Img/nocheck.png"));
        }

    }
}
