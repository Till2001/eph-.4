/**
 * WahlBoxGruppeLauscher 
 * Anmeldung/Abmeldung �ber setzeWahlBoxGruppeLauscher/entferneWahlBoxGruppeLauscher
 * bei WahlBoxGruppe-Objekten
 */package basiX;

import java.util.*;
/**
 * WahlBoxGruppeLauscher 
 * Anmeldung/Abmeldung �ber setzeWahlBoxGruppeLauscher/entferneWahlBoxGruppeLauscher
 * bei WahlBoxGruppe-Objekten
 */
public interface WahlBoxGruppeLauscher extends EventListener {
    /**
     * Antwort auf WahlBoxAuswahl-Ereignis f�r WahlBoxGruppeLauscher.
     * Das WahlBoxAuswahl-Ereignis wird ausgel�st, wenn der Zustand einer
     * der zur Gruppe k geh�renden WahlBoxen ge�ndert wurde.
     */
    public void bearbeiteWahlBoxAuswahl(Object k);
//    /**
//     * liefert die neuGewaehlteBox
//     * @param k
//     * @param gewaehlt
//     */
//    public void bearbeiteNeuwahl(Object k, WahlBox gewaehlt);
}
