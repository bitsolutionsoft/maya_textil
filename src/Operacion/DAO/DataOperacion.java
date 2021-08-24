package Operacion.DAO;

import ClassAux.Util;
import Conexion.Conexion;
import Operacion.Estilo.DAO.Estilo;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataOperacion {

    public ArrayList<Operacion> viewOperacion(Operacion op, String accion){


        ArrayList<Operacion> lista=new ArrayList<Operacion>();
        try {
            Conexion conexion =new Conexion();

            conexion.Conexion();
            CallableStatement callableStatement=conexion.con.prepareCall("{call ingreso_operacion(?, ?, ?, ?, ?, ?, ?)}");
            callableStatement.setInt(1,op.codigo);
            callableStatement.setInt(2,op.codigoTipo);
            callableStatement.setInt(3,op.codigoEstilo);
            callableStatement.setString(4,op.nombre);
            callableStatement.setFloat(5,op.precio);
            callableStatement.setFloat(6,op.variacion);
            callableStatement.setString(7,accion);

            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()){
                Operacion operacion=new Operacion(
                        resultSet.getInt("idoperacion"),
                        resultSet.getInt("idtipo"),
                        resultSet.getInt("idestilo"),
                        resultSet.getString("nombre"),
                        resultSet.getFloat("precio"),
                        resultSet.getFloat("variacion")
                        ) ;
                lista.add(operacion);
            }

            callableStatement.close();
            conexion.con.close();
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return lista;
    }
    public boolean  crudOperacion(Operacion op, String accion){
        try {
            Conexion conexion =new Conexion();

            conexion.Conexion();
            CallableStatement callableStatement=conexion.con.prepareCall("{call ingreso_operacion(?, ?, ?, ?, ?, ?, ?)}");
            callableStatement.setInt(1,op.codigo);
            callableStatement.setInt(2,op.codigoTipo);
            callableStatement.setInt(3,op.codigoEstilo);
            callableStatement.setString(4,op.nombre);
            callableStatement.setFloat(5,op.precio);
            callableStatement.setFloat(6,op.variacion);
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
