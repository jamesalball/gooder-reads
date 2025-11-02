package com.gooderreads.gooder_reads.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import com.gooderreads.gooder_reads.entity.User;

@DataJpaTest
public class UserRepositoryTest {
    
    @Autowired
    UserRepository userRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    void testSaveNewUser() {
        User newUser = new User("test@testing.com", "Test User");
        User savedUser = userRepository.save(newUser);
        assertThat(entityManager.find(User.class, savedUser.getId())).isEqualTo(newUser);
    }

    @Test
    void testUpdateExistingUser() {
        User user = new User("test@testing.com", "Test User");
        entityManager.persist(user);
        String newTitle = "New Display Name";
        user.setDisplayName(newTitle);
        userRepository.save(user);
        assertThat(entityManager.find(User.class, user.getId()).getDisplayName()).isEqualTo(newTitle);
    }

    @Test
    void testFindAllUsers() {
        User userA = new User("test@testing.com", "Test User");
        User userB = new User("test2@testing.com", "Test User 2");
        entityManager.persist(userA);
        entityManager.persist(userB);

        List<User> userList = userRepository.findAll();

        assertThat(entityManager.find(User.class, userA.getId())).isEqualTo(userList.get(0));

    }

    @Test
    void testFindUserById() {
        User user = new User("test@testing.com", "Test User");
        entityManager.persist(user);
        Optional<User> actualUser = userRepository.findById(user.getId());
        assertThat(actualUser).contains(user);
    }

    @Test
    void testDeleteUser() {
        User user = new User("test@testing.com", "Test User");
        entityManager.persist(user);
        userRepository.delete(user);
        assertThat(entityManager.find(User.class, user.getId())).isNull();
    }
}
