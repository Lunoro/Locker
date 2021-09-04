package de.lunoro.locker.lock;

import java.util.ArrayList;
import java.util.List;

public class LockContainer {

    private static final LockContainer instance = new LockContainer();
    private List<Lock> lockList;

    private LockContainer() {

    }

    private List<Lock> fillList() {
        List<Lock> list = new ArrayList<>();
        return list;
    }

    public void saveLock(Lock lock) {
        lockList.add(lock);
    }
}
