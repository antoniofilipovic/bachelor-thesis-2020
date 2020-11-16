package hr.fer.zpm.generator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import hr.fer.zpm.model.Predmet;
import hr.fer.zpm.model.Student;

public class GeneratorPredmetaStudentima {

	public void dodijeliStudentimaPrvogSemestraPredmete(List<Student> studenti, List<Predmet> regularniPredmeti,
			List<Predmet> transverzalniPredmeti) throws Exception {
		List<Predmet> upisaniPredmeti = new ArrayList<Predmet>();
		upisaniPredmeti.addAll(regularniPredmeti);
		upisaniPredmeti.addAll(transverzalniPredmeti);
		
		List<Student> upisaniStudenti=new ArrayList<Student>();

		for (Student student : studenti) {

			if (student.getGodina() == 2) {
				break;
			}
			upisaniStudenti.add(student);

			student.dodajUpisanePredmete(upisaniPredmeti);
			

		}
//		for (Predmet p : upisaniPredmeti) {
//			upisanoStudenata.put(p, upisaniStudenti);
//		}

	}

	public void dodijeliStudentimaTrecegSemestraPredmete(List<Student> studenti,
			List<Predmet> regularniPredmetiDrugaGodina, List<Predmet> transverzalniPredmetiDrugaGodina,
			 List<Predmet> regularniPredmetiPrvaGodina,
			List<Predmet> transverzalniPredmetiPrvaGodina) throws Exception {

		List<Predmet> upisaniPredmetiRegularniStudenti = new ArrayList<Predmet>();
		upisaniPredmetiRegularniStudenti.addAll(regularniPredmetiDrugaGodina);

		int brojUpisanihStudenata = 0;
	

		Random rand = new Random();

		int randomBrojTransverzalnihRegularniStudenti;

		for (Student student : studenti) {
			if (student.getGodina() == 1) {
				continue;
			}
			if (student.getGodina() == 3) {
				break;
			}

			if (brojUpisanihStudenata >= 0 && brojUpisanihStudenata < 250) {

				student.dodajUpisanePredmete(upisaniPredmetiRegularniStudenti);
				
//				for (Predmet p : upisaniPredmetiRegularniStudenti) {
//					if(!upisanoStudenata.get(p).contains(student)) {
//						upisanoStudenata.get(p).add(student);
//					}
//					
//				}

				// random broj transverzalnih od 1 do 3
				randomBrojTransverzalnihRegularniStudenti = rand.nextInt(1) + 1;//jedan transverzalni samo

				List<Predmet> upisaniTransverzalniPredmeti = new ArrayList<>();
				List<Predmet> transverzalniPredmetiDrugaGodinaKopija = new ArrayList<Predmet>(
						transverzalniPredmetiDrugaGodina);
				for (int i = 0; i < randomBrojTransverzalnihRegularniStudenti; i++) {

					int randomIndex = rand.nextInt(transverzalniPredmetiDrugaGodinaKopija.size());

					// add element in temporary list
					upisaniTransverzalniPredmeti.add(transverzalniPredmetiDrugaGodinaKopija.get(randomIndex));

					// Remove selected element from orginal list
					transverzalniPredmetiDrugaGodinaKopija.remove(randomIndex);
				}

				student.dodajUpisanePredmete(upisaniTransverzalniPredmeti);
				
//				for (Predmet p : upisaniTransverzalniPredmeti) {
//					if(!upisanoStudenata.get(p).contains(student)) {
//						upisanoStudenata.get(p).add(student);
//
//					}
//				}

			} else {

				List<Predmet> regularniPredmetiKopija = new ArrayList<Predmet>(regularniPredmetiPrvaGodina);
				regularniPredmetiKopija.addAll(regularniPredmetiDrugaGodina);

				List<Predmet> transverzalniPredmetiKopija = new ArrayList<Predmet>(transverzalniPredmetiPrvaGodina);
				transverzalniPredmetiKopija.addAll(transverzalniPredmetiDrugaGodina);

//				boolean uzimamDodatniPredmet =false;

				boolean uzimamoRegularni;

//				int uzelihIzbornih = 0;
//				int uzeliDodatnih=0;
				
				//sad nek je ovako pa cemo kasnije mijenjat u bolji random
				int brojRegularnihZaUzet=ThreadLocalRandom.current().nextInt(4, 5 + 1);;;
				int brojTransverzalnihZaUzet=1;
				
				// System.out.println(transverzalniPredmetiKopija.size());
				//maknuo uvjet uzimamDodatni privremeno
				
				while (brojTransverzalnihZaUzet>0 || brojRegularnihZaUzet>0) {
					
//					uzimamDodatniPredmet = (rand.nextInt(2) == 0);
//					if(student.getUpisanoEcts() >=24 && uzimamDodatniPredmet) {
//						uzeliDodatnih++;
//					}
//					if(uzeliDodatnih>=3) {
//						break;
//					}
					if(brojRegularnihZaUzet>0) {
						uzimamoRegularni=true;
					}else if(brojTransverzalnihZaUzet>0) {
						uzimamoRegularni=false;
					}else {
						break;
					}
				
//					if (transverzalniPredmetiKopija.size() > 0 && regularniPredmetiKopija.size() > 0 ) {
//						uzimamoRegularni = ((rand.nextInt(2)) == 0);
//					} else if (transverzalniPredmetiKopija.size() == 0 && regularniPredmetiKopija.size() > 0) {
//						uzimamoRegularni = true;
//					} else {
//						uzimamoRegularni = false;
//					}
//					
//					if(uzelihIzbornih>=2 && regularniPredmetiKopija.size()>0) {
//						uzimamoRegularni=true;
//					}

					if (uzimamoRegularni) {

						int randomIndex = rand.nextInt(regularniPredmetiKopija.size());
						if(student.dodajUpisanPredmet(regularniPredmetiKopija.get(randomIndex))==false) {
							continue;
						}
						
//						if(!upisanoStudenata.get(regularniPredmetiKopija.get(randomIndex)).contains(student)) {
//							upisanoStudenata.get(regularniPredmetiKopija.get(randomIndex)).add(student);
//
//						}
						
					
						regularniPredmetiKopija.remove(randomIndex);
						brojRegularnihZaUzet--;

					} else {
						// System.out.println(transverzalniPredmetiKopija.size());
						if (transverzalniPredmetiKopija.size() == 0) {
							continue;
						}
						int randomIndex = rand.nextInt(transverzalniPredmetiKopija.size());

						if(student.dodajUpisanPredmet(transverzalniPredmetiKopija.get(randomIndex))==false) {
							continue;
						}
						
//						if(!upisanoStudenata.get(transverzalniPredmetiKopija.get(randomIndex)).contains(student)) {
//							upisanoStudenata.get(transverzalniPredmetiKopija.get(randomIndex)).add(student);
//
//						}
						transverzalniPredmetiKopija.remove(randomIndex);
						brojTransverzalnihZaUzet--;
					}
					if(student.getUpisanoEcts()<24 && brojRegularnihZaUzet==0 && brojTransverzalnihZaUzet==0) {
						brojRegularnihZaUzet=1;
					}

					// System.out.println(uzimamDodatniPredmet);

				}

			}
			brojUpisanihStudenata++;

		}
//		for(Predmet p:upisaniPredmeti) {
//			upisanoStudenata.put(p, brojUpisanihStudenata);
//		}

	}

