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
//		System.out.println("\nAnswers: " + answers);

//		System.out.print("\n\nBefore GUI: ");
//		printArray(answers);
		
		return answers;
	}
	
	private int generateType() {
		
		int type = 1 + (int) (Math.random() * ((4 - 1) + 1));
//		int type = 4;
		
		return type;
	}

	private int[] generateProblem(int type, boolean valid) {

//		int[] answers = null;
		
		valid = true;
		
		if(valid) {
			
			int[] values = generateValues();
			int a = values[0];
			int b = values[1];
//			int a = 100;
//			int b = 50;
			
//			System.out.println("a: " + a);
//			System.out.println("b: " + b);
//			System.out.println("type: " + type + "\n");
			
			if(type != 4) {
				
				generateQuestion(a, b, type);
//				answers = generateAnswer(a, b, type);
				return generateAnswer(a, b, type);
				
			} else if(type == 4) {
				
				valid = isValid(a, b);
//				System.out.println("Valid: " + valid);
				
				if(valid) {
					
					generateQuestion(a, b, type);
//					answers = generateAnswer(a, b, type);
					return generateAnswer(a, b, type);
					
				} else {
					
//					answers = generateProblem(type, valid);
					return generateProblem(type, valid);
				}
			}
		}
		
//		System.out.println("-Answers: " + answers);
		
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
//			result = a + " + " + b;
			result = sumEquation(a, b);
			break;		
			
		case 2:
//			result = a + " - " + b;
			result = subEquation(a, b);
			break;
		
		case 3:
//			result = a + " * " + b;
			result = multEquation(a, b);
			break;

		case 4:
//			result = a + " / " + b;
			result = divEquation(a, b);
			break;
		}
		
		setCurrentQuestion(result);
//		System.out.println("Equation: " + result);
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
//		
//		System.out.println("Correct: " + correct);
//		System.out.println("Upper: " + upper);
//		System.out.println("Lower: " + lower + "\n");
		
		int wrong1, wrong2, wrong3;
		
		
		wrong1 = lower + (int) (Math.random() * ((upper - lower) + 1));
//		System.out.println("Wrong1: " + wrong1);
		
		while(wrong1 == correct) {
			
			wrong1 = lower + (int) (Math.random() * ((upper - lower) + 1));
//			System.out.println("New Wrong1: " + wrong1);
		}
		
		wrong2 = lower + (int) (Math.random() * ((upper - lower) + 1));
//		System.out.println("Wrong2: " + wrong2);
		
		while(wrong2 == correct || wrong2 == wrong1) {
			
			wrong2 = lower + (int) (Math.random() * ((upper - lower) + 1));
//			System.out.println("New Wrong2: " + wrong2);
		}
		
		wrong3 = lower + (int) (Math.random() * ((upper - lower) + 1));
//		System.out.println("Wrong3: " + wrong3);
		
		while(wrong3 == correct || wrong3 == wrong1 || wrong3 == wrong2) {
			
			wrong3 = lower + (int) (Math.random() * ((upper - lower) + 1));
//			System.out.println("New Wrong3: " + wrong3);
		}
		
		int[] answers = {correct, wrong1, wrong2, wrong3};
		
//		System.out.print("Before: ");
//		printArray(answers);
		
		Random rand = new Random();
		
		for(int i = 0; i < answers.length; i++) {
			
			int randomIndex = rand.nextInt(answers.length);
			int temp = answers[randomIndex];
			answers[randomIndex] = answers[i];
			answers[i] = temp;
		}
		
//		System.out.println();
//		System.out.print("Random: ");
//		printArray(answers);
		
//		System.out.println("\n\nAnswers: " + answers);
		
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
		
		printArray(values);
		
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

