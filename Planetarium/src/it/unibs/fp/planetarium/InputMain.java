package it.unibs.fp.planetarium;

import java.util.*;
public class InputMain {
	
	//La classe InputMain contiene metodi utili al main per l'input di vari tipi di variabili
	//In ogni input c'è un controllo del tipo inserito, tranne nei nomi, dove son consentiti tutti i caratteri
		
	//La stringa daButtare è spesso presente e viene assegnata all'eventuale InputDati.input errato 
		
	//Le avvertenze sono per gli Scanner non chiusi (System.in è sconsigliato chiuderlo) e per il "non utilizzo" di daButtare
	
	//Serve per l'InputDati.input di numeri da un minimo a un massimo consentiti
	public static int controlloInput(int min, int max) {
		Scanner leggiInput= new Scanner(System.in);
		String daButtare;
		boolean verificato=false;
		int scelta=0;
		do {
			if(leggiInput.hasNextInt()) {
				scelta=leggiInput.nextInt();
				verificato=true;
			}else {
				verificato=false;
				daButtare=leggiInput.next();
				System.out.println("Scelta non valida, scegli un numero tra "+min+" e "+max+"\nScegli di nuovo:");
			}
			if(verificato==true) {
				if(scelta<min || scelta>max) {
					System.out.println("Scelta non valida, scegli un numero tra "+min+" e "+max+"\nScegli di nuovo:");
					verificato=false;
				}
				else
					verificato=true;
			}
		}while(verificato==false);
		return scelta;
	}
	
	//Serve per l'input di un nome, non vi sono vincoli
	public static String inputNome() {
		Scanner leggi=new Scanner(System.in);
		System.out.println("Inserisci il nome:");
		String str=leggi.nextLine();
		return str;
	}
	
	//Serve per l'input della massa di un corpo celeste
	public static int inputMassa() {
		Scanner leggiMassa=new Scanner(System.in);
		System.out.println("\nInserisci la massa:");
		String daButtare;
		int massa=0;
		boolean valido=false;
		while(valido==false) {
			if(leggiMassa.hasNextInt()) {
				massa=leggiMassa.nextInt();
				valido=true;
			}
			else {
				System.out.println("Massa non valida, inserisci un numero!\nInserisci la massa:");
				daButtare = leggiMassa.next();
			}
			if(valido==true) {
				if(massa<=0) {
					System.out.println("Massa non valida, inserisci un numero positivo\nInserisci la massa:");
					valido=false;
				}else
					valido=true;
			}
		}
		return massa;
	}
	
	//Serve per l'input di una coordinata
	public static int inputCoordinata(String c) {
		Scanner leggiCoordinata=new Scanner(System.in);
		System.out.println("Inserisci la coordinata "+c+":");
		int coordinata=0;
		boolean valido=false;
		String daButtare;
		while(valido==false) {
			if(leggiCoordinata.hasNextInt()) {
				coordinata=leggiCoordinata.nextInt();
				valido=true;
			}
			else {
				System.out.println("Coordinata non valida, inserisci un numero!\nInserisci la coordinata "+c+":");
				daButtare=leggiCoordinata.next();
			}
		}
		return coordinata;
	}
	
	//Input per il nome di un pianeta attorno a cui una luna deve orbitare
	public static String inputNomePianeta() {
		Scanner leggiPianeta=new Scanner(System.in);
		System.out.println("Inserisci il nome del pianeta attorno a cui orbita:");
		String nome;
		nome=leggiPianeta.nextLine();
		return nome;
	}
}
