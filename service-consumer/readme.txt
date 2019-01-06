配置文件参数：				
	eureka.client.service-url.defaultZone		指定服务注册中心的地址

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
