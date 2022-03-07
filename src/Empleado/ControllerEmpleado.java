package Empleado;

import ClassAux.*;
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
import javafx.geometry.Pos;
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
   SetBotonIcon setBotonIcon=new SetBotonIcon();

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

                    Button editButon=new Button();
                    editButon.setGraphic(setBotonIcon.ImgUpdate());
                   editButon.setStyle(setBotonIcon.ButtonStyle());
                    Button deleteButon=new Button();
                    deleteButon.setGraphic(setBotonIcon.ImgDelete());
                   deleteButon.setStyle(setBotonIcon.ButtonStyle());

                   editButon.setOnAction(new EventHandler<ActionEvent>() {
                       @Override
                       public void handle(ActionEvent actionEvent) {
                           Empleado empleado=getTableView().getItems().get(getIndex());
                           EditarEmpleado(empleado);
                       }
                   });
                   deleteButon.setOnAction(new EventHandler<ActionEvent>() {
                       @Override
                       public void handle(ActionEvent actionEvent) {
                           Empleado empleado=getTableView().getItems().get(getIndex());
                           EliminarEmpleado(empleado);
                       }
                   });

                    HBox containButton=new HBox(deleteButon,editButon);
                    containButton.setAlignment(Pos.CENTER);
                    containButton.setSpacing(1);
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
                            setStyle(setBotonIcon.Activo());
                        }else{

                            setStyle(setBotonIcon.NoActivo());

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

