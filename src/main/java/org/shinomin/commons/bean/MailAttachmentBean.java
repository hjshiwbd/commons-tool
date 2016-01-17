package org.shinomin.commons.bean;

import java.io.File;

/**
 * 邮件附件
 * 
 * @author hjin
 * 
 */
public class MailAttachmentBean
{
	/**
	 * 附件文件
	 */
	private File file;
	/**
	 * 用于显示的名称
	 */
	private String filename;

	public MailAttachmentBean()
	{

	}

	public MailAttachmentBean(File file, String filename)
	{
		this.file = file;
		this.filename = filename;
	}

	public String getFilename()
	{
		return filename;
	}

	public void setFilename(String filename)
	{
		this.filename = filename;
	}

	public File getFile()
	{
		return file;
	}

	public void setFile(File file)
	{
		this.file = file;
	}
}
