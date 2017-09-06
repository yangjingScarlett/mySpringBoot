package com.yang.springboot;

import com.rometools.rome.feed.synd.SyndEntry;
import com.yang.springboot.g_customizeEndpoint.StatusEndpoint;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.dsl.core.Pollers;
import org.springframework.integration.dsl.file.Files;
import org.springframework.integration.dsl.mail.Mail;
import org.springframework.integration.feed.inbound.FeedEntryMessageSource;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.scheduling.PollerMetadata;

import java.io.File;
import java.io.IOException;

import static org.springframework.core.SpringProperties.getProperty;

@SpringBootApplication
@EnableCaching//如果要使用缓存机制，在spring boot中还是要使用@EnableCaching
public class MySpringbootjpaApplication implements CommandLineRunner {

    @Autowired
    private
    RabbitTemplate rabbitTemplate;

    @Bean
    public Queue wiselyQueue() {
        return new Queue("my-queue");
    }

    @Override
    public void run(String... args) throws Exception {
        rabbitTemplate.convertAndSend("my-queue", "来自RabbitMQ的问候！");
    }


    //通过@Value注解自动获得https://spring.io/blog.atom的资源
    @Value("https://spring.io/blog.atom")
    Resource resource;

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    //使用FluentAPI和Pollers配置默认的轮询方式
    public PollerMetadata poller() {
        return Pollers.fixedRate(500).get();
    }

    @Bean
    //FeedEntryMessageSource实际为feed:inbound-channel-adapter.此处即构造feed的入站通道适配器作为数据输入
    public FeedEntryMessageSource feedMessageSource() throws IOException {
        return new FeedEntryMessageSource(resource.getURL(), "news");
    }

    @Bean
    public IntegrationFlow myFlow() throws IOException {
        return IntegrationFlows.from(feedMessageSource())//流程从from方法开始
                //通过路由方法route来选择路由，消息体（payload）的类型是SyndEntry，作为判断条件的类型为string，
                //判断的值是通过payload获得的Category
                .<SyndEntry, String>route(payload -> payload.getCategories().get(0).getName(),
                        //通过不同分类的值转向不同的消息通道，若分类为news，则转向newsChannel
                        mapping -> mapping.channelMapping("releases", "releasesChannel")
                                .channelMapping("engineering", "engineeringChannel")
                                .channelMapping("news", "newsChannel")
                )
                //通过get方法获得IntegrationFlow实体，配置为spring的bean
                .get();
    }

    @Bean
    //release流程
    public IntegrationFlow releaseFlow() {
        return IntegrationFlows.from(MessageChannels.queue("releasesChannel", 10))//从消息通道releasesChannel开始获取数据
                //使用transform方法进行数据转换，payload类型为SyndEntry，将其转换为字符串类型，并自定义数据格式
                .<SyndEntry, String>transform(payload -> "《" + payload.getTitle() + "》" + payload.getLink() + getProperty("line.separator"))
                //用handle方法处理file的出站适配器，Files类是由Spring Integration Java DSL提供的Fluent API用来构造文件输出的适配器
                .handle(Files.outboundAdapter(new File("e:/springBlog"))
                        .fileExistsMode(FileExistsMode.APPEND)
                        .charset("UTF-8")
                        .fileNameGenerator(message -> "releases.txt")
                        .get())
                .get();
    }

    @Bean
    //engineering流程
    public IntegrationFlow engineeringFlow() {
        return IntegrationFlows.from(MessageChannels.queue("engineeringChannel", 10))
                .<SyndEntry, String>transform(e -> "《" + e.getTitle() + "》" + e.getLink() + getProperty("line.separator"))
                .handle(Files.outboundAdapter(new File("e:/springBlog"))
                        .fileExistsMode(FileExistsMode.APPEND)
                        .charset("UTF-8")
                        .fileNameGenerator(message -> "engineering.txt")
                        .get())
                .get();
    }

    @Bean
    public IntegrationFlow newsFlow() {
        return IntegrationFlows.from(MessageChannels.queue("newsChannel", 10))
                .<SyndEntry, String>transform(payload -> "《" + payload.getTitle() + "》" + payload.getLink() + getProperty("line.separator"))
                .enrichHeaders(//通过enrichHeaders来增加消息头的信息
                        Mail.headers()
                                .subject("来自spring 的新闻")
                                .to("2411837616@qq.com")
                                .from("2411837616@qq.com")
                )
                //使用handle 方法来定义邮件发送的出站适配器，使用Spring Integration Java DSL 提供的Mail.outboundAdapter 来构造，这里使用2411837616@qq.com邮箱向自己发送邮件。
                .handle(Mail.outboundAdapter("smtp.qq.com")//邮件发送的相关信息通过Spring Integration Java DSL提供的Mail的header方法来构造
                        .port(465)
                        .protocol("smtp")
                        .credentials("2411837616@qq.com", "ilfamily1314521")
                        .javaMailProperties(p -> p.put("mail.debug", "false")), e -> e.id("smtpOut")
                )
                .get();
    }

    //这里是为了把自定义的端点“status”注册为一个bean
    @Bean
    public Endpoint<String> status() {
        return new StatusEndpoint();
    }

    public static void main(String[] args) {
        SpringApplication.run(MySpringbootjpaApplication.class, args);
    }
}
