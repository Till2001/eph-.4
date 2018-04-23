
package basiX;

import java.util.*;
/**
 * KnopfLauscher 
 * Anmeldung/Abmeldung �ber setzeKnopfLauscher/entferneKnopfLauscher
 * bei Knopf-Objekten
 */
public interface KnopfLauscher extends EventListener {
    /**
     * Antwort auf KnopfDruck-Ereignis f�r KnopfLauscher.
     * Das KnopfDruck-Ereignis wird durch einen Mausklick auf einen Knopf ausgel�st
     */
    public void bearbeiteKnopfDruck(Knopf k);
}
