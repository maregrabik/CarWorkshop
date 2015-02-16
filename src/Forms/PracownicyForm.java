package Forms;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
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
import javax.swing.text.JTextComponent;

import Entitity.Pracownik;
import Entitity.TypPracownika;
import Utility.PracownicyTableModel;

/**
 * Pracownicy form. Formularz w module administracyjnym u¿ywany do edycji pracowników.
 *
 */
public class PracownicyForm extends JFrame {

	private static final long serialVersionUID = 1L;
	private JScrollPane pracownicyScrollPane;
	private JPanel buttonPanel;
	private JPanel danePracownikowPanel;
	private JButton zatwierdzButton;
	private JButton edytujButton;
	private JButton usunButton;
	private JButton dodajButton;
	private JTextField imieTextField;
	private JTextField nazwiskoTextField;
	private JTextField peselTextField;
	@SuppressWarnings("rawtypes")
	private JComboBox typPracowniklaComboBox;
	private JTextField stawkaGodzinna;
	private JTextArea uwagi;
	private JTable pracownicyTable;
	private JTextField idPracownika;
	private PracownicyTableModel pracownicyTableModel;

	public PracownicyForm() {

		createLayout();

	}

	/**
	 * tworzymy obeikty wizualne
	 */
	private void createLayout() {
		GroupLayout groupLayout = setPanelPos();

		createPracownicyTable();

		createButtonPanel();

		createDanePracownikowPanel();

		getContentPane().setLayout(groupLayout);

		enableButtonPanel(false);
		enablePracownikPanell(false);
		dodajButton.setEnabled(true);
	}

	/**
	 * Tworzy panel pracowników Pracownicy
	 */
	private void createPracownicyTable() {
		setPracownicyTable(new JTable());
		pracownicyScrollPane.setViewportView(getPracownicyTable());
		pracownicyTableModel = new PracownicyTableModel();
		getPracownicyTable().setModel(pracownicyTableModel);
		pracownicyTableModel.setSelectionListener(this);
		hideModelColumns(4);
	}

	
	/**
	 * Group layput dla formularza
	 * @return
	 */
	private GroupLayout setPanelPos() {
		pracownicyScrollPane = new JScrollPane();
		pracownicyScrollPane.setViewportBorder(new TitledBorder(null, "Pracownicy", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		buttonPanel = new JPanel();
		danePracownikowPanel = new JPanel();
		danePracownikowPanel.setBorder(new TitledBorder(null, "Dane pracownika", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addContainerGap(72, Short.MAX_VALUE)
						.addGroup(
								groupLayout
										.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(buttonPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(pracownicyScrollPane, GroupLayout.DEFAULT_SIZE, 854, Short.MAX_VALUE)
										.addGroup(
												groupLayout
														.createSequentialGroup()
														.addComponent(danePracownikowPanel, GroupLayout.PREFERRED_SIZE, 660,
																GroupLayout.PREFERRED_SIZE).addGap(148))).addGap(46)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(
				groupLayout.createSequentialGroup().addGap(39)
						.addComponent(danePracownikowPanel, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
						.addComponent(buttonPanel, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE).addGap(18)
						.addComponent(pracownicyScrollPane, GroupLayout.PREFERRED_SIZE, 241, GroupLayout.PREFERRED_SIZE).addContainerGap()));
		return groupLayout;
	}

	
	/**
	 * Tworzymy dane pracowników 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void createDanePracownikowPanel() {
		danePracownikowPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		idPracownika = new JTextField();
		danePracownikowPanel.add(idPracownika);
		idPracownika.setColumns(1);
		idPracownika.setVisible(false);

		JLabel imieLabel = new JLabel("Imie:");
		danePracownikowPanel.add(imieLabel);

		imieTextField = new JTextField();
		danePracownikowPanel.add(imieTextField);
		imieTextField.setColumns(10);

		JLabel nazwiskoLabel = new JLabel("Nazwisko");
		danePracownikowPanel.add(nazwiskoLabel);

		nazwiskoTextField = new JTextField();
		danePracownikowPanel.add(nazwiskoTextField);
		nazwiskoTextField.setColumns(10);

		JLabel peselLabel = new JLabel("PESEL");
		danePracownikowPanel.add(peselLabel);

		peselTextField = new JTextField();
		danePracownikowPanel.add(peselTextField);
		peselTextField.setColumns(10);

		JLabel typPracownika = new JLabel("Typ pracownika");
		danePracownikowPanel.add(typPracownika);

		typPracowniklaComboBox = new JComboBox(TypPracownika.getAllTypes());
		danePracownikowPanel.add(typPracowniklaComboBox);

		JLabel stawkaLabel = new JLabel("Stawka godzinna");
		danePracownikowPanel.add(stawkaLabel);

		stawkaGodzinna = new JTextField();
		danePracownikowPanel.add(stawkaGodzinna);
		stawkaGodzinna.setColumns(5);
//		stawkaGodzinna.setDocument(new OnlyNumberValidatorr());

		JLabel lblUwagi = new JLabel("Uwagi");
		danePracownikowPanel.add(lblUwagi);

		uwagi = new JTextArea();
		uwagi.setColumns(15);
		uwagi.setRows(3);
		danePracownikowPanel.add(uwagi);
	}

	/**
	 * Panel przyciskoów i ich akcje
	 */
	private void createButtonPanel() {
		dodajButton = new JButton("Dodaj");
		buttonPanel.add(dodajButton);
		dodajButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dodajAction();
			}
		});

		usunButton = new JButton("Usu\u0144");
		usunButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				usunAction();
			}
		});
		buttonPanel.add(usunButton);

