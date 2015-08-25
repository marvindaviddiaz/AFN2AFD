import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Marvin Díaz
 */
public class Estado {

	private String nombre;
	private TipoEstado tipoEstado;
	private Map<String, Set<Estado>> transiciones = new LinkedHashMap<String, Set<Estado>>();

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public TipoEstado getTipoEstado() {
		return tipoEstado;
	}

	public void setTipoEstado(TipoEstado tipoEstado) {
		this.tipoEstado = tipoEstado;
	}

	public Map<String, Set<Estado>> getTransiciones() {
		return transiciones;
	}

	public void setTransiciones(Map<String, Set<Estado>> transiciones) {
		this.transiciones = transiciones;
	}

	public void addTransicion(String entrada, Set<Estado> estados) {
		this.transiciones.put(entrada, estados);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Estado other = (Estado) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nombre;
	}

	public String aString() {
		return " Nombre:\t" + nombre + "\n TipoEstado:\t" + tipoEstado + "\n Transiciones:\t" + transiciones + "\n";
	}

}
