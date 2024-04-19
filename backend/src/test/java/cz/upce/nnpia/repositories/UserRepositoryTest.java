package cz.upce.nnpia.repositories;

import cz.upce.nnpia.model.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    private static User admin;

    @Autowired
    private UserRepository underTest;

    @BeforeAll
    static void beforeAll() {
        admin = User.builder()
                .username("admin")
                .firstName("admin")
                .lastName("admin")
                .email("admin@admin.cz")
                .password("admin")
                .roles(Collections.emptySet())
                .build();
    }

    @BeforeEach
    void setUp() {
        underTest.save(admin);
    }

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void findByUsername() {
        Optional<User> expected = underTest.findByUsername(admin.getUsername());
        assertTrue(expected.isPresent());
        assertThat(expected.get()).isEqualTo(admin);

        expected = underTest.findByUsername("invalidUsername");
        assertFalse(expected.isPresent());

    }

    @Test
    void existsByUsername() {
        assertThat(underTest.existsByUsername(admin.getUsername())).isTrue();
        assertThat(underTest.existsByUsername("invalidUsername")).isFalse();
    }

    @Test
    void existsByEmail() {
        assertThat(underTest.existsByEmail(admin.getEmail())).isTrue();
        assertThat(underTest.existsByEmail("invalid@email.cz")).isFalse();
    }
}