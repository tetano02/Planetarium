package it.unibs.fp.planetarium;

import java.util.ArrayList;

public class SistemaStellare {
		
		private static final int MAX_LUNE_PER_PIANETA = 5000;
		private static final int MAX_PIANETI = 26000;
		
		public static Stella stella;
		public static ArrayList<Pianeta> elencoPianeti= new ArrayList<Pianeta>();
		public static ArrayList <Luna> elencoLune=new ArrayList<Luna>();
		public static ArrayList<Integer> indiciCodiciPianeti=new ArrayList<Integer>();
		public static ArrayList<Integer> indiciCodiciLune=new ArrayList<Integer>();

		
		public static void aggiungiPianeta(Pianeta p) {
			if(elencoPianeti.size()<MAX_PIANETI) {
				elencoPianeti.add(p);
				System.out.println("Complimenti! "+p.getNome()+" è stato aggiunto al registro intergalattico!");
			}
			else
				System.out.println("È stato raggiunto il numero massimo di pianeti!");
		}

		public static void aggiungiLuna(Luna l) {
			elencoLune.add(l);
			System.out.println("Complimenti! "+l.getNome()+" è stata aggiunta al registro intergalattico!");
		}
		
		//Verifica che un pianeta non abbia già 5000 lune che orbitano intorno
		public static boolean controlloNumeroLune(Pianeta p){
			int conta=0;
			for(int i=0; i<elencoLune.size(); i++) {
				if(elencoLune.get(i).getCentro().equals(p))
					conta++;
			}
			if(conta<MAX_LUNE_PER_PIANETA)
				return true;
			return false;
		}
		
		public static boolean esistenzaPianeti() {
			if(elencoPianeti.size()==0) {
				System.out.println("Impossibile svolgere l'azione, aggiungi prima un pianeta al registro intergalattico!");
				return false;
			}
			return true;
		}
		
		public static boolean esistenzaLune() {
			if(elencoLune.size()==0) {
				System.out.println("Impossibile svolgere l'azione, aggiungi prima una luna al registro intergalattico!");
				return false;
			}
			return true;
		}
		
		public static void rimuoviPianeta(String str) {
			int pos=SistemaStellare.trovaPianeta(str);
			if(pos!=-1) {
				SistemaStellare.rimuoviLuneDiUnPianeta(elencoPianeti.get(pos));
				elencoPianeti.remove(pos);
				System.out.println("Il Pianeta "+str+" è stato rimosso con successo!");
				System.out.println("L'esplosione è stata talmente forte da distruggere tutte le lune che vi orbitavano intorno!");
			}else
				System.out.println("Mi spiace, il pianeta non è stato trovato, impossibile rimuoverlo");
		}
		
		
		public static void rimuoviLuneDiUnPianeta(Pianeta p) {
			for(int i=0; i<elencoLune.size();i++) {
				if(elencoLune.get(i).getCentro().equals(p)) {
					elencoLune.remove(i);
					//i-- perchè rimuovendo un elemento tutti gli altri traslano a sinistra
					//Senza i-- perderei il controllo dell'elemento che prende il posto di quello in posizione i
					i--;
				}
			}
		}
		
		public static void rimuoviLuna(String str) {
			int pos=SistemaStellare.trovaLuna(str);
			if(pos!=-1) {
				elencoLune.remove(pos);
				System.out.println("La luna "+str+" è stata rimossa con successo!");
			}else
				System.out.println("Mi spiace, la luna non è stata trovata, impossibile rimuoverla");
		}
		
		//Il metodo verifica che un pianeta sia presente e ritorna la sua posizione, ritorna -1 se non lo trova
		//Trova pianeta non fa distinzioni tra maiuscole e minuscole,
		//Così se l'utente cerca (ad esempio) terra invece che Terra, glielo trova comunque
		public static int trovaPianeta(String str) {
			for(int i=0; i<elencoPianeti.size(); i++) {
				if(elencoPianeti.get(i).getNome().equalsIgnoreCase(str)) {
					return i;
				}
			}
			return -1;
		}
		
		public static int trovaLuna(String str) {
			for(int i=0; i<elencoLune.size(); i++) {
				if(elencoLune.get(i).getNome().equalsIgnoreCase(str)) {
					return i;
				}
			}
			return -1;
		}
		
