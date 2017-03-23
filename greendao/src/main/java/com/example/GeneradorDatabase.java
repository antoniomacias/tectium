package com.example;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class GeneradorDatabase {
    public static void main(String args[]) {
        // Definimos el esquema de la base de datos
        // PARAM 1: códigeo de versión: 1, 2, 3, 4
        // PARAM 2: el nombre del paquete del módule app de Android
        // donde queremos volcar los POJOS y demás ficheros de la base de datos
        Schema schema = new Schema(1, "com.ammacias.tectium.localdb");

        // Definimos las entidades (tablas) de la base de datos
        Entity categoriaDB = schema.addEntity("CategoriaDB");
        categoriaDB.addIdProperty().autoincrement().primaryKey();
        categoriaDB.addStringProperty("nombre");
        categoriaDB.addStringProperty("descripcion");
        categoriaDB.addStringProperty("tag");

        try {
            new DaoGenerator().generateAll(schema, "./app/src/main/java/");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
