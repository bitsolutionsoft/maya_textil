package Informe.DAO;

public class InformeCorte {
    int canCorte, cantPantalon;

    public InformeCorte() {
    }

    public InformeCorte(int canCorte, int cantPantalon) {
        this.canCorte = canCorte;
        this.cantPantalon = cantPantalon;
    }

    public int getCanCorte() {
        return canCorte;
    }

    public void setCanCorte(int canCorte) {
        this.canCorte = canCorte;
    }

    public int getCantPantalon() {
        return cantPantalon;
    }

    public void setCantPantalon(int cantPantalon) {
        this.cantPantalon = cantPantalon;
    }
}
