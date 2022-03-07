package Permiso;

import Permiso.DAO.Permiso;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import java.io.IOException;

public class CellPermiso extends ListCell<Permiso> {
    private Node graphic;
    private  RowPermiso rowPermiso;

    public CellPermiso(){
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Permiso/RowPermiso.fxml"));
       try {
           graphic=loader.load();
       }catch (IOException e){
           e.printStackTrace();
       }
        rowPermiso=loader.getController();

       rowPermiso.cboxAcceso.setOnAction(new EventHandler<ActionEvent>() {

           @Override
           public void handle(ActionEvent actionEvent) {
               int acceso=0;
               if (rowPermiso.cboxAcceso.isSelected()){
                 acceso=1;
               }else{
                  acceso=0;
               }

            //   PermisoController pr=new PermisoController();
               for (int i=0; i<PermisoController.listPermiso.size();i++){
                   if (Integer.parseInt(rowPermiso.codigo.getText())==PermisoController.listPermiso.get(i).getCodigo()){
                       PermisoController.listPermiso.get(i).setAcceso(acceso);
                   }
               }
               getListView().refresh();
           }
       });

    }
    @Override
    protected void updateItem(Permiso permiso,boolean empty){
        super.updateItem(permiso,empty);
        if (empty){
            clearContent();
        }else{
            addContent(permiso);
        }
    }
    private void clearContent(){setGraphic(null);}
    private  void addContent(Permiso permiso){
        setText(null);
        rowPermiso.setCodigo(String.valueOf(permiso.getCodigo()));
        rowPermiso.setNombre(permiso.getNombre());
        rowPermiso.setCboxAcceso(permiso.getAcceso());
        setGraphic(graphic);
    }

}
