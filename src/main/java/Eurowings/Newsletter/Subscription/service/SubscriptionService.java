package Eurowings.Newsletter.Subscription.service;

import Eurowings.Newsletter.Subscription.model.Subscription;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


public interface SubscriptionService {

    Subscription createSubscription(String email);

     void unsubscribe(String email);

    boolean shouldReceiveNewsletter(String email);

    List<Subscription> getAllSubscriptions();

    List<Subscription> getUsersBySubscriptionDateBetween(LocalDate beforeDate, LocalDate afterDate);

    List<Subscription> getUsersBySubscriptionDateBefore(LocalDate beforeDate);

    List<Subscription> getUsersBySubscriptionDateAfter(LocalDate afterDate);
}

