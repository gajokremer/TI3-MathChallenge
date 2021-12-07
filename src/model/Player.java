package model;

import java.io.Serializable;

public class Player implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private int score;
	private Player right;
	private Player left;
	private boolean isLeaf;
	
	public Player(String name, int score) {
		this.setName(name);
		this.setScore(score);
		isLeaf = true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Player getRight() {
		return right;
	}

	public void setRight(Player right) {
		this.right = right;
	}

	public Player getLeft() {
		return left;
	}

	public void setLeft(Player left) {
		this.left = left;
	}
	
	public boolean isLeaf() {
		
		if(right == null && left == null) {
			
			isLeaf = true;
			
		} else {
			
			isLeaf = false;
		}
		
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String nodeForm() {
		
		String result = "";
		
		if(left != null) {
			
			result += left.getName();
			
		} else {
			
			result += null;
		}
		
		result += " <-- " + name + " --> ";
		
		if(right != null) {
			
			result += right.getName();
			
		} else {
			
			result += null;
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		
		return name + ", " + score + ", " + left + ", " + right;
	}
}