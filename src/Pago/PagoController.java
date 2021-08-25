package Pago;

import ClassAux.AlertDialog;
import Corte.DAO.Corte;
import Empleado.DAO.DataEmpleado;
import Empleado.DAO.Empleado;
import Operacion.Estilo.DAO.Estilo;
import Corte.CorteController;
import Operacion.Estilo.DAO.EstiloData;
import Pago.DAO.*;
import Pago.Factura.ConstanciaPago;
import Pago.Factura.ImprimirVale;
import Pago.ListCorte.ListCorte;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class PagoController implements Initializable {
    public Label lblPagos;
    public Button btnIngresarNuevo;
    public Button btnAdelanto;
    public ListView<Empleado> listEmpleado;
    public TextField txtBuscar;
    static ObservableList<Empleado> empleados;
    static FilteredList<Empleado> empleadodata;
    static ObservableList<DetallePago> listdatellePago;
    static FilteredList<DetallePago> filterDetallePago;
    static ObservableList<Adelanto> listdatelleAdelanto;
    static FilteredList<Adelanto> filterDetalleAdelanto;
    public ListView<Adelanto> listViewAdelanto;
    public Button btnPagar;
    public Button btnHistorial;
    public Label lblDescuento;
    public Label lblTotalOperacion;

    public Corte corteSleccionado;
    public Estilo estiloSeleccinado;
    public CheckBox cbxTodo;
    public CheckBox cbxTodo2;
    public int tipoSeleccionado;
    public String tipoNSeleccionado;
    public CheckBox cbxImprimir;
    private Empleado empleadoSeleccionado;
    public Label lblidcorte;
    public  Label lblTotalPago;
    public RadioButton rDelantera;
    public RadioButton rTrasera;
    public RadioButton rEmsamble;
    public ListView<DetallePago> listViewPago;


    int codigoPago;
    private  boolean imprimir=false;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // btnIngresarNuevo.setVisible(false);
        totalAPagar();
        initLista(listEmpleado);
        lblDescuento.setText("0.0");

        listEmpleado.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount()==2 && event.getButton() == MouseButton.PRIMARY) {
                    empleadoSeleccionado=listEmpleado.getSelectionModel().getSelectedItem();
                    if (estiloSeleccinado!=null && tipoSeleccionado!=0){
                        try {
                            FXMLLoader loader=new FXMLLoader(getClass().getResource("/Pago/FormAsignar.fxml"));
                            Parent parent = loader.load();
                            Stage stage=new Stage();
                            stage.setScene(new Scene(parent));
                            stage.show();
                            stage.getIcons().add(new Image("/Img/icon.png"));
                            FormAsignar formEmpleado = loader.getController();
                            formEmpleado.pasarRegistro(empleadoSeleccionado,estiloSeleccinado,codigoPago,tipoSeleccionado,tipoNSeleccionado);
                            stage.setOnHiding((event1) ->{
                                llenarTarea(listViewPago,empleadoSeleccionado);
                                llenarAdelanto(listViewAdelanto,empleadoSeleccionado);
                                totalAPagar();
                            });
                        }catch (IOException e){
                            e.printStackTrace();

                        }}
                    else{
                        AlertDialog alertDialog=new AlertDialog();
                        alertDialog.alert("Aviso","Para asigar tarea, primero debe seleccionar el corte y el tipo de operación, en la parte superior. ");
                    }
                }
                if (event.getClickCount()==1 && event.getButton()==MouseButton.PRIMARY){
                    empleadoSeleccionado =listEmpleado.getSelectionModel().getSelectedItem();
                    totalAPagar();
                    llenarTarea(listViewPago,empleadoSeleccionado);
                    llenarAdelanto(listViewAdelanto,empleadoSeleccionado);
                }
            }
        });

        rDelantera.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                tipoSeleccionado=1;
                rTrasera.setSelected(false);
                rEmsamble.setSelected(false);
                tipoNSeleccionado="Delantera";
            }
        });
        rTrasera.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                tipoSeleccionado=2;
                rDelantera.setSelected(false);
                rEmsamble.setSelected(false);
                tipoNSeleccionado="Trasera";
            }
        });
        rEmsamble.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                tipoSeleccionado=3;
                rTrasera.setSelected(false);
                rDelantera.setSelected(false);
                tipoNSeleccionado="Ensamble";
            }
        });
        listViewPago.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount()==2 && event.getButton()==MouseButton.PRIMARY) {
                    DetallePago detalle = listViewPago.getSelectionModel().getSelectedItem();
                    if (detalle != null) {
                        for (int i = 0; i < listdatellePago.size(); i++) {
                            if (listdatellePago.get(i).getIddetalle() == detalle.getIddetalle()) {
                                if (listdatellePago.get(i).getEstado().equals("Pendiente")) {
                                    listdatellePago.get(i).setEstado("Cancelado");
                                    float total = Float.parseFloat(lblTotalPago.getText()) + listdatellePago.get(i).getTotal();
                                    lblTotalOperacion.setText(String.valueOf(total));
                                    lblTotalPago.setText(String.valueOf(Float.parseFloat(lblTotalOperacion.getText())-Float.parseFloat(lblDescuento.getText())));

                                } else {
                                    listdatellePago.get(i).setEstado("Pendiente");
                                    float total = Float.parseFloat(lblTotalPago.getText()) - listdatellePago.get(i).getTotal();
                                    lblTotalOperacion.setText(String.valueOf(total));
                                    lblTotalPago.setText(String.valueOf(Float.parseFloat(lblTotalOperacion.getText())-Float.parseFloat(lblDescuento.getText())));

                                }
                            }
                        }
                        listViewPago.refresh();

                        if (cbxTodo.isSelected()) {
                            cbxTodo.setSelected(false);
                        }
                    }
                }
            }
        });


        listViewAdelanto.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount()==2 && event.getButton()==MouseButton.PRIMARY) {
                    Adelanto adelanto = listViewAdelanto.getSelectionModel().getSelectedItem();
                    if (adelanto != null) {
                        for (int i = 0; i < listdatelleAdelanto.size(); i++) {
                            if (listdatelleAdelanto.get(i).getIdadelanto() == adelanto.getIdadelanto()) {
                                if (listdatelleAdelanto.get(i).getEstado().equals("Pendiente")) {
                                    listdatelleAdelanto.get(i).setEstado("Cancelado");
                                    float total = Float.parseFloat(lblDescuento.getText()) + listdatelleAdelanto.get(i).getCantidad();
                                    lblDescuento.setText(String.valueOf(total));
                                    lblTotalPago.setText(String.valueOf(Float.parseFloat(lblTotalOperacion.getText())-Float.parseFloat(lblDescuento.getText())));

                                } else {
                                    listdatelleAdelanto.get(i).setEstado("Pendiente");
                                    float total = Float.parseFloat(lblDescuento.getText()) - listdatelleAdelanto.get(i).getCantidad();
                                    lblDescuento.setText(String.valueOf(total));
                                    lblTotalPago.setText(String.valueOf(Float.parseFloat(lblTotalOperacion.getText())-Float.parseFloat(lblDescuento.getText())));

                                }
                            }
                        }
                        listViewAdelanto.refresh();

                        if (cbxTodo2.isSelected()) {
                            cbxTodo2.setSelected(false);
                        }
                    }
                }
            }
        });


        cbxTodo2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (cbxTodo2.isSelected()){
                    lblDescuento.setText(String.valueOf(0));
                    lblTotalPago.setText(String.valueOf(0));
                    for (int i=0;i<listdatelleAdelanto.size();i++){
                        listdatelleAdelanto.get(i).setEstado("Cancelado");
                        float total=Float.parseFloat(lblDescuento.getText())+listdatelleAdelanto.get(i).getCantidad();
                        lblDescuento.setText(String.valueOf(total));
                        lblTotalPago.setText(String.valueOf(Float.parseFloat(lblTotalOperacion.getText())-Float.parseFloat(lblDescuento.getText())));
                    }
                }else{
                    for (int i=0;i<listdatelleAdelanto.size();i++){
                        listdatelleAdelanto.get(i).setEstado("Pendiente");
                        float total=Float.parseFloat(lblDescuento.getText())-listdatelleAdelanto.get(i).getCantidad();
                        lblDescuento.setText(String.valueOf(total));
                        lblTotalPago.setText(String.valueOf(Float.parseFloat(lblTotalOperacion.getText())-Float.parseFloat(lblDescuento.getText())));

                    }
                }
                listViewPago.refresh();
            }
        });

        cbxTodo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (cbxTodo.isSelected()){
                    lblTotalPago.setText(String.valueOf(0));
                    lblTotalOperacion.setText(String.valueOf(0));
                    for (int i=0;i<listdatellePago.size();i++){
                        listdatellePago.get(i).setEstado("Cancelado");
                        float total=Float.parseFloat(lblTotalOperacion.getText())+listdatellePago.get(i).getTotal();
                        lblTotalOperacion.setText(String.valueOf(total));
                        lblTotalPago.setText(String.valueOf(Float.parseFloat(lblTotalOperacion.getText())-Float.parseFloat(lblDescuento.getText())));
                    }
                }else{
                    for (int i=0;i<listdatellePago.size();i++){
                        listdatellePago.get(i).setEstado("Pendiente");
                        float total=Float.parseFloat(lblTotalOperacion.getText())-listdatellePago.get(i).getTotal();
                        lblTotalOperacion.setText(String.valueOf(total));
                        lblTotalPago.setText(String.valueOf(Float.parseFloat(lblTotalOperacion.getText())-Float.parseFloat(lblDescuento.getText())));

                    }
                }
                listViewPago.refresh();
            }
        });
        cbxImprimir.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (cbxImprimir.isSelected()){
                    imprimir=true;
                }else {
                    imprimir=false;
                }
            }
        });
    }
    public void initLista(ListView<Empleado> listView){
        DataEmpleado datos=new DataEmpleado();
        empleados = FXCollections.observableArrayList(datos.viewEmpleado(new Empleado(0,"","","",0,""),"viewact"));
        empleadodata=new FilteredList<Empleado>(empleados,s->true);
        listView.setItems(empleadodata);
        listView.setCellFactory(new Callback<ListView<Empleado>, ListCell<Empleado>>() {
            @Override
            public ListCell<Empleado> call(ListView<Empleado> param) {
                EmpleadoCell empleadoCell=new EmpleadoCell();
                return empleadoCell;
            }
        });

    }


    public void llenarListaEmpleado(){
        txtBuscar.textProperty().addListener((prop,old,text) ->{
            empleadodata.setPredicate(empleado ->{
                if (text==null || text.isEmpty()){
                    return  true;
                }
                String texto=text.toLowerCase();
                if(String.valueOf(empleado.getCodigo()).toLowerCase().contains(texto)){
                    return true;
                }
                else if(empleado.getNombre().toLowerCase().contains(texto)){
                    return true;
                }
                else if(empleado.getApellido().toLowerCase().contains(texto)){
                    return true;
                }

                return false;
            });
        });
    }

    public  void llenarTarea(ListView<DetallePago> listView,Empleado empleado){
        DataDetallePago datos=new DataDetallePago();
        listdatellePago = FXCollections.observableArrayList(datos.viewDetallePagoXEmp(new DetallePago(0,0,empleado.getCodigo(),0,0,0,0,0,"Pendiente"),"viewxemp"));
        filterDetallePago=new FilteredList<DetallePago>(listdatellePago,s->true);
        listView.setItems(filterDetallePago);
        listView.setCellFactory(new Callback<ListView<DetallePago>, ListCell<DetallePago>>() {
            @Override
            public ListCell<DetallePago> call(ListView<DetallePago> listView) {
                CellDetalle cellDetalle=new CellDetalle();
                return  cellDetalle;
            }
        });

    }
    public  void llenarAdelanto(ListView<Adelanto> listView,Empleado empleado){
        DataAdelanto datos=new DataAdelanto();
        listdatelleAdelanto = FXCollections.observableArrayList(datos.viewAdelanto(new Adelanto(0,empleado.getCodigo(),0,"","Pendiente"),"viewxemp"));
        filterDetalleAdelanto=new FilteredList<Adelanto>(listdatelleAdelanto,s->true);
        listView.setItems(filterDetalleAdelanto);
        listView.setCellFactory(new Callback<ListView<Adelanto>, ListCell<Adelanto>>() {
            @Override
            public ListCell<Adelanto> call(ListView<Adelanto> listView) {
                CellAdelanto cellAdelanto=new CellAdelanto();
                return  cellAdelanto;
            }
        });

    }

    public void RealizarPago(ActionEvent actionEvent) {
        float operacion=Float.parseFloat(lblTotalOperacion.getText());
        float descuento=Float.parseFloat(lblDescuento.getText());
        float total=Float.parseFloat(lblTotalPago.getText());
        if (listdatellePago!=null){
            for (int i=0;i<listdatellePago.size();i++){
                if (listdatellePago.get(i).getEstado().equals("Cancelado")) {
                    DataDetallePago detalle = new DataDetallePago();
                    detalle.crudDetallePago(new DetallePago(
                            listdatellePago.get(i).getIddetalle(),
                            listdatellePago.get(i).getIdpago(),
                            listdatellePago.get(i).getIdempleado(),
                            listdatellePago.get(i).getIdoperacion(),
                            listdatellePago.get(i).getCantidad(),
                            listdatellePago.get(i).getPrecio(),
                            listdatellePago.get(i).getDescuento(),
                            listdatellePago.get(i).getTotal(),
                            listdatellePago.get(i).getEstado()
                    ), "update");
                }
            }
            imprimirVoucher(listdatellePago, operacion,descuento,total);
            llenarTarea(listViewPago,empleadoSeleccionado);
            lblTotalOperacion.setText(String.valueOf(0.0));
            lblTotalPago.setText(String.valueOf(0.0));
            lblDescuento.setText(String.valueOf(0.0));

        }
        if (listdatelleAdelanto!=null){
            for (int i=0;i<listdatelleAdelanto.size();i++){
                if (listdatelleAdelanto.get(i).getEstado().equals("Cancelado")) {
                    DataAdelanto detalle = new DataAdelanto();
                    detalle.crudAdelanto(new Adelanto(
                            listdatelleAdelanto.get(i).getIdadelanto(),
                            listdatelleAdelanto.get(i).getIdempleado(),
                            listdatelleAdelanto.get(i).getCantidad(),
                            listdatelleAdelanto.get(i).getConcepto(),
                            listdatelleAdelanto.get(i).getEstado()
                    ), "update");
                }
            }
            llenarAdelanto(listViewAdelanto,empleadoSeleccionado);
            lblDescuento.setText("0.0");
            lblTotalPago.setText(String.valueOf(0.0));
        }
totalAPagar();
    }


    public void SeleccionarCorte(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Pago/ListCorte/ListCorte.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            ListCorte controller=fxmlLoader.getController();
            controller.listCorte.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getClickCount()==1 && event.getButton()==MouseButton.PRIMARY){
                        Corte corts=controller.listCorte.getSelectionModel().getSelectedItem();
                        llenarDatos(corts);
                        Stage stage1=(Stage)controller.listCorte.getScene().getWindow();
                        stage1.close();
                    }
                }
            });

            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private void llenarDatos(Corte corte){
        corteSleccionado=corte;
        estiloSeleccinado=returEstilo(corteSleccionado.getIdestilo());
        lblidcorte.setText(corteSleccionado.getIdcorte()+"  "+estiloSeleccinado.getNombre() );
        Pago pago=returnpago(corteSleccionado.getIdcorte());
        if (pago ==null){
            codigoPago= agregarPago(corteSleccionado.getIdcorte());
        }else {
            codigoPago=pago.getIdpago();
        }


    }

    public Estilo returEstilo(int codigo){
        Estilo estilo=null;
        EstiloData estiloData=new EstiloData();
        ObservableList<Estilo> lis=FXCollections.observableArrayList(estiloData.viewEstilo(new Estilo(codigo,"",""),"viewone"));
        for (int i=0; i<lis.size();i++){
            Estilo es=new Estilo();
            es.setCodigo( lis.get(i).getCodigo());
            es.setNombre(lis.get(i).getNombre());
            es.setEstado(lis.get(i).getEstado());

            estilo=es;
        }
        return estilo;
    }
    private Pago returnpago(String idcorte){
        Pago pago=null;
        DataPago dataPago=new DataPago();
        ObservableList<Pago> list=FXCollections.observableArrayList(dataPago.viewPago(new Pago(0,idcorte,"2020-07-01",0),"viewpago"));
        for (int i=0;i<list.size();i++){
            Pago pg=new Pago();
            pg.setIdpago(list.get(i).getIdpago());
            pg.setIdcorte(list.get(i).getIdcorte());
            pg.setFecha_pago(list.get(i).getFecha_pago());
            pg.setTotal( list.get(i).getTotal());
            pago=pg;
        }
        return pago;
    }
    private  int agregarPago(String idcorte){
        int pago=0;
        DataPago dataPago=new DataPago();
        if (dataPago.crudPago(new Pago(0,idcorte,obtnerfechaHoy(),0),"new")){
            pago=dataPago.numeroPago();
        }
        return pago;
    }
    public String obtnerfechaHoy(){
        return String.valueOf(LocalDate.now());
    }

    public void AbrirAdelanto(ActionEvent actionEvent) {
        if (empleadoSeleccionado!=null){
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Pago/FormAdelanto.fxml"));
                Parent parent = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(parent));
                stage.getIcons().add(new Image("/Img/icon.png"));
                FormAdelanto formAdelanto = loader.getController();
                formAdelanto.pasarRegistro(empleadoSeleccionado);
                stage.setOnHiding((event ->{
                    llenarAdelanto(listViewAdelanto,empleadoSeleccionado);
                    totalAPagar();
                }));
                stage.show();

            }catch (IOException e){
                e.printStackTrace();


            }

        }else{
            AlertDialog alertDialog=new AlertDialog();
            alertDialog.alert("Error","Por favor seleccione un empleado");
        }

    }

    public void imprimirVoucher(ObservableList<DetallePago> list, float operacion, float descuentos, float totals) {
        String empleado= empleadoSeleccionado.getNombre()+ " "+empleadoSeleccionado.getApellido();
        ConstanciaPago constanciaPago=new ConstanciaPago();
        ObservableList<ConstanciaPago> lista=FXCollections.observableArrayList(constanciaPago.datos(list));
        if (!list.isEmpty()) {
            ImprimirVale imprimirVale = new ImprimirVale();
            imprimirVale.Constancia(lista, operacion, descuentos, totals, empleado, imprimir);
        }
    }

    public void AbrirHistorial(ActionEvent actionEvent) {
        if (empleadoSeleccionado!=null){
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Pago/Historial.fxml"));
                Parent parent = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(parent));
                stage.getIcons().add(new Image("/Img/icon.png"));
                Historial historial = loader.getController();
                historial.pasarRegistro(empleadoSeleccionado);
                stage.setOnHiding((event ->{
                  //  initLista(listEmpleado);
                   // listEmpleado.refresh();
                }));
                stage.show();

            }catch (IOException e){
                e.printStackTrace();


            }

        }else{
            AlertDialog alertDialog=new AlertDialog();
            alertDialog.alert("Error","Por favor seleccione un empleado");
        }


    }
    private void totalAPagar(){
        llenarListaEmpleado();
        DataAdelanto dataAdelanto=new DataAdelanto();
        float totalpago=0;
        totalpago=dataAdelanto.totalPago();
        lblPagos.setText(String.valueOf("Q"+totalpago));
    }
}