		//Verifica se una Stringa inserita corrisponde al nome di un corpo celeste e in base a che corpo celeste è ritorna un valore diverso
		public static int isNomePresente(String str) {
			if(str.equals(stella.getNome()))
				return 1;
			if(trovaPianeta(str)!=-1)
				return 2;
			if(trovaLuna(str)!=-1)
				return 3;
			return -1;
		}
		
		//Verifica se una posizione è libera, due corpi celesti non possono sovrapporsi
		public static boolean posizioneLibera(Posizione p1) {
			if(p1.getX()==0 && p1.getY()==0)
				return false;
			for(int i=0; i<elencoPianeti.size(); i++) {
				if(elencoPianeti.get(i).getPos().getX()==p1.getX() && elencoPianeti.get(i).getPos().getY()==p1.getY()) {
					return false;
				}
			}
			
			for(int i=0; i<elencoLune.size(); i++) {
				if(elencoLune.get(i).getPos().getX()==p1.getX() && elencoLune.get(i).getPos().getY()==p1.getY()) {
					return false;
				}
			}
			return true;
		}
		
		//Il codice è composto in questo modo: LETTERA POSIZIONE_ARRAY(teorica)
		//La lettera indica, con L che è una luna, P un pianeta, S una stella.
		//Essendoci una sola stella nel sistema, il suo codice sarà di default S000000000.
		//I numeri sono 9 in quanto il numero massimo di lune è 26000(numero max di pianeti)*5000=130.000.000.
		//Per rimanere conformi nella scrittura del codice, ogni codice ha 9 numeri.
		//Il codice viene impostato non appena il corpo celeste viene aggiunto al sistema.
		//Queste informazioni sono commentate e non in output in quanto all'utente interessa solo sapere qual è il codice, non cosa ci sta dietro.
		//Ovviamente in seguito a modifiche come remove gli indici si muoverebbero e a quel punto i codici non corrisponderebbero più alle posizioni.
		//L'indice che sale viene salvato in ArrayList di interi, così da non dover fare riferimento a ElencoPianeti o Lune, che nel caso
		//di remove non sarebbero più affidabili per gli indici.
		//Così facendo ci potrebbero essere salvate nel caso più estremo 130.000.000 lune e sarebbero state fatte 870.000.000 rimozioni, che credo siano abbastanza esplosioni...
		
		
		public static void setCodiceCorpoCeleste(Pianeta p) {
			String code=String.format("P%09d", indiciCodiciPianeti.size());
			indiciCodiciPianeti.add(null);
			p.setCodice(code);
		}
		
		public static void setCodiceCorpoCeleste(Luna l) {
			String code=String.format("L%09d", indiciCodiciLune.size());
			indiciCodiciLune.add(null);
			l.setCodice(code);
		}
		
		//Visualizza le lune intorno a un pianeta
		public static void luneIntornoAlPianeta(Pianeta p) {
			int conta=0;
			for(int i=0; i<elencoLune.size(); i++) {
				if(elencoLune.get(i).getCentro().equals(p)) {
					if(conta==0)
						System.out.println("Ecco l'elenco delle lune che orbitano intorno a "+p.getNome()+":");
					System.out.println(elencoLune.get(i).getNome());
					conta++;
				}
			}
			if(conta==0)
				System.out.println("Questo pianeta non ha lune che vi orbitano intorno!");
		}
		
		//Calcolo del centro di massa
		public static void calcoloCentroDiMassa() {
			int massaTotale=0;
			double sommaPesataPosizioniX=0;
			double sommaPesataPosizioniY=0;
			massaTotale+=stella.getMassa();
			if(elencoPianeti.size()>0) {
				for(int i=0; i<elencoPianeti.size(); i++) {
					massaTotale+=elencoPianeti.get(i).getMassa();
					sommaPesataPosizioniX+=(elencoPianeti.get(i).getMassa())*(elencoPianeti.get(i).getPos().getX());
					sommaPesataPosizioniY+=(elencoPianeti.get(i).getMassa())*(elencoPianeti.get(i).getPos().getY());
				}
				if(elencoLune.size()>0) {
					for(int i=0; i<elencoLune.size(); i++) {
						massaTotale+=elencoLune.get(i).getMassa();
						sommaPesataPosizioniX+=(elencoLune.get(i).getMassa())*(elencoLune.get(i).getPos().getX());
						sommaPesataPosizioniY+=(elencoLune.get(i).getMassa())*(elencoLune.get(i).getPos().getY());
					}
				}
			}
			
			double centroDiMassaX=sommaPesataPosizioniX/massaTotale;
			double centroDiMassaY=sommaPesataPosizioniY/massaTotale;
			System.out.println("Il centro di massa del sistema stellare ha coordinate ("+centroDiMassaX+","+centroDiMassaY+")");
		}
		
