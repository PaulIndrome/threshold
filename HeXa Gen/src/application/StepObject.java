package application;

public class StepObject {
	private StepObject dir0;
	private StepObject dir1;
	private StepObject dir2;
	private StepObject dir3;
	private StepObject dir4;
	private StepObject dir5;
	private boolean jump;
	private boolean end;

	public StepObject(StepObject dir0, StepObject dir1, StepObject dir2, StepObject dir3, StepObject dir4,
			StepObject dir5, boolean jump, boolean end) {
		this.dir0 = dir0;
		this.dir1 = dir1;
		this.dir2 = dir2;
		this.dir3 = dir3;
		this.dir4 = dir4;
		this.dir5 = dir5;
		this.jump = jump;
		this.end = end;
	}

	public StepObject getDir(int dir) {
		switch (dir) {
		case 0:
			return dir0;
		case 1:
			return dir1;
		case 2:
			return dir2;
		case 3:
			return dir3;
		case 4:
			return dir4;
		case 5:
			return dir5;
		default:
			System.out.println("default case reached, check getDir argument");
			return null;
		}
	}
	
	public boolean isJump() {
		return jump;
	}

	public boolean isEnd() {
		return end;
	}

	public void setDir0(StepObject dir0) {
		this.dir0 = dir0;
	}

	public void setDir1(StepObject dir1) {
		this.dir1 = dir1;
	}

	public void setDir2(StepObject dir2) {
		this.dir2 = dir2;
	}

	public void setDir3(StepObject dir3) {
		this.dir3 = dir3;
	}

	public void setDir4(StepObject dir4) {
		this.dir4 = dir4;
	}

	public void setDir5(StepObject dir5) {
		this.dir5 = dir5;
	}

	public StepObject getDir0() {
		return dir0;
	}

	public StepObject getDir1() {
		return dir1;
	}

	public StepObject getDir2() {
		return dir2;
	}

	public StepObject getDir3() {
		return dir3;
	}

	public StepObject getDir4() {
		return dir4;
	}

	public StepObject getDir5() {
		return dir5;
	}


}
