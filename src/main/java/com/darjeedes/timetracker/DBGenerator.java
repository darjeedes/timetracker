package com.darjeedes.timetracker;

import java.util.EnumSet;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;

public class DBGenerator {

    public static void createDatabase() {
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder() //
                .configure("META-INF/hibernate.cfg.xml") //
                .build();
        Metadata metadata = new MetadataSources(serviceRegistry).buildMetadata();

        new SchemaExport().create(EnumSet.of(TargetType.DATABASE), metadata);

        metadata.buildSessionFactory().close();
    }

}
