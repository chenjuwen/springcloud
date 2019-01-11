配置中心访问地址：
	格式： 
		http://localhost:4001/{name}-{profiles}.properties
		http://localhost:4001/{name}/{profile}
	范例：
		http://localhost:4001/username/dev

http://localhost:9001/config-server/dev/master
http://localhost:9001/config-server/dev
http://localhost:9001/username/dev

http://localhost:4001/config-dev.properties
http://localhost:4001/config/dev

配置参数说明：
	#配置中心的git仓库地址
	spring.cloud.config.server.git.uri=https://github.com/chenjuwen/config-repository.git
	#git仓库地址下的相对地址，多个用逗号分割
	spring.cloud.config.server.git.search-paths=repository1
	#访问 git仓库的用户名
	spring.cloud.config.server.git.username=
	#访问 git仓库的用户密码
	spring.cloud.config.server.git.password=
