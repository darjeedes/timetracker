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

        try {
            // load driver
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {

        }
    }

    @Override
    public BaseData getBaseData() {
        System.out.println("After Sucessfully insertion ");
        BaseData baseData1 = saveBaseData("Sumith");
        BaseData baseData2 = saveBaseData("Anoop");
        listBaseData();
//        System.out.println("After Sucessfully modification ");
//        updateBaseData(baseData1.getBaseDataId(), "Sumith Honai");
//        updateBaseData(baseData2.getBaseDataId(), "Anoop Pavanai");
//        listBaseData();
//        System.out.println("After Sucessfully deletion ");
//        deleteBaseData(baseData2.getBaseDataId());
//        listBaseData();

        return new BaseData();
    }

    public BaseData saveBaseData(String baseDataName) {
        BaseData baseData = new BaseData();
        try {
            entityManager.getTransaction().begin();
            baseData.setIssueManagementSystemBaseUrl(baseDataName);
            baseData = entityManager.merge(baseData);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
        return baseData;
    }

    public void listBaseData() {
        try {
            entityManager.getTransaction().begin();
            @SuppressWarnings("unchecked")
            List<BaseData> BaseDatas = entityManager.createQuery("from BaseData").getResultList();
            for (Iterator<BaseData> iterator = BaseDatas.iterator(); iterator.hasNext();) {
                BaseData baseData = (BaseData) iterator.next();
//                System.out.println(baseData.getBaseDataName());
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
    }
//
//    public void updateBaseData(Long baseDataId, String baseDataName) {
//        try {
//            entityManager.getTransaction().begin();
//            BaseData baseData = (BaseData) entityManager.find(BaseData.class, baseDataId);
//            baseData.setBaseDataName(baseDataName);
//            entityManager.getTransaction().commit();
//        } catch (Exception e) {
//            entityManager.getTransaction().rollback();
//        }
//    }
//
//    public void deleteBaseData(Long baseDataId) {
//        try {
//            entityManager.getTransaction().begin();
//            BaseData baseData = (BaseData) entityManager.find(BaseData.class, baseDataId);
//            entityManager.remove(baseData);
//            entityManager.getTransaction().commit();
//        } catch (Exception e) {
//            entityManager.getTransaction().rollback();
//        }
//    }

}


