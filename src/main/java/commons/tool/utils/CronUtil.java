package commons.tool.utils;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.SimpleTriggerContext;

public class CronUtil {
    private static Logger logger = LoggerFactory.getLogger(CronUtil.class);

    /**
     * 当前时间是否匹配cron表达式。(cron时间计算出的下一次运行时间)和(当前时间+1m)相等。<br>
     * 要求period的配置的最小轮询间隔是分钟以上
     * 
     * @param rule
     * @return
     */
    public static boolean isPeriodMatch(String period) {
        if (StringUtils.isBlank(period)) {
            return false;
        }

        Trigger trigger = new CronTrigger(period);
        Date scheduleDate = trigger.nextExecutionTime(new SimpleTriggerContext());

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 1);// 加一分钟
        Date now = cal.getTime();

        String pattern = "yyyy-MM-dd HH:mm";
        String dateSchedule = DateUtil.formatDate(scheduleDate, pattern);
        String dateNow = DateUtil.formatDate(now, pattern);
        logger.debug("dateSchedule:{}, dateNow:{}", dateSchedule, dateNow);
        if (dateSchedule.equals(dateNow)) {
            return true;
        } else {
            return false;
        }
    }

}
