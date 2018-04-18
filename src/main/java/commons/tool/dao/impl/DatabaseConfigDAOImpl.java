package commons.tool.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import commons.tool.bean.DatabaseConfigBean;
import commons.tool.dao.DatabaseConfigDAO;
import commons.tool.dao.GxtBaseDAO;

@Repository
public class DatabaseConfigDAOImpl implements DatabaseConfigDAO {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private GxtBaseDAO gxtDAO;

    @Override
    public DatabaseConfigBean queryById(Object... params) {
        String sql = "select * from config.database_config where id = ?";
        DatabaseConfigBean config = null;
        try {
            config = gxtDAO.queryOne(sql, DatabaseConfigBean.class, params);
            return config;
        } catch (Exception e) {
            logger.info("", e);
            return null;
        }
    }

}
