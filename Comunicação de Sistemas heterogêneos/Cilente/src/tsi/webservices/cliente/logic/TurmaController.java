package tsi.webservices.cliente.logic;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import tsi.webservices.cliente.gui.ApplicationWindow;
import tsi.webservices.cliente.model.Turma;

class TurmaController {
	private ApplicationWindow applicationWindow;
	
	public TurmaController(ApplicationWindow applicationWindow_) {
		applicationWindow = applicationWindow_;
	}
	
	public void onActionNovaTurma() {
		DefaultTableModel turmasTableModel = (DefaultTableModel)applicationWindow.getTurmasTable().getModel();
		
		int nextTurma = turmasTableModel.getRowCount();
		
		turmasTableModel.addRow(new Object[] { String.format("%04d", nextTurma), 
				String.format("2%03d", nextTurma),String.format("TSI-2%03d", nextTurma)});
	}
	
	@SuppressWarnings("unchecked")
	public List<Turma> getTurmas() {
			ArrayList<Turma> turmas = new ArrayList<>();
			Turma turma;
			
			DefaultTableModel turmasTableModel = (DefaultTableModel)applicationWindow.getTurmasTable().getModel();
			
			for (Iterator<Vector<String>> iterator = turmasTableModel.getDataVector().iterator(); iterator.hasNext();) {
				Vector<String> dados = iterator.next();
				
				turma = new Turma();
				turma.setId(Long.parseLong(dados.get(0)));
				turma.setAno(Integer.parseInt(dados.get(1)));
				turma.setCurso(dados.get(2));
				
				turmas.add(turma);
			}

			return turmas;
	}
}

