import java.util.ArrayList;

public class Pila<Tipo> implements Stack<Tipo> {
	ArrayList<Tipo> list;
	
	public Pila() {
		list= new ArrayList<Tipo>();
	}
	
	public void push(Tipo elem) {
		list.add(list.size(), elem);
	}
	
	public void pop() {
		list.remove(list.size()-1);
	}
	
	public void hazNula() {
		list.clear();
	}
	
	public boolean esVacia() {
		if(list.size()==0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public Tipo valorTope() {
		return list.get(list.size()-1);
	} 
}
