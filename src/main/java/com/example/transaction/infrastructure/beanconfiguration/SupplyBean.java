package com.example.transaction.infrastructure.beanconfiguration;

import com.example.transaction.application.interfaces.FeignInterceptorService;
import com.example.transaction.domain.port.dao.SupplyDao;
import com.example.transaction.domain.port.publisher.SupplyPublisher;
import com.example.transaction.domain.port.repository.SaleRepository;
import com.example.transaction.domain.port.repository.SupplyRepository;
import com.example.transaction.domain.port.services.ArticleService;
import com.example.transaction.domain.service.BuyArticleServices;
import com.example.transaction.domain.service.SupplyCreateService;
import com.example.transaction.infrastructure.adapter.http.HttpArticleService;
import com.example.transaction.infrastructure.adapter.http.client.TokenInterceptor;
import com.example.transaction.infrastructure.adapter.http.client.ArticleClient;
import com.example.transaction.infrastructure.adapter.http.client.FeignClientInterceptor;
import com.example.transaction.infrastructure.adapter.mapper.SaleDboMapper;
import feign.Logger;
import feign.Request;
import org.springframework.amqp.core.Queue;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@AllArgsConstructor
public class SupplyBean {
    public static final String QUEUE_NAME = "tst";
    public static final String EXCHANGE_NAME = "test-exchange";

    @Bean
    public SupplyCreateService supplyCreateService(SupplyRepository supplyRepository, SupplyPublisher supplyPublisher, SupplyDao supplyDao) {
        return new SupplyCreateService(supplyRepository, supplyPublisher, supplyDao);
    }

    @Bean
    public BuyArticleServices buyArticleServices(SaleRepository supplyRepository, ArticleService articleService) {
        return new BuyArticleServices(supplyRepository, articleService);
    }

    @Bean
    FeignInterceptorService feignInterceptorService(FeignClientInterceptor feignClientInterceptor){
        return new TokenInterceptor(feignClientInterceptor);
    }

    @Bean
    public ArticleService articleService(ArticleClient articleClient, SaleDboMapper articleDboMapper) {
        return new HttpArticleService(articleClient,articleDboMapper);
    }

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public Request.Options requestOptions() {
        return new Request.Options();
    }

    @Bean
    public FeignClientInterceptor feignClientInterceptor() {
        return new FeignClientInterceptor(null);
    }

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}
