/**
 * Created by zhaohs on 2020/3/18.
 */
public class Demo {

    public static void main(String[] args) {
        String sql = "dadasdasd;das'da'sd;werwerewr;rwer'ew'erwer;";
        sql = sql.replaceAll("'","\"");
        System.out.println(sql);
    }
}
