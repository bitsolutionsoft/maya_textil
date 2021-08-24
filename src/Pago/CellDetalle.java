package Pago;

import ClassAux.AlertDialog;
import Empleado.DAO.DataEmpleado;
import Empleado.DAO.Empleado;
import Pago.DAO.DataDetallePago;
import Pago.DAO.DetallePago;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class CellDetalle extends ListCell<DetallePago> {
    AlertDialog alertDialog=new AlertDialog();
    private Node graphic;
    private RowPago rowPago;
    // el constructor donde llamamos el el rowproducto
    public CellDetalle(){
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Pago/RowPago.fxml"));
        try {
            graphic=loader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        rowPago=loader.getController();
        rowPago.btnEliminar.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if (alertDialog.alertConfirm("Empleado", "esta seguro de elliminar es tarea")){
                    DetallePago detalle=getListView().getSelectionModel().getSelectedItem();
                    Empleado empleado=new Empleado(detalle.getIdempleado(),"","","",0,"");
                    DataDetallePago datos=new DataDetallePago();
                    datos.crudDetallePago(detalle,"delete");
                    PagoController controller=new PagoController();
                    controller.llenarTarea(getListView(),empleado);
                    getListView().refresh();

                }


            }
        });
        rowPago.btnEditar.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                PagoController controllers=new PagoController();

                DetallePago detalle=getListView().getSelectionModel().getSelectedItem();
                Empleado empleado=new Empleado(detalle.getIdempleado(),"","","",0,"");
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Pago/FormAsignar.fxml"));
                    Parent parent = loader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Modificar producto");
                    stage.getIcons().add(new Image("/Img/icon.png"));
                    stage.setScene(new Scene(parent));
                    FormAsignar formAsignar = loader.<FormAsignar>getController();
                    formAsignar.pasarRegistroEditar(detalle);
                    stage.show();

                    stage.setOnHiding((events -> {
                        PagoController controller = new PagoController();
                        controller.llenarTarea(getListView(),empleado);
                        getListView().refresh();
                    }));

                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        });

        //el evento del botono eliminar para eliminar productos
        /*
        rowEmpleado.btnEliminar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (alertDialog.alertConfirm("Empleado", "esta seguro de elliminar al empleado")){
                    Empleado pro=new Empleado(Integer.parseInt(rowEmpleado.codigo.getText()),"x","x","x",0,"x");
                    DataEmpleado datos=new DataEmpleado();
                    datos.crudEmpleado(pro,"delete");
                    PagoController empleadoController=new PagoController();
                    empleadoController.initLista(getListView());
                    getListView().refresh();

                }
            }
        });*/
        //aquii dejas los eventos del boton donde los tenes te dar problema por el costructor
        //evento del boton modificar _______________________________________________________________________________________//

      /*  rowEmpleado.btnEditar.setOnAction(new EventHandler<ActionEvent>() {
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
                    stage.getIcons().add(new Image("/img/icon.png"));
                    stage.setScene(new Scene(parent));
                    FormAsignar formEmpleado = loader.<FormAsignar>getController();
                    formEmpleado.pasarRegistro(empleado);
                    stage.show();
                    stage.setOnHiding((event -> {
                        PagoController Controllerempleado = new PagoController();
                        Controllerempleado.initLista(getListView());
                        getListView().refresh();
                    }));

                } catch (IOException e) {
                    e.printStackTrace();

                }
            }

        });*/


    }

    //aqui llenas la lista con  el rowProducto
    @Override
    protected void updateItem(DetallePago detalle, boolean empty){
        super.updateItem(detalle, empty);
        if (empty){
            clearContent();
        }else{
            addContent(detalle);
            //  rowProducto.pasar(getItem());
        }
    }


    //para limpiar contenido
    private void clearContent(){
        setGraphic(null);
    }
    //agregamos contenido a cada label creado en el rowproducto
    private void addContent(DetallePago detalle){
        setText(null);
        //  rowProducto.setAncho(getListView().getWidth()-16);
        rowPago.setCodigo(String.valueOf(detalle.getIddetalle()));
        rowPago.setNombre(String.valueOf(detalle.getNombre()));
        rowPago.setCantidad(String.valueOf(detalle.getCantidad()));
        rowPago.setPrecio(String.valueOf(detalle.getPrecio()));
        rowPago.setTotal(String.valueOf(detalle.getTotal()));
        rowPago.setEstadoPago(detalle.getEstado());
        setGraphic(graphic);
    }
}
