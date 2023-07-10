package utils;

import ui.UIManager;

/**
 * Provides a representation for a continuous progress. Uses
 * {@link UIManager#setStatus} for displaying. Needs to be used in a
 * try-with-resouce block.
 * <p>
 * Example:
 *
 * <pre>
 * try (Progress progress = new Progress(1, 3, "Downloading: ")) {
 * 	for (int i = 0; i < 3; i++) {
 * 		progress.show().step();
 * 		download(i);
 * 	}
 * }
 * </pre>
 *
 * @author Alex Mikulinski
 * @since 18.04.2019
 */
public class Progress implements AutoCloseable {
	private boolean	running;
	private String		task;
	private int			taskLength;
	private int			progress;

	@Override
	public void close() {
		if (!this.running) {
			return;
		}

		this._show(this.task + "finished");
		this.finish();
	}

	/**
	 * Creates a new {@link Progress}. Does not {@link #start() start} the
	 * {@link Progress}.
	 */
	public Progress() {
		this.task			= "";
		this.progress		= 0;
		this.taskLength	= 0;
	}

	/**
	 * Creates a new {@link Progress}. Will {@link #start() start} the
	 * {@link Progress} if the passed task length is greater than 0.
	 *
	 * @param _taskLength
	 *           The miximum task length.
	 * @param _task
	 *           A short task description.
	 */
	public Progress(int _taskLength, String _task) {
		this.task			= _task;
		this.progress		= 0;
		this.taskLength	= _taskLength;

		// If the length is 0, there will be no progress
		if (this.taskLength <= 0) {
			return;
		}

		this.start();
	}

	/**
	 * Creates a new {@link Progress}. Will {@link #start() start} the
	 * {@link Progress} if the passed start value is not greater than the passed
	 * task length.
	 *
	 * @param _start
	 *           The starting point.
	 * @param _taskLength
	 *           The miximum task length.
	 * @param _task
	 *           A short task description.
	 */
	public Progress(int _start, int _taskLength, String _task) {
		this.task			= _task;
		this.progress		= _start;
		this.taskLength	= _taskLength;

		// If the progress is already longer than the task length, there will be no
		// progress
		if (this.progress > this.taskLength) {
			return;
		}

		this.start();
	}

	/**
	 * Make one step.
	 *
	 * @return This {@link Progress}.
	 */
	public Progress step() {
		return this.step(1);
	}

	/**
	 * Make passed amount of steps.
	 *
	 * @param _steps
	 *           Amount of steps.
	 * @return This {@link Progress}.
	 */
	public Progress step(int _steps) {
		this.progress += _steps;
		return this;
	}

	/** Start this {@link Progress}. */
	public void start() {
		this.running = true;
		UIManager.getInstance().setCursorWait();
	}

	/** Finish this {@link Progress}. */
	public void finish() {
		this.running = false;
		UIManager.getInstance().setCursorNormal();
	}

	private void _show(String _text) {
		UIManager.getInstance().setStatus(_text);
	}

	/**
	 * Shows the representation for this {@link Progress}.
	 *
	 * @return This {@link Progress}.
	 */
	public Progress show() {
		this._show(this.task + this.progress + " / " + this.taskLength);
		return this;
	}

	/**
	 * Shows the representation for this {@link Progress} in percent.
	 *
	 * @return This {@link Progress}.
	 */
	public Progress showPercent() {
		this._show(this.task + Math.round(this.progress / this.taskLength * 100));
		return this;
	}

	/**
	 * Returns the {@link #task} of current {@link Progress}.
	 *
	 * @return The {@link #task} as {@link String}.
	 */
	public String getTask() {
		return this.task;
	}

	/**
	 * Sets the {@link #task} of current {@link Progress}.
	 *
	 * @param task
	 *           The {@link #task} to set as {@link String}.
	 */
	public void setTask(String task) {
		this.task = task;
	}

	/**
	 * Returns the {@link #taskLength} of current {@link Progress}.
	 *
	 * @return The {@link #taskLength} as int.
	 */
	public int getTaskLength() {
		return this.taskLength;
	}

	/**
	 * Sets the {@link #taskLength} of current {@link Progress}.
	 *
	 * @param taskLength
	 *           The {@link #taskLength} to set as int.
	 */
	public void setTaskLength(int taskLength) {
		this.taskLength = taskLength;
	}

	/**
	 * Returns the {@link #progress} of current {@link Progress}.
	 *
	 * @return The {@link #progress} as int.
	 */
	public int getProgress() {
		return this.progress;
	}

	/**
	 * Sets the {@link #progress} of current {@link Progress}.
	 *
	 * @param progress
	 *           The {@link #progress} to set as int.
	 */
	public void setProgress(int progress) {
		this.progress = progress;
	}
}
