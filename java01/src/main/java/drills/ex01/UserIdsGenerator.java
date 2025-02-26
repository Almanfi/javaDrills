package drills.ex01;

import drills.utils.*;

public class UserIdsGenerator {
    static private UserIdsGenerator instance;
    private int id;

    private UserIdsGenerator() {
        this.id = 0;
    }
    
    public static synchronized UserIdsGenerator getInstance() {
        if (instance == null) {
            instance = new UserIdsGenerator();
        }
        return instance;
    }

    public synchronized int generateId() {
        return id++;
    }

    @TestOnly
    static synchronized void _rest() {
        instance = null;
    }
}
