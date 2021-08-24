package Operacion;

import ClassAux.Util;
import Operacion.DAO.DataOperacion;
import Operacion.DAO.Operacion;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class FormOperacion implements Initializable {
    public TextField txtcodigo;
    public TextField txtNombre;
    public TextField txtPrecio;
    public TextField txtVariacion;
    public Button btnIngresar;
    public Label btnCancelar;
    public Label lblTitulo;
    private  int codigo_tipo=0,codigo_estilo=0;
    private String accion="new";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public Operacion returnOperacion(){
        Operacion operacion=new Operacion();
        if (txtcodigo.getText().isEmpty()){
            operacion.setCodigo(0);
        }else {
            operacion.setCodigo(Integer.parseInt(txtcodigo.getText()));
        }
        if (txtVariacion.getText().isEmpty()){
            operacion.setVariacion(0);
        }else{
            operacion.setVariacion(Float.parseFloat(txtVariacion.getText()));
        }

        if (txtNombre.getText().isEmpty()){
            Util.Error("Error","Ingrese el nombre de la operacion");
            return null;
        }else{
            operacion.setNombre(txtNombre.getText());
            if (txtPrecio.getText().isEmpty()){
                Util.Error("Error","Ingrese el precio de la operacion");
                return null;
            }else {
                operacion.setCodigoEstilo(codigo_estilo);
                operacion.setCodigoTipo(codigo_tipo);
                operacion.setPrecio(Float.parseFloat(txtPrecio.getText()));
                return operacion;
            }
        }
    }
    private void limpiar(){
        txtcodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
        txtVariacion.setText("");

    }
    public void IngresarOperacion(ActionEvent actionEvent) {
        if (returnOperacion() !=null){
            DataOperacion dataOperacion=new DataOperacion();
            if (dataOperacion.crudOperacion(returnOperacion(),accion)){
                limpiar();
            }
        }
    }

    public void Cancelar(MouseEvent event) {
        Stage stage=(Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
    public void pasarDatos(Operacion operacion,String acciones,String tipo, int idtipo, int idestilo){
        codigo_estilo=idestilo;
        codigo_tipo=idtipo;
        accion=acciones;
        switch (tipo){
            case "Delantera":
                if (operacion ==null){
                    lblTitulo.setText("Ingresar operación de la delantera");
                    btnIngresar.setText("Ingresar");
                    txtcodigo.setEditable(false);
                }else{
                    lblTitulo.setText("Modificar operación de la delantera");
                    btnIngresar.setText("Guardar Modificacón");
                    txtcodigo.setEditable(false);
                    txtcodigo.setText(String.valueOf(operacion.getCodigo()));
                    txtNombre.setText(operacion.getNombre());
                    txtPrecio.setText(String.valueOf( operacion.getPrecio()));
                    txtVariacion.setText(String.valueOf( operacion.getVariacion()));
                }

                break;
            case "Trasera":
                if (operacion ==null){
                    lblTitulo.setText("Ingresar operación de la Trasera");
                    btnIngresar.setText("Ingresar");
                    txtcodigo.setEditable(false);
                }else{
                    lblTitulo.setText("Modificar operación de la Trasera");
                    btnIngresar.setText("Guardar Modificacón");
                    txtcodigo.setEditable(false);
                    txtcodigo.setText(String.valueOf(operacion.getCodigo()));
                    txtNombre.setText(operacion.getNombre());
                    txtPrecio.setText(String.valueOf( operacion.getPrecio()));
                    txtVariacion.setText(String.valueOf( operacion.getVariacion()));
                }
                    break;
            case "Ensamble":
                if (operacion ==null){
                    lblTitulo.setText("Ingresar operación del Ensamble");
                    btnIngresar.setText("Ingresar");
                    txtcodigo.setEditable(false);
                }else{
                    lblTitulo.setText("Modificar operación del Ensamble");
                    btnIngresar.setText("Guardar Modificacón");
                    txtcodigo.setEditable(false);
                    txtcodigo.setText(String.valueOf(operacion.getCodigo()));
                    txtNombre.setText(operacion.getNombre());
                    txtPrecio.setText(String.valueOf( operacion.getPrecio()));
                    txtVariacion.setText(String.valueOf( operacion.getVariacion()));
                }
                break;
        }

    }
}
