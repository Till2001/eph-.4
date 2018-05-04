package game;

import basiX.Dialog;
import basiX.Fenster;
import basiX.Hilfe;
import basiX.Tastatur;

public class Welt {

	private Fenster f;
	private Akteur[] a;
	private int n = 0;
	private boolean ende = false,c1 = false;
	private Tastatur t;
	private int h,w,c;

	public Welt(int h, int w) {
		f = new Fenster("Game (Till)", w, h);
		
		// f.ladeBildInZeichenflaeche("/basiX/images/default.jpg");
		t = new Tastatur();
		a = new Akteur[100];
		c = 0;
	}

	public void play() {
		while (!ende) {
			Hilfe.kurzePause();
			if (f.istSichtbar() == false) {
				ende = true;
			}
			for (int i = 0; i < n; i++) {
				if (!a[i].geloescht()) {
					a[i].tues();
				}
			}
		}
		System.exit(0);
	}

	public void fuegeEin(Akteur pAkteur, int pX, int pY) {
		a[n] = pAkteur;
		a[n].setzteIndex(n);
		a[n].lerneWeltKennen(this);
		a[n].setzePosition(pX, pY);
		n++;
	}

	public Tastatur tastatur() {
		return t;
	}

	public Fenster fenster() {
		return f;
	}

	public void keypress() {
		if(t.wurdeGedrueckt()) {
			t.holeZeichen();
			switch((int)t.aktuellesZeichen()) {

			default:
//				Dialog.info("", ""+(int)t.aktuellesZeichen());
				break;
			}
		}
	}
	
	public void anim() {
		if(c>=2) {
			if(c1==true) {
				a[0].setzeBild("/basiX/images/crab2.png");
				c1 = false;
				c = 0;
			}else {
				a[0].setzeBild("/basiX/images/crab.png");
				c1 = true;
				c = 0;
			}
		}
	}
	
	public void setzeHintergrund(String pPfad) {
		f.ladeBildInZeichenflaeche(pPfad);
	}

	public boolean kollision(Akteur pAkteur, Class pKlasse) {
		int index = pAkteur.index();
		for (int i = 0; i < n; i++) {

			if (index != i && a[i].getClass().equals(pKlasse)
					&& a[index].gibBild().kollisionErkanntMit(a[i].gibBild())) {

				return true;
			}
		}
		return false;

	}
	
	public int hight() {
		return h;
	}
	
	public int width() {
		return w;
	}

	public void entferne(Akteur pAkteur, Class pKlasse) {
		int index = pAkteur.index();
		for (int i = 0; i < n; i++) {

			if (index != i && a[i].getClass().equals(pKlasse)
					&& a[index].gibBild().kollisionErkanntMit(a[i].gibBild())) {
				a[i].loeschen();
				return;
			}
		}

	}

}
