/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banca;

import java.io.BufferedReader; //Input
import java.io.InputStreamReader; //Input
import java.util.concurrent.ThreadLocalRandom; //RNG per il saldo

/**
 *
 * @author XenSide
 */
public class Banca {

    public static void main(String[] args) {
        Conto conto1 = null; //pre-dichiarazione necessaria per abilitare la creazione di un oggetto in un controllo if.
        Conto conto2 = null;
        String nConto;
        float importo = 0;
        String data = null;
        float saldo;

        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader tastiera = new BufferedReader(input);

        for (int i = 0; i < 2;) {   //Necessario per la creazione di due diversi oggetti con due set di valori
            int iOut = i + 1;
            nConto = Conto.reqNConto(iOut); //Inserimento di nConto NB:Siamo dentro un for.
            boolean rngSaldo=Conto.reqRngSaldo(iOut);
            if (rngSaldo) {
                saldo = ThreadLocalRandom.current().nextInt(1, 999999 + 1);  //RNG Saldo 
            } else {
                saldo = Conto.reqSaldo(iOut);
            }
            //Creazione di due oggetti separati, inserimento di nConto e saldo tramite costruttore.
            if (i == 0) { //facilmente sostituibile con un Array di vettori, non l'ho fatto semplicemente perché non ne abbiamo ancora parlato in classe
                conto1 = new Conto(nConto, saldo);
                System.out.println("Creazione del primo conto riuscita");
                if (rngSaldo) {
                    System.out.println("Saldo generato automaticamente (" + saldo + "€)\n");
                } else {
                    System.out.println("Il saldo è stato impostato manualmente a " + saldo + "€\n");
                }
                i++;
            } else if (i == 1) {
                conto2 = new Conto(nConto, saldo);
                System.out.println("Creazione del secondo conto riuscita");
                if (rngSaldo) {
                    System.out.println("Saldo generato automaticamente (" + saldo + "€)\n");
                } else {
                    System.out.println("Il saldo è stato impostato manualmente a " + saldo + "€\n");
                }
                i++;
            }

        }

        System.out.println("Inizio della simulazione dei movimenti");
        for (int i = 0; i < 5; i++) { //rimetti a 5
            int iOut = i + 1;
            int conto = 1;
            boolean v = false; //gestice la modalità "verbose", usata principalmente per debug
            System.out.println("Set di movimenti numero " + iOut + "\n");
            //Creazione di un movimento per conto1
            System.out.println("Primo Conto:");
            if (conto1.reqRngTipoMovimento(conto)) {
                conto1.setBoolPreORVer(ThreadLocalRandom.current().nextBoolean());
            } else {
                conto1.reqTipoMovimento(iOut);
            }
            //boolean boolPreORVer = conto1.richiediTipoMovimento(); //Usato per richiedere il tipo di movimento all'utente
            conto1.setData(Conto.reqData(iOut));
            conto1.setImporto(Conto.reqImporto(iOut));
            conto1.movimento(v);
            //Creazione di un movimento per conto2
            System.out.println("\nSecondo Conto:");
            conto++;
            if (conto2.reqRngTipoMovimento(conto)) {
                conto2.setBoolPreORVer(ThreadLocalRandom.current().nextBoolean());
            } else {
                conto2.reqTipoMovimento(iOut);
            }
            //boolean boolPreORVer = conto1.richiediTipoMovimento(); //Usato per richiedere il tipo di movimento all'utente
            conto2.setData(Conto.reqData(iOut));
            conto2.setImporto(Conto.reqImporto(iOut));
            conto2.movimento(v);
        }
        conto1.stampa();
        conto2.stampa();
        //Add bigger Conto control and printing
    }
}
