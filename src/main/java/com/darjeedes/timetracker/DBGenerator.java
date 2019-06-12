package com.darjeedes.timetracker;

import java.util.EnumSet;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;

public class DBGenerator {

    /**
     * Looks up configuration in hibernate.cfg.xml and creates a usable database by processing annotations from code.
     * Not needed at the moment, because hibernate creates a database if it doesn't find one. Will be useful later,
     * when we allow the user to create, save and load *.db files manually.
     * <br>
     * Attention: If you run this, e.g. from DBGeneratorRunner, <bold>you will overwrite the existing database</bold>.
     */
    public static void createDatabase() {
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder() //
                .configure("META-INF/hibernate.cfg.xml") //
                .build();
        Metadata metadata = new MetadataSources(serviceRegistry).buildMetadata();

        new SchemaExport().create(EnumSet.of(TargetType.DATABASE), metadata);

        metadata.buildSessionFactory().close();
    }

}
