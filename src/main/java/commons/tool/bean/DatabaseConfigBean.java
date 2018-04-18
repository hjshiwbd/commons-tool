package commons.tool.bean;

public class DatabaseConfigBean {
    private String id;//
    private String host;// 服务器IP地址或者域名地址
    private String port;// 端口号
    private String username;// 登陆数据库服务器的用户名
    private String password;// 登陆数据库服务器的密码
    private String connection_url;// 数据库连接字符串
    private String db_type;// 0 mysql;
    private String is_slave;// 0 主库， 1 从库
    private String db_name;//
    private String remark;//
    private String last_modified;//

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConnection_url() {
        return connection_url;
    }

    public void setConnection_url(String connection_url) {
        this.connection_url = connection_url;
    }

    public String getDb_type() {
        return db_type;
    }

    public void setDb_type(String db_type) {
        this.db_type = db_type;
    }

    public String getIs_slave() {
        return is_slave;
    }

    public void setIs_slave(String is_slave) {
        this.is_slave = is_slave;
    }

    public String getDb_name() {
        return db_name;
    }

    public void setDb_name(String db_name) {
        this.db_name = db_name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLast_modified() {
        return last_modified;
    }

    public void setLast_modified(String last_modified) {
        this.last_modified = last_modified;
    }

}
