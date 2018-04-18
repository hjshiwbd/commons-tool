package commons.tool.utils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * 替换字符串中的占位符.如"您的信息是{name},性别{sex}",会执行object中的getName和getSex方法,替换{xxx} 的内容
 * 
 * @author hjin
 * 
 */
public class StringFormatter {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private String src;
    private Object object;
    private String regex;

    /**
     * 替换字符串中的占位符.如"您的信息是{name},性别{sex}",会执行object中的getName和getSex方法,替换{xxx} 的内容
     * 
     * @param src
     *            原字符串
     * @param object
     *            用于获取属性的对象(Object或者Map)
     */
    public StringFormatter(String src, Object object) {
        this.src = src;
        this.object = object;
    }

    public String format() {
        if (StringUtils.isBlank(src)) {
            logger.info("src is blank");
            return null;
        }
        if (object == null) {
            logger.info("object is null");
            return null;
        }

        Pattern pattern = Pattern.compile(getRegex());
        Matcher matcher = pattern.matcher(src);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            String group = matcher.group();
            String key = invokeKey(group);
            String value = getValue(object, key);
            if (logger.isDebugEnabled()) {
                logger.debug("key:{}, value:{}", key, value);
            }
            matcher.appendReplacement(buffer, value);
        }
        matcher.appendTail(buffer);

        return buffer.toString();
    }

    /**
     * 获取关键字
     * 
     * @param group
     * @return
     * @author hjin
     */
    private String invokeKey(String group) {
        String regex = "[a-zA-Z0-9\\[\\]]\\S*[a-zA-Z0-9\\[\\]]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(group);
        String s = "";
        while (matcher.find()) {
            s += matcher.group();
        }
        return s;
    }

    @SuppressWarnings("rawtypes")
    private String getValue(Object obj, String key) {
        Object o = null;
        if (obj instanceof Map) {
            Map map = (Map) obj;
            int indexOfFirstDot = key.indexOf(".");
            if (indexOfFirstDot != -1) {
                String objname = key.substring(0, indexOfFirstDot);
                Object rootObject = map.get(objname);
                o = parseExpression(rootObject, key.substring(indexOfFirstDot + 1));
            } else {
                o = map.get(key);
            }

        } else {
            // o = ReflectUtil.invokeGetMethod(obj, key);
            o = parseExpression(obj, key);
        }
        return StringUtil.null2Empty(ConvertUtil.objToString(o));
    }

    private Object parseExpression(Object rootObject, String expression) {
        if (rootObject == null) {
            return "";
        }
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression(expression);
        Object o = exp.getValue(rootObject);
        return o;
    }

    public String getRegex() {
        if (regex == null) {
            regex = "\\{\\S*?\\}";
        }
        return regex;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public static void main(String[] args) {

    }

}
