 package basiX;

import java.util.*;
/**
 * ListAuswahlLauscher 
 * Anmeldung/Abmeldung �ber setzeListAuswahlLauscher/entferneListAuswahlLauscher
 * bei ListAuswahl-Objekten
 */
public interface ListAuswahlLauscher extends EventListener {
    /**
     * Antwort auf Auswahl-Ereignis f�r ListAuswahlLauscher. Das Auswahl-Ereignis wird ausgel�st, wenn in der Liste durch
     * Mausklick oder Return ein Listenelement ausgew�hlt wurde.
     */
    public void bearbeiteAuswahl(ListAuswahl a);
}
