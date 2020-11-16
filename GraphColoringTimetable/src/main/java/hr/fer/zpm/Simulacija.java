package hr.fer.zpm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import hr.fer.zpm.algoritam.BojanjeGrafa;
import hr.fer.zpm.model.Brid;
import hr.fer.zpm.model.Dan;
import hr.fer.zpm.model.GodinaPremdeta;
import hr.fer.zpm.model.NacinRada;
import hr.fer.zpm.model.Predmet;
import hr.fer.zpm.model.Student;
import hr.fer.zpm.model.Termin;
import hr.fer.zpm.model.Čvor;
import hr.fer.zpm.generator.Generator;

public class Simulacija {
	private int brojTerminaPoDanu;
	private int brojDana;
	private List<Termin> sviTermini;
	private List<Predmet> sviPredmeti;
	private Map<Predmet, List<Student>> studentiPoPredmetima;
	private List<Student> sviStudenti;
	private static int maxColor;
	private List<Dan> daniPopunjeniTerminima = new ArrayList<Dan>();
	private Generator generator;
	private BojanjeGrafa bojanjeGrafa;
	private boolean generirano = false;
	public Simulacija() {
		super();
		this.generator = new Generator();

	}

	public void pripremiNovuSimulaciju() throws Exception {
		if(!generirano) {
			generator = new Generator();
			generator.generirajSve();
			generirano=true;
		}
		
		sviTermini = new ArrayList<Termin>();
		for (int i = 0; i < brojDana * brojTerminaPoDanu; i++) {
			sviTermini.add(new Termin(i));
		}
		
	}

	public String pocniSimulaciju(NacinRada nacinRada) throws Exception {
		StringBuilder stringBuilder = new StringBuilder();
		int brojTermina = brojDana * brojTerminaPoDanu;
	
		int brojPredmeta = generator.getBrojPredmeta();
		

		sviPredmeti = generator.getSviPredmeti();
		sviStudenti = generator.getSviStudenti();
		studentiPoPredmetima = generator.getStudentiPoPredmetima();

		// provjera moze li se uopce napraviti raspored
		if (Math.round(brojPredmeta * 1.0 / brojTerminaPoDanu) > brojDana) {
			throw new RuntimeException("Broj potrebnih dana je " + Math.round(brojPredmeta * 1.0 / brojTerminaPoDanu)
					+ ", a broj dostupnih dana je:" + brojDana);
		}

		bojanjeGrafa = new BojanjeGrafa(sviTermini, brojPredmeta, sviPredmeti, sviStudenti, studentiPoPredmetima,
				brojTermina);

		bojanjeGrafa.pocni(stringBuilder);
		maxColor = bojanjeGrafa.getMaxColor();

		Iterator<Termin> iter = sviTermini.iterator();

		while (iter.hasNext()) {
			Termin t = iter.next();
			if (t.getMojiPredmeti().size() == 0) {
				iter.remove();
			}
		}

		int brojPotrebnihDana = Math.floorDiv(maxColor, brojTerminaPoDanu) + 1;
		
		stringBuilder.append("Broj predmeta je:"+ brojPredmeta+"\n");
		stringBuilder.append("Kromatski broj (broj potrebnih termina): " + maxColor + "\n");
		//stringBuilder.append("Broj potrebnih dana:" + brojPotrebnihDana + "\n");
		stringBuilder.append("------------------------------------------------------\n");
		if (brojPotrebnihDana > brojDana) {
			throw new RuntimeException(
					"Nije moguće rasporedit sve termine jer je broj potrebnih dana veci od broja dostupnih dana.");
		}

		switch (nacinRada) {
		case MIN_METODA:
			sloziTerminePoDanima(stringBuilder);
			break;
		case PRIRODNO_RASPOREDJIVANJE:
			sloziRucnoTerminePoDanima(stringBuilder);
			break;
		default:
			throw new RuntimeException("Pogresan nacin simulacije");
		}

		//Collections.sort(daniPopunjeniTerminima);

		//stringBuilder.append(daniPopunjeniTerminima.toString());
			
		stringBuilder.append(sviTermini.toString());
		return stringBuilder.toString();

	}
	
