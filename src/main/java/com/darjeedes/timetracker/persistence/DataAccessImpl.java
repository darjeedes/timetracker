package com.darjeedes.timetracker.persistence;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.darjeedes.timetracker.domain.BaseData;

public class DataAccessImpl implements DataAccess {

    private EntityManager entityManager;

    public DataAccessImpl() {

        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("test");
        this.entityManager = emfactory.createEntityManager();
    }

    @Override
    public BaseData getBaseData() {
        BaseData baseData = null;

        try {
            entityManager.getTransaction().begin();
            baseData = entityManager.find(BaseData.class, 1);
//            List<BaseData> BaseDatas = entityManager.createQuery("from BaseData").getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }

        // TODO: Could be tricky, as soon as baseData is null, everything gets overwritten in DB.
        if (baseData == null) {
            baseData = new BaseData();
            save(baseData);
        }

        return baseData;
    }

    /**
     * Saves an Entity to the DB. Returns null on fail.
     *
     * @param timeTrackerEntity
     * @return
     */
    public TimeTrackerEntity save(TimeTrackerEntity timeTrackerEntity) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(timeTrackerEntity);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return null;
        }

        return timeTrackerEntity;
    }

}


