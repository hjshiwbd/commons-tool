package commons.tool.dao.impl;

import commons.tool.bean.CmdRunResultBean;
import commons.tool.bean.QueryResultBean;
import commons.tool.utils.CmdUtil;
import org.apache.commons.exec.ExecuteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HivecmdDAO {
    private static Logger logger = LoggerFactory.getLogger(HivecmdDAO.class);

    public static void main(String[] args) {
        String cmd = "hive -e 'select count(*) from dw.dim_goods_info where 1=1'";
        try {
            getCmdHiveResult(cmd);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public static QueryResultBean queryHive(String sql) throws IOException, InterruptedException {
        logger.info("hql:" + sql);
//        String cmd = "hive -e \"" + sql + ";\"";
        return getCmdHiveResult(sql);
    }

    private static QueryResultBean getCmdHiveResult(String sql) throws IOException, InterruptedException {
        List<Object[]> resultList = new ArrayList<Object[]>();
        String[] param = { "hive", "-e", sql };
        CmdRunResultBean crrb = CmdUtil.runCmd3(param);

        List<String> list = crrb.getRunlogsInput();
        for (String str : list) {
            String datas[] = str.split("\t");
            resultList.add(datas);
        }
        crrb.getRunLogs().addAll(crrb.getRunlogsInput());
        return new QueryResultBean(resultList, crrb.getRunLogs());
    }

}
