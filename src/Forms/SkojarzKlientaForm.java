package Forms;

import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import Entitity.Klient;
import Utility.TabelaKlientModel;

public class SkojarzKlientaForm extends JFrame {

	/**
	 * Okno które jest wyœwteilane gdy chcemy skojarzykæ istniej¹cego klienta z danym autem
	 */
	private static final long serialVersionUID = 1L;

	public SkojarzKlientaForm(final MechFrame mechFrame) {
		setSize(400, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JTable klintTable = new JTable();
		final TabelaKlientModel model = new TabelaKlientModel();
		klintTable.setModel(model);

		JScrollPane tabelaAutScrPane = new JScrollPane(klintTable);
		tabelaAutScrPane.setViewportBorder(new TitledBorder(null, "Lista klientów", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(tabelaAutScrPane);
		klintTable.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				JTable table = (JTable) me.getSource();
				Point p = me.getPoint();
				int row = table.rowAtPoint(p);
				if (me.getClickCount() == 2) {
					System.out.println(model.getValueAt(row, 0));
					Klient k = new Klient();
					k.setIdKlient(Integer.parseInt(model.getValueAt(row, 0).toString()));
					mechFrame.setKlientFields(k);
					mechFrame.getZatwierdzEdycjeButton().setEnabled(true);
					mechFrame.setEnabled(true);
					SkojarzKlientaForm.this.setVisible(false);

				}
			}
		});

	}

}
