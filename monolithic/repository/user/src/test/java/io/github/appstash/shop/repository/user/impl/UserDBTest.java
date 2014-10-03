package io.github.appstash.shop.repository.user.impl;

import io.github.appstash.shop.repository.user.api.UserRepository;
import io.github.appstash.shop.repository.user.model.Address;
import io.github.appstash.shop.repository.user.model.Role;
import io.github.appstash.shop.repository.user.model.User;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.util.Collections;
import java.util.Random;

import static org.junit.Assert.assertEquals;

@ActiveProfiles("test")
@ContextConfiguration(locations = "classpath:io/github/appstash/shop/repository/user/spring-context.xml")
public class UserDBTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private MongoOperations mongoOperations;

    @Test
    public void saveAndRetrieveOrderTest() {
        mongoOperations.dropCollection(User.class);
        User sampleUser = createSampleUser("sample");
        userRepository.save(sampleUser);

        assertEquals(1, userRepository.countAll());
        assertEquals(sampleUser.getUsername(), userRepository.findAll().get(0).getUsername());
    }

    @Test
    @Ignore // not ensured by Fongo
    public void uniqueUsernameTest() {
        User user1 = createSampleUser("user1");
        User user1Clone = createSampleUser("user1");
        User user2 = createSampleUser("user2");

        userRepository.save(user1);
        assertEquals(1, userRepository.countAll());

        userRepository.save(user2);
        assertEquals(2, userRepository.countAll());

        userRepository.save(user1Clone);
        assertEquals(2, userRepository.countAll());
    }

    private static User createSampleUser(String username) {
        User user = new User(username, "John", "Smith", "securepw", new Address("", "", "", ""), Collections.<Role>emptySet());
        user.setEmail("username" + new Random().nextInt(1000) + "@gmail.com");
        return user;
    }
}
