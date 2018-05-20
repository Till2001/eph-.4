package game;
import basiX.*;

public class Hummer extends Akteur{

	public Hummer() {
		super();
		setzeBild("/basiX/images/lobster.png");
		
	}
	
	
	
	public void tues() {
		Hilfe.kurzePause();
		essen();
		if(!amRand()) {
			bewege();
		}else {
			dreheUm(75);
			bewege();
		}

	}
	
	public void essen() {
		if (kollision(Krebs.class)) {
			entferne(Krebs.class);
			Dialog.info("", "Gameover");
		}
	}
}
