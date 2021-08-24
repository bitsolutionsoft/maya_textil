package Corte.DAO;

import ClassAux.Util;
import Conexion.Conexion;
import Empleado.DAO.Empleado;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataCorte {
    public ArrayList<Corte> viewCorte(String accion){
        ArrayList<Corte> lista=new ArrayList<Corte>();
        try {
            Conexion conexion =new Conexion();

            conexion.Conexion();
            CallableStatement callableStatement=conexion.con.prepareCall("{call ingreso_corte(?, ?, ?, ?, ?, ?, ?)}");
            callableStatement.setString(1,"");
            callableStatement.setInt(2,0);
            callableStatement.setInt(3,0);
            callableStatement.setString(4,"2021-07-30");
            callableStatement.setInt(5,0);
            callableStatement.setString(6,"");
            callableStatement.setString(7,accion);

            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()){
                Corte corte=new Corte();
                corte.setIdcorte(resultSet.getString("idcorte"));
                corte.setIdestilo(resultSet.getInt("idestilo"));
                corte.setCantidad(resultSet.getInt("cantidad"));
                corte.setNombre(resultSet.getString("nombre"));
                corte.setFecha_corte(resultSet.getString("fecha_corte"));
                corte.setCant_rollo(resultSet.getInt("cant_rollo"));
                corte.setEstado(resultSet.getString("estado"));
                lista.add(corte);
            }

            callableStatement.close();
            conexion.con.close();
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return lista;
    }
    public boolean crudCorte(Corte corte, String accion){


        try {
            Conexion conexion =new Conexion();

            conexion.Conexion();
            CallableStatement callableStatement=conexion.con.prepareCall("{call ingreso_corte(?, ?, ?, ?, ?, ?,?)}");
            callableStatement.setString(1,corte.idcorte);
            callableStatement.setInt(2,corte.idestilo);
            callableStatement.setInt(3,corte.cantidad);
            callableStatement.setString(4,corte.fecha_corte);
            callableStatement.setInt(5,corte.cant_rollo);
            callableStatement.setString(6,corte.estado);
            callableStatement.setString(7,accion);

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
