package basiX;

import java.util.EventListener;




	/**
	 * KomponentenLauscher 
	 * Anmeldung/Abmeldung �ber setzeKomponentenLauscher/entferneKomponentenLauscher
	 * bei Komponenten
	 */
	public interface KomponentenLauscher extends EventListener {

	    /**
	     * Antwort auf KomponentenVeraenderung-Ereignis f�r KomponentenLauscher. 
	     * Das KomponentenLauscher-Ereignis wird ausgel�st, wenn eine Komponente
	     * ver�ndert wurde
	     */
	    
		public void bearbeiteKomponentenVeraenderung(Komponente komponente);
}
