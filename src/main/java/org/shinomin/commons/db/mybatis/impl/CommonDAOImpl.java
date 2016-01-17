package org.shinomin.commons.db.mybatis.impl;

import java.util.List;

import org.shinomin.commons.db.mybatis.ICommonDAO;
import org.shinomin.commons.db.mybatis.ICommonDAOsuport;
import org.shinomin.commons.db.mybatis.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class CommonDAOImpl implements ICommonDAO
{
	@Autowired
	@Qualifier("daoSupport")
	protected ICommonDAOsuport mysqlDAOsuport;

	public void setMysqlDAOsuport(ICommonDAOsuport mysqlDAOsuport)
	{
		this.mysqlDAOsuport = mysqlDAOsuport;
	}

	@Override
	public int insert(Object param)
	{
		return mysqlDAOsuport.insert(getNamespace() + "insert", param);
	}

	@Override
	public int delete(Object param)
	{
		return mysqlDAOsuport.delete(getNamespace() + "delete", param);
	}

	@Override
	public int update(Object param)
	{
		return mysqlDAOsuport.update(getNamespace() + "update", param);
	}

	@Override
	public <E> List<E> selectList(Object param)
	{
		return mysqlDAOsuport.selectList(getNamespace() + "select", param);
	}

	@Override
	public <T> T selectOne(Object param)
	{
		return mysqlDAOsuport.selectOne(getNamespace() + "select", param);
	}

	@Override
	public <E> Pager<E> selectPage(Object param, Pager<E> pager)
	{
		return mysqlDAOsuport.selectPage(getNamespace() + "select", param, pager);
	}

	@Override
	public int selectCount(String table, String where, Object... param)
	{
		return mysqlDAOsuport.selectCount(table, where, param);
	}

}
