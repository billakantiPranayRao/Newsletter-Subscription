package Eurowings.Newsletter.Subscription.service;

import Eurowings.Newsletter.Subscription.model.Subscription;
import Eurowings.Newsletter.Subscription.repository.SubscriptionRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public Subscription createSubscription(String email) {

        if (!EmailValidator.getInstance().isValid(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }

        // Check if the email already exists in the database
        if (subscriptionRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }
        Subscription subscription = new Subscription();
        subscription.setEmail(email);
        return subscriptionRepository.save(subscription);
    }
    @Override
    public void unsubscribe(String email) {
        Subscription subscription = subscriptionRepository.findByEmail(email);
        if (subscription != null) {
            subscriptionRepository.delete(subscription);
        }
    }

    @Override
    public boolean shouldReceiveNewsletter(String email) {
        // Implementation to check if a user should receive the newsletter
        boolean activeStatus = false;
       Subscription subscription = subscriptionRepository.findByEmail(email);
                  if (subscription != null)
                      activeStatus = true;

                 return activeStatus;
    }

    @Override
    public List<Subscription> getAllSubscriptions() {
        // Implementation to get all subscriptions
         return subscriptionRepository.findAll();

    }

    @Override
    public List<Subscription> getUsersBySubscriptionDateBetween(LocalDate beforeDate, LocalDate afterDate) {
        // Implementation to get users subscribed between the provided dates
        return subscriptionRepository.findBySubscriptionDateBetween(beforeDate, afterDate);

    }

    @Override
    public List<Subscription> getUsersBySubscriptionDateBefore(LocalDate beforeDate) {
        // Implementation to get users subscribed before the provided date
        return subscriptionRepository.findBySubscriptionDateBefore(beforeDate);

    }

    @Override
    public List<Subscription> getUsersBySubscriptionDateAfter(LocalDate afterDate) {
        // Implementation to get users subscribed after the provided date
        return subscriptionRepository.findBySubscriptionDateAfter(afterDate);

    }
}

