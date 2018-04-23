package game;

import basiX.Bild;
import basiX.Hilfe;


public class Akteur {
	private int xPos, yPos;
	private double zRichtung;
	private int zGeschwindigkeit;
	private Bild zBild;
	private boolean angekommen=false;
	private int zIndex=-1;
	private Welt w;
	private boolean zGeloescht = false;

	public Akteur() {
		xPos = -500;
		yPos = -500;
		zRichtung = Hilfe.zufall(0, 360);
		zGeschwindigkeit = 10;
		zBild = new Bild("/basiX/images/greenfoot.png");
		zBild.setzeMitMausVerschiebbar(true);
		zeige();
	}

	public boolean geloescht(){
		return zGeloescht;
	}

	public int getX(){
		return xPos;
	}

	public int getY(){
		return yPos;
	}

	public double gibRichtung(){
		return zRichtung;
	}

	public int gibGeschwindigkeit(){
		return zGeschwindigkeit;
	}

	public void setzeBild(String pBild){
		zBild.ladeBild(pBild);
		zeige();
	}

	public String gibBildPfad(){
//		System.out.println(zBild.dateiName());
		return zBild.dateiName();
	}
	public Bild gibBild(){
		return zBild;
	}

	public Welt gibWelt(){
		return w;
	}

	public boolean kollision(Class pKlasse){
		return w.kollision(this, pKlasse);
	}

	public void entferne(Class pKlasse){
		w.entferne(this, pKlasse);
	}

	private void zeige() {
		double alteRichtung = zBild.bildWinkel();
		if (alteRichtung != zRichtung) {
			zBild.dreheUmMitGrößenAnpassung(zRichtung - alteRichtung);
		}
		zBild.setzePosition(xPos, yPos);
	}

	public boolean istGedrueckt(char pTaste){
		return w.tastatur().istGedrueckt(pTaste);
	}

	public boolean amRand(){
		return xPos>w.fenster().breite()-zBild.breite()||yPos>w.fenster().hoehe()-zBild.hoehe()||xPos<0||yPos<0;
	}

//	public boolean binDa() {
//		return angekommen;
//	}
//
//	public void setBinDa(boolean pDa){
//		angekommen=pDa;
//	}

	public void bewegeAuf(int px, int py) {
		if (!angekommen) {
			int dx = px - xPos;
			int dy = py - yPos;
			if (dx==0&&dy==0) {
				angekommen = true;
				//Dialog.info("angekommen"+angekommen, this.toString());
			} else {
				if (dx > 0 && dy > 0) {
					zRichtung = 360 - Hilfe.arctangens(dy / dx);
				} else {
					if (dx > 0 && dy < 0) {
						zRichtung = Hilfe.arctangens(-dy / dx);
					} else {
						if (dx < 0) {
							zRichtung = 180 - Hilfe.arctangens(dy / dx);
						} else {
							if ((dx == 0) & (dy < 0)) {
								zRichtung = 90;
							} else {
								if ((dx == 0) & (dy > 0)) {
									zRichtung = 270;
								}
							}
						}
					}
				}
				bewege();
				angekommen = false;
			}

		}

	}

	public void setzePosition(int px, int py) {
		xPos = px;
		yPos = py;
		zeige();
	}
	

	public void setzeGeschwindigkeit(int pGeschwindigkeit){
		zGeschwindigkeit=pGeschwindigkeit;
	}

	public void dreheBis(int pw) {
		zRichtung = pw;
		zeige();
	}

	public void dreheUm(int pw) {
		zRichtung += pw;
		zeige();
	}

	public void bewege() {
		Hilfe.kurzePause();
		xPos = (int) (xPos + Math.round(zGeschwindigkeit
				* Hilfe.cosinus(zRichtung)));
		yPos = (int) (yPos - Math.round(zGeschwindigkeit
				* Hilfe.sinus(zRichtung)));
		zeige();
	}

	public void tues(){
		// leere Methode, die in der Unterklasse ueberschrieben werden kann
	}

	public void setzteIndex(int pIndex) {
		zIndex=pIndex;

	}

	public int index(){
		return zIndex;
	}

	public void loeschen() {
		zBild.setzePosition(-500, -500);
		zBild.loescheAlles();
		zGeloescht  = true;

	}

	public void lerneWeltKennen(Welt pWelt) {
		w=pWelt;

	}

	public void zurueck() {
		Hilfe.kurzePause();
		xPos = (int) (xPos + Math.round(-zGeschwindigkeit * Hilfe.cosinus(zRichtung)));
		yPos = (int) (yPos - Math.round(-zGeschwindigkeit * Hilfe.sinus(zRichtung)));
		zeige();
		
	}

	public void turn(char c) {
		switch(c) {
		case 'l':
			this.dreheUm(5);
			break;
		case 'r':
			this.dreheUm(-5);
			break;
		}
		
	}

}
