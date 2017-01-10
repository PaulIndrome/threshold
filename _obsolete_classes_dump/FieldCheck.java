package application;

public final class FieldCheck {

	private static GameRectangle[][] rectArray = GameStartController.getRectArray();
	private int height = GameStartController.getHeight();
	private int width = GameStartController.getWidth();
	private static int streak = GameStartController.getStreak();

	public static boolean check(int column, int heightPlaced, int team) {

		for (int i = 1; i <= streak; i++) {
			if (rectArray[column - i][heightPlaced - i].getTeam() != team)
				break;
			else if (i == streak && rectArray[column - i][heightPlaced - i].getTeam() == team)
				return true;
			else
				continue;
		}

		return false;

	}

}
