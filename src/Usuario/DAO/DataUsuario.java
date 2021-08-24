package Usuario.DAO;

import ClassAux.Util;
import Conexion.Conexion;


import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataUsuario {

    public ArrayList<Usuario> viewUsuario(Usuario usuarios,String accion){


        ArrayList<Usuario> lista=new ArrayList<Usuario>();
        try {
            Conexion conexion =new Conexion();

            conexion.Conexion();
            CallableStatement callableStatement=conexion.con.prepareCall("{call ingreso_usuario(?, ?, ?, ?, ?, ?,?)}");
            callableStatement.setInt(1,usuarios.codigo);
            callableStatement.setString(2,usuarios.nombre);
            callableStatement.setString(3,usuarios.apellido);
            callableStatement.setString(4,usuarios.usuario);
            callableStatement.setString(5,usuarios.pass);
            callableStatement.setString(6,usuarios.dpi);
            callableStatement.setString(7,accion);

            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()){
                Usuario usuario=new Usuario();
                usuario.setCodigo(resultSet.getInt("idusuario"));
                usuario.setNombre(resultSet.getString("nombre"));
                usuario.setApellido(resultSet.getString("apellido"));
                usuario.setUsuario(resultSet.getString("usuario"));
                usuario.setPass(resultSet.getString("pass"));
                usuario.setDpi(resultSet.getString("dpi"));
                lista.add(usuario);
            }

            callableStatement.close();
            conexion.con.close();
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return lista;
    }
    public boolean  crudUsuario(Usuario usuario, String accion){
        try {
            Conexion conexion =new Conexion();

            conexion.Conexion();
            CallableStatement callableStatement=conexion.con.prepareCall("{call ingreso_usuario(?, ?, ?, ?, ?, ?,?)}");
            callableStatement.setInt(1,usuario.codigo);
            callableStatement.setString(2,usuario.nombre);
            callableStatement.setString(3,usuario.apellido);
            callableStatement.setString(4,usuario.usuario);
            callableStatement.setString(5,usuario.pass);
            callableStatement.setString(6,usuario.dpi);
            callableStatement.setString(7,accion);
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
