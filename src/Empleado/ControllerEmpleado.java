package Empleado;

import ClassAux.AlertDialog;
import ClassAux.Util;
import Empleado.DAO.DataEmpleado;
import Empleado.DAO.Empleado;
import Usuario.DAO.DataUsuario;
import Usuario.DAO.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerEmpleado implements Initializable {
public Button btnIngresarNuevo;
    public ListView<Empleado> listEmpleado;
    public TextField txtBuscar;
    static ObservableList<Empleado> empleados;
    static FilteredList<Empleado> empleadodata;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initLista(listEmpleado);
        llenarListaEmpleado();
    }
    public void initLista(ListView<Empleado> listView){
        DataEmpleado datos=new DataEmpleado();
        empleados = FXCollections.observableArrayList(datos.viewEmpleado(new Empleado(0,"","","",0,""),"viewall"));
        empleadodata=new FilteredList<Empleado>(empleados,s->true);
        listView.setItems(empleadodata);
        //para llenar las filas personalizadas

        listView.setCellFactory(new Callback<ListView<Empleado>, ListCell<Empleado>>() {
            @Override
            public ListCell<Empleado> call(ListView<Empleado> param) {
                EmpleadoCell empleadoCell=new EmpleadoCell();
                return empleadoCell;
            }
        });

    }

    public void llenarListaEmpleado(){

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
            stage.setScene(new Scene(parent));
            stage.getIcons().add(new Image("/Img/icon.png"));
            stage.setOnHiding((event ->{
                initLista(listEmpleado);
                listEmpleado.refresh();
            }));
            stage.show();

        }catch (IOException e){
            e.printStackTrace();
            AlertDialog alertDialog=new AlertDialog();
            alertDialog.alert("",""+e);

        }
    }

    }

