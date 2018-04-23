package basiX;

import java.awt.AWTException;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.event.MouseInputAdapter;

//import basis.*;
import basiX.swing.BufferedCanvas;
import basiX.swing.Picture;
import basiX.vw.DasFenster;

/**
 * Objekte der Klasse Bild k�nnen Pixelbilder auf einer Leinwand oder einem
 * Fenster darstellen. Die Bilder k�nnen aus einer Datei geladen werden (jpg,
 * gif, png oder bmp) oder aus Picture-Objekten (basis.swing.Picture)�bernommen
 * werden. Bilder k�nnen gedreht, skaliert und gespiegelt werden. Bezugspunkt
 * f�r ein Bild ist die linke obere Ecke des umgebenden Rechtecks. Bildobjekte
 * unterst�tzen eine gegebenefalls vorhanden Transparenz der Vorlagen und k�nnen
 * selbst transparent �ber die Leinwand gelegt werden. Ein Bild kann ferner
 * feststellen, ob es sich mit einem anderen �berlappt. Hierbei kann unterucht
 * werden, ob nur die umgebenden Rechtecke sich �berlappen oder (im Falle
 * transparenter Bildbereiche), ob es (mindestens) zwei nicht vollst�ndig
 * transparente Pixel gibt, die �bereinander liegen. Ist f�r die zugeh�rige
 * Leinwand eine Maus erstellt worden, so leitet das Bild standardm��ig
 * Mauskoordinaten und Mausaktionen an die Leinwand weiter und wertet sie nicht
 * selber aus. Dieses Verhalten der Bilder kann ab- und angeschaltet werden.
 * 
 * Bilder sind Oberfl�chenkomponenten und erben ihre Grundeigenschaften von
 * Komponente.
 * 
 * @author Georg Dick
 * 
 */
public class Bild extends Komponente implements StiftFlaeche {
	protected BufferedCanvas malGrundlage;
	protected Picture bild;
	// gibt den letzten Punkt an, an dem eine Kollision festgestellt wurde
	private Punkt kollisionspunkt = null;
	// Umgebendes Rechteck, enth�lt auch die Information f�r die Position auf
	// einer Unterlage
	// linke obere Ecke. Das ist f�r eventuelle Kollisionen von Bedeutung
	private Rectangle2D.Double umgebendesRechteck;
	// bei vereinfachter Kollisionserkennung wird nicht pixelgenau
	// gepr�ft, sondern nur ein Ducrhschnitt mit dem das Bild umgebenden
	// Rechteck
	private boolean vereinfachteKollisionerkennung;

	protected boolean leinwandneugezeichnet;

	private Color zhintergrundfarbe = Farbe.WEISS;

	// protected Graphics grafikKontext=null;
	protected Toolkit toolkit;
	private boolean mausweiterleitunggewuenscht = true;

	/**
	 * Ist f�r die zugeh�rige Leinwand eine Maus erstellt worden, so leitet das
	 * Bild standardm��ig Mauskoordinaten und Mausaktionen an die Leinwand
	 * weiter und wertet sie nicht selber aus. Dieses Verhalten der Bilder kann
	 * ab- und angeschaltet werden.
	 * 
	 * @param mbg
	 *            bei true erfolgt eine Weiterleitung
	 */
	public void setzeMausWeiterleitung(boolean mbg) {
		if (mbg == this.mausweiterleitunggewuenscht) {
			return;
		}
		mausweiterleitunggewuenscht = mbg;
		if (!mbg) {
			this.fakeMouseListenerErzeugen();
		} else {
			this.fakeMouseListenerEntfernen();
		}
	}

	private void fakeMouseListenerEntfernen() {
		while (meineKomponente.getMouseListeners().length > 0) {
			meineKomponente.removeMouseListener(meineKomponente
					.getMouseListeners()[0]);
		}
		while (meineKomponente.getMouseMotionListeners().length > 0) {
			meineKomponente.removeMouseMotionListener(meineKomponente
					.getMouseMotionListeners()[0]);
		}
	}

