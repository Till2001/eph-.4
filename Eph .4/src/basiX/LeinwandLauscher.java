
package basiX;

import java.util.*;
/**
 * LeinwandLauscher 
 * Anmeldung/Abmeldung �ber setzeLeinwandLauscher/entferneLeinwandLauscher
 * bei Leinwand-Objekten
 */
public interface LeinwandLauscher extends EventListener {
    /**
     * Antwort auf leinwandGezeichnet-Ereignis f�r LeinwandLauscher.
     * Das leinwandGezeichnet-Ereignis wird ausgel�st, wenn die Leinwand durch
     * Gr��en�nderung, in den Vorder- oder Hintergrund-Stellen oder bei �nderung der Sichtbarkeit neu dargestellt werden musste
     */
    public void leinwandWurdeGezeichnet(LeinwandIF l);
}
