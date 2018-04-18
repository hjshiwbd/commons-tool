package commons.tool.org.apache.commons.exec.stream;

import java.util.List;
import java.util.Vector;

import org.apache.commons.exec.LogOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CollectingLogOutputStream extends LogOutputStream {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private final List<String> lines = new Vector<String>();

	private final int LINE_MAX = 10000;
	
    @Override
    protected void processLine(String line, int level) {
        logger.info(line);
        addLine(lines,line);
    }

    private void addLine(List<String> list, String line) {
		if (list.size() >= LINE_MAX) {
			list.remove(0);
		}
		list.add(line);
	}

	public List<String> getLines() {
        return lines;
    }
}
