package planista;

import java.util.Collections;
import java.util.Comparator;

/**
 * Klasa reprezentująca kolejkę oraz implementująca wykonywaną na niej strategię FCFS.
 * Podklasa klasy Kolejka.
 *
 * @author Magdalena Augustyńska
 * @version 2018.0516
 */
public class KolejkaFCFS extends Kolejka {

    public KolejkaFCFS() {
        super();
    }

    public void przeprowadź() {
        Collections.sort(kolejka, Comparator.comparing(Proces::getMomentPojawieniaSię)
                .thenComparing(Proces::getId));

        int czas = 0;
        int size = kolejka.size();
        double średniCzasObrotu = 0;
        double średniCzasOczekiwania = 0;

        System.out.println("Strategia: FCFS");

        while (!kolejka.isEmpty()) {
            Proces aktualny = kolejka.removeFirst();
            if (czas < aktualny.getMomentPojawieniaSię())
                czas = aktualny.getMomentPojawieniaSię() + aktualny.getZapotrzebowanie();
            else czas += aktualny.getZapotrzebowanie();
            średniCzasObrotu += czas - aktualny.getMomentPojawieniaSię();
            średniCzasOczekiwania += czas - aktualny.getMomentPojawieniaSię() - aktualny
                    .getZapotrzebowanie();
            aktualny.drukuj(czas);
        }
        drukujKryteria(średniCzasObrotu, średniCzasOczekiwania, size);
    }
}
