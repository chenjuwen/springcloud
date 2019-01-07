配置文件参数：				
	eureka.client.service-url.defaultZone		指定服务注册中心的地址

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
