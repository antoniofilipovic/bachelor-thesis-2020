package hr.fer.zpm.model;

import java.util.ArrayList;
import java.util.List;

public class Student implements Comparable<Student> {
    private String ime;
    private String prezime;
    private String jmbag;
    private int upisanoEcts;
    private final List<Predmet> upisaniPredmeti = new ArrayList<Predmet>();
    private final int godina;

    public Student(String ime, String prezime, String jmbag, int godina) {
        this.ime = ime;
        this.prezime = prezime;
        this.jmbag = jmbag;
        this.godina = godina;
        this.upisanoEcts = 0;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }


    public int getUpisanoEcts() {
        return upisanoEcts;
    }


    public List<Predmet> getUpisaniPredmeti() {
        return upisaniPredmeti;
    }

    public void dodajUpisanePredmete(List<Predmet> upisaniPredmeti) throws Exception {

        for (Predmet p : upisaniPredmeti) {
            dodajUpisanPredmet(p);
        }
    }

    public int getGodina() {
        return godina;
    }

    public boolean dodajUpisanPredmet(Predmet p) throws Exception {
        if (upisanoEcts + p.getEcts() > 38) {
            return false;
        }
        if (this.upisaniPredmeti.contains(p)) {
            throw new Exception();

        }

        this.upisanoEcts += p.getEcts();
        this.upisaniPredmeti.add(p);
        return true;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Student other = (Student) obj;
        if (jmbag == null) {
			return other.jmbag == null;
        } else return jmbag.equals(other.jmbag);
	}

    @Override
    public String toString() {
        return ime + " " + prezime + " " + jmbag + " " + godina + " " + upisanoEcts + "\n Slijede upisani predmeti\n" + upisaniPredmeti.toString() + "\n\n";
    }

//	@Override
//	public String toString() {
//		return ime + " " + prezime + " " + jmbag + " " + godina + " "+upisanoEcts+ "\n";
//	}

    @Override
    public int compareTo(Student o) {
        return this.jmbag.compareTo(o.jmbag);
    }

}
