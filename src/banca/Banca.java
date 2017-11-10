package banca;

import java.io.BufferedReader; //Input
import java.io.IOException;
import java.io.InputStreamReader; //Input
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom; //RNG per il saldo

public class Banca {

    /*WIP
    public void tryInputUntill({
            do {
                try {
                    switch (tastiera.readLine().toLowerCase()) { //il .toLowerCase() forza il lowercase dell'input evitando molti problemi.
                        case "si":
                            System.out.println("Inserisci l'importo da versare:");
                            //importo=Double.valueOf(tastiera.readLine());
                            try {
                                done = conto2.setImporto(Double.valueOf(tastiera.readLine())); //Questo metodo restituisce un booleano che serve a capire se l'operazione è stata effettuata senza problemi
                            } catch (IOException | NumberFormatException e) {
                                System.out.println("Errore nell'immisione dell'importo, se stai provando ad inserire delle cifre decimali usa il punto al posto della virgola");
                            }
                        case "no":
                            done = true;
                            break;
                        default:
                            done=false;
                            throw new IOException();
                    }
                } catch (IOException e) {
                    System.out.println("Errore di immisione, ");
                    done=false;
                }
            }while (!done);
    }
    WIP*/
    private static String reqNConto(int iOut) throws IOException { //transform in setNConto
        Boolean done = null;
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader tastiera = new BufferedReader(input);
        String nConto = null;
        System.out.println("Inserisci il numero identificativo del " + iOut + "° conto");
        do {
            try {
                nConto = tastiera.readLine();
                if (nConto.length() < 7 || nConto.length() > 30) {
                    throw new IOException();
                } else {
                    done = true;
                }
            } catch (IOException e) {
                System.out.println("Errore nell'inserimento del numero di conto, riprova facendo attenzione ad inserire un numero compreso tra le 7 e le 30 cifre");
                done = false;
            }
        } while (!done);
        return nConto;
    }

    private static boolean reqRngTipoMovimento(int iOut) {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader tastiera = new BufferedReader(input);
        String rng;
        boolean done;
        Boolean boolRngTipoMovimento = null;
        System.out.println("Vuoi inserire la tipologia dei movimenti per il " + iOut + "° conto manualmente? Scrivi Si o No");
        do {
            try {
                rng = tastiera.readLine();
                switch (rng.toLowerCase()) {
                    case "si":
                        boolRngTipoMovimento = false;
                        done = true;
                        break;
                    case "no":
                        boolRngTipoMovimento = true;
                        done = true;
                        break;
                    default:
                        throw new IOException();
                }
            } catch (IOException e) {
                System.out.println("Errore nell'inserimento, scrivi si o no");
                done = false;
            }
        } while (!done);
        return boolRngTipoMovimento;
    }

    private static boolean reqTipoMovimento() {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader tastiera = new BufferedReader(input);
        String preORVer;
        boolean done;
        Boolean boolPreORVer = null;
        do {
            try {
                System.out.println("Che operazione vuoi effettuare? Scrivi versamento o prelievo");
                preORVer = tastiera.readLine().toLowerCase();
                switch (preORVer.toLowerCase()) {
                    case "prelievo":
                        boolPreORVer = true;
                        done = true;
                        break;
                    case "versamento":
                        boolPreORVer = false;
                        done = true;
                        break;
                    default:
                        throw new IOException();
                }
            } catch (IOException | NullPointerException e) {
                System.out.println("Errore nell'inserimento, scrivi versamento o prelievo");
                done = false;
            }
        } while (!done); //Era la stessa condizione dell'if all'interno del try catch, ho deciso di creare un booleano al posto di ripetere la stessa condizione
        return boolPreORVer;
    }

    private static boolean reqImporto(Conto contoProv, boolean boolPreORVer) {
        boolean done;
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader tastiera = new BufferedReader(input);
        if (!boolPreORVer) {
            System.out.println("Inserisci l'importo da versare:");
        } else {
            System.out.println("Inserisci l'importo da prelevare");
        }
        do {
            try {
                done = contoProv.setImporto(Double.valueOf(tastiera.readLine()),boolPreORVer); //Questo metodo restituisce un booleano che serve a capire se l'operazione è stata effettuata senza problemi
                if (!done) {
                    throw new IOException();
                }
            } catch (IOException | NumberFormatException e) {
                System.out.println("Errore nell'immissione dell'importo, se stai provando ad inserire delle cifre decimali usa il punto al posto della virgola");
                done = false;
            }
        } while (!done);
        return done;
    }

