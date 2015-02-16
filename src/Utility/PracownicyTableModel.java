package Utility;

import java.util.Vector;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import Entitity.Pracownik;
import Forms.PracownicyForm;

/**
 * Model dla tabeli pracowników u¿ywany w module administratora zka³dace pracownicy
 * @author Marek
 *
 */
public class PracownicyTableModel extends AbstractTableModel {

	String[] columnNames = { "idPracownik", "imie", "nazwisko", "pesel", "typ", "uwagi", "stawka", "Typ pracownika" };

	Vector data = Pracownik.fetchAllPracownik();

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

	public void refreshPracownikTable() {
		data = Pracownik.fetchAllPracownik();
		fireTableDataChanged();
	}

	/**
	 * Dodawanie action listenera który dzia³ajaca na klasie pracownicyForm który przenosi odpowiednio zaznaczony rekord do danych na formularzu
	 * @param pracownicyForm
	 */
	public void setSelectionListener(final PracownicyForm pracownicyForm) {
		ListSelectionModel x = pracownicyForm.getPracownicyTable().getSelectionModel();
		x.setSelectionMode(x.SINGLE_SELECTION);
		x.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int y = pracownicyForm.getPracownicyTable().getSelectedRow();
				
				//aby unikn¹c b³êdów z pust¹ tabel¹, szybki fix
				if (y != -1) {
					Pracownik pracownik = new Pracownik();
					pracownik.setIdPracownik((Integer) pracownicyForm.getPracownicyTable().getValueAt(y, 0));
					pracownik.setImie(pracownicyForm.getPracownicyTable().getValueAt(y, 1).toString());
					pracownik.setNazwisko((pracownicyForm.getPracownicyTable().getValueAt(y, 2).toString()));
					pracownik.setPESEL((pracownicyForm.getPracownicyTable().getValueAt(y, 3).toString()));
					pracownik.setTypPracownika((Integer) (pracownicyForm.getPracownicyTable().getValueAt(y, 4)));
					pracownik.setUwagi((pracownicyForm.getPracownicyTable().getValueAt(y, 5).toString()));
					pracownik.setStawkaGodzinna((Integer) (pracownicyForm.getPracownicyTable().getValueAt(y, 6)));
					pracownicyForm.setpracownikField(pracownik);
					pracownicyForm.setUsunEdytuj(true);
				}
			}
		});
	}

}
