package commons.tool.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import commons.tool.dao.GxtBaseDAO;

/**
 * 使用固定datasource(观星台)进行查询
 * 
 * @author xinqian
 *
 */
public class GxtBaseDAOImpl implements GxtBaseDAO {
    private Logger logger = Logger.getLogger(getClass().getName());

    private DataSource dataSource;

    @Override
    public <T> List<T> queryBeanList(String sql, Class<T> clz, Object... params) {
        List<T> list = new ArrayList<T>();
        QueryRunner runner = new QueryRunner(dataSource);
        try {
            logger.info("sql:" + sql);
            logger.info("params:" + Arrays.deepToString(params));
            list = runner.query(sql, new BeanListHandler<T>(clz), params);
        } catch (SQLException e) {
            logger.error("", e);
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public int update(String sql, Object... params) {
        QueryRunner runner = new QueryRunner();
        try {
            logger.info("sql:" + sql);
            logger.info("params:" + Arrays.deepToString(params));
            return runner.update(sql, params);
        } catch (SQLException e) {
            logger.error("", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public int insert(String sql, Object... params) {
        QueryRunner runner = new QueryRunner();
        try {
            return runner.update(sql, params);
        } catch (SQLException e) {
            logger.error("", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public int[] insertReturnId(String sql, Object... params) {
        QueryRunner runner = new QueryRunner();
        Connection conn = null;
        int[] result = { 0, 0 };
        try {
            conn = dataSource.getConnection();
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
    public List<Map<String, Object>> queryMapList(String sql, Object... params) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        QueryRunner runner = new QueryRunner();
        try {
            logger.info("sql:" + sql);
            logger.info("params:" + Arrays.deepToString(params));
            list = runner.query(sql, new MapListHandler(), params);
        } catch (SQLException e) {
            logger.error("", e);
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<Object[]> queryArrayList(String sql, Object... params) {
        List<Object[]> list = new ArrayList<Object[]>();
        QueryRunner runner = new QueryRunner(dataSource);
        try {
            logger.info("sql:" + sql);
            logger.info("params:" + Arrays.deepToString(params));
            list = runner.query(sql, new ArrayListHandler(), params);
        } catch (SQLException e) {
            logger.error("", e);
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public <T> T queryOne(String sql, Class<T> clz, Object... params) {
        List<T> list = queryBeanList(sql, clz, params);
        if (CollectionUtils.isEmpty(list)) {
            throw new RuntimeException("expected 1 row but found none");
        } else {
            if (list.size() == 1) {
                return list.get(0);
            } else {
                throw new RuntimeException("expected 1 row but found " + list.size());
            }
        }
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

}
