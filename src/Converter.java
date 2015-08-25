import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

/**
 * @author Marvin Díaz
 */
public class Converter {

	private Set<Estado> estados;
	private Set<String> alfabeto;
	private int contEstados = 0;
	private Set<Estado> nuevos = new LinkedHashSet<Estado>();
	boolean isAFN = false;

	public Set<Estado> convertir(final Set<Estado> ests, final Set<String> alfabeto) {
		this.estados = ests;
		this.alfabeto = alfabeto;

		isAFN = false;
		int i = 0;
		while (i < this.estados.size()) {
			Estado e = this.estados.toArray(new Estado[this.estados.size()])[i];
			for (String letra : alfabeto) {
				if (letra != Inicio.TITULO_ESTADOS) {
					Set<Estado> transiciones = e.getTransiciones().get(letra);
					if (transiciones != null) {
						if (transiciones.size() > 1) {
							isAFN = true;
							agregarNuevoEstado(transiciones);
						}
					}
				}
			}
			i++;
		}

		if (!isAFN) {
			JOptionPane.showMessageDialog(null, "El Automata no es un AFN", "ERROR", JOptionPane.ERROR_MESSAGE);
		} else {
			for (Estado e : this.estados) {
				System.out.println(e.aString());
			}
		}
		
		return estados;
	}
	
	public boolean isAFN() {
		return isAFN;
	}

	public void setAFN(boolean isAFN) {
		this.isAFN = isAFN;
	}

	public void agregarNuevoEstado(final Set<Estado> union) {
		Estado nuevo = new Estado();
		nuevo.setNombre(union.toString());
		nuevo.setTipoEstado(TipoEstado.UNION);
		Map<String, Set<Estado>> transiciones;
		Set<Estado> subGrupo;
		for (final String letra : alfabeto) {
			if (letra != Inicio.TITULO_ESTADOS) {
				subGrupo = new LinkedHashSet<Estado>();
				for (Estado e : union) {
					if (e.getTransiciones().get(letra) != null) {
						subGrupo.addAll(e.getTransiciones().get(letra));
					}
				}
				nuevo.addTransicion(letra, subGrupo);
			}
		}

		// estados.add(nuevo);
		Set<Estado> secundaria = new LinkedHashSet<Estado>();
		secundaria.add(nuevo);
		estados.addAll(secundaria);
	}

}
