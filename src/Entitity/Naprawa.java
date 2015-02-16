package Entitity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;


/**
 * 
 * Klasa przedstawia encje z bazy danych 
 *
 */
public class Naprawa {

	Integer idNaprawy;
	Timestamp zarejestWycenyTimestamp;
	Integer idPracownik;
	Timestamp rozpoczNapTimestamp;
	Timestamp zakonczNaprTimestamp;
	private String cenaNaprawy;
	
	
	/**
	 * @return the idNaprawy
	 */
	public Integer getIdNaprawy() {
		return idNaprawy;
	}
	/**
	 * @param idNaprawy the idNaprawy to set
	 */
	public void setIdNaprawy(Integer idNaprawy) {
		this.idNaprawy = idNaprawy;
	}
	/**
	 * @return the zarejestWycenyTimestamp
	 */
	public Timestamp getZarejestWycenyTimestamp() {
		return zarejestWycenyTimestamp;
	}
	/**
	 * @param zarejestWycenyTimestamp the zarejestWycenyTimestamp to set
	 */
	public void setZarejestWycenyTimestamp(Timestamp zarejestWycenyTimestamp) {
		this.zarejestWycenyTimestamp = zarejestWycenyTimestamp;
	}
	/**
	 * @return the idPracownik
	 */
	public Integer getIdPracownik() {
		return idPracownik;
	}
	/**
	 * @param idPracownik the idPracownik to set
	 */
	public void setIdPracownik(Integer idPracownik) {
		this.idPracownik = idPracownik;
	}
	/**
	 * @return the rozpoczNapTimestamp
	 */
	public Timestamp getRozpoczNapTimestamp() {
		return rozpoczNapTimestamp;
	}
	/**
	 * @param rozpoczNapTimestamp the rozpoczNapTimestamp to set
	 */
	public void setRozpoczNapTimestamp(Timestamp rozpoczNapTimestamp) {
		this.rozpoczNapTimestamp = rozpoczNapTimestamp;
	}
	/**
	 * @return the zakonczNaprTimestamp
	 */
	public Timestamp getZakonczNaprTimestamp() {
		return zakonczNaprTimestamp;
	}
	/**
	 * @param zakonczNaprTimestamp the zakonczNaprTimestamp to set
	 */
	public void setZakonczNaprTimestamp(Timestamp zakonczNaprTimestamp) {
		this.zakonczNaprTimestamp = zakonczNaprTimestamp;
	}
	public String getCenaNaprawy() {
		return cenaNaprawy;
	}
	public void setCenaNaprawy(String cenaNaprawy) {
		this.cenaNaprawy = cenaNaprawy;
	}
	
	
	
	
	/**
	 * Zapis aktualnego obiektu naprawy w bazie danych
	 */
	public void saveNaprawa(){
		try {
		String insertTableSQL = "INSERT INTO naprawa"
				+ "(idNaprawa, cenaNaprawy, ZarejestrowanieWyceny) VALUES"
				+ "(?,?,?)";
		Connection con = null;
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztat", "root", "password");
		PreparedStatement preparedStatement = null;
		
		//mówimy aby zapytanie zwróci³o wygenerowany przez AI klucz g³owny
		preparedStatement = con.prepareStatement(insertTableSQL,Statement.RETURN_GENERATED_KEYS);

		// jako id przesy³amy 0 z raci AI po stronie bazy danych
		preparedStatement.setInt(1, 0);
		preparedStatement.setString(2, cenaNaprawy);
		preparedStatement.setString(3, zarejestWycenyTimestamp.toString());
		
		// execute insert SQL stetement
		preparedStatement.executeUpdate();
		
		//pobieramy klucz g³owny wygenerowany porzez AI bazy danych
		ResultSet gk = preparedStatement.getGeneratedKeys();
		gk.next();
		
		setIdNaprawy(gk.getInt(1));
		System.out.println("Dodano" + gk.getInt(1));
		
	}catch (SQLException | ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}
	
	
	
	/**
	 * Pobieranie danych naprawy  auta przeslanego w parametrze 
	 * 
	 * @param s samochód zawierj¹cy ID dla którego pobieramy dane naprawy 
	 * @return  Naprawa dla danego samochodu 
	 */
	public static Naprawa getNaprawaSamochodu(Samochod s){
		
		Naprawa naprawa = null;
		try {
			Connection con = null;
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztat", "root",
					"password");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT a.* FROM naprawa a inner join samochod  b on a.idNaprawa = b.Naprawa where idSamochod ="
					+ s.getIdSamochod().toString());
			rs.next();
			naprawa = new Naprawa();
			naprawa.setIdNaprawy((Integer) rs.getObject(1));
			naprawa.setZarejestWycenyTimestamp(Timestamp.valueOf(rs.getObject(2).toString()));
			naprawa.setCenaNaprawy(rs.getObject(3).toString());
			rs.close();
			st.close();
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return naprawa;
		
	}
	
	
	/**
	 * Aktualizacja stanu obiektu po stronie bazy danych
	 */
	public void update() {

		try {

			String updateeSQL = "UPDATE naprawa SET cenaNaprawy=\""+cenaNaprawy+ "\" WHERE idNaprawa=" + idNaprawy.toString();
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
