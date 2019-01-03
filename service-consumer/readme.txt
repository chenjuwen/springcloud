配置文件参数：				
	eureka.client.service-url.defaultZone		指定服务注册中心的地址

Ribbon：
	是一个基于 HTTP 和 TCP 的客户端负载均衡器。它主要包括六个组件：
		ServerList，负载均衡使用的服务器列表。这个列表会缓存在负载均衡器中，并定期更新。当 Ribbon 与 Eureka 结合使用时，ServerList 的实现类就是 DiscoveryEnabledNIWSServerList，它会保存 Eureka Server 中注册的服务实例表。
		ServerListFilter，服务器列表过滤器。这是一个接口，主要用于对 Service Consumer 获取到的服务器列表进行预过滤，过滤的结果也是 ServerList。Ribbon 提供了多种过滤器的实现。
		IPing，探测服务实例是否存活的策略。
		IRule，负载均衡策略，其实现类表述的策略包括：轮询、随机、根据响应时间加权等。
		ILoadBalancer，负载均衡器。这也是一个接口，Ribbon 为其提供了多个实现，比如 ZoneAwareLoadBalancer。而上层代码通过调用其 API 进行服务调用的负载均衡选择。
		RestClient，服务调用器。

Hystrix：
	Hystrix实现了断路器的模式。断路器本身是一种开关装置，当某个服务单元发生故障之后，通过断路器的故障监控，向调用方返回一个符合预期的、可处理的备选响应（FallBack），而不是长时间的等待或者抛出调用方无法处理的异常，这样就保证了服务调用方的线程不会被长时间、不必要地占用，从而避免了故障在分布式系统中的蔓延，乃至雪崩。
	在请求失败频率较低的情况下，Hystrix 还是会直接把故障返回给客户端。只有当失败次数达到阈值时，断路器打开并且不进行后续通信，而是直接返回备选响应。
	
Hystrix Dashboard：
	Hystrix 会持续地记录所有通过 Hystrix 发起的请求的执行信息，并以统计报表和图形的形式展示给用户。

	
Feign：
	Feign是一个声明式的 Web Service 客户端，它的目的就是让 Web Service 调用更加简单。它整合了 Ribbon 和 Hystrix，从而让我们不再需要显式地使用这两个组件。
	Feign还提供了 HTTP 请求的模板，通过编写简单的接口和插入注解，我们就可以定义好 HTTP 请求的参数、格式、地址等信息。
	Feign 会完全代理 HTTP 的请求，我们只需要像调用方法一样调用它就可以完成服务请求。
	
	Feign 具有如下特性：
		可插拔的注解支持，包括 Feign 注解和 JAX-RS 注解
		支持可插拔的 HTTP 编码器和解码器
		支持 Hystrix 和它的 Fallback
		支持 Ribbon 的负载均衡
		支持 HTTP 请求和响应的压缩
	
	
	
	
创建服务消费者的三种方式：
1、使用 LoadBalancerClient方式
	启动类：
		@SpringBootApplication
		public class Main {
			public static void main(String[] args) {
				SpringApplication.run(Main.class, args);
			}
			
			@Bean
		    public RestTemplate getRestTemplate() {
		        return new RestTemplate();
		    }
		}
		
	Controller：
		@RestController
		public class UserController {
			@Autowired
		    private LoadBalancerClient client;
			
			@Autowired
		    private RestTemplate restTemplate;
			
			@GetMapping("/user/{id}")
			public String addUser(@PathVariable Long id){
				//通过负载均衡选出一个服务提供者的服务实例
				ServiceInstance instance = client.choose("service-provider-1");
				
				//拼接服务调用者的接口地址：此处用IP:端口
				String url = "http://" + instance.getHost() + ":" + instance.getPort() + "/user/" + id;
				
				//通过RestTemplate对象调用接口
				String result = restTemplate.getForObject(url, String.class);
				
				return "consumer >> " + result;
			}
		}

2、使用 Spring Cloud Ribbon方式
	启动类：
		@SpringBootApplication
		public class Main2 {
			public static void main(String[] args) {
				SpringApplication.run(Main2.class, args);
			}
			
			@LoadBalanced
			@Bean
		    public RestTemplate getRestTemplate() {
		        return new RestTemplate();
		    }
		}
		
	Controller：
		@RestController
		public class User2Controller {
			@Autowired
		    private RestTemplate restTemplate;
			
			@GetMapping("/user/{id}")
			public String addUser(@PathVariable Long id){
				System.out.println("consumer id = " + id);
				
				//服务调用者的接口地址：此处用应用名
				String url = "http://service-provider-1/user/" + id;
				
				//通过RestTemplate对象调用接口
				String result = restTemplate.getForObject(url, String.class);
				
				return "consumer >> " + result;
			}
		}

3、使用 Spring Cloud Feign方式
	启动类：		
		@EnableFeignClients
		@SpringBootApplication
		public class Main3 {
			public static void main(String[] args) {
				SpringApplication.run(Main3.class, args);
			}
		}
		
	服务提供者Controller类：
		@RestController
		@RequestMapping("/user")
		public class UserController {
			@GetMapping("/{id}")
			public String addUser(@PathVariable(value="id") Long id){
				return "provider >> id=" + id;
			}
		
			@GetMapping("/query")
			public String query(@RequestParam(value="name") String name){
				return "provider >> name=" + name;
			}
		}

	Feign的客户端接口类：
		@FeignClient(name="service-provider-1") //要调用的服务名称
		public interface ServiceRemote {
			//接口方法的结果要与服务提供者Controller定义的一致
			@GetMapping("/user/{id}")
			String addUser(@PathVariable(value="id") Long id);
			
			@GetMapping("/user/query")
			String query(@RequestParam(value="name") String name);
		}

	Controller：	
		@RestController
		public class User3Controller {
			@Autowired
		    ServiceRemote serviceRemote;
			
			@GetMapping("/consumer")
			public String test(){
				String result = serviceRemote.addUser(22L);
				System.out.println("result=" + result);
		
				result = serviceRemote.query("cjm");
				System.out.println("result=" + result);
				
				return "ok";
			}
		}
