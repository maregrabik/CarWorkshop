package Forms;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;

import Entitity.Klient;
import Entitity.Naprawa;
import Entitity.Samochod;
import Utility.TabelaAutModel;

/**
 * Klasa przyjumj¹ca auta do naprawy.
 * Daje mo¿lwioœæ dodania nowego auta oraz klienta/ lub powi¹zania go ju¿ z istniej¹cym.<br>
 * Wszystkie auta dodane odzera zostaj¹ ustalone jako auta "oczekuj¹ce" w statusie naprawy.<br>
 * 
 * Jest rónie¿ baz¹ dla formularzy Zakoñczone prace oraz Rejestracja prac które s¹ trzymane w jTabbedPane
 */
public class MechFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField imieField;
	private JTextField nazwiskoField;
	private JTextField peselField;
	private GroupLayout gl_przyjmowanieAutJPanel;
	private JPanel klientPane;
	private JScrollPane tabelaAutScrPane;
	private JTable tabelaAut;
	private JPanel addEdditButtonPanel;
	private JPanel carTextPanel;
	private JButton dodajButton;
	private JButton zatwierdzEdycjeButton;
	private JLabel markaLabel;
	private JTextField rokField;
	private JTextField nrSilnikaField;
	private JTextField nrPodwoziaField;
	private JTextField markaField;
	private JTextField idKlient;
	private JButton dodajKlienta;
	private TabelaAutModel tabelaAutModel;
	private JTextField textField;
	private JTextField idSamochod;
	private JTextField wycenaText;
	private JComboBox comboBoxStan;
	private JCheckBox czyStalyKlient;
	private JButton usunButton;
	private JButton edytujButton;
	private JButton skojarzKlientaButton;

	/**
	 * Konstruktor domyœlny
	 */
	public MechFrame() {
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 600);
		setLocationRelativeTo(null);
		JTabbedPane jTabbedPane = new JTabbedPane();
		//pierwszy formularz tworzony w tej klasie 
		jTabbedPane.addTab("Przyjmowanie auta", createZarejestrujAutoPanel());
		jTabbedPane.addTab("Rejestracja pracy", new NaprawyForm().getContentPane());
		jTabbedPane.addTab("Zakoñczone prace", new ZakonczonePraceForm().getContentPane());
		getContentPane().add(jTabbedPane);
	}

	
	/**
	 * Tworzymy panel w którym znajduj¹ siê g³óowny formularz rejestrowania aut oraz dane layoutu
	 * @return
	 */
	private JPanel createZarejestrujAutoPanel() {

		JPanel przyjmowanieAutJPanel = new JPanel();

		createCarTextPanel();

		createTabelaAut();

		createAddEditButtonPanel();

		createKlientPanel();

		gl_przyjmowanieAutJPanel = new GroupLayout(przyjmowanieAutJPanel);
		gl_przyjmowanieAutJPanel.setHorizontalGroup(gl_przyjmowanieAutJPanel
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_przyjmowanieAutJPanel.createSequentialGroup().addContainerGap()
								.addComponent(addEdditButtonPanel, GroupLayout.DEFAULT_SIZE, 749, Short.MAX_VALUE).addGap(20))
				.addGroup(
						gl_przyjmowanieAutJPanel.createSequentialGroup().addContainerGap()
								.addComponent(klientPane, GroupLayout.DEFAULT_SIZE, 759, Short.MAX_VALUE).addContainerGap())
				.addGroup(
						gl_przyjmowanieAutJPanel.createSequentialGroup().addContainerGap()
								.addComponent(tabelaAutScrPane, GroupLayout.DEFAULT_SIZE, 759, Short.MAX_VALUE).addContainerGap())
				.addGroup(
						gl_przyjmowanieAutJPanel.createSequentialGroup().addComponent(carTextPanel, GroupLayout.DEFAULT_SIZE, 779, Short.MAX_VALUE)
								.addContainerGap()));
		gl_przyjmowanieAutJPanel.setVerticalGroup(gl_przyjmowanieAutJPanel.createParallelGroup(Alignment.LEADING).addGroup(
				gl_przyjmowanieAutJPanel.createSequentialGroup()
						.addComponent(carTextPanel, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(klientPane, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(addEdditButtonPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(tabelaAutScrPane, GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
						.addContainerGap()));
		setTabGroupLayout();
		przyjmowanieAutJPanel.setLayout(gl_przyjmowanieAutJPanel);
		return przyjmowanieAutJPanel;

	}

	/**
	 * Panel przycisków
	 */
	private void createAddEditButtonPanel() {
		addEdditButtonPanel = new JPanel();
		addEdditButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		dodajButton = new JButton("Dodaj");
		dodajButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dodajAction();
			}
		});
		addEdditButtonPanel.add(dodajButton);

		setUsunButton(new JButton("Usu\u0144 auto"));
		getUsunButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				usunAction();
			}
		});
		getUsunButton().setEnabled(false);

		addEdditButtonPanel.add(getUsunButton());

		edytujButton = new JButton("Edytuj auto");

		edytujButton.setEnabled(false);
		edytujButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				edytujAction();
			}
		});
		addEdditButtonPanel.add(getEdytujButton());

		setZatwierdzEdycjeButton(new JButton("Zatwierd\u017A"));
		getZatwierdzEdycjeButton().setEnabled(false);
		getZatwierdzEdycjeButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(valid()){
					zatwierdzAction();
				}else{
					JOptionPane.showMessageDialog(null, "Wycena musi byc liczba");
				}
			}
		});

		addEdditButtonPanel.add(getZatwierdzEdycjeButton());
	}

	
	
	protected void edytujAction() {

		if (edytujButton.getText().equals("Anuluj")) {
			enableCarTextPanel(false);
			edytujButton.setText("Edytuj");
			zatwierdzEdycjeButton.setEnabled(false);
			getTabelaAut().setEnabled(true);
			usunButton.setEnabled(true);
			dodajButton.setEnabled(true);
		} else {
			enableCarTextPanel(true);
			edytujButton.setText("Anuluj");
			zatwierdzEdycjeButton.setEnabled(true);
			skojarzKlientaButton.setEnabled(false);
			dodajKlienta.setEnabled(false);
			usunButton.setEnabled(false);
			dodajButton.setEnabled(false);
			getTabelaAut().setEnabled(false);
		}

	}

	/**
	 * Tworzymy tabele aut oraz podpinay do niej model danych
	 */
	private void createTabelaAut() {

		setTabelaAut(new JTable());
		tabelaAutModel = new TabelaAutModel();
		tabelaAutModel.setSelectionListener(this);
		getTabelaAut().setModel(tabelaAutModel);

		//ukrywamy id z tabeli
		hideModelColumns(6);
		hideModelColumns(7);
		hideModelColumns(8);

		tabelaAutScrPane = new JScrollPane(getTabelaAut());
		tabelaAutScrPane.setViewportBorder(new TitledBorder(null, "Lista aut", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	}

	/**
	 * Panel z obiektami wizualnymy dla samochodu
	 */
	private void createCarTextPanel() {
		carTextPanel = new JPanel();
		carTextPanel.setBorder(new TitledBorder(null, "Samoch\u00F3d", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		markaField = new JTextField();
		markaLabel = new JLabel("Marka:");
		markaField.setColumns(10);

		JLabel rokLabel = new JLabel("Rok:");
		rokField = new JTextField("");
		// rokField.setEnabled(false);
		rokField.setColumns(4);

		JLabel nrSilnikaLabel = new JLabel("Nr silnika:");
		nrSilnikaField = new JTextField();
		// nrSilnikaField.setEnabled(false);
		nrSilnikaField.setColumns(8);

		JLabel nrPodwoziaJLabel = new JLabel("Nr podwozia:");
		nrPodwoziaField = new JTextField();
		// nrPodwoziaField.setEnabled(false);
		nrPodwoziaField.setColumns(8);
		// stan.setEnabled(false);
		JLabel stanJLabel = new JLabel("Stan:");
		carTextPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		idSamochod = new JTextField();
		carTextPanel.add(idSamochod);
		idSamochod.setColumns(2);
		idSamochod.setVisible(false);

		carTextPanel.add(markaLabel);
		carTextPanel.add(markaField);

		carTextPanel.add(rokLabel);
		carTextPanel.add(rokField);

		carTextPanel.add(nrSilnikaLabel);
		carTextPanel.add(nrSilnikaField);

		carTextPanel.add(nrPodwoziaJLabel);
		carTextPanel.add(nrPodwoziaField);

		carTextPanel.add(stanJLabel);
		comboBoxStan = new JComboBox();
		comboBoxStan.setModel(new DefaultComboBoxModel(new String[] { "Z\u0142y", "\u015Aredni", "Dobry" }));
		comboBoxStan.setEnabled(false);
		carTextPanel.add(comboBoxStan);

		JLabel wycenaLabel = new JLabel("Wycena naprawy");
		carTextPanel.add(wycenaLabel);

		setWycenaText(new JTextField());
		carTextPanel.add(getWycenaText());
		wycenaLabel.setEnabled(false);
		getWycenaText().setColumns(5);

		carTextPanel.setMinimumSize(new Dimension(200, 200));
		enableCarTextPanel(false);

		//równie¿ przycski klienta dodane tutaj
		skojarzKlientaButton = new JButton("Skojarz klienta");
		skojarzKlientaButton.setEnabled(false);
		skojarzKlientaButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				skojarzKlientaAction();
			}
		});

		carTextPanel.add(skojarzKlientaButton);

		dodajKlienta = new JButton("Dodaj nowego klienta");
		dodajKlienta.setEnabled(false);
		dodajKlienta.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dodajKlientaAction();
			}
		});
		carTextPanel.add(dodajKlienta);
	}

	private void enableCarTextPanel(boolean b) {
		Component[] components = carTextPanel.getComponents();
		for (Component component : components) {
			component.setEnabled(b);
		}

	}

	private void cleanCarTextPanel() {
		Component[] components = carTextPanel.getComponents();
		for (Component C : components) {
			if (C instanceof JTextField) {
				((JTextComponent) C).setText(""); // abstract superclass
			}
		}

	}

	private void cleanKlientTextPanel() {
		Component[] components = klientPane.getComponents();
		for (Component C : components) {
			if (C instanceof JTextField) {
				((JTextComponent) C).setText(""); // abstract superclass
			}
		}
	}

	
	/**
	 * Tworzymy panel klienta
	 */
	private void createKlientPanel() {

		klientPane = new JPanel();
		klientPane.setBorder(new TitledBorder(null, "Klient", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		klientPane.setToolTipText("Dane klienta");

		idKlient = new JTextField();
		klientPane.add(idKlient);
		idKlient.setColumns(2);
		idKlient.setVisible(false);

		JLabel imieLabel = new JLabel("Imie:");
		klientPane.add(imieLabel);

		imieField = new JTextField();
		imieField.setEnabled(false);
		klientPane.add(imieField);
		imieField.setColumns(10);

		JLabel lblNawisko = new JLabel("Nawisko:");
		klientPane.add(lblNawisko);

		nazwiskoField = new JTextField();
		nazwiskoField.setEnabled(false);
		klientPane.add(nazwiskoField);
		nazwiskoField.setColumns(10);

		JLabel peselLabel = new JLabel("PESEL:");
		klientPane.add(peselLabel);

		peselField = new JTextField();
		peselField.setEnabled(false);
		klientPane.add(peselField);
		peselField.setColumns(10);

		czyStalyKlient = new JCheckBox("Czy sta\u0142y klient");
		czyStalyKlient.setEnabled(false);
		klientPane.add(czyStalyKlient);
	}

	private void setTabGroupLayout() {
	}

	private void dodajAction() {
		if (dodajButton.getText().equals("Dodaj")) {
			enableCarTextPanel(true);
			cleanCarTextPanel();
			cleanKlientTextPanel();
			dodajButton.setText("Anuluj");
			tabelaAut.setEnabled(false);
			setUsunEdytuj(false);

		} else {
			enableCarTextPanel(false);
			dodajButton.setText("Dodaj");
			tabelaAut.setEnabled(true);
		}

	}

	private void zatwierdzAction() {

		Samochod samochod = pobierzSamochodZPol();

		//aktualizujemy badz dodoajmy nowy obiekt samochud, zalezne od ID
		if (samochod.getIdSamochod() != null) {
			samochod.update();
			Naprawa naprawa = Naprawa.getNaprawaSamochodu(samochod);
			naprawa.setCenaNaprawy(wycenaText.getText());
			naprawa.update();
			
		} else {
			Klient klient = new Klient();
			klient.setImie(imieField.getText());
			klient.setNazwisko(nazwiskoField.getText());
			klient.setPESEL(peselField.getText());
			klient.setCzyStalyKlient(czyStalyKlient.isSelected());
			if (!idKlient.getText().isEmpty()) {
				klient.setIdKlient(Integer.parseInt(idKlient.getText()));
			}else{
				klient.save();
			}
			
			samochod.setIdKlient(klient.getIdKlient());

			Naprawa naprawa = new Naprawa();
			naprawa.setIdPracownik(1);
			naprawa.setCenaNaprawy(getWycenaText().getText());
			naprawa.setZarejestWycenyTimestamp(new Timestamp(Calendar.getInstance().getTimeInMillis()));

			//Je¿eli sta³y klient to dajemy upust w postaci 27%
			if (klient.getCzyStalyKlient()) {
				Double cena = Double.parseDouble((naprawa.getCenaNaprawy()));
				cena = cena * 0.73;
				naprawa.setCenaNaprawy(cena.toString());
			}
			naprawa.saveNaprawa();
			samochod.setNaprawa(naprawa.getIdNaprawy());
			samochod.saveOrUpdateCar();

		}

		klientEnable(false);
		enableCarTextPanel(false);
		tabelaAutModel.refresCarTable();
		dodajButton.setEnabled(true);
		edytujButton.setEnabled(false);
		edytujButton.setText("Edytuj");
		cleanCarTextPanel();
		cleanKlientTextPanel();
		tabelaAut.setEnabled(true);
	}

	/**
	 * Zwraca obiekt auta z pol na formularzu
	 * @return
	 */
	private Samochod pobierzSamochodZPol() {

		Samochod samochod = new Samochod();
		if (!idSamochod.getText().isEmpty()) {
			samochod.setIdSamochod(Integer.parseInt(idSamochod.getText()));
		}
		samochod.setMarka(markaField.getText());
		samochod.setRok(rokField.getText());
		samochod.setNrPodwozia(nrPodwoziaField.getText());
		samochod.setNrSilnika(nrSilnikaField.getText());
		samochod.setStanAuta(comboBoxStan.getSelectedItem().toString());
		return samochod;
	}

	private void dodajKlientaAction() {

		if (dodajKlienta.getText().equals("Anuluj")) {
			klientEnable(false);
			cleanKlientTextPanel();
			dodajKlienta.setText("Dodaj nowego klienta");
		} else {
			klientEnable(true);
			dodajKlienta.setText("Anuluj");

		}
	}

	private void klientEnable(boolean b) {

		if (b) {
			dodajButton.setText("Anuluj");
			getZatwierdzEdycjeButton().setEnabled(true);
		} else {
			dodajButton.setText("Dodaj");
			getZatwierdzEdycjeButton().setEnabled(false);
		}
		imieField.setEnabled(b);
		nazwiskoField.setEnabled(b);
		peselField.setEnabled(b);
		czyStalyKlient.setEnabled(b);
	}

	public JTable getTabelaAut() {
		return tabelaAut;
	}

	public void setTabelaAut(JTable tabelaAut) {
		this.tabelaAut = tabelaAut;
	}

	public void setKlientFields(Klient k) {
		k.fetch();
		imieField.setText(k.getImie());
		nazwiskoField.setText(k.getNazwisko());
		peselField.setText(k.getPESEL());
		czyStalyKlient.setSelected(k.getCzyStalyKlient());
	}

	public void setSamochodField(Samochod s) {
		markaField.setText(s.getMarka());
		rokField.setText(s.getRok());
		nrPodwoziaField.setText(s.getNrPodwozia());
		nrSilnikaField.setText(s.getNrSilnika());
		comboBoxStan.setSelectedItem(s.getStanAuta());
		idSamochod.setText(s.getIdSamochod().toString());

		Klient klient = new Klient();
		klient.setIdKlient(s.getIdKlient());
		setKlientFields(klient);

		// idKlient.setText(s.getIdKlient().toString());

	}

	public JTextField getWycenaText() {
		return wycenaText;
	}

	public void setWycenaText(JTextField wycenaText) {
		this.wycenaText = wycenaText;
//		wycenaText.setDocument(new OnlyNumberValidatorr());
	}

	public void hideModelColumns(int arg) {
		tabelaAut.getColumnModel().getColumn(arg).setMinWidth(0);
		tabelaAut.getColumnModel().getColumn(arg).setMaxWidth(0);
		tabelaAut.getColumnModel().getColumn(arg).setPreferredWidth(0);
	}

	public JButton getZatwierdzEdycjeButton() {
		return zatwierdzEdycjeButton;
	}

	public void setZatwierdzEdycjeButton(JButton zatwierdzEdycjeButton) {
		this.zatwierdzEdycjeButton = zatwierdzEdycjeButton;
	}

	public JButton getEdytujButton() {
		return edytujButton;
	}

	public void setEdytujButton(JButton edytujButton) {
		this.edytujButton = edytujButton;
	}

	public JButton getUsunButton() {
		return usunButton;
	}

	public void setUsunButton(JButton usunButton) {
		this.usunButton = usunButton;
	}

	public void setUsunEdytuj(boolean arg) {
		getUsunButton().setEnabled(arg);
		getEdytujButton().setEnabled(arg);
	}

	public void usunAction() {

		int dialogResult = JOptionPane.showConfirmDialog(null, "Na pewno chcesz usun¹æ dany auto z listy?", "Ostrze¿enie", 0);
		if (dialogResult == JOptionPane.YES_OPTION) {
			Samochod s = new Samochod();
			s.setIdSamochod(Integer.parseInt(idSamochod.getText()));
			Samochod.usunSamochod(s);
			tabelaAutModel.refresCarTable();
		}

	}

	/**
	 * Wyswietlamy okno klientow
	 */
	private void skojarzKlientaAction() {
		SkojarzKlientaForm form = new SkojarzKlientaForm(MechFrame.this);
		form.setVisible(true);
		MechFrame.this.setEnabled(false);
	}
	
	private boolean valid(){
		if (wycenaText.getText().matches("[0-9]+")) {
			
			return true;
			
		}else{
			
			return false;
		}
	}

}
