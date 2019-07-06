package com.darjeedes.timetracker.persistence.low;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.darjeedes.timetracker.domain.BaseData;
import com.darjeedes.timetracker.domain.TimeTrackerEntity;

public class DataAccessImpl implements DataAccess {

    private EntityManager entityManager;

    public DataAccessImpl() {

        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("timetracker-persistence-unit");
        this.entityManager = emfactory.createEntityManager();
    }

    @Override
    public BaseData getBaseData() {
        BaseData baseData = this.entityManager.find(BaseData.class, 1);

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
    public void save(TimeTrackerEntity timeTrackerEntity) {

        try {
            this.entityManager.getTransaction().begin();
            this.entityManager.persist(timeTrackerEntity);

            this.entityManager.getTransaction().commit();
        } catch (Exception e) {
            this.entityManager.getTransaction().rollback();
            throw new RuntimeException(
                    "Could not persist object of type `" + timeTrackerEntity.getClass().getName() + "`.");
        }
    }

    @Override
    public void update(final TimeTrackerEntity timeTrackerEntity) {
        try {
            this.entityManager.getTransaction().begin();
            this.entityManager.persist(timeTrackerEntity);

            this.entityManager.getTransaction().commit();
        } catch (Exception e) {
            this.entityManager.getTransaction().rollback();
            throw new RuntimeException(
                    "Could not persist object of type `" + timeTrackerEntity.getClass().getName() + "`.");
        }
    }

    @Override
    public void delete(final TimeTrackerEntity timeTrackerEntity) {
        try {
            this.entityManager.getTransaction().begin();
            this.entityManager.remove(timeTrackerEntity);

            this.entityManager.getTransaction().commit();
        } catch (Exception e) {
            this.entityManager.getTransaction().rollback();
            throw new RuntimeException(
                    "Could not remove object of type `" + timeTrackerEntity.getClass().getName() + "`.");
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
