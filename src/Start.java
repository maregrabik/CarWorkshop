import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import Forms.AdminFrame;
import Forms.MechFrame;


/**
 * Klasa startuj¹ca aplikacje
 */
public class Start extends JFrame  implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JButton mechkButton;
	private JButton admButton;


	public static void main(String[] args) {
    new Start();
	}

	
	public Start(){
	setVisible(true);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setSize(200, 110);
	setLocationRelativeTo(null);
	FlowLayout flowLayout = new FlowLayout();
	this.setLayout(flowLayout);
	
	mechkButton = new JButton("Modu³ mechanika");
	mechkButton.addActionListener(this);
	admButton = new JButton("Modu³ Administratora");
	admButton.addActionListener(this);
	add(mechkButton);
	add(admButton);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(mechkButton)){
			this.setVisible(false);
			MechFrame  mechFrame = new MechFrame();
		} else
		{	
			this.setVisible(false);
			AdminFrame adminFrame = new AdminFrame();
		}
	}
	
	
}
