package planista;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 * Klasa reprezentująca kolejkę oraz implementująca wykonywaną na niej strategię SRT.
 * Podklasa klasy Kolejka.
 *
 * @author Magdalena Augustyńska
 * @version 2018.0516
 */
public class KolejkaSRT extends Kolejka {

    public KolejkaSRT() {
        super();
    }

    public void przeprowadź() {
        Collections.sort(kolejka, Comparator.comparing(Proces::getMomentPojawieniaSię));

        int size = kolejka.size();
        int czas = 0;
        double średniCzasObrotu = 0;
        double średniCzasOczekiwania = 0;
        TreeSet<Proces> obecne = new TreeSet<Proces>(Comparator.comparingDouble
                (Proces::getPozostaleZapotrzebowanie).thenComparing(Proces::getId));

        System.out.println("Strategia: SRT");

        dodajProcesy(czas, obecne);
        Proces aktualny = obecne.pollFirst();
        while (aktualny != null || !kolejka.isEmpty()) {
            if (aktualny == null) {
                czas = kolejka.getFirst().getMomentPojawieniaSię();
            } else {
                czas++;
                aktualny.wykonajOdcinek(1);
                if (aktualny.wykonany()) {
                    średniCzasObrotu += czas - aktualny.getMomentPojawieniaSię();
                    średniCzasOczekiwania += czas - aktualny.getMomentPojawieniaSię() - aktualny
                            .getZapotrzebowanie();
                    aktualny.drukuj(czas);
                    aktualny = null;
                }
            }
            dodajProcesy(czas, obecne);
            if (!obecne.isEmpty() && (aktualny == null || (obecne.first()
                    .getPozostaleZapotrzebowanie() < aktualny
                    .getPozostaleZapotrzebowanie()))) {
                if (aktualny != null) obecne.add(aktualny);
                aktualny = obecne.pollFirst();
            }
        }
        drukujKryteria(średniCzasObrotu, średniCzasOczekiwania, size);
    }

    /**
     * Dodaje procesy, które się pojawiły.
     * Dodaje procesy, które pojawiły się w czasie @param czas i znajdują się jeszcze w
     * kolejce, do aktualnie wykonywanej kolejki.
     *
     * @param czas   - czas liczony od 0;
     * @param obecne - struktura przechowująca procesy znajdujące się w aktualnie wykonywanej
     *               kolejce.
     */
    private void dodajProcesy(int czas, TreeSet<Proces> aktualne) {
        while (!kolejka.isEmpty() && kolejka.getFirst().getMomentPojawieniaSię() == czas) {
            aktualne.add(kolejka.removeFirst());
        }
    }
}
