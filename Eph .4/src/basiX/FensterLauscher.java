

package basiX;
import java.util.*;
/**
 * FensterLauscher 
 * Anmeldung/Abmeldung �ber setzeFensterLauscher/entferneFensterLauscher
 * bei Fenster-Objekten
 */
public interface FensterLauscher extends EventListener{
    public void bearbeiteFensterWurdeGeschlossen(Fenster b);
} 