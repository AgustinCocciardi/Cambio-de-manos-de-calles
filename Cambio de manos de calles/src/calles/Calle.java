package calles;

public class Calle {
	
	private int esquina1;
	private int esquina2;
	private int numeroCalle;
	private static int numero = 1;
	
	public Calle(int esquina1, int esquina2) {
		this.esquina1 = esquina1;
		this.esquina2 = esquina2;
		this.numeroCalle = numero;
		numero++;
	}
	
	public int getEsquina1() {
		return this.esquina1;
	}
	
	public int getEsquina2() {
		return this.esquina2;
	}
	
	public int getNumeroCalle() {
		return this.numeroCalle;
	}
	
}
