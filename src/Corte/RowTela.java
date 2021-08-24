package Corte;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class RowTela implements Initializable {
    public Label idrollo;
    public Label codigotela;
    public Label tipo;
    public Label colores;
    public Label cantidad;
    public Label btnEliminar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idrollo.setVisible(false);

    }
    public void setIdrollo(String texto) {idrollo.setText(texto);}
    public void setCodigotela(String texto) {codigotela.setText(texto);}
    public void setTipo(String texto) {tipo.setText(texto);}
    public void setColores(String texto) {colores.setText(texto);}
    public void setCantidad(String texto) {cantidad.setText(texto);}
}
