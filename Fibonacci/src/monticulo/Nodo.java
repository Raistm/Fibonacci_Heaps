package monticulo;

public class Nodo {
	int grado; //Grado del nodo
	int valor; //Valor del nodo
	Nodo hermanoIzq; //Puntero al nodo de la izquierda
	Nodo hermanoDer; //Puntero al nodo de la derecha
	Nodo padre; //Puntero al nodo padre
	Nodo hijo; //Puntero a su hijo m�s reciente
	boolean marca; //Indica si el nodo ha perdido un hijo desde la �ltima vez que el nodo se puso como hijo de otro nodo
	
	public Nodo(){
		this.grado = 0;
		this.hermanoIzq = null;
		this.hermanoDer = null;
		this.padre = null;
		this.hijo = null;
		this.marca = false;
	}
	
	public Nodo(int value){
		this.grado = 0;
		this.valor = value;
		this.hermanoIzq = null;
		this.hermanoDer = null;
		this.padre = null;
		this.hijo = null;
		this.marca = false;
	}
	
	public Nodo(Nodo node){
		this.grado = node.grado;
		this.valor = node.valor;
		this.hermanoDer = node.hermanoDer;
		this.hermanoIzq = node.hermanoIzq;
		this.padre = node.padre;
		this.hijo = node.hijo;
		this.marca = node.marca;
	}
	
	////// Funci�n para pruebas, no vale realmente //////
	public void crearHijo(Nodo hijo){ //Funci�n auxiliar para probar r�pidamente las funciones que suben los hijos o los marcan, no vale realmente ya que no aumenta el n�mero de nodos total
		if (this.hijo != null) {
			Nodo aux = new Nodo();
			aux = this.hijo;
			hijo.hermanoDer = this.hijo;
			hijo.hermanoIzq = aux.hermanoIzq;
			this.hijo.hermanoIzq.hermanoDer = hijo;
			this.hijo.hermanoIzq = hijo;
		}
		else {
			hijo.hermanoDer = hijo;
			hijo.hermanoIzq = hijo;
		}	
		this.hijo = hijo;
		hijo.padre = this;
		this.grado++;
	}
}
