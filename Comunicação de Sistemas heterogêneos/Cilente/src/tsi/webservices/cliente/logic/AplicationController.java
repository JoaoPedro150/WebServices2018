package tsi.webservices.cliente.logic;

import java.net.UnknownHostException;
import java.util.List;

import javax.swing.JOptionPane;

import tsi.webservices.cliente.gui.ApplicationWindow;
import tsi.webservices.cliente.model.Aluno;
import tsi.webservices.cliente.model.Turma;
import tsi.webservices.cliente.socket.SocketCliente;

public class AplicationController {
	private ApplicationWindow applicationWindow;
	
	private AlunoController alunoController;
	private TurmaController turmaController;
	
	public AplicationController(ApplicationWindow applicationWindow_) {
		applicationWindow = applicationWindow_;
		
		turmaController = new TurmaController(applicationWindow_);
		alunoController = new AlunoController(applicationWindow_);
		
		initialize();
	}

	private void initialize() {
		applicationWindow.getButtonNovaTurma().addActionListener((action) -> onActionNovaTurma());
		applicationWindow.getButtonNovoAluno().addActionListener((action) -> onActionNovoAluno());
		applicationWindow.getTurmasTable().getSelectionModel().addListSelectionListener((action) -> onRowSelectedAction());
		applicationWindow.getButtonEnviar().addActionListener((action) -> onActionEnviar());
		applicationWindow.getDialogButtonConfirmar().addActionListener((action) -> enviarDados());
	}
	
	private void enviarDados() {
		try (SocketCliente socketCliente = new SocketCliente(applicationWindow.getDialogTextFieldHost().getText(), 
				Integer.parseInt(applicationWindow.getDialogTextFieldPort().getText()))) {
			
			Turma turmas[] = obterTurmas();
			
			if (turmas.length > 0)
				socketCliente.sendTurmas(obterTurmas());
			else
				JOptionPane.showMessageDialog(applicationWindow.getTurmasTable().getRootPane(), 
						"Não há dados a serem enviados", "Atenção", JOptionPane.WARNING_MESSAGE);
		}
		catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(applicationWindow.getTurmasTable().getRootPane(), 
					"Porta inv�lida.", "Erro", JOptionPane.ERROR_MESSAGE);
		}
		catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(applicationWindow.getTurmasTable().getRootPane(), 
					"Host inv�lido.", "Erro", JOptionPane.ERROR_MESSAGE);
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(applicationWindow.getTurmasTable().getRootPane(), e.getMessage(),
					"Erro", JOptionPane.ERROR_MESSAGE);
		}
		
		applicationWindow.closeDialog();
	}

	private Turma[] obterTurmas() {
		Turma turmas[] = turmaController.getTurmas().toArray(new Turma[0]);
		int indiceTurma = 0;
		List<List<Aluno>> alunosPorTurma = alunoController.getAlunosPorTurma();
		
		for (List<Aluno> alunos : alunosPorTurma) {
			turmas[indiceTurma++].setAlunos(alunos);
		}
		
		return turmas;
	}

	private void onActionEnviar() {
		applicationWindow.showDialog();
	}

	private void onRowSelectedAction() {
		alunoController.changeDataTable(applicationWindow.getTurmasTable().getSelectedRow());
	}

	private void onActionNovaTurma() {
		alunoController.onActionNovaTurma();
		turmaController.onActionNovaTurma();
	}
	
	private void onActionNovoAluno() {
		int selectedTurma = applicationWindow.getTurmasTable().getSelectedRow();
		
		if (selectedTurma == -1) {
			JOptionPane.showMessageDialog(applicationWindow.getTurmasTable().getRootPane(), "Selecione a turma primeiro", "Erro", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		alunoController.onActionNovoAluno(selectedTurma);
	}
}
