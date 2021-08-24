package Corte;

import ClassAux.AlertDialog;

import Corte.DAO.Corte;
import Corte.DAO.DataRollos;
import Corte.DAO.Rollos;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class CellTela extends ListCell<Rollos> {
    AlertDialog alertDialog=new AlertDialog();
    private Node graphic;
    private RowTela rowtela;

    public CellTela(){
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Corte/RowTela.fxml"));
        try {
            graphic=loader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        rowtela=loader.getController();
rowtela.btnEliminar.setOnMouseClicked(new EventHandler<MouseEvent>() {
    @Override
    public void handle(MouseEvent event) {

        if (alertDialog.alertConfirm("","Esta seguiro de eliminar este registro")) {
            Rollos rollos = getListView().getSelectionModel().getSelectedItem();
            DataRollos dataRollos=new DataRollos();
            dataRollos.crudCorte(rollos,"delete");

            CorteController controller=new CorteController();
            controller.initListaTela(getListView(),new Corte(rollos.getIdcorte(),0,0,"2021-08-12",0,""));
            getListView().refresh();
        }
    }
});

    }

    @Override
    protected void updateItem(Rollos rollos, boolean empty){
        super.updateItem(rollos, empty);
        if (empty){
            clearContent();
        }else{
            addContent(rollos);

        }
    }


    private void clearContent(){
        setGraphic(null);
    }

    private void addContent(Rollos rollos){
        setText(null);
        rowtela.setIdrollo(String.valueOf(rollos.getIdrollo()));
        rowtela.setCodigotela(String.valueOf(rollos.getCodigo_tela()));
        rowtela.setCantidad(String.valueOf(rollos.getCantidad()));
        rowtela.setTipo(String.valueOf(rollos.getTipo()));
        rowtela.setColores(String.valueOf(rollos.getColor()));


        setGraphic(graphic);
    }


}
