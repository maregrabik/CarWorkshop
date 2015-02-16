package Forms;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

import Entitity.Naprawa;
import Entitity.Pracownik;
import Entitity.Samochod;
import Entitity.WykonanaPraca;
import Utility.WykonanaPracaModel;

/**
 * 
 * Clasa prezentuj¹ca formularz naprawczy, pod nazw¹ "Rejestracja pracy". Po
 * wybraniu auta mamy mo¿liwosæ zarejestrowania iloœci godzin i wskazania
 * pracownika który wykonywa³ dan¹ prace. <br>
 * Auta które s¹ prezentowane w liœcie nie s¹ oznaczone jako "zakoñczone" w
 * kolummnie bazodanowej statusNaprawy.<br>
 *
 * Auta któr¹ nie maj¹ ¿adnej zarejstrowanej naprawy widniej¹ jako "oczekuj¹ce",
 * po dodaniu jednej naprawy stan przechodzi na "w trakcie", po wciœnieciu
 * przycisku "zakoñcz naprawe", autozostaje oznaczone jako zakoñczone i bêdzie
 * ju¿ widoczne ytylko w zak³dace "zakonczone prace"<br>
 *
 *
 */
@SuppressWarnings("serial")
public class NaprawyForm extends JFrame {

	private JPanel wybierzAutoPanel;
	private JPanel panelEdycji;
	private JTextField markaField;
	private JTextField rokField;
	private JTextField nrSilnikaField;
	@SuppressWarnings("rawtypes")
	private JComboBox stanComboBox;
	private JTextField StatusField;
	private JTextField dataRejestracji;
	private JTable wykonanaPracatable;
	/** ukryty komponent mowicay o id wykonanej pracy */
	private JTextField idWYkonanaPraca;
	private JTextField godzinyField;
	private JTextField godzinyNocneField;
	private JTextField godzinySwietaField;
	private JButton btnWybierzAuto;
	/** G³owny panel samochodów */
	private JPanel carPanel;
	/** G³owny panel pracy */
	private JPanel pracaPanel;
	private JScrollPane scrollPane;
	/** G³owny panel przycisków */
	private JPanel buttonPanel;
	private JButton dodajPraceButton;
	private JButton usunPraceButton;
	private JButton edytujButton;
	private JButton zatwierdzButton;
	private JTextField idSamochod;
	/** ComboBox zawieraj¹cy pracowników */
	private JComboBox pracownicyComboBox;
	/**
	 * obiekt s³u¿y jako pomoc , jest ustalona jako aktywna w moemencie wybrania
	 * samochodu
	 */
	private Naprawa naprawaAktywna;
	private JTextArea opisTextArea;

	/** Model tabeli g³ównej */
	private WykonanaPracaModel wykonanaPracaTableModel;
	private JButton zakonczNapraweButton;

	/** obiekt mówi¹cy o aktualnym aktywnym przetwarzanym samochodzie */
	private Samochod samochodAktywny;