	private void formatirajIspis(StringBuilder stringBuilder, List<Dan> sviDani) {
		stringBuilder.append("Slijedi redni broj dana i broj studenata koji tog dana pišu više od jednog ispita:\n");
		stringBuilder.append("| Redni broj dana || Broj termina u danu || Broj studenata u presjeku \n");
		for (Dan d: sviDani) {
			String danId = String.valueOf(d.getId());
			int duljinaStringaDanID= danId.length();
			int brojRazmakaDanId=(27-duljinaStringaDanID)/2;
			
			String brojStudenataUPresjeku = String.valueOf(d.getBrojStudentataUPresjeku());
			
			int duljinaBrojUPresjeku= brojStudenataUPresjeku.length();
			
			int brojRazmakaUPresjeku=(34-duljinaBrojUPresjeku)/2;
			
			
			String brojTermina = String.valueOf(d.getTerminiUDanu().size());
			int duljinaBrojTermina= brojTermina.length();
			int brojRazmakaTermini=(34-duljinaBrojTermina)/2;
			
			int added=0;
			if(duljinaBrojTermina==1) {
				added=2;
			}
			
			stringBuilder.append("|" +" ".repeat(brojRazmakaDanId)+danId +" ".repeat(brojRazmakaDanId)+ "|"
									+"|" +" ".repeat(brojRazmakaTermini) + brojTermina +" ".repeat(brojRazmakaTermini+added)+"|"
									+"|" +" ".repeat(brojRazmakaUPresjeku) + brojStudenataUPresjeku +" ".repeat(brojRazmakaUPresjeku) +"\n");
			
		}
		stringBuilder.append("\n\n");
		
		stringBuilder.append("Slijedi redni broj dana i predmeti u danu:\n\n");
		
		Map<GodinaPremdeta, String> mapaIspisa = new HashMap<GodinaPremdeta, String>();
		mapaIspisa.put(GodinaPremdeta.PRVA_GODINA, "Prva godina");
		mapaIspisa.put(GodinaPremdeta.DRUGA_GODINA, "Druga godina");
		mapaIspisa.put(GodinaPremdeta.TRECA_GODINA, "Treća godina");
		mapaIspisa.put(GodinaPremdeta.VIŠE_GODINA, "Više godina");
		
		
		for (Dan d: sviDani) {
			String danId = String.valueOf(d.getId());
			stringBuilder.append("| Redni broj dana: " +danId+" | Broj termina u danu:"+d.getTerminiUDanu().size()+"\n");
			
			
			List<Termin> terminiDana = d.getTerminiUDanu();
			
			
			StringBuilder sb = new StringBuilder();
			
			terminiDana.forEach(t-> {
				sb.append("Termin ID: "+t.getId()+" -> ");
				List<Predmet> predmetiTmp = t.getMojiPredmeti();
				predmetiTmp.forEach(p-> sb.append("[ "+p.getIme()+", "+mapaIspisa.get(p.getGodinaPredmeta())+" ] "));
				sb.setLength(sb.length()-1);
				sb.append("\n");
				
			});
			stringBuilder.append(sb.toString());
			stringBuilder.append("-------------------------------------------------------------\n");
		}
	}

