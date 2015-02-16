/**
 * 
 */
package Forms;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import Entitity.Pracownik;
import Entitity.TypPracownika;
import Entitity.WykonanaPraca;

/**
 * Fromularz zawieraj¹cy liczbe godzizn przepracown¹ przez pracownika oraz dni wolne. Ponadto mo¿liwoœæ podejrzenia wszystkich jego p[rac wykonanych
 *
 */
public class WynagordzeniaPracownika extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField nazwiskoTextField;
	private JTextField peselTextField;
	private JTextField liczbaGodzinStandardField;
	private JTextField godzinyNocneField;
	private JTextField godzinyWSwietaField;
	private JTextField typTextField;
	private JTextField lacznaKwota;
	private JTextField godzinyWolne;
	private JTable detailTable;
	private JButton wybierzPracownika;
	private JPanel obliczeniaPracyPane;
	private JPanel danePracownika;
	private JScrollPane szczegolyPracyScroll;
	private JPanel wybierzPanel;
	private JTextField imieTextField;
	private JTextField idPracownik;
	private JTextField stawkaGodzinna;

	public WynagordzeniaPracownika() {

		createWybierzButtonPane();

		danePracownikaPane();

		createObliczaniePanel();

		createSzczegolyPracyPane();

		GroupLayout groupLayout = createLayout();

		getContentPane().setLayout(groupLayout);

		enableObliczeniaPanel(false);
		enablePracownikPanel(false);
	}

	public void enablePracownikPanel(boolean b) {
		Component[] components = danePracownika.getComponents();
		for (Component component : components) {
			component.setEnabled(b);
		}
	}

	public void enableObliczeniaPanel(boolean b) {
		Component[] components = obliczeniaPracyPane.getComponents();
		for (Component component : components) {
			component.setEnabled(b);
		}

	}

	private GroupLayout createLayout() {
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(
						Alignment.TRAILING,
						groupLayout
								.createSequentialGroup()
								.addGroup(
										groupLayout
												.createParallelGroup(Alignment.TRAILING)
												.addGroup(
														Alignment.LEADING,
														groupLayout
																.createSequentialGroup()
																.addGap(353)
																.addComponent(wybierzPanel, GroupLayout.PREFERRED_SIZE, 171,
																		GroupLayout.PREFERRED_SIZE))
												.addGroup(
														Alignment.LEADING,
														groupLayout
																.createSequentialGroup()
																.addGap(71)
																.addGroup(
																		groupLayout
																				.createParallelGroup(Alignment.LEADING)
																				.addComponent(obliczeniaPracyPane, GroupLayout.PREFERRED_SIZE, 739,
																						GroupLayout.PREFERRED_SIZE)
																				.addComponent(danePracownika, GroupLayout.DEFAULT_SIZE, 739,
																						Short.MAX_VALUE)))
												.addGroup(
														Alignment.LEADING,
														groupLayout.createSequentialGroup().addContainerGap()
																.addComponent(szczegolyPracyScroll, GroupLayout.DEFAULT_SIZE, 896, Short.MAX_VALUE)))
								.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(wybierzPanel, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE).addGap(34)
						.addComponent(danePracownika, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE).addGap(18)
						.addComponent(obliczeniaPracyPane, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE).addGap(18)
						.addComponent(szczegolyPracyScroll, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(26, Short.MAX_VALUE)));
		return groupLayout;
	}

	private void createSzczegolyPracyPane() {
		szczegolyPracyScroll = new JScrollPane();
		szczegolyPracyScroll.setViewportBorder(new TitledBorder(null, "Szeg\u00F3\u0142y pracy", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		detailTable = new JTable();
		szczegolyPracyScroll.setViewportView(detailTable);
		detailTable.setVisible(false);
	}

	private void createObliczaniePanel() {
		obliczeniaPracyPane = new JPanel();
		obliczeniaPracyPane.setBorder(new TitledBorder(null, "Dane na temat pracy", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		obliczeniaPracyPane.setLayout(null);

		JLabel lidzba = new JLabel("Liczba zarejestrowanych godzin standardowych");
		lidzba.setBounds(23, 24, 334, 14);
		obliczeniaPracyPane.add(lidzba);

		liczbaGodzinStandardField = new JTextField();
		liczbaGodzinStandardField.setBounds(367, 21, 39, 20);
		obliczeniaPracyPane.add(liczbaGodzinStandardField);
		liczbaGodzinStandardField.setColumns(4);

		JLabel lblNewLabel_2 = new JLabel("Godzin nocnych");
		lblNewLabel_2.setBounds(416, 24, 119, 14);
		obliczeniaPracyPane.add(lblNewLabel_2);

		godzinyNocneField = new JTextField();
		godzinyNocneField.setBounds(529, 21, 38, 20);
		obliczeniaPracyPane.add(godzinyNocneField);
		godzinyNocneField.setColumns(4);

		JLabel lblNewLabel_3 = new JLabel("Godzin w \u015Bw\u0119ta");
		lblNewLabel_3.setBounds(577, 24, 111, 14);
		obliczeniaPracyPane.add(lblNewLabel_3);

		godzinyWSwietaField = new JTextField();
		godzinyWSwietaField.setBounds(682, 21, 38, 20);
		obliczeniaPracyPane.add(godzinyWSwietaField);
		godzinyWSwietaField.setColumns(4);

		JLabel lblNewLabel_5 = new JLabel("\u0141\u0105czna kwota zarobiona w okresie funcjonowania, z\u0142");
		lblNewLabel_5.setBounds(23, 62, 334, 14);
		obliczeniaPracyPane.add(lblNewLabel_5);

		lacznaKwota = new JTextField();
		lacznaKwota.setBounds(367, 56, 119, 20);
		obliczeniaPracyPane.add(lacznaKwota);
		lacznaKwota.setColumns(5);

		JLabel lblWolneGodzinyPrzysugujce = new JLabel("Wolne godziny przys\u0142uguj\u0105ce za prace w \u015Bwi\u0119ta");
		lblWolneGodzinyPrzysugujce.setBounds(23, 105, 334, 14);
		obliczeniaPracyPane.add(lblWolneGodzinyPrzysugujce);

		godzinyWolne = new JTextField();
		godzinyWolne.setBounds(367, 102, 28, 20);
		obliczeniaPracyPane.add(godzinyWolne);
		godzinyWolne.setColumns(10);

		JButton szczegolyPracyButton = new JButton("Wy\u015Bwietl prace w kt\u00F3rej bra\u0142 udzia\u0142 pracownik");
		szczegolyPracyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				wyswietlSzczegolyAction();
			}
		});
		szczegolyPracyButton.setBounds(416, 101, 313, 23);
		obliczeniaPracyPane.add(szczegolyPracyButton);
	}

	protected void wyswietlSzczegolyAction() {
		if (detailTable.isVisible()) {
			detailTable.setVisible(false);
		} else {
			detailTable.setVisible(true);
		}

	}

	private void danePracownikaPane() {
		danePracownika = new JPanel();
		danePracownika.setBorder(new TitledBorder(null, "Dane pracownika", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		stawkaGodzinna = new JTextField();
		danePracownika.add(stawkaGodzinna);
		stawkaGodzinna.setColumns(1);
		stawkaGodzinna.setVisible(false);

		idPracownik = new JTextField();
		danePracownika.add(idPracownik);
		idPracownik.setColumns(1);
		idPracownik.setVisible(false);
		JLabel lblNewLabel = new JLabel("Imie");
		danePracownika.add(lblNewLabel);

		imieTextField = new JTextField();
		danePracownika.add(imieTextField);
		imieTextField.setColumns(10);

		JLabel Nazwisko = new JLabel("Nazwisko");
		danePracownika.add(Nazwisko);

		nazwiskoTextField = new JTextField();
		danePracownika.add(nazwiskoTextField);
		nazwiskoTextField.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("PESEL");
		danePracownika.add(lblNewLabel_1);

		peselTextField = new JTextField();
		danePracownika.add(peselTextField);
		peselTextField.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("Typ");
		danePracownika.add(lblNewLabel_4);

		typTextField = new JTextField();
		danePracownika.add(typTextField);
		typTextField.setColumns(10);
	}

	private void createWybierzButtonPane() {
		wybierzPanel = new JPanel();
		wybierzPracownika = new JButton("Wybierz pracownika");
		wybierzPracownika.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				wybierzAction();

			}
		});
		wybierzPanel.add(wybierzPracownika);
	}

	protected void wybierzAction() {
		WybierzPracownikaForm wybierzPracownikaForm = new WybierzPracownikaForm(this);
		wybierzPracownikaForm.setVisible(true);
	}

	public void setPracownikFields(Pracownik pracownik) {
		idPracownik.setText(pracownik.getIdPracownik().toString());
		imieTextField.setText(pracownik.getImie());
		nazwiskoTextField.setText(pracownik.getNazwisko());
		peselTextField.setText(pracownik.getPESEL());
		TypPracownika typPracownika = new TypPracownika();
		typPracownika.setIdTypu(pracownik.getTypPracownika());
		typPracownika.fetch();
		typTextField.setText(typPracownika.getNazwaTypu());
		stawkaGodzinna.setText(pracownik.getStawkaGodzinna().toString());
	}

	/**
	 * ustalamy jako tylko do odczytu
	 * @param b
	 */
	public void setreadOnly(boolean b) {
		Component[] components = danePracownika.getComponents();
		for (Component component : components) {

			if (component.getClass() == JTextField.class) {
				((JTextField) component).setEditable(b);
			}
		}

		components = obliczeniaPracyPane.getComponents();
		for (Component component : components) {

			if (component.getClass() == JTextField.class) {
				((JTextField) component).setEditable(b);
			}
		}

	}

	@SuppressWarnings("rawtypes")
	public void setObliczeniaFields(Pracownik pracownik) {
		Vector obliczenia = WykonanaPraca.getPracownikPracaOblicz(pracownik);
		double a, b, c, d, x;
		liczbaGodzinStandardField.setText(obliczenia.get(0).toString());
		a = (int) obliczenia.get(0);
		godzinyNocneField.setText(obliczenia.get(1).toString());
		b = (int) obliczenia.get(1);
		godzinyWSwietaField.setText(obliczenia.get(2).toString());
		c = (int) obliczenia.get(2);
		d = Double.parseDouble(stawkaGodzinna.getText());

		x = (a * d) + (b * 1.33 * d) + (c * 1.5 * d);
		lacznaKwota.setText(String.valueOf(x));

		x = c * 2;
		godzinyWolne.setText(String.valueOf(x));

	}

	public JTable getDetailTable() {
		return detailTable;
	}

	public void setDetailTable(JTable detailTable) {
		this.detailTable = detailTable;
	}

}
