
public interface Lista<Tipo>{

	public boolean busca(String elem);
	public void inserta(String elem, float valor);
	public double getValor(String elem);
	public void despliega();
	public void hazNula();
	public boolean esVacia();
}
