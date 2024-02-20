# Gestione Esami
Il progetto mira all'implementazione di uno strumento per la gestione statistica degli esami universitari. Lo scopo principale è fornire ad un ipotetico docente un modo rapido ed intuitivo per raccogliere e gestire esami universitari di diverso tipo, tenendo sotto controllo le carriere accademiche degli studenti.

### Funzionalità principali:
- **Gestione degli esami:** permette ai docenti di inserire rapidamente i risultati degli esami universitari dei loro studenti

- **Salvataggio e caricamento degli esami:** è possibile salvare la tabella degli esami su file specificando il nome, e ricaricare il file specificando il nome del file salvato in precedenza

- **Gestione delle carriere accademiche:** fornisce strumenti per l'analisi statistica dei dati raccolti, inclusi grafici e report dettagliati

- **Stampa della tabella degli esami:** opzionalmente, l'utente può stampare la tabella tramite una delle stampanti configurate dal sistema operativo 

### Installazione:
1. Clona il repository dal seguente URL:
   
   ``` https://github.com/Verlanti2002/tesina_PO.git  ```

2. Compila il progetto utilizzando il tuo IDE preferito:
   
   ``` javac -Xlint -cp ./lib/*.jar ./classi/*.java ./gui/*.java -d ./build/  ```

3. Esegui il file JAR generato:
   
   ``` bash java -jar out/artifacts/GestioneEsami_jar/GestioneEsami.jar  ```

### Versioni utilizzate:
- **Compilatore Java:**

  ``` javac 21.0.1  ```

- **Java:**

  ```
      java version "21.0.1" 2023-10-17 LTS
      Java(TM) SE Runtime Environment (build 21.0.1+12-LTS-29)
      Java HotSpot(TM) 64-Bit Server VM (build 21.0.1+12-LTS-29, mixed mode, sharing)
  ```
