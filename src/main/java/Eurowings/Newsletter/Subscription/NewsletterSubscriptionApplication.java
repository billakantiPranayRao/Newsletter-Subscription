package Eurowings.Newsletter.Subscription;

import Eurowings.Newsletter.Subscription.controller.SubscriptionController;
import Eurowings.Newsletter.Subscription.model.Subscription;
import Eurowings.Newsletter.Subscription.repository.SubscriptionRepository;
import Eurowings.Newsletter.Subscription.service.SubscriptionService;
import Eurowings.Newsletter.Subscription.service.SubscriptionServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import java.util.concurrent.Flow;


@SpringBootApplication
@EnableJpaRepositories("Newsletter-Subscription.src.main.java.Eurowings.Newsletter.Subscription.repository")
@ComponentScan(basePackages = {"Newsletter-Subscription.src.main.java.Eurowings.Newsletter.Subscription"})
public class NewsletterSubscriptionApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsletterSubscriptionApplication.class, args);
	}

}
