package hr.fer.zpm.model;

public class Brid<T> {
    private Čvor<T> prviČvor;
    private Čvor<T> drugiČvor;
    private int ID;

    public Brid(Čvor<T> čvor, Čvor<T> čvor2, int ID) {
        this.prviČvor = čvor;
        this.drugiČvor = čvor2;
        this.ID = ID;
    }

    public Čvor<T> getPrviČvor() {
        return prviČvor;
    }


    public Čvor<T> getDrugiČvor() {
        return drugiČvor;
    }


    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        ID = iD;
    }

}
