package game;

import basiX.Bild;
import basiX.Hilfe;

public class Krebs extends Akteur{

		
	public Krebs(){
		super();
		setzeBild("/basiX/images/crab.png");
	}
	
	
	public void tues(){
		if(this.amRand()) {
			this.dreheUm(Hilfe.zufall(10, 45));		
		}	
	}
	
	
	
}
