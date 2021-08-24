package Pago;

import Empleado.DAO.DataEmpleado;
import Empleado.DAO.Empleado;
import Pago.DAO.Adelanto;
import Pago.DAO.DataAdelanto;
import Pago.DAO.DataDetallePago;
import Pago.DAO.DetallePago;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Historial implements Initializable {
    static ObservableList<DetallePago> listdatellePago;
    static FilteredList<DetallePago> filterDetallePago;
    public ListView<Empleado> listEmpleado;
    public ListView<DetallePago>listPago;
    public Label labelTitulo;
    private Empleado Seleccionado;

    public void cancelados(){


    }

    public void nuevoPago(ActionEvent actionEvent) {
    }
    public void pasarRegistro(Empleado empleado) {
        if (empleado != null) {
            labelTitulo.setText(String.valueOf(empleado.getNombre()+""+empleado.getApellido()));
            Seleccionado=empleado;
            llenarCancelado(listPago,Seleccionado);
        }
    }
    public  void llenarCancelado(ListView<DetallePago> listView,Empleado empleado){
        DataDetallePago datos=new DataDetallePago();
        listdatellePago = FXCollections.observableArrayList(datos.viewDetallePagoXEmp(new DetallePago(0,0,empleado.getCodigo(),0,0,0,0,0,"Cancelado"),"viewxemp"));
        filterDetallePago=new FilteredList<DetallePago>(listdatellePago,s->true);
        listView.setItems(filterDetallePago);
        listView.setCellFactory(new Callback<ListView<DetallePago>, ListCell<DetallePago>>() {
            @Override
            public ListCell<DetallePago> call(ListView<DetallePago> listView) {
                CellCancelado cellCancelado=new CellCancelado();
                return  cellCancelado;
            }
        });

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
