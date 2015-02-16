package Entitity;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JOptionPane;

/**
 * 
 * Klasa przedtstawiaj¹ca bazodanowy odpiwednik Wykonanej pracy
 *
 */
public class WykonanaPraca {

	private Integer idWykonanaPraca;
	private Integer idNaprawa;
	private String opis;
	private Date dataPracy;
	private Integer iloscGodzin;
	private Integer iloscGodzinNoc;
	private Integer iloscGodzinSwieta;
	private Integer pracownik;

	/**
	 * @return the idWykonanaPraca
	 */
	public Integer getIdWykonanaPraca() {
		return idWykonanaPraca;
	}

	/**
	 * @param idWykonanaPraca
	 *            the idWykonanaPraca to set
	 */
	public void setIdWykonanaPraca(Integer idWykonanaPraca) {
		this.idWykonanaPraca = idWykonanaPraca;
	}

	/**
	 * @return the idNaprawa
	 */
	public Integer getIdNaprawa() {
		return idNaprawa;
	}

	/**
	 * @param idNaprawa
	 *            the idNaprawa to set
	 */
	public void setIdNaprawa(Integer idNaprawa) {
		this.idNaprawa = idNaprawa;
	}

	/**
	 * @return the opis
	 */
	public String getOpis() {
		return opis;
	}

	/**
	 * @param opis
	 *            the opis to set
	 */
	public void setOpis(String opis) {
		this.opis = opis;
	}

	/**
	 * @return the dataPracy
	 */
	public Date getDataPracy() {
		return dataPracy;
	}

	/**
	 * @param dataPracy
	 *            the dataPracy to set
	 */
	public void setDataPracy(Date dataPracy) {
		this.dataPracy = dataPracy;
	}

	/**
	 * @return the iloscGodzin
	 */
	public Integer getIloscGodzin() {
		return iloscGodzin;
	}

	/**
	 * @param iloscGodzin
	 *            the iloscGodzin to set
	 */
	public void setIloscGodzin(Integer iloscGodzin) {
		this.iloscGodzin = iloscGodzin;
	}

	/**
	 * @return the iloscGodzinNoc
	 */
	public Integer getIloscGodzinNoc() {
		return iloscGodzinNoc;
	}

	/**
	 * @param iloscGodzinNoc
	 *            the iloscGodzinNoc to set
	 */
	public void setIloscGodzinNoc(Integer iloscGodzinNoc) {
		this.iloscGodzinNoc = iloscGodzinNoc;
	}

	/**
	 * @return the iloscGodzinSwieta
	 */
	public Integer getIloscGodzinSwieta() {
		return iloscGodzinSwieta;
	}

	/**
	 * @param iloscGodzinSwieta
	 *            the iloscGodzinSwieta to set
	 */
	public void setIloscGodzinSwieta(Integer iloscGodzinSwieta) {
		this.iloscGodzinSwieta = iloscGodzinSwieta;
	}

