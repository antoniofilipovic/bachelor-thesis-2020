package hr.fer.zpm.generator;

import hr.fer.zpm.model.Predmet;
import hr.fer.zpm.model.Student;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Generator {
    private static final int BROJ_STUDENATA_PRVE_GODINE = 500;
    private static final int BROJ_STUDENATA_DRUGE_GODINE = 500;
    private static final int BROJ_STUDENATA_TRECE_GODINE = 350;
    private static final Map<Predmet, List<Student>> studentiPoPredmetima = new LinkedHashMap<Predmet, List<Student>>();
    private List<Student> sviStudenti;
    private int brojPredmeta;
    private List<Predmet> sviPredmeti;

    /**
     * Ova metoda sluzi za generiranje studenata, generiranje predmeta i generiranje studenata po predmetima
     *
     * @throws Exception
     */
    public void generirajSve() throws Exception {
        //Generiranje studenata i dohvacanje
        GeneratorStudenata studentGenerator = new GeneratorStudenata(BROJ_STUDENATA_PRVE_GODINE,
                BROJ_STUDENATA_DRUGE_GODINE, BROJ_STUDENATA_TRECE_GODINE);
        studentGenerator.generirajStudente();
        List<Student> studenti = studentGenerator.getStudenti();

        //generiranje predmeta i dohvacanje predmeta
        GeneratorPredmeta generatorPredmeta = new GeneratorPredmeta();
        generatorPredmeta.stvoriPredmetePrvogSemestra();
        generatorPredmeta.stvoriPredmeteTrecegSemestra();
        generatorPredmeta.stvoriPredmetePetogSemestra();

        generatorPredmeta.getSviPredmeti().forEach(p -> studentiPoPredmetima.put(p, new ArrayList<>()));

        //svi predmeti su samo oni s ispitima
        sviPredmeti = generatorPredmeta.getPredmetiSIspitom();
        brojPredmeta = sviPredmeti.size();


        GeneratorPredmetaStudentima generatorPredmetaStudentima = new GeneratorPredmetaStudentima();

        generatorPredmetaStudentima.dodijeliStudentimaPrvogSemestraPredmete(studenti,
                generatorPredmeta.getRegularniPredmetiPrvogSemestra(),
                generatorPredmeta.getTransverzalniPredmetiPrvogSemestra());


        generatorPredmetaStudentima.dodijeliStudentimaTrecegSemestraPredmete(studenti,
                generatorPredmeta.getRegularniPredmetiTrecegSemestra(),
                generatorPredmeta.getTransverzalniPredmetiTrecegSemestra(),
                generatorPredmeta.getRegularniPredmetiPrvogSemestra(),
                generatorPredmeta.getTransverzalniPredmetiPrvogSemestra());


        generatorPredmetaStudentima.dodijeliStudentimaPetogSemestraPredmete(studenti,
                generatorPredmeta.getRegularniPredmetiPetogSemestra(),
                generatorPredmeta.getTransverzalniPredmetiPetogSemestra(),
                generatorPredmeta.getIzborniPredmetiPetogSemestra(),
                generatorPredmeta.getRegularniPredmetiTrecegSemestra(),
                generatorPredmeta.getTransverzalniPredmetiTrecegSemestra());


        sviStudenti = studenti;
        for (Student s : sviStudenti) {
            List<Predmet> mojiPredmeti = s.getUpisaniPredmeti();
            for (Predmet p : mojiPredmeti) {

                studentiPoPredmetima.get(p).add(s);
            }

        }
    }


    public Map<Predmet, List<Student>> getStudentiPoPredmetima() {
        return studentiPoPredmetima;
    }


    public List<Student> getSviStudenti() {
        return sviStudenti;
    }

    public int getBrojPredmeta() {
        return brojPredmeta;
    }


    public List<Predmet> getSviPredmeti() {
        return sviPredmeti;
    }


}
