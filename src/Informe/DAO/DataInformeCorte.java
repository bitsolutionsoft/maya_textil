package Informe.DAO;

import Conexion.Conexion;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataInformeCorte {

    public ArrayList<InformeCorte> viewCortes(Cortes cortes, String accion){


        ArrayList<InformeCorte> lista=new ArrayList<>();
        try {
            Conexion conexion =new Conexion();

            conexion.Conexion();
            CallableStatement callableStatement=conexion.con.prepareCall("{call informe_corte_cantidad( ?, ?, ?)}");

            callableStatement.setString(1, cortes.getfInicial());
            callableStatement.setString(2,cortes.getfFinal());
            callableStatement.setString(3,accion);

            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()){
                InformeCorte informeCorte=new InformeCorte();
                informeCorte.setCanCorte(resultSet.getInt("cantidadcorte"));
                informeCorte.setCantPantalon(resultSet.getInt("cantidadpantalon"));
                lista.add(informeCorte);
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
