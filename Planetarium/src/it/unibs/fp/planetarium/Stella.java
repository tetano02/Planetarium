package it.unibs.fp.planetarium;

public class Stella extends CorpoCeleste{
	
	public Stella(int massa, String nome){
		super();
		this.setMassa(massa);
		this.setOrigine();
		this.setNome(nome);
		//La stella è solo una, codice univoco di default
		this.setCodice("S000000000");
	}
	
	//La stella ha sempre posizione (0,0)
	private void setOrigine() {
		Posizione origine=new Posizione(0,0);
		this.setPos(origine);
	}
	
}
