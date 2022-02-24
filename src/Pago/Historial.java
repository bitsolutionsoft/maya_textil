package Pago;

import ClassAux.AlertDialog;
import ClassAux.SizeColumnTable;
import Corte.Pdf.imprimir;
import Empleado.DAO.DataEmpleado;
import Empleado.DAO.Empleado;
import Informe.DAO.ResumenCorte;
import Pago.DAO.*;
import Pago.Factura.ConstanciaPago;
import Pago.Factura.ImprimirVale;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Historial implements Initializable {
    static ObservableList<DetallePago> listdatellePago;
    static FilteredList<DetallePago> filterDetallePago;
    //public ListView<Empleado> listEmpleado;
    //public ListView<DetallePago>listPago;
    //public ListView<Adelanto>listCancelado;

    public Label labelTitulo;
    public TextField txtBuscar;
    public ComboBox<Empleado> cbxEmpleado;
    public Button btnReimprimir;
    public TableView <DetallePago> tblPago;
    public TableColumn<DetallePago,String> cellIdpago;
    public TableColumn<DetallePago,String> cellIdcorte;
    public TableColumn<DetallePago,String> cellNombre;
    public TableColumn<DetallePago,String> cellCantidad;
    public TableColumn<DetallePago,String> cellFecha;
    public TableColumn<DetallePago,String> cellprecio;
    public TableColumn<DetallePago,String> cellEstado;
    public TableColumn<DetallePago,String> cellTotal;

    public TableView<Adelanto> tblAdelanto;
    public TableColumn<Adelanto,String> cellIdelanto;
    public TableColumn<Adelanto,String> cellAempleado;
    public TableColumn<Adelanto,String> cellAcantidad;
    public TableColumn<Adelanto,String> cellConcepto;
    public TableColumn<Adelanto,String> cellAfecha;
    public TableColumn<Adelanto,String> cellAestado;


    private Empleado Seleccionado;
    static ObservableList<Adelanto> listdatelleAdelanto;
    static FilteredList<Adelanto> filterDetalleAdelanto;
    SizeColumnTable size_tabla=new SizeColumnTable();
    AlertDialog alertDialog=new AlertDialog();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        iniciarTabla();


       cbxEmpleado.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent actionEvent) {
               Empleado empleado= cbxEmpleado.getSelectionModel().getSelectedItem();
               llenarAdelanto(empleado);
               llenarCancelado(empleado);
           }
       });
       btnReimprimir.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent actionEvent) {
               try{
                   FXMLLoader loader =new FXMLLoader(getClass().getResource("/Pago/ReImprimir.fxml"));
                   Parent parent=loader.load();
                   Stage stage=new Stage();
                   stage.setTitle("Reimprimir  comrpobante");
                   stage.getIcons().add(new Image("/Img/icon.png"));
                   stage.setScene(new Scene(parent));
                   stage.show();
               }catch (IOException e){
                   alertDialog.alert("Hubo un error: ",""+e);
                  e.printStackTrace();
               }
           }
       });
    }
    public void iniciarTabla(){
        cellIdpago=new TableColumn<>("No. pago");
        cellIdcorte=new TableColumn<>("Código corte");
        cellNombre=new TableColumn<>("Operación");
        cellCantidad=new TableColumn<>("Cantidad");
        cellFecha =new TableColumn<>("Fecha");
        cellprecio=new TableColumn<>("Precio");
        cellTotal=new TableColumn<>("Total");
        cellEstado=new TableColumn<>("Estado");

        cellNombre.setPrefWidth(250);
        cellIdpago.setCellValueFactory(new PropertyValueFactory<DetallePago,String>("idpago"));
        cellIdcorte.setCellValueFactory(new PropertyValueFactory<DetallePago,String>("idcorte"));
        cellNombre.setCellValueFactory(new PropertyValueFactory<DetallePago,String>("nombre"));
        cellCantidad.setCellValueFactory(new PropertyValueFactory<DetallePago,String>("cantidad"));
        cellprecio.setCellValueFactory(new PropertyValueFactory<DetallePago,String>("precio"));
        cellFecha.setCellValueFactory(new PropertyValueFactory<DetallePago,String>("fecha"));
        cellTotal.setCellValueFactory(new PropertyValueFactory<DetallePago,String>("total"));
        cellEstado.setCellValueFactory(new PropertyValueFactory<DetallePago,String>("estado"));
        tblPago.setEditable(true);
        tblPago.getColumns().addAll(cellIdpago,cellIdcorte, cellNombre,cellCantidad,cellprecio, cellFecha ,cellTotal,cellEstado);
        Platform.runLater(()-> size_tabla.ajustarColumna(tblPago));

        cellIdelanto =new TableColumn<>("Código Adenlanto");
        cellAempleado =new TableColumn<>("Código empleado");
        cellAcantidad =new TableColumn<>("Cantidad");
        cellConcepto =new TableColumn<>("Concepto");
        cellAfecha =new TableColumn<>("Fecha");
        cellAestado =new TableColumn<>("Estado");

        cellConcepto.setPrefWidth(250);
        cellIdelanto.setCellValueFactory(new PropertyValueFactory<Adelanto,String>("idadelanto"));
        cellAempleado.setCellValueFactory(new PropertyValueFactory<Adelanto,String>("idempleado"));
        cellAcantidad.setCellValueFactory(new PropertyValueFactory<Adelanto,String>("cantidad"));
        cellAfecha.setCellValueFactory(new PropertyValueFactory<Adelanto,String>("fecha"));
        cellConcepto.setCellValueFactory(new PropertyValueFactory<Adelanto,String>("concepto"));
        cellAestado.setCellValueFactory(new PropertyValueFactory<Adelanto,String>("estado"));
        tblAdelanto.setEditable(true);
        tblAdelanto.getColumns().addAll(cellAcantidad,cellConcepto,cellAfecha,cellAestado);
        Platform.runLater(()-> size_tabla.ajustarColumna(tblAdelanto));
    }

    public void pasarRegistro(ObservableList<Empleado> empleado) {
        ObservableList empleados=FXCollections.observableArrayList(empleado);
        cbxEmpleado.setItems(empleados);
      //  if (empleado != null) {
          //  labelTitulo.setText(String.valueOf(empleado.getNombre()+" "+empleado.getApellido()));
        //    Seleccionado=empleado;
           // llenarCancelado(listPago,Seleccionado);
           // llenarAdelanto(listCancelado,Seleccionad);
       // }
    }
    public  void llenarCancelado(Empleado empleado){

        DataDetallePago datos=new DataDetallePago();
        listdatellePago = FXCollections.observableArrayList(datos.viewDetallePagoXEmp(new DetallePago(0,0,empleado.getCodigo(),0,0,0,0,0,"Cancelado"),"viewxemp"));
        filterDetallePago=new FilteredList<DetallePago>(listdatellePago,s->true);
        tblPago.setItems(filterDetallePago);
/*        listView.setItems(filterDetallePago);
        listView.setCellFactory(new Callback<ListView<DetallePago>, ListCell<DetallePago>>() {
            @Override
            public ListCell<DetallePago> call(ListView<DetallePago> listView) {
                CellCancelado cellCancelado=new CellCancelado();
                return  cellCancelado;
            }
        });
*/
    }


    public  void llenarAdelanto(Empleado empleado){
        DataAdelanto datos=new DataAdelanto();
        listdatelleAdelanto = FXCollections.observableArrayList(datos.viewAdelanto(new Adelanto(0,empleado.getCodigo(),0,"","Cancelado"),"viewxemp"));
        filterDetalleAdelanto=new FilteredList<Adelanto>(listdatelleAdelanto,s->true);
        tblAdelanto.setItems(filterDetalleAdelanto);/*
        listView.setItems(filterDetalleAdelanto);
        listView.setCellFactory(new Callback<ListView<Adelanto>, ListCell<Adelanto>>() {
            @Override
            public ListCell<Adelanto> call(ListView<Adelanto> listView) {
                CellACancelado cellAcancelado=new CellACancelado();
                return  cellAcancelado;
            }
        });
*/
    }

    public void imprimirVoucher(ObservableList<DetallePago> list, float operacion, float descuentos, float totals) {
       Empleado empleadoSeleccionado=cbxEmpleado.getSelectionModel().getSelectedItem();
        String empleado=  empleadoSeleccionado.getNombre()+ " "+empleadoSeleccionado.getApellido();
        ConstanciaPago constanciaPago=new ConstanciaPago();
        ObservableList<ConstanciaPago> lista=FXCollections.observableArrayList(constanciaPago.datos(list));
        if (!list.isEmpty()) {
            ImprimirVale imprimirVale = new ImprimirVale();
            imprimirVale.Constancia(lista, operacion, descuentos, totals, empleado, true);
        }
    }


    public void ReimprimirConstancia(DetallePago dt) {
        float operacion=0;
        float descuento=0;
        float total=0;
        ObservableList<DetallePago> lista=FXCollections.observableArrayList();
        if (listdatellePago!=null){
            for (int i=0;i<listdatellePago.size();i++){
                if (listdatellePago.get(i).getFecha().equals(dt.getFecha())) {
                    DetallePago detalle = new DetallePago();
                    detalle.setIddetalle( listdatellePago.get(i).getIddetalle());
                    detalle.setIdpago(listdatellePago.get(i).getIdpago());
                    detalle.setNombre(listdatellePago.get(i).getNombre());
                    detalle.setIdempleado(listdatellePago.get(i).getIdempleado());
                    detalle.setIdoperacion(listdatellePago.get(i).getIdoperacion());
                    detalle.setCantidad(listdatellePago.get(i).getCantidad());
                    detalle.setPrecio(listdatellePago.get(i).getPrecio());
                    detalle.setDescuento(listdatellePago.get(i).getDescuento());
                    detalle.setTotal(listdatellePago.get(i).getTotal());
                    detalle.setEstado(listdatellePago.get(i).getEstado());
                    descuento=descuento+listdatellePago.get(i).getDescuento();
                    total=total+(listdatellePago.get(i).getTotal()-listdatellePago.get(i).getDescuento());
                    operacion=operacion+listdatellePago.get(i).getTotal();
                    lista.addAll(detalle);
                }
            }
            imprimirVoucher(lista, operacion,descuento,total);


        }

    }

}
