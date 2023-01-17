
package fastfood.util;

import java.util.HashMap;
import java.util.Map;

public class ListLikeComment {
    
    public  static Map<Integer,Boolean[]> put(){
        Map<Integer,Boolean[]> listLike = new HashMap<>();
        listLike.put(0, new Boolean[]{false,false});
        listLike.put(1, new Boolean[]{true,false});
        listLike.put(2, new Boolean[]{false,true});
        return listLike;
    }
}
