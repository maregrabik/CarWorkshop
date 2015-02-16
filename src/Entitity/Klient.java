package Entitity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 * 
 * Klasa przedtswia tabele Klienta z bazy danych. S³u¿y jako kontener danych dla
 * operacji z formularza oraz posiada pare metod statycznych które s¹
 * wykorzystywane bezpoœrednio.
 */
public class Klient {
	
	Integer idKlient;
	String Imie;
	String Nazwisko;
	String PESEL;
	Boolean czyStalyKlient;

	public Klient() {

	}

	/**
	 * @return the idKlient
	 */
	public Integer getIdKlient() {
		return idKlient;
	}

	/**
	 * @param idKlient
	 *            the idKlient to set
	 */
	public void setIdKlient(Integer idKlient) {
		this.idKlient = idKlient;
	}

	/**
	 * @return the imie
	 */
	public String getImie() {
		return Imie;
	}

	/**
	 * @param imie
	 *            the imie to set
	 */
	public void setImie(String imie) {
		Imie = imie;
	}

	/**
	 * @return the nazwisko
	 */
	public String getNazwisko() {
		return Nazwisko;
	}

	/**
	 * @param nazwisko
	 *            the nazwisko to set
	 */
	public void setNazwisko(String nazwisko) {
		Nazwisko = nazwisko;
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
	 * @return the czyStalyKlient
	 */
	public Boolean getCzyStalyKlient() {
		return czyStalyKlient;
	}

	/**
	 * @param czyStalyKlient
	 *            the czyStalyKlient to set
	 */
	public void setCzyStalyKlient(Boolean czyStalyKlient) {
		this.czyStalyKlient = czyStalyKlient;
	}

	/**
	 * Metoda s³u¿y do zapisywania aktualnych danych w obiekcie do bazy
	 */
	public void save() {

		try {
			String insertTableSQL = "INSERT INTO klient" + "(idKlient, imie, nazwisko, pesel,stalyklient) VALUES" + "(?,?,?,?,?)";
			Connection con = null;
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztat", "root", "password");
			PreparedStatement preparedStatement = null;

			preparedStatement = con.prepareStatement(insertTableSQL);
			preparedStatement = con.prepareStatement(insertTableSQL, Statement.RETURN_GENERATED_KEYS);
			
			// przesy³amy 0, po dodaniu z poziomu bazy danych jest stosowany Auto increment
			preparedStatement.setInt(1, 0);
			preparedStatement.setString(2, Imie);
			preparedStatement.setString(3, Nazwisko);
			preparedStatement.setString(4, PESEL);
			preparedStatement.setBoolean(5, czyStalyKlient);
			
			// execute insert SQL stetement
			preparedStatement.executeUpdate();
			ResultSet gk = preparedStatement.getGeneratedKeys();
			gk.next();
			setIdKlient(gk.getInt(1));
			
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Pobieramy wszystkie dane obiektu, uzupe³niamy wszystko na podstawie
	 * idKlient(wymagany);
	 */
	public void fetch() {
		try {
			Connection con = null;
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztat", "root", "password");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM klient where idKlient =" + idKlient.toString());
			rs.next();
			Imie = (String) rs.getObject(2);
			Nazwisko = (String) rs.getObject(3);
			PESEL = (String) rs.getObject(4);
			if (rs.getObject(5).toString().equals("false")) {
				czyStalyKlient = false;
			} else {
				czyStalyKlient = true;
			}

			rs.close();
			st.close();

		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Metoda statyczna s³u¿y do pobierania wszystkich klientow w formmie
	 * vectorowwej wygodnej do u¿ycia w JTABLE
	 * 
	 * @return vector Zwraca liste wszystkich kleintow zawieraj¹cych sie w bazie
	 *         danych.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Vector getAllKleints() {

		Vector data = null;

		try {
			Connection con = null;
			data = new Vector();
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztat", "root", "password");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM klient;");

			// dla kazego wiersza/klienta tworzymy kolejny vector. Select *
			// zwróci 5 kolumn które pó¿niej s¹ bezpoœrednio pobierane z
			// rs.getObject(i)
			while (rs.next()) {
				Vector row = new Vector();
				for (int i = 1; i <= 5; i++) {
					row.addElement(rs.getObject(i));
				}
				data.addElement(row);
			}

			rs.close();
			st.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return data;

	}

}
