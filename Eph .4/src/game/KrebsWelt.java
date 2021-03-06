package game;

import basiX.Dialog;
import basiX.Hilfe;;

public class KrebsWelt extends Welt {

	private Krebs K1,K2;
	private Hummer H1;
	
	public KrebsWelt() {
		super(600, 600);
		Dialog.info("Willkommen!", "Dieses Spiel wird Ihnen pr�sentiert von Till!");
		K1 = new Krebs('w','s','a','d',1);
		K2 = new Krebs('i','k','j','l',2);
		H1 = new Hummer();
		fuegeEin(K1, 100,100);
		fuegeEin(K2, 250, 250);
		fuegeEin(H1, 300, 300);
		int wn = Dialog.eingabeINT("", "Wieviele W�rmer");
		for(int i = 0;i<wn;i++) {
			fuegeEin(new Worm(), Hilfe.zufall(0, 500), Hilfe.zufall(0, 500));
		}
		
		
		
		
		
	}

	public static void main(String[] args) {
		KrebsWelt w;
		w = new KrebsWelt();
		w.setzeHintergrund("/basiX/images/sand.jpg");
		w.play();
	}
	

}
