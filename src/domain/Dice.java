package domain;

import java.util.concurrent.ThreadLocalRandom;

public class Dice {

	private int faceNumber;

	public Dice(int faceNumber) {
		this.faceNumber = faceNumber;
	}

	public int roll() {
		int result = ThreadLocalRandom.current().nextInt(faceNumber) + 1;
		return result;
	}

	// GETTERS AND SETTERS
	public int getFaceNumber() {
		return this.faceNumber;
	}
	public void setFaceNumber(int faceNumber) {
		this.faceNumber = faceNumber;
	}

}