	public void dodijeliStudentimaPetogSemestraPredmete(List<Student> studenti,
			List<Predmet> regularniPredmetiTrecaGodina, List<Predmet> transverzalniPredmetiTrecaGodina,
			List<Predmet> izborniPredmetiTrecaGodina,
			List<Predmet> regularniPredmetiDrugaGodina, List<Predmet> transverzalniPredmetiDrugaGodina) throws Exception {
		
		
		List<Predmet> upisaniPredmetiRegularniStudenti = new ArrayList<Predmet>();
		upisaniPredmetiRegularniStudenti.addAll(regularniPredmetiTrecaGodina);

		int brojUpisanihStudenata = 0;
		

		Random rand = new Random();

		int randomBrojTransverzalnihRegularniStudenti;
		int randomBrojIzbornihRegularniStudenti;

		for (Student student : studenti) {
			if (student.getGodina() == 1) {
				continue;
			}
			if (student.getGodina() == 2) {
				continue;
			}

			if (brojUpisanihStudenata >= 0 && brojUpisanihStudenata < 150) {

				student.dodajUpisanePredmete(upisaniPredmetiRegularniStudenti);
				
//				for (Predmet p : upisaniPredmetiRegularniStudenti) {
//					if(!upisanoStudenata.get(p).contains(student)) {
//						upisanoStudenata.get(p).add(student);
//
//					}
//					
//				}

				// random broj transverzalnih od 1 do 4
				randomBrojTransverzalnihRegularniStudenti = rand.nextInt(1) + 1;//jedan transverzalni samo

				List<Predmet> upisaniTransverzalniPredmeti = new ArrayList<>();
				List<Predmet> transverzalniPredmetiTrecaGodinaKopija = new ArrayList<Predmet>(
						transverzalniPredmetiTrecaGodina);
				for (int i = 0; i < randomBrojTransverzalnihRegularniStudenti; i++) {

					int randomIndex = rand.nextInt(transverzalniPredmetiTrecaGodinaKopija.size());

					// add element in temporary list
					upisaniTransverzalniPredmeti.add(transverzalniPredmetiTrecaGodinaKopija.get(randomIndex));

					// Remove selected element from orginal list
					transverzalniPredmetiTrecaGodinaKopija.remove(randomIndex);
				}

				student.dodajUpisanePredmete(upisaniTransverzalniPredmeti);
//				for (Predmet p : upisaniTransverzalniPredmeti) {
//					if(!upisanoStudenata.get(p).contains(student)) {
//						upisanoStudenata.get(p).add(student);
//
//					}
//				}
				
				randomBrojIzbornihRegularniStudenti = 2;//dva izborna samo
				
				//izborni predmeti
				List<Predmet> upisaniIzborniPredmeti = new ArrayList<>();
				List<Predmet> izborniPredmetiTrecaGodinaKopija = new ArrayList<Predmet>(
						izborniPredmetiTrecaGodina);
				for (int i = 0; i < randomBrojIzbornihRegularniStudenti; i++) {

					int randomIndex = rand.nextInt(izborniPredmetiTrecaGodinaKopija.size());

					// add element in temporary list
					upisaniIzborniPredmeti.add(izborniPredmetiTrecaGodinaKopija.get(randomIndex));

					// Remove selected element from orginal list
					izborniPredmetiTrecaGodinaKopija.remove(randomIndex);
				}
				student.dodajUpisanePredmete(upisaniIzborniPredmeti);
//				for (Predmet p : upisaniIzborniPredmeti) {
//					if(!upisanoStudenata.get(p).contains(student)) {
//						upisanoStudenata.get(p).add(student);
//
//					}
//					
//				}
			} else {

				List<Predmet> regularniPredmetiKopija = new ArrayList<Predmet>(regularniPredmetiTrecaGodina);
				regularniPredmetiKopija.addAll(regularniPredmetiDrugaGodina);
				

				Set<Predmet> setTransverzalniPredmetiKopija = new HashSet<Predmet>(transverzalniPredmetiTrecaGodina);
				for(Predmet p:transverzalniPredmetiDrugaGodina) {
					setTransverzalniPredmetiKopija.add(p);
				}
				List<Predmet> transverzalniPredmetiKopija=new ArrayList<Predmet>(setTransverzalniPredmetiKopija);
				List<Predmet> izborniPredmetiKopija = new ArrayList<Predmet>(izborniPredmetiTrecaGodina);

				boolean uzimamDodatniPredmet = (rand.nextInt(1) == 0);

				int uzimamo=0;

				int uzeliIzbornih=0;
				int uzeliTransverzalnih=0;
				int uzeliRegularnih=0;
				int uzeliDodatnih=0;
				
				int brojRegularnihZaUzet= ThreadLocalRandom.current().nextInt(3, 4 + 1);;
				int brojTransZaUzet=1;
				int brojIzbZaUzet=ThreadLocalRandom.current().nextInt(0, 2 + 1);;
				
				// System.out.println(transverzalniPredmetiKopija.size());
				//student.getUpisanoEcts() < 24 || uzimamDodatniPredmet prijasni uvjet
				while (brojRegularnihZaUzet>0 || brojTransZaUzet>0 || brojIzbZaUzet>0 ) {
//					uzimamDodatniPredmet = (rand.nextInt(2) == 0);
//					if(student.getUpisanoEcts() >=24 && uzimamDodatniPredmet) {
//						uzeliDodatnih++;
//					}
//					if(uzeliDodatnih>=3) {
//						break;
//					}
					
//					if (transverzalniPredmetiKopija.size() > 0 && regularniPredmetiKopija.size() > 0 && izborniPredmetiKopija.size()>0 &&
//							uzeliIzbornih<2 && uzeliTransverzalnih<2) {
//						uzimamo = rand.nextInt(3);
//					} else if (uzeliIzbornih>=2 && uzeliTransverzalnih>=2 && regularniPredmetiKopija.size() > 0 ) {
//						uzimamo = 0;
//					} else if(uzeliIzbornih<2 && uzeliTransverzalnih>=2 && izborniPredmetiKopija.size()>0) {
//						uzimamo = 2;
//					}
//					
//					else if(uzeliIzbornih>=2 && uzeliTransverzalnih<2 && transverzalniPredmetiKopija.size()>0) {
//						uzimamo = 1;
//					}
//					else if(regularniPredmetiKopija.size() > 0) {
//						uzimamo=0;
//					}
//					else if(transverzalniPredmetiKopija.size() > 0) {
//						uzimamo=1;
//					}
					
					if(brojRegularnihZaUzet>0) {
						uzimamo=0;
					}else if(brojTransZaUzet>0) {
						uzimamo=1;
					}
					else if(brojIzbZaUzet>0) {
						uzimamo=2;
					}else {
						break;
					}
					
					

					if (uzimamo==0) {

						int randomIndex = rand.nextInt(regularniPredmetiKopija.size());
						student.dodajUpisanPredmet(regularniPredmetiKopija.get(randomIndex));
//						if(!upisanoStudenata.get(regularniPredmetiKopija.get(randomIndex)).contains(student)) {
//							upisanoStudenata.get(regularniPredmetiKopija.get(randomIndex)).add(student);
//
//						}
//						
						regularniPredmetiKopija.remove(randomIndex);
						uzeliRegularnih++;
						brojRegularnihZaUzet--;
					} else if(uzimamo==1) {
						if(transverzalniPredmetiKopija.size()==0) {
							continue;
						}
						int randomIndex = rand.nextInt(transverzalniPredmetiKopija.size());

						student.dodajUpisanPredmet(transverzalniPredmetiKopija.get(randomIndex));
//						if(!upisanoStudenata.get(transverzalniPredmetiKopija.get(randomIndex)).contains(student)) {
//							upisanoStudenata.get(transverzalniPredmetiKopija.get(randomIndex)).add(student);
//
//						}
						
						transverzalniPredmetiKopija.remove(randomIndex);
						uzeliTransverzalnih++;
						brojTransZaUzet--;
					}else if(uzimamo==2) {

						int randomIndex = rand.nextInt(izborniPredmetiKopija.size());

						student.dodajUpisanPredmet(izborniPredmetiKopija.get(randomIndex));
//						if(!upisanoStudenata.get(izborniPredmetiKopija.get(randomIndex)).contains(student)) {
//							upisanoStudenata.get(izborniPredmetiKopija.get(randomIndex)).add(student);
//
//						}
						
						izborniPredmetiKopija.remove(randomIndex);
						uzeliIzbornih++;
						brojIzbZaUzet--;
					}

					// System.out.println(uzimamDodatniPredmet);

				}

			}
			brojUpisanihStudenata++;

		}
		
		

	}
}
