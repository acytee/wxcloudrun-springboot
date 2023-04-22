package com.tencent.wxcloudrun.utils;


import com.google.common.base.CaseFormat;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 实体代码生成工具
 *
 * @author chenzg
 */
public class EntityBatch {

    private static String projectDir = System.getProperty("user.dir");
    private static String type = "manage";
    private static String tableSchema = "home_parking_space";
    private static String pathSTr = projectDir + "\\src\\main\\java\\com\\tencent\\wxcloudrun";
    private static String controllerSTr = projectDir + "\\src\\main\\java\\com\\tencent\\wxcloudrun\\controller";
    private static String serviceSTr = projectDir + "\\src\\main\\java\\com\\tencent\\wxcloudrun\\service";
    private static String tableName;
    private static String tableComment;
    private static String className;
    private static String pkName;
    private static String driverClassName = "com.mysql.cj.jdbc.Driver";
    private static String url = "jdbc:mysql://sh-cynosdbmysql-grp-jo6wlxpw.sql.tencentcdb.com:26214/home_parking_space";
    private static String username = "root";
    private static String password = "7DT2gVQb";

    public static void main(String[] args) throws Exception {
        /*String driverClassName, url, username, password;
        Yaml yaml = new Yaml();
        File dumpFile = Paths.get(projectDir + "\\src\\main\\resources\\application.yml").toFile();
        InputStream inputStream = new FileInputStream(dumpFile);
        try {
            Map<String, Object> map = yaml.load(inputStream);
            Map<String, Object> datasourceMap = (Map<String, Object>) ((Map) map.get("spring")).get("datasource");
            driverClassName = datasourceMap.get("driver-class-name") + "";
            url = datasourceMap.get("url") + "";
            username = datasourceMap.get("username") + "";
            password = datasourceMap.get("password") + "";
        } finally {
            inputStream.close();
        }*/
//        String table = "plugin_goods,plugin_shop";
//        String table = "plugin_goods_norms_price";
        String table = "Counters";
        Class.forName(driverClassName);
        Connection conn = DriverManager.getConnection(url, username, password);
        try {
            Statement stat = conn.createStatement();
            for (String tn : table.split(",")) {
                ResultSet rs = stat.executeQuery("select table_name,table_comment from information_schema.`TABLES` where table_name like '" + tn + "%'");
                while (rs.next()) {
                    tableName = rs.getString(1);
                    tableComment = rs.getString(2);
                    createEntity(driverClassName, url, username, password);
                }
            }
        } catch (Exception e) {

        } finally {
            conn.close();
        }

    }

