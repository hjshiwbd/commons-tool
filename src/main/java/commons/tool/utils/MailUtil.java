package commons.tool.utils;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.sun.mail.imap.IMAPStore;

import commons.tool.bean.MailAttachmentBean;

/**
 * 邮件工具类
 * 
 * @author hjin
 * @cratedate 2013-9-20 下午5:13:29
 * 
 */
public class MailUtil {
    /**
     * 发送邮件,to单人,无附件
     * 
     * @author hjin
     * @param displayName
     *            发件人名字,用于显示
     * @param mailBoxTo
     *            收件人邮箱地址
     * @param title
     *            邮件标题
     * @param content
     *            邮件内容,支持html标签
     * @param mailServer
     *            邮件服务器(smtp)
     * @param mailAccount
     *            发件人邮件地址
     * @param mailPassword
     *            发件人邮件密码
     * @throws Exception
     * 
     */
    public static void send(String displayName, String mailBoxTo, String title, String content, String mailServer,
            String mailAccount, String mailPassword) throws Exception {
        MailAttachmentBean[] files = null;
        send(displayName, new String[] { mailBoxTo }, null, title, content, mailServer, mailAccount, mailPassword,
                files);
    }

    /**
     * 发送邮件,to多人,cc多人,无附件
     * 
     * @param displayName
     * @param mailBoxTo
     * @param mailBoxCC
     * @param title
     * @param content
     * @param mailServer
     * @param mailAccount
     * @param mailPassword
     * @throws Exception
     */
    public static void send(String displayName, String[] mailBoxTo, String[] mailBoxCC, String title, String content,
            String mailServer, String mailAccount, String mailPassword) throws Exception {
        MailAttachmentBean[] files = null;
        send(displayName, mailBoxTo, mailBoxCC, title, content, mailServer, mailAccount, mailPassword, files);
    }

    /**
     * 发送邮件
     * 
     * @param displayName
     *            发件人名字,用于显示
     * @param mailBoxTo
     *            收件人邮箱地址
     * @param mailBoxesCC
     *            抄送人地址
     * @param title
     *            邮件标题
     * @param content
     *            邮件内容,支持html标签
     * @param mailServer
     *            邮件服务器(smtp)
     * @param mailAccount
     *            发件人邮件地址
     * @param mailPassword
     *            发件人邮件密码
     * @param attachFiles
     *            附件
     */
    public static void send(String displayName, String[] mailBoxesTo, String[] mailBoxesCC, String title,
            String content, String mailServer, String mailAccount, String mailPassword,
            MailAttachmentBean[] attachFiles) throws Exception {
        // str_content="<a href='www.163.com'>html元素</a>"; //for testing send
        // html mail!
        // 建立邮件会话
        Properties props = new Properties(); // 用来在一个文件中存储键-值对的，其中键和值是用等号分隔的，
        // 存储发送邮件服务器的信息
        props.put("mail.smtp.host", mailServer);
        // 同时通过验证
        props.put("mail.smtp.auth", "true");
        // 根据属性新建一个邮件会话
        Session s = Session.getInstance(props);
        // s.setDebug(true); // 打印调试信息。
        s.setDebug(false);

        // 由邮件会话新建一个消息对象
        MimeMessage message = new MimeMessage(s);

        // 设置邮件
        displayName = MimeUtility.encodeText(displayName);
        InternetAddress from = new InternetAddress(displayName + " <" + mailAccount + ">"); // xxx@163.com
        message.setFrom(from); // 设置发件人的地址

        // 设置收件人和抄送人
        setTOAndCC(message, mailBoxesTo, mailBoxesCC);

        // 设置标题
        message.setSubject(title);

        // 设置信件内容
        Multipart multipart = new MimeMultipart();
        BodyPart contentPart = new MimeBodyPart();
        contentPart.setContent(content, "text/html;charset=gbk");
        multipart.addBodyPart(contentPart);

        // 添加附件
        if (attachFiles != null) {
            for (MailAttachmentBean attachment : attachFiles) {
                if (attachment.getFile().exists()) {
                    BodyPart messageBodyPart = new MimeBodyPart();
                    String affix = attachment.getFile().getAbsolutePath();
                    String affixName = attachment.getFilename();
                    DataSource source = new FileDataSource(affix);
                    // 添加附件的内容
                    messageBodyPart.setDataHandler(new DataHandler(source));
                    // 添加附件的标题
                    messageBodyPart.setFileName(MimeUtility.encodeText(affixName));
                    multipart.addBodyPart(messageBodyPart);
                }
            }
        }
        message.setContent(multipart);

        // 存储邮件信息
        message.saveChanges();

        // 发送邮件
        Transport transport = s.getTransport("smtp");
        // 以smtp方式登录邮箱,第一个参数是发送邮件用的邮件服务器SMTP地址,第二个参数为用户名,第三个参数为密码
        transport.connect(mailServer, mailAccount, mailPassword);
        // 发送邮件,其中第二个参数是所有已设好的收件人地址
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    private static void setTOAndCC(MimeMessage message, String[] mailBoxesTo, String[] mailBoxesCC)
            throws MessagingException {
        if (mailBoxesTo == null || mailBoxesTo.length == 0) {
            throw new RuntimeException("mail receiver is empty");
        }
        Address[] a1 = new InternetAddress[mailBoxesTo.length];
        for (int i = 0; i < mailBoxesTo.length; i++) {
            String mail = mailBoxesTo[i];
            Address address = new InternetAddress(mail);
            a1[i] = address;
        }
        message.setRecipients(Message.RecipientType.TO, a1);

        if (mailBoxesCC != null && mailBoxesCC.length > 0) {
            Address[] a2 = new InternetAddress[mailBoxesCC.length];
            for (int i = 0; i < mailBoxesCC.length; i++) {
                String mail = mailBoxesCC[i];
                Address address = new InternetAddress(mail);
                a2[i] = address;
            }
            message.setRecipients(Message.RecipientType.CC, a2);
        }
    }

    /**
     * 使用pop3方式连接邮箱
     * 
     * @return
     * @throws Exception
     */
    public static Store connectMail(String servername, String username, String password) throws Exception {
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "pop3");
        props.setProperty("mail.pop3.host", servername);
        Session session = Session.getDefaultInstance(props);
        Store store = session.getStore("pop3");
        store.connect(username, password);
        return store;
    }

    /**
     * 使用imap方式连接邮箱
     * 
     * @param imapserver
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    public static Store connectIMAPMail(String imapserver, String username, String password) throws Exception {
        Properties props = System.getProperties();
        props.setProperty("mail.imap.port", "143");

        // 获取默认会话
        Properties prop = System.getProperties();
        prop.put("mail.imap.host", imapserver);

        prop.put("mail.imap.auth.plain.disable", "true");
        Session mailsession = Session.getInstance(prop, null);
        mailsession.setDebug(false); // 是否启用debug模式
        IMAPStore store = null;
        store = (IMAPStore) mailsession.getStore("imap"); // 使用imap会话机制，连接服务器
        store.connect(imapserver, 143, username, password);
        return store;
    }
}
