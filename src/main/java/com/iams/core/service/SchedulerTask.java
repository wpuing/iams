package com.iams.core.service;

import com.iams.core.pojo.Assignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Wei yz
 * @ClassName: SchedulerTask
 * @Description: 定时任务
 * @date 2021/6/8 13:55
 */
@Component
public class SchedulerTask {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private StudentTaskService studentTaskService;

    @Autowired
    private MailService mailService;

    /**
     * 开启定时任务
     */
    @Scheduled(fixedRate = 30*1000)//30秒钟
    public void process() throws InterruptedException {
        Date now = new Date();//获得当前时间的范围
        String date1 = dateFormat.format(now);//当前时间
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, 30);//30分钟后的时间
        String date2 = dateFormat.format(nowTime.getTime());//格式化
        System.out.println("正在扫描作业的定时时间，当前时间为："+date1+"  ,30分钟后："+date2);
        //查询出在时间范围内的作业定时时间
        List<Assignment> list = assignmentService.find(date1, date2);
            if(!CollectionUtils.isEmpty(list)){//不为空
                for(Assignment assignment:list){ //执行发送
                    List<String> emails = studentTaskService.findEmails(assignment.getId());//得到作业的id查询出学生的邮箱
                    if(!CollectionUtils.isEmpty(emails) && !ObjectUtils.isEmpty(assignment)){//不为空
                        for(String email:emails){ //批量发送邮件
                            String title = email+" 用户您好,来自网络作业管理系统的定时任务的温馨提示：该做作业了";
                            String context = "hello "+email+" 用户 ,您的作业：" + assignment.getTitle()+"  已到账！";
                            mailService.sendSimpleMail(email, title, context);
                            Thread.sleep(2 * 1000); //设置暂停的时间 2 秒
                        }
                    }
                    assignmentService.update(assignment.setIsSend(1)); //修改作业的是否发送
                }
            }
    }


}
