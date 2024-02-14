package gui;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.Styler;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class Grafico {

    public Grafico(Applicazione applicazione){

        /** Recupera il numero di righe filtrate nella tabella */
        int rowCount = applicazione.getTabella().getTable().getRowCount();

        /** Lista per memorizzare i voti recuperati dalla tabella */
        ArrayList<Integer> voti = new ArrayList<>();

        /** Recupera i voti dalla tabella */
        for(int i=0; i<rowCount; i++){
            /** Converte il valore della cella prima in una stringa e poi lo converte in un intero */
            voti.add(Integer.parseInt((String) applicazione.getTabella().getTable().getValueAt(i,4)));
        }

        /** Creazione della mappa di frequenze dei voti */
        Map<Integer, Long> frequencyMap = voti.stream()
                /** Raggruppa i voti e conta le occorrenze di ciascun voto */
                .collect(Collectors.groupingBy(v -> v, Collectors.counting()));

        /** Creazione del grafico XChart di tipo istogramma */
        CategoryChart chart = new CategoryChartBuilder().width(800).height(600)
                .title("Istogramma dei voti") /** Titolo del grafico */
                .xAxisTitle("Voti") /** Etichetta dell'asse X */
                .yAxisTitle("Frequenza") /** Etichetta dell'asse Y */
                .build();

        /** Personalizzazione dello stile dell'istogramma: imposta la posizione della legenda */
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);

        /** Aggiunta dei dati all'istogramma */
        frequencyMap.forEach((voto, frequenza) ->
                chart.addSeries(String.valueOf(voto), /** Etichetta della serie */
                        Collections.singletonList(voto), /** Valore dell'asse X (voto) */
                        Collections.singletonList(frequenza))); /** Valore dell'asse Y (frequenza) */

        /** Mostra il grafico in un nuovo thread per evitare blocchi nell'interfaccia utente */
        new Thread(() -> {
            /** Crea il wrapper del grafico Swing */
            SwingWrapper<CategoryChart> swingWrapper = new SwingWrapper<>(chart);

            /** Crea il frame per contenere il grafico */
            JFrame frame = swingWrapper.displayChart();
            /** Imposta il comportamento che deve assumere il frame quando viene chiuso dall'utente */
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Viene chiuso solo questo frame
        }).start();
    }
}
