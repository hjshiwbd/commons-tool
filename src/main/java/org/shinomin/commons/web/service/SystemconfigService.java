package org.shinomin.commons.web.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.shinomin.commons.web.bean.SystemConfigBean;
import org.shinomin.commons.web.util.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SystemconfigService
{
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private Connector connector;

	public List<SystemConfigBean> getList(SystemConfigBean search)
	{
		Connection conn = connector.getConn();

		String sql = createSql(search);
		List<SystemConfigBean> list = new ArrayList<SystemConfigBean>();
		try
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			setParam(ps, search);
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				SystemConfigBean bean = new SystemConfigBean();
				bean.setConfcode(rs.getString("confcode"));
				bean.setConfdesc(rs.getString("confdesc"));
				bean.setConfgroup(rs.getString("confgroup"));
				bean.setConfname(rs.getString("confname"));
				bean.setServicecode(rs.getString("servicecode"));
				bean.setConfvalue(rs.getString("confvalue"));
				bean.setConfstatus(rs.getString("confstatus"));
				list.add(bean);
			}
		}
		catch (SQLException e)
		{
			logger.error(e.getMessage(),e);
		}

		return list;
	}

	private void setParam(PreparedStatement ps, SystemConfigBean search) throws SQLException
	{
		if (search.getServicecode() != null)
		{
			ps.setString(1, search.getServicecode());
		}
	}

	private String createSql(SystemConfigBean search)
	{
		String sql = "select * from system_config where 1=1";
		if (search.getServicecode() != null)
		{
			sql += " and servicecode=? ";
		}
		return sql;
	}

}
