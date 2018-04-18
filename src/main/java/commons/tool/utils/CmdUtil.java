package commons.tool.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteStreamHandler;
import org.apache.commons.exec.PumpStreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import commons.tool.bean.CmdRunResultBean;
import commons.tool.org.apache.commons.exec.stream.CollectingLogOutputStream;

public class CmdUtil {
	private static Logger logger = LoggerFactory.getLogger(CmdUtil.class);

	/**
	 * 日志最多保留行数.
	 */
	private final static int LINE_MAX = 10000;

	/**
	 * depends on org.apache.commons.exec.CommandLine
	 * 
	 * @param cmd
	 * @return
	 * @throws ExecuteException
	 * @throws IOException
	 */
	public static CmdRunResultBean runCmd(String cmd) throws ExecuteException, IOException {
		CommandLine cmdLine = CommandLine.parse(cmd);
		DefaultExecutor executor = new DefaultExecutor();
		CollectingLogOutputStream stdout = new CollectingLogOutputStream();
		CollectingLogOutputStream errout = new CollectingLogOutputStream();
		ExecuteStreamHandler handler = new PumpStreamHandler(stdout, errout);
		executor.setStreamHandler(handler);
		int v = executor.execute(cmdLine);

		CmdRunResultBean r = new CmdRunResultBean();
		r.setExitValue(v);
		r.setRunlogsInput(stdout.getLines());
		r.setRunLogs(errout.getLines());
		return r;
	}

	/**
	 * depends on java.lang.Process
	 * 
	 * @param cmd
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static CmdRunResultBean runCmd2(String cmd) throws IOException, InterruptedException {
		Process process = Runtime.getRuntime().exec(new String[] { "sh", "-c", cmd });
		// 标准错误
		BufferedReader buf = new BufferedReader(new InputStreamReader(process.getErrorStream()));
		List<String> stderrLogs = new ArrayList<>();
		String line = null;
		while ((line = buf.readLine()) != null) {
			logger.info(line);
			addLineToList(line, stderrLogs);
		}
		// 标准输出
		BufferedReader buf2 = new BufferedReader(new InputStreamReader(process.getInputStream()));
		List<String> stdoutLogs = new ArrayList<>(LINE_MAX);
		line = null;
		while ((line = buf2.readLine()) != null) {
			logger.info(line);
			addLineToList(line, stdoutLogs);
		}
		process.waitFor();
		int v = process.exitValue();

		CmdRunResultBean r = new CmdRunResultBean();
		r.setExitValue(v);
		r.setRunLogs(stderrLogs);
		r.setRunlogsInput(stdoutLogs);
		return r;
	}

	/**
	 * usage: curl -i www.baidu.com -> {"curl","-i","www.baidu.com"}
	 * 
	 * @param cmds
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static CmdRunResultBean runCmd3(String[] cmds) throws IOException, InterruptedException {
		Process process = Runtime.getRuntime().exec(cmds);
		// 标准错误
		BufferedReader buf = new BufferedReader(new InputStreamReader(process.getErrorStream()));
		List<String> stderrLogs = new ArrayList<>(LINE_MAX);
		String line;
		while ((line = buf.readLine()) != null) {
			logger.info(line);
			addLineToList(line, stderrLogs);
		}

		// 标准输出
		BufferedReader buf2 = new BufferedReader(new InputStreamReader(process.getInputStream()));
		List<String> stdoutLogs = new ArrayList<>(LINE_MAX);
		line = null;
		while ((line = buf2.readLine()) != null) {
			logger.info(line);
			addLineToList(line, stdoutLogs);
		}
		process.waitFor();
		int v = process.exitValue();

		CmdRunResultBean r = new CmdRunResultBean();
		r.setExitValue(v);
		r.setRunLogs(stderrLogs);
		r.setRunlogsInput(stdoutLogs);
		return r;
	}

	/**
	 * 仅保留LINE_LIMIT行.先进先出
	 * 
	 * @param line
	 * @param list
	 */
	private static void addLineToList(String line, List<String> list) {
		if (list.size() >= LINE_MAX) {
			list.remove(0);
		}
		list.add(line);
	}

}
