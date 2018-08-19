#coding: utf-8

import socket

from turma import Turma
from aluno import Aluno

class ServerSocket:
	def __init__(self, host, port):
		self.__socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
		self.__socket.bind((host, port))
		self.__socket.listen(5)

	def recv_turma(self):
	    turmas = []

	    try:
	        try:
	            conexao = None
	            conexao, cliente = self.__socket.accept()

	            while(True):
	                msg = conexao.recv(1024).decode()[2:]

	                if (msg == '0'): break;

	                elif (msg == '1'):
	                    conexao.send('1\n'.encode())
	                    turmas.append(self.__newTurma(conexao.recv(1024).decode()[2:]))
	                elif (len(turmas) >= 1):
	                    turmas[-1].addAluno(self.__newAluno(msg))
	                conexao.send('1\n'.encode())
	            return (turmas, cliente)
	        finally:
	            if conexao:
	                conexao.close()
	    except socket.error:
	        pass

	def __newTurma(self, dados_turma):
	    dados = dados_turma.split('[:-X-:/]')

	    turma = Turma()
	    turma.setId(dados[0])
	    turma.setAno(dados[1])
	    turma.setCurso(dados[2])

	    return turma

	def __newAluno(self, dados_aluno):
	    dados = dados_aluno.split('[:-X-:/]')

	    aluno = Aluno()
	    aluno.setId(dados[0])
	    aluno.setNome(dados[1])
	    aluno.setMatriculado(True if dados[2] == '1' else False)

	    return aluno

	def close(self):
		self.__socket.close()
