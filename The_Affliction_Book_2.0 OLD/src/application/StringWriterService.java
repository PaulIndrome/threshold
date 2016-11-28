package application;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.TextArea;

public class StringWriterService {

	private Service<Void> appendService;

	public StringWriterService(String appender, TextArea area) {
		appendService = new Service<Void>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					protected Void call() throws Exception {
						int i = 0;
						while (!isCancelled()) {
							if (MainController.run) {
								if (!MainController.getPopup().getSelectedFont().equals(area.getFont())) {
									area.setFont(MainController.getPopup().getSelectedFont());
								}
								area.appendText("" + appender.charAt(i));
								i++;
								MainController.progress.set(appender.length()/);
								Thread.sleep(100);
							} else {
								Thread.sleep(500);
							}
						}
						return null;
					}
				};
			}
		};

	}

	public void startService() {
		appendService.restart();
	}

}
