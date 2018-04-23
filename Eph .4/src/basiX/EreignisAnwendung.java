package basiX;

// EreignisAnwendung.java

import java.awt.event.*;

import basiX.vw.*;

/**
 * Eine EreignisAnwendung ist der Prototyp einer Anwendung, die auf die
 * Standardereignisse der Maus und der Tastatur reagiert. Anfallende Ereignisse
 * werden einzeln einer zugeh�rigen Bearbeitungsmethode �bergeben. Unabh�ngig
 * davon k�nnen zwischendurch andere Ereignisse bearbeitet werden. Bei der
 * Realisierung von weiteren Ereignisanwendungen als Unterklasse k�nnen die
 * leeren Ereignisbearbeitungsmethjoden, wie bearbeiteTaste, bearbeiteMausDruck,
 * und andere �berschrieben werden. Mit ihnen werden die konkreten Reaktionen
 * auf die entsprechenden Ereignisse realisiert. Maus- und Tastaturobjekte sind
 * nicht verf�gbar, die Auswertung von Maus- und Tastaturereigneissen erfolgt
 * direkt �ber die Ereignismethoden. Die Methoden fuehreAus und beenden d�rfen
 * nicht �berschrieben werden. Erst der Auftrag fuehreAus setzt die
 * Ereignisbearbeitung in Gang.
 */
public class EreignisAnwendung {

	protected Fenster fenster = null;

	private boolean istMac = System.getProperty("os.name").equals("Mac OS");
	private boolean ende = false;

	/**
	 * Die EreignisAnwendung wird initialisiert. Es existiert ein Fenster der
	 * Dimension (800,600). Durch Aufruf von "fuehreAus" kann kann die
	 * Bearbeitung der Eriegnisse gestartet werden.
	 */
	public EreignisAnwendung() {
		this("", 800, 600);
	}

	/**
	 * Die EreignisAnwendung wird initialisiert. Die Titelleiste enth�lt den
	 * �bergebene Titel, Breite und H�he des zugeh�rigen Fenster werden �ber die
	 * entsprechenden Parameter festgelegt. Durch Aufruf von "fuehreAus" kann
	 * die Bearbeitung der Eriegnisse gestartet werden.
	 */
	public EreignisAnwendung(int breite, int hoehe, String st) {
		this(st, breite, hoehe);
	}

	/**
	 * Die EreignisAnwendung wird initialisiert. Die Titelleiste enth�lt den
	 * �bergebene Titel, Breite und H�he des zugeh�rigen Fenster werden �ber die
	 * entsprechenden Parameter festgelegt. Durch Aufruf von "fuehreAus" kann
	 * die Bearbeitung der Ereignisse gestartet werden.
	 */
	public EreignisAnwendung(String s, int breite, int hoehe) {
		super();
		info("fuehreAus");
		fenster = new Fenster(breite, hoehe);
		fenster.setzeTitel(s);

		// DieEreignisAnwendung.setzeEreignisAnwendung(this);
	}

