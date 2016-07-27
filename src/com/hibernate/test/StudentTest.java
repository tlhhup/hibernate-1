package com.hibernate.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.procedure.ProcedureCall;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hibernate.entity.Student;

@SuppressWarnings("deprecation")
public class StudentTest {

	private SessionFactory sessionFactory;
	private Session session;

	@Before
	public void before() {
		// 1、获取configuration对象
		Configuration configuration = new Configuration().configure();
		// 2、获取sessionFactory对象
		sessionFactory = configuration.buildSessionFactory();
	}

	@After
	public void after() {
		// 6、关闭资源
		if (session != null) {
			session.close();
		}
		if (sessionFactory != null) {
			sessionFactory.close();
		}
	}

	@Test
	public void save() {
		session = sessionFactory.openSession();
		// 4、开启事务
		Transaction tx = session.beginTransaction();
		Student student = new Student();
		student.setName("张三");

		session.save(student);

		student.setName("李四");

		// 5、提交事务
		tx.commit();
	}

	@Test
	public void delete() {
		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		Student student = new Student();
		student.setId("297ea7a1562b265e01562b26623b0000");
		session.delete(student);
		
		tx.commit();
	}
	
	@Test
	public void update() {
		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		Student student = new Student();
		student.setId("297ea7a1562b1d8701562b1d8a560000");
		student.setName("发打发士大夫");
		session.update(student);
		
		
		tx.commit();
	}
	
	@Test
	public void get() {
		session = sessionFactory.openSession();
		
		//马上查询数据-->执行select语句
		Student student = (Student) session.get(Student.class, "297ea7a1562b1d8701562b1d8a560000");
		//System.out.println(student);
	}
	
	@Test
	public void load() {
		session = sessionFactory.openSession();
		
		//返回的是一个代理对象-->马上执行select语句
		Student student = (Student) session.load(Student.class, "297ea7a1562b1d8701562b1d8a560000");
		//必须显示的访问其中的某个属性
		student.getName();
//		System.out.println(student);
	}
	
	@Test
	public void saveOrUpdate(){
		session=sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		Student student=new Student();
		student.setId("297ea7a1562b331601562b331a6d0000");
		student.setName("发结束节点放假啊介绍的饭卡收到回复");
		session.saveOrUpdate(student);
		
		tx.commit();
	}

}
