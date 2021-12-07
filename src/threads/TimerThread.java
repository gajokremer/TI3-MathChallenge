package threads;

import java.io.IOException;

import javafx.application.Platform;
import ui.ControllerGUI;

public class TimerThread extends Thread {

	private ControllerGUI cGUI;
	
	public TimerThread(ControllerGUI cGUI) {
		this.cGUI = cGUI;
	}
	
	@Override
	public void run() {
		
		int sec = 60;
		int mili = 0;
		int width = 0;
		boolean active = true;
		
		while(active) {
			
			try {
				Thread.sleep(4);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			mili += 4;
			
			if(mili == 1000) {
				
				mili = 0;
				sec--;
				width = 5;

				cGUI.changeProgressBar(width);
				
				Platform.runLater(new Thread() {
					
					public void run() {
						
						try {
							cGUI.changeTimer();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
				
				if(sec == 0) {
					
					active = false;
				}
			}
		}
		
		sec = 0;
	}
}
