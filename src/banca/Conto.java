package banca;

/*Todo list:
Aggiunta del fido, preferibilmente come classe figlio, con @override.
Rimozione di Date Data e scrittura di apposita stupida classe
Aggiunta di Classe che includa:
nome, cognome,sesso,comune di nascità,indirizzo,numTel,codFisc,dataNascita dell'intestatario
e che permetta le seguenti operazioni(metodi):
checkSex,Check18.
*/
import java.io.BufferedReader;
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
    private Date data;
    private double saldo;
    private double importo;
    InputStreamReader input = new InputStreamReader(System.in);
    BufferedReader tastiera = new BufferedReader(input);

    public Conto(String nConto) {
        this.nConto = nConto;
        saldo = 0;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public double getImporto() {
        return importo;
    }

    public boolean setImporto(Double importo,boolean boolPreORVer) {
        Boolean done = null;
        if (importo <= 0) {
            done = false;
        } else {
            this.importo = importo;
            movimento(boolPreORVer);
            done = true;
        }
        return done;
    }

    public Double movimento(boolean boolPreORVer) { //v= verbose, log di tutti i traferimenti
        Date dataAuto = new Date();
        setData(dataAuto);
        if (boolPreORVer && saldo >= importo) {
            saldo = saldo - importo;
        } else if (boolPreORVer && saldo < importo) {
            System.out.println("È stato impossibile effettuare l'operazione a causa di un saldo insufficiente");
        } else if (!boolPreORVer) {
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
            System.out.println("prelievo di " + dfEuro.format(importo) + "€");
        } else {
            System.out.println("versamento di " + dfEuro.format(importo) + "€");
        }
        System.out.println("Effettuato in data " + data);
        System.out.println("Il saldo totale è " + dfEuro.format(saldo) + "€");
    }
}
