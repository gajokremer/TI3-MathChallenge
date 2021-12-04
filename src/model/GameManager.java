package model;

public class GameManager {
	
	private Player root;
	private String currentQuestion;
	
	public GameManager() {
		
	}

	public Player getRoot() {
		return root;
	}

	public void setRoot(Player root) {
		this.root = root;
	}
	
	public String getCurrentQuestion() {
		return currentQuestion;
	}

	public void setCurrentQuestion(String currentQuestion) {
		this.currentQuestion = currentQuestion;
	}

	public int[] newProblem() {
		
		int type = generateType();
		
		generateProblem(type, true);

		return null;
	}
	
	private int generateType() {
		
		int type = (int) (Math.random() * (4 - 1) + 1);
//		int type = 1;
		
		return type;
	}

	private int[] generateProblem(int type, boolean valid) {

		valid = true;
		
		if(valid) {
			
			int[] values = generateValues();
			int a = values[0];
			int b = values[1];
			
			System.out.println("a: " + a);
			System.out.println("b: " + b);
			System.out.println("type: " + type + "\n");
			
			if(type != 4) {
				
				generateQuestion(a, b, type);
				return generateAnswer(a, b, type);
				
			} else if(type == 4) {
				
				valid = isValid(a, b);
				
				if(valid) {
					
					generateQuestion(a, b, type);
					return generateAnswer(a,b, type);
					
				} else {
					
					generateProblem(type, valid);
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
	
	private String generateQuestion(int a, int b, int type) {
		
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
		
		return result;
	}
	
	private boolean isValid(int a, int b) {
		
		if(a != 0 && b != 0) {
			
			if(a > b || a == b) {
				
				if(a % b == 0) {
					
					return true;
					
				} else {
					
					return false;
				}
				
			} else {
				
				if(b % a == 0) {
					
					return true;
					
				} else {
					
					return false;
				}
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
		
		System.out.println("Correct: " + correct);
		System.out.println("Upper: " + upper);
		System.out.println("Lower: " + lower + "\n");
		
		int wrong1, wrong2, wrong3;
		
		wrong1 = (int) (Math.random() * (upper - lower) + lower);
		
		do {
			
			wrong2 = (int) (Math.random() * (upper - lower) + lower);
			
		} while(wrong1 == wrong2);
		
		do {
			
			wrong3 = (int) (Math.random() * (upper - lower) + lower);
			
		} while(wrong1 == wrong3);
		
//		System.out.println(wrong1 + ", " + wrong2 + "," + wrong3);

		for(int i = 0; i < 3; i++) {
			
			
		}
		
		return null;
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

		if(a > b || a == b) {
			
			return a / b;
		}
		
		else {
			
			return b / a;
		}
	}
	
	public void addOrdered(String name) {
		
		
	}
}