		//Restituisce la tratta e il percorso tra due corpi celesti
		public static void getTrattaCorpiCelesti(String str1,String str2) {
			//Variabili per riconoscere se i corpi celesti sono presenti
			int riconosci1,riconosci2;
			int posizione1,posizione2;
			double percorso;
			boolean trovato;
			riconosci1=isNomePresente(str1);
			riconosci2=isNomePresente(str2);
			if(riconosci1==-1 || riconosci2==-1) {
				if(riconosci1==-1)
					System.out.println("Impossibile trovare "+str1);
				if(riconosci2==-1)
					System.out.println("Impossibile trovare "+str2);
				return;
			}
			
			if(str1.equals(str2) && riconosci1==riconosci2) {
				getTratteUguali(str1,str2);
				return;
			}
			
			//Gli switch annidati prendono in considerazione tutte le 9 possibilità di partenza-arrivo (3 case per il primo switch, 3 case per il secondo switch)
			//Prendono in considerazione anche i casi particolari (Se due lune orbitano attorno allo stesso pianeta non c'è bisogno di passare per la stella etc)
			switch(riconosci1) {
				case 1:
					switch(riconosci2) {
						//Il case 1 è inutile da considerare, c'è già il metodo getTratteUguali
						case 2:
							System.out.println("La tratta da effettuare per arrivare è "+str1+" > "+str2);
							posizione1=trovaPianeta(str2);
							System.out.println("Quanta strada! Ben "+getPercorsoCorpiCelesti(stella,elencoPianeti.get(posizione1))+" migliaia di kilometri!");
							break;
						case 3:
							percorso=0;
							posizione2=trovaLuna(str2);
							//Riutilizzo il metodo presente nella classe Luna
							elencoLune.get(posizione2).getPercorsoNomi();
							break;
					}
					break;
				case 2:
					switch(riconosci2) {
						case 1:
							System.out.println("La tratta da effettuare per arrivare è "+str1+" > "+str2);
							posizione1=trovaPianeta(str1);
							System.out.println("Quanta strada! Ben "+getPercorsoCorpiCelesti(elencoPianeti.get(posizione1),stella)+" migliaia di kilometri!");
							break;
						case 2:
							percorso=0;
							System.out.println("La tratta da effettuare per arrivare è "+str1+" > "+stella.getNome()+" > "+str2);
							posizione1=trovaPianeta(str1);
							posizione2=trovaPianeta(str2);
							percorso+=getPercorsoCorpiCelesti(elencoPianeti.get(posizione1),stella);
							percorso+=getPercorsoCorpiCelesti(stella,elencoPianeti.get(posizione2));
							System.out.println("Quanta strada! Ben "+percorso+" migliaia di kilometri!");
							break;
						case 3:
							percorso=0;
							posizione2=trovaLuna(str2);
							posizione1=trovaPianeta(str1);
							trovato=false;
							//Verifica se la luna ruota intorno al pianeta
							if(elencoLune.get(posizione2).getCentro().equals(elencoPianeti.get(posizione1)))
									trovato=true;
							if(trovato) {
								System.out.println("La tratta da effettuare per arrivare è "+str1+" > "+str2);
								System.out.println("Quanta strada! Ben "+getPercorsoCorpiCelesti(elencoPianeti.get(posizione1),elencoLune.get(posizione2))+" migliaia di kilometri!");
							} else {
								System.out.println("La tratta da effettuare per arrivare è "+str1+" > "+stella.getNome()+" > "+elencoLune.get(posizione2).getNomePianeta()+" > "+elencoLune.get(posizione2).getNome());
								percorso+=getPercorsoCorpiCelesti(elencoPianeti.get(posizione1),stella);
								percorso+=getPercorsoCorpiCelesti(stella,elencoLune.get(posizione2).getCentro());
								percorso+=getPercorsoCorpiCelesti(elencoLune.get(posizione2).getCentro(),elencoLune.get(posizione2));
								System.out.println("Quanta strada! Ben "+percorso+" migliaia di kilometri!");
							}
							break;			
					}
					break;
				case 3:
					switch(riconosci2) {
						case 1:
							percorso=0;
							posizione1=trovaLuna(str1);
							System.out.println("La tratta da effettuare per arrivare è "+str1+" > "+elencoLune.get(posizione1).getNomePianeta()+" > "+str2);
							percorso+=getPercorsoCorpiCelesti(elencoLune.get(posizione1),elencoLune.get(posizione1).getCentro());
							percorso+=getPercorsoCorpiCelesti(elencoLune.get(posizione1).getCentro(),stella);
							System.out.println("Quanta strada! Ben "+percorso+" migliaia di kilometri!");
							break;
						case 2:
							percorso=0;
							trovato=false;
							posizione1=trovaLuna(str1);
							posizione2=trovaPianeta(str2);
							//Verifica se la luna ruota intorno al pianeta
							if(elencoLune.get(posizione1).getCentro().equals(elencoPianeti.get(posizione2)))
								trovato=true;
							if(trovato) {
								System.out.println("La tratta da effettuare per arrivare è "+str1+" > "+str2);
								System.out.println("Quanta strada! Ben "+getPercorsoCorpiCelesti(elencoLune.get(posizione1),elencoPianeti.get(posizione2))+" migliaia di kilometri!");
							} else {
								System.out.println("La tratta da effettuare è "+str1+" > "+elencoLune.get(posizione1).getCentro().getNome()+" > "+stella.getNome()+" > "+str2);
								percorso+=getPercorsoCorpiCelesti(elencoLune.get(posizione1),elencoLune.get(posizione1).getCentro());
								percorso+=getPercorsoCorpiCelesti(elencoLune.get(posizione1).getCentro(),stella);
								percorso+=getPercorsoCorpiCelesti(stella,elencoPianeti.get(posizione2));
								System.out.println("Quanta strada! Ben "+percorso+" migliaia di kilometri!");
							}
							break;
						case 3:
							percorso=0;
							posizione1=trovaLuna(str1);
							posizione2=trovaLuna(str2);
							trovato=false;
							//Verifica se le due lune orbitano intorno allo stesso pianeta
							if(elencoLune.get(posizione1).getCentro().equals(elencoLune.get(posizione2).getCentro()))
								trovato=true;
							//I due casi, se orbitano(true) attorno allo stesso pianeta o no(false)
							if(trovato) {
								System.out.println("La tratta da effettuare per arrivare è "+str1+" > "+elencoLune.get(posizione1).getNomePianeta()+" > "+str2);
								percorso+=getPercorsoCorpiCelesti(elencoLune.get(posizione1),elencoLune.get(posizione1).getCentro());
								percorso+=getPercorsoCorpiCelesti(elencoLune.get(posizione1).getCentro(),elencoLune.get(posizione2));
								System.out.println("Quanta strada! Ben "+percorso+" migliaia di kilometri!");
							} else {
								System.out.println("La tratta da effettuare per arrivare è "+str1+" > "+elencoLune.get(posizione1).getNomePianeta()+" > "+stella.getNome()+" > "+elencoLune.get(posizione2).getNomePianeta()+" > "+str2);
								percorso+=getPercorsoCorpiCelesti(elencoLune.get(posizione1),elencoLune.get(posizione1).getCentro());
								percorso+=getPercorsoCorpiCelesti(elencoLune.get(posizione1).getCentro(),stella);
								percorso+=getPercorsoCorpiCelesti(stella,elencoLune.get(posizione2).getCentro());
								percorso+=getPercorsoCorpiCelesti(elencoLune.get(posizione2).getCentro(),elencoLune.get(posizione2));
								System.out.println("Quanta strada! Ben "+percorso+" migliaia di kilometri!");
							}
							break;
					}
					break;
				
			}
		}
		
		//Metodo utile se vengono inseriti due volte gli stessi corpi celesti
		public static void getTratteUguali(String str1,String str2) {
			System.out.println("La tratta da effettuare per arrivare è "+str1+" > "+str2);
			System.out.println("La distanza è nulla!");
		}
		
		//Calcola la distanza tra due corpi celesti
		public static double getPercorsoCorpiCelesti(CorpoCeleste c1,CorpoCeleste c2) {
			return Math.sqrt(Math.pow(c1.getPos().getX() - c2.getPos().getX(),2) + Math.pow(c1.getPos().getY() - c2.getPos().getY(), 2));
		}
}