	/**
	 * wenn kein Mouselistener vorhanden, werden die Mausevents an parent
	 * geleitet, damit ist dann die Herkunft nicht mehr identifizierbar. Siehe
	 * Bug ID 4413412
	 */
	protected void fakeMouseListenerErzeugen() {

		meineKomponente.addMouseListener(new MouseInputAdapter() {
		});
		meineKomponente.addMouseMotionListener(new MouseInputAdapter() {
		});

	}

	/** leert das Bild */
	public void loescheAlles() {
		BufferedImage img = ((BufferedCanvas) meineKomponente)
				.getBufferedImage();
		try {
			Graphics2D gr = (Graphics2D)meineKomponente.getGraphics();
			gr.setComposite(AlphaComposite.Src);
			gr.setColor(zhintergrundfarbe);
			gr.fillRect(0, 0, meineKomponente.getSize().width, meineKomponente
					.getSize().height);
			Graphics2D gh = (Graphics2D)img.createGraphics();
			gh.setComposite(AlphaComposite.Src);			
			gh.setColor(zhintergrundfarbe);
			gh.fillRect(0, 0, img.getWidth(), img.getHeight());
			meineKomponente.repaint();
		} catch (Exception e) {
		}

	}

	/**
	 * liefert genau dann wahr, wenn die SpriteFlaeche seit dem letzten Aufruf
	 * dieser Methode neu gezeichnet wurde, etwa durch Gr��en�nderung oder
	 * Verschieben.
	 */
	public boolean wurdeNeuGezeichnet() {
		boolean merke = this.leinwandneugezeichnet;
		this.leinwandneugezeichnet = false;
		return merke;
	}

	/** liefert die Farbe des Hintergrundes */
	public Color hintergrundFarbe() {
		return zhintergrundfarbe;
	}

	/**
	 * leert die SpriteFlaeche.
	 */
	public void l�scheAlles() {
		this.loescheAlles();
	}

	/**
	 * Legt fest, inwieweit die gesamte Spritefl�che transparent dargestellt
	 * werden soll
	 * 
	 * @param a
	 *            Grad: zwischen 0 und 1 (jeweils einschlie�lich)
	 */
	public void setzeTransparenzGrad(double a) {
		((BufferedCanvas) this.meineKomponente).setAlpha((float) a);
	}

	/**
	 * Legt fest, ob die SpriteFlaeche eine transparente Farbe enthalten kann
	 * Dies hat nichts mit der Setzung des Transparenzgrades zu tun.
	 * 
	 * @param t
	 */
	public void setzeTransparenz(boolean t) {
		// this.transparent = t;
		((BufferedCanvas) this.meineKomponente).setOpaque(!t);
	}

	/**  */
	protected void sync() {
		((BufferedCanvas) this.meineKomponente).sync();

	}

	/** Bearbeitung des SpriteFlaeche Neu Gezeichnet Ereignisses */
	public void bearbeiteLeinwandNeuGezeichnet() {
		// if (this == DasFenster.hauptLeinwand())System.out.println("Lw
		// neugez");
	}

	/**
	 * 
	 * @return liefert ein BufferedImage des angezeigten Bildes
	 */
	public BufferedImage hintergrundBild() {
		return ((BufferedCanvas) meineKomponente).getBufferedImage();
	}

	/**
	 * liefert die Farbe des Pixels mit den angegebenen Koordinaten
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public Color farbeVon(int x, int y) {

		try {
			Robot r;
			r = new Robot();
			return r.getPixelColor(x
					+ this.getSwingComponent().getLocationOnScreen().x, y
					+ this.getSwingComponent().getLocationOnScreen().y);
		} catch (AWTException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * liefert ein Graphics-Objekt des zur Komponente geh�renden BufferedImage
	 */
	
	public Graphics getBufferedImageGraphics() {
		return ((BufferedCanvas) this.getSwingComponent())
				.getBufferedImageGraphics();

	}

	/**
	 * 
	 * @return liefert den Namen der Datei unter der das aktuelle Bild
	 *         gespeichert ist
	 * 
	 */
	public String dateiName() {
		if (bild != null) {
			return bild.getLastSavedPicturePath().equals("") ? bild
					.getPicturePath() : bild.getLastSavedPicturePath();
		}
		return "";
	}

