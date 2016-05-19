package chat;

import java.util.LinkedList;

class Response {
	private LinkedList<String> listOfContext;
	private LinkedList<String> listOfResp;

	public Response() {
		listOfContext = new LinkedList<String>();
		listOfResp = new LinkedList<String>();
	}

	public void addContext(String context) {
		listOfContext.push(context);
	}

	public void addResp(String resp) {
		listOfResp.push(resp);
	}

	public LinkedList<String> getContextList() {
		return listOfContext;
	}

	public LinkedList<String> getRespList() {
		return listOfResp;
	}

	public void clear() {
		listOfResp.clear();
		listOfContext.clear();
	}

	public boolean isContaining() {
		return listOfResp.size() > 0;
	}
}
