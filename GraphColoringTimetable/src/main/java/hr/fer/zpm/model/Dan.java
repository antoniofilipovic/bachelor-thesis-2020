package hr.fer.zpm.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Klasa dan sastoji se od termina u danu i sadrzi odredena ogranicenja. Jedno
 * ogranicenje je broj termina koje moze imati po danu, a taj broj zadan je pri
 * konstrukciji objekta i nije ga moguce mijenjati dalje
 *
 * @author filip
 */
public class Dan implements Comparable<Dan> {
    private List<Termin> terminiUDanu = new ArrayList<Termin>();
    private int brojTerminaTrenutni;
    private int brojStudentataUPresjeku;
    private int id;
    private final int zadaniBrojTerminaUDanu;

    public Dan(int id, int zadaniBrojTerminaUDanu) {
        this.id = id;
        this.zadaniBrojTerminaUDanu = zadaniBrojTerminaUDanu;
    }

    public int izracunajPresjekZaTerminUDanu(Termin termin) {
        Map<Student, Integer> brojPojavljivanjaStudenta = new HashMap<Student, Integer>();

        List<Termin> kopijaTerminaSNovim = new ArrayList<Termin>();
        kopijaTerminaSNovim.addAll(terminiUDanu);
        kopijaTerminaSNovim.add(termin);

        for (Termin t : kopijaTerminaSNovim) {
            List<Student> studenti = t.getStudentiMogTermina(null);
            for (Student s : studenti) {
                brojPojavljivanjaStudenta.merge(s, 1, Integer::sum);
            }
        }
        int presjek = 0;

        List<Integer> values = new ArrayList<Integer>(brojPojavljivanjaStudenta.values());
        for (int i : values) {
            if (i > 1) {
                presjek++;
            }
        }
        return presjek;
    }

    public void dodajTermin(Termin termin) {

        Map<Student, Integer> brojPojavljivanjaStudenta = new HashMap<Student, Integer>();

        terminiUDanu.add(termin);

        for (Termin t : terminiUDanu) {
            List<Student> studenti = t.getStudentiMogTermina(null);// vec je odredeno
            for (Student s : studenti) {
                brojPojavljivanjaStudenta.merge(s, 1, Integer::sum);
            }
        }
        int presjek = 0;

        List<Integer> values = new ArrayList<Integer>(brojPojavljivanjaStudenta.values());
        for (int i : values) {
            if (i > 1) {
                presjek++;
            }
        }
        brojStudentataUPresjeku = presjek;

    }

    public int moguceDodat() {
        return zadaniBrojTerminaUDanu - terminiUDanu.size();
    }

    public boolean sadrziTermin(Termin t2) {
        return terminiUDanu.contains(t2);
    }

    public List<Termin> getTerminiUDanu() {
        return terminiUDanu;
    }


    public int getBrojStudentataUPresjeku() {
        return brojStudentataUPresjeku;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "REDNI BROJ DANA:" + (id + 1) + " ,\nBROJ STUDENATA S VIŠE OD JEDNIM ISPITOM:" + brojStudentataUPresjeku
                + "\n TERMINI U DANU: " + terminiUDanu.toString() + "\n\n\n";
    }

    @Override
    public int compareTo(Dan o) {
        return Integer.compare(this.id, o.id);
    }


    public boolean sadrziTerminSPredmetomNaGodini(GodinaPremdeta... godine) {

        for (Termin termin : terminiUDanu) {
            if (termin.sadrziPredmetGodine(godine)) {
                return true;
            }
        }

        return false;
    }

    public boolean sadrziTerminTransverzalni() {

        for (Termin termin : terminiUDanu) {
            if (termin.sadrziPredmeteSviTransverzalni()) {
                return true;
            }
        }

        return false;
    }

}
