package basiX;

import java.util.EventListener;

/**
	 * PasswortFeldLauscher 
	 * Anmeldung/Abmeldung �ber setzePasswortFeldLauscher/entfernePasswortFeldLauscher
	 * bei PasswortFeld-Objekten
	 */
public interface PasswortFeldLauscher extends EventListener{
	
	
	    /**
	     * Antwort auf ReturnGedrueckt-Ereignis f�r PasswortFeldLauscher.
	     * Das ReturnGedrueckt-Ereignis wird ausgel�st, nachdem der Benutzer
	     * die Return-Taste gedr�ckt hat. Das PasswortFeld gibt den Fokus an das n�chste Oberflaechenelement ab.
	     */
	    public void bearbeiteReturnGedrueckt(PasswortFeld t);

	    /**
	     * Antwort auf TextVeraenderung-Ereignis f�r PasswortFeldLauscher. Das TextVeraenderung-Ereignis wird ausgel�st, wenn der Text
	     * in der Textzeile durch den Benutzer ver�ndert wurde
	     */
	    public void bearbeiteTextVeraenderung(PasswortFeld t);
	    /**
	     * Antwort auf FokusVerloren-Ereignis f�r PasswortFeldLauscher. Das FokusVerloren-Ereignis wird ausgel�st, wenn das PasswortFeld den Fokus abgibt.
	     */
	    public void bearbeiteFokusVerloren(PasswortFeld t);
	

}
