package com.example.demo;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(classes = Acti7DemoApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class Acti7DemoApplicationTests {

	private ProcessEngine processEngine;

	@Before
	public void before() {
		processEngine = ProcessEngines.getDefaultProcessEngine();
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void testDeploy() {
		Deployment deployment = processEngine.getRepositoryService()
				.createDeployment()
				.name("请假流程")
				.addClasspathResource("static/Vacation.bpmn")
				.addClasspathResource("static/Vacation.png")
				.deploy();
		System.err.println(deployment.getId());
	}

	@Test
	public void testStartProcess() {
		Map<String, Object> var = new HashMap<>();
		var.put("userId", "小王");
		ProcessInstance processInstance = processEngine.getRuntimeService()
				.startProcessInstanceByKey("vacation", var);
		System.err.println(processInstance.getId());
	}

	@Test
	public void testCompletedTask() {
		Task task = processEngine.getTaskService()
				.createTaskQuery()
				.taskAssignee("李总")
				.singleResult();
		System.err.println(task.getId());

		Map<String, Object> var = new HashMap<>();
		// var.put("bossId", "李总");
		processEngine.getTaskService().complete(task.getId(), var);
	}
}
