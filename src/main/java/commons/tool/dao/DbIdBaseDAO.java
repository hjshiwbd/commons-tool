package commons.tool.dao;

import java.util.List;
import java.util.Map;

public interface DbIdBaseDAO {

    /**
     * 通用update方法.sql中使用?占位,params的内容要和?匹配
     * 
     * @param dbId
     * @param sql
     * @param clz
     * @param params
     * @return
     */
    <T> List<T> queryBeanList(String dbId, String sql, Class<T> clz, Object... params) throws Exception;

    /**
     * 通用的list查询,只针对简单bean对象.sql中使用?占位,params的内容要和?匹配
     *
     * @param dbId
     * @param sql
     * @param clz
     * @param params
     * @param <T>
     * @return
     */
    int update(String dbId, String sql, Object... params) throws Exception;

    /**
     * 通用insert
     * 
     * @param dbId
     * @param sql
     * @param params
     * @return
     */
    int insert(String dbId, String sql, Object... params) throws Exception;

    /**
     * 通用insert,返回mysql自增的id
     * 
     * @param dbId
     * @param sql
     * @param params
     * @return [0]insert成功行数,[1]insert的自增id
     */
    int[] insertReturnId(String dbId, String sql, Object... params) throws Exception;

    /**
     * 查询得到List[map]
     * 
     * @param dbId
     * @param sql
     * @param params
     * @return
     */
    List<Map<String, Object>> queryMapList(String dbId, String sql, Object... params) throws Exception;

    /**
     * 查询单行记录
     * 
     * @param dbId
     * @param sql
     * @param clz
     * @param params
     * @return
     */
    <T> T queryOne(String dbId, String sql, Class<T> clz, Object... params) throws Exception;

    long queryCount(String dbId, String sql, Object... params) throws Exception;
}
