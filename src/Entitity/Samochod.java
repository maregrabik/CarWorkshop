package Entitity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JOptionPane;

/**
 * 
 * Klasa przedstawia tabele pracownika z bazy danych
 *
 */
public class Samochod {

	Integer idSamochod;
	String stanAuta;
	String Marka;
	String NrSilnika;
	String NrPodwozia;
	String Rok;
	String Klient;
	Integer idKlient;
	private String statusNaprawy;
	private Integer naprawa;
	@SuppressWarnings("rawtypes")
	final static Vector columnNames = new Vector();
	@SuppressWarnings("rawtypes")
	static Vector data = new Vector();

	public Samochod() {
	}

	/**
	 * Metoda statyczna u¿ywana do pobierania danych w formie u¿ycia dla JTABLE
	 * 
	 * @return Podwójny wektor zawieraj¹cy wszystkie samochody nie oznaczone
	 *         jako ukoñczone
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Vector getAllCars() {
		try {
			Connection con = null;
			data = new Vector();
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztat", "root", "password");
			Statement st = con.createStatement();
			ResultSet rs = st
					.executeQuery("Select a.Marka,a.Rok,a.NrPodwozia,a.NrSilnika,a.stanauta,a.StatusNaprawy,c.CenaNaprawy, a.idSamochod,idKlient,c.idNaprawa from samochod  a inner join klient b on a.Klient = b.idKlient inner join naprawa c on a.naprawa = c.idNaprawa where a.StatusNaprawy <> 'Zakoñczony'");

			while (rs.next()) {
				Vector row = new Vector();
				for (int i = 1; i <= 10; i++) {
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
	 * Zwraca wszystkie samochody które zosta³y oznaczone jako "zakoñczone"
	 * 
	 * @return Vector podwójny vector
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Vector getAllFinishedCars() {
		try {
			Connection con = null;
			data = new Vector();
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztat", "root", "password");
			Statement st = con.createStatement();
			ResultSet rs = st
					.executeQuery("Select a.Marka,a.Rok,a.NrPodwozia,a.NrSilnika,a.stanauta,a.StatusNaprawy,c.CenaNaprawy, a.idSamochod,idKlient,c.idNaprawa from samochod  a inner join klient b on a.Klient = b.idKlient inner join naprawa c on a.naprawa = c.idNaprawa where a.StatusNaprawy = 'Zakoñczony'");

			while (rs.next()) {
				Vector row = new Vector();
				for (int i = 1; i <= 10; i++) {
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
	 * @return the idSamochod
	 */
	public Integer getIdSamochod() {
		return idSamochod;
	}

	/**
	 * @param idSamochod
	 *            the idSamochod to set
	 */
	public void setIdSamochod(Integer idSamochod) {
		this.idSamochod = idSamochod;
	}

	/**
	 * @return the stanAuta
	 */
	public String getStanAuta() {
		return stanAuta;
	}

	/**
	 * @param stanAuta
	 *            the stanAuta to set
	 */
	public void setStanAuta(String stanAuta) {
		this.stanAuta = stanAuta;
	}

	/**
	 * @return the marka
	 */
	public String getMarka() {
		return Marka;
	}

	/**
	 * @param marka
	 *            the marka to set
	 */
	public void setMarka(String marka) {
		Marka = marka;
	}

	/**
	 * @return the nrSilnika
	 */
	public String getNrSilnika() {
		return NrSilnika;
	}

	/**
	 * @param nrSilnika
	 *            the nrSilnika to set
	 */
	public void setNrSilnika(String nrSilnika) {
		NrSilnika = nrSilnika;
	}

	/**
	 * @return the nrPodwozia
	 */
	public String getNrPodwozia() {
		return NrPodwozia;
	}

	/**
	 * @param nrPodwozia
	 *            the nrPodwozia to set
	 */
	public void setNrPodwozia(String nrPodwozia) {
		NrPodwozia = nrPodwozia;
	}

	/**
	 * @return the rok
	 */
	public String getRok() {
		return Rok;
	}

	/**
	 * @param rok
	 *            the rok to set
	 */
	public void setRok(String rok) {
		Rok = rok;
	}

	/**
	 * @return the klient
	 */
	public String getKlient() {
		return Klient;
	}

	/**
	 * @param klient
	 *            the klient to set
	 */
	public void setKlient(String klient) {
		Klient = klient;
	}

