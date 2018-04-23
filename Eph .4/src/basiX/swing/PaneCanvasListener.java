
package basiX.swing;

import java.util.*;
/**
 * CanvasLauscher 
 * Anmeldung/Abmeldung �ber setzeCanvasLauscher/entferneCanvasLauscher
 * bei Canvas-Objekten
 */
public interface PaneCanvasListener extends EventListener {
    /**
     * Antwort auf canvasGezeichnet-Ereignis f�r CanvasLauscher.
     * Das canvasGezeichnet-Ereignis wird ausgel�st, wenn der Canvas durch
     * Gr��en�nderung, in den Vorder- oder Hintergrund-Stellen oder bei �nderung der Sichtbarkeit neu dargestellt werden musste
     */
    public void paneCanvasRepainted(PaneCanvas l);
}