	private void sloziRucnoTerminePoDanima(StringBuilder stringBuilder) {
		// System.out.println("simulacija pocela");

		List<Dan> sviDani = new ArrayList<>();
		for (int i = 0; i < brojDana; i++) {
			sviDani.add(new Dan(i, brojTerminaPoDanu));
		}

		List<Termin> neDodaniTermini = new ArrayList<Termin>();

		List<Termin> dodaniTermini = new ArrayList<Termin>();

		// pokusaj odvajanja termina s transverzalnim cistim u jedan dan
		for (int i = 0; i < sviTermini.size(); i++) {
			Termin t = sviTermini.get(i);
			if (dodaniTermini.contains(t)) {
				continue;
			}
			if (!t.sadrziPredmeteSviTransverzalni()) {
				continue;

			}

			neDodaniTermini.add(t);

		}
		int indeks = 0;
		while (neDodaniTermini.size() > 0) {
			Dan d = sviDani.get(indeks);
			int moguceDodati = brojTerminaPoDanu;
			while (moguceDodati > 0 && neDodaniTermini.size() > 0) {
				Termin t = neDodaniTermini.remove(0);
				d.dodajTermin(t);
				moguceDodati--;

			}
			indeks++;
		}
		neDodaniTermini.clear();

		// pokusaj odvajanja termina prve godine
		for (int i = 0; i < sviTermini.size(); i++) {
			Termin t = sviTermini.get(i);
			if (dodaniTermini.contains(t)) {
				continue;
			}
			if (!t.sadrziPredmetGodineNeTransverzalni(GodinaPremdeta.PRVA_GODINA)) {
				continue;

			}
			boolean dodan = false;
			for (int j = 0; j < sviDani.size(); j++) {
				Dan d = sviDani.get(j);

				if (d.sadrziTerminSPredmetomNaGodini(GodinaPremdeta.PRVA_GODINA) || d.sadrziTerminTransverzalni()) {
					continue;
				}
				if (d.moguceDodat() > 0) {
					d.dodajTermin(t);
					dodan = true;
					dodaniTermini.add(t);
					break;
				}

			}

			if (!dodan) {
				neDodaniTermini.add(t);
			}

		}
		// dodavanje nedodanih termina Prve godine
		for (int j = 0; j < neDodaniTermini.size(); j++) {
			Termin t = neDodaniTermini.get(j);
			int indeksMinimalnog = -1;
			int minPresjek = Integer.MAX_VALUE;
			for (int k = 0; k < sviDani.size(); k++) {
				Dan d = sviDani.get(k);
				if (d.moguceDodat() == 0) {
					continue;
				}
				int presjekZaDan = d.izracunajPresjekZaTerminUDanu(t);
				if (presjekZaDan < minPresjek) {
					indeksMinimalnog = k;
					minPresjek = presjekZaDan;
				}
			}
			if (!(indeksMinimalnog >= 0 && indeksMinimalnog < sviDani.size())) {
				throw new RuntimeException("Nije moguce dodati termin nigdje");
			}
			dodaniTermini.add(t);
			sviDani.get(indeksMinimalnog).dodajTermin(t);
		}

		neDodaniTermini.clear();

		// dodavanje ispita s druge godine u razlicite dane od ispita prve godine i
		// ispita druge godine

		for (int i = 0; i < sviTermini.size(); i++) {
			Termin t = sviTermini.get(i);
			if (dodaniTermini.contains(t)) {
				continue;
			}
			if (!t.sadrziPredmetGodineNeTransverzalni(GodinaPremdeta.DRUGA_GODINA)) {
				continue;
			}
			boolean dodan = false;
			for (int j = 0; j < sviDani.size(); j++) {
				Dan d = sviDani.get(j);
				if (d.sadrziTerminSPredmetomNaGodini(GodinaPremdeta.PRVA_GODINA, GodinaPremdeta.DRUGA_GODINA) || d.sadrziTerminTransverzalni()) {
					continue;
				}

				if (d.moguceDodat() > 0) {
					d.dodajTermin(t);
					dodan = true;
					dodaniTermini.add(t);
					break;
				}

			}
			if (!dodan) {
				neDodaniTermini.add(t);
			}

		}
		// dodavanje ispita druge godine koji jos nisu dodani tako da je trenutno stanje
		// presjeka najmanje
		for (int j = 0; j < neDodaniTermini.size(); j++) {
			Termin t = neDodaniTermini.get(j);
			int indeksMinimalnog = -1;
			int minPresjek = Integer.MAX_VALUE;
			for (int k = 0; k < sviDani.size(); k++) {
				Dan d = sviDani.get(k);
				if (d.moguceDodat() == 0) {
					continue;
				}
				int presjekZaDan = d.izracunajPresjekZaTerminUDanu(t);
				if (presjekZaDan < minPresjek) {
					indeksMinimalnog = k;
					minPresjek = presjekZaDan;
				}
			}
			if (!(indeksMinimalnog >= 0 && indeksMinimalnog < sviDani.size())) {
				throw new RuntimeException("Nije moguce dodati termin nigdje");
			}
			dodaniTermini.add(t);
			sviDani.get(indeksMinimalnog).dodajTermin(t);
		}

		neDodaniTermini.clear();

		// dodavanje ispita trece godine tamo gdje je najmanji presjek
		for (int i = 0; i < sviTermini.size(); i++) {
			Termin t = sviTermini.get(i);
			if (dodaniTermini.contains(t)) {
				continue;
			}
			if (t.sadrziPredmetGodineNeTransverzalni(GodinaPremdeta.TRECA_GODINA)) {
				neDodaniTermini.add(t);
			}
		}

		// dodavanje ispita trece godine tako da je trenutno stanje presjeka najmanje
		for (int j = 0; j < neDodaniTermini.size(); j++) {
			Termin t = neDodaniTermini.get(j);
			int indeksMinimalnog = -1;
			int minPresjek = Integer.MAX_VALUE;
			for (int k = 0; k < sviDani.size(); k++) {
				Dan d = sviDani.get(k);
				if (d.moguceDodat() == 0) {
					continue;
				}
				int presjekZaDan = d.izracunajPresjekZaTerminUDanu(t);
				if (presjekZaDan < minPresjek) {
					indeksMinimalnog = k;
					minPresjek = presjekZaDan;
				}
			}
			if (!(indeksMinimalnog >= 0 && indeksMinimalnog < sviDani.size())) {
				throw new RuntimeException("Nije moguce dodati termin nigdje");
			}
			dodaniTermini.add(t);
			sviDani.get(indeksMinimalnog).dodajTermin(t);
		}
		neDodaniTermini.clear();
		
		formatirajIspis(stringBuilder, sviDani);
		

	}

