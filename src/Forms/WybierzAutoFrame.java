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
 * Okno z autami które mo¿emy wybraæ za pomoc¹ 2kliku. Dane s¹ przenoszone do formularza naprawyForm w któr¹ ustawiamy je jako aktualne 
 *
 */
public class WybierzAutoFrame extends JFrame {
	public WybierzAutoFrame(final NaprawyForm naprawyForm) {
		setSize(400, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(this.HIDE_ON_CLOSE);
		JTable tabelaAut = new JTable();
		JScrollPane jScrollPane = new JScrollPane(tabelaAut);
		final TabelaAutModel autModel = new TabelaAutModel();
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
					naprawyForm.setSamochodFields(samochod);
					naprawyForm.enableCarPanell(true);
					naprawyForm.getDodajPrace().setEnabled(true);
					if(naprawyForm.getWykonanaPracatable().getModel().getRowCount()==0){
						naprawyForm.getZakonczNapraweButton().setEnabled(false);
					}
					WybierzAutoFrame.this.setVisible(false);
					naprawyForm.setEnabled(true);
				}
			}
		});
		add(jScrollPane);
	}
	
}
