public class Dice {

	private int faceNumber;

	public Dice(int faceNumber) {
		this.faceNumber = faceNumber;
	}

	public int roll() {
		int result = (int)(Math.random() * faceNumber) + 1;
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