	private void sloziTerminePoDanima(StringBuilder stringBuilder) {
		System.out.println("MIN METODA-sloziTErmine po danim");
		List<PresjekTermina> listaPresjekaTermina = new ArrayList<>();
		kreirajListuPresjekaTermina(listaPresjekaTermina, sviTermini, studentiPoPredmetima);

		Collections.sort(listaPresjekaTermina);
		// System.out.println(listaPresjekaTermina);

		int brojDana = this.brojDana;
	
		List<Dan> nepopunenjiDani = new ArrayList<>();
		for (int i = 0; i < brojDana; i++) {
			nepopunenjiDani.add(new Dan(i, brojTerminaPoDanu));
		}

		int presjekTerminaObrada = 0;

		boolean potrebnoDodatPrvi = false;
		boolean potrebnoDodatDrugi = false;
		int potrebnoDodatTermina = maxColor;

		while (potrebnoDodatTermina > 0) {

			PresjekTermina presjekTermina = listaPresjekaTermina.get(presjekTerminaObrada);
			Termin t1 = presjekTermina.prviTermin;
			Termin t2 = presjekTermina.drugiTermin;
			potrebnoDodatPrvi = true;
			potrebnoDodatDrugi = true;

			for (int i = 0; i < nepopunenjiDani.size(); i++) {
				Dan d = nepopunenjiDani.get(i);
				if (d.sadrziTermin(t1)) {
					potrebnoDodatPrvi = false;
				}
				if (d.sadrziTermin(t2)) {
					potrebnoDodatDrugi = false;
				}

			}

			for (int i = 0; i < daniPopunjeniTerminima.size(); i++) {
				Dan d = daniPopunjeniTerminima.get(i);
				if (d.sadrziTermin(t1)) {
					potrebnoDodatPrvi = false;
				}
				if (d.sadrziTermin(t2)) {
					potrebnoDodatDrugi = false;
				}

			}

			if (potrebnoDodatPrvi) {
				dodajTerminUDan(t1, nepopunenjiDani);
				potrebnoDodatTermina--;
			}
			if (potrebnoDodatDrugi) {
				dodajTerminUDan(t2, nepopunenjiDani);
				potrebnoDodatTermina--;
			}
			presjekTerminaObrada++;

		}
		// System.out.println("IDU DANIIIIIIIIIIIIIIII");
		// System.out.println(dani);
		daniPopunjeniTerminima.addAll(nepopunenjiDani);
		
		formatirajIspis(stringBuilder, daniPopunjeniTerminima);

	}

