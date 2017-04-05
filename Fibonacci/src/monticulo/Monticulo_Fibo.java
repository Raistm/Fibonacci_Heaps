package monticulo;

public class Monticulo_Fibo {

		public Nodo minimo; //Puntero al m�nimo del mont�culo
		public int numNodosRaiz; //N�mero de nodos ra�z 
		public int numNodos; //N�mero de nodos total
		
		public Monticulo_Fibo(){
			this.minimo = null;
			this.numNodosRaiz = 0;
			this.numNodos = 0;
		}
		
		public void insertar(Nodo nuevo){ //Inserta una nueva clave en el mont�culo
			insertarListaRaices(nuevo);
			if (this.minimo == null || nuevo.valor < this.minimo.valor) this.minimo = nuevo;
			this.numNodosRaiz++;
			this.numNodos++;
		}
		
		public void insertarListaRaices(Nodo node){ //Crea los links a los nodos hermanos
			if (this.numNodosRaiz == 0){ //En caso de que sea el primer nodo insertado, se enlaza a s� mismo
				node.hermanoDer = node;
				node.hermanoIzq = node;
			}
			else { //Se pone a la izquierda del m�nimo y actualiza los enlaces
				node.hermanoIzq = this.minimo.hermanoIzq;
				node.hermanoDer = this.minimo;
				this.minimo.hermanoIzq.hermanoDer = node;
				this.minimo.hermanoIzq = node;
			}
		}
		
		public int getMinimo(){ //Devuelve el valor del m�nimo del mont�culo
			if (this.minimo != null) return this.minimo.valor;
			else{
				System.out.println("No hay ning�n elemento en el mont�culo");
				return -1;
			}
		}
		
		public void eliminaMinimo(){
			if (this.minimo != null){
				//Eliminamos la lista de hijos y los ponemos en la lista de raices del mont�culo
				if (this.minimo.hijo != null){
					subirHijos(this.minimo);
				}
				else { //Si no tiene hijos desvinculamos los hermanos del minimo
					this.minimo.hermanoDer.hermanoIzq = this.minimo.hermanoIzq;
					this.minimo.hermanoIzq.hermanoDer = this.minimo.hermanoDer;
					this.numNodosRaiz--;
				}
				//Con este if-else se elimina toda la vinculaci�n que ten�a el mont�culo con el m�nimo anterior y se apunta a uno nuevo
				if (this.minimo.hermanoDer == this.minimo) this.minimo = null; 
				else{
					this.minimo = this.minimo.hermanoDer;
					consolidar();
				}
			}
		}
		
		public void subirHijos(Nodo node){ //Suma el n�mero de nodos que se a�aden a la lista de ra�ces, rompe los enlaces de los hijos con el padre y enlaza los nuevos nodos
			contarHijosSubidos();
			Nodo aux = new Nodo();
			aux = this.minimo.hijo.hermanoIzq;
			//Se actualizan 4 nodos, el de la izquierda del m�nimo, el de su derecha y los hijos que se enlazar�n a ellos que son:
			//El hijo que tiene el enlace del m�nimo y el izquierdo a �ste, quedando los dem�s hijos en medio de ellos
			this.minimo.hijo.hermanoIzq = this.minimo.hermanoIzq;
			aux.hermanoDer = this.minimo.hermanoDer;
			this.minimo.hermanoIzq.hermanoDer = this.minimo.hijo;
			this.minimo.hermanoDer.hermanoIzq = aux;
		}
		
		public void contarHijosSubidos(){ //Suma los hijos que va a poner en la lista de ra�ces y marca los padres a null
			Nodo aux = this.minimo.hijo.hermanoDer;
			while (aux != this.minimo.hijo){
				this.numNodosRaiz++;
				aux.padre = null;
				aux = aux.hermanoDer;
			}
			aux.padre = null;	
		}
		
		public void consolidar(){
			Nodo[] listaAux = new Nodo[this.numNodos]; //La lista auxiliar A
			for (int i=0; i < this.numNodos; i++){ //Inicializamos la lista
				listaAux[i] = null;
			}
			Nodo x = this.minimo; //Nodo X
			for (int j=0; j < this.numNodosRaiz; j++){
				if (listaAux[x.grado] == x) j--; //Excepci�n que aparece cuando inserto despu�s de borrar una vez el m�nimo y vuelvo a borrar, hace que no acabe el bucle antes de tiempo
				while (listaAux[x.grado] != null && listaAux[x.grado] != x){ //while A[d] != nil
					Nodo y = listaAux[x.grado]; //Nodo Y
					if (x.valor > y.valor){ //if key[x] > key[y] 
						Nodo aux2 = x; //
						x = y;         // EXCHANGE 
						y = aux2;      //
					}
					enlazarFib(x,y);
					listaAux[y.grado] = null;
				}
				listaAux[x.grado] = x;
				x = x.hermanoDer;
			}
			this.minimo = null; //Marcamos que no hay minimo
			this.numNodos--; //Disminuimos en 1 el numero de nodos total porque hemos terminado de eliminar el minimo por completo
			this.numNodosRaiz = 0; //Empezamos a contar los nodos ra�z
			for (int i=0; i < this.numNodos; i++){
				if (listaAux[i] != null){ //if A[i] != nil
					if (this.minimo == null || listaAux[i].valor < this.minimo.valor) this.minimo = listaAux[i];
					this.numNodosRaiz++;
				}
			}
		}
		
