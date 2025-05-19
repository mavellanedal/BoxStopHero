package models;

public class WrongWord {
    private String palabraCorrecta;
    private int dificultad;
    private String palabraEscrita;

    public WrongWord(String palabraCorrecta, int dificultad, String palabraEscrita) {
        this.palabraCorrecta = palabraCorrecta;
        this.dificultad = dificultad;
        this.palabraEscrita = palabraEscrita;
    }

    public String getPalabraCorrecta() {
        return palabraCorrecta;
    }

    public void setPalabraCorrecta(String palabraCorrecta) {
        this.palabraCorrecta = palabraCorrecta;
    }

    public int getDificultad() {
        return dificultad;
    }

    public void setDificultad(int dificultad) {
        this.dificultad = dificultad;
    }

    public String getPalabraEscrita() {
        return palabraEscrita;
    }

    public void setPalabraEscrita(String palabraEscrita) {
        this.palabraEscrita = palabraEscrita;
    }
}
