import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

public class FormEstado extends JDialog implements ActionListener {

	private JTextField nombre;
	private JLabel lNombre, lAceptacion;
	private JCheckBox aceptacion;
	private boolean retornar;
	private JButton aceptar;

	public FormEstado(final JFrame frame) {
		super(frame);
		super.setModal(true);
		this.getContentPane().setLayout(null);
		super.setTitle("Agregar Estado");
		super.setSize(425, 200);
		super.setVisible(false);
		
		lNombre = new JLabel("Nombre");
		lNombre.setBounds(75, 25, 100, 25);

		nombre = new JTextField();
		nombre.setBounds(250, 25, 100, 25);

		lAceptacion = new JLabel("Estado de Aceptacion ?");
		lAceptacion.setBounds(75, 55, 150, 25);

		aceptacion = new JCheckBox();
		aceptacion.setBounds(250, 55, 25, 25);

		aceptar = new JButton("Aceptar");
		aceptar.setBounds(250, 120, 100, 25);
		aceptar.addActionListener(this);

		this.getContentPane().add(lNombre);
		this.getContentPane().add(nombre);
		this.getContentPane().add(lAceptacion);
		this.getContentPane().add(aceptacion);
		this.getContentPane().add(aceptar);

	}

	public Estado getNewEstado() {
		if (retornar) {
			Estado e = new Estado();
			e.setNombre(nombre.getText());
			if (aceptacion.isSelected()) {
				e.setTipoEstado(TipoEstado.ACEPTACION);
			}else{
				e.setTipoEstado(TipoEstado.NORMAL);
			}
			retornar = false;
			return e;
		}
		return null;
	}

	public void showFormulario() {
		this.nombre.setText("");
		this.aceptacion.setSelected(false);
		this.nombre.requestFocus();
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == aceptar) {
			this.retornar = true;
			this.setVisible(false);
		}
	}
}
