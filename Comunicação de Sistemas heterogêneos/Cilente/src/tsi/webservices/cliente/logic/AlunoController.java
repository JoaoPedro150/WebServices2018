package tsi.webservices.cliente.logic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import tsi.webservices.cliente.gui.ApplicationWindow;
import tsi.webservices.cliente.model.Aluno;

class AlunoController {
	private ApplicationWindow applicationWindow;
	private ArrayList<DefaultTableModel> alunosTablesModel = new ArrayList<>();
	private int numAlunos;
	
	public AlunoController(ApplicationWindow applicationWindow_) {
		applicationWindow = applicationWindow_;
	}
	
	public void onActionNovaTurma() {
		alunosTablesModel.add(new DefaultTableModel(new String[] {"ID","Nome","Matriculado"}, 0));
	}
	
	public void onActionNovoAluno(int selectedTurma) {
		alunosTablesModel.get(selectedTurma).addRow(
				new Object[] { String.format("%04d", numAlunos), String.format("Teste%d", numAlunos++),
						new Random(Calendar.getInstance().get(Calendar.MILLISECOND)).nextInt() % 2 == 0 ? "Sim" : "Nao"});
	}

	public void changeDataTable(int selectedRow) {
		applicationWindow.getAlunosTable().setModel(alunosTablesModel.get(selectedRow));
	}

	@SuppressWarnings("unchecked")
	public List<List<Aluno>> getAlunosPorTurma() {
		ArrayList<List<Aluno>> alunosPorTurma = new ArrayList<>();
		ArrayList<Aluno> alunos;
		Aluno aluno;
		
		for (DefaultTableModel defaultTableModel : alunosTablesModel) {
			alunos = new ArrayList<>();
			
			for (Iterator<Vector<String>> iterator = defaultTableModel.getDataVector().iterator(); iterator.hasNext();) {
				Vector<String> dados = iterator.next();
				
				aluno = new Aluno();
				aluno.setId(Long.parseLong(dados.get(0)));
				aluno.setNome(dados.get(1));
				aluno.setMatriculado(dados.get(2).equals("Sim") ? true : false);
				
				alunos.add(aluno);
			}
			
			alunosPorTurma.add(alunos);
		}
		
		return alunosPorTurma;
	}
}
