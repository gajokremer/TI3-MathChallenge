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

	public String[] newProblem() {
		
		int type = generateType();
		
		generateProblem(type, true);

//		int[] values = generateValues();
//		String equation = generateQuestion(values[0], values[1]);
//		System.out.println("Equation: " + equation);
//		return generateAnswer(equation);
		
		return null;
	}
	
	private int generateType() {
		
//		int type = (int) (Math.random() * (4 - 1) + 1);
		int type = 4;
		
		return type;
	}

	
	private void generateProblem(int type, boolean valid) {

		valid = true;
		
		if(valid) {
			
			int[] values = generateValues();
			int a = values[0];
			int b = values[1];
			
			if(type != 4) {
				
				String equation = generateQuestion(a, b, type);
				generateAnswer(equation);
				
			} else if(type == 4) {
				
				valid = isValid(a, b);
				
				if(valid) {
					
					String equation = generateQuestion(a, b, type);
					generateAnswer(equation);
					
				} else {
					
					generateProblem(type, valid);
				}
			}
			
		}
	}

	private int[] generateValues() {

		int[] values = new int[2];

		int a = (int) (Math.random() * 99);
		int b = (int) (Math.random() * 99);
		
		System.out.println("a: " + a);
		System.out.println("b: " + b);

		values[0] = a;
		values[1] = b;
		
		return values;
	}
	
	private String generateQuestion(int a, int b, int type) {
		
		String result = "";
//
//		int a = (int) (Math.random() * 99);
//		int b = (int) (Math.random() * 99);
//		
//		System.out.println("a: " + a);
//		System.out.println("b: " + b);
//
//		int type = (int) (Math.random() * (4 - 1) + 1);
//		int type = 4;
//		System.out.println(type);
		
		switch(type) {
		
		case 1:
//			result = a + " + " + b;
			result = sum(a, b);
			break;		
			
		case 2:
//			result = a + " - " + b;
			result = sub(a, b);
			break;
		
		case 3:
//			result = a + " * " + b;
			result = mult(a, b);
			break;

		case 4:
//			result = a + " / " + b;
			result = div(a, b);
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
	
	private String sum(int a, int b) {

		return a + " + " + b;
	}
	
	private String sub(int a, int b) {

		if(a > b || a == b) {
			
			return a + " - " + b;
			
		} else {
			
			return b + " - " + a;
		}
	}
	
	private String mult(int a, int b) {

		return a + " * " + b;
	}
	
	private String div(int a, int b) {
		
		if(a > b || a == b) {
			
			return a + "/" + b;
		}
		
		else {
			
			return b + "/" + a;
		}
		
		
	}
	
	private String[] generateAnswer(String equation) {

		return null;
	}

	public void addOrdered(String name) {
		
		
	}
}
