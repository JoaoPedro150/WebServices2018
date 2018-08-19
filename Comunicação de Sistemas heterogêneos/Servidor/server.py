#coding: utf-8

import socket

from turma import Turma
from aluno import Aluno
from serversocket import ServerSocket
from threading import Thread

PORT = 12345

def main():
    port = input('PORT[' + str(PORT) + ']: ')

    if (port == ''):
        port = PORT
    else:
        try:
            port = int(port)
        except ValueError:
            print('\nPorta invalida')
            return -1

    serverSocket = ServerSocket('', port)
    print('\nEsperando por conexões na porta ' + str(port) + '...')
    print('Digite "stop" a qualquer momento para finalizar.')

    Thread(target=start, args=(serverSocket,)).start()

    flag = ''

    while(flag != 'stop'):
        flag = input()

    serverSocket.close()

    return 0

def start(serverSocket):
    while(True):
        tuple = serverSocket.recv_turma()
        if not tuple: break
        Thread(target=newConnection, args=(tuple)).start()

def newConnection(*tuple):
    turmas = tuple[0]
    cliente = tuple[1]

    if(len(turmas) > 0):
        msg = '\n\n\nDados recebidos de ' + cliente[0] + ' na porta ' + str(cliente[1]) + '\n\n'

        qtd_alunos_matriculados = 0

        for turma in turmas:
            for aluno in turma.getAlunos():
                if (aluno.isMatriculado()):
                    qtd_alunos_matriculados += 1
            msg += 'A turma ' + str(turma.getId()) + ' de ' + turma.getAno() + ' do curso ' + turma.getCurso()
            msg += ' possui ' + str(len(turma.getAlunos())) + ' alunos,\ndos quais '
            msg += str(qtd_alunos_matriculados) + ' estão devidamente matriculados.\n'
            qtd_alunos_matriculados = 0

        print (msg)

if __name__ == '__main__':
    import sys
    sys.exit(main())
