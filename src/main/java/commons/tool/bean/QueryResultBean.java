package commons.tool.bean;

import java.util.List;

/**
 * mysql/hive查询结果返回
 * 
 * @author xinqian
 *
 */
public class QueryResultBean {
    /**
     * 查询结果.数据结构一般为List[Object[]]
     */
    private Object object;
    /**
     * 执行hive查询时有运行日志
     */
    private List<String> runlogs;

    public QueryResultBean() {

    }

    public QueryResultBean(Object object, List<String> runlogs) {
        super();
        this.object = object;
        this.runlogs = runlogs;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public List<String> getRunlogs() {
        return runlogs;
    }

    public void setRunlogs(List<String> runlogs) {
        this.runlogs = runlogs;
    }

}
