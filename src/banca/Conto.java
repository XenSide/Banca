package banca;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author XenSide
 */
public class Conto {

    private String nConto = null;
    private Date data, lastData;
    private static String nContoProv, dataProv; //Creati data l'esigenza di metodi statici per la creazione dinamica dell'oggetto.
    private double saldo, lastSaldo, lastImporto;
    private double importo, saldoProv;
    private Boolean boolPreORVer, boolRngTipoMovimento, boolRngSaldo; //Boolean perché abbiamo bisogno di impostare questa variable null, cosa non possibile con un tipo primitivo.
    InputStreamReader input = new InputStreamReader(System.in);
    BufferedReader tastiera = new BufferedReader(input);

    public Conto() {
        nConto = reqNConto(1);
        saldo = 0;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setBoolPreORVer(Boolean boolPreORVer) {
        this.boolPreORVer = boolPreORVer;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public double getImporto() {
        return importo;
    }

    public boolean setImporto(Double importo) {
        Boolean done=null;
        if (importo <= 0) {
            done = false;
        } else {
            this.importo = importo;
            done = true;
        }
        return done;
    }

    //Legacy Code
/*    public boolean reqRngTipoMovimento(int iOut) {
        String rng;
        boolean done;

        do {
            try {
                System.out.println("Vuoi inserire la tipologia dei movimenti per il " + iOut + "° conto manualmente? Scrivi Si o No");
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
                System.out.println("Errore nell'inserimento, scrivi si o no, fa attenzione a scrivere minuscolo");
                done = false;
            }
        } while (!done);
        return boolRngTipoMovimento;
    }*/
    public static String reqNConto(int iOut) { //transform in setNConto
        InputStreamReader input = new InputStreamReader(System.in); //Ho bisogno di ripetere il buffer d'input qui dato che 
        BufferedReader tastiera = new BufferedReader(input); //la funzione è statica e non accetta variabili che non siano a loro volta static
        boolean done;
        System.out.println("Inserisci il numero identificativo del " + iOut + "° conto");
        do {

            try {
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

    public Double setSaldo(Double saldo) {
        boolean done;
        do {
            try {
                this.saldo = saldo;
                done = true;
            } catch (Exception e) {
                System.out.println("Errore nell'inserimento del saldo, se stai provando ad inserire delle cifre decimali usa il punto al posto della virgola");
                done = false;
            }
        } while (!done);
        return saldoProv;

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

    public Double movimento(boolean boolPreORVer) { //v= verbose, log di tutti i traferimenti
        Date dataAuto = new Date();
        setData(dataAuto);
        if (boolPreORVer && saldo >= importo) {
            lastImporto = importo;
            lastData = data;
            lastSaldo = saldo;
            saldo = saldo - importo;
        } else if (boolPreORVer && saldo < importo) {
            System.out.println("È stato impossibile effettuare l'operazione a causa di un saldo insufficiente");
        } else if (!boolPreORVer) {
            lastImporto = importo;
            lastData = data;
            lastSaldo = saldo;
            saldo = saldo + importo;
        }

        /* Verbose mode legacy code, needs an arg v.
        if (v) {
            if (boolPreORVer) {
                System.out.println(lastSaldo + "-" + lastImporto + "=" + saldo);
            } else { 
                System.out.println(lastSaldo + "+" + lastImporto + "=" + saldo);
            }
        }*/
        return saldo;
    }

    public void stampa(boolean boolPreORVer) {
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
