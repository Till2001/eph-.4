package basiX;

public interface ReglerLauscher {
	/**
     * Antwort auf ReglerBewegung-Ereignis f�r ReglerLauscher.
     * Das GleiterBewegung-Ereignis wird ausgel�st, wenn der eingestellte Reglerwert mit Hilfe der Maus ver�ndert wurde
     */
    public void bearbeiteReglerBewegung(Regler r);
}
