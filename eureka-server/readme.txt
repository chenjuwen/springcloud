Eureka服务注册中心：
	启动类：
		@EnableEurekaServer
		@SpringBootApplication
		public class Main {
			public static void main(String[] args) {
				SpringApplication.run(Main.class, args);
			}
		}
	配置文件（单点）：
		spring.application.name=eureka-server-1
		server.port=7001
		eureka.instance.hostname=localhost
		eureka.client.register-with-eureka=false
		eureka.client.fetch-registry=false
		eureka.client.service-url.defaultZone=http://${eureka.instance.hostname}:${server.port}/eureka/
		

Eureka通过互相注册的方式来实现高可用的部署，所以我们只需要将 Eureke Server 配置其他可用的 service-url 就能实现高可用部署。

自我保护模式：
	自我保护模式是一种应对网络异常的安全保护措施。
	当Eureka Server节点在短时间内丢失过多客户端时，那么这个节点就会进入自我保护模式。
	一旦进入该模式，Eureka Server就会保护服务注册表中的信息，不再删除服务注册表中的数据。
	当网络故障恢复后，该Eureka Server节点会自动退出自我保护模式。

配置文件参数：
	eureka.instance.hostname				实例的主机名
	eureka.client.register-with-eureka		是否将自己注册到 Eureka Server，默认为true。
	eureka.client.fetch-registry			是否从 Eureka Server 获取注册信息，默认为true。单节点Server不需要同步其他Server节点的数据时设为false
	eureka.client.service-url.defaultZone	注册中心的地址，多个地址用逗号分隔
	eureka.server.enable-self-preservation	是否启用自我保护模式



史上最简单的教程 https://blog.csdn.net/forezp/article/details/70148833
方志朋的博客  https://www.fangzhipeng.com/archive/?tag=SpringCloud
禁忌夜色153 https://www.cnblogs.com/jinjiyese153/category/1182033.html
一抹书香  https://www.cnblogs.com/chenweida/p/9025610.html
Finchley 版  https://windmt.com/2018/04/15/spring-cloud-2-eureka/

