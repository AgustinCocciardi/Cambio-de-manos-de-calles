package calles;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Recorrido {

	private int cantidadEsquinas;
	private int cantidadCalles;
	private int[][] matrizAdyacencia;
	private int[] precedencias;
	private int esquinaColectivo;
	private int esquinaEscuela;
	private int distancia;
	private ArrayList<Calle> calles = new ArrayList<Calle>();
	private ArrayList<Integer> callesACambiar = new ArrayList<Integer>();
	private ArrayList<Integer> noSolucion = new ArrayList<Integer>();
	
	public Recorrido(Scanner entrada) {
		int nodo1, nodo2, costo;
		this.cantidadEsquinas = entrada.nextInt();
		this.esquinaColectivo = entrada.nextInt() - 1;
		this.esquinaEscuela = entrada.nextInt() - 1;
		this.cantidadCalles = entrada.nextInt();
		this.matrizAdyacencia = new int[this.cantidadEsquinas][this.cantidadEsquinas];
		this.precedencias = new int[this.cantidadEsquinas];
		Arrays.fill(precedencias, this.esquinaColectivo);
		for(int[] rows : matrizAdyacencia)
			Arrays.fill(rows, Integer.MAX_VALUE);
		for(int i=0; i<this.cantidadCalles; i++) {
			nodo1 = entrada.nextInt() - 1;
			nodo2 = entrada.nextInt() - 1;
			costo = entrada.nextInt();
			this.calles.add(new Calle(nodo1,nodo2));
			this.matrizAdyacencia[nodo1][nodo2] = costo;
			this.matrizAdyacencia[nodo2][nodo1] = costo;
		}
		for(int i=0; i<this.cantidadEsquinas; i++) {
			this.noSolucion.add(i);
		}
	}
	
	private void calcularDijkstra() {
		Integer nodoW;
		this.noSolucion.remove(esquinaColectivo);
		while(this.noSolucion.isEmpty() == false) {
			nodoW = this.noSolucion.get(0);
			for(int i=1; i<this.noSolucion.size(); i++) {
				if(this.matrizAdyacencia[esquinaColectivo][nodoW] > 
				   this.matrizAdyacencia[esquinaColectivo][this.noSolucion.get(i)]) {
					nodoW = this.noSolucion.get(i);
				}
			}
			this.noSolucion.remove(nodoW);
			for(int i=0; i<this.noSolucion.size(); i++) {
				if(matrizAdyacencia[nodoW][this.noSolucion.get(i)] != Integer.MAX_VALUE) {
					if(matrizAdyacencia[esquinaColectivo][this.noSolucion.get(i)] > 
					   (matrizAdyacencia[esquinaColectivo][nodoW] + matrizAdyacencia[nodoW][this.noSolucion.get(i)])) {
						matrizAdyacencia[esquinaColectivo][this.noSolucion.get(i)] = (matrizAdyacencia[esquinaColectivo][nodoW] + matrizAdyacencia[nodoW][this.noSolucion.get(i)]);
						this.precedencias[this.noSolucion.get(i)] = nodoW;
					}
				}
			}
		}
	}
	
	private void callesACambiarYCalcularDistancia() {
		int  i, esquina1 = this.precedencias[this.esquinaEscuela], esquina2 = this.esquinaEscuela;
		boolean calleEncontrada;
		while(esquina1 != esquina2) {
			i = 0;
			calleEncontrada = false;
			while(i < this.calles.size() && calleEncontrada == false) {
				if((esquina1 == calles.get(i).getEsquina1() && esquina2 == calles.get(i).getEsquina2()) || 
					(esquina1 == calles.get(i).getEsquina2() && esquina2 == calles.get(i).getEsquina1())) {
					calleEncontrada = true;
					if(esquina1 == calles.get(i).getEsquina2() && esquina2 == calles.get(i).getEsquina1()){
						this.callesACambiar.add(this.calles.get(i).getNumeroCalle());
					}
					esquina2 = esquina1;
					esquina1 = this.precedencias[esquina2];
				}
				i++;
			}
		}
		
		this.distancia = this.matrizAdyacencia[this.esquinaColectivo][this.esquinaEscuela];
	}
	
	public void resolver(PrintWriter salida) {
		this.calcularDijkstra();
		this.callesACambiarYCalcularDistancia();
		salida.println(this.distancia);
		for(int i=0; i<this.callesACambiar.size(); i++) {
			salida.print(this.callesACambiar.get(i) + " ");
		}
	}
	
	public static void main(String[] args) throws IOException {
		Scanner entrada = new Scanner(new FileReader("cambio.in"));
		Recorrido recorrido = new Recorrido(entrada);
		entrada.close();
		PrintWriter salida = new PrintWriter(new FileWriter("cambio.out"));
		recorrido.resolver(salida);
		salida.close();
	}

}