	/** setzt die Ereignisbearbeitung in Gang, darf nicht �berschrieben werden */
	public final void fuehreAus() {
		fenster.leinwand().meineKomponente.addMouseListener(new MouseAdapter() {
			/**  */
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!e.isMetaDown()) {
					if (e.getClickCount() > 1) {
						EreignisAnwendung.this.bearbeiteDoppelKlick(e.getX(), e
								.getY());
						if (istMac)
							EreignisAnwendung.this.bearbeiteSpezialKlick(e
									.getX(), e.getY());
					} else
						EreignisAnwendung.this.bearbeiteMausKlick(e.getX(), e
								.getY());
				} else {
					EreignisAnwendung.this.bearbeiteMausKlickRechts(e.getX(), e
							.getY());
					EreignisAnwendung.this.bearbeiteSpezialKlick(e.getX(), e
							.getY());
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (!e.isMetaDown()) {
					EreignisAnwendung.this.bearbeiteMausDruck(e.getX(), e
							.getY());
				} else {
					EreignisAnwendung.this.bearbeiteMausDruckRechts(e.getX(), e
							.getY());
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (!e.isMetaDown()) {
					EreignisAnwendung.this.bearbeiteMausLos(e.getX(), e.getY());
				} else {
					EreignisAnwendung.this.bearbeiteMausLosRechts(e.getX(), e
							.getY());
				}
			}

			
		});fenster.leinwand().meineKomponente.addMouseMotionListener(new MouseAdapter() {
			/**  */
			

			

			@Override
			public void mouseDragged(MouseEvent e) {
				EreignisAnwendung.this.bearbeiteMausBewegt(e.getX(), e.getY());
				EreignisAnwendung.this.bearbeiteMausGezogen(e.getX(), e.getY());
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				EreignisAnwendung.this.bearbeiteMausBewegt(e.getX(), e.getY());
			}
		});


		fenster.setzeTastenLauscher(new TastenLauscher() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see basis.TastenLauscher#bearbeiteTaste(java.lang.Object, char)
			 */
			public void bearbeiteTaste(Komponente sender, char t) {
				EreignisAnwendung.this.bearbeiteTaste(t);
			}
		});
		fenster.addAndereEreignisseListener(this);
		fenster.leinwand().setzeLeinwandLauscher(new LeinwandLauscher() {
			/**  */
			public void leinwandWurdeGezeichnet(LeinwandIF l) {
				if (l == fenster.leinwand()) {
					EreignisAnwendung.this.bearbeiteFensterNeuGezeichnet();
					EreignisAnwendung.this.bearbeiteUpdate();
				}
			}
		});
		fenster.leinwand().setzeKomponentenLauscher(new KomponentenLauscher() {

			
			public void bearbeiteKomponentenVeraenderung(Komponente komponente) {
				EreignisAnwendung.this
						.bearbeiteKomponentenVeraenderung(komponente);

			}

		});
		this.bearbeiteStart();
		while (!ende) {
			try {
				Thread.sleep(10);
				this.bearbeiteAndereEreignisse();
				this.bearbeiteLeerlauf();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * public void init() { super.init(); this.fuehreAus(); }
	 */
	/**  */
	public void f\u00fchreAus() {
		this.fuehreAus();
	}

	/**
	 * ersetzt durch bearbeiteLeerlauf
	 */
	public void bearbeiteAndereEreignisse() {
	}

	/** beendet die Anwendung */
	public void beenden() {
		ende = true;
		this.gibFrei();
	}

	/**
	 * Bearbeitung des Maus-Ereignisses: linke Maustaste wurde gedr�ckt. Die
	 * Parameter enthalten die aktuelle Mausposition
	 */
	public void bearbeiteMausDruck(int x, int y) {
	}

	/**
	 * Bearbeitung des Maus-Ereignisses: rechte Maustaste wurde gedr�ckt. Die
	 * Parameter enthalten die aktuelle Mausposition
	 */
	public void bearbeiteMausDruckRechts(int x, int y) {
	}

	/**
	 * Bearbeitung des Maus-Ereignisses: linke Maustaste wurde los gelasse. Die
	 * Parameter enthalten die aktuelle Mausposition
	 */
	public void bearbeiteMausLos(int x, int y) {
	}

	/**
	 * Bearbeitung des Maus-Ereignisses: rechte Maustaste wurde los gelassen.
	 * Die Parameter enthalten die aktuelle Mausposition
	 */
	public void bearbeiteMausLosRechts(int x, int y) {
	}

	/**
	 * Bearbeitung des Maus-Ereignisses: Maus wurde bewegt. Die Parameter
	 * enthalten die aktuelle Mausposition
	 */
	public void bearbeiteMausBewegt(int x, int y) {
	}

	/**
	 * Bearbeitung des Mausn-Ereignisses: Maus wurde bei gedr�ckter linker
	 * Maustaste bewegt. Die Parameter enthalten die aktuelle Mausposition
	 */
	public void bearbeiteMausGezogen(int x, int y) {
	}

	/**
	 * Bearbeitung des Maus-Ereignisses: Klick (Druck und Loslassen) mit der
	 * linken Maustaste. Die Parameter enthalten die aktuelle Mausposition
	 */
	public void bearbeiteMausKlick(int x, int y) {
	}

	/**
	 * Bearbeitung des Maus-Ereignisses: Doppelklick bei Macintosh-Rechnern
	 * sonst Druck auf die rechte Maustaste. Die Parameter enthalten die
	 * aktuelle Mausposition
	 */
	public void bearbeiteSpezialKlick(int x, int y) {
	}

	/**
	 * Bearbeitung des Maus-Ereignisses: Klick (Druck und Loslassen) mit der
	 * rechten Maustaste. Die Parameter enthalten die aktuelle Mausposition
	 */
	public void bearbeiteMausKlickRechts(int x, int y) {
	}

	/**
	 * Bearbeitung des Maus-Ereignisses: Doppelklick ( zweimaliges kurz
	 * aufeinander folgendes Dr�cken und Loslassen) der linken Maustaste. Die
	 * Parameter enthalten die aktuelle Mausposition
	 */
	public void bearbeiteDoppelKlick(int x, int y) {
	}

	/**
	 * Bearbeitung des Ereignisses: Taste wurde gedr�ckt. Die Parameter
	 * enthalten das Zeichen, das bearbeitet werden kann. Bez�glich der
	 * Sonderzeichen werden die Konstanten der Klasse Zeichen verwendet.
	 */
	public void bearbeiteTaste(char zeichen) {
	}

	/**
	 * Bearbeitung des Ereignisses, wenn der Fenster durch �berdecken mit einem
	 * anderen Fenster, durch Gr��enver�nderung oder �hnliches vom System neu
	 * gezeichnet wurde.
	 */
	public void bearbeiteFensterNeuGezeichnet() {
	}

	/**
	 * wird immer wieder aufgerufen, wenn gerade kein Ereignis bearbeitet wird.
	 */
	public void bearbeiteLeerlauf() {
	}

	/**
	 * Bearbeitung des Ereignisses, wenn der Fenster durch �berdecken mit einem
	 * anderen Fenster, durch Gr��enver�nderung oder �hnliches vom System neu
	 * gezeichnet wurde, entspricht bearbeiteFensterNeuGezeichnet
	 */
	public void bearbeiteUpdate() {
	}

	/**
	 * Bearbeitung des Ereignisses: Anwendung wurde gerade gestartet. Wird
	 * ausgef�hrt bevor weitere Ereignisse bearbeitet werden.
	 */
	public void bearbeiteStart() {
	}

	/**
	 * Bearbeitung des Ereignisses: Anwendung wird gleich gestartet. Wird
	 * ausgef�hrt, bevor die Anwendung beendet wird.
	 */
	public void bearbeiteStop() {
	}

	/**  */
	public void gibFrei() {
		this.bearbeiteStop();
		this.fenster().gibFrei();
	}

	/**
	 * f�r Debuggingzwecke
	 * */
	public void info(String s) {
		if (Einstellungen.DEBUGMODUS)
			System.out.println(s);
	}

	/** liefert den Fenster der EreignisAnwendung */
	public Fenster fenster() {
		return this.fenster;
	}

	/**
	 * bearbeitet Komponentenver�nderungen, hier Gr��en�nderungen
	 */

	public void bearbeiteKomponentenVeraenderung(Komponente komponente) {
		// TODO Auto-generated method stub

	}

}
