package threads;

import javafx.application.Platform;
import model.Timer;
import ui.ControllerAdminGUI;

public class TimerThread extends Thread {

	private Timer tm;
	private ControllerAdminGUI cGUI;
	
	public TimerThread(ControllerAdminGUI cGUI) {
		this.cGUI = cGUI;
	}
	
	public Timer getTm() {
		return tm;
	}

	public void setTm(Timer tm) {
		this.tm = tm;
	}

	@Override
	public void run() {
		
		Platform.runLater(new Thread(() -> {
			
			cGUI.changeTimer(tm.getStart());
		}));
	}
}
