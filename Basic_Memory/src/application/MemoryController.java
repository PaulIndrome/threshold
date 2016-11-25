package application;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class MemoryController {

	@FXML
	private Pane MemField;
	private Label richtig = new Label("Richtig!");
	private Label falsch = new Label("Falsch!");

	public static ArrayList<FXMemBlock> field = new ArrayList<FXMemBlock>();
	public ArrayList<Color> hiddenColors = new ArrayList<Color>();
	public ArrayList<Color> colorList;

	private static FXMemBlock firstPick;
	private static FXMemBlock secondPick;
	private static FXMemBlock tempPick;
	private static Service<Void> controllService;
	private static boolean alreadyCalled = false;
	private static boolean match;

	private static SimpleIntegerProperty picks = new SimpleIntegerProperty(0);

	public void initialize() {
		richtig.setTextFill(Color.GREEN);
		richtig.setVisible(false);
		MemField.getChildren().add(richtig);
		falsch.setTextFill(Color.RED);
		falsch.setVisible(false);
		MemField.getChildren().add(falsch);

		picks.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				if (arg2.intValue() == 2) {
					match = MatchColors();
					if(match){
						firstPick.keepColor();
						secondPick.keepColor();
						field.remove(firstPick);
						field.remove(secondPick);
						if(field.isEmpty()){
							richtig.setText("Du hast gewonnen!");
							richtig.setVisible(true);
						}
					}
				} else if (arg2.intValue() == 3) {
					if (match) {
						firstPick = tempPick;
						picks.set(1);
					} else {
						firstPick.hideColor();
						secondPick.hideColor();
						firstPick = tempPick;
						picks.set(1);
					}
				} else {
					System.out.println("Something is wrong!");
				}
			}
		});
		try {
			drawSquares(6, 6);
		} catch (IOException | IllegalArgumentException | ClassNotFoundException | IllegalAccessException e) {
			System.out.println("Ungültige Startbedingungen.");
			e.printStackTrace();
		}

		controllService = new Service<Void>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					protected Void call() throws Exception {
						while (!isCancelled()) {
							if (picks.intValue() == 1) {
								if (!alreadyCalled) {
									System.out.println("Service noticed a first pick!");
									alreadyCalled = true;
								}
							} else if (picks.intValue() == 2) {
								if (!alreadyCalled) {
									System.out.println("Service noticed a second pick!");
									alreadyCalled = true;
								}
							}
						}

						return null;
					}
				};
			}
		};
		controllService.start();

	}

	public void drawSquares(int sizeX, int sizeY) throws IOException, ClassNotFoundException, IllegalAccessException {
		int fullsize = sizeX * sizeY;
		colorList = new ArrayList<Color>(allColors());
		for (int c = 0; c < fullsize; c++) {
			hiddenColors.add(Color.BLACK);
		}
		for (int c = 0; c < fullsize; c += 2) {
			int count = 0;
			int randomColorIndex = (int) (Math.random() * colorList.size());
			Color randomColor = colorList.get(randomColorIndex);
			colorList.remove(randomColorIndex);
			while (count < 2) {
				int randomIndex = (int) (Math.random() * fullsize);
				if (hiddenColors.get(randomIndex) == Color.BLACK) {
					hiddenColors.set(randomIndex, randomColor);
					System.out.println("HiddenColor at Index " + randomIndex + " set with " + randomColor.toString());
					count++;
				}
			}

		}
		if ((sizeX * sizeY) % 2 != 0) {
			System.out.println("Memory cannot be played on a field with an uneven number of cards.");
			MemField.getChildren().add(new Label("Memory cannot be played on a field with an uneven number of cards."));
			throw new IllegalArgumentException();
		}
		int offsetX = 20;
		int offsetY = 20;
		int space = 50;
		int colorCount = 0;

		for (int x = 0; x < sizeX; x++) {
			for (int y = 0; y < sizeY; y++) {
				field.add(new FXMemBlock(MemField, offsetX, offsetY, hiddenColors.get(colorCount), Color.BLACK,
						colorCount));
				offsetY += space;
				colorCount++;
			}
			offsetY = 20;
			offsetX += space;
		}

	}

	public boolean MatchColors() {
		if (firstPick.equals(secondPick)) {
			falsch.setVisible(false);
			richtig.setVisible(true);
			alreadyCalled = false;
			return true;
		} else {
			falsch.setVisible(true);
			richtig.setVisible(false);
			alreadyCalled = false;
			return false;
		}
	}

	public static FXMemBlock getFirstPick() {
		return firstPick;
	}

	public static void setFirstPick(FXMemBlock firstPick) {
		MemoryController.firstPick = firstPick;
	}

	public static FXMemBlock getSecondPick() {
		return secondPick;
	}

	public static void setSecondPick(FXMemBlock secondPick) {
		MemoryController.secondPick = secondPick;
	}

	public static SimpleIntegerProperty getPicks() {
		return picks;
	}

	public static void setPicks(int picks) {
		MemoryController.picks.set(picks);
	}

	public static void setAlreadyCalled() {
		alreadyCalled = !alreadyCalled;
	}

	public static FXMemBlock getBlockFromField(int index) {
		return field.get(index);
	}

	private static ArrayList<Color> allColors() throws ClassNotFoundException, IllegalAccessException {
		ArrayList<Color> colors = new ArrayList<>();
		Class clazz = Class.forName("javafx.scene.paint.Color");
		if (clazz != null) {
			Field[] field = clazz.getFields();
			for (int i = 1; i < field.length; i++) {
				Field f = field[i];
				Object obj = f.get(null);
				if (obj instanceof Color) {
					if ((Color) obj != Color.BLACK) {
						colors.add((Color) obj);
						System.out.println("Color added " + obj.toString());
					}
				}

			}
		}
		return colors;
	}

	public static void setTempPick(FXMemBlock tempPick) {
		MemoryController.tempPick = tempPick;
	}

}
