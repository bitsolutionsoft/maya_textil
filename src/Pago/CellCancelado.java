package Pago;

import ClassAux.AlertDialog;
import Empleado.DAO.Empleado;
import Pago.DAO.DataDetallePago;
import Pago.DAO.DetallePago;
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

public class CellCancelado extends ListCell<DetallePago> {
    AlertDialog alertDialog=new AlertDialog();
    private Node graphic;
    private RowPagado rowPagado;
    // el constructor donde llamamos el el rowproducto
    public CellCancelado(){
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Pago/RowPagado.fxml"));
        try {
            graphic=loader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        rowPagado=loader.getController();
        rowPagado.btnEliminar.setOnMouseClicked(new EventHandler<MouseEvent>() {
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
        rowPagado.btnEditar.setOnMouseClicked(new EventHandler<MouseEvent>() {
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



    }

    @Override
    protected void updateItem(DetallePago detalle, boolean empty){
        super.updateItem(detalle, empty);
        if (empty){
            clearContent();
        }else{
            addContent(detalle);
        }
    }


    private void clearContent(){
        setGraphic(null);
    }
    private void addContent(DetallePago detalle){
        setText(null);
        //  rowProducto.setAncho(getListView().getWidth()-16);
        rowPagado.setCodigo(String.valueOf(detalle.getIddetalle()));
        rowPagado.setNombre(String.valueOf(detalle.getNombre()));
        rowPagado.setCantidad(String.valueOf(detalle.getCantidad()));
        rowPagado.setPrecio(String.valueOf(detalle.getPrecio()));
        rowPagado.setTotal(String.valueOf(detalle.getTotal()));
        rowPagado.setEstadoPago(detalle.getEstado());
        rowPagado.setFecha(detalle.getFecha());
        setGraphic(graphic);
    }
}
