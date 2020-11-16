package hr.fer.zpm.model;

import java.awt.Color;

public class Predmet {
	private String ime;
	private int ects;
	private int FER_ID;
	private int id;
	private boolean imaIspit;
	private GodinaPremdeta godinaPredmeta;
	private VrstaPredmeta vrstaPredmeta;
	private Color boja;
	
	public Predmet(String ime, int ects, int fer_id, int id, boolean imaIspit,
			GodinaPremdeta godinaPremdeta,VrstaPredmeta vrstaPredmeta) {
		this.ime = ime;
		this.ects = ects;
		this.FER_ID = fer_id;
		this.id = id;
		this.imaIspit=imaIspit;
		this.godinaPredmeta=godinaPremdeta;
		this.vrstaPredmeta=vrstaPredmeta;
	}
	
	
	
	
	public Color getBoja() {
		return boja;
	}




	public void setBoja(Color boja) {
		this.boja = boja;
	}




	public GodinaPremdeta getGodinaPredmeta() {
		return godinaPredmeta;
	}


	public void setGodinaPredmeta(GodinaPremdeta godinaPredmeta) {
		this.godinaPredmeta = godinaPredmeta;
	}


	public boolean isImaIspit() {
		return imaIspit;
	}

	public void setImaIspit(boolean imaIspit) {
		this.imaIspit = imaIspit;
	}




	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public int getEcts() {
		return ects;
	}

	public void setEcts(int ects) {
		this.ects = ects;
	}

	public int getFER_ID() {
		return FER_ID;
	}

	public void setFER_ID(int fER_ID) {
		FER_ID = fER_ID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	

	public VrstaPredmeta getVrstaPredmeta() {
		return vrstaPredmeta;
	}




	public void setVrstaPredmeta(VrstaPredmeta vrstaPredmeta) {
		this.vrstaPredmeta = vrstaPredmeta;
	}




	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + FER_ID;
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
		Predmet other = (Predmet) obj;
		if (FER_ID != other.FER_ID)
			return false;
		return true;
	}

	@Override
	public String toString() {
//		String  array[]= {	"ID" + String.valueOf(this.id),
//							"IME:" + this.ime,
//							"ECTS:"+this.ects,
//							"FER_ID:"+this.FER_ID,
//							"BROJ STUDENATE:"+Generator.dohvatiBrojStudenataZaPredmet(this),
//							"GODINA PREDMETA:"+this.godinaPredmeta,
//							"VRSTA PREDMETA:" + this.vrstaPredmeta
//							};
//		return String.join(" ,",Arrays.asList(array)) + "\n";
		return this.ime;
		//return "ID: "+this.id+", IME:"+ this.ime+", ECTS:" +this.ects+", FER_ID:"+this.FER_ID+" "+ ",BROJ STUDENATA: "+Generator.dohvatiBrojStudenataZaPredmet(this)+"\n";
	}

}
