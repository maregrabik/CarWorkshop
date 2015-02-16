package Entitity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * Klasa przedstawiaj¹ca s³ownik z bazy danych, typPracownika
 */
public class TypPracownika {

	private Integer idTypu;
	private String nazwaTypu;
	
	
	public TypPracownika() {
		
	}

	public String getNazwaTypu() {
		return nazwaTypu;
	}

	public void setNazwaTypu(String nazwaTypu) {
		this.nazwaTypu = nazwaTypu;
	}

	public Integer getIdTypu() {
		return idTypu;
	}

	public void setIdTypu(Integer idTypu) {
		this.idTypu = idTypu;
	}

	@Override
	public String toString() {
		return nazwaTypu;
	}
	
	
	/**
	 *Nadpisane aby moóc u¿ywaæ slectObject w comboBox
	 */
	@Override
	public boolean equals(Object other) {
		TypPracownika tp = (TypPracownika) other;
		if (tp.getIdTypu() == idTypu) {
			return true;
		} else {
			return false;
		}

	}
	
	

	/**
	 * Zwrca wszystkie typy pracownika z bazy danych w postaci tablicy wygodnej do uzycia przez comboBox
	 * 
	 * @return Object[] tablica typów pracownika
	 */
	public static Object[] getAllTypes() {

		ArrayList<TypPracownika> ap = null;
		try {
			Connection con = null;
			ap = new ArrayList<TypPracownika>();

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztat", "root", "password");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM typPracownika");

			while (rs.next()) {

				TypPracownika typPracownika = new TypPracownika();
				typPracownika.setIdTypu((Integer) rs.getObject(1));
				typPracownika.setNazwaTypu(rs.getObject(2).toString());
				ap.add(typPracownika);
			}
			rs.close();
			st.close();

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "B³¹d po³¹czenia z baz¹ danych");
		}

		return ap.toArray();
	}
	
	/**
	 * Uzupe³nianie po id typu pracownika
	 */
	public void fetch() {
		try {
			Connection con = null;
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztat", "root", "password");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM typPracownika where idTypPracownika =" + idTypu.toString());
			rs.next();
			nazwaTypu = (String) rs.getObject(2);
			rs.close();
			st.close();

		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