//		Player p = new Player(name, phone, address, email);
		
		add(p, root);
		
		System.out.println("\nTrue Root: " + root);
	}
	
	private void add(Player p, Player r) {
		
		if(root == null) {

			root = p;
//			setTotalPlayers(getTotalPlayers() + 1);

		} else {
			
			System.out.println("Root: " + r);
			System.out.println("Player: " + p);
			
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
//					setTotalPlayers(getTotalPlayers() + 1);

				} else {

					add(p, r.getRight());
				}

			} else if(pScore <= rScore) {

				if(r.getLeft() == null) {

					r.setLeft(p);
					r.setLeaf(false);
//					setTotalPlayers(getTotalPlayers() + 1);

				} else {

					add(p, r.getLeft());
				}
			} 
		}
	}
	
	public void removePlayer(Player p) {

		Player prev = null;
		
		remove(p, root, prev);
		
//		removeCopy1(root, p);
	}
	
	private void remove(Player p, Player current, Player prev) {

//		if(current.isLeaf()) {
//			
//			if(prev == null) {
//				
//				if(current == root && root == p) {
//					
//					root = null;
//				}
//				
//			} else {
//
//				if(prev.getLeft() == current && current == p) {
//					
//					prev.setLeft(null);
//					
//				} else if(prev.getRight() == current && current == p) {
//					
//					prev.setRight(null);
//				}
//			}
//			
//		} else {
//			
//			remove(p, current.getLeft(), current);
//			remove(p, current.getRight(), current);
//		}
	}

//	private Player removeCopy1(Player root, Player p) {
//
//		if (root == null) {
//
//			return null;
//		}
//
//		if(root.getScore() > p.getScore()) {
//			
//			root.setLeft(removeCopy1(root.getLeft(), p));
//			
//		} else if(root.getScore() <= p.getScore()) {
//			
//			root.setRight(removeCopy1(root.getRight(), p));
//
//		} else {
//			// if nodeToBeDeleted have both children
////			if (root.left != null && root.right != null) {
//			if(root.getLeft() != null && root.getRight() != null) {
//				
//				Player temp = root;
//				// Finding minimum element from right
//				Player minNodeForRight = minimumElement(temp.getRight());
//				// Replacing current node with minimum node from right subtree
//				root.setScore(minNodeForRight.getScore());
//				// Deleting minimum node from right now
//				root.setRight(removeCopy1(root.getRight(), minNodeForRight));
//			}
//			// if nodeToBeDeleted has only left child
//			else if(root.getLeft() != null) {
//				
//				root = root.getLeft();
//			}
//			// if nodeToBeDeleted has only right child
//			else if(root.getRight() != null) {
//				
//				root = root.getRight();
//			}
//			// if nodeToBeDeleted do not have child (Leaf node)
//			else {
//				
//				root = null;
//			}
//		}
//		return root;
//	}
//	
//	public Player minimumElement(Player root) {
//		
//		if (root.getLeft() == null)
//			
//			return root;
//		
//		else {
//	
//			return minimumElement(root.getLeft());
//		}
//	}
	
//	public Player removeCopy(Player current, Player p) {
//		
//		System.out.println("Current: " + current);
//		
//		if(current == null) {
//
//			return current;
//
//		} else {
//
//			if(p.getScore() > current.getScore()){ //move right
//
//				current.setRight(removeCopy(current.getRight(), p));
//
//			} else if(p.getScore() <= current.getScore()) { //move left
//
//				current.setLeft(removeCopy(current.getLeft(), p));
//
//			} else { //oh yes, we finally found the target
//
////				if(current.getLeft() == null && current.getRight() == null) { //hmm, its a leaf node; easy peasy
//				if(current.isLeaf()) { //hmm, its a leaf node; easy peasy
//
//					current = null;
//
//				} else if(current.getRight() != null) { // oh, it has a right child, don't make it an orphan or is it old enough to become a parent ? lets find out
//
//					current = (successor(current));
//					current.setRight(removeCopy(current.getRight(), p));
//
//				} else { //oh it seems that I do not have a worthy successor, fallback, fallback ...
//
//					current = (predecessor(current));
//					current.setLeft(removeCopy(current.getLeft(), p));
//				}
//			}
//		}
//		
//		return current;
//	}
//	
//	private Player successor(Player root){
//
//		root = root.getRight();
//
//		while(root.getLeft() != null){
//			
//			root = root.getLeft();
//		}
//		
//		return root;
//	}
//	
//	private Player predecessor(Player root){
//		
//		root = root.getLeft();
//		
//		while(root.getRight() != null){
//			
//			root = root.getRight();
//		}
//		
//		return root;
//	}

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

		//		System.out.println("=" + root);

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
	
	public void printArray(String[] array) {

		for(int i = 0; i < array.length; i++) {
			
			System.out.print(array[i] + " ");
		}
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
