package jsontosql;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.Map;

public class JsonToSql2 {


    public static void toSql2(String tableName) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("ceshi.txt");
        String jsonStr = readFileContent(classPathResource.getURL().getPath());
        StringBuffer sb = new StringBuffer();
        sb.append("CREATE TABLE `");
        sb.append(tableName);
        sb.append("`  (\n" +
                "  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',");

        StringBuffer insert = new StringBuffer();
        StringBuffer value = new StringBuffer();
        StringBuffer newInsert = new StringBuffer();
        StringBuffer newValue = new StringBuffer();
        StringBuffer result = new StringBuffer();
        JSONArray json = JSONArray.parseArray(jsonStr);
        if (json.size() > 0) {
            for (int i = 0; i < json.size(); i++) {
                JSONObject jo = json.getJSONObject(i);

                JSONObject jo2 = jo.getJSONObject("geometry");


                JSONObject jo3 = jo.getJSONObject("properties");
                String NAME = jo3.getString("MC");
                String XZQH_CODE = jo3.getString("SSSQDM");
                String ADDRESS = jo3.getString("XZQHDM");
                String sqlInsert = "{\"type\":\"GeometryCollection\", \"geometries\": [\n" +
                        "{\"type\":\"Polygon\",\"coordinates\":" + jo2.getString("coordinates") +"}" +
                        "]}'";



                insert = new StringBuffer("");
                value = new StringBuffer("");
                newInsert = new StringBuffer("");
                newValue = new StringBuffer("");
                JSONObject job = json.getJSONObject(i);
                insert.append("INSERT INTO `");
                insert.append(tableName);
                insert.append("`( 'NAME','XZQH_CODE','ADDRESS','DATA' ");
                result.append(insert);
                newInsert.append(") VALUES (" + "'"+ NAME +"',"+ "'" + XZQH_CODE+"','" + ADDRESS +"'," +"'" +sqlInsert +"");

                newInsert.append("); \n");

                result.append(newInsert);
            }
        }
        try (FileWriter fileWriter = new FileWriter("C:\\Users\\sherlock.shen\\Desktop\\ceshi111.txt")) {
            fileWriter.append(result.toString());
        }

        System.out.println(result.toString());
    }



    public static String readFileContent(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr);
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }
}
