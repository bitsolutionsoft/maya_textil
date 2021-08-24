package Operacion.Estilo.DAO;

import ClassAux.Util;
import Conexion.Conexion;


import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EstiloData {

    public ArrayList<Estilo> viewEstilo(Estilo estilo, String accion){


        ArrayList<Estilo> lista=new ArrayList<Estilo>();
        try {
            Conexion conexion =new Conexion();

            conexion.Conexion();
            CallableStatement callableStatement=conexion.con.prepareCall("{call ingreso_estilo(?, ?, ?, ?)}");
            callableStatement.setInt(1,estilo.codigo);
            callableStatement.setString(2,estilo.nombre);
            callableStatement.setString(3,estilo.estado);
            callableStatement.setString(4,accion);

            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()){
                Estilo estilos=new Estilo(resultSet.getInt("idestilo"),resultSet.getString("nombre"),resultSet.getString("estado")) ;
               /* estilos.setCodigo(resultSet.getInt("idestilo"));
                estilos.setNombre(resultSet.getString("nombre"));
                estilos.setEstado(resultSet.getString("estado"));
                */


                lista.add(estilos);
            }

            callableStatement.close();
            conexion.con.close();
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return lista;
    }
    public boolean  crudEStilo(Estilo estilo, String accion){
        try {
            Conexion conexion =new Conexion();

            conexion.Conexion();
            CallableStatement callableStatement=conexion.con.prepareCall("{call ingreso_estilo(?, ?, ?, ?)}");
            callableStatement.setInt(1,estilo.codigo);
            callableStatement.setString(2,estilo.nombre);
            callableStatement.setString(3,estilo.estado);
            callableStatement.setString(4,accion);
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
