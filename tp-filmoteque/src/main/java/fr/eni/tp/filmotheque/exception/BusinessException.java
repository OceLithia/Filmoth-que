package fr.eni.tp.filmotheque.exception;

import java.util.ArrayList;
import java.util.List;

public class BusinessException extends Exception {

	private List<String> listeMessage;

	public BusinessException() {
		this.listeMessage = new ArrayList<String>();
	}
	
	public void addMessage(String message) {
		this.listeMessage.add(message);
	}

	public List<String> getListeMessage() {
		return listeMessage;
	}
}
