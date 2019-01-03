配置文件参数：
	eureka.instance.hostname				
	eureka.client.register-with-eureka		是否将自己注册到 Eureka Server，默认为true。
	eureka.client.fetch-registry			是否从 Eureka Server 获取注册信息，默认为true。单节点Server不需要同步其他Server节点的数据时设为false
	eureka.client.service-url.defaultZone	设置与 Eureka Server 交互的地址，查询服务和注册服务都需要依赖这个地址。

访问地址： http://localhost:7000/

史上最简单的 SpringCloud 教程 https://blog.csdn.net/forezp/article/details/70148833
方志朋的博客  https://www.fangzhipeng.com/archive/?tag=SpringCloud
禁忌夜色153 https://www.cnblogs.com/jinjiyese153/category/1182033.html
一抹书香  https://www.cnblogs.com/chenweida/p/9025610.html
Finchley 版  https://windmt.com/2018/04/15/spring-cloud-2-eureka/