		edytujButton = new JButton("Edytuj");
		edytujButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				editAction();
			}
		});
		buttonPanel.add(edytujButton);

		zatwierdzButton = new JButton("Zatwierd\u017A");
		zatwierdzButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(valid()){
					zatwierdzAction();
				}else{
					JOptionPane.showMessageDialog(null, "Stawka musi byc liczba");
				}
			}
		});

		buttonPanel.add(zatwierdzButton);
	}

	protected void dodajAction() {
		if (dodajButton.getText().equals("Anuluj")) {
			cleanPracaTextPanel();
			dodajButton.setText("Dodaj");
			enablePracownikPanell(false);
			zatwierdzButton.setEnabled(false);
			pracownicyTable.setEnabled(true);
		} else {
			cleanPracaTextPanel();
			dodajButton.setText("Anuluj");
			enablePracownikPanell(true);
			zatwierdzButton.setEnabled(true);
			pracownicyTable.setEnabled(false);
			usunButton.setEnabled(false);
			edytujButton.setEnabled(false);
		}

	}

	protected void usunAction() {
		int dialogResult = JOptionPane.showConfirmDialog(null, "Na pewno chcesz usun¹æ danego pracownika?", "Ostrze¿enie", 0);
		if (dialogResult == JOptionPane.YES_OPTION) {
			Pracownik p = new Pracownik();
			p.setIdPracownik(Integer.parseInt(idPracownika.getText()));
			Pracownik.usunPracownika(p);
			pracownicyTableModel.refreshPracownikTable();
		}

	}

	protected void editAction() {

		if (edytujButton.getText().equals("Anuluj")) {
			edytujButton.setText("Edytuj");
			enablePracownikPanell(false);
			zatwierdzButton.setEnabled(false);
			pracownicyTable.setEnabled(true);
			dodajButton.setEnabled(true);
			usunButton.setEnabled(true);

		} else {
			edytujButton.setText("Anuluj");
			enablePracownikPanell(true);
			zatwierdzButton.setEnabled(true);
			dodajButton.setEnabled(false);
			usunButton.setEnabled(false);
			pracownicyTable.setEnabled(false);
		}

	}

	/**
	 * Dodajemy pracownika
	 */
	protected void zatwierdzAction() {
		Pracownik pracownik = getPracownikFromFields();

		if (pracownik.getIdPracownik() != null) {
			pracownik.update();
		} else {
			pracownik.savePracownik();
		}

		zatwierdzButton.setEnabled(false);
		pracownicyTableModel.refreshPracownikTable();
		pracownicyTable.setEnabled(true);
		edytujButton.setText("Edytuj");
		dodajButton.setEnabled(true);
		dodajButton.setText("Dodaj");
		usunButton.setEnabled(true);
		cleanPracaTextPanel();
		enablePracownikPanell(false);
	}

	/**
	 * Zwraca dane pracownika z aktualnie uzupelnionych pól 
	 * @return
	 */
	private Pracownik getPracownikFromFields() {
		Pracownik pracownik = new Pracownik();
		if (!idPracownika.getText().isEmpty()) {
			pracownik.setIdPracownik(Integer.parseInt(idPracownika.getText()));
		}
		pracownik.setImie(imieTextField.getText());
		pracownik.setNazwisko(nazwiskoTextField.getText());
		pracownik.setPESEL(peselTextField.getText());
		pracownik.setTypPracownika(((TypPracownika) typPracowniklaComboBox.getSelectedItem()).getIdTypu());
		pracownik.setUwagi(uwagi.getText());
		pracownik.setStawkaGodzinna(Integer.parseInt(stawkaGodzinna.getText()));
		return pracownik;
	}

	public void enablePracownikPanell(boolean b) {
		Component[] components = danePracownikowPanel.getComponents();
		for (Component component : components) {
			component.setEnabled(b);
		}
	}

	public void enableButtonPanel(boolean b) {
		Component[] components = buttonPanel.getComponents();
		for (Component component : components) {
			component.setEnabled(b);
		}

	}

	public void hideModelColumns(int arg) {
		getPracownicyTable().getColumnModel().getColumn(arg).setMinWidth(0);
		getPracownicyTable().getColumnModel().getColumn(arg).setMaxWidth(0);
		getPracownicyTable().getColumnModel().getColumn(arg).setPreferredWidth(0);
	}

	public JTable getPracownicyTable() {
		return pracownicyTable;
	}

	public void setPracownicyTable(JTable pracownicyTable) {
		this.pracownicyTable = pracownicyTable;
	}

	/**
	 * @return the pracownicyScrollPane
	 */
	public JScrollPane getPracownicyScrollPane() {
		return pracownicyScrollPane;
	}

	/**
	 * @param pracownicyScrollPane
	 *            the pracownicyScrollPane to set
	 */
	public void setPracownicyScrollPane(JScrollPane pracownicyScrollPane) {
		this.pracownicyScrollPane = pracownicyScrollPane;
	}

	/**
	 * @return the buttonPanel
	 */
	public JPanel getButtonPanel() {
		return buttonPanel;
	}

	/**
	 * @param buttonPanel
	 *            the buttonPanel to set
	 */
	public void setButtonPanel(JPanel buttonPanel) {
		this.buttonPanel = buttonPanel;
	}

	/**
	 * @return the danePracownikowPanel
	 */
	public JPanel getDanePracownikowPanel() {
		return danePracownikowPanel;
	}

	/**
	 * @param danePracownikowPanel
	 *            the danePracownikowPanel to set
	 */
	public void setDanePracownikowPanel(JPanel danePracownikowPanel) {
		this.danePracownikowPanel = danePracownikowPanel;
	}

	/**
	 * @return the zatwierdzButton
	 */
	public JButton getZatwierdzButton() {
		return zatwierdzButton;
	}

	/**
	 * @param zatwierdzButton
	 *            the zatwierdzButton to set
	 */
	public void setZatwierdzButton(JButton zatwierdzButton) {
		this.zatwierdzButton = zatwierdzButton;
	}

	/**
	 * @return the edytujButton
	 */
	public JButton getEdytujButton() {
		return edytujButton;
	}

	/**
	 * @param edytujButton
	 *            the edytujButton to set
	 */
	public void setEdytujButton(JButton edytujButton) {
		this.edytujButton = edytujButton;
	}

	/**
	 * @return the usunButton
	 */
	public JButton getUsunButton() {
		return usunButton;
	}

	/**
	 * @param usunButton
	 *            the usunButton to set
	 */
	public void setUsunButton(JButton usunButton) {
		this.usunButton = usunButton;
	}

	/**
	 * @return the dodajButton
	 */
	public JButton getDodajButton() {
		return dodajButton;
	}

	/**
	 * @param dodajButton
	 *            the dodajButton to set
	 */
	public void setDodajButton(JButton dodajButton) {
		this.dodajButton = dodajButton;
	}

	/**
	 * @return the imieTextField
	 */
	public JTextField getImieTextField() {
		return imieTextField;
	}

	/**
	 * @param imieTextField
	 *            the imieTextField to set
	 */
	public void setImieTextField(JTextField imieTextField) {
		this.imieTextField = imieTextField;
	}

	/**
	 * @return the nazwiskoTextField
	 */
	public JTextField getNazwiskoTextField() {
		return nazwiskoTextField;
	}

	/**
	 * @param nazwiskoTextField
	 *            the nazwiskoTextField to set
	 */
	public void setNazwiskoTextField(JTextField nazwiskoTextField) {
		this.nazwiskoTextField = nazwiskoTextField;
	}

	/**
	 * @return the peselTextField
	 */
	public JTextField getPeselTextField() {
		return peselTextField;
	}

	/**
	 * @param peselTextField
	 *            the peselTextField to set
	 */
	public void setPeselTextField(JTextField peselTextField) {
		this.peselTextField = peselTextField;
	}

	/**
	 * @return the typPracowniklaComboBox
	 */
	public JComboBox getTypPracowniklaComboBox() {
		return typPracowniklaComboBox;
	}

	/**
	 * @param typPracowniklaComboBox
	 *            the typPracowniklaComboBox to set
	 */
	public void setTypPracowniklaComboBox(JComboBox typPracowniklaComboBox) {
		this.typPracowniklaComboBox = typPracowniklaComboBox;
	}

	/**
	 * @return the stawkaGodzinna
	 */
	public JTextField getStawkaGodzinna() {
		return stawkaGodzinna;
	}

	/**
	 * @param stawkaGodzinna
	 *            the stawkaGodzinna to set
	 */
	public void setStawkaGodzinna(JTextField stawkaGodzinna) {
		this.stawkaGodzinna = stawkaGodzinna;
	}

	/**
	 * @return the uwagi
	 */
	public JTextArea getUwagi() {
		return uwagi;
	}

	/**
	 * @param uwagi
	 *            the uwagi to set
	 */
	public void setUwagi(JTextArea uwagi) {
		this.uwagi = uwagi;
	}

	/**
	 * @return the idPracownika
	 */
	public JTextField getIdPracownika() {
		return idPracownika;
	}

	/**
	 * @param idPracownika
	 *            the idPracownika to set
	 */
	public void setIdPracownika(JTextField idPracownika) {
		this.idPracownika = idPracownika;
	}

	/**
	 * @return the pracownicyTableModel
	 */
	public PracownicyTableModel getPracownicyTableModel() {
		return pracownicyTableModel;
	}

	/**
	 * @param pracownicyTableModel
	 *            the pracownicyTableModel to set
	 */
	public void setPracownicyTableModel(PracownicyTableModel pracownicyTableModel) {
		this.pracownicyTableModel = pracownicyTableModel;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * Ustalamy pola na formularzu zgodnie z obiektem
	 * @param pracownik
	 */
	public void setpracownikField(Pracownik pracownik) {
		idPracownika.setText(pracownik.getIdPracownik().toString());
		imieTextField.setText(pracownik.getImie());
		nazwiskoTextField.setText(pracownik.getNazwisko());
		peselTextField.setText(pracownik.getPESEL());
		TypPracownika typPracownika = new TypPracownika();
		typPracownika.setIdTypu(pracownik.getTypPracownika());
		typPracownika.fetch();
		typPracowniklaComboBox.setSelectedItem(typPracownika);
		stawkaGodzinna.setText(pracownik.getStawkaGodzinna().toString());
		uwagi.setText(pracownik.getUwagi());
	}

	public void setUsunEdytuj(boolean arg) {
		getUsunButton().setEnabled(arg);
		getEdytujButton().setEnabled(arg);
	}

	private void cleanPracaTextPanel() {
		Component[] components = danePracownikowPanel.getComponents();
		for (Component C : components) {
			if (C instanceof JTextField) {
				((JTextComponent) C).setText(""); // abstract superclass
			}
		}
		uwagi.setText("");

	}
	
	private boolean valid(){
		if (stawkaGodzinna.getText().matches("[0-9]+")) {
			return true;
		}else{
			return false;
		}
	}

}
