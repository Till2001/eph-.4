package basiX;

import java.util.*;
/**
 * TextBereichLauscher 
 * Anmeldung/Abmeldung �ber setzeTextBereichLauscher/entferneTextBereichLauscher
 * bei TextBereich-Objekten
 */
public interface TextBereichLauscher extends EventListener {
    /**
     * Antwort auf TextVeraenderung-Ereignis f�r TextBereichLauscher.
     * Das TextVeraenderung-Ereignis wird ausgel�st, wenn der Text im Fenster durch den Benutzer ver�ndert wurde
     */
    public void bearbeiteTextBereichVeraenderung(Komponente t);
}
