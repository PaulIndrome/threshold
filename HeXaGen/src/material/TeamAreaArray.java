package material;

public class TeamAreaArray {

	int[] cleanSteps = new int[]{	16,17,18,19,20,21,
									31,32,33,34,35,
									46,47,48,49,
									61,62,63,
									76,77,
									91,
									163,
									177,178,
									191,192,193,
									205,206,207,208,
									219,220,221,222,223,
									233,234,235,236,237,238};

	public boolean contains(int step) {
		for (int i = 0; i < cleanSteps.length; i++) {
			if (cleanSteps[i] == step)
				return true;
		}
		return false;
	}
}