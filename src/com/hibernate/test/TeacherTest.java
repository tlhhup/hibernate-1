package com.hibernate.test;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import com.hibernate.entity.Student;
import com.hibernate.entity.Teacher;

public class TeacherTest {

	@SuppressWarnings("deprecation")
	@Test
	public void save(){
		Configuration configuration=new Configuration().configure();
		SessionFactory sessionFactory = configuration.buildSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		Teacher teacher=new Teacher();
		teacher.setName("王五");
		
		Set<Student> students=new HashSet<Student>();
		//该数据在数据库中存在
		Student student=new Student();
		student.setId("297ea7a1562b3d8401562b3d877a0000");
		
		students.add(student);
		
		student=new Student();
		student.setId("297ea7a1562b3bfb01562b3bfe1e0000");
		students.add(student);
		
		//绑定关系
		teacher.setStudents(students);
		
		session.save(teacher);
		
		tx.commit();
		session.close();
		sessionFactory.close();
	}
	
	@Test
	public void get(){
		Configuration configuration=new Configuration().configure();
		SessionFactory sessionFactory = configuration.buildSessionFactory();
		Session session = sessionFactory.openSession();
		
		//get出现懒加载问题
		Teacher teacher = (Teacher) session.get(Teacher.class, 4);
		
		//触发第二次加载-->获取该教师的学生信息
//		teacher.getStudents().size();
		
		session.close();
		sessionFactory.close();
		
		System.out.println(teacher);
	}
	
}
