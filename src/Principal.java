import net.xqj.exist.ExistXQDataSource;

import javax.xml.xquery.*;
import java.util.Scanner;

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
        Scanner input = new Scanner(System.in);

        xqs.setProperty("serverName","localhost");//172.31.101.225
        xqs.setProperty("port","8080");
        xconn = xqs.getConnection();
        System.out.println("\n...sessió oberta correctament");


        try {

            String menu = "";
            boolean on = true;//condicio de sortida del programa


            while (on) {
                System.out.println("\n");
                System.out.println("MENU:");
                System.out.println(" 1--> Quins països hi ha en el fitxer «factbook.xml»?");
                System.out.println(" 2--> Quants països hi ha?");
                System.out.println(" 3--> Quina és la informació sobre Alemanya ?");
                System.out.println(" 4--> Quanta gent viu a Uganda segons aquest fitxer?");
                System.out.println(" 5--> Quines són les ciutats de Perú que recull aquest fitxer?");
                System.out.println(" 6--> Quanta gent viu a Shanghai?");
                System.out.println(" 7--> Quin és el codi de matricula de cotxe de Xipre?");
                System.out.println(" 0--> Salir del programa");
                System.out.println(" ");
                menu = input.nextLine();

                switch (menu) {
                    case "0": {
                        System.out.println("\n...salir");
                        on = false;
                        break;
                    }

                    case "1": {

                        String query = "collection('mondial.xml')/count(/mondial/country)";

                        mostrarQuery (query);
                        break;
                    }

                    case "2": {

                        break;
                    }

                    case "3": {

                        break;
                    }

                    case "4": {

                        break;
                    }

                    case "5": {

                        break;
                    }

                    case "6": {

                        break;
                    }

                    case "7": {

                        break;
                    }

                    default: {
                        System.out.println("\n...entrada de menu incorrecta\n");
                        break;
                    }

                }//switch

            }
        } catch(Exception e){
            e.getStackTrace();
        }
        finally {
            input.close();
            System.out.println("...sessió tancada");
        }





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
    }//main

    public static void mostrarQuery(String q) throws XQException {
        xqpe = xconn.prepareExpression(q);
        xqResult = xqpe.executeQuery();
        while(xqResult.next()){
            System.out.println(xqResult.getItemAsString(null));
        }
    }

}//Principal
