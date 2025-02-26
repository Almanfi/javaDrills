package drills.ex02;

public interface UserList {
    public void addUser(User user);
    public User getUserById(int id) throws UserNotFoundException;
    public User getUserByIndex(int index) throws UserNotFoundException;
    public int size();

    public class UserNotFoundException extends Exception {
        public UserNotFoundException(String message) {
            super(message);
        }
    }
}
