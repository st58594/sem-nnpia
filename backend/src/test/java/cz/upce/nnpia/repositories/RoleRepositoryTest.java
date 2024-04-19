package cz.upce.nnpia.repositories;

import cz.upce.nnpia.model.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class RoleRepositoryTest {

    private static Role role;
    @Autowired
    private RoleRepository underTest;

    @BeforeAll
    static void beforeAll() {
        role = Role.builder()
                .role("ADMIN")
                .build();
    }

    @BeforeEach
    void setUp() {
        underTest.save(role);
    }

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void findByRole() {
        Optional<Role> expected = underTest.findByRole(RoleRepositoryTest.role.getRole());
        assertTrue(expected.isPresent());
        assertEquals(expected.get(), role);

        expected = underTest.findByRole("invalidRole");
        assertFalse(expected.isPresent());
    }
}