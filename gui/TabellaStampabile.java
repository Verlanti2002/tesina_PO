package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

public class TabellaStampabile implements Printable {

    private JTable jTable;
    public TabellaStampabile(Applicazione applicazione){
        this.jTable = applicazione.getjTable();
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex > 0) {
            return Printable.NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) graphics;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

        // Imposta la dimensione della tabella per la stampa
        jTable.setSize((int) pageFormat.getImageableWidth(), (int) pageFormat.getImageableHeight());

        // Disegna la tabella sul grafico
        jTable.print(g2d);

        return Printable.PAGE_EXISTS;
    }
}
