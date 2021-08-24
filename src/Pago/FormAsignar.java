package Pago;

import ClassAux.Formato;
import ClassAux.Util;
import Corte.DAO.Corte;
import Empleado.ControllerEmpleado;
import Empleado.FormEmpleado;
import Empleado.EmpleadoCell;
import Empleado.RowEmpleado;
import Empleado.DAO.DataEmpleado;
import Empleado.DAO.Empleado;
import Operacion.DAO.DataOperacion;
import Operacion.DAO.Operacion;
import Operacion.Delantera.DelanteraCotrolller;
import Operacion.Estilo.DAO.Estilo;
import Operacion.Estilo.DAO.EstiloData;
import Operacion.Estilo.EstiloController;
import Pago.DAO.DataDetallePago;
import Pago.DAO.DetallePago;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FormAsignar implements Initializable {
    public TextField txtOperacion;
    public TextField txtEmpleado;
    public TextField txtDescuento;
    public TextField txtCantidad;
    public Label lbTotal;
    public Label lblidpago;
    public Label lblidetallepago;
    public Label lblEmpleado;
    public Label lblIdOperacion;
    public CheckBox chbPendiente;
    public CheckBox chbCancelado;
    public ComboBox <String> cbxPrecio;
    public Button btnOperacion;
    public Button btnIngresarEmpleado;
    public Label lblidEmpleado;


    private String estado = "Pendiente";
    private String accion = "new";
    private int idtipo  =0;
    private String nombretipo  = "";
    public Label labelTitulo;
    private  Empleado empleadoSeleccionado;
    private  Estilo estiloSeleccionado;
   // private Corte corteSeleccionado;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnOperacion.setGraphic(new ImageView("/Img/down.png"));
        lblidetallepago.setVisible(false);
        lblidEmpleado.setVisible(false);
        lblidpago.setVisible(false);
        lblIdOperacion.setVisible(false);
        txtCantidad.textProperty().addListener((prop,old,text)->{
            if (text!=null && !text.equals("")){
                if (cbxPrecio.getSelectionModel().getSelectedItem()!=null){
                    lbTotal.setText(String.valueOf(Integer.parseInt(text)*Float.parseFloat(cbxPrecio.getSelectionModel().getSelectedItem())));
                }
            }
        });
        cbxPrecio.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
              if (cbxPrecio.getSelectionModel().getSelectedItem()!=null){
                  if (!txtCantidad.getText().isEmpty()){
                      lbTotal.setText(String.valueOf(Integer.parseInt(txtCantidad.getText())*Float.parseFloat(cbxPrecio.getSelectionModel().getSelectedItem())));
                  }
              }
            }
        });

    }

    public  void validarFormato(){
        Formato formato=new Formato();
        formato.entero(txtCantidad,13);


    }

    public void pasarRegistro(Empleado empleado, Estilo estilo, int idpago, int idtipos,String nombretipos) {
        if (empleado != null) {
            lblidpago.setText(String.valueOf(idpago));
            lblEmpleado.setText(String.valueOf(empleado.getNombre()));
            lblidEmpleado.setText(String.valueOf(empleado.getCodigo()));
            estiloSeleccionado=estilo;
          //  corteSeleccionado=corte;
            idtipo=idtipos;
            nombretipo=nombretipos;
estado("Pendiente");
        }
    }
    public void pasarRegistroEditar( DetallePago detallePago) {
       if ( detallePago!=null) {

           Empleado emp=returnEmpledo(detallePago);
            lblidetallepago.setText(String.valueOf(detallePago.getIddetalle()));
            lblidpago.setText(String.valueOf(detallePago.getIdpago()));
            lblEmpleado.setText(String.valueOf(emp.getNombre()));
            lblidEmpleado.setText(String.valueOf(emp.getCodigo()));
            txtCantidad.setText(String.valueOf(detallePago.getCantidad()));
           txtDescuento.setText(String.valueOf(detallePago.getDescuento()));
           lbTotal.setText(String.valueOf(detallePago.getTotal()));
           estado("Pendiente");
           accion ="update";

            Operacion operacion1=returnOperacion(detallePago);
            llenarOperacion(operacion1);
            estiloSeleccionado=returnEstilo(operacion1);
            idtipo=operacion1.getCodigoTipo();
            nombretipo=tipoOperacion(operacion1.getCodigoTipo());
            llenarPrecio2(operacion1.getPrecio(), operacion1.getVariacion(), String.valueOf(detallePago.getPrecio()));


        }
    }


    public Estilo returnEstilo(Operacion operacion){
        Estilo estilo=null;
        EstiloData data=new EstiloData();
        ObservableList<Estilo> list=FXCollections.observableArrayList(data.viewEstilo(new Estilo(operacion.getCodigoEstilo(),"",""),"viewone"));
        for (int i=0; i<list.size();i++){
            Estilo est=new Estilo();
            est.setCodigo(list.get(i).getCodigo());
            est.setNombre(list.get(i).getNombre());
            est.setEstado(list.get(i).getEstado());
estilo=est;
        }
return estilo;
    }

    public Empleado returnEmpledo(DetallePago dt){
        Empleado emp = null;
        DataEmpleado dataEmpleado=new DataEmpleado();
        ObservableList<Empleado> list=FXCollections.observableArrayList(dataEmpleado.viewEmpleado(new Empleado(dt.getIdempleado(),"","","",0,""),"viewone"));

            for (int i=0; i<list.size();i++){
                Empleado empleado=new Empleado();
                empleado.setCodigo(list.get(i).getCodigo());
                empleado.setNombre(list.get(i).getNombre());
                empleado.setApellido(list.get(i).getApellido());
                empleado.setDpi(list.get(i).getDpi());
                empleado.setTelefono(list.get(i).getTelefono());
                empleado.setEstado(list.get(i).getEstado());
emp=empleado;
        }

        return emp;

    }
    public Operacion returnOperacion(DetallePago detallePago){
Operacion operacion=null;
        DataOperacion data=new DataOperacion();
        ObservableList<Operacion> list=FXCollections.observableArrayList(data.viewOperacion(new Operacion(detallePago.getIdoperacion(),0,0,"",0,0),"viewone"));
        for (int i=0;i<list.size();i++){
            Operacion op=new Operacion();
            op.setCodigo(list.get(i).getCodigo());
            op.setCodigoEstilo(list.get(i).getCodigoEstilo());
            op.setCodigoTipo(list.get(i).getCodigoTipo());
            op.setNombre(list.get(i).getNombre());
            op.setPrecio(list.get(i).getPrecio());
            op.setVariacion(list.get(i).getVariacion());
           operacion=op;
        }
return operacion;
    }





    public void estado(String estado) {
        if (estado.equals("Pendiente")) {
            chbPendiente.setSelected(true);
            chbCancelado.setSelected(false);
        } else {
            chbPendiente.setSelected(false);
            chbCancelado.setSelected(true);
        }
    }

    public void estadoPendiente(ActionEvent actionEvent) {
        if (chbPendiente.isSelected()) {
            estado = "Pendiente";
            chbCancelado.setSelected(false);
        }
    }

    public void estadoCancelado(ActionEvent actionEvent) {
        if (chbCancelado.isSelected()) {
            estado = "Cancelado";
            chbPendiente.setSelected(false);
        }
    }

    public void limpiar(){
        txtCantidad.setText("");
    }
    public DetallePago returnDetallePago() {
        DetallePago detalle = new DetallePago();
        if (lblidetallepago.getText().isEmpty()) {
            detalle.setIddetalle(0);
            detalle.setIdempleado(Integer.parseInt(lblidEmpleado.getText()));
            detalle.setIdpago(Integer.parseInt(lblidpago.getText()));
        } else {
            detalle.setIdempleado(Integer.parseInt(lblidEmpleado.getText()));
            detalle.setIdpago(Integer.parseInt(lblidpago.getText()));
            detalle.setIddetalle(Integer.parseInt(lblidetallepago.getText()));
        }

        if (txtDescuento.getText().isEmpty()){
            detalle.setDescuento(0);
        }else{
            detalle.setDescuento(Float.parseFloat(txtDescuento.getText()));
        }

        if (txtCantidad.getText().isEmpty()) {
            Util.Error("Error", "El campo cantidad se encuentra vacio, ingrese la cantidad");
            return null;
        } else {
            detalle.setCantidad(Integer.parseInt(txtCantidad.getText()));
            if (txtOperacion.getText().isEmpty()) {
                Util.Error("Error", "El campo Operacion se encuentra vacio, Seleccione la operación");
                return null;
            } else {
                detalle.setIdoperacion(Integer.parseInt(lblIdOperacion.getText()));
                if (cbxPrecio.getSelectionModel().getSelectedItem()==null){
                    Util.Error("Error", "El campo precio se encuentra vacio, Seleccione el precio");
                    return null;
                }else{

                    detalle.setPrecio(Float.parseFloat(cbxPrecio.getSelectionModel().getSelectedItem()));
                    detalle.setTotal(Float.parseFloat(lbTotal.getText()));
                    detalle.setEstado(estado);
                    return detalle;
                }

            }
        }
    }

    public void AbrirOperacion(ActionEvent actionEvent) {
        try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Operacion/Delantera/Delantera.fxml"));
                Parent parent = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            DelanteraCotrolller delanteraCotrolller = fxmlLoader.getController();
            delanteraCotrolller.pasarEstilo(estiloSeleccionado,nombretipo);
            delanteraCotrolller.listDelanteras.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getClickCount()==1 && event.getButton()==MouseButton.PRIMARY){
                        Operacion operacion=delanteraCotrolller.listDelanteras.getSelectionModel().getSelectedItem();
                        llenarOperacion(operacion);
                        llenarPrecio(operacion.getPrecio(),operacion.getVariacion());
                        Stage cerrar=(Stage)delanteraCotrolller.listDelanteras.getScene().getWindow();
                        cerrar.close();

                    }
                }
            });
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

   public  void llenarOperacion(Operacion operacion){
        lblIdOperacion.setText(String.valueOf(operacion.getCodigo()));
        txtOperacion.setText(operacion.getNombre());
   }



    public void registrarTarea(ActionEvent actionEvent) {
        if (returnDetallePago()!=null){
            DataDetallePago detallePago=new DataDetallePago();
            if (
            detallePago.crudDetallePago(returnDetallePago(),accion)){
                limpiar();
            }

        }
    }
    public  void llenarPrecio(float precio,float variacion){
        ObservableList<String> list= FXCollections.observableArrayList(String.valueOf(precio),String.valueOf(variacion));
        cbxPrecio.setItems(list);
        cbxPrecio.getSelectionModel().selectFirst();
    }
    public  void llenarPrecio2(float precio,float variacion,String precioseleccionado){
        ObservableList<String> list= FXCollections.observableArrayList(String.valueOf(precio),String.valueOf(variacion));
        cbxPrecio.setItems(list);
        cbxPrecio.getSelectionModel().select(precioseleccionado);
    }
    public String tipoOperacion(int idtipo){
        String tipo="";
        switch (idtipo){
            case  1:
                tipo="Delantera";
                break;
            case  2:
                tipo="Trasera";
                break;
            case  3:
                tipo="Ensamble";
                break;
            case  4:
                tipo="Extra";
                break;
        }
        return  tipo;
    }
}