    private static boolean reqPrimoVer(Conto contoProv, int i) {
        boolean done;
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader tastiera = new BufferedReader(input);
        if (i == 1) {
            System.out.println("Creazione del primo conto riuscita, vuoi effettuare un versamento iniziale? Digita Si o No");
        } else {
            System.out.println("Creazione del secondo conto riuscita, vuoi effettuare un versamento iniziale? Digita Si o No");
        }
        do {
            try {
                switch (tastiera.readLine().toLowerCase()) { //il .toLowerCase() forza il lowercase dell'input evitando molti problemi.
                    case "si":
                        done = reqImporto(contoProv, false);
                        contoProv.movimento(false);
                        break;
                    case "no":
                        done = true;
                        break;
                    default:
                        throw new IOException();
                }
            } catch (IOException e) {
                System.out.println("Errore di immissione, digita Si o No");
                done = false;
            }
        } while (!done);
        return done;
    }

    public static void main(String[] args) throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader tastiera = new BufferedReader(input);
        Conto conto1 = null; //pre-dichiarazione necessaria per abilitare la creazione di un oggetto in un controllo if.
        Conto conto2 = null;
        String nConto;
        double importo = 0;
        String data = null;
        Double saldo = 0d;
        Boolean done;
        for (int i = 0; i < 2;) {   //Necessario per la creazione di due diversi oggetti con due set di valori
            int iOut = i + 1;
            //Creazione di due oggetti separati, inserimento di nConto e saldo tramite costruttore.
            if (i == 0) { //facilmente sostituibile con un Array di vettori, non l'ho fatto semplicemente perché non ne abbiamo ancora parlato in classe
                conto1 = new Conto(reqNConto(iOut)); //Inserimento di nConto e creazione dell'oggetto NB:Siamo dentro un for.
                reqPrimoVer(conto1,iOut);
                //potenziale funzione
                //do
                //while(!done);
                i++;
            } else if (i == 1) {
                conto2 = new Conto(reqNConto(iOut));
                reqPrimoVer(conto2,iOut);
                i++;
            }
        }
        System.out.println("Inizio della simulazione dei movimenti");
        int conto = 1;
        boolean conto1reqRngTipoMov = reqRngTipoMovimento(conto); //Variabili di comodo per evitare la continua ripetizione della richiesta all'interno del for.
        conto++;
        boolean conto2reqRngTipoMov = reqRngTipoMovimento(conto);
        Boolean boolPreORVer = null;
        for (int i = 0; i < 5; i++) {
            int iOut = i + 1;
            conto = 1;
            //boolean v = false; //gestice la modalità "verbose", usata principalmente per debug
            System.out.println("\nSet di movimenti numero " + iOut + "\n");
            //Creazione di un movimento per conto1
            System.out.println("Primo Conto:");
            if (conto1reqRngTipoMov) { //riscrivi in funzione plz
                boolPreORVer = ThreadLocalRandom.current().nextBoolean();
                reqImporto(conto1, boolPreORVer);//richiede l'importo ma non il tipo di movimento.
            } else {
                boolPreORVer = reqTipoMovimento();
                reqImporto(conto1, boolPreORVer);//richiede sia il tipo di movimento che l'importo
            }
            //Creazione di un movimento per conto2
            System.out.println("\nSecondo Conto:");
            conto++;
            if (conto2reqRngTipoMov) {
                boolPreORVer = ThreadLocalRandom.current().nextBoolean();
                reqImporto(conto2, boolPreORVer);
            } else {
                boolPreORVer = reqTipoMovimento();
                reqImporto(conto2, boolPreORVer);
            }
        }

        conto1.stampa(boolPreORVer);
        conto2.stampa(boolPreORVer);
        double sommaconti = conto1.getSaldo() - conto2.getSaldo();
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.getDefault());
        DecimalFormat dfEuro = (DecimalFormat) nf;
        dfEuro.applyPattern("0.00");
        if (sommaconti > 0) {
            System.out.println("Il 1° conto ha un saldo maggiore, con una differenza di " + dfEuro.format(sommaconti) + "€.");
        } else {
            System.out.println(
                    "il 2° conto ha un saldo maggiore, con una differenza di " + dfEuro.format(-sommaconti) + "€.");
        }
    }
}
