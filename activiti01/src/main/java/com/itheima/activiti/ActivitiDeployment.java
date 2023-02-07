package com.itheima.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;

import java.io.InputStream;
import java.util.zip.ZipInputStream;

/**
 * 流程定义的部署:
 * 1. 什么是流程定义部署
 *  将线下定义的流程部署到 activiti 数据库中，这就是流程定义部署
 * 2. 如何部署
 * a.通过调用 activiti 的 api 将流程定义的 bpmn 和 png 两个文件一个一个添加部署到 activiti 中，
 * b.也可以将两个文件打成 zip 包进行部署
 *
 * 3.部署成功后，定义的流程图数据会存入到数据库中
 * SELECT * FROM act_re_deployment #流程定义部署表，记录流程部署信息
 *
 *
 * activiti表有哪些？
 *  act_re_deployment  部署信息
    act_re_procdef     流程定义的一些信息
    act_ge_bytearray   流程定义的bpmn文件及png文件

 Activiti数据库表的命名规则：
    Activiti 的表都以 ACT_开头。 第二部分是表示表的用途的两个字母标识。 用途也和服务的 API 对
 应。
 ACT_RE_*: 'RE'表示 repository。 这个前缀的表包含了流程定义和流程静态资源 （图片， 规则，等等）。
 ACT_RU_*: 'RU'表示 runtime。 这些运行时的表，包含流程实例，任务，变量，异步任务， 等运行中的数据。
            Activiti 只在流程实例执行过程中保存这些数据， 在流程结束时就会删 除这些记录。 这样运行时表可以一直很小速度很快。
 ACT_HI_*: 'HI'表示 history。 这些表包含历史数据，比如历史流程实例， 变量，任务等等。
 ACT_GE_*: GE 表示 general。通用数据， 用于不同场景下
 */
public class ActivitiDeployment {

    public static void main(String[] args) {
//        deployment2( );
        singleFileDeployment();
    }

    /**
     * @Description: 流程定义部署  流程制作出来后要上传到服务器 zip文件更便于上传
     * @Param: []
     * @Return: void
     * @Author: tanggd
     * @Date: 2023/2/8 0:11
     */
    private static void deployment2()
    {
        //1.创建ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        //2.得到RepositoryService实例
        RepositoryService repositoryService = processEngine.getRepositoryService();

        //3.转化出ZipInputStream流对象
        InputStream is = ActivitiDeployment.class.getClassLoader().getResourceAsStream("diagram/holidayBPMN.zip");

        //将 inputstream流转化为ZipInputStream流
        ZipInputStream zipInputStream = new ZipInputStream(is);

        //3.进行部署
        Deployment deployment = repositoryService.createDeployment()
                .addZipInputStream(zipInputStream)
                .name("请假申请单流程")
                .deploy();

        //4.输出部署的一些信息
        System.out.println(deployment.getName());
        System.out.println(deployment.getId());
    }

    //流程定义部署
//    public static void main(String[] args) {
//        singleFileDeployment( );
//    }

    /**
     * @Description: 单个文件部署方式 ：分别将 bpmn 文件和 png图片文件部署。     *
     * : SELECT * FROM act_re_deployment #流程定义部署表，记录流程部署信息
     * @Param: []
     * @Return: void
     * @Author: tanggd
     * @Date: 2023/2/7 23:38
     */
    private static void singleFileDeployment()
    {
        //1.创建ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        //2.得到RepositoryService实例
        RepositoryService repositoryService = processEngine.getRepositoryService();

        //3.进行部署
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("diagram/holiday.bpmn")  //添加bpmn资源
                .addClasspathResource("diagram/holiday.png")
                .name("请假申请单流程")
                .deploy();

        //4.输出部署的一些信息
        System.out.println(deployment.getName());
        System.out.println(deployment.getId());
    }
}
