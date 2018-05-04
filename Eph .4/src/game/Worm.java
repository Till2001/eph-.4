package game;
import basiX.*;

public class Worm extends Akteur{

	private int i,digstate,sektarget;
	
	public Worm() {
		super();
		digstate = 0;
		setzeBild("/basiX/images/worm.png");
	}
	
	public void tues() {
		if(digstate == 0) {
		random();
		dig();
		}else {
			digup();
		}
		Hilfe.kurzePause();
	}

	private void digup() {
		if(Hilfe.sekunde()==sektarget) {
			setzePosition(Hilfe.zufall(0, 500), Hilfe.zufall(0, 500));
			digstate = 0;
		}
	}

	private void dig() {
		if(i==1) {
			setzePosition(900, 900);
			digstate = 1;
			sektarget = Hilfe.sekunde()+2;
			if(sektarget > 60) {
				sektarget = sektarget - 60;
			}
		}
	}

	private void random() {
		i = Hilfe.zufall(0, 100);
	}
	
}
