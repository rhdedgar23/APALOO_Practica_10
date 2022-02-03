import java.util.Scanner;

public class Principal {
	
	public static void main(String[] args) {
		
		Cola<String> exprPosf= new Cola<String>();
		ListaIdent<Variable> listaIdent= new ListaIdent<Variable>();
		int cont=1;
		
		while(cont==1) {
			try (Scanner teclado = new Scanner(System.in)) {
				System.out.println("Por favor ingresa tu ecuacion en notacion posfija: ");
				String ecuacion= teclado.nextLine();
				AnalizaEcuacion(ecuacion, exprPosf, listaIdent);	
		
				//listaIdent.inserta("a1", 8.0f);
				//listaIdent.inserta("b1", 4.0f);
				//listaIdent.inserta("c", 10.0f);
				//listaIdent.despliega();
		
				System.out.println("El resultado de tu expresion posfija es: " + AnalizaCola(exprPosf, listaIdent));
				System.out.println("Deseas ingresar otra ecuacion posfija?\nSi -> 1\nNo -> 2");
				cont= teclado.nextInt();
			}	
		}
	}
	
	//parametros una cadena y una cola; se separan sus tokens;
	//se analizan los tokens; si son operandos o valores numericos, se guardan en la cola;
	//si son operandos identificadores, TAMBIEN se guarda en la lista ListaIdent, preguntando al usuario su valor;
	public static void AnalizaEcuacion(String ecuacion, Cola<String> cola, ListaIdent<Variable> lista) {
		
		try (Scanner teclado2 = new Scanner(System.in)) {
			String []tokens;
			tokens= ecuacion.split(" ");
			
			for(int i=0; i<tokens.length; i++) {
				//System.out.println(tokens[i]);
				//System.out.println("Hola");
				if(AnalizaToken(tokens[i])==1) {
					cola.encola(tokens[i]);
					System.out.println("Se ha agregado el token " + tokens[i] + " a la cola.");
					
					if(lista.busca(tokens[i])==false) {
						System.out.println("Ingresa el valor de la variable " + tokens[i] + " :");
						float valorVar= teclado2.nextFloat();
						lista.inserta(tokens[i], valorVar);
					}
				}
				else {
					cola.encola(tokens[i]);
					System.out.println("Se ha agregado el token " + tokens[i] + " a la cola.");
				}
			}	
		}
	}
	
	public static int AnalizaToken(String token) {
		
		if(token.matches("[a-zA-z].*")) {
			return 1;//si contiene letras, es un identificador
		}
		else {
			if(token.matches("\\d")) {
				return 2;//si no contiene letras, entonces puede ser un valor numerico o un operador. Si tiene un digito, es un valor nuemrico
			}
			else {
				return 3;//si no es ni identificador ni un valor numerico, entonces es un operador. 
			}		
		}
	}
	
	public static double AnalizaCola(Cola<String> cola, ListaIdent<Variable> listaIdent) {
		Pila<Double> pila= new Pila<Double>();
		double e2, e1;
		
		while(cola.esVacia()==false) {
			
			String elemCola= cola.valorFrente();
			int tipo= AnalizaToken(elemCola);
			
			switch(tipo) {
			case 1://identificador; su valor se mete en la pila
				System.out.println("Identificador: " + elemCola);
				pila.push(listaIdent.getValor(elemCola));
				cola.desencola();
				System.out.println("Se ha metido " + elemCola + " a la pila y eliminado de la cola.");
				break;
			case 2: 
				pila.push(Double.valueOf(elemCola));
				cola.desencola();
				break;
			case 3:
				System.out.println("Operador: " + elemCola);
				cola.desencola();
				
				System.out.println("Valor tope de pila: " + pila.valorTope());
				e2= pila.valorTope();
				pila.pop();
				System.out.println("El nuevo valor tope es: " + pila.valorTope());
				e1= pila.valorTope();
				pila.pop();
				
				System.out.println("La operacion es entonces: " + e1 + " " + elemCola + " " + e2 + " = " + Operacion(elemCola, e1, e2, pila));
				break;
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
		}
		
		return valorOperacion;
	}
}