	public Bild() {

		this(0, 0, DasFenster.hauptLeinwand(), true);
	}

	public Bild(boolean sichtbar) {

		this(0, 0, DasFenster.hauptLeinwand(), sichtbar);
	}

	public Bild(String pfad, boolean sichtbar) {
		this(pfad, 0, 0, DasFenster.hauptLeinwand(), sichtbar);
	}

	public Bild(String pfad) {
		this(pfad, 0, 0, DasFenster.hauptLeinwand(), true);
	}

	
	public Bild(double x, double y,double b, double h,Komponente c, boolean sichtbar) {
		super(new BufferedCanvas(), x, y, b, h, c);
		// this.setLayout(null);
		// ((BufferedCanvas) meineKomponente).setOpaque(true);
		if (c != null) {
			if (c.meineKomponente instanceof JFrame) {
				((JFrame) c.meineKomponente).setContentPane(meineKomponente);
			} else {
				try {
					c.meineKomponente.add(meineKomponente, BorderLayout.CENTER);
				} catch (Exception exc1) {
					c.meineKomponente.add(meineKomponente);
				}
			}
		}
		try {
			this.setzeMausWeiterleitung(mausweiterleitunggewuenscht);
			// this.setzeHintergrundFarbe(zhintergrundfarbe);
			meineKomponente.repaint();
			// this.setBackground(zhintergrundfarbe);

		} catch (Exception e) {
			e.printStackTrace();
		}

		bild = new Picture(b, h);
		this.bildInit(sichtbar);
	}
	
	public Bild(double x, double y,double b, double h, boolean sichtbar) {
		this(x,y,b,h,DasFenster.hauptLeinwand(),sichtbar);
	}
	public Bild(double x, double y,double b, double h) {
		this(x,y,b,h,true);
	}
	public Bild(double x, double y, Komponente c, boolean sichtbar) {
		super(new BufferedCanvas(), x, y, 10, 10, c);
		// this.setLayout(null);
		// ((BufferedCanvas) meineKomponente).setOpaque(true);
		if (c != null) {
			if (c.meineKomponente instanceof JFrame) {
				((JFrame) c.meineKomponente).setContentPane(meineKomponente);
			} else {
				try {
					c.meineKomponente.add(meineKomponente, BorderLayout.CENTER);
				} catch (Exception exc1) {
					c.meineKomponente.add(meineKomponente);
				}
			}
		}
		try {
			this.setzeMausWeiterleitung(mausweiterleitunggewuenscht);
			// this.setzeHintergrundFarbe(zhintergrundfarbe);
			meineKomponente.repaint();
			// this.setBackground(zhintergrundfarbe);

		} catch (Exception e) {
			e.printStackTrace();
		}

		bild = new Picture(10, 10);
		this.bildInit(sichtbar);
	}

	/**
	 * erzeugt ein Bild
	 * 
	 * @param pfad
	 *            Dateipfad f�r das Bild
	 * @param x
	 *            x-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Bild liegt
	 * @param y
	 *            y-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Bild liegt
	 * @param p
	 *            Komponente in die das Bild eingebettet werden soll.
	 */
	public Bild(String pfad, double x, double y, Komponente c) {
		this(pfad, x, y, c, true);
	}

	/**
	 * erzeugt ein Bild
	 * 
	 * @param pfad
	 *            Dateipfad f�r das Bild
	 * @param x
	 *            x-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Bild liegt
	 * @param y
	 *            y-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Bild liegt
	 * @param p
	 *            Komponente in die das Bild eingebettet werden soll.
	 */
	public Bild(String pfad, double x, double y, Komponente c, boolean sichtbar) {
		super(new BufferedCanvas(), x, y, 10, 10, c, false);
		// this.setLayout(null);
		// ((BufferedCanvas) meineKomponente).setOpaque(true);
		if (c != null) {
			if (c.meineKomponente instanceof JFrame) {
				((JFrame) c.meineKomponente).setContentPane(meineKomponente);
			} else {
				try {
					c.meineKomponente.add(meineKomponente, BorderLayout.CENTER);
				} catch (Exception exc1) {
					c.meineKomponente.add(meineKomponente);
				}
			}
		}
		try {
			this.setzeMausWeiterleitung(mausweiterleitunggewuenscht);
			// this.setzeHintergrundFarbe(zhintergrundfarbe);
			meineKomponente.repaint();

			// this.setBackground(zhintergrundfarbe);

		} catch (Exception e) {
			e.printStackTrace();
		}
		bild = new Picture(pfad);
		// System.out.println(bilder.size());
		this.bildInit(sichtbar);
	}

