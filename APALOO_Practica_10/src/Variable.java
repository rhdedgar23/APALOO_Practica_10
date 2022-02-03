
public class Variable {
	String nombre;
	float valor;
	
	public Variable(){
		this.nombre="";
		this.valor= 0;
	}
	
	public Variable(String nombre, float valor) {
		this.nombre= nombre;
		this.valor= valor;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}
}
