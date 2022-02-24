package Pago;

import ClassAux.AlertDialog;
import ClassAux.EstiloBoton;
import ClassAux.SizeColumnTable;
import Corte.DAO.Corte;
import Empleado.DAO.DataEmpleado;
import Empleado.DAO.Empleado;
import Operacion.DAO.Operacion;
import Operacion.Estilo.DAO.Estilo;
import Corte.CorteController;
import Operacion.Estilo.DAO.EstiloData;
import Pago.DAO.*;
import Pago.Factura.ConstanciaPago;
import Pago.Factura.ImprimirVale;
import Pago.ListCorte.ListCorte;
import javafx.application.Platform;
import javafx.beans.binding.Binding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
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
    public TextField txtBuscar;
    static ObservableList<Empleado> empleados;
    static FilteredList<Empleado> empleadodata;
    static ObservableList<DetallePago> listdatellePago;
    static FilteredList<DetallePago> filterDetallePago;
    static ObservableList<Adelanto> listdatelleAdelanto;
    static FilteredList<Adelanto> filterDetalleAdelanto;

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
    public TableView<Empleado> tblEmpleado;
    public TableColumn<Empleado,String> cellNombre;
    public TableColumn<Empleado,String> cellApellido;
    public TableColumn<Empleado,String> cellOpEmpleado;

    public TableView<DetallePago> tblPago;
    public TableColumn<DetallePago,String>cellOperacion;
    public TableColumn<DetallePago,String>cellCantidad;
    public TableColumn<DetallePago,String>cellPrecio;
    public TableColumn<DetallePago,String>cellTotal;
    public TableColumn<DetallePago,String>cellOpciones;
    public TableColumn<DetallePago,String>cellEstado;

    public TableView<Adelanto> tblAdelanto;
    public TableColumn<Adelanto,String>cellFechaAdelanto;
    public TableColumn<Adelanto,String>cellConcepto;
    public TableColumn<Adelanto,String>cellCantAdelanto;
    public TableColumn<Adelanto,String>cellOpAdelanto;
    public  TableColumn<Adelanto,String>cellEstadoAdelanto;

    private Empleado empleadoSeleccionado;
    public Label lblidcorte;
    public  Label lblTotalPago;
    public RadioButton rDelantera;
    public RadioButton rTrasera;
    public RadioButton rEmsamble;
    public ListView<DetallePago> listViewPago;
    public ListView<Empleado> listEmpleado;
    public ListView<Adelanto> listViewAdelanto;

    EstiloBoton estiloBoton=new EstiloBoton();
    SizeColumnTable sizeColumnTable=new SizeColumnTable();
    AlertDialog alertDialog=new AlertDialog();
    int codigoPago;
    private  boolean imprimir=false;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // btnIngresarNuevo.setVisible(false);
        initTableEmpleado();
        initTablePago();
        initTableAdelanto();
        totalAPagar();
        initLista();
        lblDescuento.setText("0.0");

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
                tblPago.refresh();
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
                tblPago.refresh();
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

    public void initTableEmpleado(){
        cellNombre=new TableColumn<>("Nombre");
        cellApellido=new TableColumn<>("Apellido");
        cellOpEmpleado=new TableColumn<>("Asignar Tarea");

        cellNombre.setCellValueFactory(new PropertyValueFactory<Empleado,String>("nombre"));
        cellApellido.setCellValueFactory(new PropertyValueFactory<Empleado,String>("apellido"));
        cellOpEmpleado.setCellFactory(new Callback<TableColumn<Empleado, String>, TableCell<Empleado, String>>() {
            @Override
            public TableCell<Empleado, String> call(TableColumn<Empleado, String> empleadoStringTableColumn) {
                return new TableCell<Empleado, String>(){
                    @Override
                            public void updateItem(String item, boolean empty){
                        super.updateItem(item,empty);
                        if (!empty){
                            ImageView addTask=new ImageView(estiloBoton.AddTask());
                            addTask.setFitWidth(estiloBoton.sizeButton());
                            addTask.setFitHeight(estiloBoton.sizeButton());
                            addTask.setStyle(" -fx-background-color: yellow;");
                            addTask.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent event) {
                                    if (event.getClickCount()==1 && event.getButton()==MouseButton.PRIMARY){
                                        empleadoSeleccionado=tblEmpleado.getSelectionModel().getSelectedItem();
                                       agregarTarea(empleadoSeleccionado);
                                    }
                                }
                            });
                            HBox contain=new HBox(addTask);
                            contain.setStyle(" -fx-alignment: center;");
                            HBox.setMargin(addTask,new Insets(1,1,1,1));
                            setGraphic(contain);
                        }else {
                            setGraphic(null);
                        }
                    }
                };
            }
        });
        Platform.runLater(()-> sizeColumnTable.ajustarColumna(tblEmpleado));
        tblEmpleado.getColumns().addAll(cellNombre,cellApellido,cellOpEmpleado);

        tblEmpleado.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount()==2 && event.getButton()==MouseButton.PRIMARY){
                    empleadoSeleccionado=tblEmpleado.getSelectionModel().getSelectedItem();
                  agregarTarea(empleadoSeleccionado);
                }
                if (event.getClickCount()==1 && event.getButton()==MouseButton.PRIMARY){
                    empleadoSeleccionado =tblEmpleado.getSelectionModel().getSelectedItem();
                    totalAPagar();
                    llenarTarea(empleadoSeleccionado);
                    llenarAdelanto(empleadoSeleccionado);
                }
            }
        });
    }

    public  void agregarTarea(Empleado empleado){

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
                        llenarTarea(empleadoSeleccionado);
                        llenarAdelanto(empleadoSeleccionado);
                        totalAPagar();
                    });
                }catch (IOException e){
                    e.printStackTrace();

                }}
            else{
                AlertDialog alertDialog=new AlertDialog();
                alertDialog.alert("Aviso","Para asignar tarea, primero debe seleccionar el corte y el tipo de operación, en la parte superior. ");
            }

    }
    public  void initTablePago(){
        cellOperacion=new TableColumn<>("Operación");
        cellCantidad=new TableColumn<>("Cantidad");
        cellPrecio=new TableColumn<>("Precio");
        cellTotal=new TableColumn<>("Total");
        cellOpciones=new TableColumn<>("Opciones");
        cellEstado=new TableColumn<>("");

        cellOperacion.setPrefWidth(220);
        cellOperacion.setCellValueFactory(new PropertyValueFactory<DetallePago,String>("nombre"));
        cellCantidad.setCellValueFactory(new PropertyValueFactory<DetallePago,String>("cantidad"));
        cellPrecio.setCellValueFactory(new PropertyValueFactory<DetallePago,String>("precio"));
        cellTotal.setCellValueFactory(new PropertyValueFactory<DetallePago,String>("total"));
        cellEstado.setCellValueFactory(new PropertyValueFactory<DetallePago,String>("estado"));


        cellEstado.setCellFactory(new Callback<TableColumn<DetallePago, String>, TableCell<DetallePago, String>>() {
            @Override
            public TableCell<DetallePago, String> call(TableColumn<DetallePago, String> detallePagoStringTableColumn) {
                return new TableCell<>(){
                    @Override
                    protected  void updateItem(String item, boolean empty){
                        super.updateItem(item,empty);
                        if (!empty){

                            if (item.equals("Cancelado")){
                                ImageView iconCheck = new ImageView(estiloBoton.Cancelado());
                                iconCheck.setFitHeight(estiloBoton.sizeButton());
                                iconCheck.setFitWidth(estiloBoton.sizeButton());
                                iconCheck.setStyle(estiloBoton.Boton());
                                HBox containBoton = new HBox(iconCheck);
                                containBoton.setStyle("-fx-alignment:center");
                                HBox.setMargin(iconCheck, new Insets(2, 2, 2, 10));
                                setGraphic(containBoton);
                            }else{
                                setGraphic(null);
                            }
                        }else{
                            setText("");
                        }
                    }
                };
            }
        });


        Callback<TableColumn<DetallePago,String>,TableCell<DetallePago,String>> cellFactory=(TableColumn<DetallePago,String> param)->{
          final TableCell<DetallePago,String> cell=new TableCell<>(){
            @Override
            public  void updateItem(String item, boolean empty){
                if (empty){
                    setGraphic(null);
                }else{
                    ImageView editButton=new ImageView(estiloBoton.editImg());
                    editButton.setFitHeight(estiloBoton.sizeButton());
                    editButton.setFitWidth(estiloBoton.sizeButton());
                    editButton.setStyle(estiloBoton.Boton());

                    ImageView deleteButton=new ImageView(estiloBoton.deleteImg());
                    editButton.setFitHeight(estiloBoton.sizeButton());
                    editButton.setFitWidth(estiloBoton.sizeButton());
                    editButton.setStyle(estiloBoton.Boton());

                    deleteButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(javafx.scene.input.MouseEvent event) {
                            DetallePago detallePago=tblPago.getSelectionModel().getSelectedItem();
                            EliminarPago(detallePago);
                        }
                    });

                    editButton.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
                        @Override
                        public void handle(javafx.scene.input.MouseEvent event) {
                            DetallePago detallePago=tblPago.getSelectionModel().getSelectedItem();
                            EditarPago(detallePago);
                        }
                    });

                    HBox containBoton=new HBox(deleteButton,editButton);
                    containBoton.setStyle("-fx-alignment:center");
                    HBox.setMargin(deleteButton,new Insets(2,10,2,2));
                    HBox.setMargin(editButton,new Insets(2,2,2,10));
                    setGraphic(containBoton);
                }
                setText(null);
            }

          };
            return  cell;
        };
        cellOpciones.setCellFactory(cellFactory);




        Platform.runLater(()->sizeColumnTable.ajustarColumna(tblPago));
        tblPago.getColumns().addAll(cellOperacion,cellCantidad,cellPrecio,cellTotal,cellOpciones,cellEstado);



        tblPago.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount()==2 && event.getButton()==MouseButton.PRIMARY) {
                    DetallePago detalle = tblPago.getSelectionModel().getSelectedItem();
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
                        tblPago.refresh();

                        if (cbxTodo.isSelected()) {
                            cbxTodo.setSelected(false);
                        }
                    }
                }
            }
        });

    }
    public  void initTableAdelanto(){
        cellFechaAdelanto=new TableColumn<>("Fecha");
        cellConcepto=new TableColumn<>("Concepto");
        cellCantAdelanto=new TableColumn<>("Cantidad");
        cellOpAdelanto=new TableColumn<>("Opciones");
        cellEstadoAdelanto=new TableColumn<>("");

        cellConcepto.setPrefWidth(240);
        cellFechaAdelanto.setCellValueFactory(new PropertyValueFactory<Adelanto,String>("fecha"));
        cellConcepto.setCellValueFactory(new PropertyValueFactory<Adelanto,String>("concepto"));
        cellCantAdelanto.setCellValueFactory(new PropertyValueFactory<Adelanto,String>("cantidad"));
        cellEstadoAdelanto.setCellValueFactory(new PropertyValueFactory<Adelanto,String>("estado"));

        cellOpAdelanto.setCellFactory(new Callback<TableColumn<Adelanto, String>, TableCell<Adelanto, String>>() {
            @Override
            public TableCell<Adelanto, String> call(TableColumn<Adelanto, String> adelantoStringTableColumn) {
                return new TableCell<Adelanto,String>(){
                    @Override
                    protected  void updateItem(String item, boolean empty){
                        if (empty){
                            setGraphic(null);
                        }else{
                            ImageView editButton=new ImageView(estiloBoton.editImg());
                            editButton.setFitHeight(estiloBoton.sizeButton());
                            editButton.setFitWidth(estiloBoton.sizeButton());
                            editButton.setStyle(estiloBoton.Boton());

                            ImageView deleteButton=new ImageView(estiloBoton.deleteImg());
                            editButton.setFitHeight(estiloBoton.sizeButton());
                            editButton.setFitWidth(estiloBoton.sizeButton());
                            editButton.setStyle(estiloBoton.Boton());

                            deleteButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(javafx.scene.input.MouseEvent event) {
                                    Adelanto adelanto=tblAdelanto.getSelectionModel().getSelectedItem();
                                    EliminarAdelanto(adelanto);
                                }
                            });

                            editButton.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
                                @Override
                                public void handle(javafx.scene.input.MouseEvent event) {
                                    Adelanto adelanto=tblAdelanto.getSelectionModel().getSelectedItem();
                                    EditarAdelanto(adelanto);
                                }
                            });

                            HBox containBoton=new HBox(deleteButton,editButton);
                            containBoton.setStyle("-fx-alignment:center");
                            HBox.setMargin(deleteButton,new Insets(2,10,2,2));
                            HBox.setMargin(editButton,new Insets(2,2,2,10));
                            setGraphic(containBoton);
                        }
                    }
                };
            }
        });

        Platform.runLater(()->sizeColumnTable.ajustarColumna(tblAdelanto));
        tblAdelanto.getColumns().addAll(cellFechaAdelanto,cellConcepto,cellCantAdelanto,cellOpAdelanto,cellEstadoAdelanto);



        tblAdelanto.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount()==2 && event.getButton()==MouseButton.PRIMARY) {
                    Adelanto adelanto = tblAdelanto.getSelectionModel().getSelectedItem();
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
                        tblAdelanto.refresh();

                        if (cbxTodo2.isSelected()) {
                            cbxTodo2.setSelected(false);
                        }
                    }
                }
            }
        });
        cellEstadoAdelanto.setCellFactory(new Callback<TableColumn<Adelanto, String>, TableCell<Adelanto, String>>() {
            @Override
            public TableCell<Adelanto, String> call(TableColumn<Adelanto, String> adelantoStringTableColumn) {
                return new TableCell<Adelanto,String>(){
                    @Override
                    protected void updateItem(String item, boolean empty ){
                        super.updateItem(item,empty);
                        if (!empty){
                            if (item.equals("Cancelado")) {
                                ImageView iconCheck = new ImageView(estiloBoton.Cancelado());
                                iconCheck.setFitHeight(estiloBoton.sizeButton());
                                iconCheck.setFitWidth(estiloBoton.sizeButton());
                                iconCheck.setStyle(estiloBoton.Boton());
                                HBox containBoton=new HBox(iconCheck);
                                containBoton.setStyle("-fx-alignment:center");
                                HBox.setMargin(iconCheck, new Insets(2,2,2,10));
                                setGraphic(containBoton);
                            }else{
                                setGraphic(null);
                            }
                        }else{
                            setText("");
                        }
                    }
                };
            }
        });
    }

    public void initLista(){
        DataEmpleado datos=new DataEmpleado();
        empleados = FXCollections.observableArrayList(datos.viewEmpleado(new Empleado(0,"","","",0,""),"viewact"));
        empleadodata=new FilteredList<Empleado>(empleados,s->true);
        tblEmpleado.setItems(empleadodata);

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

    public  void llenarTarea(Empleado empleado) {

        DataDetallePago datos = new DataDetallePago();
        listdatellePago = FXCollections.observableArrayList(datos.viewDetallePagoXEmp(new DetallePago(0, 0, empleado.getCodigo(), 0, 0, 0, 0, 0, "Pendiente"), "viewxemp"));
        filterDetallePago = new FilteredList<DetallePago>(listdatellePago, s -> true);
        tblPago.setItems(filterDetallePago);
        lblDescuento.setText("0.0");
        lblTotalOperacion.setText("0.0");
        lblTotalPago.setText("0.0");


    }
    public  void llenarAdelanto(Empleado empleado){

        DataAdelanto datos=new DataAdelanto();
        listdatelleAdelanto = FXCollections.observableArrayList(datos.viewAdelanto(new Adelanto(0,empleado.getCodigo(),0,"","Pendiente"),"viewxemp"));
        filterDetalleAdelanto=new FilteredList<Adelanto>(listdatelleAdelanto,s->true);
        tblAdelanto.setItems(filterDetalleAdelanto);
        lblDescuento.setText("0.0");
        lblTotalOperacion.setText("0.0");
        lblTotalPago.setText("0.0");
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
            llenarTarea(empleadoSeleccionado);
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
            llenarAdelanto(empleadoSeleccionado);
            lblDescuento.setText("0.0");
            lblTotalPago.setText(String.valueOf(0.0));
        }
        cbxTodo.setSelected(false);
        cbxTodo2.setSelected(false);
totalAPagar();
    }


    public void SeleccionarCorte(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Pago/ListCorte/ListCorte.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            ListCorte controller=fxmlLoader.getController();
            controller.tblCorte.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getClickCount()==2 && event.getButton()==MouseButton.PRIMARY){
                        Corte corts=controller.tblCorte.getSelectionModel().getSelectedItem();
                        llenarDatos(corts);
                        Stage stage1=(Stage)controller.tblCorte.getScene().getWindow();
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
                    llenarAdelanto(empleadoSeleccionado);
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

            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Pago/Historial.fxml"));
                Parent parent = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(parent));
                stage.getIcons().add(new Image("/Img/icon.png"));

                Historial historial = loader.getController();
                historial.pasarRegistro(empleados);
                stage.setOnHiding((event ->{
                  //  initLista(listEmpleado);
                   // listEmpleado.refresh();
                }));
                stage.show();

            }catch (IOException e){
                e.printStackTrace();


            }




    }
    private void totalAPagar(){
        llenarListaEmpleado();
        DataAdelanto dataAdelanto=new DataAdelanto();
        float totalpago=0;
        totalpago=dataAdelanto.totalPago();
        lblPagos.setText(String.valueOf("Q"+totalpago));
    }
    public  void EditarPago(DetallePago detallePago){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Pago/FormAsignar.fxml"));
            Parent parent = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Modificar producto");
            stage.getIcons().add(new Image("/Img/icon.png"));
            stage.setScene(new Scene(parent));
            FormAsignar formAsignar = loader.<FormAsignar>getController();
            formAsignar.pasarRegistroEditar(detallePago);
            stage.show();
            stage.setOnHiding((events -> {
                Empleado empleado=new Empleado(detallePago.getIdempleado(),"","","",0,"");
                llenarTarea(empleado);
               tblPago.refresh();
            }));

        } catch (IOException e) {
            e.printStackTrace();

        }
    }
    public  void EliminarPago(DetallePago detallePago){
        if (alertDialog.alertConfirm("Empleado", "esta seguro de elliminar es tarea")){
            Empleado empleado=new Empleado(detallePago.getIdempleado(),"","","",0,"");
            DataDetallePago datos=new DataDetallePago();
            datos.crudDetallePago(detallePago,"delete");
            llenarTarea(empleado);
            tblPago.refresh();

        }
    }
    public  void EditarAdelanto(Adelanto adelanto){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Pago/FormAdelanto.fxml"));
            Parent parent = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Modificar Adelanto");
            stage.getIcons().add(new Image("/Img/icon.png"));
            stage.setScene(new Scene(parent));
            Empleado emp1=new Empleado(adelanto.getIdempleado(),"","","",0,"");
            FormAdelanto formAdelanto = loader.<FormAdelanto>getController();
            formAdelanto.pasarRegistro2(emp1,adelanto);
            stage.show();
            stage.setOnHiding((event -> {

                Empleado emp=new Empleado((adelanto.getIdempleado()),"","","",0,"");

               llenarAdelanto(emp);
               tblAdelanto.refresh();
            }));

        } catch (IOException e) {
            e.printStackTrace();

        }

    }
    public  void EliminarAdelanto(Adelanto adelanto){
        if (alertDialog.alertConfirm("Adelanto", "esta seguro de elliminar adelanto")){
            Adelanto pro=new Adelanto(adelanto.getIdadelanto(),0,0,"x","x");
            DataAdelanto datos=new DataAdelanto();
            datos.crudAdelanto(pro,"delete");

            Empleado emp=new Empleado(adelanto.getIdempleado(), "","","",0,"");
            llenarAdelanto(emp);
            tblAdelanto.refresh();

        }
    }
    public Node estadoIcon(String estado){
        HBox containBoton=new HBox();
        if (estado.equals("Cancelado")){
            ImageView editButton=new ImageView(estiloBoton.Cancelado());
            editButton.setFitHeight(estiloBoton.sizeButton());
            editButton.setFitWidth(estiloBoton.sizeButton());
            editButton.setStyle(estiloBoton.Boton());
            containBoton.getChildren().add(editButton);
            containBoton.setStyle("-fx-alignment:center");
            HBox.setMargin(editButton,new Insets(2,2,2,10));

        }
        return containBoton;

    }
}





/*
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
 */