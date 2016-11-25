package application;

public class MovementArrayGenerator {

	public MovementArrayGenerator(String... possibles) {
		String[] paramlist = new String[possibles.length];
		int i = 0;
		int fulllength = 0;
		for (String str : possibles) {
			paramlist[i] = str;
			fulllength += str.length();
			i++;
		}
		/*
		 * At this point, paramlist includes all given parameters in an array
		 * and fulllength is the length of all those parameters added
		 */
		for (int e = 0; i < paramlist.length - 1; i++) {
			if (paramlist[e].length() != paramlist[e + 1].length()) {
				System.out.println("All parameters must be equal in length.");
			} else {
				e++;
			}
		}
		/*
		 * At this point, we have checked all Strings in paramlist for the same
		 * length. It would be possible to add a boolean-query here that
		 * prohibits progression past this point if the parameters are of
		 * unequal lengths
		 */
		StepObject root = new StepObject(null, null, null, null, null, null, false, false);

		for (String str : paramlist) {
			for (char a : str.toCharArray()) {
				
			}
		}

	}

	public void appendStepObjects(StepObject o, int dir, boolean isend) {
		if(!isend){
		switch (dir) {
		case 0:
			o.setDir0(new StepObject(null, null, null, null, null, null, false, false));
			break;
		case 1:
			o.setDir1(new StepObject(null, null, null, null, null, null, false, false));
			break;
		case 2:
			o.setDir2(new StepObject(null, null, null, null, null, null, false, false));
			break;
		case 3:
			o.setDir3(new StepObject(null, null, null, null, null, null, false, false));
			break;
		case 4:
			o.setDir4(new StepObject(null, null, null, null, null, null, false, false));
			break;
		case 5:
			o.setDir5(new StepObject(null, null, null, null, null, null, false, false));
			break;
		default:
			break;
		}
		} else {
			switch (dir) {
			case 0:
				o.setDir0(new StepObject(null, null, null, null, null, null, false, true));
				break;
			case 1:
				o.setDir1(new StepObject(null, null, null, null, null, null, false, true));
				break;
			case 2:
				o.setDir2(new StepObject(null, null, null, null, null, null, false, true));
				break;
			case 3:
				o.setDir3(new StepObject(null, null, null, null, null, null, false, true));
				break;
			case 4:
				o.setDir4(new StepObject(null, null, null, null, null, null, false, true));
				break;
			case 5:
				o.setDir5(new StepObject(null, null, null, null, null, null, false, true));
				break;
			default:
				break;
			}
			return;
		}
	}

}
