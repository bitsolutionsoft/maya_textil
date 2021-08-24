package Informe;

import Bodega.DAO.Bodega;
import Bodega.DAO.DataBodega;
import ClassAux.AlertDialog;
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

public class CellBodega extends ListCell<Bodega> {
    AlertDialog alertDialog=new AlertDialog();
    private Node graphic;
    private RowBodega rowBodega;
    // el constructor donde llamamos el el rowproducto
    public CellBodega(){
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Informe/RowBodega.fxml"));
        try {
            graphic=loader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        rowBodega=loader.getController();
        //el evento del botono eliminar para eliminar productos

        //aquii dejas los eventos del boton donde los tenes te dar problema por el costructor
        //evento del boton modificar _______________________________________________________________________________________//

        rowBodega.btnEditar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                Bodega bodega = new Bodega();
                for (int i = 0; i < getListView().getItems().size(); i++) {
                    if (Integer.parseInt(rowBodega.codigo.getText()) == getListView().getItems().get(i).getIdBodega()) {
                        bodega.setIdBodega(getListView().getItems().get(i).getIdBodega());
                        bodega.setCodigo_tela(getListView().getItems().get(i).getCodigo_tela());
                        bodega.setTipo(getListView().getItems().get(i).getTipo());
                        bodega.setColores(getListView().getItems().get(i).getColores());
                        bodega.setCantidad(getListView().getItems().get(i).getCantidad());
                        bodega.setPrecio(getListView().getItems().get(i).getPrecio());
                        bodega.setTotal(getListView().getItems().get(i).getTotal());


                    }
                }
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Informe/Informe.fxml"));
                    Parent parent = loader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Maya Textil");
                    stage.getIcons().add(new Image("/img/icon.png"));
                    stage.setScene(new Scene(parent));
                    InformeController informeController = loader.getController();
                    //InformeController.pasarRegistro(bodega);
                    stage.show();
                    stage.setOnHiding((event -> {
                        InformeController controller = new InformeController();
                        controller.initList(getListView());
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
    protected void updateItem(Bodega bodega, boolean empty){
        super.updateItem(bodega, empty);
        if (empty){
            clearContent();
        }else{
            addContent(bodega);
            //  rowProducto.pasar(getItem());
        }
    }


    //para limpiar contenido
    private void clearContent(){
        setGraphic(null);
    }
    //agregamos contenido a cada label creado en el rowproducto
    private void addContent(Bodega bodega){
        setText(null);
        //  rowProducto.setAncho(getListView().getWidth()-16);
        rowBodega.setCodigo(String.valueOf(bodega.getIdBodega()));
        rowBodega.setCodigo_tela(String.valueOf(bodega.getCodigo_tela()));
        rowBodega.setTipo(bodega.getTipo());
        rowBodega.setColor(bodega.getColores());
        rowBodega.setCantidad(String.valueOf(bodega.getCantidad()));
        rowBodega.setPrecio(String.valueOf(bodega.getPrecio()));
        rowBodega.setTotal(String.valueOf(bodega.getTotal()));

        setGraphic(graphic);
    }
}
