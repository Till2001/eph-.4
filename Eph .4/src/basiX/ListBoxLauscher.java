package basiX;
/**
 * ListBoxLauscher 
 * Anmeldung/Abmeldung �ber setzeListBoxLauscher/entferneListBoxLauscher
 * bei ListBox-Objekten
 */
public interface ListBoxLauscher {
    /**
     * Antwort auf ListBoxAuswahl-Ereignis f�r ListBoxLauscher. Das ListBoxAuswahl-Ereignis wird ausgel�st, wenn in der Liste
     * durch Mausklick oder Return ein Listenelement ausgew�hlt wurde.
     */
    public void bearbeiteListBoxAuswahl(ListBox l);
}
