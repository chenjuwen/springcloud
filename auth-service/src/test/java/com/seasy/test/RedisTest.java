package com.seasy.test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.seasy.Main;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Main.class)
public class RedisTest {
	@Autowired
    StringRedisTemplate stringRedisTemplate;

	@Test
    public void testStringRedisTemplate() throws Exception {
		String nowtime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
		
		stringRedisTemplate.opsForValue().set("username", nowtime);
		
		String username = stringRedisTemplate.opsForValue().get("username");
		System.out.println("username=" + username);
		
		testValue();
//		testHash();
//		testZSet();
//		testSet();
//		testList();
	}
	
	private void testValue(){
		ValueOperations<String, String> value = stringRedisTemplate.opsForValue();
		value.getOperations().delete("aaa"); //删除
		value.getOperations().delete("bbb");
		
		value.set("aaa", "123"); //set
		System.out.println(value.setIfAbsent("aaa", "123")); //没有才set
		
		System.out.println(value.size("aaa")); //长度
		System.out.println(value.get("aaa")); //获取值
		
		value.append("aaa", "456"); //追加
		System.out.println(value.get("aaa"));
		
		value.increment("bbb", 1); //数值自增
		System.out.println(value.get("bbb"));
		
		value.multiGet(Arrays.asList("aaa", "bbb", "ccc"))
			.stream().forEach(System.out::println);
	}
	
	private void testHash(){
		HashOperations<String, String, String> hash = stringRedisTemplate.opsForHash();
		hash.getOperations().delete("hash1");
		
		hash.put("hash1", "aaa", "111"); //put
		hash.put("hash1", "bbb", "222");
		hash.put("hash1", "ccc", "333");
		
		System.out.println(hash.size("hash1")); //长度
		
		hash.entries("hash1").forEach((k,v) -> { //显示所有的key和value
			System.out.println(k + "=" + v);
		});
		
		hash.keys("hash1").stream().forEach(System.out::println); //显示所有的key
		hash.values("hash1").stream().forEach(System.out::println); //显示所有的value
		
		System.out.println(hash.putIfAbsent("hash1", "aaa", "aaa")); //没有key才put
		
		hash.increment("hash1", "count", 1); //数值自增
		System.out.println(hash.get("hash1", "count"));
		
		hash.increment("hash1", "count", 1);
		System.out.println(hash.get("hash1", "count"));
		
		hash.increment("hash1", "count", -1);
		System.out.println(hash.get("hash1", "count"));
		
		System.out.println(hash.hasKey("hash1", "amount")); //判断key是否存在
		System.out.println(hash.get("hash1", "count"));
		hash.delete("hash1", "count"); //删除
	}
	
	private void testZSet(){
		ZSetOperations<String, String> zset = stringRedisTemplate.opsForZSet();
		zset.getOperations().delete("zset1");
		zset.getOperations().delete("zset2");
		
		zset.add("zset1", "aaa", 1);
		zset.add("zset1", "bbb", 1);
		zset.add("zset1", "ccc", 1);
		
		zset.add("zset2", "bbb", 1);
		zset.add("zset2", "ccc", 1);
		zset.add("zset2", "ddd", 1);
		
		System.out.println(zset.size("zset1")); //长度
		System.out.println(zset.range("zset1", 0, -1)); //取所有元素
		
		zset.incrementScore("zset1", "bbb", 1); //增加分数score
		zset.incrementScore("zset1", "aaa", 2);
		
		System.out.println(zset.range("zset1", 0, -1)); //分数递增排序
		zset.rangeWithScores("zset1", 0, -1).stream().forEach(t -> {
			System.out.println(t.getValue() + "-" + t.getScore());
		});
		
		System.out.println(zset.reverseRange("zset1", 0, -1)); //分数递减排序
		zset.reverseRangeWithScores("zset1", 0, -1).stream().forEach(t -> {
			System.out.println(t.getValue() + "-" + t.getScore());
		});
		
		System.out.println(zset.rank("zset1", "ccc")); //分数递增排序，取元素的索引
		System.out.println(zset.reverseRank("zset1", "ccc")); //分数递减排序，取元素的索引
		
		System.out.println(zset.score("zset1", "bbb")); //取元素的分数
		
		zset.remove("zset2", "bbb"); //删除元素
		System.out.println(zset.range("zset2", 0, -1));
	}
	
	private void testSet(){
		SetOperations<String, String> set = stringRedisTemplate.opsForSet();
		set.getOperations().delete("set1");
		set.getOperations().delete("set2");
		
		set.add("set1", "111", "222", "333", "111");
		set.add("set2", "222", "333", "444");
		
		System.out.println(set.size("set1")); //长度
		
		System.out.println(set.members("set1")); //获取所有值
		System.out.println(set.members("set2"));
		
		System.out.println(set.difference("set1", "set2")); //从前者取得与后者不一样的元素
		System.out.println(set.intersect("set1", "set2")); //交集
		System.out.println(set.union("set1", "set2")); //并集
		set.remove("set1", "111"); //删除一个元素
		System.out.println(set.pop("set1")); //弹出一个元素
		System.out.println(set.randomMember("set2")); //随机取一个元素
		System.out.println(set.isMember("set2", "444")); //给定元素是否是成员
		set.move("set2", "444", "set1"); //移动
		System.out.println(set.members("set1"));
	}

	private void testList() {
		ListOperations<String, String> list = stringRedisTemplate.opsForList();
		list.getOperations().delete("list"); //清空
		
		list.leftPush("list", "111"); //push 放到list中
		list.leftPushIfPresent("list", "222");
		list.rightPush("list", "333");
		list.rightPushIfPresent("list", "444");
		
		System.out.println(list.index("list", 0));
		System.out.println(list.range("list", 0, -1));
		list.trim("list", 0, 2); //截取
		System.out.println(list.range("list", 0, -1));
		
		System.out.println(list.leftPop("list")); //pop 取出消费掉
		System.out.println(list.rightPop("list"));
	}
}
