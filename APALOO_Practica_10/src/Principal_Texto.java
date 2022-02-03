import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Principal_Texto {
	
	public static void main(String[] args) {
		
		Cola<String> exprPosf= new Cola<String>();
		ListaIdent<Variable> listaIdent= new ListaIdent<Variable>();
		
		try {
			FileReader archiEntrada= new FileReader("src\\expresiones.txt");
			FileWriter archiSalida= new FileWriter("src\\resultados.txt");
			Scanner lector = new Scanner(archiEntrada);
			int cont=1;
			double resultado;
			
			while(lector.hasNextLine()) {
				
				String ecuacion= lector.nextLine();
				System.out.println("Ecuacion " + cont + ": " + ecuacion);
				archiSalida.write("Ecuacion " + cont + ": " + ecuacion + "\n");
				
				AnalizaEcuacion(ecuacion, exprPosf, listaIdent, archiSalida);
				
				resultado= AnalizaCola(exprPosf, listaIdent, archiSalida);
				
				System.out.println("El resultado de tu expresion posfija es: " + resultado + "\n");
				archiSalida.write("El resultado de tu expresion posfija es: " + Double.toString(resultado) + "\n\n");
				
				cont++;
			}
			
			lector.close();
			archiSalida.close();
		} 
		catch (FileNotFoundException e) {
			System.out.println("ERROR! El archivo no se ha encontrado.");
			e.printStackTrace();
		}
		catch(IOException e) {
			System.out.println("ERROR al escribir en el archivo.");
			e.printStackTrace();
		}
	}
	
	//parametros una cadena y una cola; se separan sus tokens;
	//se analizan los tokens; si son operandos o valores numericos, se guardan en la cola;
	//si son operandos identificadores, TAMBIEN se guarda en la lista ListaIdent, preguntando al usuario su valor;
	public static void AnalizaEcuacion(String ecuacion, Cola<String> cola, ListaIdent<Variable> lista, FileWriter archiSalida) {
		
		try (Scanner teclado2 = new Scanner(System.in)) {
			String []tokens;
			tokens= ecuacion.split(" ");
			
			for(int i=0; i<tokens.length; i++) {
				//System.out.println(tokens[i]);
				//System.out.println("Hola");
				if(AnalizaToken(tokens[i])== 1) {
					
					cola.encola(tokens[i]);
					System.out.println("Se ha agregado el token " + tokens[i] + " a la cola.");
					archiSalida.write("Se ha agregado el token " + tokens[i] + " a la cola.\n" );
					
					if(lista.busca(tokens[i])==false) {
						System.out.println("Ingresa el valor de la variable " + tokens[i] + " :");
						archiSalida.write("Ingresa el valor de la variable " + tokens[i] + " :\n");
						
						float valorVar= teclado2.nextFloat();
						archiSalida.write(Float.toString(valorVar) + "\n");
						lista.inserta(tokens[i], valorVar);
					}
				}
				else {
					cola.encola(tokens[i]);
					System.out.println("Se ha agregado el token " + tokens[i] + " a la cola.");
					archiSalida.write("Se ha agregado el token " + tokens[i] + " a la cola.\n");
				}
			}	
		}
		catch(IOException e) {
			System.out.println("ERROR al escribir en el archivo.");
			e.printStackTrace();
		}
	}
	
	public static int AnalizaToken(String token) {
		
		if(token.matches("[a-zA-z].*")) {
			if(token.matches("[\\^]")){//si contiene letras, es un identificador
				return 3;
			}
			else {
				return 1;
			}
		}
		else {
			if(token.matches("\\d+")) {
				return 2;//si no contiene letras, entonces puede ser un valor numerico o un operador. Si tiene un digito, es un valor nuemrico
			}
			else {
				return 3;//si no es ni identificador ni un valor numerico, entonces es un operador. 
			}		
		}
	}
	
	public static double AnalizaCola(Cola<String> cola, ListaIdent<Variable> listaIdent, FileWriter archiSalida) {
		Pila<Double> pila= new Pila<Double>();
		double e2, e1, operacion;
		
		while(cola.esVacia()==false) {
			
			String elemCola= cola.valorFrente();
			int tipo= AnalizaToken(elemCola);
			
			try{
				switch(tipo) {
			
				case 1://identificador; su valor se mete en la pila
					System.out.println("Identificador: " + elemCola);
					pila.push(listaIdent.getValor(elemCola));
					cola.desencola();
				
					System.out.println("Se ha metido " + elemCola + " a la pila y eliminado de la cola.");
					archiSalida.write("Se ha metido " + elemCola + " a la pila y eliminado de la cola.\n" );
				
					break;
				case 2://valor numerico
					pila.push(Double.valueOf(elemCola));
					cola.desencola();
					break;
				case 3://operador
					System.out.println("Operador: " + elemCola);
					cola.desencola();
				
					System.out.println("Valor tope de pila: " + pila.valorTope());
					archiSalida.write("Valor tope de pila: " + pila.valorTope() + "\n");
					e2= pila.valorTope();
					pila.pop();
					
					System.out.println("El nuevo valor tope es: " + pila.valorTope());
					archiSalida.write("El nuevo valor tope es: " + pila.valorTope() + "\n");
					e1= pila.valorTope();
					pila.pop();
					
					operacion= Operacion(elemCola, e1, e2, pila);
					
					System.out.println("La operacion es entonces: " + e1 + " " + elemCola + " " + e2 + " = " + operacion);
					archiSalida.write("La operacion es entonces: " + e1 + " " + elemCola + " " + e2 + " = " + operacion + "\n");
					break;
				}
			}
			catch(IOException e) {
				System.out.println("ERROR al escribir en el archivo.");
				e.printStackTrace();
			}
		}
		
		return pila.valorTope();
	}
	
	public static double Operacion(String elemCola, double e1, double e2, Pila<Double> pila) {
		double valorOperacion=0;
		
		switch(elemCola) {
		case "/":
			valorOperacion= e1/e2;
			pila.push(valorOperacion);
			break;
		case "*": 
			valorOperacion= e1*e2;
			pila.push(valorOperacion);
			break;
		case "+":
			valorOperacion= e1+e2;
			pila.push(valorOperacion);
			break;
		case "-":
			valorOperacion= e1-e2;
			pila.push(valorOperacion);
			break;
		case "^":
			valorOperacion= Math.pow(e1, e2);
			pila.push(valorOperacion);
		}
		
		return valorOperacion;
	}
}
