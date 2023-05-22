package Eurowings.Newsletter.Subscription.controller;

import Eurowings.Newsletter.Subscription.model.Subscription;
import Eurowings.Newsletter.Subscription.service.SubscriptionService;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.logging.Logger;


import java.util.List;


@RestController
@RequestMapping("/subscriptions")
@Controller
public class SubscriptionController {

    private static final Logger logger = Logger.getLogger(SubscriptionController.class.getName());

    @Autowired
    private final SubscriptionService subscriptionService;


    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping(value = "/subscribed", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createSubscription(@RequestParam String email) {
        if (!EmailValidator.getInstance().isValid(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        Subscription isCreated = subscriptionService.createSubscription(email);
        if (isCreated!=null) {
            logger.info("subscription created successfully.");
            return ResponseEntity.status(HttpStatus.CREATED).body("subscription created successfully");
        } else {
            logger.warning("Failed to create subscription.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create subscription");
        }
    }

    /*@GetMapping
    public List<Subscription> getAllSubscriptions() {
        return subscriptionService.getAllSubscriptions();
    }*/

    @GetMapping(value = "/should-receive-newsletter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> shouldReceiveNewsletter(@RequestParam String email) {
        boolean shouldReceive = subscriptionService.shouldReceiveNewsletter(email);
        if (shouldReceive) {
            logger.info("The user should receive the newsletter.");
            return ResponseEntity.ok("The user should receive the newsletter.");
        } else {
            logger.warning("The user should not receive the newsletter.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User should not receive the newsletter");
        }
    }

    @DeleteMapping("/unsubscribe")
    public ResponseEntity<String> unsubscribe(@RequestParam String email) {
        subscriptionService.unsubscribe(email);
        logger.info("The user successfully unsubscribed.");
        return ResponseEntity.ok("Successfully unsubscribed");
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Subscription>> getUsersBySubscriptionDate(
            @RequestParam(value = "before", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate beforeDate,
            @RequestParam(value = "after", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate afterDate) {

        if (beforeDate != null && afterDate != null) {
            // Fetch users subscribed between beforeDate and afterDate
            try {
                List<Subscription> users = subscriptionService.getUsersBySubscriptionDateBetween(beforeDate, afterDate);
                return ResponseEntity.ok().body(users);
            }catch (Exception e){
                logger.warning("Error occurred while fetching users by subscription date range" +e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

        } else if (beforeDate != null) {
            // Fetch users subscribed before beforeDate
            try {
                List<Subscription> users = subscriptionService.getUsersBySubscriptionDateBefore(beforeDate);
                return ResponseEntity.ok().body(users);
            }catch (Exception e){
                logger.warning("Error occurred while fetching users by subscription before date" +e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else if (afterDate != null) {
            // Fetch users subscribed after afterDate
            try {
                List<Subscription> users = subscriptionService.getUsersBySubscriptionDateAfter(afterDate);
                return ResponseEntity.ok().body(users);
            }catch (Exception e){
                logger.warning("Error occurred while fetching users by subscription after date" +e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

        } else {
            // If neither 'before' nor 'after' is provided, return all users
            try {
                List<Subscription> users = subscriptionService.getAllSubscriptions();
                return ResponseEntity.ok().body(users);
            }catch (Exception e){
                logger.warning("Error occurred while fetching all users" +e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

        }
    }
}

