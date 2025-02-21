package drills.ex02;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class UserListTest {
    @Test
    public void UserListAddUserTest() {
        UserList userList = new UserArrayList();
        User user = new User("John Doe", 1000);
        userList.addUser(user);
        assertDoesNotThrow(() -> {
            assertEquals(userList.getUserByIndex(0), user);
        });
    }

    @Test
    public void UserListGetUserByIdTest() {
        UserList userList = new UserArrayList();
        User user = new User("John Doe", 1000);
        userList.addUser(user);
        assertDoesNotThrow(() -> {
            assertEquals(userList.getUserById(user.getId()), user);
        });
    }

    @Test
    public void UserListGetUserByIdThrowsOnUserNotFound() {
        UserList userList = new UserArrayList();
        User user = new User("John Doe", 1000);
        int newId = user.getId() + 1;
        userList.addUser(user);
        assertThrows(UserList.UserNotFoundException.class, () -> {
            userList.getUserById(newId);
        });
    }

    @Test
    public void UserListGetUserByIndexTest() {
        UserList userList = new UserArrayList();
        User user = new User("John Doe", 1000);
        userList.addUser(user);
        assertDoesNotThrow(() -> {
            assertEquals(userList.getUserByIndex(0), user);
        });
    }

    @Test
    public void UserListGetUserByIndexThrowsOnUserNotFound() {
        UserList userList = new UserArrayList();
        assertThrows(UserList.UserNotFoundException.class, () -> {
            userList.getUserById(0);
        });
    }

    @Test
    public void UserListSizeTest() {
        UserList userList = new UserArrayList();
        assertEquals(userList.size(), 0);
        User user = new User("John Doe", 1000);
        userList.addUser(user);
        assertEquals(userList.size(), 1);
        User user2 = new User("smith beth", 2000);
        userList.addUser(user2);
        assertEquals(userList.size(), 2);
    }
    
}
