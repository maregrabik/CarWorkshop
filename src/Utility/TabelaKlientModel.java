package Utility;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import Entitity.Klient;
import Entitity.Samochod;

/**
 * Model dla tabeli z klientami 
 *
 */
public class TabelaKlientModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String[] columnNames = { "Id", "Imie", "Nazwisko", "PESEL", "Staly klient" };
	Vector data;

	public TabelaKlientModel() {
		data = Klient.getAllKleints();
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

	/**
	 * Nadpisany aby prawid³owó by³ wyœwietlany checkbox sta³ego klienta
	 */
	@Override
	public Class getColumnClass(int column) {

		switch (column) {
		case 0:
			return String.class;
		case 1:
			return String.class;
		case 2:
			return String.class;
		case 3:
			return String.class;
		default:
			return Boolean.class;
		}
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
