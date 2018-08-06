package planista;

import java.text.DecimalFormat;
import java.util.LinkedList;

/**
 * Klasa reprezentująca kolejkę procesów.
 * Abstrakcyjna klasa przechowująca strukturę zawierającą procesy.
 * Zawiera deklarację metody przeprowadzającej strategię na kolejce.
 *
 * @author Magdalena Augustyńska
 * @version 2018.0516
 */
public abstract class Kolejka {
    /**
     * Struktura przechowująca obiekty klasy Proces.
     * Przechowuje wszystkie procesy, które zostały przekazane do wykonania na wejściu.
     */
    private LinkedList<Proces> kolejka;

    /**
     * Tworzy nowy obiekt klasy Kolejka zawierający pustą listę jako atrybut kolejka.
     */
    public Kolejka() {
        this.kolejka = new LinkedList<Proces>();
    }

    /**
     * Dodaje proces do kolejki.
     *
     * @param p - obiekt klasy Proces, który ma być dodany na końcu listy reprezentującej atrybut
     *          kolejka.
     */
    public void dodaj(Proces p) {
        kolejka.add(p);
    }

    /**
     * Deklaracja abstrakcyjnej metody przeprowadzającej wykonanie procesów zawartych w kolejce.
     * Przeprowadza symulację wykonywania się procesów w kolejce.
     * Drukuje na wyjście standardowe nazwę strategii oraz ciąg trójek liczb zawierających dane o
     * procesach.
     * Implementacja metody w podklasach.
     */
    public abstract void przeprowadź();

    /**
     * Wypisuje na wyjście standardowe kryteria opisujące wykonanie danej strategii szeregowania.
     *
     * @param średniCzasObrotu      - średni czas liczony od momentu pojawienia się zadania w
     *                              kolejce zadań oczekujących do momentu zakończenia jego
     *                              wykonywania;
     * @param średniCzasOczekiwania - średni czas przebywania zadania w kolejce zadań oczekujących;
     * @param size                  - rozmiar kolejki przekazanej do wykonania.
     */
    public void drukujKryteria(double średniCzasObrotu, double średniCzasOczekiwania, int size) {
        System.out.println();
        DecimalFormat df = new DecimalFormat("0.00");
        System.out.println("Średni czas obrotu: " + df.format(Math.round(średniCzasObrotu / size
                * 100.0) / 100.0).replaceAll
                (",", "."));
        System.out.println("Średni czas oczekiwania: " + df.format(Math.round
                (średniCzasOczekiwania / size * 100.0) / 100.0)
                .replaceAll(",", "."));
        System.out.println();
    }
}