	/**
	 * konstruktor domyœlny
	 */
	public NaprawyForm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 600);
		btnWybierzAuto = new JButton("Wybierz auto");
		btnWybierzAuto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				wybierzAutoAction();
			}

		});

		carPanel = new JPanel();
		carPanel.setBorder(new TitledBorder(null, "Samochod", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		pracaPanel = new JPanel();
		pracaPanel.setBorder(new TitledBorder(null, "Szczeg\u00F3\u0142y wykonanej pracy", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		setWykonanaPracaTable();

		buttonPanel = new JPanel();

		GroupLayout groupLayout = setLayout();

		setButtonPanel();

		setPracaPanel();

		setCarPanel();

		getContentPane().setLayout(groupLayout);
		enablePracaPanell(false);
		enableCarPanell(false);
		setCanEditCar(false);
		enableButtonPanel(false);

	}

	/**
	 * Definuje obiekt scroll pane dla tebeli, be zniego nie bylo by widocznych nag³ówków
	 */
	private void setWykonanaPracaTable() {
		scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new TitledBorder(null, "Wykonana praca", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setWykonanaPracatable(new JTable());
		scrollPane.setViewportView(getWykonanaPracatable());
	}

	
	/**
	 * Definujemy g³ówne obiekty wizualne samochodu
	 */
	private void setCarPanel() {
		carPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		idSamochod = new JTextField();
		carPanel.add(idSamochod);
		idSamochod.setColumns(1);
		idSamochod.setVisible(false);

		JLabel markaLabel = new JLabel("Marka");
		carPanel.add(markaLabel);

		markaField = new JTextField();
		markaField.setEnabled(false);
		carPanel.add(markaField);
		markaField.setColumns(10);

		JLabel lblNewLabel = new JLabel("Rok");
		carPanel.add(lblNewLabel);

		rokField = new JTextField();
		rokField.setEnabled(false);
		carPanel.add(rokField);
		rokField.setColumns(10);

		JLabel nrSilnikaLabel = new JLabel("nrSilnika");
		carPanel.add(nrSilnikaLabel);

		nrSilnikaField = new JTextField();
		nrSilnikaField.setEnabled(false);
		carPanel.add(nrSilnikaField);
		nrSilnikaField.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Stan");
		carPanel.add(lblNewLabel_1);

		stanComboBox = new JComboBox();
		
		//Wartoœci dla stanu auta w combo
		stanComboBox.setModel(new DefaultComboBoxModel(new String[] { "Z\u0142y", "\u015Aredni", "Dobry" }));
		stanComboBox.setEnabled(false);
		carPanel.add(stanComboBox);

		JLabel lblNewLabel_2 = new JLabel("Status");
		carPanel.add(lblNewLabel_2);

		StatusField = new JTextField();
		StatusField.setEnabled(false);
		carPanel.add(StatusField);
		StatusField.setColumns(6);

		JLabel lblNewLabel_3 = new JLabel("Data rejestracji auta");
		carPanel.add(lblNewLabel_3);

		dataRejestracji = new JTextField();
		dataRejestracji.setEnabled(false);
		carPanel.add(dataRejestracji);
		dataRejestracji.setColumns(10);
	}

	/**
	 * Definiujemy g³ówne obiekty wizualne panelu z wykonana prac¹
	 */
	private void setPracaPanel() {
		idWYkonanaPraca = new JTextField();
		idWYkonanaPraca.setVisible(false);
		pracaPanel.add(idWYkonanaPraca);
		idWYkonanaPraca.setColumns(1);

		JLabel lblNewLabel_4 = new JLabel("Godziny:");
		pracaPanel.add(lblNewLabel_4);

		godzinyField = new JTextField();
		pracaPanel.add(godzinyField);
		godzinyField.setColumns(2);
//		godzinyField.setDocument(new OnlyNumberValidatorr());

		JLabel lblNewLabel_5 = new JLabel("Godziny nocne:");
		pracaPanel.add(lblNewLabel_5);

		godzinyNocneField = new JTextField();
//		godzinyNocneField.setDocument(new OnlyNumberValidatorr());
		pracaPanel.add(godzinyNocneField);
		godzinyNocneField.setColumns(2);

		JLabel lblNewLabel_6 = new JLabel("Godziny w \u015Bwieta");
		pracaPanel.add(lblNewLabel_6);

		godzinySwietaField = new JTextField();
//		godzinySwietaField.setDocument(new OnlyNumberValidatorr());
		pracaPanel.add(godzinySwietaField);
		godzinySwietaField.setColumns(2);

		JLabel lblNewLabel_7 = new JLabel("Opis wykonanej pracy");
		pracaPanel.add(lblNewLabel_7);

		opisTextArea = new JTextArea();
		opisTextArea.setTabSize(15);
		opisTextArea.setRows(3);
		opisTextArea.setColumns(15);
		pracaPanel.add(opisTextArea);

		JLabel pracwonikLabel = new JLabel("Pracownik");
		pracaPanel.add(pracwonikLabel);

		pracownicyComboBox = new JComboBox(Pracownik.fetchPracownicy());
		pracaPanel.add(pracownicyComboBox);
	}

	
	/**
	 * Definiujemy g³ówne przyciski i action listenery w panelu 
	 */
	private void setButtonPanel() {
		setDodajPrace(new JButton("Dodaj"));
		buttonPanel.add(dodajPraceButton);
		dodajPraceButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dodajPraceAction();

			}
		});

		usunPraceButton = new JButton("Usu\u0144");
		usunPraceButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				usunAction();

			}
		});
		buttonPanel.add(usunPraceButton);

		edytujButton = new JButton("Edytuj");
		edytujButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				edytujAction();

			}
		});
		buttonPanel.add(edytujButton);

		zatwierdzButton = new JButton("Zatwierdz");
		zatwierdzButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(valid()){
					zatwierdzAction();					
				}else{
					JOptionPane.showMessageDialog(null, "Godziny musz¹ byc liczbami");
				}
			}
		});
		buttonPanel.add(zatwierdzButton);

		setZakonczNapraweButton(new JButton("Zakoncz naprawe"));
		getZakonczNapraweButton().setToolTipText("Ko\u0144czy naprawe i oznacza autoa jako zako\u0144czone");
		getZakonczNapraweButton().setIcon(new ImageIcon(NaprawyForm.class.getResource("/com/sun/java/swing/plaf/windows/icons/TreeLeaf.gif")));
		getZakonczNapraweButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				zakonczAction();

			}
		});
		buttonPanel.add(getZakonczNapraweButton());
	}

	
	/**
	 * Akcja po wcisniêciu przycisku Edytuj
	 */
	protected void edytujAction() {

		if (edytujButton.getText().equals("Anuluj")) {
			enablePracaPanell(false);
			dodajPraceButton.setEnabled(true);
			usunPraceButton.setEnabled(true);
			zakonczNapraweButton.setEnabled(true);
		} else {
			edytujButton.setText("Anuluj");
			zatwierdzButton.setEnabled(true);
			enablePracaPanell(true);
			dodajPraceButton.setEnabled(false);
			usunPraceButton.setEnabled(false);
			zakonczNapraweButton.setEnabled(false);
		}

	}

	/**
	 * Ustawia obiekt group layout któtry agreujê obiekty w odpowiednim po³o¿eniu
	 * @return 
	 */
	private GroupLayout setLayout() {
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addGroup(
								groupLayout
										.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup().addGap(427).addComponent(btnWybierzAuto))
										.addGroup(
												groupLayout
														.createSequentialGroup()
														.addGap(209)
														.addGroup(
																groupLayout
																		.createParallelGroup(Alignment.LEADING)
																		.addComponent(buttonPanel, GroupLayout.PREFERRED_SIZE, 490,
																				GroupLayout.PREFERRED_SIZE)
																		.addComponent(pracaPanel, GroupLayout.PREFERRED_SIZE, 465,
																				GroupLayout.PREFERRED_SIZE)))
										.addGroup(
												groupLayout.createSequentialGroup().addGap(133)
														.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 698, GroupLayout.PREFERRED_SIZE))
										.addGroup(
												groupLayout
														.createSequentialGroup()
														.addGap(29)
														.addComponent(carPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))).addContainerGap(136, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				groupLayout.createSequentialGroup().addGap(21).addComponent(btnWybierzAuto).addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(carPanel, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE).addGap(38)
						.addComponent(pracaPanel, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(buttonPanel, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE).addContainerGap(49, Short.MAX_VALUE)));
		return groupLayout;
	}

	
	
	/**
	 * Akcja po wybraniu auta, tworzymy nowe okno z wyborem aut które nie s¹ zakoñczone
	 */
	private void wybierzAutoAction() {
		WybierzAutoFrame wybierzAutoFrame = new WybierzAutoFrame(this);
		wybierzAutoFrame.setVisible(true);
	}

	
	
	
	
	/**
	 * Metoda wywo³ywan z WybierzAutoFrame która ustawia odpowiednie dane po wybraniu auta w oknie
	 * 
	 * @param  Samochód który zostanie ustalowiony w panelu
	 */
	public void setSamochodFields(Samochod s) {
		samochodAktywny = s;
		this.markaField.setText(s.getMarka());
		this.rokField.setText(s.getRok());
		this.nrSilnikaField.setText(s.getNrSilnika());
		this.stanComboBox.setSelectedItem(s.getStanAuta());
		this.idSamochod.setText(s.getIdSamochod().toString());
		this.StatusField.setText(s.getStatusNaprawy());
		naprawaAktywna = Naprawa.getNaprawaSamochodu(s);
		String dataDisp = new SimpleDateFormat("MM/dd/yyyy").format(naprawaAktywna.getZarejestWycenyTimestamp());
		this.dataRejestracji.setText(dataDisp);

		//ustalamy model dla tabeli zawierj¹cy prace danego samochodu
		wykonanaPracaTableModel = new WykonanaPracaModel(naprawaAktywna);
		wykonanaPracatable.setModel(wykonanaPracaTableModel);
		wykonanaPracaTableModel.setSelectionListener(this);
		if (wykonanaPracaTableModel.getRowCount() > 0) {
			zakonczNapraweButton.setEnabled(true);
		}
		
		//ukrywamy kolumny w tabeli idWykNaprawy,idNaprawy,IdPracownik
		hideModelColumns(0);
		hideModelColumns(1);
		hideModelColumns(7);
	}

	
	/**
	 * Sterowanie blokowaniem panelu pracy
	 * @param b
	 */
	public void enablePracaPanell(boolean b) {
		Component[] components = pracaPanel.getComponents();
		for (Component component : components) {
			component.setEnabled(b);
		}

	}

	/**
	 * Sterowanie blokowaniem panelu samochodu
	 * @param b
	 */
	public void enableCarPanell(boolean b) {
		Component[] components = carPanel.getComponents();
		for (Component component : components) {
			component.setEnabled(b);
		}
		this.stanComboBox.setEnabled(false);

	}
	/**
	 * Sterowanie blokowaniem panelu przyciksów
	 * @param b
	 */
	public void enableButtonPanel(boolean b) {
		Component[] components = buttonPanel.getComponents();
		for (Component component : components) {
			component.setEnabled(b);
		}

	}

	/**
	 * Sterowanie ustawianie jako tylko do wygl¹du elementów edytowalnych
	 * @param b
	 */
	public void setCanEditCar(boolean b) {
		Component[] components = carPanel.getComponents();
		for (Component component : components) {
			if (component instanceof JTextField) {
				((JTextComponent) component).setEditable(false);
			}
		}

	}

	/**
	 * Czyszczenie komponentów
	 */
	private void cleanPracaTextPanel() {
		Component[] components = pracaPanel.getComponents();
		for (Component C : components) {
			if (C instanceof JTextField) {
				((JTextComponent) C).setText(""); // abstract superclass
			}
		}
		opisTextArea.setText("");

	}

	/**
	 * Czyszczenie komponentów
	 */
	private void cleanKlientTextPanel() {
		Component[] components = carPanel.getComponents();
		for (Component C : components) {
			if (C instanceof JTextField) {
				((JTextComponent) C).setText(""); // abstract superclass
			}
		}
	}

	
	/**
	 * Akcja po wcisniecu przycisku dodaj
	 */
	private void dodajPraceAction() {

		if (dodajPraceButton.getText().equals("Anuluj")) {
			cleanPracaTextPanel();
			enablePracaPanell(false);
			zatwierdzButton.setEnabled(false);
			dodajPraceButton.setText("Dodaj");
			usunPraceButton.setEnabled(true);
			edytujButton.setEnabled(true);
			btnWybierzAuto.setEnabled(true);
			wykonanaPracatable.setEnabled(true);

		} else {
			enablePracaPanell(true);
			zatwierdzButton.setEnabled(true);
			dodajPraceButton.setText("Anuluj");
			usunPraceButton.setEnabled(false);
			edytujButton.setEnabled(false);
			btnWybierzAuto.setEnabled(false);
			wykonanaPracatable.setEnabled(false);
			cleanPracaTextPanel();
		}

	}

	/**
	 * Akcja po wcisnieciu przycisku zatwierdŸ
	 */
	private void zatwierdzAction() {
		WykonanaPraca wykonanaPraca = getWykonanaPracaFields();

		
		//jezeli id istnieje robimy aktulizacje, brak - nowy wpis
		if (wykonanaPraca.getIdWykonanaPraca() != null) {
			wykonanaPraca.update();

		} else {

			wykonanaPraca.saveWykonanaPraca();
			Samochod samochod = new Samochod();
			samochod.setIdSamochod(Integer.parseInt(idSamochod.getText()));
			samochod.setStatusWTrakcie();
			StatusField.setText(samochod.getStatusNaprawy());
		}

		//odswierzamy tabele
		wykonanaPracaTableModel.refresCarTable(naprawaAktywna);
		
		cleanPracaTextPanel();
		enablePracaPanell(false);
		zatwierdzButton.setEnabled(false);
		wykonanaPracatable.setEnabled(true);
		zakonczNapraweButton.setEnabled(true);

	}

	
	/**
	 * Tworzy obiekt wykonanej pracy na podstawie pól na formularzu
	 * @return WykonanaPraca z formularza
	 */
	private WykonanaPraca getWykonanaPracaFields() {

		WykonanaPraca wykonanaPraca = new WykonanaPraca();
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date date = new java.sql.Date(utilDate.getTime());
		wykonanaPraca.setIloscGodzin(Integer.parseInt(godzinyField.getText()));
		wykonanaPraca.setIloscGodzinNoc(Integer.parseInt(godzinyNocneField.getText()));
		wykonanaPraca.setIloscGodzinSwieta(Integer.parseInt(godzinySwietaField.getText()));
		wykonanaPraca.setOpis(opisTextArea.getText());
		wykonanaPraca.setIdNaprawa(naprawaAktywna.getIdNaprawy());
		wykonanaPraca.setDataPracy(date);
		wykonanaPraca.setPracownik(((Pracownik) pracownicyComboBox.getSelectedItem()).getIdPracownik());

		if (!idWYkonanaPraca.getText().isEmpty()) {
			wykonanaPraca.setIdWykonanaPraca(Integer.parseInt(idWYkonanaPraca.getText()));
		}

		return wykonanaPraca;
	}

	public JButton getDodajPrace() {
		return dodajPraceButton;
	}

	public void setDodajPrace(JButton dodajPrace) {
		this.dodajPraceButton = dodajPrace;
	}

	/**
	 * Chowwamy dana kolumne zmniejszaj¹c jej rozmiary
	 * @param arg
	 */
	public void hideModelColumns(int arg) {
		getWykonanaPracatable().getColumnModel().getColumn(arg).setMinWidth(0);
		getWykonanaPracatable().getColumnModel().getColumn(arg).setMaxWidth(0);
		getWykonanaPracatable().getColumnModel().getColumn(arg).setPreferredWidth(0);
	}

	public JTable getWykonanaPracatable() {
		return wykonanaPracatable;
	}

	public void setWykonanaPracatable(JTable wykonanaPracatable) {
		this.wykonanaPracatable = wykonanaPracatable;
	}

	
	/**
	 * ustala pola guid po obiekcie 
	 * @param wykonanaPraca
	 */
	public void setWykonaPracaFields(WykonanaPraca wykonanaPraca) {
		idWYkonanaPraca.setText(wykonanaPraca.getIdWykonanaPraca().toString());
		godzinyField.setText(wykonanaPraca.getIloscGodzin().toString());
		godzinyNocneField.setText((wykonanaPraca.getIloscGodzinNoc().toString()));
		godzinySwietaField.setText(wykonanaPraca.getIloscGodzinSwieta().toString());
		opisTextArea.setText(wykonanaPraca.getOpis());
		Pracownik pracownik = new Pracownik();
		pracownik.setIdPracownik((wykonanaPraca.getPracownik()));
		pracownik.fetch();

		if (pracownik.getImie() != null && pracownik.getNazwisko() != null) {
			pracownicyComboBox.setSelectedItem(pracownik);
		} else {
			JOptionPane.showConfirmDialog(null, "Pracownik wykonuj¹cy ten etap zosta³ usuniêty", "Ostrze¿enie", 0);
		}

	}

	public JButton getEdytujButton() {
		return edytujButton;
	}

	public void setEdytujButton(JButton edytujButton) {
		this.edytujButton = edytujButton;
	}

	public JButton getUsunPraceButton() {
		return usunPraceButton;
	}

	public void setUsunPraceButton(JButton usunPraceButton) {
		this.usunPraceButton = usunPraceButton;
	}

	/**
	 * Akcja po wcisnieciu przycisku usun
	 */
	private void usunAction() {

		int dialogResult = JOptionPane.showConfirmDialog(null, "Na pewno chcesz usun¹æ dan¹ naprawê?", "Ostrze¿enie", 0);
		if (dialogResult == JOptionPane.YES_OPTION) {
			WykonanaPraca w = new WykonanaPraca();
			w.setIdWykonanaPraca(Integer.parseInt(idWYkonanaPraca.getText()));
			WykonanaPraca.usunWykonanaPrace(w);
			wykonanaPracaTableModel.refresCarTable(naprawaAktywna);
		}

	}

	/**
	 * akcja po wcisnieciu przyski zakoncz
	 */
	private void zakonczAction() {

		int dialogResult = JOptionPane.showConfirmDialog(null, "Auto zostanie oznaczone jako sprawne i gotowe do oddanie, wykonaæ?", "Informacja", 0);
		if (dialogResult == JOptionPane.YES_OPTION) {
			samochodAktywny.setStatusNaprawy("Zakoñczony");
			samochodAktywny.update();
			cleanPracaTextPanel();
			cleanKlientTextPanel();
			wykonanaPracatable.setModel(new DefaultTableModel());
			enableCarPanell(false);
			enableButtonPanel(false);
		}

	}

	public JButton getZakonczNapraweButton() {
		return zakonczNapraweButton;
	}

	public void setZakonczNapraweButton(JButton zakonczNapraweButton) {
		this.zakonczNapraweButton = zakonczNapraweButton;
	}
	private boolean valid(){
		if (godzinyField.getText().matches("[0-9]+") && godzinyNocneField.getText().matches("[0-9]+") && godzinySwietaField.getText().matches("[0-9]+")) {
			return true;
			
		}else{
			
			return false;
		}
	}
	
}