    private static StringBuilder createService(String pn, String tableComment) throws Exception {
        StringBuilder str = new StringBuilder("package com.tencent.wxcloudrun.service;\n\n");
        str.append("import com.tencent.wxcloudrun.config.ApiResponse;\n" +
                "import com.tencent.wxcloudrun.dto.BaseBodyDelete;\n" +
                "import com.tencent.wxcloudrun.dto.BaseBodyDetail;\n" +
                "import com.tencent.wxcloudrun.dto.ResultList;\n" +
                "import com.tencent.wxcloudrun.utils.SqlUtil;\n" +
                "import com.tencent.wxcloudrun.utils.StrUtil;\n" +
                "import com.tencent.wxcloudrun.dao." + pn + "." + className + "Repository;\n" +
                "import com.tencent.wxcloudrun.model." + pn + "." + className + ";\n" +
                "import com.tencent.wxcloudrun.dto." + pn + "." + className + "Get;\n" +
                "import com.tencent.wxcloudrun.dto." + pn + "." + className + "Post;\n" +
                "import com.tencent.wxcloudrun.dto." + pn + "." + className + "Put;\n" +
                "import org.springframework.beans.BeanUtils;\n" +
                "import org.springframework.beans.factory.annotation.Autowired;\n" +
                "import org.springframework.stereotype.Service;\n" +
                "\n" +
                "import javax.transaction.Transactional;\n" +
                "import java.util.Arrays;\n" +
                "import java.util.Date;\n" +
                "import java.util.Optional;\n\n");
        str.append("/**\n" +
                " * " + tableComment + "\n" +
                " *\n" +
                " * @author : chenzg\n" +
                " * @date : " + DateUtil.dateString1(new Date()) + "\n */\n");
        String sn = className;
        sn = sn.substring(0, 1).toLowerCase() + sn.substring(1);
        str.append("@Service\n" +
                "public class " + className + "Service {\n" +
                "\n" +
                "    @Autowired\n" +
                "    private SqlUtil sqlUtil;\n" +
                "    @Autowired\n" +
                "    private " + className + "Repository " + sn + "Repository;\n" +
                "\n" +
                "    @Transactional(rollbackOn = Exception.class)\n" +
                "    public ApiResponse post(" + className + "Post body) {\n" +
                "        " + className + " obj = new " + className + "();\n" +
                "        BeanUtils.copyProperties(body, obj, StrUtil.getNullPropertyNames(body));\n" +
                "        String id = " + sn + "Repository.save(obj).get" + toClassName(pkName) + "();\n" +
                "        return ApiResponse.success(id);\n" +
                "    }\n" +
                "\n" +
                "    @Transactional(rollbackOn = Exception.class)\n" +
                "    public ApiResponse delete(BaseBodyDelete body) {\n" +
                "        Optional<" + className + "> optional = " + sn + "Repository.findById(body.getId());\n" +
                "        if (!optional.isPresent()) {\n" +
                "            return ApiResponse.failNoData();\n" +
                "        }\n" +
                "        " + sn + "Repository.deleteBy" + toClassName(pkName) + "(Arrays.asList(body.getId().split(\",\")));\n" +
                "        return ApiResponse.success();\n" +
                "    }\n" +
                "\n" +
                "    @Transactional(rollbackOn = Exception.class)\n" +
                "    public ApiResponse put(" + className + "Put body) {\n" +
                "        Optional<" + className + "> optional = " + sn + "Repository.findById(body.getId());\n" +
                "        if (!optional.isPresent()) {\n" +
                "            return ApiResponse.failNoData();\n" +
                "        }\n" +
                "        " + className + " obj = optional.get();\n" +
                "        obj.setUpdateTime(new Date());\n" +
                "        obj.setUpdateUserId(body.getCreateUserId());\n" +
                "        BeanUtils.copyProperties(body, obj, StrUtil.getNullPropertyNames(body));\n" +
                "        return ApiResponse.success();\n" +
                "    }\n" +
                "\n" +
                "    public ResultList get(" + className + "Get body) {\n" +
                "        String sql = \"select queryField \" +\n" +
                "                \n" +
                "                \"from " + tableName + " t1\";\n" +
                "        return sqlUtil.get(sql, body, null, null, null);\n" +
                "    }\n" +
                "\n" +
                "    public ApiResponse detail(BaseBodyDetail body) throws Exception {\n" +
                "        String sql = \"select queryField \" +\n" +
                "                \n" +
                "                \"from " + tableName + " t1\";\n" +
                "        return sqlUtil.detail(sql, body, null, null, null);\n" +
                "    }\n" +
                "}");
        String dir = serviceSTr + "\\" + pn + "\\service";
        new File(dir).mkdirs();
        File f = new File(dir + "/" + className + "Service.java");
        if (!f.exists()) {
            f.createNewFile();
        }
        return str;
    }

