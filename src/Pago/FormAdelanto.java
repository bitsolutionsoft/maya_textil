package Pago;

import ClassAux.Formato;
import ClassAux.Util;
import Empleado.DAO.DataEmpleado;
import Empleado.DAO.Empleado;
import Operacion.Estilo.DAO.Estilo;
import Pago.DAO.Adelanto;
import Pago.DAO.DataAdelanto;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class FormAdelanto implements Initializable {

    public TextField txtidadelanto;
    public TextField txtidempleado;
    public TextField txtCantidad;
    public TextField txtConcepto;
    public Button btnIngresarAdelanto;
    public CheckBox Activo;
    public CheckBox Noactivo;
    private String estado = "Pendiente";
    private String accion = "new";
    public Label labelTitulo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        estado("Pendiente");
        txtidadelanto.setEditable(false);

    }




    public void estado(String estado) {
        if (estado.equals("Pendiente")) {
            Activo.setSelected(true);
            Noactivo.setSelected(false);
        } else {
            Noactivo.setSelected(true);
            Activo.setSelected(false);
        }
    }
    public void pasarRegistro(Empleado empleado) {
        if (empleado != null) {
            txtidempleado.setText(String.valueOf(empleado.getCodigo()));
            labelTitulo.setText(String.valueOf(empleado.getNombre()+" "+empleado.getApellido()));
            estado("Pendiente");
        }
    }
    public void pasarRegistro2(Empleado empleado, Adelanto adelanto) {
        if (adelanto != null) {
            accion = "update";
            txtidempleado.setText(String.valueOf(empleado.getCodigo()));
            labelTitulo.setText(String.valueOf(empleado.getNombre()+" "+empleado.getApellido()));
            txtidadelanto.setText(String.valueOf(adelanto.getIdadelanto()));
            txtidempleado.setEditable(false); ///
            txtCantidad.setText(String.valueOf(adelanto.getCantidad()));
            txtConcepto.setText(adelanto.getConcepto());

            estado(adelanto.getEstado());
            btnIngresarAdelanto.setText("Actualizar empleado");
        }
    }
    public void estadoActivo(ActionEvent actionEvent) {
        if (Activo.isSelected()) {
            estado = "Pendiente";
            Noactivo.setSelected(false);
        }
    }

    public void estadoNoActivo(ActionEvent actionEvent) {
        if (Noactivo.isSelected()) {
            estado = "Cancelado";
            Activo.setSelected(false);
        }
    }
    public void registrarAdelanto(ActionEvent actionEvent) {
        if (returnAdelanto()!=null){
            DataAdelanto dataAdelanto=new DataAdelanto();
            if (dataAdelanto.crudAdelanto(returnAdelanto(),accion)){
                limpiar();
            }
        }
    }
    public void limpiar(){
        txtidadelanto.setText("");
        txtidempleado.setText("");
        txtCantidad.setText("");
        txtConcepto.setText("");

    }
    public Adelanto returnAdelanto(){
        Adelanto adelanto=new Adelanto();
        if (txtidadelanto.getText().isEmpty()){
            adelanto.setIdadelanto(0);
        }else{
            adelanto.setIdadelanto(Integer.parseInt(txtidadelanto.getText()));
        }

        if (txtidempleado.getText().isEmpty()){
            Util.Error("Error","El campo nombre se encuentra vacio, ingrese el nombre");
            return  null;
        }else {
            adelanto.setIdempleado(Integer.parseInt(txtidempleado.getText()));
            if (txtCantidad.getText().isEmpty()){
                Util.Error("Error","El campo apellido se encuentra vacio, ingrese el apellido");
                return  null;
            }else{
                adelanto.setCantidad(Float.parseFloat(txtCantidad.getText()));

                    if (txtConcepto.getText().isEmpty()){
                        Util.Error("Error","El campo telefono se encuentra vacío, favor de ingresar un numero de telefono");
                        return  null;
                    }else{
                        adelanto.setConcepto(txtConcepto.getText());
                        adelanto.setEstado(estado);
                        return adelanto;

                    }
                }
            }


        }
    }


