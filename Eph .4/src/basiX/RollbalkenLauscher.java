package basiX;

import java.util.*;
/**
 * RollbalkenLauscher 
 * Anmeldung/Abmeldung �ber setzeRollbalkenLauscher/entferneRollbalkenLauscher
 * bei Rollbalken-Objekten
 */
public interface RollbalkenLauscher extends EventListener {
    /**
     * Antwort auf RollbalkenBewegung-Ereignis f�r Rollbalkenlauscher.
     * Das RollbalkenBewegung-Ereignis wird ausgel�st, wenn der eingestellte Rollbalkenwert mit Hilfe der Maus ver�ndert wurde
     */
    public void bearbeiteRollbalkenBewegung(Rollbalken r);
}
