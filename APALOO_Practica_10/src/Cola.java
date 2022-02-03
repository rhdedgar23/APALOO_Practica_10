import java.util.LinkedList;

public class Cola<Tipo> implements Queue<Tipo>{
	LinkedList<Tipo> list;
	
	public Cola() {
		this.list= new LinkedList<Tipo>();
	}
	
	public void encola (Tipo elem){
		list.addLast(elem);
	}
	
	public void desencola(){
		list.remove();
	}
	
	public void hazNula(){
		list.clear();
	}
	
	public boolean esVacia(){
		return list.isEmpty();
	}
	

	public Tipo valorFrente(){
		return(list.getFirst());
	}
}