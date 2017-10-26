package banca;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author XenSide
 */
public class Conto {

    public String nConto = null, data, lastData;
    private static String nContoProv, dataProv; //Creati data l'esigenza di metodi statici per la creazione dinamica dell'oggetto.
    private float saldo, lastSaldo, lastImporto;
    private static float importo, saldoProv;
    private Boolean boolPreORVer, boolRngTipoMovimento, boolRngSaldo; //Boolean perché abbiamo bisogno di impostare questa variable null, cosa non possibile con un tipo primitivo.
    InputStreamReader input = new InputStreamReader(System.in);
    BufferedReader tastiera = new BufferedReader(input);

    public Conto(String nConto, float saldo) {
        this.nConto = nConto;
        this.saldo = saldo;
    }

    public void setBoolPreORVer(Boolean boolPreORVer) {
        this.boolPreORVer = boolPreORVer;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setImporto(float importo) {
        Conto.importo = importo;
    }

    public boolean reqTipoMovimento(int iOut) {
        String preORVer;
        boolean done;
        do {
            try {
                System.out.println("Inserisci il tipo di movimento che va testato per il " + iOut + "° importo: Scrivi versamento o prelievo");
                preORVer = tastiera.readLine();
                switch (preORVer) {
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
                System.out.println("Errore nell'inserimento, scrivi versamento o prelievo, fa attenzione a scrivere minuscolo");
                done = false;
            }
        } while (!done); //Era la stessa condizione dell'if all'interno del try catch, ho deciso di creare un booleano al posto di ripetere la stessa condizione
        return boolPreORVer;
    } //Legacy Code

    public boolean reqRngTipoMovimento(int iOut) {
        String rng;
        boolean done;

        do {
            try {
                System.out.println("Vuoi inserire la tipologia dei movimenti per il " + iOut + "° conto manualmente? Scrivi si o no");
                rng = tastiera.readLine();
                switch (rng) {
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
                System.out.println("Errore nell'inserimento, scrivi si o no, fa attenzione a scrivere minuscolo");
                done = false;
            }
        } while (!done);
        return boolRngTipoMovimento;
    }

    public static String reqNConto(int iOut) {
        InputStreamReader input = new InputStreamReader(System.in); //Ho bisogno di ripetere il buffer d'input qui dato che 
        BufferedReader tastiera = new BufferedReader(input); //la funzione è statica e non accetta variabili che non siano a loro volta static
        boolean done;
        do {

            try {
                System.out.println("Inserisci il numero identificativo del " + iOut + "° conto");
                nContoProv = tastiera.readLine();
                done = true;
                if (nContoProv.length() < 7 || nContoProv.length() > 30) {
                    throw new IOException(); //si potrebbe aggiungere un booleano per evitare il controllo nel while ma non so se è effettivamente più efficiente o no.
                }
            } catch (IOException | NumberFormatException | NullPointerException e) {
                System.out.println("Errore nell'inserimento del numero di conto, riprova facendo attenzione ad inserire un numero compreso tra le 7 e le 30 cifre");
                done = false;
            }
        } while (!done);
        return nContoProv;
    }

     public static boolean reqRngSaldo(int iOut) {
        InputStreamReader input = new InputStreamReader(System.in); //Ho bisogno di ripetere il buffer d'input qui dato che 
        BufferedReader tastiera = new BufferedReader(input); //la funzione è statica e non accetta variabili che non siano a loro volta static
        String rng;
        boolean boolRngSaldo = false;
        boolean done;
        do {
            try {
                System.out.println("Vuoi inserire il saldo per il " + iOut + "° conto manualmente? Scrivi si o no");
                rng = tastiera.readLine();
                switch (rng) {
                    case "si":
                        boolRngSaldo = false;
                        done = true;
                        break;
                    case "no":
                        boolRngSaldo = true;
                        done = true;
                        break;

                    default:
                        throw new IOException();
                }
            } catch (IOException e) {
                System.out.println("Errore nell'inserimento, scrivi si o no, fa attenzione a scrivere minuscolo");
                done = false;
            }
        } while (!done);
        return boolRngSaldo;
    }
    public static float reqSaldo(int iOut){
        InputStreamReader input = new InputStreamReader(System.in); //Ho bisogno di ripetere il buffer d'input qui dato che 
        BufferedReader tastiera = new BufferedReader(input); //la funzione è statica e non accetta variabili che non siano a loro volta static
        boolean done;
        do{
         try {
                System.out.println("Inserisci il saldo del " + iOut + "° conto");
                saldoProv =Float.valueOf(tastiera.readLine());
                done = true;
                if (nContoProv.length() < 7 || nContoProv.length() > 30) {
                    throw new IOException(); //si potrebbe aggiungere un booleano per evitare il controllo nel while ma non so se è effettivamente più efficiente o no.
                }
            } catch (IOException | NumberFormatException | NullPointerException e) {
                System.out.println("Errore nell'inserimento del saldo, se stai provando ad inserire delle cifre decimali usa il punto al posto della virgola");
                done = false;
            }
        } while (!done);
        return saldoProv;
        
    }
    public static float reqImporto(int iOut) {
        InputStreamReader input = new InputStreamReader(System.in); //Ho bisogno di ripetere il buffer d'input qui dato che 
        BufferedReader tastiera = new BufferedReader(input); //la funzione è statica e non accetta variabili che non siano a loro volta static
        boolean done;
        do {
            try {
                System.out.println("Inserisci l'importo versato o prelevato");
                importo = Float.valueOf(tastiera.readLine());
                done = true;
                if (importo <= 0) {
                    importo = 0;
                    throw new IOException();
                }
            } catch (IOException | NumberFormatException e) {
                System.out.println("Errore nell'immisione dell'importo, se stai provando ad inserire delle cifre decimali usa il punto al posto della virgola");
                done = false;
            }

        } while (!done);
        return importo;
    }

    public static String reqData(int iOut) {
        InputStreamReader input = new InputStreamReader(System.in); //Ho bisogno di ripetere il buffer d'input qui dato che 
        BufferedReader tastiera = new BufferedReader(input); //la funzione è statica e non accetta variabili che non siano a loro volta static
        boolean done;
        do {
            try {
                System.out.println("Inserisci la data in cui è avvenuto il " + iOut + "° importo (gg/mm/aa)");
                dataProv = tastiera.readLine();
                done = true;
                if (dataProv.length() != 8) {
                    throw new IOException();
                }
            } catch (IOException | NullPointerException e) {
                System.out.println("Errore nell'inserimento della data, riprova facendo attenzione al formato (gg/mm/aa)");
                done = false;
            }
        } while (!done);
        return dataProv;
    }

    public float movimento(boolean v) { //v= verbose, log di tutti i traferimenti
        lastImporto = importo;
        lastData = data;
        lastSaldo = saldo;

        if (boolPreORVer && saldo >= importo) {
            saldo = saldo - importo;
        } else if (boolPreORVer && saldo < importo) {
            System.out.println("È stato impossibile effettuare l'operazione a causa di un saldo insufficiente");
        } else if (!boolPreORVer) {
            saldo = saldo + importo;
        }

        if (v) {
            if (boolPreORVer) {
                System.out.println(lastSaldo + "-" + lastImporto + "=" + saldo);
            } else {
                System.out.println(lastSaldo + "+" + lastImporto + "=" + saldo);
            }
        }
        return saldo;
    }

    public void stampa() {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.getDefault());
        DecimalFormat dfEuro = (DecimalFormat) nf;
        dfEuro.applyPattern("0.00");
        System.out.print("\nL'ultimo movimento nel conto " + nConto + " è stato un ");
        if (boolPreORVer) {
            System.out.println("prelievo di " + dfEuro.format(lastImporto) + "€");
        } else {
            System.out.println("versamento di " + dfEuro.format(lastImporto) + "€");
        }
        System.out.println("Effettuato in data " + lastData);
        System.out.println("Il saldo totale è " + dfEuro.format(saldo) + "€");
    }
}