	/**
	 * erzeugt ein Bild auf einem Fenster
	 * 
	 * @param pfad
	 *            Dateipfad f�r das Bild
	 * @param x
	 *            x-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Bild liegt
	 * @param y
	 *            y-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Bild liegt
	 */
	public Bild(String pfad, double x, double y) {
		this(pfad, x, y, DasFenster.hauptLeinwand());
	}

	/**
	 * erzeugt ein Bild auf einem Fenster
	 * 
	 * @param pfad
	 *            Dateipfad f�r das Bild
	 * @param x
	 *            x-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Bild liegt
	 * @param y
	 *            y-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Bild liegt
	 */
	public Bild(String pfad, double x, double y, boolean sichtbar) {
		this(pfad, x, y, DasFenster.hauptLeinwand(), sichtbar);
	}

	/**
	 * erzeugt ein Bild
	 * 
	 * @param bild
	 *            Imageobjekt f�r das Bild
	 * @param x
	 *            x-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Bild liegt
	 * @param y
	 *            y-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Bild liegt
	 * @param p
	 *            Komponente in die das Bild eingebettet werden soll.
	 */
	public Bild(Picture bild, double x, double y, Komponente c) {
		this(bild, x, y, c, true);
	}

	/**
	 * erzeugt ein Bild
	 * 
	 * @param bild
	 *            Imageobjekt f�r das Bild
	 * @param x
	 *            x-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Bild liegt
	 * @param y
	 *            y-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Bild liegt
	 * @param p
	 *            Komponente in die das Bild eingebettet werden soll.
	 */
	public Bild(Picture bild, double x, double y, Komponente c, boolean sichtbar) {
		super(new BufferedCanvas(), x, y, 10, 10, c);
		// this.setLayout(null);
		// ((BufferedCanvas) meineKomponente).setOpaque(true);
		if (c != null) {
			if (c.meineKomponente instanceof JFrame) {
				((JFrame) c.meineKomponente).setContentPane(meineKomponente);
			} else {
				try {
					c.meineKomponente.add(meineKomponente, BorderLayout.CENTER);
				} catch (Exception exc1) {
					c.meineKomponente.add(meineKomponente);
				}
			}
		}
		try {
			this.setzeMausWeiterleitung(mausweiterleitunggewuenscht);
			// this.setzeHintergrundFarbe(zhintergrundfarbe);
			meineKomponente.repaint();
			// this.setBackground(zhintergrundfarbe);

		} catch (Exception e) {
			e.printStackTrace();
		}
		this.bild = bild;
		this.bildInit(sichtbar);
	}

	/**
	 * erzeugt ein Bild auf dem zuletzt erzeugten Fenster. Dies muss vorhanden
	 * sein.
	 * 
	 * @param bild
	 *            Imageobjekt f�r das Bild
	 * @param x
	 *            x-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Bild liegt
	 * @param y
	 *            y-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Bild liegt
	 * 
	 */
	public Bild(Picture b, double x, double y, boolean sichtbar) {
		this(b, x, y, DasFenster.hauptLeinwand(), sichtbar);
	}

	/**
	 * erzeugt ein Bild auf dem zuletzt erzeugten Fenster. Dies muss vorhanden
	 * sein.
	 * 
	 * @param bild
	 *            Imageobjekt f�r das Bild
	 * @param x
	 *            x-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Bild liegt
	 * @param y
	 *            y-Koordinate der linken obere Ecke des Rechtecks, in dem das
	 *            Bild liegt
	 * 
	 */
	public Bild(Picture b, double x, double y) {
		this(b, x, y, DasFenster.hauptLeinwand());
	}

