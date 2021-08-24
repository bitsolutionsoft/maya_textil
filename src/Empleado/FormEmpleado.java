package Empleado;

import ClassAux.Formato;
import ClassAux.Util;
import Empleado.DAO.DataEmpleado;
import Empleado.DAO.Empleado;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class FormEmpleado implements Initializable {
    public TextField txtCodigo;
    public TextField txtNombre;
    public TextField txtApellido;
    public TextField txtDpi;
    public TextField txtTelefono;
    public Button btnIngresarEmpleado;
    public CheckBox Activo;
    public CheckBox Noactivo;
    private String estado = "Activo";
    private String accion = "new";
    public Label labelTitulo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        estado("Activo");
        txtCodigo.setEditable(false);
        validarFormato();
        Formato formato=new Formato();
        formato.entero(txtDpi,13);

    }

    public  void validarFormato(){
        Formato formato=new Formato();
        txtCodigo.setEditable(false);
        formato.entero(txtDpi,13);
        formato.entero(txtTelefono,8);

    }

    public void pasarRegistro(Empleado empleado) {
        if (empleado != null) {
            accion = "update";
            labelTitulo.setText("Modificar datos del empleado");
            txtCodigo.setText(String.valueOf(empleado.getCodigo()));
            txtCodigo.setEditable(false); ///
            txtNombre.setText(empleado.getNombre());
            txtApellido.setText(empleado.getApellido());
            txtDpi.setText(empleado.getDpi());
            txtTelefono.setText(String.valueOf(empleado.getTelefono()));
            estado(empleado.getEstado());
            btnIngresarEmpleado.setText("Actualizar empleado");

        }
    }


    public void estado(String estado) {
        if (estado.equals("Activo")) {
            Activo.setSelected(true);
            Noactivo.setSelected(false);
        } else {
            Noactivo.setSelected(true);
            Activo.setSelected(false);
        }
    }

    public void estadoActivo(ActionEvent actionEvent) {
        if (Activo.isSelected()) {
            estado = "Activo";
            Noactivo.setSelected(false);
        }
    }

    public void estadoNoActivo(ActionEvent actionEvent) {
        if (Noactivo.isSelected()) {
            estado = "No Activo";
            Activo.setSelected(false);
        }
    }
    public void registrarEmpleado(ActionEvent actionEvent) {
        if (returnEmpleado()!=null){
            DataEmpleado dataEmpleado=new DataEmpleado();
            if (dataEmpleado.crudEmpleado(returnEmpleado(),accion)){
                limpiar();
            }
        }
    }
    public void limpiar(){
        txtCodigo.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtDpi.setText("");
        txtTelefono.setText("");
    }
    public Empleado returnEmpleado(){
        Empleado empleado=new Empleado();
        if (txtCodigo.getText().isEmpty()){
            empleado.setCodigo(0);
        }else{
            empleado.setCodigo(Integer.parseInt(txtCodigo.getText()));
        }

        if (txtNombre.getText().isEmpty()){
            Util.Error("Error","El campo nombre se encuentra vacio, ingrese el nombre");
            return  null;
        }else {
            empleado.setNombre(txtNombre.getText());
            if (txtApellido.getText().isEmpty()){
                Util.Error("Error","El campo apellido se encuentra vacio, ingrese el apellido");
                return  null;
            }else{
                empleado.setApellido(txtApellido.getText());
                if (txtDpi.getText().isEmpty()){
                    Util.Error("Error","El campo DPI del empleado se encuentra vacío, ingrese el dpi");
                    return  null;
                }else{
                    empleado.setDpi(txtDpi.getText());
                    if (txtTelefono.getText().isEmpty()){
                        Util.Error("Error","El campo telefono se encuentra vacío, favor de ingresar un numero de telefono");
                        return  null;
                    }else{
                        empleado.setTelefono(Integer.parseInt(txtTelefono.getText().trim()));
                        empleado.setEstado(estado);
                        return empleado;

                    }
                }
            }


        }
    }

}
