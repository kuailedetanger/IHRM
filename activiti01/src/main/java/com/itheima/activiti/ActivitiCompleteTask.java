package com.itheima.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;

import java.util.Objects;

/**
 * 处理当前用户的任务
 * 背后操作的表：
 *   act_hi_actinst
     act_hi_identitylink
     act_hi_taskinst
     act_ru_identitylink
     act_ru_task
 */
public class ActivitiCompleteTask {

    public static void main(String[] args) {
//        wangWuCompleteTask( );
        zhangsanCompleteTask();

    }

    /**
     * @Description:
     * @Param: 查询当前用户wangwu的任务并处理掉
     * @Return: void
     * @Author: tanggd
     * @Date: 2023/2/8 0:40
     */
    private static void wangWuCompleteTask()
    {
        //1.得到ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        //2.得到TaskService对象
        TaskService taskService = processEngine.getTaskService();

        //3.查询当前用户的任务
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("holiday")
                .taskAssignee("wangwu")
                .singleResult();

        //4.处理任务,结合当前用户任务列表的查询操作的话,任务ID:task.getId()
        if(Objects.nonNull(task )){
            taskService.complete(task.getId());
            //5.输出任务的id
            System.out.println(task.getId());
        }
    }

    /**
     * @Description: lishi完成自己的任务
     * @Param: []
     * @Return: void
     * @Author: tanggd
     * @Date: 2023/2/8 0:40
     */
    private static void lisiCompleteTask()
    {
        //1.得到ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        //2.得到TaskService对象
        TaskService taskService = processEngine.getTaskService();

        //3.处理任务,结合当前用户任务列表的查询操作的话,任务ID:5002
        taskService.complete("5002");
    }

    /**
     * @Description: zhangsan完成自己的任务
     * @Param: []
     * @Return: void
     * @Author: tanggd
     * @Date: 2023/2/8 0:39
     */
    private static void zhangsanCompleteTask()
    {
        //1.得到ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        //2.得到TaskService对象
        TaskService taskService = processEngine.getTaskService();

        //3.处理任务,结合当前用户任务列表的查询操作的话,任务ID:2505
        taskService.complete("2505");
    }
}
