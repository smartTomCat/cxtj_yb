import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaohs on 2020/3/18.
 */
public class Demo {

    public static void main(String[] args) {
        Map m1 = new HashMap<>();
        m1.put("s1","111");
        m1.put("s2","222");
        m1.put("s3","333");
        Map m2 = new HashMap<>();
        m2.put("z1","111");
        m2.put("z2","222");
        m2.put("z3","333");
        List<Map> list = new ArrayList<>();
        list.add(m1);
        list.add(m2);
        int index = 0;
        for (Map map : list) {
            String s;
            if(index == 0){
                s = "0";
            }else {
                s = "1";
            }
            map.put("k",s);
            index++;
        }
    }
}
