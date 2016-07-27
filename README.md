# hibernate-1
1. 简介：orm 实体关系映射--->操作数据库-->完成数据的持久化操作
	1. hibernate：面向对象，全自动的，不需要编写任何的sql语句，hql,学习成本较高的
		1. 对jdbc进行封装
	2. mybatis：半自动化，需要编写sql语句，使用起来比较灵活的
3. 第一程序：
	1. 下载hibernate的资源：http://hibernate.org/orm/downloads/
	2. 添加jar包：解压之后lib文件夹required文件夹中的所有的jar包
	3. 编写hibernate的配置文件：hibernate.cfg.xml(文件名必须为该)

			<!DOCTYPE hibernate-configuration PUBLIC
				"-//Hibernate/Hibernate Configuration DTD 3.0//EN"
				"http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">
			<hibernate-configuration>
			    <!-- 回话工厂 -->
			    <session-factory>
			        <!-- 配置jdbc的属性 -->
			        <property name="hibernate.connection.driver_class">org.gjt.mm.mysql.Driver</property>
			        <property name="hibernate.connection.url">jdbc:mysql://127.0.0.1:3306/hibernate</property>
			        <property name="hibernate.connection.username">admin</property>
			        <property name="hibernate.connection.password">123456</property>
			        <!-- 方言 -->
			        <property name="hibernate.dialect">org.hibernate.dialect.MySQL5Dialect</property>
			        
			        <!-- 映入实体映射文件 -->
			        <mapping resource="com/hibernate/entity/User.hbm.xml"/>
			    </session-factory>
			</hibernate-configuration>
	4. 编写实体类
		
		public class User implements Serializable {

			private static final long serialVersionUID = 1L;
		
			private int id;
			private String userName;
			private String password;
			private String address;
			private String tel;
	5. 编写实体类映射文件：类名.hbm.xml
	
			<!DOCTYPE hibernate-mapping PUBLIC 
			    "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
			    "http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd">
			<hibernate-mapping>
			    <!-- 指名该映射文件对应的实体类
			    	table:数据库中对应的表的名称
			     -->
			    <class name="com.hibernate.entity.User" table="users">
			        <!-- 标识主键的属性 -->
			        <id name="id" column="id" type="int">
			            <!-- 主键生成策略
			            	identity:自增长
			            	uuid：string类型的数据
			             -->
			            <generator class="identity"/>
			        </id>
			        <!-- 普通属性 -->
			        <property name="userName" column="userName" length="20" type="string"/>
			        <property name="password" column="password" length="20" type="string"/>
			        <property name="address" column="address" length="20" type="string"/>
			        <property name="tel" column="tel" length="20" type="string"/>
			        <property name="number" column="number" type="string"/>
			        <property name="note">
			            <!-- 
			            	column属性和colum节点不能同时使用
			            	sql-teyp：数据库中的数据类型
			             -->
			            <column name="note" sql-type="varchar(600)"/>
			        </property>
			    </class>
			</hibernate-mapping>  
	6. hibernate的配置文件中注入实体映射文件
	7. 测试代码

			//1、获取configuration对象
			Configuration configuration=new Configuration().configure();
			//2、获取sessionFactory对象
			SessionFactory sessionFactory = configuration.buildSessionFactory();
			//3、获取session对象
			Session session = sessionFactory.openSession();
			//4、开启事务
			Transaction tx = session.beginTransaction();
			Student student=new Student();
			student.setName("上");
			session.save(student);
			//5、提交事务
			tx.commit();
			//6、关闭资源
			session.close();
			sessionFactory.close();
3. hibernate中对象的状态--->session有关
	1. 游离态：新创建出来的对象，数据库中不存在并且和session没有关联

			User user=new User();
	2. 持久态：数据库存在并且和session相关联，当该对象的某个属性变化了，会直接影响数据库中的数据

			Session session = sessionFactory.openSession();
			//4、开启事务
			Transaction tx = session.beginTransaction();
			Student student=new Student();
			student.setName("张三");
			
			session.save(student);
			
			//执行update语句
			student.setName("李四");
	3. 挂起：数据库中存在数据，但是和session没有关联
4. session中常用的方法
	1. save、persist:保存数据
	2. delete:根据id删除删除
	3. update：更新条件是为id
	3. get: 根据id查，会立刻访问数据库
	4. load: 根据id查，返回的是代理、不会立即访问数据库
	6. saveorupdate、merge(根据id和version的值来确定是save或update),调用merge
	7. 注意事项：
		1. get方法存在懒加载的问题：
			1. 显示反问该集合数据
			2. 配置文件中set 的lazy属性该false
7. hibernate.hbm2ddl.auto
	1. create-drop:先删除表，创建表，执行操作，再删除表
	2. create:先删除表，创建表，执行操作，不删除表
	3. update:执行表的结构更新操作，不删除表--->开发的时候较多
	4. validate:只执行表结构校验------------>上线的时候较多
