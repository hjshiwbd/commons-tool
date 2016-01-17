package org.shinomin.commons.web.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SystemConfigBean implements Serializable
{
	private String confcode;// 参数编码
	private String confname;// 参数名称
	private String confgroup;// 参数分组
	private String confvalue;// 参数值
	private String confdesc;// 参数描述
	private String servicecode;// 参数所属应用
	private String confstatus;// 状态,0停用1可用

	public String getConfcode()
	{
		return confcode;
	}

	public void setConfcode(String confcode)
	{
		this.confcode = confcode;
	}

	public String getConfname()
	{
		return confname;
	}

	public void setConfname(String confname)
	{
		this.confname = confname;
	}

	public String getConfgroup()
	{
		return confgroup;
	}

	public void setConfgroup(String confgroup)
	{
		this.confgroup = confgroup;
	}

	public String getConfvalue()
	{
		return confvalue;
	}

	public void setConfvalue(String confvalue)
	{
		this.confvalue = confvalue;
	}

	public String getConfdesc()
	{
		return confdesc;
	}

	public void setConfdesc(String confdesc)
	{
		this.confdesc = confdesc;
	}

	public String getServicecode()
	{
		return servicecode;
	}

	public void setServicecode(String servicecode)
	{
		this.servicecode = servicecode;
	}

	public String getConfstatus()
	{
		return confstatus;
	}

	public void setConfstatus(String confstatus)
	{
		this.confstatus = confstatus;
	}

}