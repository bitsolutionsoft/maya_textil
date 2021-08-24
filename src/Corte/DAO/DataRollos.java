package Corte.DAO;

import ClassAux.Util;
import Conexion.Conexion;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataRollos {
    public ArrayList<Rollos> viewTela( Rollos rollos, String accion){
        ArrayList<Rollos> lista=new ArrayList<Rollos>();
        try {
            Conexion conexion =new Conexion();

            conexion.Conexion();
            CallableStatement callableStatement=conexion.con.prepareCall("{call ingreso_rollo_utilizado(?, ?, ?, ?, ?)}");
            callableStatement.setInt(1,rollos.idrollo);
            callableStatement.setString(2,rollos.idcorte);
            callableStatement.setInt(3,rollos.idetela);
            callableStatement.setInt(4,rollos.cantidad);
            callableStatement.setString(5,accion);

            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()){
                Rollos rollos1=new Rollos();
                rollos1.setIdrollo(resultSet.getInt("idRollos"));
                rollos1.setIdcorte(resultSet.getString("idcorte"));
                rollos1.setCodigo_tela(resultSet.getString("codigo_tela"));
                rollos1.setColor(resultSet.getString("colores"));
                rollos1.setTipo(resultSet.getString("tipo"));
                rollos1.setIdetela(resultSet.getInt("idtela"));
                rollos1.setCantidad(resultSet.getInt("cantidad"));
                lista.add(rollos1);
            }

            callableStatement.close();
            conexion.con.close();
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return lista;
    }
    public boolean crudCorte(Rollos rollos, String accion){


        try {
            Conexion conexion =new Conexion();

            conexion.Conexion();
            CallableStatement callableStatement=conexion.con.prepareCall("{call ingreso_rollo_utilizado(?, ?, ?, ?, ?)}");
            callableStatement.setInt(1,rollos.idrollo);
            callableStatement.setString(2,rollos.idcorte);
            callableStatement.setInt(3,rollos.idetela);
            callableStatement.setInt(4,rollos.cantidad);
            callableStatement.setString(5,accion);

            callableStatement.execute();
            Util.Exito("Operacion","Realizado con exito:");

            callableStatement.close();
            conexion.con.close();
            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }



    }
}
