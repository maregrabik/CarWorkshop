package Forms;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

/**
 * 
 * Oknow w którym s¹ przetrzymywane 2 g³owne formularze PracownicyForm,WynagordzeniaPracownika modu³u administracyjnego. Zawieraj¹ siê w JTabbedPane
 *
 */
public class AdminFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	public AdminFrame(){
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 600);
		setLocationRelativeTo(null);
		JTabbedPane jTabbedPane = new JTabbedPane();
		jTabbedPane.add("Pracownicy", new PracownicyForm().getContentPane());
		jTabbedPane.addTab("Wynagrodzenia pracowników", new WynagordzeniaPracownika().getContentPane());
		getContentPane().add(jTabbedPane);
		
	}
	
}
