package it.unibs.fp.planetarium;

public class CorpoCeleste {
	
	//SuperClasse
	
	private int massa;
	
	private Posizione pos;
	
	private String codice;
	
	private String nome;

	public int getMassa() {
		return massa;
	}

	public void setMassa(int massa) {
		this.massa = massa;
	}

	public Posizione getPos() {
		return pos;
	}

	public void setPos(Posizione pos) {
		this.pos = pos;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}