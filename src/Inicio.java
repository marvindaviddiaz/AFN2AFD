import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

/**
 * @author Marvin Díaz
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class Inicio extends JFrame implements ActionListener {

	private JButton addEstado, addAlfabeto, convertir;

	private Set<Estado> estados = new LinkedHashSet<Estado>();
	private Set<String> alfabeto = new LinkedHashSet<String>();
	private Grid grid;
	private JScrollPane scroll;
	private FormEstado formEstado = new FormEstado(this);

	public static String TITULO_ESTADOS = "Estados";

	Converter converter = new Converter();

	private String FLECHA = "→ ";
	private String ASTERISCO = "* ";

	public Inicio() {
		super("AFN-AFD  0900-10-4557  Marvin Diaz");
		this.setLayout(new FlowLayout());
		super.setVisible(true);
		super.setSize(400, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		super.getContentPane().setBackground(Color.WHITE);

		super.add(new JLabel("AFN - AFD"));
		super.add(new JLabel("                                                                                      "
				+ "                                                                                                   "
				+ "                                                                                                   "
				+ "                                                                                                   "
				+ "                                                                                                   "));
		super.add(new JSeparator());

		addAlfabeto = new JButton("Definir Alfabeto");
		super.getContentPane().add(addAlfabeto);
		addAlfabeto.addActionListener(this);

		addEstado = new JButton("Agregar Estado");
		super.getContentPane().add(addEstado);
		addEstado.addActionListener(this);

		convertir = new JButton("Convertir");
		super.getContentPane().add(convertir);
		convertir.addActionListener(this);

		ingresarAlfabeto();
		super.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addAlfabeto) {
			actionAddAlfabeto();
		} else if (e.getSource() == addEstado) {
			actionAddEstado();
		} else if (e.getSource() == convertir) {
			actionConvertir();
		}
	}

	private void actionAddAlfabeto() {
		String cadena = JOptionPane.showInputDialog(this, "Ingresar el Alfabeto", "INGRESE EL ALFABETO", JOptionPane.INFORMATION_MESSAGE);
		alfabeto.add(TITULO_ESTADOS);
		if (cadena != null) {
			for (String c : cadena.split("|")) {
				if (c.trim().length() > 0)
					alfabeto.add(c.trim());
			}
			// alfabeto.add("ε");
		}
		if (alfabeto.size() > 1) {
			ingresarEstados();
			inicializarTabla();
		}
	}

	private void actionAddEstado() {
		formEstado.setLocationRelativeTo(this);
		formEstado.showFormulario();
		Estado estado = formEstado.getNewEstado();
		int countEstados = estados.size();
		if (estado != null && estado.getNombre() != null && estado.getNombre().trim().length() > 0) {
			if (countEstados == 0) {
				estado.setTipoEstado(TipoEstado.INICIAL);
			}
			estados.add(estado);
			if (countEstados < estados.size()) {
				Vector v = new Vector<String>();
				switch (estado.getTipoEstado()) {
				case INICIAL:
					v.add(FLECHA + estado.getNombre());
					break;
				case ACEPTACION:
					v.add(ASTERISCO + estado.getNombre());
					break;
				default:
					v.add(estado.getNombre());
					break;
				}
				grid.addRow(v);
			}
		}
	}

	public void actionConvertir() {
		if (estados.size() == 0) {
			JOptionPane.showMessageDialog(this, "Debe Ingresar estados", "ERROR", JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			int count = 0;
			for (final Estado estado : estados) {
				estado.setTransiciones(grid.getRowData(count, estados));
				count++;
			}
			estados = converter.convertir(estados, alfabeto);
			mostrarResultados();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		}

	}

	public void inicializarTabla() {
		Vector<String> encabezado = new Vector<String>();
		Vector datos = new Vector();

		for (String a : alfabeto) {
			encabezado.add(a);
		}
		if (grid == null) {
			grid = new Grid(encabezado, datos);
			scroll = new JScrollPane(grid.getTable());
			scroll.setBackground(Color.WHITE);
			super.getContentPane().add(scroll);
		} else {
			grid.addRow(datos);
		}

		this.setSize(600, 600);
		this.repaint();
	}

	public void mostrarResultados() {
		Vector<String> encabezado = new Vector<String>();
		Vector datos = new Vector();

		for (String a : alfabeto) {
			encabezado.add(a);
		}
		super.getContentPane().remove(scroll);

		grid = new Grid(encabezado, datos);
		scroll = new JScrollPane(grid.getTable());
		scroll.setBackground(Color.WHITE);
		super.getContentPane().add(scroll);

		for(Estado estado : this.estados){
			Vector v = new Vector<String>();
			switch (estado.getTipoEstado()) {
			case INICIAL:
				v.add(FLECHA + estado.getNombre());
				break;
			case ACEPTACION:
				v.add(ASTERISCO + estado.getNombre());
				break;
			default:
				v.add(estado.getNombre());
				break;
			}
			for (Set<Estado> s : estado.getTransiciones().values()) {
				v.add(s);
			}
			grid.addRow(v);
		}
		
		this.addEstado.setEnabled(false);
		this.addAlfabeto.setEnabled(false);
		this.setSize(610, 600);
		this.repaint();
	}

	public void ingresarAlfabeto() {
		this.addAlfabeto.setEnabled(true);
		this.addEstado.setEnabled(false);
		this.convertir.setEnabled(false);
	}

	public void ingresarEstados() {
		this.addAlfabeto.setEnabled(false);
		this.addEstado.setEnabled(true);
		this.convertir.setEnabled(true);
	}

	public static void main(String[] args) {
		new Inicio();
	}
}
