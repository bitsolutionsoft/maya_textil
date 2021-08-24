package Permiso.DAO;

import ClassAux.Util;
import Conexion.Conexion;
import Usuario.DAO.Usuario;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PermisoData {
    public ArrayList<Permiso> viewPermiso(Permiso permiso, String accion){
        ArrayList<Permiso> lista=new ArrayList<Permiso>();
        try {
            Conexion conexion =new Conexion();

            conexion.Conexion();
            CallableStatement callableStatement=conexion.con.prepareCall("{call ingreso_permiso(?, ?, ?, ?, ?)}");
            callableStatement.setInt(1,permiso.codigo);
            callableStatement.setInt(2,permiso.codigoUsuario);
            callableStatement.setInt(3,permiso.codigoModulo);
            callableStatement.setInt(4,permiso.acceso);
            callableStatement.setString(5,accion);

            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()){
                Permiso per=new Permiso();
                per.setCodigo(resultSet.getInt("idpermiso"));
                per.setCodigoUsuario(resultSet.getInt("idusuario"));
                per.setCodigoModulo(resultSet.getInt("idmodulo"));
                per.setAcceso(resultSet.getInt("acceso"));
                lista.add(per);
            }

            callableStatement.close();
            conexion.con.close();
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return lista;
    }
    public ArrayList<Permiso> viewPermisoActivo(Permiso permiso, String accion){
        ArrayList<Permiso> lista=new ArrayList<Permiso>();
        try {
            Conexion conexion =new Conexion();

            conexion.Conexion();
            CallableStatement callableStatement=conexion.con.prepareCall("{call ingreso_permiso(?, ?, ?, ?, ?)}");
            callableStatement.setInt(1,permiso.codigo);
            callableStatement.setInt(2,permiso.codigoUsuario);
            callableStatement.setInt(3,permiso.codigoModulo);
            callableStatement.setInt(4,permiso.acceso);
            callableStatement.setString(5,accion);

            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()){
                Permiso per=new Permiso();
                per.setCodigo(resultSet.getInt("idpermiso"));
                per.setCodigoUsuario(resultSet.getInt("idusuario"));
                per.setCodigoModulo(resultSet.getInt("idmodulo"));
                per.setNombre(resultSet.getString("nombre"));
                per.setAcceso(resultSet.getInt("acceso"));
                lista.add(per);
            }

            callableStatement.close();
            conexion.con.close();
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return lista;
    }
    public boolean  crudPermiso(Permiso permiso, String accion){
        try {
            Conexion conexion =new Conexion();

            conexion.Conexion();
            CallableStatement callableStatement=conexion.con.prepareCall("{call ingreso_permiso(?, ?, ?, ?, ?)}");
            callableStatement.setInt(1,permiso.codigo);
            callableStatement.setInt(2,permiso.codigoUsuario);
            callableStatement.setInt(3,permiso.codigoModulo);
            callableStatement.setInt(4,permiso.acceso);
            callableStatement.setString(5,accion);
            callableStatement.execute();
            callableStatement.close();
            Util.Exito("Operación","Realizado con exito:");
            conexion.con.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }


    }
}
