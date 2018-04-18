package commons.tool.bean;

import java.util.List;

public class CmdRunResultBean {
	private int exitValue;
	/**
	 * std err
	 */
	private List<String> runLogs;
	/**
	 * std out
	 */
	private List<String> runlogsInput;

	public int getExitValue() {
		return exitValue;
	}

	public void setExitValue(int exitValue) {
		this.exitValue = exitValue;
	}

	public List<String> getRunLogs() {
		return runLogs;
	}

	public void setRunLogs(List<String> runLogs) {
		this.runLogs = runLogs;
	}

	public List<String> getRunlogsInput() {
		return runlogsInput;
	}

	public void setRunlogsInput(List<String> runlogsInput) {
		this.runlogsInput = runlogsInput;
	}
}
