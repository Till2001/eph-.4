package basiX.menu;

import java.util.*;
/**
 * MenuePunktLauscher 
 * Anmeldung/Abmeldung �ber setzeMenuePunktLauscher/entferneMenuePunktLauscher
 * bei MenuePunkt-Objekten
 */
public interface MenuePunktLauscher extends EventListener {
    /**
     * Antwort auf MenuepunktAuswahl-Ereignis f�r MenuePunktLauscher.
     * Das MenuepunktAuswahl-Ereignis wird ausgel�st, wenn der Men�punkt mp gew�hlt wurde
     */
    public void bearbeiteMenuepunktAuswahl(Menuepunkt mp);
}
