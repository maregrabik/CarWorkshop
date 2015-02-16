package Utility;

import java.util.Vector;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import Entitity.Samochod;
import Forms.MechFrame;

/**
 * Model u¿ywany do w tabeli samochodów w g³o ekranie warsztatu i przyjmowania saut
 *
 */
public class TabelaAutModel extends AbstractTableModel {

	String[] columnNames = { "Marka", "Rok", "Nr podwozia", "Nr silnika", "Stan", "Status", "Cena naprawy", "idSamochod", "idKlient" };

	Vector data = Samochod.getAllCars();

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

	public void refresCarTable() {
		data = Samochod.getAllCars();
		fireTableDataChanged();
	}
	public void refresFinishedCarTable() {
		data = Samochod.getAllFinishedCars();
		fireTableDataChanged();
	}

	/**
	 * Przenoszenie danych do formularzy po zaznaczeniu odpowiednio rekordu na tabeli
	 */
	public void setSelectionListener(final MechFrame mechFrame) {
		ListSelectionModel x = mechFrame.getTabelaAut().getSelectionModel();
		x.setSelectionMode(x.SINGLE_SELECTION);
		x.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int y = mechFrame.getTabelaAut().getSelectedRow();
				if (y != -1) {
					Samochod s = new Samochod();
					s.setMarka(mechFrame.getTabelaAut().getValueAt(y, 0).toString());
					s.setRok(mechFrame.getTabelaAut().getValueAt(y, 1).toString());
					s.setNrPodwozia(mechFrame.getTabelaAut().getValueAt(y, 2).toString());
					s.setNrSilnika(mechFrame.getTabelaAut().getValueAt(y, 3).toString());
					s.setStanAuta(mechFrame.getTabelaAut().getValueAt(y, 4).toString());
					s.setStatusNaprawy(mechFrame.getTabelaAut().getValueAt(y, 5).toString());
					mechFrame.getWycenaText().setText(mechFrame.getTabelaAut().getValueAt(y, 6).toString());
					s.setIdSamochod(Integer.parseInt(mechFrame.getTabelaAut().getValueAt(y, 7).toString()));
					s.setIdKlient(Integer.parseInt(mechFrame.getTabelaAut().getValueAt(y, 8).toString()));
					mechFrame.setSamochodField(s);
					mechFrame.setUsunEdytuj(true);

					System.out.println(1);
				}
			}
		});
	}

}