    private static StringBuilder createController(String pn, String tableComment) throws Exception {
        StringBuilder str = new StringBuilder("package com.wp.seal.v11." + type + ".plugins." + pn + ".web;\n\n");
        str.append("import com.tencent.wxcloudrun.dto.").append(pn).append(".").append(className).append("Post;\n");
        str.append("import com.tencent.wxcloudrun.dto.").append(pn).append(".").append(className).append("Put;\n");
        str.append("import com.tencent.wxcloudrun.dto.").append(pn).append(".").append(className).append("Get;\n");
        str.append("import com.tencent.wxcloudrun.service.").append(className).append("Service;\n");
        str.append("import com.tencent.wxcloudrun.config.ApiResponse;\n" +
                "import com.tencent.wxcloudrun.model.JsonPage2;\n" +
                "import com.tencent.wxcloudrun.dto.BaseBodyDelete;\n" +
                "import com.tencent.wxcloudrun.dto.BaseBodyDetail;\n" +
                "import io.swagger.annotations.Api;\n" +
                "import io.swagger.annotations.ApiOperation;\n" +
                "import org.springframework.beans.factory.annotation.Autowired;\n" +
                "import org.springframework.security.access.prepost.PreAuthorize;\n" +
                "import org.springframework.web.bind.annotation.*;\n\n");
        str.append("/**\n" +
                " * " + tableComment + "\n" +
                " *\n" +
                " * @author : chenzg\n" +
                " * @date : " + DateUtil.dateString1(new Date()) + "\n */\n");
        String url = className;
        url = lowerUnderscore(url);
        url = url.replace("_", "/");
        str.append("@Api(tags = {\"" + tableComment + "\"})\n" +
                "@RequestMapping(\"/" + url + "\")\n" +
                "@RestController\n");
        String sn = className + "Service";
        sn = sn.substring(0, 1).toLowerCase() + sn.substring(1);
        String s1 = "hasAnyAuthority('ADMIN')  AND @mpa.init(#body)";
        String s2 = "hasAnyAuthority('SHOP','ADMIN')  AND @mpa.init(#body)";

        str.append("public class " + className + "Controller {\n" +
                "\n" +
                "    @Autowired\n" +
                "    private " + className + "Service " + sn + ";\n" +
                "\n" +
                "    @ApiOperation(\"创建\")\n" +
                "    @PreAuthorize(\"" + s1 + "\")\n" +
                "    @PostMapping\n" +
                "    public ApiResponse post(@RequestBody " + className + "Post body) {\n" +
                "        return " + sn + ".post(body);\n" +
                "    }\n" +
                "\n" +
                "    @ApiOperation(\"删除\")\n" +
                "    @PreAuthorize(\"" + s1 + "\")\n" +
                "    @DeleteMapping(\"/delete\")\n" +
                "    public ApiResponse delete(@RequestBody BaseBodyDelete body) {\n" +
                "        return " + sn + ".delete(body);\n" +
                "    }\n" +
                "\n" +
                "    @ApiOperation(\"修改\")\n" +
                "    @PreAuthorize(\"" + s1 + "\")\n" +
                "    @PutMapping(\"/put\")\n" +
                "    public ApiResponse put(@RequestBody " + className + "Put body) {\n" +
                "        return " + sn + ".put(body);\n" +
                "    }\n" +
                "\n" +
                "    @ApiOperation(\"列表\")\n" +
                "    @PreAuthorize(\"" + s2 + "\")\n" +
                "    @PostMapping(\"/get\")\n" +
                "    public ApiResponse get(@RequestBody " + className + "Get body) {\n" +
                "        return ApiResponse.success(new JsonPage2(" + sn + ".get(body), body));\n" +
                "    }\n" +
                "\n" +
                "    @ApiOperation(\"详情\")\n" +
                "    @PreAuthorize(\"" + s2 + "\")\n" +
                "    @PostMapping(\"/detail\")\n" +
                "    public ApiResponse detail(@RequestBody BaseBodyDetail body) throws Exception {\n" +
                "        return " + sn + ".detail(body);\n" +
                "    }\n" +
                "}");
        String dir = controllerSTr + "\\" + pn + "\\web";
        new File(dir).mkdirs();
        File f = new File(dir + "/" + className + "Controller.java");
        if (!f.exists()) {
            f.createNewFile();
        }
        return str;
    }

    private static StringBuilder createBodyPost(String pn, String tableComment) throws Exception {
        StringBuilder str = new StringBuilder("package com.tencent.wxcloudrun.dto." + pn + ";\n\n");
        str.append("import com.tencent.wxcloudrun.model." + pn + "." + className + ";\n");
        str.append("import io.swagger.annotations.ApiModel;\n");
        str.append("import lombok.Data;\n" +
                "import lombok.EqualsAndHashCode;\n\n");
        str.append("/**\n * @author : chenzg\n" +
                " * @date : " + DateUtil.dateString1(new Date()) + "\n */\n");
        str.append("@Data\n" +
                "@EqualsAndHashCode(callSuper = true)\n" +
                "@ApiModel(description = \"" + tableComment + "\", value = \"" + className + "Post\")\n");
        str.append("public class " + className + "Post extends " + className + " {\n}");
        String dir = pathSTr + "\\request\\" + pn;
        new File(dir).mkdirs();
        File f = new File(dir + "/" + className + "Post.java");
        if (!f.exists()) {
            f.createNewFile();
        }
        return str;
    }

