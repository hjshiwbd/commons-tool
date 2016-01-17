package org.shinomin.commons.web.service;

import java.io.File;
import java.io.FileReader;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.shinomin.commons.web.bean.SystemConfigBean;
import org.shinomin.commons.web.cache.impl.ContextInitCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class ContextInitService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private ApplicationContext ctx;

    public ContextInitService(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    public void loadAllProperies(String folder) {
        String location = ctx.getClassLoader().getResource("").getPath();
        File loader = new File(location);

        String classPath = loader.getParentFile().getAbsolutePath() + "/classes";
        logger.info("class path:{}", classPath);
        File root = new File(classPath);
        File[] rootChildren = root.listFiles();
        for (File child : rootChildren) {
            readFile(child);
        }
    }

    /**
     * 递归
     * 
     * @param file
     */
    private void readFile(File file) {
        if (file.isDirectory()) {
            // 文件夹
            for (File child : file.listFiles()) {
                readFile(child);
            }

        } else {
            if (file.getName().endsWith(".properties")) {
                // 属性文件
                readProperties(file);
            } else {
                // 其他类型,暂不处理

            }
        }
    }

    /**
     * 读取properties
     * 
     * @param child
     */
    @SuppressWarnings("rawtypes")
    private void readProperties(File child) {
        Properties prop = new Properties();
        try {
            prop.load(new FileReader(new File(child.getAbsolutePath())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Enumeration names = prop.propertyNames();
        while (names.hasMoreElements()) {
            String key = (String) names.nextElement();
            String value = prop.getProperty(key);
            if (ContextInitCache.getInstance().getCachedData(key) != null) {
                logger.warn("properties dupicated key found:{}", key);
            }
            ContextInitCache.getInstance().cache(key, value);
            logger.info(String.format("key:%s, value:%s", key, value));
        }
    }

    public void loadSystemConfig(String serviceCode) {
        SystemconfigService service = ctx.getBean(SystemconfigService.class);
        SystemConfigBean search = new SystemConfigBean();
        search.setServicecode(serviceCode);
        List<SystemConfigBean> list = service.getList(search);
        logger.info("systemconfig size:{}", list.size());
        for (SystemConfigBean bean : list) {
            ContextInitCache.getInstance().cache(bean.getConfcode(), bean);
        }
    }
}
