package PicController;

import static PicController.Controller.threadSpeed;
import static PicController.Simulator_UI.frequency;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class OwnKeyListener implements KeyListener {

	public OwnKeyListener() {

	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("Pressed");
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		System.out.println(e.getKeyCode());
		switch (e.getKeyCode()) {
		case 521:
			if (threadSpeed >= 50) {
				frequency.setText(Long.toString(threadSpeed += 50));
			}
			
			break;

		case 45:
			if (threadSpeed > 50) {
				frequency.setText(Long.toString(threadSpeed -= 50));
			}
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		System.out.println("Typed");
		
	}

}
