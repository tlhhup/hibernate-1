package com.hibernate.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import com.hibernate.entity.User;

public class UserTest {

	@SuppressWarnings("deprecation")
	@Test
	public void save(){
		//1、获取configuration对象
		Configuration configuration=new Configuration().configure();
		//2、获取sessionFactory对象
		SessionFactory sessionFactory = configuration.buildSessionFactory();
		//3、获取session对象
		Session session = sessionFactory.openSession();
		//4、开启事务
		Transaction tx = session.beginTransaction();
		User user=new User();
		user.setAddress("承诺孤独");
		user.setUserName("张三");
		session.save(user);
		//5、提交事务
		tx.commit();
		//6、关闭资源
		session.close();
		sessionFactory.close();
		System.out.println(user);
	}
	
}
