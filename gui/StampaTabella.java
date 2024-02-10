package gui;

import javax.print.*;
import javax.print.attribute.*;


public class StampaTabella {

    public StampaTabella(Applicazione applicazione){
        // Creazione del formato di stampa per la tabella
        DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        PrintService printService = PrintServiceLookup.lookupDefaultPrintService();

        // Impostazione del documento da stampare
        TabellaStampabile printableTable = new TabellaStampabile(applicazione);
        Doc doc = new SimpleDoc(printableTable, flavor, null);

        // Stampa del documento
        try {
            DocPrintJob printJob = printService.createPrintJob();
            printJob.print(doc, pras);
        } catch (PrintException e) {
            e.printStackTrace();
        }
    }
}
