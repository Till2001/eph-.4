package game;

import basiX.*;

public class KrebsWelt extends Welt {

	private Krebs K1;
	
	public KrebsWelt() {
		super(600, 600);
		Dialog.info("Willkommen!", "Dieses Spiel wird Ihnen präsentiert von Till!");
		prepare();
	}


	private void prepare() {
		
		K1 = new Krebs();
		fuegeEin(K1, 150,150);
		
	}

	public static void main(String[] args) {
		KrebsWelt w;
		w = new KrebsWelt();
		w.setzeHintergrund("/basiX/images/sand.jpg");
		w.play();

	}

}
