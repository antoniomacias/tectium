package com.ammacias.tectium.localdb;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.ammacias.tectium.localdb.CategoriaDB;

import com.ammacias.tectium.localdb.CategoriaDBDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig categoriaDBDaoConfig;

    private final CategoriaDBDao categoriaDBDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        categoriaDBDaoConfig = daoConfigMap.get(CategoriaDBDao.class).clone();
        categoriaDBDaoConfig.initIdentityScope(type);

        categoriaDBDao = new CategoriaDBDao(categoriaDBDaoConfig, this);

        registerDao(CategoriaDB.class, categoriaDBDao);
    }
    
    public void clear() {
        categoriaDBDaoConfig.getIdentityScope().clear();
    }

    public CategoriaDBDao getCategoriaDBDao() {
        return categoriaDBDao;
    }

}