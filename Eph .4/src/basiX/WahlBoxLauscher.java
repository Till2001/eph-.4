package basiX;

import java.util.*;
/**
 * WahlBoxLauscher 
 * Anmeldung/Abmeldung �ber setzeWahlBoxLauscher/entferneWahlBoxLauscher
 * bei WahlBox-Objekten
 */
public interface WahlBoxLauscher extends EventListener {
    /**
     * Antwort auf WahlBoxAenderung-Ereignis f�r WahlBoxLauscher.
     * Das WahlBoxAenderung-Ereignis wird ausgel�st, wenn der Zustand der WahlBox ge�ndert wurde.
     */
    public void bearbeiteWahlBoxAenderung(WahlBox k);
}
