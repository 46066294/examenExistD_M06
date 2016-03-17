import net.xqj.exist.ExistXQDataSource;

import javax.xml.xquery.*;

//Part 1: MODE XQJ
public class Principal {
    public static String nomFitxer = "UF3-ExamenF-Plantes.xml";
    public static String ipMaquina = "172.31.101.225";

    public static XQConnection conn = null;

    public static void main(String[]args) throws XQException {

        iniciarConexio();
        p1();
        p2();
        //p3();
        tancarConexio();

    }//main


    //::::::::::METODES::::::::::


    private static void iniciarConexio() throws XQException {
        XQDataSource xqs = new ExistXQDataSource();
        xqs.setProperty("serverName", ipMaquina);
        xqs.setProperty("port", "8080");
    }

    private static void tancarConexio() throws XQException {
        conn.close();
    }

    //query 1
    public static void p1 () throws XQException {//+ nomFitxer +

        String xpath = "doc('" + nomFitxer + "')/CATALOG/PLANT[AVAILABILITY = max(/CATALOG/PLANT/AVAILABILITY)]/COMMON/text()";

        String resultado = "";
        String linea = "";
        XQDataSource xqs = new ExistXQDataSource();
        xqs.setProperty("serverName", ipMaquina);
        xqs.setProperty("port", "8080");

        XQConnection conn = xqs.getConnection();

        XQPreparedExpression xqpe = conn.prepareExpression(xpath);// /library/book[@id = max(/library/book/@id)]

        XQResultSequence rs = xqpe.executeQuery();

        while (rs.next()){
            linea = rs.getItemAsString(null);
            resultado += linea + "\n";
            //System.out.println(linea);
        }

        System.out.println(resultado + " es la planta de la que tenim mes stoc");
    }



    //query 2
    public static void p2 () throws XQException {// sum(/Xavor/Dev[2]/Salary/text())
        String xpath = "sum(doc(\"" + nomFitxer + "\")/CATALOG/PLANT/AVAILABILITY)";

        String resultado = "";
        String linea = "";
        XQDataSource xqs = new ExistXQDataSource();
        xqs.setProperty("serverName", ipMaquina);
        xqs.setProperty("port", "8080");

        XQConnection conn = xqs.getConnection();

        XQPreparedExpression xqpe = conn.prepareExpression(xpath);

        XQResultSequence rs = xqpe.executeQuery();

        while (rs.next()){
            linea = rs.getItemAsString(null);
            resultado += linea + "\n";
            break;
            //System.out.println(linea);
        }

        System.out.println("Total " + resultado + " plantes en stoc.");
    }

}//Principal
