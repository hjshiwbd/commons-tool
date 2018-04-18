package commons.tool.dao;

import commons.tool.bean.DatabaseConfigBean;

public interface DatabaseConfigDAO {
    DatabaseConfigBean queryById(Object... params);
}
