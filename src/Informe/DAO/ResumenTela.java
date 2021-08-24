package Informe.DAO;

public class ResumenTela {

    int idRollos, idtela, Cantidad;
    String idcorte;
    public ResumenTela() {
    }

    public ResumenTela(int idRollos, String idcorte,int idtela, int Cantidad) {
        this.idRollos = idRollos;
        this.idcorte = idcorte;
        this.idtela = idtela;
        this.Cantidad = Cantidad;
    }

    public int getIdRollos() {
        return idRollos;
    }

    public void setIdRollos(int idRollos) {
        this.idRollos = idRollos;
    }

    public int getIdtela() {
        return idtela;
    }

    public void setIdtela(int idtela) {
        this.idtela = idtela;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int cantidad) {
        Cantidad = cantidad;
    }

    public String getIdcorte() {
        return idcorte;
    }

    public void setIdcorte(String idcorte) {
        this.idcorte = idcorte;
    }
}
