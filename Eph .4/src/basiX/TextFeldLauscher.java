/**
 * TextFeldLauscher 
 * Anmeldung/Abmeldung �ber setzeTextFeldLauscher/entferneTextFeldLauscher
 * bei TextFeld-Objekten
 */package basiX;

import java.util.*;
/**
 * TextFeldLauscher 
 * Anmeldung/Abmeldung �ber setzeTextFeldLauscher/entferneTextFeldLauscher
 * bei TextFeld-Objekten
 */
public interface TextFeldLauscher extends EventListener {
    /**
     * Antwort auf ReturnGedrueckt-Ereignis f�r TextFeldLauscher.
     * Das ReturnGedrueckt-Ereignis wird ausgel�st, nachdem der Benutzer
     * die Return-Taste gedr�ckt hat. Das Textfeld gibt den Fokus an das n�chste Oberflaechenelement ab.
     */
    public void bearbeiteReturnGedrueckt(TextFeld t);

    /**
     * Antwort auf TextVeraenderung-Ereignis f�r TextFeldLauscher. Das TextVeraenderung-Ereignis wird ausgel�st, wenn der Text
     * in der Textzeile durch den Benutzer ver�ndert wurde
     */
    public void bearbeiteTextVeraenderung(TextFeld t);
    /**
     * Antwort auf FokusVerloren-Ereignis f�r TextFeldLauscher. Das FokusVerloren-Ereignis wird ausgel�st, wenn das Textfeld den Fokus abgibt.
     */
    public void bearbeiteFokusVerloren(TextFeld t);
}
