配置文件参数：
	eureka.instance.hostname				实例的主机名
	eureka.client.register-with-eureka		是否将自己注册到 Eureka Server，默认为true。
	eureka.client.fetch-registry			是否从 Eureka Server 获取注册信息，默认为true。单节点Server不需要同步其他Server节点的数据时设为false
	eureka.client.service-url.defaultZone	注册中心的地址，多个地址用逗号分隔
	eureka.server.enable-self-preservation	是否启用自我保护模式


