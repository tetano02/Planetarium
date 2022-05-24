package it.unibs.fp.planetarium;

public class Pianeta extends CorpoCeleste{
	
	private Stella centro;
	
	public Pianeta(int massa, Posizione pos, String nome){
		super();
		this.setMassa(massa);
		this.setNome(nome);
		this.setPos(pos);
		this.centro=SistemaStellare.stella;
	}
	
	public Stella getCentro() {
		return this.centro;
	}
	
}