package chat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Adi {
	private final String logFileName = "log.log";
	private final String dataFileName = "script.txt";

	private String name;
	private String userName;
	private String input;
	private String prevInput;
	private String response;
	private String prevResponse;
	private String event;
	private String prevEvent;
	private String inputBackup;
	private String subject;
	private String keyWord;
	private String context;
	private String prevContext;

	private boolean quitProgram;

	private LinkedList<String> listOfResponse;
	private LinkedList<String> listOfUnknownInput;
	private LinkedList<String> responseLog;

	private HashMap<String, LinkedList<Response>> knowledgeBase;

	private FileWriter logFileWriter;

	private Random random;
	private String[][] transposeList;

	private Scanner inputScanner;
	private PrintStream outputStream;

	public Adi(String name, InputStream is, PrintStream os) throws IOException, FileNotFoundException {
		this.name = name;
		this.input = "null";
		this.quitProgram = false;
		userName = "";
		prevInput = "";
		response = "";
		prevResponse = "";
		event = "";
		prevEvent = "";
		inputBackup = "";
		subject = "";
		keyWord = "";
		context = "";
		prevContext = "";


		random = new Random();
		listOfResponse = new LinkedList<String> ();
		listOfUnknownInput = new LinkedList<String> ();
		responseLog = new LinkedList<String> ();

		knowledgeBase = new HashMap<String, LinkedList<Response>>();

		logFileWriter = new FileWriter(logFileName, true);
		transposeList = new String[][]{
			{" myself ", " yourself "},
			{" dreams ", " dream "},
			{" weren't ", " wasn't "},	
			{" aren't ", " am not "},
			{" i've ", " you've "},
			{" mine ", " yours "},
			{" my ", " your "},
			{" were ", " was "},
			{" mom ", " mother "},
			{" i am ", " you are "},
			{" i'm ", " you're "},
			{" dad ", " father "},
			{" my ", " your "},
			{" am ", " are "},
			{" i'd ", " you'd "},
			{" i ", " you "},
			{" me ", " you "}
		};

		inputScanner = new Scanner(is);
		outputStream = os;

		loadDatabase();
	}

	public Adi(String name) throws IOException, FileNotFoundException {
		this(name, System.in, System.out);
	}

	public void getInput() {
		System.out.print(">");

		savePrevInput();
		input = inputScanner.nextLine();
	}

	public void respond() {
		savePrevResponse();
		setEvent("bot understand**");

		if (nullInput()) {
			handleEvent("null input**");
		} else if (nullInputRepetition()) {
			handleEvent("null input repetition**");
		} else if (userRepeat()) {
			handleUserRepetition();
		} else {
			findMatch();
		}

		if (userWantToQuit()) {
			quitProgram = true;
		}

		if (!botUnderstand()) {
			handleEvent("bot don't understand**");
			updateUnknownInputList();
		}

		if (listOfResponse.size() > 0) {
			selectResponse();
			saveBotResponse();
			preprocessResponse();

			if (botRepeat()) {
				handleRepetition();
			}

			saveLog("chatterbot");
			printResponse();
		}
	}

	public boolean quit() {
		return quitProgram;
	}

	public void findMatch() {
		listOfResponse = new LinkedList<String>();

		String bestKeyWord;
		LinkedList<String> listOfWord = new LinkedList<String>();

		if (input.indexOf("**") == -1) {
			input = input.replaceFirst("\\.+$", "");
			input = input.toLowerCase();
			StringTokenizer st = new StringTokenizer(input, "?!;, \n\t\r\f.");
			while (st.hasMoreTokens()) {
				listOfWord.add(st.nextToken());
			}
			bestKeyWord = findBestKey(listOfWord);
			keyWord = bestKeyWord;
		} else {
			keyWord = input;
		}

		LinkedList<Response> listOfResObj;
		if ((listOfResObj = knowledgeBase.get(keyWord)) != null) {
			extractRespList(listOfResObj);
		}
	}

	public void handleRepetition() {
		HashMap<Integer, String> listOfPrevResponse = new HashMap<Integer, String>();
		PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
		Integer pos;
		for(String responseTemp: listOfResponse) {
			pos = findRespPos(responseTemp);
			listOfPrevResponse.put(pos, responseTemp);
			pq.add(pos);
		}

		if (pq.peek() != null) {
			response = listOfPrevResponse.get(pq.peek());
		}
	}

	public void handleUserRepetition() {
		if (sameInput()) {
			handleEvent("repetition t1**");
		} else if (similarInput()) {
			handleEvent("repetition t2**");
		}
	}

	public void handleEvent(String eventName) {
		savePrevEvent();
		setEvent(eventName);

		saveInput();
		setInput(eventName);

		if (!sameEvent()) {
			findMatch();
		}

		restoreInput();
	}

	public void selectResponse() {
		if (botUnderstand()) {
			response = listOfResponse.get(random.nextInt(listOfResponse.size()));
		}
	}

	public void savePrevInput() {
		prevInput = input;
	}

	public void savePrevResponse() {
		prevResponse = response;
	}

	public void savePrevEvent() {
		prevResponse = event;
	}

	public void setEvent(String eventName) {
		event = eventName;
	}

	public void saveInput() {
		inputBackup = input;
	}

	public void setInput(String str) {
		input = str;
	}

	public void restoreInput() {
		input = inputBackup;
	}

	public void printResponse() {
		if (response.length() > 0) {
			System.out.println(response);
		}
	}

	public void preprocessInput() {
		input = input.replaceAll("[,!?;,.]", " ").replaceAll(" +", " ").replaceAll("( +$|^ +)", "");
		input = input.toLowerCase();
	}

	public void preprocessResponse() {
		if (response.indexOf('*') != -1) {
			findSubject();
			subject = transpose(subject);
			response = response.replaceFirst("\\*", " " + subject);
		}
	}

	public void findSubject() {
		subject = "";
		input = input.replaceFirst(" +$", "");
		keyWord = keyWord.replaceAll("(_+$|^_+)", "");
		int position = input.indexOf(keyWord);
		if (position != -1) {
			subject = input.substring(position + keyWord.length());
		}
	}

	public boolean botRepeat() {
		int position = findRespPos(response);;
		return (position > 0)? position + 1 < responseLog.size(): false;
	}

	public boolean userRepeat() {
		return (prevInput.length() > 0 &&
				input.indexOf(prevInput) != -1 ||
				prevInput.indexOf(input) != -1);
	}

	public boolean botUnderstand() {
		return listOfResponse.size() > 0;
	}

	public boolean nullInput() {
		return input.length() == 0 && prevInput.length() != 0;
	}

	public boolean nullInputRepetition() {
		return input.length() == 0 && prevInput.length() == 0;
	}

	public boolean userWantToQuit() {
		return input.indexOf("bye") != -1;
	}

	public boolean sameEvent() {
		return event.length() > 0 && event.equals(prevEvent);
	}

	public boolean noResponse() {
		return listOfResponse.size() == 0;
	}

	public boolean sameInput() {
		return input.length() > 0 && input.contentEquals(prevInput);
	}

	public String getSubPhrase(LinkedList<String> wordList, int start, int end) {
		String buffer = "";
		if (end > wordList.size()) {
			end = wordList.size();
		}

		for (int iii = start; iii < end; iii++) {
			buffer += wordList.get(iii);
			if (iii != end - 1) {
				buffer += " ";
			}
		}

		return buffer;
	}

	public String findBestKey(LinkedList<String> l) {
		String buffer = "";
		int size = l.size();
		boolean keyFound = false;
		
		for (int iii = 0, jjj = size; iii < size && jjj >= 1; iii++, jjj--) {
			for (int kkk = 0; (kkk + jjj) <= size; kkk++) {
				buffer = getSubPhrase(l, kkk, kkk + jjj);
				if (knowledgeBase.containsKey(buffer)) {
					keyFound = true;
				} else {
					buffer = preprocessKeyWord(buffer, kkk, kkk + jjj, size);
					if (knowledgeBase.containsKey(buffer)) {
						keyFound = true;
					}
				}

				if (keyFound) {
					return buffer;
				}
			}
		}
		
		return buffer;
	}

	public String preprocessKeyWord(String str, int start, int end, int size) {
		if (start == 0 ) {
			str = "_" + str;
		}

		if (end == size - 1) {
			str += "_";
		}

		return str;
	}

	public boolean similarInput() {
		return input.length() > 0 && input.indexOf(prevInput) != -1 ||
			prevInput.indexOf(input) != -1;
	}

	public String transpose(String str) {
		String buffer = " " + str + " ";
		boolean transposed = true;

		for (String[] transposePair: transposeList) {
			if (buffer.indexOf(transposePair[0]) != -1) {
				transposed = true;
				str = str.replaceAll(transposePair[0], transposePair[1]);
			}
		}

		if (!transposed) {
			for (String[] transposePair: transposeList) {
				if (buffer.indexOf(transposePair[1]) != -1) {
					str = str.replaceAll(transposePair[1], transposePair[0]);
				}
			}
		}
		return buffer.replaceAll("(^ +| +$", "");
	}

	public void loadDatabase() throws FileNotFoundException {
		Scanner dataScanner = new Scanner(new File(dataFileName));

		Response respObj = new Response();
		LinkedList<Response> listOfResObj = new LinkedList<Response>();

		String buffer;
		LinkedList<String> keyList = new LinkedList<String>();

		char symbol;

		while (dataScanner.hasNextLine()) {
			buffer = dataScanner.nextLine();
			symbol = buffer.charAt(0);
			buffer = buffer.substring(1);
			switch (symbol) {
				case 'k':
					keyList.push(buffer);
					break;
				case 'c':
					respObj.addContext(buffer);
					break;
				case 'r':
					respObj.addResp(buffer);
					break;
				case '%':
					listOfResObj.push(respObj);
					respObj = new Response();
					break;
				case '#':
					{
						if (respObj.isContaining()) {
							listOfResObj.push(respObj);
						}

						for (String key: keyList) {
							knowledgeBase.put(key, listOfResObj);
						}
						keyList.clear();
						listOfResObj = new LinkedList<Response>();
						respObj = new Response();
					}
					break;
			}
		}
		dataScanner.close();
	}

	public void signon() {
		handleEvent("signon**");
		selectResponse();
		saveLog();
		saveLog("chatterbot");
		printResponse();
	}

	public void extractRespList(LinkedList<Response> objList) {
		for(Response obj: objList) {
			LinkedList<String> listOfContext = obj.getContextList();
			if (listOfContext.isEmpty()) {
				listOfResponse = obj.getRespList();
			} else if (listOfContext.contains(prevResponse)) {
				listOfResponse = obj.getRespList();
				break;
			}
		}
	}

	public void saveBotResponse() {
		if (!response.isEmpty()) responseLog.push(response);
	}

	public int findRespPos(String str) {
		return responseLog.lastIndexOf(str);
	}

	public void saveUnknownInput() {
	}

	public void updateUnknownInputList() {
		listOfUnknownInput.push(input);
	}

	public void saveLog() {
	}

	public void saveLog(String str) {
	}

	public static void main(String[] args) {
		System.out.println("Chatterbot port");
		try {
			Adi bot = new Adi("Adi");

			bot.signon();
			while(!bot.quit()) {
				bot.getInput();
				bot.saveLog("user");
				bot.respond();
			}
			bot.saveUnknownInput();
		} catch(IOException e) {
			System.err.println(e);
		}
	}
}
