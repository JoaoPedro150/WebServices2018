package tsi.webservices.cliente.model;

import java.util.ArrayList;
import java.util.List;

public class Turma {
	private Long id;
	private Integer ano;
	private String curso;
	private ArrayList<Aluno> alunos;
	
	public Turma() {
		alunos = new ArrayList<>();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getAno() {
		return ano;
	}
	public void setAno(Integer ano) {
		this.ano = ano;
	}
	public String getCurso() {
		return curso;
	}
	public void setCurso(String curso) {
		this.curso = curso;
	}
	public Aluno[] getAlunos() {
		return alunos.toArray(new Aluno[0]);
	}
	public void setAlunos(Aluno[] alunos) {
		for (Aluno aluno : alunos) {
			this.alunos.add(aluno);
		}
	}
	public void setAlunos(List<Aluno> alunos) {
		if (alunos instanceof ArrayList<?>)
			this.alunos = (ArrayList<Aluno>) alunos;
		else
			setAlunos(alunos.toArray(new Aluno[0]));
	}
	public void addAluno(Aluno aluno) {
		alunos.add(aluno);
	}
}
