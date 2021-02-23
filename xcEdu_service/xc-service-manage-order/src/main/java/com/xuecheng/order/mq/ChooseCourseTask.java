package com.xuecheng.order.mq;

import com.xuecheng.framework.domain.task.XcTask;
import com.xuecheng.order.config.RabbitMQConfig;
import com.xuecheng.order.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * 定时发送选课消息到MQ
 *
 * @author atom
 */
@Slf4j
@Component
public class ChooseCourseTask {

    @Resource
    private TaskService taskService;

    /**
     * 每分钟扫描消息表，发送到MQ
     */
    @Scheduled(fixedDelay = 60 * 1000)
    public void sendChooseCourseTask() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        //取出超过一分钟未处理的任务
        calendar.add(GregorianCalendar.MINUTE, -1);
        Date time = calendar.getTime();
        List<XcTask> taskList = taskService.findTaskList(time, 1000);

        // 发送消息
        taskList.forEach(task -> {
            // 校验版本号，订单服务会集群部署，为了避免在1分钟内重复执行，这里使用乐观锁，实现思路：
            //1。每次取任务时判断当前版本以及任务ID是否匹配，如果匹配则执行任务，如果不匹配则取消执行。
            //2。如果当前版本和任务ID可以匹配到任务则更新当前版本+1

            //处理之前更新版本号
            if (taskService.getTask(task.getId(), task.getVersion()) > 0) {
                taskService.publish(task, task.getMqExchange(), task.getMqRoutingkey());
                log.info("[订单微服务] 发送选课消息到MQ, task id :[{}]", task.getId());
            }
        });
    }

    /**
     * 接收选课响应结果
     */
    @RabbitListener(queues = {RabbitMQConfig.XC_LEARNING_FINISHADDCHOOSECOURSE})
    public void receiveFinishChoosecourseTask(XcTask task) {
        log.info("receiveChoosecourseTask...{}", task.getId());
        //接收到 的消息id
        String taskId = task.getId();
        //删除任务，添加历史任务
        taskService.finishTask(taskId);
    }


}