	/**
	 * liefert das aktuell dargestellte PixelBild als Picture-Objekt
	 */
	public Picture holeBilddaten() {
		return bild;
	}

	/**
	 * setzt Bilddaten aus einem Pictureobjekt. Das Bild wird anschlie�end
	 * dargestellt.
	 */
	public void setzeBilddaten(Picture bild) {
		this.bild = bild;
		bildInit(true);
	}

	/**
	 * L�dt ein Bild �ber einen Dateidialog
	 */
	public void ladeBild() {
		Picture neu = new Picture(1, 1);
		if (neu.loadImageByDialog()) {
			this.bild = neu;
			bildInit(true);
		}
	}

	/**
	 * Speichert das Bild
	 * 
	 * @param dateiname
	 *            Pfad in der Form "LW:/Ordner/..../Dateiname.typ" oder
	 *            "/Ordner/..../Dateiname.typ" oder "/Dateiname.typ" Typ : gif,
	 *            jpg , png oder bmp. Fehlt der Typ, so wird jpg als Typ
	 *            angenommen
	 */
	public boolean speichereBildUnter(String dateiname) {
		return this.bild.saveRecent(dateiname);
	}

	/**
	 * Speichert das Bild unter seinem alten Namen. Falls keiner vorhanden ist,
	 * wird es nicht gespeichert
	 */
	public boolean speichereBildUnterAltemNamen() {
		if (bild.getLastSavedPicturePath() != null
				&& !bild.getLastSavedPicturePath().equals("")) {
			return this.speichereBildUnter(bild.getLastSavedPicturePath());
		}
		if (bild.getPicturePath() != null && !bild.getPicturePath().equals("")) {
			return this.speichereBildUnter(bild.getPicturePath());
		}
		return false;
	}

	/**
	 * Speichert das Bild mittels Dateidialog
	 */
	public boolean speichereMitDateidialog() {
		return this.bild.saveByDialog();
	}

	/**
	 * 
	 * @return liefert letzten Kollisionspunkt mit einem anderen Bild. Wenn
	 *         bislang keine Kollision stattgefunden hat wird null zur�ck
	 *         gegeben
	 */
	public Punkt holeKollisionspunkt() {
		return kollisionspunkt;
	}

	protected void bildInit(boolean visible) {
		this.setzeSichtbar(false);
		this.setzeTransparenz(true);
//		this.setzeHintergrundFarbe(Farbe.rgb(Hilfe.zufall(1,200),Hilfe.zufall(1,200),Hilfe.zufall(1,200)));
		malGrundlage = (basiX.swing.BufferedCanvas) this.getSwingComponent();
		// this.fakeMouseListenerErzeugen();
		this.setzeGroesse(bild.getWidth(), bild.getHeight());
		this.setzeSichtbar(visible);
//		 malGrundlage.setBufferedImage(bild.getRecentImage());
//		 malGrundlage.repaint();
//		 malGrundlage.setVisible(visible);

		// malGrundlage.repaint();
	}

	/**
	 * f�gt das Bild hinzu, dessen Pfad angegeben wird. Zul�ssig sind jpg, gif,
	 * png und bmp-codierte Bilder
	 */
	public void ladeBild(String pfad) {
		bild = new Picture(pfad);
		bildInit(true);
	}

	/**
	 * f�gt das Bild hinzu, dessen Pfad angegeben wird. Zul�ssig sind jpg, gif,
	 * png und bmp-codierte Bilder. Dieses Bild erh�lt zus�tzlich eine ID-Nummer
	 */
	public void ladeBild(String pfad, int id) {
		bild = new Picture(pfad);
		bild.setId(id);
		bildInit(true);
	}

	/**
	 * ver�ndert die Bildgr��e, das Bild wird passend skaliert
	 * 
	 */
	@Override
	public void setzeGroesse(double b, double h) {
		if (bild != null) {
			bild.scale(b / bild.getWidth(), h / bild.getHeight());
		}
		super.setzeGroesse(b, h);
		if (bild != null) {
			malGrundlage = ((BufferedCanvas) this.getSwingComponent());
			malGrundlage.setBufferedImage(bild.getRecentImage());
			malGrundlage.repaint();
		}
	}

