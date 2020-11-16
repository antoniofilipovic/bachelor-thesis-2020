package hr.fer.zpm.model;

import java.awt.*;

public class Čvor<T> {
    private final T struktura;
    private Color boja;
    private final int ID;

    public Čvor(T p, int uniqueID) {
        struktura = p;
        ID = uniqueID;
    }

    public String getIme() {
        return struktura.toString();
    }

    public Color getBoja() {
        return boja;
    }

    public void setBoja(Color boja) {
        this.boja = boja;
    }

    public T getStruktura() {
        return struktura;
    }


}
