package it.unibs.fp.planetarium;

/*Note introduttive sul programma:
 * -C'è una sola stella public
 * -C'è un solo ArrayList contenente tutti i pianeti
 * -C'è un solo ArrayList contenente tutte le lune, ogni luna ha il suo centro(pianeta)
 * -Gli ArrayList sono contenuti nella classe non istanziabile SistemaStellare
 * -Per questo motivo i metodi che prendono in considerazione operazioni sugli array son posti tutti in quella classe, così da ridurre anche gli accoppiamenti e dipendenze tra classi
 * -Nelle classi Stella,Pianeta,Luna ci sono solo i metodi "base" e i costruttori
 * -Gran parte dei loro metodi "base" si trovano però in CorpoCeleste, la classe da cui ereditano
 * -Vengono utilizzate pressochè sempre le stesse variabili in quanto hanno solo scopo di contenere informazioni temporaneamente prima di venire inserite in un ArrayList o metodo
 * -Queste variabili sono dichiarate all'inizio del main
 * -I metodi input sono tutti contenuti nella classe InputMain
 * -Alcuni input verranno richiesti fino all'inserimento di un input corretto, altri "fermeranno" il processo in atto all'inserimento di un input errato
 * -Questa distinzione per gli input è stata decisa arbitrariamente dal gruppo in base alle varie funzionalità, non ci son fattori che lo determinino
 * (PS anche se un input non viene subito richiesto e interrompe l'azione si può sempre fare riferimento al menù e svolgerla nuovamente, non provoca limitazioni di nessun tipo questa distinzione)
 */

public class PlaneMain {
	
	private static final int INDEX_NON_VALIDO = -1;
	private static final int ULTIMA_SCELTA_CORPO_CELESTE = 3;
	private static final int SECONDA_SCELTA = 2;
	private static final int ULTIMA_SCELTA = 9;
	private static final int PRIMA_SCELTA = 1;
	private static final String MENU = "\nJavabbandiere, cosa vuoi fare adesso?"
			+ "\n1)Grande scoperta!Aggiungi un corpo celeste"
			+ "\n2)Catastrofe naturale!Rimuovi un corpo celeste"
			+ "\n3)Ricevi il codice di un corpo celeste"
			+ "\n4)Verifica la presenza di un corpo celeste nel sistema stellare"
			+ "\n5)Identifica tutte le lune che ruotano attorno a un pianeta"
			+ "\n6)Visualizza la distanza della luna dalla stella"
			+ "\n7)Calcola centro di massa del sistema stellare"
			+ "\n8)Calcola percorso tra due corpi celesti"
			+ "\n9)Esci!"
			+ "\n\nScegli: ";

