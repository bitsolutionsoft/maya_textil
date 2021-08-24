package Informe.DAO;

import Conexion.Conexion;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataResumPago {


    public ArrayList<ResumenPago> viewResumPago(int codigo){


        ArrayList<ResumenPago> lista=new ArrayList<>();
        try {
            Conexion conexion =new Conexion();

            conexion.Conexion();
            CallableStatement callableStatement=conexion.con.prepareCall("{call informe_detallepago(?)}");

            callableStatement.setInt(1, codigo);

            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()){
                ResumenPago resumenPago=new ResumenPago();
                resumenPago.setIdpago(resultSet.getInt("idpago"));
                resumenPago.setIdcorte(resultSet.getString("idcorte"));
                resumenPago.setTotal(resultSet.getFloat("total"));
                resumenPago.setSubtotal(resultSet.getFloat("subtotal"));

                resumenPago.setNombre(resultSet.getString("nombre"));
                resumenPago.setTipo(resultSet.getInt("idtipo"));
                lista.add(resumenPago);
            }

            callableStatement.close();
            conexion.con.close();
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return lista;
    }
}
