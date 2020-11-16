package hr.fer.zpm.model;

import java.util.*;

public class Termin {
    private final int MAKSIMALNO_STUDENATA = 750;
    private List<Predmet> mojiPredmeti = new ArrayList<Predmet>();
    private List<Student> studentiMogTermina = new ArrayList<Student>();
    private int id;

    public Termin(int id) {
        super();
        this.id = id;
    }

    public List<Predmet> getMojiPredmeti() {
        return mojiPredmeti;
    }

    public void setMojiPredmeti(List<Predmet> mojiPredmeti) {
        this.mojiPredmeti = mojiPredmeti;
    }

    public void dodajPredmet(Predmet p, Map<Predmet, List<Student>> studentiPoPredmetima) throws Exception {
        if (mojiPredmeti.contains(p)) {
            return;
        }
        mojiPredmeti.add(p);
        postaviStudenteMogTermina(studentiPoPredmetima);

    }

    public List<Student> getStudentiMogTermina(Map<Predmet, List<Student>> studentiPoPredmetima) {
        if (studentiMogTermina.size() != 0) {
            return studentiMogTermina;
        }
        Set<Student> setStudenata = new HashSet<>();
        studentiMogTermina = new ArrayList<Student>();
        for (Predmet p : mojiPredmeti) {
            List<Student> studenti = studentiPoPredmetima.get(p);
            for (Student s : studenti) {
                setStudenata.add(s);
            }

        }
        studentiMogTermina.clear();
        studentiMogTermina.addAll(setStudenata);
        return studentiMogTermina;
    }

    public void postaviStudenteMogTermina(Map<Predmet, List<Student>> studentiPoPredmetima) throws Exception {
        Set<Student> setStudenata = new HashSet<>();

        for (Predmet p : mojiPredmeti) {
            List<Student> studenti = studentiPoPredmetima.get(p);
            for (Student s : studenti) {
                setStudenata.add(s);
            }

        }
//		List<Student> testLista=new ArrayList<Student>();
//		for (Predmet p : mojiPredmeti) {
//			List<Student> studenti = studentiPoPredmetima.get(p);
//			for (Student s : studenti) {
//				if(testLista.contains(s)) {
//					System.out.println("U TERMINU " + id+ " ISTI STUDENT U LISTI VEC KAO I OVAJ "+ s  );
//					throw new Exception();
//					//break;
//				}else {
//					testLista.add(s);
//				}
//				
//			}
//
//		}
        studentiMogTermina.clear();
        studentiMogTermina.addAll(setStudenata);

    }

//	@Override
//	public String toString() {
//		return "\n u terminu " + id + " nalaze se predmeti " + mojiPredmeti.toString() + "\n";
//	}

    @Override
    public String toString() {
        return "\n U terminu s ID-jem:" + id + ", nalaze se predmeti:\n " + mojiPredmeti.toString() + " , broj studenta mog termina:" + studentiMogTermina.size() + "\n";
    }

    public List<Student> getStudentiMogTermina() {
        return studentiMogTermina;
    }

    public void setStudentiMogTermina(List<Student> studentiMogTermina) {
        this.studentiMogTermina = studentiMogTermina;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean probajDodatPredmet(Predmet predmet, Map<Predmet, List<Student>> studentiPoPredmetima)
            throws Exception {
        // ako je kapacitet veci od 750

        List<Student> studenti = new ArrayList<Student>();
        List<Predmet> privremeniPredmeti = new ArrayList<>();

        privremeniPredmeti.addAll(mojiPredmeti);
        privremeniPredmeti.add(predmet);
        for (Predmet p : privremeniPredmeti) {

            List<Student> stud = studentiPoPredmetima.get(p);
            for (Student s : stud) {
                if (studenti.contains(s)) {

                    for (Predmet p1 : privremeniPredmeti) {
                        if (!p1.equals(p) && studentiPoPredmetima.get(p1).contains(s)) {
                            for (Student s1 : studentiPoPredmetima.get(p1)) {
                                if (s.equals(s1)) {
                                    throw new Exception("student" + s + "nalazi se u predmetu" + p1
                                            + " a nasli smo ga i u predmetu" + p);
                                }
                            }

                        }
                    }

                }
                studenti.add(s);
            }

        }
        // System.out.println("velicina seta studenata kod probaj dodat predmet je"+
        // setStudenata.size() + "za termin "+id);
		return studenti.size() <= MAKSIMALNO_STUDENATA;
	}

    public void makniPredmet(Predmet predmet, Map<Predmet, List<Student>> upisanoStudenata) throws Exception {
        mojiPredmeti.remove(predmet);
        postaviStudenteMogTermina(upisanoStudenata);

    }

    /**
     * Metoda provjerava sadrzi li termin predmet godine koja je predana kao
     * parametar, ali takav da predmet nije transverzalnog tipa
     *
     * @param godina
     * @return
     */
    public boolean sadrziPredmetGodineNeTransverzalni(GodinaPremdeta... godine) {
        for (Predmet p : mojiPredmeti) {
            for (GodinaPremdeta godinaPredmeta : godine) {
                if (p.getVrstaPredmeta().equals(VrstaPredmeta.TRANSVERZALNI_PREDMET)) {
                    continue;
                }
                if (p.getGodinaPredmeta().equals(godinaPredmeta)) {
                    return true;
                }
            }

        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
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
        Termin other = (Termin) obj;
		return id == other.id;
	}

    public boolean sadrziPredmetGodine(GodinaPremdeta[] godine) {
        for (Predmet p : mojiPredmeti) {
            for (GodinaPremdeta godinaPredmeta : godine) {
                if (p.getGodinaPredmeta().equals(godinaPredmeta)) {
                    return true;
                }
            }

        }
        return false;
    }

    public boolean sadrziPredmeteSviTransverzalni() {
        for (Predmet p : mojiPredmeti) {

            if (!p.getVrstaPredmeta().equals(VrstaPredmeta.TRANSVERZALNI_PREDMET)) {
                return false;
            }


        }
        return true;
    }


}
