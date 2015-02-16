package Entitity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;


/**
 * 
 * Klasa przedstawia tabele Pracownika 
 *
 */
public class Pracownik {

	private Integer idPracownik;
	private String imie;
	private String nazwisko;
	private String PESEL;
	private Integer typPracownika;
	private String uwagi;
	private Integer stawkaGodzinna;

	/**
	 * @return the idPracownik
	 */
	public Integer getIdPracownik() {
		return idPracownik;
	}

	/**
	 * @param idPracownik
	 *            the idPracownik to set
	 */
	public void setIdPracownik(Integer idPracownik) {
		this.idPracownik = idPracownik;
	}

	/**
	 * @return the imie
	 */
	public String getImie() {
		return imie;
	}

	/**
	 * @param imie
	 *            the imie to set
	 */
	public void setImie(String imie) {
		this.imie = imie;
	}

	/**
	 * @return the nazwisko
	 */
	public String getNazwisko() {
		return nazwisko;
	}

	/**
	 * @param nazwisko
	 *            the nazwisko to set
	 */
	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}

	/**
	 * @return the pESEL
	 */
	public String getPESEL() {
		return PESEL;
	}

	/**
	 * @param pESEL
	 *            the pESEL to set
	 */
	public void setPESEL(String pESEL) {
		PESEL = pESEL;
	}

	/**
	 * @return the typPracownika
	 */
	public Integer getTypPracownika() {
		return typPracownika;
	}

	/**
	 * @param typPracownika
	 *            the typPracownika to set
	 */
	public void setTypPracownika(Integer typPracownika) {
		this.typPracownika = typPracownika;
	}

	/**
	 * @return the uwagi
	 */
	public String getUwagi() {
		return uwagi;
	}

	/**
	 * @param uwagi
	 *            the uwagi to set
	 */
	public void setUwagi(String uwagi) {
		this.uwagi = uwagi;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		return getImie() + " " + getNazwisko();
	}

	
	/**
	 * Pobiera wszystkich pracownikow w postaci tablicy, do wykorzystania bezpoœrednio w combo boxie 
	 * 
	 * @return Tablica pracowników
	 */
	public static Object[] fetchPracownicy() {

		ArrayList<Pracownik> ap = null;
		try {
			Connection con = null;
			ap = new ArrayList<Pracownik>();

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztat", "root", "password");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM pracownik");

			//dane potrzebne do wyœwietlania w combo boxie
			while (rs.next()) {
				Pracownik p = new Pracownik();
				p.setIdPracownik((Integer) rs.getObject(1));
				p.setImie(rs.getObject(2).toString());
				p.setNazwisko(rs.getObject(3).toString());
				ap.add(p);
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
	 * Zwraca podwójny vector który mo¿emy wygodnie u¿ywaæ w obiekcie JTABLE
	 * 
	 * @return Vector zaiweraj¹cy kolejny vector z danymi pracownika 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Vector fetchAllPracownik() {
		Vector data = null;
		try {
			Connection con = null;
			data = new Vector();
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztat", "root", "password");
			Statement st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select a.*,b.NazwaTypu from pracownik a inner join typpracownika b on a.TypPracownika = b.idTypPracownika");

			while (rs.next()) {
				Vector row = new Vector();
				for (int i = 1; i <= 8; i++) {
					row.addElement(rs.getObject(i));
				}
				data.addElement(row);
			}
			rs.close();
			st.close();

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "B³¹d po³¹czenia z baz¹ danych");
		}
		return data;
	}

	
	/**
	 * uzupelnia wszystkie dane pracownika na podstawie jego ID w obiekcie
	 */
	public void fetch() {
		try {
			Connection con = null;
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztat", "root", "password");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM pracownik where idPracownik =" + idPracownik.toString());
			if(rs.next()){
			imie = (String) rs.getObject(2);
			nazwisko = (String) rs.getObject(3);
			PESEL = (String) rs.getObject(4);
			typPracownika = ((Integer) rs.getObject(5));
			uwagi = (String) rs.getObject(6);
			stawkaGodzinna = (Integer) rs.getObject(7);
			}
			rs.close();
			st.close();

		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * nadpisany aby mo¿na by³o u¿ywaæ selectObject w combo boxie
	 */
	@Override
	public boolean equals(Object other) {

		Pracownik p = (Pracownik) other;
		if (p.idPracownik == idPracownik) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Zapisuje aktualny obiekt w bazie danych
	 */
	public void savePracownik() {
		try {

			String insertTableSQL = "INSERT INTO pracownik" + "(idPracownik, Imie, Nazwisko,PESEL,typPracownika,uwagi,stawkaGodzinna) VALUES"
					+ "(?,?,?,?,?,?,?)";
			Connection con = null;
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztat", "root", "password");
			PreparedStatement preparedStatement = null;

			preparedStatement = con.prepareStatement(insertTableSQL, Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setInt(1, 0);
			preparedStatement.setString(2, imie);
			preparedStatement.setString(3, nazwisko);
			preparedStatement.setString(4, PESEL);
			preparedStatement.setInt(5, typPracownika);
			preparedStatement.setString(6, uwagi);
			preparedStatement.setInt(7, getStawkaGodzinna());

			// execute insert SQL stetement
			preparedStatement.executeUpdate();
			ResultSet gk = preparedStatement.getGeneratedKeys();
			gk.next();
			setIdPracownik(gk.getInt(1));
			System.out.println("Dodano" + gk.getInt(1));

		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @return stawkaGodzinna
	 */
	public Integer getStawkaGodzinna() {
		return stawkaGodzinna;
	}

	public void setStawkaGodzinna(Integer stawkaGodzinna) {
		this.stawkaGodzinna = stawkaGodzinna;
	}

	
	/**
	 * Usuwa danego pracownika z bazy danych
	 * @param p pracownik do usuniêcia
	 */
	public static void usunPracownika(Pracownik p) {
		try {
			Connection con = null;
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztat", "root", "password");
			Statement stmt;
			String sql = "DELETE FROM Pracownik " + "WHERE idPracownik =" + p.idPracownik;
			stmt = con.createStatement();
			stmt.executeUpdate(sql);
			System.out.println(sql);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	/**
	 * Aktualizujemy danego pracownika, identyfikacje po idPracownik.
	 */
	public void update() {

		try {
			String updateeSQL = "UPDATE pracownik SET imie=\"" + imie + "\",nazwisko =\"" + nazwisko + "\", pesel = \"" + PESEL
					+ "\", TypPracownika =" + typPracownika + ",uwagi=\"" + uwagi + "\",stawkaGodzinna=" + stawkaGodzinna + " WHERE idPracownik=" + idPracownik;
			Connection con = null;
			System.out.println(updateeSQL);
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztat", "root", "password");
			PreparedStatement preparedStatement = null;
			preparedStatement = con.prepareStatement(updateeSQL);

			// execute insert SQL stetement
			preparedStatement.executeUpdate();

		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
