package de.lunoro.locker.lock.sql;

import de.lunoro.locker.lock.Lock;
import de.lunoro.locker.sql.SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LockSQLLoader {

    LockSQLSerializer lockSQLSerializer;

    private static LockSQLLoader instance;

    public void save(Lock lock) {
        getLockSQLSerializer().serialize(lock);
    }

    public List<Lock> load() {
        ResultSet resultSet = SQL.getInstance().getResult("SELECT * FROM Locker");
        List<Lock> lockList = new ArrayList<>();
        while (hasNext(resultSet)) {
            Lock lock = getLockSQLSerializer().deserialize(resultSet);
            lockList.add(lock);
        }
        return lockList;
    }

    private boolean hasNext(ResultSet resultSet) {
        try {
            if (resultSet.next()) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void clear() {
        SQL.getInstance().update("TRUNCATE Locker");
    }

    private LockSQLSerializer getLockSQLSerializer() {
        if (lockSQLSerializer == null) {
            return new LockSQLSerializer();
        }
        return lockSQLSerializer;
    }

    public static LockSQLLoader getInstance() {
        if (instance == null) {
            return new LockSQLLoader();
        }
        return instance;
    }
}
