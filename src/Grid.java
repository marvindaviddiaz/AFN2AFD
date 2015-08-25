import java.awt.Font;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 * @author Marvin Díaz
 */
@SuppressWarnings({ "rawtypes", "serial" })
public class Grid extends DefaultTableModel {

	private Vector vectorDatos;
	private Vector vectorEncabezado;
	private JTable table;
	private List<String> args = new ArrayList<String>();
	private Set<Estado> estados;

	public Map<String, Set<Estado>> getRowData(Integer row, final Set<Estado> estados) throws Exception{
		this.estados = estados;
		Map<String, Set<Estado>> transiciones = new LinkedHashMap<String, Set<Estado>>();
		Set<Estado> listaSub;
		String transicion;
		if (super.getRowCount() > 0) {
			for (int i = 1; i < super.getColumnCount(); i++) {
				listaSub =  new LinkedHashSet<Estado>();
				transicion = (String) this.getValueAt(row, i);
				if(transicion != null){
					if (transicion.contains(",")) {
						String[] vec = transicion.split(",");
						for (String s : vec) {
							s = s.trim();
							if (s.length() > 0) {
								listaSub.add(existEstado(s));
							}

						}
					}else{
						listaSub.add(existEstado(transicion));
					}
				}
				String estado = (String) vectorEncabezado.get(i);
				transiciones.put(estado, listaSub);
			}
		}
		return transiciones;
	}
	
	private Estado existEstado(String estado) throws Exception{
		for (final Estado e : estados) {
			if(e.getNombre().equals(estado)){
				return e;
			}
		}
		throw new Exception("No existe el estado: " + estado);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return (column != 0);
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		super.setValueAt(aValue, row, column);
	}

	public Grid(Vector encabezado, Vector datos) {
		vectorDatos = datos;
		vectorEncabezado = encabezado;
		this.setDataVector(vectorDatos, vectorEncabezado);
		table = new JTable(this) {
			@Override
			public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
				super.changeSelection(rowIndex, -1, toggle, false);
				args.clear();
				for (int i = 0; i < table.getColumnCount(); i++) {
					String body = (String) this.getValueAt(rowIndex, i);
					args.add(body);
				}
			}
		};
		table.setFont(new Font("Arial", Font.BOLD, 16));
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		for (int i = 0; i < vectorEncabezado.size(); i++) {
			tcr.setHorizontalAlignment(SwingConstants.RIGHT);
			table.getColumnModel().getColumn(i).setCellRenderer(tcr);
		}
	}

	public JTable getTable() {
		return table;
	}

}
