package com.darjeedes.timetracker.persistence;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.darjeedes.timetracker.domain.BaseData;
import com.darjeedes.timetracker.domain.Context;

public class DataAccessImpl implements DataAccess {

    private EntityManager entityManager;

    public DataAccessImpl() {

        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("test");

        this.entityManager = emfactory.createEntityManager();

//        try {
        // load driver
//            Class.forName("org.h2.Driver");
//            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
//        } catch (ClassNotFoundException e) {
//
//        }
    }

    @Override
    public BaseData getBaseData() {
        BaseData baseData = null;

        try {
            entityManager.getTransaction().begin();
//            baseData = entityManager.find(BaseData.class, baseData.getId());
            baseData = entityManager.find(BaseData.class, 1);
//            List<BaseData> BaseDatas = entityManager.createQuery("from BaseData").getResultList();
//            System.out.println(BaseDatas.toString());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }

        return (baseData != null) ? baseData : new BaseData();
    }

    @Override
    public BaseData saveBaseData(BaseData baseData) {

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(baseData);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }

        return baseData;

    }

    @Override
    public Context saveContext(final Context context) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(context);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }

        return context;
    }

    /**
     * Saves an Entity to the DB. Returns null on fail.
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

    public void listBaseData() {
        try {
            entityManager.getTransaction().begin();
            @SuppressWarnings("unchecked")
            List<BaseData> BaseDatas = entityManager.createQuery("from BaseData").getResultList();
            for (Iterator<BaseData> iterator = BaseDatas.iterator(); iterator.hasNext(); ) {
                BaseData baseData = (BaseData) iterator.next();
//                System.out.println(baseData.getBaseDataName());
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
    }

}