		public void enlazarFib(Nodo x, Nodo y){ //Fib-Heap-Link
			//Quitar y de la lista de nodos ra�z
			Nodo aux = new Nodo();
			aux = y;
			//El nodo y se quita de la lista por lo que los hermanos apuntan a sus nuevos hermanos
			y.hermanoDer.hermanoIzq = aux.hermanoIzq;
			y.hermanoIzq.hermanoDer = aux.hermanoDer;
			y.padre = x;
			if (x.hijo == null){ //Si no ten�a hijos lo pongo como nuevo hijo apuntando a s� mismo
				x.hijo = y;
				y.hermanoDer = y;
				y.hermanoIzq = y;
			}
			else {
				//El hijo de x ahora apunta al nuevo hijo para que todos los enlaces est�n como deben 
				y.hermanoIzq = x.hijo.hermanoIzq;
				y.hermanoDer = x.hijo;
				x.hijo = y;
				y.hermanoDer.hermanoIzq = y;
				y.hermanoIzq.hermanoDer = y;				
			}
			x.grado++;
			y.marca = false;
		}
		
		public void decrementar(Nodo x, int nuevoVal){ //Funci�n decrease-key
			if (x != null){
				if (nuevoVal > x.valor){
					System.out.println("Error, el nuevo valor es mayor que el anterior");
				}
				else {
					x.valor = nuevoVal; //key[x] <- k 
					Nodo y = x.padre; //y <- p[x]
					if (y != null && x.valor < y.valor){
						corte(x, y);
						corteEnCascada(y);
					}
					if (x.valor < this.minimo.valor){
						this.minimo = x; //min(H) <- x
					}
				}
			}
			else System.out.println("El nodo seleccionado no existe");
		}
		
		public void corte(Nodo x, Nodo y){
			//Quito el nodo de la lista de hijos
			quitarHijo(x, y);
			insertarListaRaices(x); 
			this.numNodosRaiz++; 
			y.grado--;
			x.padre = null;
			x.marca = false;
		}
		
		public void quitarHijo(Nodo x, Nodo y){
			if (x.hermanoDer != x){ //Si x no es "hijo �nico"
				if (y.hijo == x) y.hijo = x.hermanoDer;
				x.hermanoDer.hermanoIzq = x.hermanoIzq;
				x.hermanoIzq.hermanoDer = x.hermanoDer;
			}
			else y.hijo = null; //Si x s�lo tiene un hijo lo desvinculo
		}
		
		public void corteEnCascada(Nodo y){
			Nodo z = y.padre;
			if (z != null){
				if (y.marca == false){
					y.marca = true;
				}
				else {
						corte(y, z);
						corteEnCascada(z);
				}
			}
		}
	
		////////// Funciones auxiliares de muestreo /////////////

		public void imprimeMonticulo(){
			if (this.minimo != null){
				int[] lista = new int[this.numNodos];
				int contadorAux = 0;
				Nodo aux = new Nodo(this.minimo);
				System.out.println("Valores (empezando desde el m�nimo hacia la derecha)"); 
				for (int i=0; i < this.numNodosRaiz; i++){
					if (aux.marca) {
						lista[contadorAux] = aux.valor;
						contadorAux++;
					}
					System.out.print(aux.valor);
					imprimeMonticulo(aux, lista, contadorAux);
					if (i < this.numNodosRaiz-1) System.out.print(" - ");
					aux = aux.hermanoDer;
				}
				System.out.println();
				System.out.println("El m�nimo del mont�culo es: " + this.getMinimo());
				System.out.println("El n�mero de nodos ra�z es: " + this.numNodosRaiz);
				System.out.println("El n�mero de nodos totales es: " + this.numNodos);
				if (contadorAux != 0) {
					System.out.println("Los nodos marcados son: ");
					for (int i=0; i <= contadorAux; i++){
						if (i != contadorAux) System.out.println(lista[i] + ", ");
						else System.out.println(lista[i]);
					}
				}
			}
		}
		
		public void imprimeMonticulo(Nodo n, int[] lista, int contadorAux){
			if (n.hijo != null){
				System.out.print("(");
				System.out.print(n.hijo.valor);
				if (n.hijo.marca) {
					lista[contadorAux] = n.hijo.valor;
					contadorAux++;
				}
				if (n.hijo.hermanoDer != n.hijo) System.out.print(" ");
				imprimeMonticulo(n.hijo, lista, contadorAux);
				Nodo aux = new Nodo();
				aux = n.hijo.hermanoDer;
				while (n.hijo != aux){
					System.out.print(aux.valor);
					if (aux.marca) {
						lista[contadorAux] = aux.valor;
						contadorAux++;
					}
					if (aux.hermanoDer != n.hijo) System.out.print(" ");
					imprimeMonticulo(aux, lista, contadorAux);
					aux = aux.hermanoDer;
				}
				System.out.print(") ");
			}
		}			
}
