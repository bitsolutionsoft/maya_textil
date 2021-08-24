package Informe.DAO;

public class Cortes {



    String  fFinal,fInicial;


    public Cortes() {
    }
    public Cortes(String fInicial, String fFinal) {
        this.fInicial = fInicial;
        this.fFinal = fFinal;
    }

    public String getfFinal() {
        return fFinal;
    }

    public void setfFinal(String fFinal) {
        this.fFinal = fFinal;
    }

    public String getfInicial() {
        return fInicial;
    }

    public void setfInicial(String fInicial) {
        this.fInicial = fInicial;
    }
}
