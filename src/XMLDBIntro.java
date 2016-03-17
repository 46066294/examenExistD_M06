import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;

import java.io.File;

//Part 2: MODE Administratiu
public class XMLDBIntro {
    private static String URI = "xmldb:exist://172.31.101.225:8080/exist/xmlrpc";
    private static String driver = "org.exist.xmldb.DatabaseImpl";

    public static void main(String args[]) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        afegirFitxer();

    }

    //Afegeixes un fitxer (Cano.xml) dins la nova coleccio cano
    private static void afegirFitxer() throws XMLDBException, ClassNotFoundException, IllegalAccessException, InstantiationException{
        File f = new File("Cano.xml");

        // initialize database driver
        Class cl = Class.forName(driver);
        Database database = (Database) cl.newInstance();
        database.setProperty("create-database", "true");

        // crear el manejador
        DatabaseManager.registerDatabase(database);

        //crear la collecion
        Collection parent = DatabaseManager.getCollection(URI + "/db","admin","dionis");//padre de la coleccion
        CollectionManagementService c = (CollectionManagementService) parent.getService("CollectionManagementService", "1.0");

        c.createCollection("cano");//nom de la coleccio

        Collection col = DatabaseManager.getCollection(URI + "/db/cano", "admin", "dionis");

        //afegir el recurs que farem servir
        Resource res = col.createResource("Cano.xml","XMLResource");
        res.setContent(f);
        col.storeResource(res);

    }
}