# LEARNING GROUP 

> ***LEARNING GROUP*** è un applicazione web  creata per facilitare 
la vita dei sviluppatori.<br>
Essa permette di mettere in relazione un _**utente
lambda (lettori)**_ con **_professionali_ (creatori)** in diversi _linguaggi di programmazione_ tramite 
> _**templates**_ pubblicati dai creatori o richiedendoli appuntamenti per _**consultazioni private**_.


### **Requisiti**
1. **Lista dei Templates pubblicati da soli creatori** con informazioni seguenti:
   * data e ora di pubblicazione,
   * linguaggio d'interesse,
   * titolo del templates,
   * una description leggera o tagliata di massimo 3 frasi,
   * indicazioni sul numero di likes e commenti succitati dal template.
2. **Funzione di ricerca** per _linguaggio_ e per _titolo_. 
3. **Descrizione** e **giudizio** degli utenti per ciascun template con possibilità di vedere il trailer del 
   video esplicativo caricato su YOUTUBE dal creatore se disponibile.
4. **Possibilità di registrarsi al portale per inserire giudizi** (numero di stelle da 1 a 5 e commento
   testuale)
5. **Possibilità di visualizzare**
6. **modificare un template**.
7. **Possibilità di prendere appuntamento** (_massimo 1 appuntamenti per template_) con il creatore rispetto ad un template specificando ora, data, oggetto e mezzo d'incontro (google meet,zoom).
8. **Possibilità di visualizzare e modificare un appuntamento** modificando data, ora, luogo d'incontro e mezzo d'incontro.
9. **Possibilità per il creatore di rispondere alla prenotazione**, lasciando link di connessione e un messaggio per il richiedente, visualizzabile sempre nello stato della prenotazione.  
10. **Possibilità di effettuare ricerche** per: _stato di risposta a una prenotazione_, _oggetto_ o per _via d'incontro_.
11. una volta la prenotazione richiesta puo essere modificata fin tanto che il creatore la confermi dopo di che potrà solo essere cancellata. 

### Il sistema prevede che le categorie di utenti sia così rappresentata:
* **_utenti pubblici_** che possono solamente registrarsi per poter acceder al sito, possono scigliere se sono lettore o creatore.
* **_utenti registrati_** che possono effettuare tutti punti precedenti e avremo: 
  > * **_creatore_** che puo eseguire tutti i punti precedenti tranne **7**.

  > * **_lettore_** che puo eseguire tutti i punti precedenti tranne **6,11**.

  > * **_administratore_** che possono inserire, cancellare o ristorare un utente.  