	/**
	 * stellt die Komponente dar.
	 */
	public void zeige() {
		boolean s = this.istSichtbar();
		this.setzeSichtbar(false);

		super.setzeGroesse(bild.getWidth(), bild.getHeight());
		malGrundlage = ((BufferedCanvas) this.getSwingComponent());
		malGrundlage.setBufferedImage(bild.getRecentImage());
		this.setzeSichtbar(s);
		// malGrundlage.repaint();
		// malGrundlage.getParent().repaint();
	}

	/**
	 * dreht das Bild mit Gr��enanpassung um w weiter. 
	 * @param w Winkel
	 */
	public void dreheUmMitGr��enAnpassung(double w){
		this.setzeBildWinkelMitGr��enAnpassung(w+this.bildWinkel());
	}
	/**
	 * dreht das Bild ohne Gr��enanpassung um w weiter. 
	 * @param w Winkel
	 */
	public void dreheUmOhneGr��enAnpassung(double w){
		this.setzeBildWinkelOhneGr��enAnpassung(w+this.bildWinkel());
	}
	/**
	 * setzt den Winkel um den das Bild gedreht wird und dreht das Bild um den
	 * Diagonalenschnittpunkt Die Gr��e des Bildes bleibt unver�ndert, so dass
	 * in der Anzeige Ecken abgeschnitten werden k�nnen. Das Ausgangsbild wird
	 * nicht ver�ndert und beispielsweise durch eine umgekehrte Drehung wieder
	 * vollst�ndig sichtbar.
	 */
	public void setzeBildWinkelOhneGr��enAnpassung(double a) {
		if (bild != null) {
			bild.setRotationAdjustment(false);
			bild.rotateTo(a);
		}
		this.zeige();
	}

	/**
	 * setzt den Winkel um den das Bild gedreht wird und dreht das Bild um den
	 * Diagonalenschnittpunkt Die Gr��e des Bildes wird so ver�ndert, dass in
	 * der Anzeige keine Ecken abgeschnitten werden. Die Position (linke obere
	 * Ecke) des neuen Bildes wird nicht ver�ndert, so dass gegebenenfalls eine
	 * Positionskorrektur erfolgen muss, wenn der Mittelpunkt fest bleiben soll.
	 */
	public void setzeBildWinkelMitGr��enAnpassung(double a) {
		if (bild != null) {
			int mx, my;
			mx = bild.getCenter().getX();
			my = bild.getCenter().getY();
			bild.setRotationAdjustment(true);
			bild.rotateTo(a);
			this.setzePosition(this.hPosition() + mx - bild.getCenter().getX(),
					this.vPosition() + my - bild.getCenter().getY());
		}
		this.zeige();
	}
	
	


	/**
	 * Spiegelt das Bild an der horizontalen oder vertikalen Achse
	 * 
	 * @param anHorizontalerAchse
	 *            (wenn true: Spiegelung an der horizontalen Achse)
	 */
	public void spiegeleBild(boolean anHorizontalerAchse) {
		if (bild != null) {
			bild.flip(anHorizontalerAchse);
		}
		this.zeige();
	}

	/**
	 * skaliert das Bild in x und y-Richtung. Die Bildgr��e wird dabie
	 * ver�ndert, die Position bleibt.
	 * 
	 * @param fx
	 *            Skalierungsfaktor in x-Richtung
	 * @param fy
	 *            Skalierungsfaktor in y-Richtung
	 * 
	 *            die linke obere Ecke bleibt bestehen
	 */
	public void skaliereBild(double fx, double fy) {
		if (bild != null) {
			bild.scale(fx, fy);
		}
		this.zeige();

	}

	/**
	 * 
	 * @return liefert den Winkel, auf den das Bild gedreht ist
	 */
	public double bildWinkel() {
		return bild.getRotationAngle();
	}

