package jsontosql;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

/**
 * @Author Sherlock.shen
 * @Date 2021/12/24 17:14
 * @Description:
 */
public class JsonToSql {

    public static void toSql(String tableName) throws IOException {
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
                insert = new StringBuffer("");
                value = new StringBuffer("");
                newInsert = new StringBuffer("");
                newValue = new StringBuffer("");
                JSONObject job = json.getJSONObject(i);
                insert.append("INSERT INTO `");
                insert.append(tableName);
                insert.append("`(");

                //fastjson解析方法
                for (Map.Entry<String, Object> entry : job.entrySet()) {
                    if (i == 0) {
                        sb.append("  `");
                        sb.append(entry.getKey());
                        sb.append("` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,\n");
                    }

                    insert.append("`");
                    insert.append(entry.getKey());
                    insert.append("`, ");

                    value.append("'");
                    value.append(entry.getValue());
                    value.append("', ");
                }

                newInsert.append(insert.substring(0, insert.length() - 2));
                newInsert.append(") VALUES (");

                newValue.append(value.substring(0, value.length() - 2));
                newInsert.append(newValue);
                newInsert.append(" ); \n");

                result.append(newInsert);
            }
        }

        sb.append(
                "  `DELETED_FLAG` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '是否删除(1:删除0:未删除)',\n" +
                        "  `CREATEDTIME` timestamp(0) NOT NULL DEFAULT current_timestamp(0) COMMENT '创建时间',\n" +
                        "  `CREATEDUSERID` bigint NOT NULL DEFAULT 1 COMMENT '创建人ID',\n" +
                        "  `UPDATEDTIME` timestamp(0) NOT NULL DEFAULT current_timestamp(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',\n" +
                        "  `UPDATEDUSERID` bigint NOT NULL DEFAULT 1 COMMENT '更新人ID',\n" +
                        "  PRIMARY KEY (`id`) USING BTREE\n" +
                        ")");

        System.out.println(sb.toString() + ";\n");
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
