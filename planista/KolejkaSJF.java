package planista;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 * Klasa reprezentująca kolejkę oraz implementująca wykonywaną na niej strategię SJF.
 * Podklasa klasy Kolejka.
 *
 * @author Magdalena Augustyńska
 * @version 2018.0516
 */
public class KolejkaSJF extends Kolejka {

    public KolejkaSJF() {
        super();
    }

    public void przeprowadź() {
        TreeSet<Proces> obecne = new TreeSet<Proces>(Comparator.comparing
                (Proces::getZapotrzebowanie).thenComparing(Proces::getId));
        Collections.sort(kolejka, Comparator.comparing(Proces::getMomentPojawieniaSię));

        int czas = 0;
        int size = kolejka.size();
        double średniCzasObrotu = 0;
        double średniCzasOczekiwania = 0;

        System.out.println("Strategia: SJF");

        dodajProcesy(czas, obecne);
        while (!obecne.isEmpty() || !kolejka.isEmpty()) {
            if (!obecne.isEmpty()) {
                Proces aktualny = obecne.pollFirst();
                if (czas < aktualny.getMomentPojawieniaSię())
                    czas = aktualny.getMomentPojawieniaSię() + aktualny.getZapotrzebowanie();
                else czas += aktualny.getZapotrzebowanie();
                średniCzasObrotu += czas - aktualny.getMomentPojawieniaSię();
                średniCzasOczekiwania += czas - aktualny.getMomentPojawieniaSię() - aktualny
                        .getZapotrzebowanie();
                aktualny.drukuj(czas);
            } else {
                czas = kolejka.getFirst().getMomentPojawieniaSię();
            }
            dodajProcesy(czas, obecne);
        }
        drukujKryteria(średniCzasObrotu, średniCzasOczekiwania, size);
    }

    /**
     * Dodaje procesy, które się pojawiły.
     * Dodaje procesy, które pojawiły się od czasu 0 do czasu @param czas i znajdują się jeszcze w
     * kolejce, do aktualnie wykonywanej kolejki.
     *
     * @param czas   - czas liczony od 0;
     * @param obecne - struktura przechowująca procesy znajdujące się w aktualnie wykonywanej
     *               kolejce.
     */
    private void dodajProcesy(int czas, TreeSet<Proces> obecne) {
        while (!kolejka.isEmpty() && kolejka.getFirst().getMomentPojawieniaSię() <= czas) {
            obecne.add(kolejka.removeFirst());
        }
    }
}