	/**
	 * Zwraca dane wykonanej naprawy po id naprawy
	 * 
	 * @param n
	 *            naprawa dla której pobieramy wszystkie wykonane naprawy
	 * @return podwójny vector do u¿ycia w JTABLE
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Vector getWykonanaPraca(Naprawa n) {

		Vector wpt = null;
		try {
			Connection con = null;
			wpt = new Vector();

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztat", "root", "password");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM WykonanaPraca where naprawa=" + n.getIdNaprawy());

			while (rs.next()) {
				Vector vector = new Vector();
				vector.add((Integer) rs.getObject(1));
				vector.add(n.getIdNaprawy());
				vector.add(rs.getObject(3).toString());
				vector.add((Date) rs.getObject(4));
				vector.add(Integer.parseInt(rs.getObject(5).toString()));
				vector.add(Integer.parseInt(rs.getObject(6).toString()));
				vector.add(Integer.parseInt(rs.getObject(7).toString()));
				vector.add(Integer.parseInt(rs.getObject(8).toString()));
				wpt.add(vector);
			}
			rs.close();
			st.close();

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "B³¹d po³¹czenia z baz¹ danych");
		}

		return wpt;

	}

	/**
	 * Zwraca wszystkie wykonane prace danego pracownika
	 * 
	 * @param p
	 *            pracownik którego dane pobieramy
	 * @return podwójny vector do u¿ytku w JTABLE
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Vector getWykonanaPracaPracownik(Pracownik p) {

		Vector wpt = null;
		try {
			Connection con = null;
			wpt = new Vector();

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztat", "root", "password");
			Statement st = con.createStatement();
			ResultSet rs = st
					.executeQuery("SELECT a.iloscGodzin+a.iloscGodzinNoc+a.iloscGodzinSwieta,a.Opis,a.dataPracy,b.ZarejestrowanieWyceny,b.cenaNaprawy,c.Marka,d.PESEL,d.Nazwisko FROM wykonanapraca a inner join naprawa b on a.Naprawa = b.idNaprawa inner join samochod c on b.idNaprawa = c.Naprawa inner join klient d on c.Klient = d.idKlient where a.pracownik="
							+ p.getIdPracownik());

			while (rs.next()) {

				Vector vector = new Vector();
				vector.add(rs.getInt((1)));
				vector.add(rs.getString(2));
				vector.add(rs.getDate(3).toString());
				vector.add((rs.getTimestamp(4)));
				vector.add(rs.getObject(5).toString());
				vector.add(rs.getObject(6).toString());
				vector.add(rs.getObject(7).toString());
				vector.add(rs.getObject(8).toString());
				wpt.add(vector);
			}
			rs.close();
			st.close();

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "B³¹d po³¹czenia z baz¹ danych");
		}
		return wpt;

	}

	/**
	 * Metoda zwraca sumy poszczególnych godzin danego pracownika
	 * 
	 * @param np
	 *            pracownik
	 * @return Vector 1- godziny normalne, 2- godziny nocne, 3- godziny w œwieta
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Vector getPracownikPracaOblicz(Pracownik np) {
		Vector vector = null;
		try {
			Connection con = null;

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztat", "root", "password");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT sum(iloscGodzin),sum(iloscGodzinNoc),sum(iloscGodzinSwieta) FROM wykonanapraca where pracownik ="
					+ np.getIdPracownik());
			rs.next();

			vector = new Vector();
			vector.add(rs.getInt(1));
			vector.add(rs.getInt(2));
			vector.add(rs.getInt(3));

			rs.close();
			st.close();

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "B³¹d po³¹czenia z baz¹ danych");
		}

		return vector;

	}

	/**
	 * Zapisujemy oniekt wykonanej pracy
	 */
	public void saveWykonanaPraca() {
		try {

			String insertTableSQL = "INSERT INTO wykonanapraca"
					+ "(idWykonanaPraca, Naprawa, Opis,dataPracy,iloscGodzin,iloscGodzinNoc,iloscGodzinSwieta,pracownik) VALUES"
					+ "(?,?,?,?,?,?,?,?)";
			Connection con = null;
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztat", "root", "password");
			PreparedStatement preparedStatement = null;

			preparedStatement = con.prepareStatement(insertTableSQL, Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setInt(1, 0);
			preparedStatement.setString(2, idNaprawa.toString());
			preparedStatement.setString(3, opis);
			preparedStatement.setString(4, dataPracy.toString());
			preparedStatement.setString(5, iloscGodzin.toString());
			preparedStatement.setString(6, iloscGodzinNoc.toString());
			preparedStatement.setString(7, iloscGodzinSwieta.toString());
			preparedStatement.setInt(8, getPracownik());

			// execute insert SQL stetement
			preparedStatement.executeUpdate();
			ResultSet gk = preparedStatement.getGeneratedKeys();
			gk.next();
			setIdNaprawa(gk.getInt(1));
			System.out.println("Dodano" + gk.getInt(1));

		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Integer getPracownik() {
		return pracownik;
	}

	public void setPracownik(Integer pracownik) {
		this.pracownik = pracownik;
	}

	/**
	 * Uusuwa wskazan¹ przez nas wykonan¹ prace
	 * 
	 * @param w
	 *            wykonana praca do usuniecia
	 */
	public static void usunWykonanaPrace(WykonanaPraca w) {
		try {
			// STEP 4: Execute a query
			Connection con = null;
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztat", "root", "password");
			System.out.println("Creating statement...");
			Statement stmt;
			String sql = "DELETE FROM wykonanaPraca " + "WHERE idWykonanaPraca =" + w.getIdWykonanaPraca();
			stmt = con.createStatement();
			stmt.executeUpdate(sql);
			System.out.println(sql);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Aktualizacja danego obiektu na bazie danych, po ID
	 */
	public void update() {

		try {

			String updateeSQL = "UPDATE wykonanaPraca SET opis=\"" + opis + "\",iloscGodzin =\"" + iloscGodzin.toString() + "\", iloscGodzinNoc = \""
					+ iloscGodzinNoc + "\", iloscGodzinSwieta =\"" + iloscGodzinSwieta + "\",pracownik=" + pracownik.toString()
					+ " WHERE idWykonanaPraca=" + idWykonanaPraca;
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
