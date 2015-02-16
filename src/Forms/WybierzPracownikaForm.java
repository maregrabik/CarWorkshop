package Forms;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import Entitity.Pracownik;
import Utility.TabelaPracownikaModel;
import Utility.WykonanaPracaObliczeniaTableModel;


public class WybierzPracownikaForm extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Wybieramy pracownika do obliczenia jego wynagrodzenia
	 * @param wynagrPracForm
	 */
	public WybierzPracownikaForm(final WynagordzeniaPracownika wynagrPracForm) {
		setSize(400, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JTable pracownikTable = new JTable();
		final TabelaPracownikaModel tabelaPracownikaModel = new TabelaPracownikaModel();
		pracownikTable.setModel(tabelaPracownikaModel);
		JScrollPane tabelaAutScrPane = new JScrollPane(pracownikTable);
		tabelaAutScrPane.setViewportBorder(new TitledBorder(null, "Lista klientów", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(tabelaAutScrPane);
		pracownikTable.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				JTable table = (JTable) me.getSource();
				Point p = me.getPoint();
				int row = table.rowAtPoint(p);
				if (me.getClickCount() == 2) {
					Pracownik pracownik = new Pracownik();
					pracownik.setIdPracownik((Integer) tabelaPracownikaModel.getValueAt(row, 0));
					pracownik.fetch();
					wynagrPracForm.setPracownikFields(pracownik);
					wynagrPracForm.setObliczeniaFields(pracownik);
					wynagrPracForm.enablePracownikPanel(true);
					wynagrPracForm.enableObliczeniaPanel(true);
					wynagrPracForm.setreadOnly(false);
					wynagrPracForm.getDetailTable().setModel(new WykonanaPracaObliczeniaTableModel(pracownik));
					WybierzPracownikaForm.this.setVisible(false);

				}
			}
		});

	}

}
