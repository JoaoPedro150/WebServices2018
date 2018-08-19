package tsi.webservices.cliente.gui;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import tsi.webservices.cliente.logic.AplicationController;

public class ApplicationWindow{
	private JFrame mainFrame;
	private JTable turmasTable;
	private JTable alunosTable;
	private JButton buttonNovaTurma;
	private JButton buttonNovoAluno;
	private JButton buttonEnviar;
	private Dialog dialog;

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		new AplicationController(new ApplicationWindow("Clinte"));
	}
	
	public ApplicationWindow(String title) {
		mainFrame = new JFrame(title);
		turmasTable = new JTable(new DefaultTableModel(new String[] {"ID","Ano","Curso"}, 0));
		alunosTable = new JTable(new DefaultTableModel(new String[] {"ID","Nome","Matriculado"}, 0));
		buttonNovaTurma = new JButton("Nova Turma");
		buttonNovoAluno = new JButton("Nova Aluno");
		buttonEnviar = new JButton("Enviar");
		dialog = new Dialog(mainFrame);
		
		initialize();
	}
	
	private void initialize() {
		turmasTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		alunosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scrollPaneTurmas = new JScrollPane(turmasTable);
		JScrollPane scrollPaneAlunos = new JScrollPane(alunosTable);
		
		scrollPaneAlunos.setPreferredSize(new Dimension(300, 500));
		scrollPaneTurmas.setPreferredSize(new Dimension(300, 500));
		
		JPanel panelTableTurmas = new JPanel(new BorderLayout());
		
		panelTableTurmas.add(scrollPaneTurmas, BorderLayout.CENTER);
		panelTableTurmas.add(new JLabel("Turmas"), BorderLayout.NORTH);
		
		JPanel panelTableAlunos = new JPanel(new BorderLayout());
		
		panelTableAlunos.add(scrollPaneAlunos, BorderLayout.CENTER);
		panelTableAlunos.add(new JLabel("Alunos"), BorderLayout.NORTH);
		
		JPanel panelTables = new JPanel();
		
		panelTables.add(panelTableAlunos);
		panelTables.add(panelTableTurmas);
		
		JPanel panelButtons = new JPanel();
		
		panelButtons.add(buttonNovaTurma);
		panelButtons.add(buttonNovoAluno);
		panelButtons.add(buttonEnviar);
		
		JPanel panel = new JPanel(new BorderLayout(5,5));
		
		panel.add(panelTables, BorderLayout.CENTER);
		panel.add(panelButtons, BorderLayout.SOUTH);
	
		mainFrame.setContentPane(panel);
		mainFrame.setSize(650,600);
		mainFrame.setResizable(false);
		mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
	}
	
	public JTable getTurmasTable() {
		return turmasTable;
	}

	public JTable getAlunosTable() {
		return alunosTable;
	}

	public JButton getButtonNovaTurma() {
		return buttonNovaTurma;
	}

	public JButton getButtonNovoAluno() {
		return buttonNovoAluno;
	}

	public JButton getButtonEnviar() {
		return buttonEnviar;
	}

	public JTextField getDialogTextFieldHost() {
		return dialog.textFieldHost;
	}

	public JTextField getDialogTextFieldPort() {
		return dialog.textFieldPort;
	}

	public JButton getDialogButtonConfirmar() {
		return dialog.buttonConfirmar;
	}
	
	public void showDialog() {
		dialog.jdialog.setBounds(mainFrame.getX() + mainFrame.getWidth()/2 - 124, 
				mainFrame.getY() + mainFrame.getHeight()/2 - 63, 234, 155);
		
		dialog.jdialog.setVisible(true);
	}
	
	public void closeDialog() {
		dialog.jdialog.setVisible(false);
	}
	
	class Dialog {
		JDialog jdialog;
		JTextField textFieldHost;
		JTextField textFieldPort;
		JButton buttonConfirmar;

		public Dialog(JFrame owner) {
			jdialog = new JDialog(owner.getOwner(),"Endereco do servidor", ModalityType.APPLICATION_MODAL);
			initialize();
		}

		private void initialize() {
			jdialog.getContentPane().setLayout(new BorderLayout(0, 0));
			
			JPanel panel_1 = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
			flowLayout.setVgap(30);
			panel_1.setBorder(null);
			jdialog.getContentPane().add(panel_1, BorderLayout.CENTER);
			
			JPanel panel = new JPanel();
			panel_1.add(panel);
			panel.setBorder(null);
			panel.setLayout(new GridLayout(2, 1, 0, 14));
			
			JLabel lblHost = new JLabel("HOST: ");
			panel.add(lblHost);
			
			JLabel lblPort = new JLabel("PORT:");
			panel.add(lblPort);
			
			JPanel panel_2 = new JPanel();
			panel_1.add(panel_2);
			panel_2.setBorder(null);
			panel_2.setLayout(new GridLayout(2, 1, 0, 10));
			
			textFieldHost = new JTextField();
			textFieldHost.setText("127.0.0.1");
			panel_2.add(textFieldHost);
			textFieldHost.setColumns(10);
			
			textFieldPort = new JTextField();
			textFieldPort.setText("12345");
			panel_2.add(textFieldPort);
			textFieldPort.setColumns(10);
			
			JPanel panel_3 = new JPanel();
			jdialog.getContentPane().add(panel_3, BorderLayout.SOUTH);
			
			buttonConfirmar = new JButton("Confirmar");
			panel_3.add(buttonConfirmar);
			
			jdialog.setResizable(false);
		}
	}
}
