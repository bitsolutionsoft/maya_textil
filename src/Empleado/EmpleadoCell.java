package Empleado;

import ClassAux.AlertDialog;
import Empleado.DAO.DataEmpleado;
import Empleado.DAO.Empleado;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class EmpleadoCell extends ListCell<Empleado> {
    AlertDialog alertDialog=new AlertDialog();
    private Node graphic;
    private RowEmpleado rowEmpleado;
    // el constructor donde llamamos el el rowproducto
    public EmpleadoCell(){
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Empleado/RowEmpleado.fxml"));
        try {
            graphic=loader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        rowEmpleado=loader.getController();
        //el evento del botono eliminar para eliminar productos
        rowEmpleado.btnEliminar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (alertDialog.alertConfirm("Empleado", "esta seguro de elliminar al empleado")){
                    Empleado pro=new Empleado(Integer.parseInt(rowEmpleado.codigo.getText()),"x","x","x",0,"x");
                    DataEmpleado datos=new DataEmpleado();
                    datos.crudEmpleado(pro,"delete");
                    ControllerEmpleado empleadoController=new ControllerEmpleado();
                    empleadoController.initLista(getListView());
                    getListView().refresh();

                }
            }
        });
        //aquii dejas los eventos del boton donde los tenes te dar problema por el costructor
        //evento del boton modificar _______________________________________________________________________________________//

        rowEmpleado.btnEditar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                Empleado empleado = new Empleado();
                for (int i = 0; i < getListView().getItems().size(); i++) {
                    if (Integer.parseInt(rowEmpleado.codigo.getText()) == getListView().getItems().get(i).getCodigo()) {
                        empleado.setCodigo(getListView().getItems().get(i).getCodigo());
                        empleado.setNombre(getListView().getItems().get(i).getNombre());
                        empleado.setApellido(getListView().getItems().get(i).getApellido());
                        empleado.setDpi(getListView().getItems().get(i).getDpi());
                        empleado.setTelefono(getListView().getItems().get(i).getTelefono());
                        empleado.setEstado(getListView().getItems().get(i).getEstado());


                    }
                }
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Empleado/FormEmpleado.fxml"));
                    Parent parent = loader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Modificar producto");
                    stage.getIcons().add(new Image("/Img/icon.png"));
                    stage.setScene(new Scene(parent));
                    FormEmpleado formEmpleado = loader.<FormEmpleado>getController();
                    formEmpleado.pasarRegistro(empleado);
                    stage.show();
                    stage.setOnHiding((event -> {
                        ControllerEmpleado Controllerempleado = new ControllerEmpleado();
                        Controllerempleado.initLista(getListView());
                        getListView().refresh();
                    }));

                } catch (IOException e) {
                    e.printStackTrace();

                }
            }

        });
        //final del evento modificar


    }
    //aqui llenas la lista con  el rowProducto
    @Override
    protected void updateItem(Empleado empleado, boolean empty){
        super.updateItem(empleado, empty);
        if (empty){
            clearContent();
        }else{
            addContent(empleado);
            //  rowProducto.pasar(getItem());
        }
    }


    //para limpiar contenido
    private void clearContent(){
        setGraphic(null);
    }
    //agregamos contenido a cada label creado en el rowproducto
    private void addContent(Empleado empleado){
        setText(null);
        //  rowProducto.setAncho(getListView().getWidth()-16);
        rowEmpleado.setCodigo(empleado.getCodigo());
        rowEmpleado.setNombre(empleado.getNombre()+"  "+empleado.getApellido());
        rowEmpleado.setTelefono(empleado.getTelefono());
        rowEmpleado.setDpi(empleado.getDpi());//este
        rowEmpleado.setEstado(empleado.getEstado());

        setGraphic(graphic);
    }

}
