package Forms;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Entitity.Samochod;
import Utility.TabelaAutModel;

/**
 * Formularz z zakoñcczonymi pracami
 * @author Marek
 *
 */
public class ZakonczonePraceForm extends JFrame {

	private static final long serialVersionUID = 1L;

	public ZakonczonePraceForm() {
		setSize(400, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		JTable tabelaAut = new JTable();
		JScrollPane jScrollPane = new JScrollPane(tabelaAut);
		final TabelaAutModel autModel = new TabelaAutModel();
		autModel.refresFinishedCarTable();
		tabelaAut.setModel(autModel);
		tabelaAut.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				JTable table = (JTable) me.getSource();
				Point p = me.getPoint();
				int row = table.rowAtPoint(p);
				if (me.getClickCount() == 2) {
					System.out.println(autModel.getValueAt(row, 0));
					Samochod samochod = new Samochod();
					samochod.setIdSamochod(Integer.parseInt(autModel.getValueAt(row, 7).toString()));
					samochod.fetch();
				}
			}
		});
		add(jScrollPane);
	}
}
