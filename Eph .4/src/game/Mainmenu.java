package game;
import basiX.*;


public class Mainmenu {

	private Fenster f;
	private Knopf k1,k2,k3,k4,ke;
	private boolean game = true;
	
	public Mainmenu() {
		f = new Fenster("Main Menu (Till)",150,350);
		k1 = new Knopf("Crabworld",0,0,150,50);
		k2 = new Knopf("TBD",0,100,150,50);
		k3 = new Knopf("TBD",0,200,150,50);
		ke = new Knopf("Ende",0,300,150,50);
	}
	
	private void mainf() {
	
		while(game) {
			Hilfe.kurzePause();
			if(ke.wurdeGedrueckt()) {
				game = false;
			}
			if(k1.wurdeGedrueckt()) {
				f.setzeSichtbar(false);
				KrebsWelt.main(null);	
			}
		}
		System.exit(0);
	}

	
	public static void main(String[] args) {
		Mainmenu m;
		m = new Mainmenu();
		m.mainf();
	}
	
}
