配置中心访问地址：
	格式： 
		http://localhost:4001/{name}-{profile}.properties
		http://localhost:4001/{name}/{profile}/{label}
		http://localhost:4001/{name}/{profile}
		
		{label}对应 git上不同的分支，默认为 master。

http://localhost:4001/config-dev.properties
http://localhost:4001/config/dev/master
http://localhost:4001/config/dev

配置参数说明：
	#配置中心的git仓库地址
	spring.cloud.config.server.git.uri=https://github.com/chenjuwen/config-repository.git
	#git仓库地址下的相对地址，多个用逗号分割
	spring.cloud.config.server.git.search-paths=repository1
	#配置仓库的分支
	#spring.cloud.config.label=master
	#访问 git仓库的用户名
	spring.cloud.config.server.git.username=
	#访问 git仓库的用户密码
	spring.cloud.config.server.git.password=

#配置中心应用的URL地址
spring.cloud.config.uri=http://localhost:4001
#配置文件的名称，对应 {name} 部分
spring.cloud.config.name=config
#对应 {profile} 部分
spring.cloud.config.profile=dev
#对应 {label} 部分
#spring.cloud.config.label=master
