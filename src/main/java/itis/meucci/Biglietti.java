package itis.meucci;

public class Biglietti {
    private int identificativo;
    private String posto;

    public Biglietti( String posto) {
        identificativo = identificativo + 1;
        this.posto = posto;
    }

    public int getIdentificativo() {
        return identificativo;
    }

    public void setIdentificativo(int id) {
        identificativo = id;
    }

    public String getPosto() {
        return posto;
    }

    public void setPosto(String p) {
        posto = p;
    }
}