	/**
	 * 
	 * @return liefert den Mittelpunkt der Rotation des Bildes
	 */
	public Punkt mittelpunkt() {
		return new Punkt(bild.getCenter().getX(), bild.getCenter().getY());
	}

	/**
	 * stellt das Bild nochmals dar, wenn etwa durch andere Zeichenoperationen
	 * Artefakte entstanden sind.
	 */

	public void aktualisiereDarstellung() {
		this.zeige();
	}

	private Rectangle2D.Double getRechteck() {
		if (umgebendesRechteck == null) {
			umgebendesRechteck = new Rectangle2D.Double();
		}
		umgebendesRechteck.x = this.hPosition();
		umgebendesRechteck.y = this.vPosition();
		umgebendesRechteck.width = this.breite();
		umgebendesRechteck.height = this.hoehe();
		return umgebendesRechteck;
	}

	private Rectangle2D.Double getSubRec(Rectangle2D.Double source,
			Rectangle2D.Double part) {

		// Rechteck erzeugen
		Rectangle2D.Double sub = new Rectangle2D.Double();
		sub.x = source.x > part.x ? 0 : part.x - source.x;
		sub.y = source.y > part.y ? 0 : part.y - source.y;
		sub.width = part.width;
		sub.height = part.height;
		return sub;
	}

