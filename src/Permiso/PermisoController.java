package Permiso;

import Permiso.DAO.Modulo;
import Permiso.DAO.ModulosData;
import Permiso.DAO.Permiso;
import Permiso.DAO.PermisoData;
import Usuario.DAO.DataUsuario;
import Usuario.DAO.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PermisoController implements Initializable {
    public Label lblUsuario;
    public Button btnIngresarNuevo;
    public TextField txtBuscar;
    public ListView <Permiso> ListPermiso;
  public static ObservableList<Permiso> listPermiso;
    public static   FilteredList<Permiso> permisoFilter;
    Usuario usuarioRecibido;
    ArrayList <Modulo> modulos;
    ArrayList<Permiso> permisos;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void guardarCambios(ActionEvent actionEvent) {
        if (listPermiso!=null){
            PermisoData permisoData=new PermisoData();
            for (int i=0;i<listPermiso.size();i++){
                permisoData.crudPermiso(new Permiso(
                        listPermiso.get(i).getCodigo(),
                        listPermiso.get(i).getCodigoUsuario(),
                        listPermiso.get(i).getCodigoModulo(),
                        listPermiso.get(i).getAcceso()
                ),"update");
            }
        }

    }
public void pasarUsuario(Usuario usuario){
if (usuario != null){
    usuarioRecibido=usuario;
lblUsuario.setText("Usuario:  "+usuarioRecibido.getNombre()+ "   "+usuarioRecibido.getApellido());
    iniciarLista(ListPermiso);
    llenarLista();
}
    }
    public void iniciarLista(ListView<Permiso> listView){
        listPermiso=FXCollections.observableArrayList(returnPermiso());
        permisoFilter=new FilteredList<Permiso>(listPermiso,s->true);
        listView.setItems(permisoFilter);
        listView.setCellFactory(new Callback<ListView<Permiso>, ListCell<Permiso>>() {
            @Override
            public ListCell<Permiso> call(ListView<Permiso> listView) {
                CellPermiso cellPermiso=new CellPermiso();
                return cellPermiso;
            }
        });


    }
    public  void llenarLista(){
        txtBuscar.textProperty().addListener((prop,old,text)->{
            permisoFilter.setPredicate(permiso -> {
                if (text ==null || text.isEmpty()){
                    return  true;
                }
                String texto=text.toLowerCase();
                if (permiso.getNombre().toLowerCase().contains(texto)){
                    return true;
                }
                return false;
            });
        });
    }

public ArrayList<Permiso> returnPermiso(){
    PermisoData permisoData=new PermisoData();
    ModulosData modulosData=new ModulosData();
   ArrayList <Permiso> list=new ArrayList<>(permisoData.viewPermisoActivo(new Permiso(0,usuarioRecibido.getCodigo(), 0,0),"viewper"));
   if (list.size()==0){

       ArrayList<Modulo> list1=new ArrayList<>(modulosData.viewModulo(new Modulo(0,""),"viewall"));
       for (int i=0;i<list1.size();i++){
           permisoData.crudPermiso(new Permiso(0,usuarioRecibido.getCodigo(),list1.get(i).getCodigo(),0),"new");
       }
     list=permisoData.viewPermisoActivo(new Permiso(0,usuarioRecibido.getCodigo(), 0,0),"viewper");
   }

   return list;
}


}
