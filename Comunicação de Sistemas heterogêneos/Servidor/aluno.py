class Aluno:
    def setId(self, id):
        self.__id = id

    def setNome(self, nome):
        self.__nome = nome

    def setMatriculado(self, matriculado):
        self.__matriculado = matriculado

    def getId(self):
        return self.__id

    def getNome(self):
        return self.__nome

    def isMatriculado(self):
        return self.__matriculado
