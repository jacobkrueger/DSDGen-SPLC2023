package ui;

import java.awt.Cursor;
import java.awt.EventQueue;

import ui.view.MainFrame;

/**
 * Singleton. Manages the GUI for this program.
 *
 * @author Alex Mikulinski
 * @since 10.02.2019
 */
public class UIManager {
	private static UIManager	instance;

	private MainFrame				mainFrame;

	private UIManager() {
	}

	/**
	 * @return The {@link UIManager} instance for this program.
	 */
	public static UIManager getInstance() {
		if (instance == null) {
			instance = new UIManager();
		}
		return instance;
	}

	/**
	 * Starts the GUI.
	 */
	public void start() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.this.mainFrame = new MainFrame();
					UIManager.this.mainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Clears the title for this {@link #mainFrame}. The title is used to display
	 * the status of a process.
	 */
	public void clearStatus() {
		this.setStatus("");
	}

	/**
	 * Sets the title for this {@link #mainFrame}. The title is used to display the
	 * status of a process.
	 *
	 * @param _text
	 *           The title to set.
	 */
	public void setStatus(String _text) {
		this.mainFrame.setTitle(_text);
	}

	/**
	 * Sets the mouse cursor to {@link Cursor#WAIT_CURSOR}.
	 */
	public void setCursorWait() {
		this.mainFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	}

	/**
	 * Sets the mouse cursor to default.
	 */
	public void setCursorNormal() {
		this.mainFrame.setCursor(null);
	}
}
