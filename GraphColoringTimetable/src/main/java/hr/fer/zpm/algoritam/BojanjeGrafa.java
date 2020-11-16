package hr.fer.zpm.algoritam;

import hr.fer.zpm.model.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BojanjeGrafa {
    private static final String[] COLOR_VALUES = new String[]{"FF0000", "00FF00", "0000FF", "FFFF00", "FF00FF",
            "00FFFF", "000000", "800000", "008000", "000080", "808000", "800080", "008080", "808080", "C00000",
            "00C000", "0000C0", "C0C000", "C000C0", "00C0C0", "C0C0C0", "400000", "004000", "000040", "404000",
            "400040", "004040", "404040", "200000", "002000", "000020", "202000", "200020", "002020", "202020",
            "600000", "006000", "000060", "606000", "600060", "006060", "606060", "A00000", "00A000", "0000A0",
            "A0A000", "A000A0", "00A0A0", "A0A0A0", "E00000", "00E000", "0000E0", "E0E000", "E000E0", "00E0E0",
            "E0E0E0",};
    /**
     * svi termini
     */
    private List<Termin> sviTermini = new ArrayList<Termin>();
    /**
     *
     */
    private int maxColor;
    private final int brojVrhova;
    private int[] color;
    private final List<Predmet> sviPredmeti;
    private final List<Student> sviStudenti;
    private final Map<Predmet, List<Student>> studentiPoPredmetima;
    private Map<Predmet, Integer> predmetIDMap;
    private List<Čvor<Predmet>> čvorovi;
    private final List<Brid<Predmet>> bridovi;
    private final int brojTermina;
    private final int[][] graf;


    public BojanjeGrafa(List<Termin> sviTermini, int v, List<Predmet> sviPredmeti, List<Student> sviStudenti,
                        Map<Predmet, List<Student>> studentiPoPredmetima, int brojTermina) {
        super();
        this.sviTermini = sviTermini;
        brojVrhova = v;
        this.sviPredmeti = sviPredmeti;
        this.sviStudenti = sviStudenti;
        this.studentiPoPredmetima = studentiPoPredmetima;
        this.brojTermina = brojTermina;
        this.predmetIDMap = new HashMap<>();
        this.čvorovi = new ArrayList<>();
        this.bridovi = new ArrayList<>();

        graf = new int[brojVrhova][brojVrhova];
        for (int i = 0; i < brojVrhova; i++) {
            for (int j = 0; j < brojVrhova; j++) {
                graf[i][j] = 0;
            }
        }
        stvoriUniqueIdZaPredmete();

    }

    public static Color hex2Rgb(String colorStr) {
        return new Color(Integer.valueOf(colorStr.substring(0, 2), 16), Integer.valueOf(colorStr.substring(2, 4), 16),
                Integer.valueOf(colorStr.substring(4, 6), 16));
    }

    private void stvoriUniqueIdZaPredmete() {
        int uniqueID = 0;
        čvorovi = new ArrayList<>();
        predmetIDMap = new HashMap<>();
        for (Predmet p : sviPredmeti) {
            if (p.isImaIspit()) {
                čvorovi.add(new Čvor<Predmet>(p, uniqueID));
                predmetIDMap.put(p, uniqueID);
                uniqueID++;
            }

        }
    }

    private boolean jeLiSiguran(int trenutniVrh, int c) throws Exception {
        for (int i = 0; i < brojVrhova; i++) {
            if (graf[trenutniVrh][i] == 1 && c == color[i])
                return false;

        }


        try {
            if (!sviTermini.get(c - 1).probajDodatPredmet(sviPredmeti.get(trenutniVrh), studentiPoPredmetima)) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }

        try {
            sviTermini.get(c - 1).dodajPredmet(sviPredmeti.get(trenutniVrh), studentiPoPredmetima);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
        return true;
    }

    private boolean bojanjeGrafaPomocnaMetoda(int trenutniVrh) throws Exception {
        if (trenutniVrh == brojVrhova)
            return true;

        for (int c = 1; c <= brojTermina; c++) {
            // mozemo li dodijeliti boju c, ako mozemo onda odma dodamo studente u termin
            // jer se oni dalje gledaju s backtrackingom
            if (jeLiSiguran(trenutniVrh, c)) {
                color[trenutniVrh] = c;

                /*
                 * ako je oke dodijeli boje dalje
                 */
                if (bojanjeGrafaPomocnaMetoda(trenutniVrh + 1)) {
                    return true;
                }

                /*
                 * Ako boja ne vodi do rjesenja idemo po novu boju, i vracamo studente koje smo
                 * dodali
                 */
                sviTermini.get(color[trenutniVrh] - 1).makniPredmet(sviPredmeti.get(trenutniVrh), studentiPoPredmetima);
                color[trenutniVrh] = 0;
            }
        }

        /*
         * Ako se ne moze postaviti ni jedna boja vrati false
         */
        return false;
    }

    /*
     * Trazimo rjesenje backtrackingom. Postoji mogućnost da ima više
     * zadovoljavajućih rješenja
     */
    private boolean bojanjeGrafa() throws Exception {

        color = new int[brojVrhova];
        // Postavimo boje na 0
        for (int i = 0; i < brojVrhova; i++) {
            color[i] = 0;
        }

        if (!bojanjeGrafaPomocnaMetoda(0)) {
            System.out.println("Ne postoji rješenje");
            return false;
        }

        pripremiRjesenje();
        System.out.println("Rješenje postoji.");
        return true;
    }

    private void pripremiRjesenje() throws Exception {

        maxColor = 0;
        for (int i = 0; i < brojVrhova; i++) {

            int brojTermina = color[i] - 1;
            Predmet predmet = sviPredmeti.get(i);
            Čvor<Predmet> cvor = čvorovi.get(i);

            sviTermini.get(brojTermina).dodajPredmet(predmet, studentiPoPredmetima);
            predmet.setBoja(hex2Rgb(COLOR_VALUES[brojTermina]));
            cvor.setBoja(predmet.getBoja());
            maxColor = Math.max(maxColor, color[i]);
        }
        int bridoviID = 0;
        for (int i = 0; i < brojVrhova; i++) {
            for (int j = i + 1; j < brojVrhova; j++) {
                if (graf[i][j] == 1) {
                    bridovi.add(new Brid<Predmet>(čvorovi.get(i), čvorovi.get(j), bridoviID++));
                }

            }
        }

        for (int i = 0; i < sviTermini.size(); i++) {

            List<Predmet> predmetiTermina = sviTermini.get(i).getMojiPredmeti();

            for (Student s : sviStudenti) {
                int brojPredmetaUpisanihKojiSuUTerminu = 0;
                for (Predmet p : predmetiTermina) {
                    if (!s.getUpisaniPredmeti().contains(p)) {
                        brojPredmetaUpisanihKojiSuUTerminu++;
                        break;
                    }
                }
                if (brojPredmetaUpisanihKojiSuUTerminu > 1) {
                    System.out.println("slijedi student kojeg smo zeznuli");
                    System.out.println(s);
                    System.out.println(predmetiTermina);
                }
            }

        }
    }

    public void pocni(StringBuilder sb) throws Exception {

        for (int i = 0; i < sviStudenti.size(); i++) {
            List<Predmet> predmeti = sviStudenti.get(i).getUpisaniPredmeti();
            for (int j = 0; j < predmeti.size(); j++) {
                for (int k = 0; k < predmeti.size(); k++) {
                    Predmet p1 = predmeti.get(j);
                    Predmet p2 = predmeti.get(k);
                    if (!predmetIDMap.containsKey(p1) || !predmetIDMap.containsKey(p2)) {
                        continue;
                    }
                    int i1 = predmetIDMap.get(p1);
                    int i2 = predmetIDMap.get(p2);
                    if (i1 == i2) {
                        continue;
                    }
                    graf[i1][i2] = 1;
                    graf[i2][i1] = 1;
                }
            }
        }
        bojanjeGrafa();

    }

    public int getMaxColor() {
        return maxColor;
    }

    public int[][] getGraf() {
        return graf;
    }

    public List<Čvor<Predmet>> getČvorovi() {
        return čvorovi;
    }

    public List<Brid<Predmet>> getBridovi() {
        return bridovi;
    }

}
