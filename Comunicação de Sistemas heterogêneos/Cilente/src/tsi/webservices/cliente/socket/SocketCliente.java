package tsi.webservices.cliente.socket;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import tsi.webservices.cliente.model.Aluno;
import tsi.webservices.cliente.model.Turma;

public class SocketCliente implements AutoCloseable{
	private DataOutputStream output;
	private BufferedReader input;
	private Socket socket;
	
	public SocketCliente(String host, int port) throws UnknownHostException, IOException {
		socket = new Socket(host, port);
		
		output = new DataOutputStream(socket.getOutputStream());
		input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	public void sendTurmas(Turma...turmas) throws IOException {
		if (turmas != null) 
			for (Turma turma : turmas) {
				output.writeUTF("1");
	        	output.flush();
	        	waitResponse();
				
				output.writeUTF(String.format("%d[:-X-:/]%d[:-X-:/]%s", turma.getId(), turma.getAno(), turma.getCurso()));
	        	output.flush();
	        	waitResponse();
	        	
				for (Aluno aluno : turma.getAlunos())  {
					output.writeUTF(String.format("%d[:-X-:/]%s[:-X-:/]%d", aluno.getId(), aluno.getNome(), (aluno.isMatriculado() ? 1 : 0)));
		        	output.flush();
		        	waitResponse();
				}
			}
		
		output.writeUTF("0");
    	output.flush();
	}

	private void waitResponse() throws IOException {
		input.readLine();
	}
	
	@Override
	public void close() throws Exception {
		socket.close();
		output.close();
		input.close();
	}
}
