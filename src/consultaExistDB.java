/**
 * Created by 47419119l on 17/03/16.
 */
import net.xqj.exist.ExistXQDataSource;
import javax.xml.xquery.*;
public class consultaExistDB {
    public static void main(String[] args) throws XQException {


       // String xquery100 = "(collection('47419119l')/mondial/country[name='China']/population[@year='2013'])div(count(collection('xmls')/mondial/country[name='China']/province))";

        //String xpath = "collection('plantes.xml')/CATALOG/PLANT/COMMON";
        String xpath = "/CATALOG/PLANT/COMMON";
        //String xpath = "/CATALOG/PLANT";
        String query = consulta(xpath);
        //System.out.println(query);

        String [] name = query.replaceAll("</COMMON>","").split("<COMMON>");

        System.out.println("Hi han "+(name.length -1) +" plantes");
        for (int i = 0; i < name.length; i++) {
            System.out.println(name[i]);
        }

    }

    public static String consulta (String xPath) throws XQException {
        String resultado = "";
        String linea = "";
        XQDataSource xqs = new ExistXQDataSource();
        xqs.setProperty("serverName", "172.31.101.225");
        xqs.setProperty("port", "8080");

        XQConnection conn = xqs.getConnection();

        XQPreparedExpression xqpe = conn.prepareExpression(xPath);

        XQResultSequence rs = xqpe.executeQuery();

        while (rs.next()){
            linea = rs.getItemAsString(null);
            resultado += linea;
        }
        conn.close();
        System.out.println(resultado);
        return resultado;
    }
}
