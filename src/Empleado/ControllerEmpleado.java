package Empleado;

import ClassAux.AlertDialog;
import ClassAux.EstiloBoton;
import ClassAux.SizeColumnTable;
import ClassAux.Util;
import Empleado.DAO.DataEmpleado;
import Empleado.DAO.Empleado;
import Usuario.DAO.DataUsuario;
import Usuario.DAO.Usuario;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerEmpleado implements Initializable {
    public Button btnIngresarNuevo;

    public TextField txtBuscar;
    static ObservableList<Empleado> empleados;
    static FilteredList<Empleado> empleadodata;
    public TableView<Empleado> tblEmpleado;
    public TableColumn<Empleado, String> cellCodigo;
    public TableColumn<Empleado, String> cellNombre;
    public TableColumn<Empleado, String> cellApellido;
    public TableColumn<Empleado, String> cellTelefono;
    public TableColumn<Empleado, String> cellDpi;
    public TableColumn<Empleado, String> cellEstado;
    public TableColumn<Empleado, String> cellopciones;
    SizeColumnTable size_tabla=new SizeColumnTable();
    EstiloBoton estiloBoton=new EstiloBoton();

    AlertDialog alertDialog=new AlertDialog();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTabla();
        llenarListaEmpleado();
    }

public void initTabla(){
    cellCodigo=new TableColumn<>("Codigo");
    cellNombre=new TableColumn<>("Nombre");
    cellApellido=new TableColumn<>("Apellido");
    cellTelefono=new TableColumn<>("Telefono");
    cellDpi=new TableColumn<>("DPI");
    cellEstado=new TableColumn<>("Estado");
    cellopciones=new TableColumn<>("Opciones");

    cellCodigo.setCellValueFactory(new PropertyValueFactory<Empleado,String>("codigo"));
    cellNombre.setCellValueFactory(new PropertyValueFactory<Empleado,String>("nombre"));
    cellApellido.setCellValueFactory(new PropertyValueFactory<Empleado,String>("apellido"));
    cellTelefono.setCellValueFactory(new PropertyValueFactory<Empleado,String>("telefono"));
    cellDpi.setCellValueFactory(new PropertyValueFactory<Empleado,String>("dpi"));
    cellEstado.setCellValueFactory(new PropertyValueFactory<Empleado,String>("estado"));

    Callback<TableColumn<Empleado, String>,TableCell<Empleado,String>> cellFactory=(TableColumn<Empleado,String> param)->{
        final TableCell<Empleado,String> cell=new TableCell<>(){
            @Override
            public  void  updateItem(String item, boolean empty){
                if (empty){
                    setGraphic(null);
                }else{
                    ImageView editButon=new ImageView(estiloBoton.editImg());
                    editButon.setFitHeight(estiloBoton.sizeButton());
                    editButon.setFitWidth(estiloBoton.sizeButton());
                    editButon.setStyle(estiloBoton.Boton());
                    //editButon.setStyle("-fx-background-color:red;");

                    ImageView deleteButon=new ImageView(estiloBoton.deleteImg());
                    deleteButon.setFitHeight(estiloBoton.sizeButton());
                    deleteButon.setFitWidth(estiloBoton.sizeButton());
                    deleteButon.setStyle(estiloBoton.Boton());
                    //deleteButon.setStyle("-fx-background-color:green; width:200px;");




                   /*  editButon.setOnMouseClicked((MouseEvent event)->{
                        Empleado empleado=tblEmpleado.getSelectionModel().getSelectedItem();
                        EditarEmpleado(empleado);

                    });
                    deleteButon.setOnMouseClicked((MouseEvent event)->{
                        Empleado empleado=tblEmpleado.getSelectionModel().getSelectedItem();
                        EliminarEmpleado(empleado);
                    });
*/


                   editButon.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            Empleado empleado=tblEmpleado.getSelectionModel().getSelectedItem();
                            EditarEmpleado(empleado);
                        }
                    });
                    deleteButon.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            Empleado empleado=tblEmpleado.getSelectionModel().getSelectedItem();
                            EliminarEmpleado(empleado);
                        }
                    });
                    HBox containButton=new HBox(deleteButon,editButon);
                    containButton.setStyle("-fx-alignment:center");
                    HBox.setMargin(deleteButon,new Insets(2,10,2,2));
                    HBox.setMargin(editButon, new Insets(2,2,2,10));
                    setGraphic(containButton);
                }
                setText(null);
            }
        };
        return cell;
    };
    cellopciones.setCellFactory(cellFactory);
    //tblEmpleado.setEditable(true);
    tblEmpleado.getColumns().addAll(cellCodigo,cellNombre,cellApellido,cellTelefono,cellDpi,cellEstado,cellopciones);
    Platform.runLater(()-> size_tabla.ajustarColumna(tblEmpleado));

    cellEstado.setCellFactory(new Callback<TableColumn<Empleado, String>, TableCell<Empleado, String>>() {
        @Override
        public TableCell<Empleado, String> call(TableColumn<Empleado, String> tableColumn) {
            return  new TableCell<Empleado,String>(){
                @Override
                protected void updateItem(String item, boolean empty){
                    super.updateItem(item,empty);
                    if (!empty){
                        if (item.equals("Activo")){
                            //setStyle("-fx-text-fill:#30CF9D;-fx-font-weight:bold;");
                            setStyle(estiloBoton.Activo1());
                        }else{
                            //getStyleClass().add("NoActivo");
                            //setStyle("-fx-text-fill:#D12E33; -fx-font-weight:bold;");
                            setStyle(estiloBoton.NoActivo1());

                        }
                        setText(item);
                    }else{
                        setText("");
                    }
                }
            };
        }
    });
}

    public void llenarListaEmpleado(){
        DataEmpleado datos=new DataEmpleado();
        empleados = FXCollections.observableArrayList(datos.viewEmpleado(new Empleado(0,"","","",0,""),"viewall"));
        empleadodata=new FilteredList<Empleado>(empleados,s->true);
        tblEmpleado.setItems(empleadodata);
        //capturar el texto y filtrar
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

    public void nuevoEmpleado(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Empleado/FormEmpleado.fxml"));
            Parent parent = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Nuevo Empleado");
            stage.setScene(new Scene(parent));
            stage.getIcons().add(new Image("/Img/icon.png"));
            stage.setOnHiding((event ->{
                llenarListaEmpleado();
                tblEmpleado.refresh();
            }));
            stage.show();

        }catch (IOException e){
            e.printStackTrace();
            AlertDialog alertDialog=new AlertDialog();
            alertDialog.alert("",""+e);

        }
    }
public void EditarEmpleado(Empleado empleado){
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Empleado/FormEmpleado.fxml"));
        Parent parent = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Modificar empleado");
        stage.getIcons().add(new Image("/Img/icon.png"));
        stage.setScene(new Scene(parent));
        FormEmpleado formEmpleado = loader.<FormEmpleado>getController();
        formEmpleado.pasarRegistro(empleado);
        stage.show();
        stage.setOnHiding((event -> {
           llenarListaEmpleado();
           tblEmpleado.refresh();
        }));

    } catch (IOException e) {
        e.printStackTrace();

    }
}

public void EliminarEmpleado(Empleado empleado){
    if (alertDialog.alertConfirm("Empleado", "esta seguro de elliminar al empleado "+ empleado.getNombre() )){
        DataEmpleado datos=new DataEmpleado();
        datos.crudEmpleado(empleado,"delete");
       llenarListaEmpleado();
       tblEmpleado.refresh();

    }
}
    }

