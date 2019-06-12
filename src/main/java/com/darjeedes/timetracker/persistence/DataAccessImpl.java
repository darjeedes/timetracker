package com.darjeedes.timetracker.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.darjeedes.timetracker.domain.BaseData;

public class DataAccessImpl implements DataAccess {

    private EntityManager entityManager;

    public DataAccessImpl() {

        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("timetracker-persistence-unit");
        this.entityManager = emfactory.createEntityManager();
    }

    @Override
    public BaseData getBaseData() {
        BaseData baseData = entityManager.find(BaseData.class, 1);

        if (baseData == null) {
            // TODO: Make a backup of original .db file first.
            baseData = initBaseData();
        }

        return baseData;
    }

    /**
     * Saves a {@link TimeTrackerEntity} to the DB.
     *
     * @param timeTrackerEntity the entity to be saved
     */
    public void save(TimeTrackerEntity timeTrackerEntity) throws RuntimeException {

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(timeTrackerEntity);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException(
                    "Could not persist object of type `" + timeTrackerEntity.getClass().getName() + "`.");
        }
    }

    /**
     * Creates and persists a new {@link BaseData} entity.
     *
     * @return the newly created entity
     */
    private BaseData initBaseData() {
        BaseData baseData = new BaseData();
        save(baseData);
        return baseData;
    }

}
