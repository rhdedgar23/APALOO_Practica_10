import java.util.LinkedList;

public class ListaIdent<Tipo> implements Lista<Tipo> {
	LinkedList<Variable> list;
	Variable ident;
	
	public ListaIdent(){
		this.list= new LinkedList<Variable>();
	}
	
	public boolean busca(String ident) {//metodo-funcion que busca en la lista list a un objeto mediante su identificador que es un string
		int i=0;
		
		while(i<list.size() && ident.compareTo(((Variable)(list.get(i))).getNombre()) != 0) {
			i++;
		}
		if(i==list.size()) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public void inserta(String ident, float valor) {
		
		if(busca(ident)==true) {
			System.out.println("Este identificador ya esta guardado");
		}
		else {
			Variable elem= new Variable(ident, valor);
			list.addLast(elem);
		}
	}
	
	public double getValor(String ident) {
		int i = 0;
		double valor = 0;
		
		while(i < list.size() && ident.compareTo(((Variable) (list.get(i))).getNombre()) != 0) {
			i++;
		}
		
		if (i < list.size()) {
			valor = ((Variable)(list.get(i))).getValor();
		}
		
		return valor;
	}
	
	public void despliega() {
		
		for(int i=0; i<list.size(); i++) {
			System.out.println(((Variable)(list.get(i))).getNombre() + ((Variable)list.get(i)).getValor());
		}
	}
	
	public void hazNula() {
		list.clear();
	}
	
	public boolean esVacia() {
		
		if(list.isEmpty()==true) {
			return true;
		}
		else {
			return false;
		}
	}
}
