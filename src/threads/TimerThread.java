package threads;

import javafx.application.Platform;
import model.Timer;
import ui.ControllerGUI;

public class TimerThread extends Thread {

//	private Timer tm;
	private ControllerGUI cGUI;
	
	public TimerThread(ControllerGUI cGUI) {
		this.cGUI = cGUI;
	}
	
//	public Timer getTm() {
//		return tm;
//	}
//
//	public void setTm(Timer tm) {
//		this.tm = tm;
//	}

	@Override
	public void run() {
		
		int sec = 0;
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
				sec += 1;
				width = 5;

				cGUI.changeProgressBar(width);
				
				Platform.runLater(new Thread() {
					
					public void run() {
						
						cGUI.changeTimer();
					}
				});
				
				if(sec == 0) {
					
					active = false;
				}
			}
		}
	}
}
