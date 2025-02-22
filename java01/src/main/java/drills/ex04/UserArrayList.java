package drills.ex04;

public class UserArrayList implements UserList {
    private User[] users;
    private int size;


    public UserArrayList() {
        this.users = new User[10];
        this.size = 0;
    }

    @Override
    public void addUser(User user) {
        if (size == users.length) {
            User[] newUsers = new User[(users.length * 3) / 2];
            for (int i = 0; i < users.length; i++) {
                newUsers[i] = users[i];
            }
            users = newUsers;
        }
        users[size] = user;
        size++;
    }

    @Override
    public User getUserById(int id) throws UserNotFoundException {
        for (int i = 0; i < size; i++) {
            if (users[i].getId() == id) {
                return users[i];
            }
        }
        throw new UserNotFoundException(null);
    }

    @Override
    public User getUserByIndex(int index) throws UserNotFoundException {
        if (index < 0 || index >= size) {
            throw new UserNotFoundException(null);
        }
        return users[index];
    }

    @Override
    public int size() {
        return size;
    }
}