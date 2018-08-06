package planista;

import java.text.DecimalFormat;

/**
 * Klasa reprezentująca pojedynczy proces.
 *
 * @author Magdalena Augustyńska
 * @version 2018.0516
 */
public class Proces {
    /**
     * Identyfikator procesu.
     */
    private int id;
    /**
     * Zapotrzebowanie zadania na procesor
     * w momencie pojawienia się w kolejce.
     */
    private int zapotrzebowanie;
    /**
     * Moment pojawienia się zadania w kolejce (liczony od 0).
     */
    private int momentPojawieniaSię;
    /**
     * Zapotrzebowanie zadania na procesor aktualizowane na bieżąco.
     */
    private double pozostałeZapotrzebowanie;

    /**
     * Tworzy obiekt klasy Proces.
     *
     * @param id                  - identyfikator procesu;
     * @param momentPojawieniaSię - moment pojawienia się zadania w kolejce;
     * @param zapotrzebowanie     - zapotrzebowanie zadania na procesor
     *                            w momencie pojawienia się w kolejce.
     */
    public Proces(int id, int momentPojawieniaSię, int zapotrzebowanie) {
        this.id = id;
        this.zapotrzebowanie = zapotrzebowanie;
        this.momentPojawieniaSię = momentPojawieniaSię;
        pozostałeZapotrzebowanie = zapotrzebowanie;
    }

    /**
     * Tworzy obiekt klasy Proces.
     * Tworzy głęboką kopię obiektu klasy Proces @param p.
     *
     * @param p - obiekt klasy Proces.
     */
    public Proces(Proces p) {
        this.id = p.id;
        this.zapotrzebowanie = p.zapotrzebowanie;
        this.momentPojawieniaSię = p.momentPojawieniaSię;
        this.pozostałeZapotrzebowanie = p.pozostałeZapotrzebowanie;
    }

    public int getId() {
        return id;
    }

    public double getPozostaleZapotrzebowanie() {
        return pozostałeZapotrzebowanie;
    }

    public int getZapotrzebowanie() {
        return zapotrzebowanie;
    }

    public int getMomentPojawieniaSię() {
        return momentPojawieniaSię;
    }

    /**
     * Wykonuje odcinek pracy na obiekcie klasy Proces.
     *
     * @param q - ilość przydzielonych jednostek czasu.
     * @return Pozostałe zapotrzebowanie procesu, jeśli jest ono mniejsze niż ilość
     * przydzielonych jednostek q.
     * Wartość q, w przeciwnym przypadku.
     */
    public double wykonajOdcinek(double q) {
        double wykonanyOdcinek;
        if (q <= pozostałeZapotrzebowanie) {
            pozostałeZapotrzebowanie -= q;
            wykonanyOdcinek = q;
        } else {
            wykonanyOdcinek = pozostałeZapotrzebowanie;
            pozostałeZapotrzebowanie = 0;
        }
        return wykonanyOdcinek;
    }

    /**
     * Sprawdza, czy proces został wykonany w całości.
     *
     * @return Wartość true, jeśli proces został wykonany.
     * Wartość false, w przeciwnym przypadku.
     */
    public boolean wykonany() {
        return pozostałeZapotrzebowanie == 0;
    }

    /**
     * Drukuje dane o procesie.
     * Drukuje ciąg trójek liczb oddzielonych spacjami, ujętych w nawiasy [...].
     * Dwie pierwsze liczby są liczbami całkowitymi, a trzecia wypisaną z dokładnością do dwóch
     * miejsc po kropce liczbą dziesietną.
     * Kolejne liczby oznaczają: identyfikator procesu, moment przybycia procesu do kolejki
     * zadań, moment zakończenia wykonywania się procesu.
     *
     * @param momentZakończenia - moment zakończenia wykonywania się procesu wg danej strategii.
     */
    public void drukuj(double momentZakończenia) {
        DecimalFormat df = new DecimalFormat("#.00");
        System.out.print("[" + id + " " + momentPojawieniaSię + " " + df.format
                (Math.round(momentZakończenia * 100.0) / 100.0).replaceAll(",", ".") + "]");
    }
}
