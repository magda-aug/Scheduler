package planista;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * Klasa reprezentująca kolejkę oraz implementująca wykonywaną na niej strategię RR.
 * Podklasa klasy Kolejka.
 *
 * @author Magdalena Augustyńska
 * @version 2018.0516
 */
public class KolejkaRR extends Kolejka {
    private int wariant;

    public KolejkaRR(int wariant) {
        super();
        this.wariant = wariant;
    }

    public void przeprowadź() {
        Collections.sort(kolejka, Comparator.comparing(Proces::getMomentPojawieniaSię)
                .thenComparing(Proces::getId));

        LinkedList<Proces> obecne = new LinkedList<Proces>();
        int czas = 0;
        int size = kolejka.size();
        double średniCzasObrotu = 0;
        double średniCzasOczekiwania = 0;

        System.out.println("Strategia: RR-" + wariant);

        dodajProcesy(czas, obecne);
        Proces aktualny;
        while (!obecne.isEmpty() || !kolejka.isEmpty()) {
            if (obecne.isEmpty()) {
                czas = kolejka.getFirst().getMomentPojawieniaSię();
            } else {
                aktualny = obecne.removeFirst();
                czas += aktualny.wykonajOdcinek(wariant);
                if (aktualny.wykonany()) {
                    aktualny.drukuj(czas);
                    średniCzasObrotu += czas - aktualny.getMomentPojawieniaSię();
                    średniCzasOczekiwania += czas - aktualny.getMomentPojawieniaSię() - aktualny
                            .getZapotrzebowanie();
                } else {
                    obecne.add(aktualny);
                }
            }
            dodajProcesy(czas, obecne);
        }
        drukujKryteria(średniCzasObrotu, średniCzasOczekiwania, size);
    }

    /**
     * Dodaje procesy, które się pojawiły.
     * Dodaje procesy, które pojawiły się od czasu 0 do czasu (@param czas + wariant -1) i
     * znajdują się jeszcze w kolejce, do aktualnie wykonywanej kolejki. To znaczy, dodaje
     * procesy okresowo, po upłynięciu przedziału czasu wskazywanego przez atrybut wariant.
     *
     * @param czas   - czas liczony od 0;
     * @param obecne - struktura przechowująca procesy znajdujące się w aktualnie wykonywanej
     *               kolejce.
     */
    private void dodajProcesy(int czas, LinkedList<Proces> obecne) {
        while (!kolejka.isEmpty() && kolejka.getFirst().getMomentPojawieniaSię() < czas + wariant) {
            obecne.add(kolejka.removeFirst());
        }
    }
}
