import net.xqj.exist.ExistXQDataSource;

import javax.xml.xquery.*;

//    /home/46066294p/eXist-db/webapp/WEB-INF/data/fs/db/mondial
//     172.31.83.21
//     http://172.31.101.225:8080/exist/apps/dashboard/index.html

/**
 * Created by 46066294p on 18/02/16.
 */
public class Principal {
    static XQDataSource xqs=new ExistXQDataSource();
    static XQConnection xconn;
    static XQPreparedExpression xqpe;
    static XQResultSequence xqResult;

    public static void main(String[]args) throws XQException {

        xqs.setProperty("serverName","172.31.101.225");
        xqs.setProperty("port","8080");
        xconn = xqs.getConnection();



        //prova inicial
        System.out.println("PROVA INICIAL\n");

        String query = "for $country in /mondial//country[count(.//city) > 2]\n" +
                "let $cities_pops := (\n" +
                "\tfor $c in $country//city[population]\n" +
                "\tlet $pnum := number($c/population[1])\n" +
                "\torder by $pnum descending\n" +
                "\treturn $c/population[1]\n" +
                ")\n" +
                "return\n" +
                "<result>\n" +
                "\t{$country/name}\n" +
                "\t<three-cities>\n" +
                "\t\t{sum($cities_pops[position()<=3])}\n" +
                "\t</three-cities>\n" +
                "</result>";

        mostrarQuery (query);


        //1. Doneu el nom del país de major població (que és Xina, però heu de fer la consulta).
        System.out.println("\n\n1. Doneu el nom del país de major població (que és Xina, però heu de fer la consulta).\n");

        String query1 = "for $ctr in /mondial/country\n" +
                "where($ctr/population = max(/mondial/country/population))\n" +
                "return\n" +
                "<result>\n" +
                "\t{$ctr/name}\n" +
                "\t{$ctr/population}\n" +
                "</result>";

        mostrarQuery (query1);

        //2. Quins llacs, rius o mars comparteixen Rússia amb exactament un altre país.
        System.out.println("\n\n2. Quins llacs, rius o mars comparteixen Rússia amb exactament un altre país.\n");

        String query2 = "for $water in fn:doc(\"xmls/mondial.xml\")//(lake|river|sea) "+
                "where $water/located/@country='Russia' "+
                "and count($water/located/@country) = 2 "+
                "order by $water/name "+
                "return element {$water/name()} {$water/name/text()} ";

        mostrarQuery (query2);

        xconn.close();
    }

    public static void mostrarQuery(String q) throws XQException {
        xqpe = xconn.prepareExpression(q);
        xqResult = xqpe.executeQuery();
        while(xqResult.next()){
            System.out.println(xqResult.getItemAsString(null));
        }
    }

}//Principal
