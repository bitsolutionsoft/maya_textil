package Permiso.DAO;

import ClassAux.Util;
import Conexion.Conexion;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ModulosData {
    public ArrayList<Modulo> viewModulo(Modulo modulo, String accion){
        ArrayList<Modulo> lista=new ArrayList<Modulo>();
        try {
            Conexion conexion =new Conexion();
            conexion.Conexion();
            CallableStatement callableStatement=conexion.con.prepareCall("{call ingreso_modulo(?, ?, ?)}");
            callableStatement.setInt(1,modulo.codigo);
            callableStatement.setString(2,modulo.nombre);
            callableStatement.setString(3,accion);

            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()){
                Modulo mod=new Modulo();
                mod.setCodigo(resultSet.getInt("idmodulo"));
                mod.setNombre(resultSet.getString("nombre"));
                lista.add(mod);
            }
            callableStatement.close();
            conexion.con.close();
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return lista;
    }
    public boolean  crudModulo(Modulo modulo, String accion){
        try {
            Conexion conexion =new Conexion();

            conexion.Conexion();
            CallableStatement callableStatement=conexion.con.prepareCall("{call ingreso_permiso(?, ?, ?)}");
            callableStatement.setInt(1,modulo.codigo);
            callableStatement.setString(2,modulo.nombre);
            callableStatement.setString(3,accion);
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
