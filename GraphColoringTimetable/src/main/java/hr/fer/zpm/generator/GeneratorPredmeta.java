package hr.fer.zpm.generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zpm.model.GodinaPremdeta;
import hr.fer.zpm.model.Predmet;
import hr.fer.zpm.model.VrstaPredmeta;

public class GeneratorPredmeta {
	private List<Predmet> sviPredmeti = new ArrayList<>();

	private List<Predmet> regularniPredmetiPrvogSemestra = new ArrayList<>();
	private List<Predmet> transverzalniPredmetiPrvogSemestra = new ArrayList<>();

	private List<Predmet> regularniPredmetiTrecegSemestra = new ArrayList<>();
	private List<Predmet> transverzalniPredmetiTrecegSemestra = new ArrayList<>();

	private List<Predmet> regularniPredmetiPetogSemestra = new ArrayList<>();
	private List<Predmet> transverzalniPredmetiPetogSemestra = new ArrayList<>();
	private List<Predmet> izborniPredmetiPetogSemestra = new ArrayList<>();
	
	private List<Predmet> predmetiSIspitom;

	private int GLOBALNI_ID = 0;

	public List<Predmet> getSviPredmeti() {
		return sviPredmeti;
	}

	public int brojPredmeta() {
		return GLOBALNI_ID;
	}

	public void stvoriPredmetePrvogSemestra() {
		List<String> predmeti = null;
		try {
			predmeti = Files.readAllLines(Paths.get("predmeti_prvi_semestar.txt"));
		} catch (IOException e) {
		}
		boolean transverzalniPoceli = false;
		for (String line : predmeti) {
			if (line.equals("Transverzalni predmeti")) {
				transverzalniPoceli = true;
				continue;
			}
			if (!transverzalniPoceli) {
				dodajPredmet(regularniPredmetiPrvogSemestra, line, GodinaPremdeta.PRVA_GODINA,VrstaPredmeta.REGULARNI_PREDMET);
			} else {
				dodajPredmet(transverzalniPredmetiPrvogSemestra, line, GodinaPremdeta.PRVA_GODINA,VrstaPredmeta.TRANSVERZALNI_PREDMET);
			}
		}

	}

	public void stvoriPredmeteTrecegSemestra() {

		List<String> predmeti = null;
		try {
			predmeti = Files.readAllLines(Paths.get("predmeti_treci_semestar.txt"));
		} catch (IOException e) {
		}
		boolean transverzalniPoceli = false;
		for (String line : predmeti) {
			if (line.equals("Transverzalni predmeti")) {
				transverzalniPoceli = true;
				continue;
			}
			if (!transverzalniPoceli) {
				dodajPredmet(regularniPredmetiTrecegSemestra, line, GodinaPremdeta.DRUGA_GODINA,VrstaPredmeta.REGULARNI_PREDMET);
			} else {
				dodajPredmet(transverzalniPredmetiTrecegSemestra, line, GodinaPremdeta.DRUGA_GODINA,VrstaPredmeta.TRANSVERZALNI_PREDMET);
			}
		}

	}

	public void stvoriPredmetePetogSemestra() {

		List<String> predmeti = null;
		try {
			predmeti = Files.readAllLines(Paths.get("predmeti_peti_semestar.txt"));
		} catch (IOException e) {
		}
		boolean transverzalniPoceli = false;
		boolean izborniPoceli = false;
		for (String line : predmeti) {
			if (line.startsWith("#")) {
				continue;
			}
			if (line.equals("Transverzalni predmeti")) {
				transverzalniPoceli = true;
				izborniPoceli = false;
				continue;
			}
			if (line.equals("Izborni predmeti")) {
				transverzalniPoceli = false;
				izborniPoceli = true;
				continue;
			}
			// System.out.println(line);
			if (!transverzalniPoceli && !izborniPoceli) {
				dodajPredmet(regularniPredmetiPetogSemestra, line, GodinaPremdeta.TRECA_GODINA,VrstaPredmeta.REGULARNI_PREDMET);
			} else if (transverzalniPoceli && !izborniPoceli) {
				dodajPredmet(transverzalniPredmetiPetogSemestra, line, GodinaPremdeta.TRECA_GODINA,VrstaPredmeta.TRANSVERZALNI_PREDMET);
			} else {
				dodajPredmet(izborniPredmetiPetogSemestra, line, GodinaPremdeta.TRECA_GODINA,VrstaPredmeta.IZBORNI_PREDMET);
			}
		}

	}

	/**
	 * Ova metoda sluzi za dodavanje predmeta u listu predmeta
	 * 
	 * @param listaPredmeta
	 * @param line
	 * @param upisanoStudenata
	 */

	private void dodajPredmet(List<Predmet> listaPredmeta, String line, GodinaPremdeta godinaPremdeta,
			VrstaPredmeta vrstaPredmeta) {
		String[] parts = line.split(";");
		Predmet p = new Predmet(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), GLOBALNI_ID++,
				Integer.parseInt(parts[3]) == 1, godinaPremdeta,vrstaPredmeta);

		if (sviPredmeti.contains(p)) {
			p = sviPredmeti.get(sviPredmeti.indexOf(p));
			GLOBALNI_ID--;

			listaPredmeta.add(p);

		}

		else {
			sviPredmeti.add(p);
			listaPredmeta.add(p);
		}
		// upisanoStudenata.put(p,new ArrayList<Student>());

	}

	public List<Predmet> getRegularniPredmetiPrvogSemestra() {
		return regularniPredmetiPrvogSemestra;
	}

	public List<Predmet> getTransverzalniPredmetiPrvogSemestra() {
		return transverzalniPredmetiPrvogSemestra;
	}

	public List<Predmet> getRegularniPredmetiTrecegSemestra() {
		return regularniPredmetiTrecegSemestra;
	}

	public List<Predmet> getTransverzalniPredmetiTrecegSemestra() {
		return transverzalniPredmetiTrecegSemestra;
	}

	public List<Predmet> getRegularniPredmetiPetogSemestra() {
		return regularniPredmetiPetogSemestra;
	}

	public List<Predmet> getTransverzalniPredmetiPetogSemestra() {
		return transverzalniPredmetiPetogSemestra;
	}

	public List<Predmet> getIzborniPredmetiPetogSemestra() {
		return izborniPredmetiPetogSemestra;
	}

	public List<Predmet> getPredmetiSIspitom() {
		if (predmetiSIspitom != null) {
			return predmetiSIspitom;
		}
		predmetiSIspitom = new ArrayList<>();
		for (Predmet p:sviPredmeti) {
			if(p.isImaIspit()) {
				predmetiSIspitom.add(p);
			}
		}
		return predmetiSIspitom;
	}

}
