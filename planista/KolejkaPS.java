package planista;

import java.util.Collections;
import java.util.Comparator;
import java.util.TreeSet;

/**
 * Klasa reprezentująca kolejkę oraz implementująca wykonywaną na niej strategię PS.
 * Podklasa klasy Kolejka.
 *
 * @author Magdalena Augustyńska
 * @version 2018.0516
 */
public class KolejkaPS extends Kolejka {

    public KolejkaPS() {
        super();
    }

    public void przeprowadź() {
        TreeSet<Proces> obecne = new TreeSet<Proces>(Comparator.comparing
                (Proces::getPozostaleZapotrzebowanie).thenComparing(Proces::getId));
        Collections.sort(kolejka, Comparator.comparing(Proces::getMomentPojawieniaSię));

        int size = kolejka.size();
        double średniCzasObrotu = 0;
        double czas = 0;
        double wykonuje;

        System.out.println("Strategia: PS");

        dodajProcesy(czas, obecne);
        while (!obecne.isEmpty() || !kolejka.isEmpty()) {
            wykonuje = czasDziałania(czas, obecne);
            czas += wykonuje;
            if (!obecne.isEmpty()) {
                for (Proces p : obecne) {
                    p.wykonajOdcinek(wykonuje / obecne.size());
                    if (p.wykonany()) {
                        średniCzasObrotu += czas - p.getMomentPojawieniaSię();
                        p.drukuj(czas);
                    }
                }
                obecne.removeIf((Proces p) -> {
                    return p.wykonany();
                });
            }
            if (!kolejka.isEmpty() || !obecne.isEmpty())
                dodajProcesy(czas, obecne);
        }
        drukujKryteria(średniCzasObrotu, 0, size);
    }

    /**
     * Dodaje procesy, które się pojawiły.
     * Dodaje procesy, które pojawiły się od czasu 0 do czasu @param czas, i znajdują się jeszcze w
     * kolejce, do aktualnie wykonywanej kolejki.
     *
     * @param czas   - czas liczony od 0;
     * @param obecne - struktura przechowująca procesy znajdujące się w aktualnie wykonywanej
     *               kolejce.
     */
    private void dodajProcesy(double czas, TreeSet<Proces> obecne) {
        while (!kolejka.isEmpty() && kolejka.getFirst().getMomentPojawieniaSię() <= czas) {
            obecne.add(kolejka.removeFirst());
        }
    }

    /**
     * Wyznacza czas do pierwszego pojawienia się w kolejce nowego procesu lub zakończenia
     * wykonywania się aktualnie wykonywanego procesu.
     *
     * @param czas   - aktualny czas (liczony od 0);
     * @param obecne - struktura przechowująca procesy, które znajdują się w aktualnie
     *               wykonywanej kolejce, czyli takie, które już się pojawiły i jeszcze nie
     *               wykonały;
     * @return Liczba jednostek czasu zgodna z opisem.
     */
    private double czasDziałania(double czas, TreeSet<Proces> obecne) {
        if (obecne.isEmpty()) return kolejka.getFirst().getMomentPojawieniaSię() - czas;
        if (!kolejka.isEmpty())
            return Math.min((kolejka.getFirst().getMomentPojawieniaSię() - czas),
                    obecne.first().getPozostaleZapotrzebowanie() * obecne.size());
        return obecne.first().getPozostaleZapotrzebowanie() * obecne.size();
    }
}

