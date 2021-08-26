package Bodega;

import Bodega.DAO.Bodega;
import Bodega.DAO.DataBodega;
import ClassAux.Formato;
import ClassAux.Util;
import Empleado.DAO.DataEmpleado;
import Empleado.DAO.Empleado;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class FormBodega implements Initializable {
    public TextField txtCodigoTela;
    public TextField txtTipo;
    public TextField txtColor;
    public TextField txtPrecio;
    public TextField txtCantidad;
    public TextField txtTotal;
    public Label lblIdBodega;
    public Label labelTitulo;
    public Button btnIngresarTela;
    private String accion = "new";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
txtTotal.setEditable(false);
        lblIdBodega.setVisible(false);

        validarFormato();
        txtCantidad.textProperty().addListener((prop,old,text)->{
            if (text !=null && !text.equals("")) {
                if (!txtPrecio.getText().isEmpty()) {
                    txtTotal.setText(String.valueOf(Integer.parseInt(text) * Float.parseFloat(txtPrecio.getText())));
                }
            }
        });
        txtPrecio.textProperty().addListener((prop,old,text)->{
            if (text !=null && !text.equals("")) {
                if (!txtCantidad.getText().isEmpty()) {
                    txtTotal.setText(String.valueOf(Integer.parseInt(txtCantidad.getText()) * Float.parseFloat(text)));
                }
            }
        });

    }

    public  void validarFormato(){
        Formato formato=new Formato();
        formato.entero(txtCantidad,10);
        formato.decimal(txtPrecio);


    }

    public void pasarRegistro(Bodega bodega) {
        if (bodega != null) {
            accion = "update";
            labelTitulo.setText("Modificar datos ");
            lblIdBodega.setText(String.valueOf(bodega.getIdBodega()));
            txtCodigoTela.setEditable(false); ///
            txtCodigoTela.setText(bodega.getCodigo_tela());
            txtTipo.setText(bodega.getTipo());
            txtColor.setText(bodega.getColores());
            txtCantidad.setText(String.valueOf(bodega.getCantidad()));
            txtPrecio.setText(String.valueOf(bodega.getPrecio()));
            txtTotal.setText(String.valueOf(bodega.getTotal()));
            btnIngresarTela.setText("Actualizar datos");

        }
    }
    public void registrarDatos(ActionEvent actionEvent) {
        if (returnBodega()!=null){
            DataBodega dataBodega=new DataBodega();
            if (dataBodega.crudBodega(returnBodega(),accion)){
                limpiar();
            }
        }
    }

    public void limpiar(){
        txtCodigoTela.setText("");
        txtTipo.setText("");
        txtColor.setText("");
        txtCantidad.setText("0");
        txtPrecio.setText("0");
        txtTotal.setText("");
    }
    public Bodega returnBodega(){
        Bodega bodega=new Bodega();
        if (lblIdBodega.getText().isEmpty()){
            bodega.setIdBodega(0);
        }else{
            bodega.setIdBodega(Integer.parseInt(lblIdBodega.getText()));
        }

        if (txtCodigoTela.getText().isEmpty()){
            Util.Error("Error","El campo codigo de tela se encuentra vacío, ingrese el codigo");
            return  null;
        }else {
            bodega.setCodigo_tela(txtCodigoTela.getText());
            if (txtTipo.getText().isEmpty()){
                Util.Error("Error","El campo tipo de tela se encuentra vacóo, ingrese el tipo de tela");
                return  null;
            }else{
                bodega.setTipo(txtTipo.getText());
                if (txtColor.getText().isEmpty()){
                    Util.Error("Error","El campo color  se encuentra vacío, ingrese el color");
                    return  null;
                }else{
                    bodega.setColores(txtColor.getText());
                    if (txtCantidad.getText().isEmpty()){
                        Util.Error("Error","El campo cantidad se encuentra vacío, favor de ingresar la cantidad");
                        return  null;
                    }else{
                        bodega.setCantidad(Integer.parseInt(txtCantidad.getText().trim()));

                            bodega.setPrecio(Float.parseFloat("0.00"));
                            bodega.setTotal(Float.parseFloat("0.00"));
                            return bodega;

                        }


                }
            }


        }
    }



}
