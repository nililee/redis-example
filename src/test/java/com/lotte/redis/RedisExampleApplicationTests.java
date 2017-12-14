package com.lotte.redis;

import static org.junit.Assert.*;

import java.util.*;

import javax.annotation.*;

import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.serializer.*;
import org.springframework.session.*;
import org.springframework.session.data.redis.*;
import org.springframework.test.context.junit4.*;
import org.springframework.util.*;

import com.github.javafaker.*;
import com.lotte.redis.model.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisExampleApplicationTests {

    /*
    @Resource(name = "httpSessionConnectionFactory")
    private RedisConnectionFactory redisConnectionFactory;

    @Test
    public void testRedisConnectionFactory() {

        RedisTemplate<String, Person> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        ValueOperations<String, Person> valueOps = template.opsForValue();

        Person p1 = new Person(1, "USER.101",  30);
        Person p2 = new Person(2, "USER.111", 35);

        valueOps.set("USER.101", p1);
        valueOps.set("USER.111", p2);
    }
    */

    @Resource(name = "httpSessionClusterConnectionFactory")
    private RedisConnectionFactory clusterConnectionFactory;

    @Resource(name = "springSessionDefaultRedisSerializer")
    private RedisSerializer<Object> springSessionDefaultRedisSerializer;

    private RedisClusterConnection connection;

    @Before
    public void flushAllRedisClusterNodes() {

        connection = clusterConnectionFactory.getClusterConnection();

        System.out.println("## 클러스터 노드 정보 ################################################");
        connection.clusterGetNodes().forEach(
            node -> System.out.println(
                node.getHost() + ":" + node.getPort() + " " + node.getId() + " :: "
                + (node.isMaster() ? "MASTER " : "SLAVE ")
            )
        );
        System.out.println("###################################################################");

        connection.clusterGetNodes().forEach(
            node -> {
                if(node.isMaster()) {
                    connection.flushDb(node);
                }
            }
        );
    }

    @After
    public void closeRedisClusterConnection() {
        connection.close();
    }

    @Ignore
    @Test
    public void testOnlyRedisCluster() {

        Map<String, User> keyValues = new HashMap<>();

        Faker faker = new Faker();

        for(int i = 0; i < 1000; i++) {
            User user = new User(
                faker.idNumber().ssnValid(),
                faker.name().fullName(),
                System.nanoTime()
            );

            keyValues.put(
                StringUtils.replace(UUID.randomUUID().toString(), "-", ""),
                user
            );
        }

        keyValues.forEach((key, value)
            -> connection.set(key.getBytes(), springSessionDefaultRedisSerializer.serialize(value))
        );

        keyValues.forEach((key, value) -> {
            assertNotNull(connection.get(key.getBytes()));
        });
    }

//    public static void main(String[] args) {
//        Set<HostAndPort> jedisClusterNode = new HashSet<HostAndPort>();
//        jedisClusterNode.add(new HostAndPort("127.0.0.1", 7003));
//        jedisClusterNode.add(new HostAndPort("127.0.0.1", 7004));
//        jedisClusterNode.add(new HostAndPort("127.0.0.1", 7005));
//        jedisClusterNode.add(new HostAndPort("127.0.0.1", 8003));
//        jedisClusterNode.add(new HostAndPort("127.0.0.1", 8004));
//        jedisClusterNode.add(new HostAndPort("127.0.0.1", 8005));
//
//        JedisCluster jc = new JedisCluster(jedisClusterNode);
//
//        System.out.println(jc.getClusterNodes().size());
//
//        jc.set("key", "value");
//        System.out.println(jc.get("key"));
//    }

}
