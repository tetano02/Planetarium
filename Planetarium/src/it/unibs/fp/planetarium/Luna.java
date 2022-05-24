package it.unibs.fp.planetarium;

public class Luna extends CorpoCeleste {
	
	private Pianeta centro; //indica il pianeta attorno a cui ruota
	
	public Luna(int massa, Posizione pos,String nome, Pianeta centro) {
		super();
		this.setMassa(massa);
		this.setPos(pos);
		this.setNome(nome);
		this.centro = centro;
	}
	
	public Pianeta getCentro() {
		return this.centro;
	}
	
	public String getNomePianeta() {
		return this.centro.getNome();
	}
	
	public double getPercorso() {
		double percorso=0;
		percorso += Math.sqrt(Math.pow(this.getPos().getX() - this.centro.getPos().getX(),2) + Math.pow(this.getPos().getY() - this.centro.getPos().getY(), 2));
		percorso += Math.sqrt(Math.pow(this.centro.getPos().getX(), 2) + Math.pow(this.centro.getPos().getY(), 2)); //inserisco solo una coordinata poiché le coordinate della stella sono (0, 0)
		return percorso;
	}
	
	public void getPercorsoNomi() {
		System.out.println("La tratta da effettuare per arrivare è "+this.getCentro().getCentro().getNome() + " > " + this.getCentro().getNome() + " > " + this.getNome());
		System.out.println("Quanta strada! Ben "+this.getPercorso()+" migliaia di kilometri!");
	}
}