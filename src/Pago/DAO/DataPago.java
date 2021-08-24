package Pago.DAO;

import ClassAux.Util;
import Conexion.Conexion;
import Empleado.DAO.Empleado;

import java.sql.*;
import java.util.ArrayList;

public class DataPago {
    public ArrayList<Pago> viewPago(Pago pago, String accion){


        ArrayList<Pago> lista=new ArrayList<Pago>();
        try {
            Conexion conexion =new Conexion();

            conexion.Conexion();
            CallableStatement callableStatement=conexion.con.prepareCall("{call ingreso_pago(?, ?, ?, ?, ?)}");
            callableStatement.setInt(1,pago.idpago);
            callableStatement.setString(2, pago.idcorte);
            callableStatement.setString(3, pago.fecha_pago);
            callableStatement.setFloat(4,pago.total);
            callableStatement.setString(5,accion);

            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()){
                Pago pg=new Pago();
                pg.setIdpago(resultSet.getInt("idpago" ));
                pg.setIdcorte(resultSet.getString("idcorte"));
                pg.setFecha_pago(resultSet.getString("fecha_pago"));
                pg.setTotal(resultSet.getFloat("total"));

                lista.add(pg);
            }
            callableStatement.close();
            conexion.con.close();
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return lista;
    }
    public boolean crudPago(Pago pago, String accion){


        try {
            Conexion conexion =new Conexion();
            conexion.Conexion();
            CallableStatement callableStatement=conexion.con.prepareCall("{call ingreso_pago(?, ?, ?, ?, ?)}");
            callableStatement.setInt(1,pago.idpago);
            callableStatement.setString(2, pago.idcorte);
            callableStatement.setString(3, pago.fecha_pago);
            callableStatement.setFloat(4,pago.total);
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
    public int numeroPago() {
        Conexion conexion =new Conexion();
        Connection connection= conexion.Conexion();
        int codigo=0;
        String sql = "select * from numero_pago";

        try {
            conexion.Conexion();
            Statement statement= connection.createStatement();
            ResultSet resultSet=statement.executeQuery(sql);


            while (resultSet.next()) {
                codigo=resultSet.getInt("numeropago");
            }
            resultSet.close();

            connection.close();
            statement.close();
            conexion.Conexion().close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return codigo;
    }


}
