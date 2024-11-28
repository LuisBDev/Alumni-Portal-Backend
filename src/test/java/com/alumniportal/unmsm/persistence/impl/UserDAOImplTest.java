package com.alumniportal.unmsm.persistence.impl;

import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.repository.IUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserDAOImplTest {

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private UserDAOImpl userDAO;

    @Test
    void findAll_ReturnsListOfUsers_WhenUsersExist() {
        List<User> users = List.of(new User(), new User());
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userDAO.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void findAll_ReturnsEmptyList_WhenNoUsersExist() {
        when(userRepository.findAll()).thenReturn(List.of());

        List<User> result = userDAO.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void findById_ReturnsUser_WhenUserExists() {
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userDAO.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void findById_ReturnsNull_WhenUserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        User result = userDAO.findById(1L);

        assertNull(result);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void save_SavesUserSuccessfully_WhenValidUserProvided() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        userDAO.save(user);

        verify(userRepository, times(1)).save(user);
    }
    

    @Test
    void deleteById_DeletesUserSuccessfully_WhenUserExists() {
        doNothing().when(userRepository).deleteById(1L);

        userDAO.deleteById(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteById_ThrowsException_WhenUserDoesNotExist() {
        doThrow(new RuntimeException("User not found")).when(userRepository).deleteById(1L);

        assertThrows(RuntimeException.class, () -> userDAO.deleteById(1L));
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void existsByEmail_ReturnsTrue_WhenEmailExists() {
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        boolean result = userDAO.existsByEmail("test@example.com");

        assertTrue(result);
        verify(userRepository, times(1)).existsByEmail("test@example.com");
    }

    @Test
    void existsByEmail_ReturnsFalse_WhenEmailDoesNotExist() {
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);

        boolean result = userDAO.existsByEmail("test@example.com");

        assertFalse(result);
        verify(userRepository, times(1)).existsByEmail("test@example.com");
    }

    @Test
    void findByEmail_ReturnsUser_WhenEmailExists() {
        User user = new User();
        user.setEmail("test@example.com");

        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        User result = userDAO.findByEmail("test@example.com");

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        verify(userRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    void findByEmail_ReturnsNull_WhenEmailDoesNotExist() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(null);

        User result = userDAO.findByEmail("test@example.com");

        assertNull(result);
        verify(userRepository, times(1)).findByEmail("test@example.com");
    }

}