    private static StringBuilder createBodyPut(String pn, String tableComment) throws Exception {
        StringBuilder str = new StringBuilder("package com.tencent.wxcloudrun.dto." + pn + ";\n\n");
        str.append("import com.tencent.wxcloudrun.dto.BaseBody;\n");
        str.append("import com.tencent.wxcloudrun.model." + pn + "." + className + ";\n");
        str.append("import io.swagger.annotations.ApiModel;\n");
        str.append("import lombok.Data;\n" +
                "import lombok.EqualsAndHashCode;\n\n");
        str.append("/**\n * @author : chenzg\n" +
                " * @date : " + DateUtil.dateString1(new Date()) + "\n */\n");
        str.append("@Data\n" +
                "@EqualsAndHashCode(callSuper = true)\n" +
                "@ApiModel(description = \"" + tableComment + "\", value = \"" + className + "Put\")\n");
        /*String strId = lowerUnderscore(className);
        strId = strId.substring(strId.lastIndexOf("_") + 1);
        strId = toClassName(strId) + "Id";*/
        str.append("public class " + className + "Put extends " + className + " {\n" +
                "}");
        String dir = pathSTr + "\\request\\" + pn;
        new File(dir).mkdirs();
        File f = new File(dir + "/" + className + "Put.java");
        if (!f.exists()) {
            f.createNewFile();
        }
        return str;
    }

    private static StringBuilder createBodyGet(String pn, String tableComment) throws Exception {
        StringBuilder str = new StringBuilder("package com.tencent.wxcloudrun.dto." + pn + ";\n\n");
        str.append("import com.tencent.wxcloudrun.dto.BaseBodyGet;\n");
        str.append("import io.swagger.annotations.ApiModel;\n");
        str.append("import lombok.Data;\n" +
                "import lombok.EqualsAndHashCode;\n\n");
        str.append("/**\n * @author : chenzg\n" +
                " * @date : " + DateUtil.dateString1(new Date()) + "\n */\n");
        str.append("@Data\n" +
                "@EqualsAndHashCode(callSuper = true)\n" +
                "@ApiModel(description = \"" + tableComment + "\", value = \"" + className + "Get\")\n");
        str.append("public class " + className + "Get extends BaseBodyGet {\n" +
                "    public " + className + "Get() {\n" +
                "        this.setOrderBy(\"order by t1.create_time desc\");\n" +
                "    }\n" +
                "}");
        String dir = pathSTr + "\\request\\" + pn;
        new File(dir).mkdirs();
        File f = new File(dir + "/" + className + "Get.java");
        if (!f.exists()) {
            f.createNewFile();
        }
        return str;
    }

    private static StringBuilder createRepository(String pn) throws Exception {
        StringBuilder str = new StringBuilder("package com.tencent.wxcloudrun.dao." + pn + ";\n\n");
        str.append("import com.tencent.wxcloudrun.model." + pn + "." + className + ";\n");
        str.append("import org.springframework.data.jpa.repository.JpaRepository;\n" +
                "import org.springframework.data.jpa.repository.Modifying;\n" +
                "import org.springframework.data.jpa.repository.Query;\n" +
                "import org.springframework.data.repository.query.Param;\n" +
                "\n" +
                "import java.util.Collection;\n" +
                "import java.util.List;\n\n");
        str.append("/**\n * @author : chenzg\n" +
                " * @date : " + DateUtil.dateString1(new Date()) + "\n */\n");
        str.append("public interface " + className + "Repository extends JpaRepository<" + className + ", String> {\n}");
        String dir = pathSTr + "\\dao\\" + pn;
        new File(dir).mkdirs();
        File f = new File(dir + "/" + className + "Repository.java");
        if (!f.exists()) {
            f.createNewFile();
        }
        return str;
    }

