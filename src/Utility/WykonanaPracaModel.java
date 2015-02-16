package Utility;

import java.sql.Date;
import java.util.Vector;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import Entitity.Naprawa;
import Entitity.WykonanaPraca;
import Forms.NaprawyForm;


/**
 * Model dla tabeli w formularzu wykonywanej pracy
 *
 */
public class WykonanaPracaModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	String[] columnNames = { "ID", "Naprawa", "Opis", "data pracy", "Godziny", "Godziny nocna", "Godziny w swieta","IdPracownik" };
	Vector data;

	public WykonanaPracaModel(Naprawa n) {
		data = WykonanaPraca.getWykonanaPraca(n);
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public String getColumnName(int col) {
		return (String) columnNames[col];
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {

		return ((Vector) data.get(arg0)).get(arg1);
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		return false;
	}

	public void refresCarTable(Naprawa n) {
		data = WykonanaPraca.getWykonanaPraca(n);
		fireTableDataChanged();
	}

	/**
	 * Przenoszenie danch do g��wnego formularz
	 */
	public void setSelectionListener(final NaprawyForm naprawyForm) {
		ListSelectionModel x = naprawyForm.getWykonanaPracatable().getSelectionModel();
		x.setSelectionMode(x.SINGLE_SELECTION);
		x.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int y = naprawyForm.getWykonanaPracatable().getSelectedRow();
				if (y != -1) {
					WykonanaPraca wykonanaPraca = new WykonanaPraca();
					wykonanaPraca.setIdWykonanaPraca((Integer) naprawyForm.getWykonanaPracatable().getValueAt(y, 0));
					wykonanaPraca.setIdNaprawa((Integer) naprawyForm.getWykonanaPracatable().getValueAt(y, 1));
					wykonanaPraca.setOpis(naprawyForm.getWykonanaPracatable().getValueAt(y, 2).toString());
					wykonanaPraca.setDataPracy((Date) naprawyForm.getWykonanaPracatable().getValueAt(y, 3));
					wykonanaPraca.setIloscGodzin((Integer) naprawyForm.getWykonanaPracatable().getValueAt(y, 4));
					wykonanaPraca.setIloscGodzinNoc((Integer) naprawyForm.getWykonanaPracatable().getValueAt(y, 5));
					wykonanaPraca.setIloscGodzinSwieta((Integer) naprawyForm.getWykonanaPracatable().getValueAt(y, 6));
					wykonanaPraca.setPracownik((Integer) naprawyForm.getWykonanaPracatable().getValueAt(y, 7));
					naprawyForm.setWykonaPracaFields(wykonanaPraca);
					naprawyForm.getUsunPraceButton().setEnabled(true);
					naprawyForm.getEdytujButton().setEnabled(true);

					System.out.println(1);
				}
			}
		});
	}

}