	public static void main(String[] args) {
		
		int scelta, massa,sceltaCorpoCeleste,posizione;
		String nomeCorpoCeleste;
		boolean uscita;
		
		//Introduzione 
		System.out.println("Popolo dei Javabbandieri, un caldo benvenuto dal Consiglio Intergalattico!");
		System.out.println("Buon lavoro con il censimento del vostro sistema stellare!");
		
		//La stella è una ed è obbligatoria la sua aggiunta, senza quella non si possono aggiungere i pianeti
		//e neanche lune(che a loro volta vengono aggiunte solo se sono presenti pianeti)
		System.out.println("\nPer iniziare credo sia il caso di inserire il nome della vostra stella");
		System.out.println("Scegli il nome della stella del sistema stellare!");
		//Nessun InputDati.controllo sul nome
		//I controlli di InputMain.input sono presenti solo con i valori numerici, nei nomi son consentiti tutti i caratteri
		nomeCorpoCeleste=InputMain.inputNome();
		System.out.println(nomeCorpoCeleste+",un nome stellare!");
		massa=InputMain.inputMassa();
		SistemaStellare.stella=new Stella(massa,nomeCorpoCeleste);
		
		do {
			//Quando diventa true finisce il programa
			uscita=false;
			System.out.println(MENU);
			scelta=InputMain.controlloInput(PRIMA_SCELTA,ULTIMA_SCELTA);
			
			
			switch(scelta) {
				//Aggiunta di pianeti o lune
				case 1:
					boolean overSize=false;
					System.out.println("1)Pianeta\n2)Luna\nIndica cosa vuoi aggiungere:");
					sceltaCorpoCeleste=InputMain.controlloInput(PRIMA_SCELTA,SECONDA_SCELTA);
					//Il nome di un pianeta o di una luna non può essere uguale a quello di un altro corpo celeste
					nomeCorpoCeleste=InputMain.inputNome();
					while(SistemaStellare.isNomePresente(nomeCorpoCeleste)>0) {
						System.out.println("Nome già inserito, riprova!");
						nomeCorpoCeleste=InputMain.inputNome();
					}
					System.out.println("Nome convalidato!");
					//La massa può essere qualsiasi numero positivo intero, non ci sono vincoli
					int massaCorpoCeleste=InputMain.inputMassa();
					//La posizione fa riferimento rispetto alla stella, in posizione (0,0)
					int intx=InputMain.inputCoordinata("x");
					int inty=InputMain.inputCoordinata("y");
					//Due corpi celesti non possono sovrapporsi
					while(SistemaStellare.posizioneLibera(new Posizione(intx, inty))==false) {
						System.out.println("Posizione già occupata, scegli altre coordinate!");
						intx=InputMain.inputCoordinata("x");
						inty=InputMain.inputCoordinata("y");
					}
					if(sceltaCorpoCeleste==PRIMA_SCELTA) {
						//Inizializza il pianeta
						SistemaStellare.aggiungiPianeta(new Pianeta(massaCorpoCeleste,new Posizione(intx,inty),nomeCorpoCeleste));
						//Essendo appena stato aggiunto il pianeta, esso si troverà in posizione ArrayList.size()-1
						SistemaStellare.setCodiceCorpoCeleste(SistemaStellare.elencoPianeti.get(SistemaStellare.elencoPianeti.size()-1));
					}
					else {
						//Se non son presenti pianeti nell'ArrayList non è possibile aggiungere lune
						if(SistemaStellare.esistenzaPianeti()==true) {
							//L'utente inserisce il nome del pianeta e il programma lo imposta come centro, 
							//sempre se appartiene all'ArrayList
							String nomePianeta;
							do{
								nomePianeta=InputMain.inputNomePianeta();
								//Cerca il pianeta nell'Array e viene ritornato l'indice a cui è inserito
								posizione=SistemaStellare.trovaPianeta(nomePianeta);
								if(posizione==INDEX_NON_VALIDO)
									System.out.println("Pianeta non trovato");
								//Verifica che il pianeta non abbia già 5000 lune che orbitano intorno
								if(posizione!=INDEX_NON_VALIDO)
									if(SistemaStellare.controlloNumeroLune(SistemaStellare.elencoPianeti.get(posizione))==false) {
										System.out.println("Impossibile aggiungere la luna, "+SistemaStellare.elencoPianeti.get(posizione).getNome()+" ha già 5000 lune");
										//Per ovviare al caso (abbastanza raro, ma comunque possibile), per cui ogni pianeta presente abbia già 5000 lune(Il che porterebbe a un loop infinito)
										//L'input viene arrestato, o meglio, la luna viene aggiunta al pianeta con 5000 lune e immediatamente rimossa
										overSize=true;
									}
							}while(posizione==INDEX_NON_VALIDO);
							//Inizializza una nuova luna e la aggiunge, compreso il pianeta attorno a cui gira
							SistemaStellare.aggiungiLuna(new Luna(massaCorpoCeleste,new Posizione(intx,inty),nomeCorpoCeleste,SistemaStellare.elencoPianeti.get(posizione)));
							//Stesso discorso applicato al codice dei pianeti
							SistemaStellare.setCodiceCorpoCeleste(SistemaStellare.elencoLune.get(SistemaStellare.elencoLune.size()-1));
							//Se il pianeta aveva già 5000 lune(overSize=true), l'input non va a buon fine e la luna non viene aggiunta
							if(overSize==true)
								SistemaStellare.rimuoviLuna(nomeCorpoCeleste);
						}
					}
					break;
				//Rimozione di pianeti o lune
				case 2:
					System.out.println("1)Pianeta\n2)Luna\nIndica cosa vuoi rimuovere:");
					sceltaCorpoCeleste=InputMain.controlloInput(PRIMA_SCELTA,SECONDA_SCELTA);
					if(sceltaCorpoCeleste==PRIMA_SCELTA) {
						if(SistemaStellare.esistenzaPianeti()) {
							nomeCorpoCeleste=InputMain.inputNome();
							while(SistemaStellare.trovaPianeta(nomeCorpoCeleste)==INDEX_NON_VALIDO) {
								System.out.println("Impossibile trovare il pianeta\nProva con un altro:");
								nomeCorpoCeleste=InputMain.inputNome();
							}
							SistemaStellare.rimuoviPianeta(nomeCorpoCeleste);
						}
					}else{
						if(SistemaStellare.esistenzaLune()) {
							nomeCorpoCeleste=InputMain.inputNome();
							while(SistemaStellare.trovaLuna(nomeCorpoCeleste)==INDEX_NON_VALIDO) {
								System.out.println("Impossibile trovare la luna\nProva con un'altra:");
								nomeCorpoCeleste=InputMain.inputNome();
							}
							SistemaStellare.rimuoviLuna(nomeCorpoCeleste);
						}
					}
					break;
				//Identificazione codice
				case 3:
					System.out.println("1)Stella\n2)Pianeta\n3)Luna\nScegli il corpo celeste da cui ottenere il codice:");
					sceltaCorpoCeleste=InputMain.controlloInput(1,ULTIMA_SCELTA_CORPO_CELESTE);
					if(sceltaCorpoCeleste==PRIMA_SCELTA)
						System.out.println("Il codice della stella "+SistemaStellare.stella.getNome()+" è "+SistemaStellare.stella.getCodice());
					else if(sceltaCorpoCeleste==SECONDA_SCELTA) {
						nomeCorpoCeleste=InputMain.inputNome();
						posizione=SistemaStellare.trovaPianeta(nomeCorpoCeleste);
						if(posizione!=INDEX_NON_VALIDO)
							System.out.println("Il codice del pianeta "+SistemaStellare.elencoPianeti.get(posizione).getNome()+" è "+SistemaStellare.elencoPianeti.get(posizione).getCodice());
						else
							System.out.println("Pianeta non trovato, impossibile identificare il codice");
					}else {
						nomeCorpoCeleste=InputMain.inputNome();
						posizione=SistemaStellare.trovaLuna(nomeCorpoCeleste);
						if(posizione!=INDEX_NON_VALIDO)
							System.out.println("Il codice della luna "+SistemaStellare.elencoLune.get(posizione).getNome()+" è "+SistemaStellare.elencoLune.get(posizione).getCodice());
						else
							System.out.println("Luna non trovata, impossibile identificare il codice");
					}
					break;
				//Ricerca di un corpo celeste nel sistema Stellare
				case 4:
					System.out.println("Cerca un corpo celeste nel registro intergalattico!");
					nomeCorpoCeleste=InputMain.inputNome();
					int risultatoRicerca=SistemaStellare.isNomePresente(nomeCorpoCeleste);
					if(risultatoRicerca<0)
						System.out.println("\nCorpo celeste non trovato");
					else if(risultatoRicerca==PRIMA_SCELTA)
						System.out.println("Corpo celeste trovato, "+nomeCorpoCeleste+" è la stella del sistema stellare!");
					else if(risultatoRicerca==SECONDA_SCELTA)
						System.out.println("Corpo celeste trovato, "+nomeCorpoCeleste+" è un pianeta!");
					else {
						//Per il pianeta richiamo vari metodi, ovvero prima trovo la luna nell'ArrayList con posizione data dal metodo "trovaLuna", 
						//utilizzo il metodo getCentro() dichiarato nella classe Luna e getNome() presente nella classe padre CorpoCeleste
						System.out.println("Corpo celeste trovato, "+nomeCorpoCeleste+" è la luna che ruota attorno al pianeta "+SistemaStellare.elencoLune.get(SistemaStellare.trovaLuna(nomeCorpoCeleste)).getCentro().getNome()+"!");
					}
					break;
				//Identifica tutte le lune che girano intorno a un pianeta
				case 5:
					System.out.println("Scegli il pianeta!");
					nomeCorpoCeleste=InputMain.inputNome();
					posizione=SistemaStellare.trovaPianeta(nomeCorpoCeleste);
					if(posizione==INDEX_NON_VALIDO)
						System.out.println("Impossibile visualizzare le lune, pianeta non trovato!");
					else {
						SistemaStellare.luneIntornoAlPianeta(SistemaStellare.elencoPianeti.get(posizione));
					}
					break;
				//Ottiene il percorso da stella a luna
				case 6:
					System.out.println("Scegli una luna per ottenere il percorso!");
					nomeCorpoCeleste=InputMain.inputNome();
					posizione=SistemaStellare.trovaLuna(nomeCorpoCeleste);
					if(posizione==INDEX_NON_VALIDO)
						System.out.println("Impossibile visualizzare il percorso, la luna non è stata trovata!");
					else {
						SistemaStellare.elencoLune.get(posizione).getPercorsoNomi();
					}
					break;
				//Ottiene il centro di massa
				case 7:
					SistemaStellare.calcoloCentroDiMassa();
					break;
				//Ottiene la tratta tra due corpi celesti
				case 8:
					System.out.println("Sarà un lungo viaggio!\nDecidi il corpo celeste da cui partire");
					nomeCorpoCeleste=InputMain.inputNome();
					System.out.println("Decidi il corpo celeste dove arrivare");
					String nomeCorpoCelesteArrivo=InputMain.inputNome();
					SistemaStellare.getTrattaCorpiCelesti(nomeCorpoCeleste, nomeCorpoCelesteArrivo);
					break;
				//Esce dal ciclo e termina il programma
				case 9:
					uscita=true;
					break;
			}
		}while(uscita==false);
		System.out.println("Davvero un gran lavoro!\nArrivederci javabbandiere!");
	}
}
