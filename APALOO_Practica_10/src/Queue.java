
public interface Queue<Tipo> {
	void encola(Tipo elem);
	void desencola();
	void hazNula();
	boolean esVacia();
	Tipo valorFrente();
}
