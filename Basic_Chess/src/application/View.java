package application;

public class View {


	public static void putCfAtGlow(GlowField gF) {
		ChessFieldController.getCurrent().setCf(gF.getCol(), gF.getRow());
		gF.getGlowPane().setMouseTransparent(true);
		ChessFieldController.switchTeam();
	}

}
