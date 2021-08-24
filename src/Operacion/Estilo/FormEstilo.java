package Operacion.Estilo;

import ClassAux.Util;
import Operacion.Estilo.DAO.Estilo;
import Operacion.Estilo.DAO.EstiloData;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class FormEstilo implements Initializable {
    public TextField txtCodigo;
    public TextField txtNombre;
    public CheckBox cboxActivo;
    public CheckBox cboxNoActivo;
    public Button btnIngresar;
    public Label btnCancelar;
    public Label lblTitulo;
    private  String estado="Activo";
    private  String  accion="new";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        set_estado("Activo");
        cboxNoActivo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                estado="No Activo";
                set_estado(estado);
            }
        });
        cboxActivo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                estado="Activo";
                set_estado(estado);
            }
        });
    }
    public void IngresaEstilo(ActionEvent actionEvent) {
        if (returnEstilo() != null){
            EstiloData estiloData=new EstiloData();
            if (estiloData.crudEStilo(returnEstilo(),accion)){
                limpiar();
            }
        }
    }

    public void Cancelar(MouseEvent event) {
        Stage cerrar = (Stage) btnCancelar.getScene().getWindow();
        cerrar.close();
    }

    private Estilo returnEstilo(){
        Estilo estilo=new Estilo();
        if (txtCodigo.getText().isEmpty()){
            estilo.setCodigo(0);
        }else{
            estilo.setCodigo(Integer.parseInt( txtCodigo.getText()));
        }
        if (txtNombre.getText().isEmpty()){
            Util.Error("Error","Ingrese el nombre del estilo");
            txtNombre.requestFocus();
            return null;
        }else {
            estilo.setNombre(txtNombre.getText());
            estilo.setEstado(estado);
            return estilo;
        }
    }
    public void pasarEStilo(Estilo estilo){
        if (estilo!=null) {
            lblTitulo.setText("Modificar estilo");
            accion="update";
            txtCodigo.setText(String.valueOf(estilo.getCodigo()));
            txtCodigo.setEditable(false);
            txtNombre.setText(estilo.getNombre());
            estado=estilo.getEstado();
            set_estado(estado);
            btnIngresar.setText("Guardar modificación");
        }
    }
    public void set_estado(String estado){
        if (estado.equals("Activo")){
            cboxActivo.setSelected(true);
            cboxNoActivo.setSelected(false);
        }else {
            cboxActivo.setSelected(false);
            cboxNoActivo.setSelected(true);
        }
    }
    private void limpiar(){
        txtCodigo.setText("");
        txtNombre.setText("");
        estado="Activo";
        set_estado(estado);
        accion="new";

    }
}
