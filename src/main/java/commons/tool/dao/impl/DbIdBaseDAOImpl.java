package commons.tool.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import commons.tool.bean.DatabaseConfigBean;
import commons.tool.constant.DBConstant;
import commons.tool.dao.DatabaseConfigDAO;
import commons.tool.dao.DbIdBaseDAO;
import commons.tool.utils.ConnUtil;

/**
 * 使用固定datasource(观星台)进行查询
 * 
 * @author xinqian
 *
 */
@Repository
public class DbIdBaseDAOImpl implements DbIdBaseDAO {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DatabaseConfigDAO databaseConfigDao;

    private String HIVE_CONN = "dbid_eq_7";

    private Connection getConn(String dbId) throws IllegalArgumentException {
        if (DBConstant.HIVE_1.equals(dbId)) {
            throw new IllegalArgumentException(HIVE_CONN);
        }
        DatabaseConfigBean config = databaseConfigDao.queryById(dbId);
        String ip = config.getHost();
        String port = config.getPort();
        String dbname = "";
        String username = config.getUsername();
        String password = config.getPassword();
        logger.info("database:{}@{}", config.getUsername(), config.getHost());
        return ConnUtil.getMysqlConn(ip, port, dbname, username, password);
    }

    @Override
    public <T> List<T> queryBeanList(String dbId, String sql, Class<T> clz, Object... params) throws Exception {
        List<T> list = new ArrayList<T>();
        QueryRunner runner = new QueryRunner();
        Connection conn = getConn(dbId);
        try {
            logger.info("sql:" + sql);
            logger.info("params:" + Arrays.deepToString(params));
            list = runner.query(conn, sql, new BeanListHandler<T>(clz), params);
        } catch (SQLException e) {
            logger.error("", e);
            throw new RuntimeException(e);
        } finally {
            DbUtils.closeQuietly(conn);
        }
        return list;
    }

    @Override
    public int update(String dbId, String sql, Object... params) throws Exception {
        QueryRunner runner = new QueryRunner();
        Connection conn = getConn(dbId);
        try {
            logger.info("sql:" + sql);
            logger.info("params:" + Arrays.deepToString(params));
            return runner.update(conn, sql, params);
        } catch (SQLException e) {
            logger.error("", e);
            throw new RuntimeException(e);
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    @Override
    public int insert(String dbId, String sql, Object... params) throws Exception {
        QueryRunner runner = new QueryRunner();
        Connection conn = getConn(dbId);
        try {
            return runner.update(conn, sql, params);
        } catch (SQLException e) {
            logger.error("", e);
            throw new RuntimeException(e);
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    @Override
    public int[] insertReturnId(String dbId, String sql, Object... params) throws Exception {
        QueryRunner runner = new QueryRunner();
        Connection conn = getConn(dbId);
        int[] result = { 0, 0 };
        try {
            logger.info("sql:" + sql);
            logger.info("params:" + Arrays.deepToString(params));
            int n = runner.update(conn, sql, params);
            String idsql = "select LAST_INSERT_ID() LASTINSERTID";
            logger.info(idsql);
            Number lastid = (Number) runner.query(conn, idsql, new MapHandler()).get("LASTINSERTID");
            logger.info("lastid:" + lastid);
            result[0] = n;
            result[1] = lastid.intValue();
        } catch (Exception e) {
            logger.error("", e);
            throw new RuntimeException(e);
        } finally {
            DbUtils.closeQuietly(conn);
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> queryMapList(String dbId, String sql, Object... params) throws Exception {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        QueryRunner runner = new QueryRunner();
        Connection conn = getConn(dbId);
        try {
            logger.info("sql:" + sql);
            logger.info("params:" + Arrays.deepToString(params));
            list = runner.query(conn, sql, new MapListHandler(), params);
        } catch (SQLException e) {
            logger.error("", e);
            throw new RuntimeException(e);
        } finally {
            DbUtils.closeQuietly(conn);
        }
        return list;
    }

    @Override
    public <T> T queryOne(String dbId, String sql, Class<T> clz, Object... params) throws Exception {
        List<T> list = queryBeanList(dbId, sql, clz, params);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        } else {
            if (list.size() == 1) {
                return list.get(0);
            } else {
                throw new RuntimeException("expected 1 row but found " + list.size());
            }
        }
    }

    @Override
    public long queryCount(String dbId, String sql, Object... params) throws Exception {
        QueryRunner runner = new QueryRunner();
        Connection conn = getConn(dbId);
        List<Object[]> list = new ArrayList<>();
        try {
            logger.info("sql:" + sql);
            logger.info("params:" + Arrays.deepToString(params));
            list = runner.query(conn, sql, new ArrayListHandler(), params);
            if (list != null && list.size() == 1) {
                Object o = list.get(0)[0];
                if (o instanceof Number) {
                    return ((Number) o).longValue();
                } else {
                    throw new RuntimeException("result is not a number");
                }
            } else {
                throw new RuntimeException("result rows > 1");
            }
        } catch (SQLException e) {
            logger.error("", e);
            throw new RuntimeException(e);
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

}