	private void dodajTerminUDan(Termin t, List<Dan> nepopunenjiDani) {
		List<Integer> presjekTerminSDanima = new ArrayList<Integer>();

		boolean dodan = false;
		while (dodan == false) {
			Iterator<Dan> iter = nepopunenjiDani.iterator();
			while (iter.hasNext()) {
				Dan d = iter.next();
				if (d.moguceDodat() == 0) {
					daniPopunjeniTerminima.add(d);
					iter.remove();

					continue;
				}
				int presjek = d.izracunajPresjekZaTerminUDanu(t);
				presjekTerminSDanima.add(presjek);
			}
			int min = min(presjekTerminSDanima);
			int index = index(presjekTerminSDanima, min);
			if (nepopunenjiDani.get(index).moguceDodat() > 0) {
				nepopunenjiDani.get(index).dodajTermin(t);
				dodan = true;
			}
		}

	}

	private static int index(List<Integer> presjekTerminSDanima, int min) {
		for (int i = 0; i < presjekTerminSDanima.size(); i++) {
			if (presjekTerminSDanima.get(i) == min) {
				return i;

			}
		}
		return -1;
	}

	private static void kreirajListuPresjekaTermina(List<PresjekTermina> listaPresjekaTermina, List<Termin> sviTermini,
			Map<Predmet, List<Student>> studentiPoPredmetima) {
		for (int i = 0; i < maxColor; i++) {
			for (int j = i + 1; j < maxColor; j++) {
				Termin t1 = sviTermini.get(i);
				Termin t2 = sviTermini.get(j);

				List<Student> studenti1 = t1.getStudentiMogTermina(studentiPoPredmetima);

				List<Student> studenti2 = t2.getStudentiMogTermina(studentiPoPredmetima);
				int brojZajednickih = 0;
				for (Student s : studenti1) {
					if (studenti2.contains(s)) {
						brojZajednickih++;
					}
				}
				listaPresjekaTermina.add(new PresjekTermina(t1, t2, brojZajednickih));

			}
		}

	}

	private static class PresjekTermina implements Comparable<PresjekTermina> {
		private Termin prviTermin;
		private Termin drugiTermin;
		private int presjek;

		public PresjekTermina(Termin t1, Termin t2, int brojZajednickih) {
			prviTermin = t1;
			drugiTermin = t2;
			presjek = brojZajednickih;
		}

		@Override
		public int compareTo(PresjekTermina o) {
			return -Integer.compare(this.presjek, o.presjek);
		}

		@Override
		public String toString() {
			return prviTermin.getId() + prviTermin.getMojiPredmeti().toString() + " ---- " + drugiTermin.getId()
					+ drugiTermin.getMojiPredmeti().toString() + " ====" + presjek + "\n";
		}
	}

	public static int min(List<Integer> polje) {
		if (polje.size() == 0) {
			return -1;
		}
		int min = polje.get(0);

		for (int i = 0; i < polje.size(); i++) {
			if (polje.get(i) < min) {
				min = polje.get(i);
			}
		}
		return min;
	}

	public int getBROJ_TERMINA_PO_DANU() {
		return brojTerminaPoDanu;
	}

	public void setBROJ_TERMINA_PO_DANU(int brojTerminaPoDanu) {
		this.brojTerminaPoDanu = brojTerminaPoDanu;
	}

	public int getBROJ_DANA() {
		return brojDana;
	}

	public void setBROJ_DANA(int brojDana) {
		this.brojDana = brojDana;
	}

	public List<Čvor<Predmet>> getČvorovi() {
		return bojanjeGrafa.getČvorovi();
	}

	public List<Brid<Predmet>> getBridovi() {
		return bojanjeGrafa.getBridovi();
	}

	public int[][] getGraf() {
		return bojanjeGrafa.getGraf();
	}

}
