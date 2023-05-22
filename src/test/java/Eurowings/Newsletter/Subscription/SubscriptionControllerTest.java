package Eurowings.Newsletter.Subscription;

import Eurowings.Newsletter.Subscription.controller.SubscriptionController;
import Eurowings.Newsletter.Subscription.model.Subscription;
import Eurowings.Newsletter.Subscription.repository.SubscriptionRepository;
import Eurowings.Newsletter.Subscription.service.SubscriptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SubscriptionControllerTest {

    @Mock
    private SubscriptionService subscriptionService;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @InjectMocks
    private SubscriptionController subscriptionController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(subscriptionController).build();
    }

    @Test
    public void testCreateSubscription_Success() throws Exception {
        // Arrange
        String email = "test@example.com";
        String successMessage = "subscription created successfully";
        Subscription subscription = Mockito.mock(Subscription.class);


        when(subscriptionService.createSubscription(email)).thenReturn(subscription);

        // Act
        ResponseEntity<String> response = subscriptionController.createSubscription(email);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        String responseBody = response.getBody();
        assertEquals(successMessage, responseBody);


        verify(subscriptionService).createSubscription(email);
    }

    @Test
    public void testCreateSubscription_Failure() throws Exception {
        // Arrange
        String email = "test@example.com";
        String errorMessage = "Failed to create subscription.";
        Subscription subscription = Mockito.mock(Subscription.class);

        // Mock the behavior of the subscriptionService
        when(subscriptionService.createSubscription(email)).thenReturn(subscription);

        // Act
        ResponseEntity<String> response = subscriptionController.createSubscription(email);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        String responseBody = response.getBody();
        assertEquals(errorMessage, responseBody);

        // Verify that the subscriptionService method was called with the correct arguments
        verify(subscriptionService).createSubscription(email);
    }


    @Test
    public void testCreateSubscription_Failure_EmailExists() {
        // Arrange
        String existingEmail = "existing-email@example.com";
        String expectedErrorMessage = "Email already exists";
        Subscription subscription = Mockito.mock(Subscription.class);


        when(subscriptionService.createSubscription(existingEmail)).thenReturn(subscription);

        // Act
        ResponseEntity<String> response = subscriptionController.createSubscription(existingEmail);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        String responseBody = response.getBody();
        assertEquals(expectedErrorMessage, responseBody);


        verify(subscriptionRepository).existsByEmail(existingEmail);
        verify(subscriptionRepository, Mockito.never()).save(Mockito.any(Subscription.class));
    }


    @Test
    public void testShouldReceiveNewsletter_Yes() throws Exception {
        // Arrange
        String email = "test@example.com";
        boolean shouldReceive = true;


        when(subscriptionService.shouldReceiveNewsletter(email)).thenReturn(shouldReceive);

        // Act
        ResponseEntity<String> response = subscriptionController.shouldReceiveNewsletter(email);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User should receive the newsletter", response.getBody());


        verify(subscriptionService).shouldReceiveNewsletter(email);
    }

    @Test
    public void testShouldReceiveNewsletter_No() throws Exception {
        // Arrange
        String email = "test@example.com";
        boolean shouldReceive = false;


        when(subscriptionService.shouldReceiveNewsletter(email)).thenReturn(shouldReceive);

        // Act
        ResponseEntity<String> response = subscriptionController.shouldReceiveNewsletter(email);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("User should not receive the newsletter", response.getBody());


        verify(subscriptionService).shouldReceiveNewsletter(email);
    }
}
