package planista;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Klasa reprezentująca program Planista.
 * Pozwala na przeprowadzenie eksperymentów polegających na porównaniu efektywności wybranych
 * strategii zgodnie z określonym kryterium.
 *
 * @author Magdalena Augustyńska
 * @version 2018.0516
 */
public class Planista {
    /**
     * Obiekt klasy Scanner.
     * Obiekt używany przez parser do czytania wejścia.
     * Inicjalizowany w metodzie main().
     */
    private static Scanner wejście;

    /**
     * Czyta dane.
     * Czyta dane, dla których mają być wykonane eksperymenty.
     * W przypadku danych niezgodnych ze specyfikacją zadania, metoda wypisuje na wyjście
     * standardowe komunikat o błędzie i kończy działanie programu.
     *
     * @return Tablica obiektów klasy Kolejka, na których mają zostać przeprowadzone eksperymenty.
     */
    public static Kolejka[] parsuj() {
        ArrayList<Proces> procesy = new ArrayList<Proces>();
        int nrWiersza = 1;
        int liczbaProcesów, liczbaWariantów;

        String[] wiersz = wejście.nextLine().split("\\s+");
        liczbaProcesów = czyBłąd(wiersz, nrWiersza, 1)[0];
        nrWiersza++;
        while (wejście.hasNextLine() && nrWiersza - 2 < liczbaProcesów) {
            wiersz = wejście.nextLine().split("\\s+");
            int[] proces = czyBłąd(wiersz, nrWiersza, 2);
            procesy.add(new Proces(nrWiersza - 1, proces[0], proces[1]));
            nrWiersza++;
        }
        if (nrWiersza - 2 != liczbaProcesów) {
            brakDanych(nrWiersza);
        }

        wiersz = wejście.nextLine().split("\\s+");
        int[] liczba = czyBłąd(wiersz, nrWiersza, 1);
        liczbaWariantów = liczba[0];
        nrWiersza++;

        wiersz = wejście.nextLine().split("\\s+");
        int[] warianty = czyBłąd(wiersz, nrWiersza, liczbaWariantów);
        Kolejka[] kolejkiRR = new Kolejka[warianty.length];
        for (int i = 0; i < warianty.length; i++) {
            kolejkiRR[i] = new KolejkaRR(warianty[i]);
        }
        if (wejście.hasNextLine()) {
            zaDużoDanych(++nrWiersza);
        }
        wejście.close();

        Kolejka[] kolejki = new Kolejka[4 + warianty.length];
        kolejki[0] = new KolejkaFCFS();
        kolejki[1] = new KolejkaSJF();
        kolejki[2] = new KolejkaSRT();
        kolejki[3] = new KolejkaPS();
        for (int i = 4; i < kolejki.length; i++) {
            kolejki[i] = kolejkiRR[i - 4];
        }
        for (Proces p : procesy) {
            for (Kolejka k : kolejki) {
                k.dodaj(new Proces(p));
            }
        }
        return kolejki;
    }

    /**
     * Kończy działanie programu.
     * Zamyka obiekt klasy Scanner wskazywany przez atrybut wejście oraz kończy
     * działanie programu nie zwracając błędu.
     */
    public static void zakończ() {
        wejście.close();
        System.exit(0);
    }

    /**
     * Czyta wiersz danych.
     * Czyta wiersz danych przekazanych jako tablicę wszystkich niepustych napisów, które
     * wystąpiły w danym wierszu.
     * Jeśli liczba napisów jest różna od oczekiwanej lub istnieje napis różny od całkowitej
     * liczby dodatniej, wypisuje komunikat o błędzie danych i kończy działanie programu.
     *
     * @param wiersz            - tablica napisów w wierszu;
     * @param nrWiersza         - numer przetwarzanego wiersza;
     * @param oczekiwanaDługość - oczekiwana liczba parametrów zawartych w wierszu wg
     *                          specyfikacji zadania.
     * @return Tablica danych - nieujemnych liczb typu int.
     */
    public static int[] czyBłąd(String[] wiersz, int nrWiersza, int oczekiwanaDługość) {
        int[] liczby = new int[oczekiwanaDługość];
        Arrays.fill(liczby, -1);
        if (wiersz.length < oczekiwanaDługość) {
            zaMałoDanych(nrWiersza);
        } else if (wiersz.length > oczekiwanaDługość) {
            zaDużoDanych(nrWiersza);
        } else {
            try {
                liczby = Arrays.stream(wiersz).mapToInt(Integer::parseInt).toArray();
            } catch (NumberFormatException e) {
                nieprawidłoweDane(nrWiersza);
            }
        }
        for (int i : liczby) {
            if (i < 0) nieprawidłoweDane(nrWiersza);
        }
        return liczby;
    }

    /**
     * Drukuje na wyjście standardowe komunikat o braku danych we wskazanym wierszu oraz kończy
     * działanie programu.
     *
     * @param nrWiersza - numer wiersza, który jest przetwarzany.
     */
    public static void brakDanych(int nrWiersza) {
        System.out.println("Błąd w wierszu " + nrWiersza + " : brak danych.");
        zakończ();
    }

    /**
     * Drukuje na wyjście standardowe komunikat o zbyt małej ilości danych we wskazanym wierszu
     * oraz kończy działanie programu.
     *
     * @param nrWiersza - numer wiersza, który jest przetwarzany.
     */
    public static void zaMałoDanych(int nrWiersza) {
        System.out.println("Błąd w wierszu " + nrWiersza + " : za mało danych.");
        zakończ();
    }

    /**
     * Drukuje na wyjście standardowe komunikat o zbyt dużej ilości danych we wskazanym wierszu
     * oraz kończy działanie programu.
     *
     * @param nrWiersza - numer wiersza, który jest przetwarzany.
     */
    public static void zaDużoDanych(int nrWiersza) {
        System.out.println("Błąd w wierszu " + nrWiersza + " : za dużo danych.");
        zakończ();
    }

    /**
     * Drukuje na wyjście standardowe komunikat o nieprawidłowych danych we wskazanym wierszu
     * oraz kończy działanie programu.
     *
     * @param nrWiersza - numer wiersza, który jest przetwarzany.
     */
    public static void nieprawidłoweDane(int nrWiersza) {
        System.out.println("Błąd w wierszu " + nrWiersza + " : nieprawidłowe dane.");
        zakończ();
    }

    /**
     * Przeprowadza eksperymenty polegające na porównaniu efektywności wybranych strategii
     * zgodnie z określonym kryterium.
     * Dane do eksperymentu są wczytywane z pliku tekstowego, którego nazwa jest parametrem
     * wywołania programu albo ze standardowego wejścia, o ile program został wywołany bez
     * parametru.
     * Po wczytaniu danych wysyła do każdej z kolejek w tablicy zwróconej przez metodę parsuj()
     * komunikat o wywołaniu metody przeprowadź().
     *
     * @param args - tablica parametrów wywołania programu. Może zawierać parametr wskazujący
     *             nazwę pliku tekstowego z danymi.
     */
    public static void main(String args[]) {
        if (args.length == 1) {
            try {
                File f = new File(args[0]);
                wejście = new Scanner(f);
            } catch (Exception e) {
                System.out.println("Plik z danymi nie jest dostępny.");
                return;
            }
        } else {
            wejście = new Scanner(System.in);
        }

        Kolejka[] kolejki = parsuj();
        for (Kolejka k : kolejki) {
            k.przeprowadź();
        }
    }
}