	/**
	 * liefert den Wert true, wenn das Bild selbst mit einem Bild b im Bereich
	 * kollidiert. In diesem Fall wird ein Kollisionspunkt berechnet, der
	 * erfragt werden kann. Bei vereinfachter Kollsisionserkennung wird
	 * lediglich �berpr�ft, ob sich die das Bild umgebenden Rechtecke
	 * �berschneiden. Andernfalls werden pixelgenau nach �berschneidungen im
	 * nichttransparenten Bereich der Bilder gesucht. Bei einer Kollision werden
	 * die Koordinaten eines Punktes als Kollisionspunkt notiert. Man kann �ber
	 * die Abfrage @see getKollisionspunkt() den Punkt erfragen.
	 * 
	 * @param bi
	 * @return
	 */
	public boolean kollisionErkanntMit(Bild bi) {
		Rectangle2D.Double durchschnitt = (Double) this.getRechteck()
				.createIntersection(bi.getRechteck());
		if ((durchschnitt.width < 1) || (durchschnitt.height < 1)) {
			return false;
		}

		if (vereinfachteKollisionerkennung) {
			kollisionspunkt = new Punkt((int) durchschnitt.x,
					(int) durchschnitt.y);
			return true;
		}
		try {
			// Rechtecke in Bezug auf die jeweiligen Images
			Rectangle2D.Double durchschnittRechteckEigenesBild = getSubRec(
					this.umgebendesRechteck, durchschnitt);
			Rectangle2D.Double durchschnittRechteckAnderesBild = getSubRec(bi
					.getRechteck(), durchschnitt);

			BufferedImage durchschnittEigenesBild = bild.getRecentImage()
					.getSubimage((int) durchschnittRechteckEigenesBild.x,
							(int) durchschnittRechteckEigenesBild.y,
							(int) durchschnittRechteckEigenesBild.width,
							(int) durchschnittRechteckEigenesBild.height);

			BufferedImage durchschnittAnderesBild = bi.getPicture()
					.getRecentImage().getSubimage(
							(int) durchschnittRechteckAnderesBild.x,
							(int) durchschnittRechteckAnderesBild.y,
							(int) durchschnittRechteckAnderesBild.width,
							(int) durchschnittRechteckAnderesBild.height);

			for (int x = 0; x < durchschnittEigenesBild.getWidth(); x += 1) {
				for (int y = 0; y < durchschnittAnderesBild.getHeight(); y += 1) {
					int rgb1 = durchschnittEigenesBild.getRGB(x, y);
					int rgb2 = durchschnittAnderesBild.getRGB(x, y);
					// Beide Pixel undurchsichtig?
					if ((((rgb1 >> 24) & 0xff) != 0)
							&& (((rgb2 >> 24) & 0xff) != 0)) {
						kollisionspunkt = new Punkt(
								(int) durchschnittRechteckEigenesBild.x + x,
								(int) durchschnittRechteckEigenesBild.y + y);
						return true;
					}
				}
			}
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 
	 * @return liefert die Bilddaten als Picture-Objekt
	 */
	public Picture getPicture() {
		return bild;
	}

	/**
	 * 
	 * @return liefert den Wert true, wenn nur das das Bild umgebende Rechteck
	 *         f�r die Kollisionserkennung eine Rolle spielt
	 */
	public boolean vereinfachteKollisionerkennung() {
		return vereinfachteKollisionerkennung;
	}

	/**
	 * Legt fest, ob transparente Bildbereiche Kollsionen hervorrifen k�nnen
	 * (Parameter dann false) oder nicht (Parameter true)
	 * 
	 * @param vereinfachteKollisionerkennung
	 */
	public void setzeVereinfachteKollisionerkennung(
			boolean vereinfachteKollisionerkennung) {
		this.vereinfachteKollisionerkennung = vereinfachteKollisionerkennung;
	}

	// /**
	// *
	// * @return liefert einen zuletzt erkannten Kollisionspunkt. Wenn keine
	// Kollision
	// * stattgefunden hat oder der Punkt gel�scht wurde wird <b>null</b>
	// * geliefert.
	// */
	// public Punkt getKollisionspunkt() {
	// return kollisionspunkt;
	// }

	/**
	 * L�scht den Kollisionspunkt
	 */
	public void loescheKollisionspunkt() {
		this.kollisionspunkt = null;
	}

	/**
	 * 
	 * @param x
	 *            linke obere Ecke des Ausschnitts
	 * @param y
	 * @param breite
	 * @param hoehe
	 * @return liefert einen Bildausschnitt des sichtbaren Bildes als
	 *         Imageobjekt
	 */
	public Picture liefereAusschnitt(int x, int y, int breite, int hoehe) {
		return malGrundlage.getCrop(x, y, breite, hoehe);
	}
	/** erm�glicht es, das Bild mit der Maus zu verschieben 
	 * 
	 * @param mmv
	 */
		public void setzeMitMausVerschiebbar(boolean mmv){
			if (mb==null && mmv ){
				mb = new Mausbewegung();
			}
			if (mb!=null && !mmv){
				mb.entferneListener();
				mb=null;
			}
		}
		
		public boolean istMitMausVerschiebbar(){		
				return mb!=null;		
		}
		
		private Mausbewegung mb;
		class Mausbewegung {
			private boolean unten = false;
			private double aktx;
			private double akty;
			private double altx = 0;
			private double alty = 0;
			private java.awt.Point mp;
			MouseAdapter madapt1,madapt2; 
			Mausbewegung() {
				madapt1 = new MouseAdapter() {
					public void mousePressed(MouseEvent e) {
						if ( !e.isMetaDown()) {
							unten = true;
							mp = Bild.this.meineKomponente.getParent().getLocationOnScreen();
							altx =  e.getXOnScreen()-mp.x;
							alty =  e.getYOnScreen()-mp.y;
						}
					}

					public void mouseReleased(MouseEvent e) {
							if ( !e.isMetaDown()) {
							unten = false;
						}
					}
				};
				madapt2 = new MouseAdapter() {
					public void mouseDragged(MouseEvent e) {
					if (unten){
							mp = Bild.this.meineKomponente.getParent().getLocationOnScreen();
							aktx =  e.getXOnScreen()-mp.x;
							akty =  e.getYOnScreen()-mp.y;
							if (altx != aktx || alty != akty) {
								double neux = Bild.this.hPosition()
										+ (aktx - altx);
								double neuy = Bild.this.vPosition()
										+ (akty - alty);
								Bild.this.setzePosition(neux, neuy);
								altx = aktx;
								alty = akty;
							}
						}
					}	
				};
				Bild.this.meineKomponente.addMouseListener(madapt1);
				Bild.this.meineKomponente.addMouseMotionListener(madapt2);
			}

			public void entferneListener(){
				Bild.this.meineKomponente.removeMouseListener(madapt1);
				Bild.this.meineKomponente.removeMouseMotionListener(madapt2);
			}
		}

}