	/**
	 * @return the columnnames
	 */
	public static Vector getColumnnames() {
		return columnNames;
	}

	public Integer getIdKlient() {
		return idKlient;
	}

	public void setIdKlient(Integer idKlient) {
		this.idKlient = idKlient;
	}

	/**
	 * Metoda zapisuje aktualny obiekt w bazie dnaych
	 */
	public void saveOrUpdateCar() {
		try {

			String insertTableSQL = "INSERT INTO samochod"
					+ "(idsamochod, marka, rok,nrpodwozia,nrsilnika,stanauta,statusNaprawy,klient,naprawa) VALUES" + "(?,?,?,?,?,?,?,?,?)";
			Connection con = null;
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztat", "root", "password");
			PreparedStatement preparedStatement = null;

			preparedStatement = con.prepareStatement(insertTableSQL, Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setInt(1, 0);
			preparedStatement.setString(2, Marka);
			preparedStatement.setString(3, Rok);
			preparedStatement.setString(4, NrPodwozia);
			preparedStatement.setString(5, NrSilnika);
			preparedStatement.setString(6, stanAuta);
			preparedStatement.setString(7, "Oczekuje");
			preparedStatement.setString(8, idKlient.toString());
			preparedStatement.setString(9, naprawa.toString());

			// execute insert SQL stetement
			preparedStatement.executeUpdate();
			ResultSet gk = preparedStatement.getGeneratedKeys();
			gk.next();
			setIdSamochod(gk.getInt(1));
			System.out.println("Dodano" + gk.getInt(1));

		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Integer getNaprawa() {
		return naprawa;
	}

	public void setNaprawa(Integer naprawa) {
		this.naprawa = naprawa;
	}

	public String getStatusNaprawy() {
		return statusNaprawy;
	}

	public void setStatusNaprawy(String statusNaprawy) {
		this.statusNaprawy = statusNaprawy;
	}

	/**
	 * Usuwany dany samóch
	 * 
	 * @param s
	 */
	public static void usunSamochod(Samochod s) {
		try {
			// STEP 4: Execute a query
			Connection con = null;
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztat", "root", "password");
			System.out.println("Creating statement...");
			Statement stmt;
			String sql = "DELETE FROM Samochod " + "WHERE idSamochod =" + s.getIdSamochod();
			stmt = con.createStatement();
			stmt.executeUpdate(sql);
			System.out.println(sql);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * usupe³niamy dane samochodu na bpodstawie jego id
	 */
	public void fetch() {
		try {
			Connection con = null;
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztat", "root", "password");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM samochod where idSamochod =" + idSamochod.toString());
			rs.next();
			stanAuta = (String) rs.getObject(2);
			Marka = (String) rs.getObject(3);
			NrSilnika = (String) rs.getObject(4);
			NrPodwozia = (String) rs.getObject(5);
			Rok = (String) rs.getObject(6);
			Klient = rs.getObject(7).toString();
			statusNaprawy = (String) rs.getObject(8);
			naprawa = (Integer) rs.getObject(9);

			rs.close();
			st.close();

		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Aktualizujemy dane somochodu, metoda opiera sie na idSamochodu
	 */
	public void update() {

		try {

			String updateeSQL = "UPDATE samochod SET Marka=\"" + Marka + "\",rok =\"" + Rok + "\", nrSilnika = \"" + NrSilnika
					+ "\", nrPodwozia =\"" + NrPodwozia + "\", statusNaprawy =\"" + statusNaprawy + "\",stanAuta=\"" + stanAuta + "\"  WHERE idSamochod=" + idSamochod;
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

	/**
	 * Ustawiamy status auta jako "W trakcie" jako ¿e zosta³a dodana piersza
	 * naprawa po zarejsterowaniu
	 */
	public void setStatusWTrakcie() {
		try {

			String updateeSQL = "UPDATE samochod SET statusNaprawy = \"W trakcie\" WHERE idSamochod=" + idSamochod;
			Connection con = null;
			System.out.println(updateeSQL);
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztat", "root", "password");
			PreparedStatement preparedStatement = null;

			preparedStatement = con.prepareStatement(updateeSQL);

			// execute insert SQL stetement
			preparedStatement.executeUpdate();
			statusNaprawy = "W trakcie";
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
