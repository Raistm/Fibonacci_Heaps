package monticulo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) throws IOException{
		Monticulo_Fibo fib = new Monticulo_Fibo();
		////////////////////////// MAIN PRINCIPAL ////////////////////////////
		System.out.println("         Monticulos de Fibonacci \n\n");
		long time_start, time_end, sum=0;
		// Montículo vacío //
		fib = new Monticulo_Fibo();   
		Integer num, n ;
		File input = new File (args[0]); //Fichero con las operaciones a realizar en el monticulo
		FileWriter output = new FileWriter(args[1]); //Fichero para escribir de forma detalla las operaciones 
		PrintWriter fw = new PrintWriter(output); //Para escribir en el texto de salidaOps.txt
		Scanner scan;
		int contadorAux = 0;
		try {
			scan = new Scanner(input);
			String cadena;
		    while(scan.hasNextLine() ) {
		    	cadena = scan.nextLine();
		    	cadena.trim();
		        if(cadena.contentEquals("insertar")){  //Operación insertar
		        	fw.println("Operación: " + cadena);
		        	n = scan.nextInt();//Número de elementos a insertar
		        	fw.println("Numero de elementos a insertar: " + n);
		            for (int i = 0 ; i < n ; i++){                	
		            	num = scan.nextInt(); //Elemento a insertar en el montículo
		            	fw.print("Elemento: "+ num + " -> ");	                	
		            	time_start = System.nanoTime();
		            	fib.insertar(new Nodo(num));
		            	time_end = System.nanoTime();
		            	sum = sum + ( time_end-time_start);
		        		System.out.println("Tiempo de inserción "+ ( time_end - time_start ) +" nanosegundos");
		            	fw.println("Tiempo de inserción "+ ( time_end - time_start ) +" nanosegundos");         
		                fw.println("Numero de elementos del monticulo de Fibonacci  " +  fib.numNodos);
		            }      
		            fw.println("Numero de elementos del montículo de Fibonacci  "+  fib.numNodos );
		            fw.println("Numero de elementos raíz del montículo de Fibonacci " + fib.numNodosRaiz);
		            fw.println();
		            fib.imprimeMonticulo();
		            System.out.println();
		        }
		        else if (cadena.contentEquals("borrar minimo")){//Operación eliminar el mínimo
		        	fw.println("Operación: " + cadena);
		        	time_start = System.nanoTime();
		    		fib.eliminaMinimo(); 
		    		time_end = System.nanoTime();
		    		fw.println("Tiempo de eliminación del mínimo " + ( time_end - time_start ) + " nanosegundos");
		    		fw.println("Numero de elementos del monticulo de Fibonacci  " +  fib.numNodos);
		    		fw.println("Numero de elementos raíz del montículo de Fibonacci " + fib.numNodosRaiz);
		    		fw.println();
		    		System.out.println("Tiempo de eliminación mínimo " + ( time_end - time_start ) + " nanosegundos");
		    		fib.imprimeMonticulo();
		    		System.out.println();
		        }
		        else if (cadena.contentEquals("decrecer clave")){ //Operación decrementar clave
		        	fw.println("Operación: " + cadena);
		        	Nodo auxiliar = new Nodo();
		        	if (args[0].contentEquals("prueba1.txt")){
		        		if (contadorAux == 0) {
		        			auxiliar = fib.minimo.hijo.hijo;
		        			contadorAux++;
		        		}
		        		else {
		        			auxiliar = fib.minimo.hermanoDer.hijo.hijo;
		        		}
		        	}
		        	else if (args[0].contentEquals("prueba2.txt")){
		        		if (contadorAux == 0) {
		        			auxiliar = fib.minimo.hijo.hijo.hermanoIzq;
		        			contadorAux++;
		        		}
		        		else {
		        			auxiliar = fib.minimo.hijo.hijo.hermanoDer; 
		        		}
		        	}
		        	else {
		        		if (contadorAux == 0) {
		        			auxiliar = fib.minimo.hermanoDer.hijo.hermanoIzq;
		        			contadorAux++;
		        		}
		        		else auxiliar = fib.minimo.hermanoDer.hijo;
		        	}
		        	int nuevoVal;
		        	nuevoVal = scan.nextInt();
		        	fw.print("Elemento a modificar: " + auxiliar.valor);        
		        	fw.println(" por -> "+ nuevoVal);
	    			time_start = System.nanoTime();
	    			fib.decrementar(auxiliar, nuevoVal);
	    		    time_end = System.nanoTime();
	    		    sum+= time_end - time_start ;
	    		    fw.println("Tiempo de decrecer clave " + ( time_end - time_start ) + " nanosegundos");
	    		    fw.println();
	    		    System.out.println("Tiempo de decrecer clave "+ ( time_end - time_start ) +" nanosegundos");
	    		    fw.println("Numero de elementos del monticulo de Fibonacci  "+  fib.numNodos );
	    		    fw.println("Numero de elementos raíz del montículo de Fibonacci " + fib.numNodosRaiz);
	    		    fib.imprimeMonticulo();   
		        }
		        else if (cadena.contentEquals("minimo")){ //Operación obtener el mínimo
		        	fw.println("Operación: "+cadena);
		        	time_start = System.nanoTime();
		    		num=fib.getMinimo(); 
		    		time_end = System.nanoTime();
		    		fw.println("Tiempo de buscar el mínimo ( "+ num +" )"+ ( time_end - time_start ) +" nanosegundos");
		    		fw.println();
		    		System.out.println("Tiempo de buscar el mínimo ( "+ num +" )" + ( time_end - time_start ) +" nanosegundos");
		        }
		}
		scan.close();
		fw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
