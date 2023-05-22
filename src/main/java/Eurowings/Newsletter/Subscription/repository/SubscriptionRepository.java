package Eurowings.Newsletter.Subscription.repository;

import Eurowings.Newsletter.Subscription.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Subscription findByEmail(String email);

    List<Subscription> findBySubscriptionDateBetween(LocalDate beforeDate, LocalDate afterDate);

    List<Subscription> findBySubscriptionDateBefore(LocalDate beforeDate);

    List<Subscription> findBySubscriptionDateAfter(LocalDate afterDate);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Subscription s WHERE s.email = :email")
    boolean existsByEmail(@Param("email") String email);
}

