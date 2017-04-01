package application;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.TextArea;

public class StringWriterService {

	private Service<Void> appendService;
	private int i = 0;
	
	public StringWriterService(String appender, TextArea area, boolean isAppendix) {
		appendService = new Service<Void>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					protected Void call() throws Exception {
						MainController.AppendixFinished.set(false);
						MainController.MaintextFinished.set(false);
						while (!isCancelled()) {
							if (MainController.run && !MainController.jump) {
								if (!MainController.getPopup().getSelectedFont().equals(area.getFont())) {
									area.setFont(MainController.getPopup().getSelectedFont());
								}
								area.appendText("" + appender.charAt(i));
								MainController.progress.set((double)i / (double)appender.length());
								i++;
								Thread.sleep(100);
							} else if (!MainController.run && !MainController.jump){
								Thread.sleep(500);
							} else if (MainController.run && MainController.jump){
									area.appendText(appender.substring(i));
									MainController.progress.set(1);
									i = appender.length();
							}
							if(i>=appender.length()){
								if(isAppendix){
									MainController.AppendixFinished.set(true);
								} else {
									MainController.MaintextFinished.set(true);
								}
								this.cancel();
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
