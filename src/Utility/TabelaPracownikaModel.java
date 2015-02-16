package Utility;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import Entitity.Klient;
import Entitity.Pracownik;
import Entitity.Samochod;


/**
 * Model dla tabeli która z pracownikami. U¿ywane w  formularzu administarcyjnym obliczeñ pracownika
 *
 */
public class TabelaPracownikaModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String[] columnNames = { "Id", "Imie", "Nazwisko", "PESEL", "Typ pracownika"};
	Vector data;

	public TabelaPracownikaModel() {
		data = Pracownik.fetchAllPracownik();
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

}
