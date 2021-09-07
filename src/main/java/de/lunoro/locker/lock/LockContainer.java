package de.lunoro.locker.lock;

import de.lunoro.locker.Locker;
import lombok.Getter;

import java.nio.file.Paths;
import java.util.List;

public class LockContainer {

    @Getter
    private static final LockContainer instance = new LockContainer();
    private List<Lock> lockList;
    private final LockLoader lockLoader;

    private LockContainer() {
        lockLoader = new LockLoader(Paths.get(Locker.getInstance().getConfigDir() + "\\lock.conf"));
    }

    public void load() {
        lockList = lockLoader.load();
    }

    public void save() {
        lockLoader.clear();
        for (Lock lock : lockList) {
            lockLoader.save(lock);
        }
    }
}