    private static void createEntity(String driverClassName, String url, String username, String password) throws Exception {
        String cn = tableName.substring(tableName.indexOf("_") + 1);
        String pn = cn.split("_")[0];
        if (!tableName.startsWith("plugin_")) {
            cn = tableName;
            pn = "common";
        }
        className = toClassName(cn);
        pkName = cn.substring(cn.lastIndexOf("_"));
        pkName = pkName + "_id";
        String dir = pathSTr + "\\entity\\" + pn;
        StringBuilder post = createBodyPost(pn, tableComment);
        StringBuilder put = createBodyPut(pn, tableComment);
        StringBuilder get = createBodyGet(pn, tableComment);
        StringBuilder service = createService(pn, tableComment);
        StringBuilder controller = createController(pn, tableComment);
        StringBuilder jpaRepository = createRepository(pn);
        StringBuilder stringBuilder = new StringBuilder("package com.tencent.wxcloudrun.model." + pn + ";\n");
        StringBuilder queryField = new StringBuilder();
        if (tableName.length() > 0) {
            stringBuilder.append("\n" +
                    "import com.tencent.wxcloudrun.dto.IBody;\n" +
                    "import com.tencent.wxcloudrun.exception.EnumValid;\n" +
                    "import com.tencent.wxcloudrun.utils.StrUtil;\n" +
                    "import io.swagger.annotations.ApiModel;\n" +
                    "import io.swagger.annotations.ApiModelProperty;\n" +
                    "import lombok.*;\n" +
                    "import org.hibernate.Hibernate;\n" +
                    "import org.hibernate.annotations.DynamicInsert;\n" +
                    "import org.hibernate.annotations.DynamicUpdate;\n" +
                    "\n" +
                    "import javax.persistence.Column;\n" +
                    "import javax.persistence.Entity;\n" +
                    "import javax.persistence.Id;\n" +
                    "import javax.persistence.Table;\n" +
                    "\n\n/**\n" +
                    " * @author : chenzg\n" +
                    " * @date : " + DateUtil.dateString1(new Date()) + "\n" +
                    " */\n" +
                    "@Getter\n" +
                    "@Setter\n" +
                    "@ToString\n" +
                    "@RequiredArgsConstructor\n" +
                    "@Builder\n" +
                    "@AllArgsConstructor\n" +
                    "@ApiModel\n" +
                    "@DynamicInsert\n" +
                    "@DynamicUpdate\n" +
                    "@Entity\n");
            stringBuilder.append("@Table(name = \"" + tableName + "\")\n");
            stringBuilder.append("public class " + className + " implements IBody {");
            stringBuilder.append("\n");
            stringBuilder.append("\t@Id\n");
//            stringBuilder.append("\t@GeneratedValue\n");

            Class.forName(driverClassName);
            Connection conn = DriverManager.getConnection(url, username, password);
            List<String> pros = new ArrayList<>();
            try {
                Statement stat = conn.createStatement();
                try {
                    String sql = "select  column_name,column_key,data_type,column_comment,is_nullable " +

                            "from information_schema.columns where  table_name='" + tableName +

                            "'  and table_schema='" + tableSchema + "'  order by ordinal_position ";
                    ResultSet rs = stat.executeQuery(sql);
                    StringBuilder sbVar = new StringBuilder();
                    int count = 1;
                    boolean bool = false;
                    while (rs.next()) {
                        String fType;
                        String columnName = rs.getString(1);
                        String columnKey = rs.getString(2);
                        String columnType = rs.getString(3);
                        String columnComment = rs.getString(4);
                        String isNullAble = rs.getString(5);
                        if (StrUtil.isNull(columnComment)) {
                            columnComment = columnName;
                        }
                        boolean b = !columnName.startsWith("update_time") && !columnName.startsWith("update_user_id");
                        if (StrUtil.equals(columnName, "create_time")) {
                            bool = true;
                        } else if (b) {
                            String tmp = "";
                            if (columnName.contains("_")) {
                                tmp = " " + toParamName(columnName);
                            }
                            queryField.append("t1.").append(columnName).append(tmp).append(",");
                            if (count % 6 == 0) {
                                queryField.append("\" +\n\n                \"");
                            }
                            count++;
                        }
                        pros.add(columnName);
                        String defaultValue = columnComment;
                        switch (columnType) {
                            case "char":
                            case "varchar":
                            case "text":
                            case "nvarchar":
                            case "json":
                                fType = "String";
                                defaultValue = StrUtil.uuid();
                                break;
                            case "date":
                            case "datetime":
                            case "time":
                            case "timestamp":
                                fType = "Date";
                                defaultValue = DateUtil.dateString(new Date());
                                break;
                            case "real":
                                fType = "real";
                                break;
                            case "double":
                            case "decimal":
                            case "float":
                                fType = "Double";
                                defaultValue = "1";
                                break;
                            case "SMALLINT":
                                fType = "Short";
                                defaultValue = "1";
                                break;
                            case "int":
                            case "integer":
                                fType = "Integer";
                                defaultValue = "1";
                                break;
                            case "numeric":
                            case "bigint":
                                fType = "Long";
                                defaultValue = "1";
                                break;
                            case "boolean":
                            case "bit":
                                fType = "Boolean";
                                defaultValue = "true";
                                break;
                            case "blob":
                            case "clob":
                            case "nclob":
                            case "mediumblob":
                                fType = "byte[]";
                                break;
                            default:
                                fType = "type" + columnType + "?";
                        }

                        String _text = "\"";
                        if ("json".equals(columnType)) {
                            _text = "\", columnDefinition = \"json\"";
                        } else if ("text".equals(columnType)) {
                            _text = "\", columnDefinition = \"text\"";
                        } else if ("mediumblob".equals(columnType)) {
                            _text = "\", columnDefinition = \"mediumblob\"";
                        }
                        sbVar.append("\t@Column(name = \"`" + columnName + "`").append(_text).append(")\r\n");
                        String fn = "\t@ApiModelProperty(notes = \"" + columnComment + "\", example = \"" + defaultValue + "\")\r\n";
                        String paramName = toParamName(columnName);
                        if ("Integer".equals(fType)) {
                            if (StrUtil.equals("status", columnName) && columnComment.contains("启用")) {
                                fn += "\t@EnumValid\n";
                            } else if (StrUtil.equals("delete_status", columnName)) {
                                fn += "\t@EnumValid(prefix = \"DELETE_STATUS_\")\n";
                            } else {
                                String en = lowerUnderscore(className) + "_" + columnName;
                                en = en.toUpperCase();
                                fn += "\t@EnumValid(prefix = \"" + en + "_\")\n";
                            }
                        }
                        fn += "\tprivate " + fType + " " + paramName;
                        sbVar.append(fn);
                        boolean hide = false;
                        if ("Date".equals(fType)) {
                            String str = "import java.util.Date;";
                            if (stringBuilder.indexOf(str) < 0) {
                                stringBuilder.insert(stringBuilder.indexOf("\n\n/**\n"), str);
                            }
                            sbVar.insert(sbVar.lastIndexOf("\tprivate "), "\t@Builder.Default\n");
                            sbVar.insert(sbVar.lastIndexOf(", example = "), ", hidden = true");
                            sbVar.append(" = new Date()");
                            hide = true;
                        }
                        if ("deleteStatus".equals(paramName)) {
                            sbVar.insert(sbVar.lastIndexOf("\tprivate "), "\t@Builder.Default\n");
                            sbVar.insert(sbVar.lastIndexOf(", example = "), ", hidden = true");
                            sbVar.append(" = 1");
                            hide = true;
                        }
                        if (columnName.endsWith("_user_id")) {
                            sbVar.insert(sbVar.lastIndexOf(", example = "), ", hidden = true");
                            hide = true;
                        }
                        if ("PRI".equals(columnKey) && "varchar".equals(columnType)) {
                            sbVar.insert(sbVar.lastIndexOf("\tprivate "), "\t@Builder.Default\n");
                            sbVar.insert(sbVar.lastIndexOf(", example = "), ", hidden = true");
                            sbVar.append(" = StrUtil.uuid()");
                            hide = true;
                        }
                        if (!hide && StrUtil.equalsIgnoreCase("no", isNullAble)) {
                            sbVar.insert(sbVar.lastIndexOf(", example = "), ", required = true");
                        }
                        sbVar.append(";\r\n");
                    }
                    if (bool) {
                        queryField.append("t1.create_time").append(" createTime");
                    }
                    boolean isDel = pros.contains("delete_status");
                    if (!isDel) {
                        service.insert(service.indexOf("        return sqlUtil.list("), "        body.setDeleteStatus(sqlUtil.FLAG_NO_FIELD);\n");
                        service.insert(service.indexOf("        return sqlUtil.detail("), "        body.setDeleteStatus(sqlUtil.FLAG_NO_FIELD);\n");
                    }
                    for (String p : pros) {
                        String fn = toParamName(p);
                        if (!fn.endsWith("UserId") && fn.endsWith("Id")) {
                            String ob = pros.contains("sorting") ? " order by sorting " : pros.contains("create_time") ? " order by create_time desc " : " ";
                            String ds = isDel ? " and deleteStatus=:deleteStatus " : "";
                            String tmp = "\t@Query(value = \"from " + className + " where " + fn + " in (:" + fn + ")" + ob + "\")\n" +
                                    "\tList<" + className + "> " + toParamName("find_by_" + p) + "(@Param(\"" + fn + "\") Collection<String> " + fn + ");\n\n";
                            jpaRepository.insert(jpaRepository.lastIndexOf("}"), tmp);
                            if (ds.length() > 0) {
                                tmp = "\t@Query(value = \"from " + className + " where " + fn + " in (:" + fn + ")" + ds + ob + "\")\n" +
                                        "\tList<" + className + "> " + toParamName("find_by_" + p) + "(@Param(\"deleteStatus\") Integer deleteStatus, @Param(\"" + fn + "\") Collection<String> " + fn + ");\n\n";
                                jpaRepository.insert(jpaRepository.lastIndexOf("}"), tmp);
                            }
                            tmp = "\t@Modifying\n" +
                                    "\t@Query(value = \"delete from " + className + " where " + fn + " in (:" + fn + ")\")\n" +
                                    "\tint " + toParamName("delete_by_" + p) + "(@Param(\"" + fn + "\") Collection<String> " + fn + ");\n\n";
                            jpaRepository.insert(jpaRepository.lastIndexOf("}"), tmp);
                            String up = "";
                            String uu = "";
                            String ut = "";
                            if (pros.contains("update_user_id")) {
                                up = "@Param(\"updateUserId\") String updateUserId, ";
                                uu = ",update_user_id=:updateUserId";
                            }
                            if (pros.contains("update_time")) {
                                ut = ",update_time=CURRENT_TIMESTAMP()";
                            }
                            String uut = uu + ut;
                            if (pros.contains("status")) {
                                tmp = "\t@Modifying\n" +
                                        "\t@Query(value = \"update " + className + " set status=:status " + uut + " where " + fn + " in (:" + fn + ")\")\n" +
                                        "\tint " + toParamName("update_status_by_" + p) + "(" + up + "@Param(\"status\") Integer status, @Param(\"" + fn + "\") Collection<String> " + fn + ");\n\n";
                                jpaRepository.insert(jpaRepository.lastIndexOf("}"), tmp);
                            }
                            if (isDel) {
                                tmp = "\t@Modifying\n" +
                                        "\t@Query(value = \"update " + className + " set deleteStatus=:deleteStatus " + uut + " where " + fn + " in (:" + fn + ")\")\n" +
                                        "\tint " + toParamName("update_delete_status_by_" + p) + "(" + up + "@Param(\"deleteStatus\") Integer deleteStatus, @Param(\"" + fn + "\") Collection<String> " + fn + ");\n\n";
                                jpaRepository.insert(jpaRepository.lastIndexOf("}"), tmp);
                            }
                            if (pros.contains("sorting")) {
                                tmp = "\t@Modifying\n" +
                                        "\t@Query(value = \"update " + className + " set sorting=:sorting " + uut + " where " + fn + " in (:" + fn + ")\")\n" +
                                        "\tint " + toParamName("update_sorting_by_" + p) + "(" + up + "@Param(\"sorting\") Integer sorting, @Param(\"" + fn + "\") Collection<String> " + fn + ");\n\n";
                                jpaRepository.insert(jpaRepository.lastIndexOf("}"), tmp);
                                //add and subtract
                                tmp = "\t@Modifying\n" +
                                        "\t@Query(value = \"update " + className + " set sorting=sorting+1 " + uut + " where " + fn + " in (:" + fn + ")\")\n" +
                                        "\tint " + toParamName("update_sorting_add_by_" + p) + "(" + up + "@Param(\"" + fn + "\") Collection<String> " + fn + ");\n\n";
                                jpaRepository.insert(jpaRepository.lastIndexOf("}"), tmp);
                                tmp = "\t@Modifying\n" +
                                        "\t@Query(value = \"update " + className + " set sorting=sorting-1 " + uut + " where " + fn + " in (:" + fn + ")\")\n" +
                                        "\tint " + toParamName("update_sorting_subtract_by_" + p) + "(" + up + "@Param(\"" + fn + "\") Collection<String> " + fn + ");\n\n";
                                jpaRepository.insert(jpaRepository.lastIndexOf("}"), tmp);
                            }
                        }
                    }
                    stringBuilder.append(sbVar);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn.close();
            }
            if (!stringBuilder.toString().contains(" createUserId;")) {
                stringBuilder.append("\t@Override\n\tpublic void setCreateUserId(String createUserId) {\n");
                stringBuilder.append("\t}\n");
            }
            /*stringBuilder.append("\t@Override\n\tpublic void setCreateUserId(String userId) {\n");
            if (stringBuilder.toString().contains(" createUserId;")) {
                stringBuilder.append("\t\tthis.createUserId = userId;\n");
            }
            stringBuilder.append("\t}\n\n\t@Override\n\tpublic void setEnterpriseId(String enterpriseId) {\n");
            if (stringBuilder.toString().contains(" enterpriseId;")) {
                stringBuilder.append("\t\tthis.enterpriseId = enterpriseId;\n");
            }
            stringBuilder.append("\t}");*/
            stringBuilder.append("\n" +
                    "\t@Override\n" +
                    "\tpublic boolean equals(Object o) {\n" +
                    "\t\tif (this == o) {\n" +
                    "\t\t\treturn true;\n" +
                    "\t\t}\n" +
                    "\t\tif (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {\n" +
                    "\t\t\treturn false;\n" +
                    "\t\t}\n" +
                    "\t\t" + className + " that = (" + className + ") o;\n" +
                    "\t\treturn " + toParamName(pros.get(0)) + " != null && java.util.Objects.equals(" + toParamName(pros.get(0)) + ", that." + toParamName(pros.get(0)) + ");\n" +
                    "\t}\n" +
                    "\n" +
                    "\t@Override\n" +
                    "\tpublic int hashCode() {\n" +
                    "\t\treturn getClass().hashCode();\n" +
                    "\t}");
            stringBuilder.append("}");
            new File(dir).mkdirs();
            File f = new File(dir + "/" + className + ".java");
            if (!f.exists()) {
                f.createNewFile();
            }
            FileUtil.writeFile(stringBuilder.toString().getBytes(StandardCharsets.UTF_8), f.getAbsolutePath());
            //Repository
            String s1 = jpaRepository.toString();
            if (!s1.contains("List<")) {
                s1 = s1.replace("import java.util.List;\n\n", "");
            }
            File f1 = new File(pathSTr + "\\dao\\" + pn + "/" + className + "Repository.java");
            FileUtil.writeFile(s1.getBytes(StandardCharsets.UTF_8), f1.getAbsolutePath());

            File f2 = new File(pathSTr + "\\request\\" + pn + "/" + className + "Post.java");
            FileUtil.writeFile(post.toString().getBytes(StandardCharsets.UTF_8), f2.getAbsolutePath());

            File f3 = new File(pathSTr + "\\request\\" + pn + "/" + className + "Get.java");
            FileUtil.writeFile(get.toString().getBytes(StandardCharsets.UTF_8), f3.getAbsolutePath());

            File f4 = new File(pathSTr + "\\request\\" + pn + "/" + className + "Put.java");
            FileUtil.writeFile(put.toString().getBytes(StandardCharsets.UTF_8), f4.getAbsolutePath());

            String serviceStr = service.toString();
            String tmp = queryField.toString();
            if (tmp.endsWith(",")) {
                tmp = tmp.substring(0, tmp.length() - 1);
            }
            serviceStr = serviceStr.replace("queryField", tmp);
            File f5 = new File(serviceSTr + "\\" + pn + "/service/" + className + "Service.java");
            FileUtil.writeFile(serviceStr.getBytes(StandardCharsets.UTF_8), f5.getAbsolutePath());

            File f6 = new File(controllerSTr + "\\" + pn + "/web/" + className + "Controller.java");
            FileUtil.writeFile(controller.toString().getBytes(StandardCharsets.UTF_8), f6.getAbsolutePath());

        }
    }

    private static String toParamName(String field) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, field);
    }

    private static String toClassName(String field) {
        String str = toParamName(field);
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private static String lowerUnderscore(String str) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, str);
    }

}
