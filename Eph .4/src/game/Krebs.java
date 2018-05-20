package game;
import basiX.*;

public class Krebs extends Akteur{
	char o,u,l,r;
	private int frame,fstate,ky1,ky2,kx1,kx2,score,n;
	
	public Krebs(char po,char pu, char pl, char pr,int nb) {
		super();
		o = po;
		u = pu;
		l = pl;
		r = pr;
		fstate = 0;
		score = 0;
		n = nb;
		setzeBild("/basiX/images/crab.png");
	}
	
	
	public void tues(){
		Hilfe.warte(10);
		edge();
		move();
		framef();
		test();
		koll();
		scorecheck();
	}




	private void scorecheck() {
		if(score==10) {
			Dialog.info("Ende", "Der Spieler "+n+" hat gewonnen");
		}
	}


	private void koll() {
		if(kollision(Worm.class)) {
			entferne(Worm.class);
			score++;
			
		}
	}


	private void framef() {
		if(frame>=5) {
			if(fstate == 0) {
				setzeBild("/basiX/images/crab2.png");
				fstate = 1;
			}else {
				setzeBild("/basiX/images/crab.png");
				fstate = 0;
			}
			frame = 0;
		}
	}
	
	public void test() {
		if(this.istGedrueckt('c')) {
			this.setzePosition(250, 250);
			Dialog.info("", ""+ky1+" + "+ky2);
			Dialog.info("", ""+kx1+" + "+kx2);
		}
	}


	public void edge() {
		if(this.amRand()) {
			this.dreheUm((int)this.gibRichtung());
		}
	}
	
	public void move() {
		if(this.istGedrueckt(o)) {
			this.bewege();
			frame++;
			Hilfe.warte(30);
		}
		if(this.istGedrueckt(u)) {
			this.ruecklaufen();
			frame++;
			Hilfe.warte(30);
		}
		if(this.istGedrueckt(l)) {
			this.dreheUm(-5);
		}
		if(this.istGedrueckt(r)) {
			this.dreheUm(5);
		}
		}
	
	
}
