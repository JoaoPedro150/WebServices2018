
from aluno import Aluno

class Turma:
    def setId(self, id):
        self.__id = id
        self.__alunos = []

    def setAno(self, ano):
        self.__ano = ano

    def setCurso(self, curso):
        self.__curso = curso

    def setAlunos(self, alunos):
        self.__alunos = alunos

    def getId(self):
        return self.__id

    def getAno(self):
        return self.__ano

    def getCurso(self):
        return self.__curso

    def getAlunos(self):
        return self.__alunos

    def addAluno(self, aluno):
        self.__alunos.append(aluno)
