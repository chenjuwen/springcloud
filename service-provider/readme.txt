配置文件参数：		
	spring.application.name						服务应用名
	server.port									服务端口	
	eureka.client.service-url.defaultZone		服务注册中心的地址
	eureka.instance.hostname					服务实例的主机名
	eureka.instance.instance-id					自定义服务实例ID
	eureka.instance.prefer-ip-address			是否以IP地址而不是主机名注册到Eureka Server



服务的 Instance ID 默认值是：
	应用实例ID不为空时：
		${spring.cloud.client.hostname}:${spring.application.name}:${spring.application.instance_id}
			主机名：应用名：应用实例ID
	应用实例ID为空时：
		${spring.cloud.client.hostname}:${spring.application.name}:${server.port}
			主机名：应用名：应用端口
	自定义：
		${spring.cloud.client.ip-address}:${server.port}
			本机IP:应用端口
		${spring.cloud.client.ip-address}:${server.port}:@project.version@
			本机IP:应用端口:工程版本号
			
		让实例URL也指向本机IP：
			eureka.instance.hostname=${spring.cloud.client.ip-address}

			
			
