package hr.fer.zpm.generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import hr.fer.zpm.model.Student;

public class GeneratorStudenata {
	private List<Student> studenti;
	
	private int brojStudenataPrveGodine;
	private int brojStudenataDrugeGodine;
	private int brojStudenataTreceGodine;
	
	public GeneratorStudenata(int brojStudenataPrveGodine, int brojStudenataDrugeGodine,
			int brojStudenataTreceGodine) {
		super();
		this.studenti = new ArrayList<Student>();
		this.brojStudenataPrveGodine = brojStudenataPrveGodine;
		this.brojStudenataDrugeGodine = brojStudenataDrugeGodine;
		this.brojStudenataTreceGodine = brojStudenataTreceGodine;
	}
	/**
	 * Ova metoda generira studente i njhove jmbagove
	 */
	public void generirajStudente() {
		try {
			List<String> names=Files.readAllLines(Paths.get("imena_generiranje.txt"));
			List<String> surnames=Files.readAllLines(Paths.get("prezimena_generiranje.txt"));

			Random rand = new Random(); 
			int randomIndeksImena,randomIndeksPrezimena,randomJmbag ;
			int brojImena=names.size();
			int brojPrezimena=surnames.size();
			int godina;
			String jmbag;
			
			//jmbagovi prve godine
			
			List<String> jmbagoviPrvaGodina=new LinkedList<String>();
			for(int i=0;i<10000;i++) {
				String formatted = String.format("%04d", i);
				jmbagoviPrvaGodina.add("00365"+formatted);
			}
			
			
			//jmbagovi druge godine
			List<String> jmbagoviDrugaGodina=new LinkedList<String>();
			for(int i=0;i<10000;i++) {
				String formatted = String.format("%04d", i);
				jmbagoviDrugaGodina.add("00364"+formatted);
			}
			
			//jmbagovi trece godine
			List<String> jmbagoviTrecaGodina=new LinkedList<String>();
			for(int i=0;i<10000;i++) {
				String formatted = String.format("%04d", i);
				jmbagoviTrecaGodina.add("00363"+formatted);
			}
			
			
			for(int i=0;i<1350;i++) {
				if(i<brojStudenataPrveGodine) {
					randomJmbag=rand.nextInt(jmbagoviPrvaGodina.size());
					jmbag=jmbagoviPrvaGodina.remove(randomJmbag);
					godina=1;
				}
				else if(i>=brojStudenataPrveGodine && i < brojStudenataPrveGodine+brojStudenataDrugeGodine ) {
					randomJmbag=rand.nextInt(jmbagoviDrugaGodina.size());
					jmbag=jmbagoviDrugaGodina.remove(randomJmbag);
					godina=2;
				}
				else {
					randomJmbag=rand.nextInt(jmbagoviTrecaGodina.size());
					jmbag=jmbagoviTrecaGodina.remove(randomJmbag);
					godina=3;
				}
				randomIndeksImena = rand.nextInt(brojImena-1);
				randomIndeksPrezimena=rand.nextInt(brojPrezimena-1);
				
				
				Student s=new Student(names.get(randomIndeksImena),surnames.get(randomIndeksPrezimena),jmbag,godina);
				studenti.add(s);
				
			} 
	        
		} catch (IOException e) {
			throw new RuntimeException("Ne mogu otvoriti datoteku.");
		}
		
	}
	/**
	 * Sluzi za dohvacanje studenata
	 * @return
	 */
	public List<Student> getStudenti() {
		return studenti;
	}
	

}
