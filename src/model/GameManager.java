package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameManager {
	
	private Player root;
	private Player playingNow;
	private String currentQuestion;
	private List<Player> players;
	
	public String PLAYER_DATA = "data/PlayerTree.bin";
	
	public GameManager() {
		players = new ArrayList<>();
	}

	public Player getRoot() {
		return root;
	}

	public void setRoot(Player root) {
		this.root = root;
	}
	
	public Player getPlayingNow() {
		return playingNow;
	}

	public void setPlayingNow(Player playingNow) {
		this.playingNow = playingNow;
	}

	public String getCurrentQuestion() {
		return currentQuestion;
	}

	public void setCurrentQuestion(String currentQuestion) {
		this.currentQuestion = currentQuestion;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public boolean playerExists(Player p) {

		boolean found = false;
		
		return findPlayer(p, root, found);
	}
	
	private boolean findPlayer(Player p, Player r, boolean found) {
		
		if(r != null) {
			
			if(p.getName().equals(r.getName())) {
				
				found = true;
				
			} else {
				
				if(r.getRight() != null && !found) {
					
					found = findPlayer(p, r.getRight(), found);
				}
				
				if(r.getLeft() != null && !found) {
					
					found = findPlayer(p, r.getLeft(), found);
				}
			}
		}
		
		return found;
	}
	
	public int[] newProblem() {
		
		int type = generateType();
		
		int[] answers = generateProblem(type, true);
		
		return answers;
	}
	
	private int generateType() {
		
		int type = 1 + (int) (Math.random() * ((4 - 1) + 1));
		
		return type;
	}

	private int[] generateProblem(int type, boolean valid) {

		valid = true;
		
		if(valid) {
			
			int[] values = generateValues();
			int a = values[0];
			int b = values[1];

			if(type != 4) {
				
				generateQuestion(a, b, type);
				return generateAnswer(a, b, type);
				
			} else if(type == 4) {
				
				valid = isValid(a, b);
				
				if(valid) {
					
					generateQuestion(a, b, type);
					return generateAnswer(a, b, type);
					
				} else {
					
					return generateProblem(type, valid);
				}
			}
		}
		
		return null;
	}

	private int[] generateValues() {

		int[] values = new int[2];

		int a = (int) (Math.random() * 99);
		int b = (int) (Math.random() * 99);
		
		values[0] = a;
		values[1] = b;
		
		return values;
	}
	
	private void generateQuestion(int a, int b, int type) {
		
		String result = "";
		
		switch(type) {
		
		case 1:
			result = sumEquation(a, b);
			break;		
			
		case 2:
			result = subEquation(a, b);
			break;
		
		case 3:
			result = multEquation(a, b);
			break;

		case 4:
			result = divEquation(a, b);
			break;
		}
		
		setCurrentQuestion(result);
	}
	
	private boolean isValid(int a, int b) {
		
		if(a != 0 && b != 0) {
			
			if(a > b) {
				
				if(a % b == 0) {
					
					return true;
					
				} else {
					
					return false;
				}
				
			} else if(a < b) {
				
				if(b % a == 0) {
					
					return true;
					
				} else {
					
					return false;
				}
				
			} else {
				
				return true;
			}
		}
		
		return false;
	}
	
	private String sumEquation(int a, int b) {

		return a + " + " + b;
	}
	
	private String subEquation(int a, int b) {

		if(a > b || a == b) {
			
			return a + " - " + b;
			
		} else {
			
			return b + " - " + a;
		}
	}
	
	private String multEquation(int a, int b) {

		return a + " * " + b;
	}
	
	private String divEquation(int a, int b) {
		
		if(a > b || a == b) {
			
			return a + " / " + b;
		}
		
		else {
			
			return b + " / " + a;
		}
	}
	
	private int[] generateAnswer(int a, int b, int type) {

		int correct = 0;
		
		switch(type) {

		case 1:
			correct = sum(a, b);
			break;		

		case 2:
			correct = sub(a, b);
			break;

		case 3:
			correct = mult(a, b);
			break;

		case 4:
			correct = div(a, b);
			break;
		}
		
		int upper = 0;
		int lower = 0;
		
		upper = correct + 10;
		
		if(correct - 10 > 0) {
			
			lower = correct - 10;
			
		} else {
			
			lower = 0;
		}
		
		int wrong1, wrong2, wrong3;
		
		
		wrong1 = lower + (int) (Math.random() * ((upper - lower) + 1));
		
		while(wrong1 == correct) {
			
			wrong1 = lower + (int) (Math.random() * ((upper - lower) + 1));
		}
		
		wrong2 = lower + (int) (Math.random() * ((upper - lower) + 1));
		
		while(wrong2 == correct || wrong2 == wrong1) {
			
			wrong2 = lower + (int) (Math.random() * ((upper - lower) + 1));
		}
		
		wrong3 = lower + (int) (Math.random() * ((upper - lower) + 1));
		
		while(wrong3 == correct || wrong3 == wrong1 || wrong3 == wrong2) {
			
			wrong3 = lower + (int) (Math.random() * ((upper - lower) + 1));
		}
		
		int[] answers = {correct, wrong1, wrong2, wrong3};
		
		Random rand = new Random();
		
		for(int i = 0; i < answers.length; i++) {
			
			int randomIndex = rand.nextInt(answers.length);
			int temp = answers[randomIndex];
			answers[randomIndex] = answers[i];
			answers[i] = temp;
		}
		
		return answers;
	}

	private int sum(int a, int b) {
		
		return a + b;
	}

	private int sub(int a, int b) {

		if(a > b) {
			
			return a - b;
			
		} else if(b > a) {
			
			return b - a;
			
		} else {
			
			return 0;
		}
	}
	
	private int mult(int a, int b) {

		return a * b;
	}
	
	private int div(int a, int b) {

		if(a > b) {
			
			return a / b;
		}
		
		else if(a < b) {
			
			return b / a;
			
		} else {
			
			return 1;
		}
	}
	
	public boolean verifyAnswer(int answer) {
		
		String[] values = currentQuestion.split(" ");
		
		int a = Integer.parseInt(values[0]);
		int b = Integer.parseInt(values[2]);
		
		int c = 0;
		
		switch(values[1]) {
		
		case "+":
			c = a + b;
			break;
			
		case "-":
			c = a - b;
			break;
			
		case "*":
			c = a * b;
			break;
			
		case "/":
			c = a / b;
			break;
		}
		
		if(answer == c) {
			
			return true;
			
		} else {
			
			return false;
		}
	}
	
	public void addPlayer(Player p) {

		add(p, root);
	}
	
	private void add(Player p, Player r) {
		
		if(root == null) {

			root = p;

		} else {
			
			int rScore = 0;
			int pScore = 0;
			
			if(r != null && p != null) {
				
				rScore = r.getScore();
				pScore = p.getScore();
			}

			if(pScore > rScore) {

				if(r.getRight() == null) {

					r.setRight(p);
					r.setLeaf(false);

				} else {

					add(p, r.getRight());
				}

			} else if(pScore <= rScore) {

				if(r.getLeft() == null) {

					r.setLeft(p);
					r.setLeaf(false);

				} else {

					add(p, r.getLeft());
				}
			} 
		}
	}
	
	public boolean removePlayer(Player p) {

		if(remove(p, root) == p) {
			
			return true;
			
		} else {
			
			return false;
		}
	}
	
	private Player remove(Player p, Player current) {

		if(current == null) {

			return current;

		} else {
			
			if(p.getName() == root.getName()) {

				return removeRoot(p, root);
			} 
			
			if(current.getName().equals(p.getName())) {
				
				if(current.isLeaf()) { //FINE
					
					current = null;
					
				} else if(current.getRight() != null && current.getLeft() != null) {
					
					current = changePositions(current, successor(current));
					
				} else if(current.getRight() != null) {
					
					current = successor(current);
					current.setRight(remove(p, current.getRight()));
					
				} else if(current.getLeft() != null) {
					
					current = predecessor(current);
					current.setLeft(remove(p, current.getLeft()));
				}
				
			} else {
				
				if(p.getScore() > current.getScore()) {
					
					current.setRight(remove(p, current.getRight()));
					
				} else if(p.getScore() <= current.getScore()) {
					
					current.setLeft(remove(p, current.getLeft()));
				}
			}
		}
		
		return current;
	}
	
	private Player changePositions(Player current, Player successor) {

		Player parent = parent(current, successor);

		Player aux = successor;

		if(successor.getRight() != null) {
			
			parent.setLeft(successor.getRight());
			
		} else {
			
			parent.setLeft(null);
		}
		
		successor = null;
		
		Player currentCopy = current;
		
		current.setName(aux.getName());
		current.setScore(aux.getScore());
		
		if(aux.isLeaf() && aux == current.getRight()) {
			
			current.setRight(null);
		}
		
		if(aux.isLeaf() && aux == current.getLeft()) {
			
			current.setLeft(null);
		}
		
		if(current == parent) {
			
			current.setLeft(null);
		}
		
		if(currentCopy.getName() == root.getName()) {
			
			root = current;
		}
		
		return current;
	}
	
	private Player parent(Player current, Player successor) {

		current = current.getRight();
		
		if(current.getLeft() != null) {
			
			while(current.getLeft() != successor){
				
				current = current.getLeft();
			}
		}
		
		return current;
	}
	
	private Player removeRoot(Player p, Player root) {

		if(root != null) {

			if(root.getName().equals(p.getName())) {

				if(root.isLeaf()) { //FINE

					if(root.getName().equals(this.root.getName())) {

						this.root = null;
						root = null;

					} else {

						root = null;
					}

				} else if(root.getRight() != null && root.getLeft() != null) {

					root = changePositions(root, successor(root));

				} else if(root.getRight() != null) {

					root = successor(root);
					root.setRight(removeRoot(p, root.getRight()));

				} else if(root.getLeft() != null) {

					root = predecessor(root);
					root.setLeft(removeRoot(p, root.getLeft()));
				}
			} 
		}
		
		return p;
	}

	private Player successor(Player root){
		
		if(root.getRight() != null) {
			
			root = root.getRight();
			
			while(root.getLeft() != null){
				
				root = root.getLeft();
			}
			
		} else {
			
			root = root.getLeft();
			
			while(root.getLeft() != null){
				
				root = root.getLeft();
			}
		}
		
		return root;
	}

	private Player predecessor(Player root){
		
		if(root.getLeft() != null) {
			
			root = root.getLeft();
			
			while(root.getRight() != null){
				
				root = root.getRight();
			}
			
		} else {
			
			root = root.getRight();
			
			while(root.getRight() != null){
				
				root = root.getRight();
			}
		}
		
		return root;
	}
	
	public List<Player> orderedPlayerList() {

		players.removeAll(players);
		
		inOrder(root);
		
		return players;
	}
	
	private void inOrder(Player r) {
		
		if(r == null) {
			
			return;
			 
		} else {
			
			inOrder(r.getRight());
			players.add(r);
			inOrder(r.getLeft());
		}	
	}
	
	public String printOrdered(List<Player> pL) {
		
		String list = "";
		
		for(int i = 0; i < pL.size(); i++) {
			
			list += "\n" + (i + 1) + ". " + pL.get(i);
		}
		
		return list;
	}
	
	public String print(Player r) {

		String result = "";

		if(r != null) {

			result += r.nodeForm();

			if(r.getLeft() != null) {

				result += "\n" + print(r.getLeft());
			}
			
			if(r.getRight() != null) {
				
				result += "\n" + print(r.getRight());
			}
			 
		} else {
			
			result = "\n--There are no Programmers registered"; 
		}
		
		return result;
	}
	
	public Player findPlayer(String name) {
		
		Player p = null;
		
		for(int i = 0; i < players.size(); i++) {
			
			if(players.get(i).getName().equals(name)) {
				
				p = players.get(i);
			}
		}
		
		return p;
	}
	
	public int findPlayerPos(String name) {
		
		int pos = 0;
		
		for(int i = 0; i < players.size(); i++) {
			
			if(players.get(i).getName().equals(name)) {
				
				pos = i + 1;
			}
		}
		
		return pos;
	}
	
	public int findPlayerScore(String name) {
		
		int score = 0;
		
		for(int i = 0; i < players.size(); i++) {
			
			if(players.get(i).getName().equals(name)) {
				
				score = players.get(i).getScore();
			}
		}
		
		return score;
	}
	
	public void saveData() throws FileNotFoundException, IOException {

		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PLAYER_DATA));
		oos.writeObject(root);
		oos.close();
	}

	public boolean loadData() throws FileNotFoundException, IOException, ClassNotFoundException {

		File f = new File(PLAYER_DATA);

		boolean isLoaded = false;

		if(f.exists()) {

			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
			root = (Player) ois.readObject();
			ois.close();
			isLoaded = true;
		}

		return isLoaded;
	}
}
