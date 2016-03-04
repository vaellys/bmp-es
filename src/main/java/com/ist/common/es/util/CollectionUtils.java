package com.ist.common.es.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ist.dto.bmp.ESDto;

public class CollectionUtils {
    private CollectionUtils() {
    }

    /**
     * 求两个集合中的交集
     * 
     * @param sourceSet
     *            源集合
     * @param targetSet
     *            目标集合
     * @return <T> Set<T> 返回新的交集集合
     */
    public static <T> Set<T> intersect(Set<T> sourceSet, Set<T> targetSet) {
        Set<T> set = new HashSet<T>();
        int sourceSetSize = sourceSet.size();
        int targetSetSize = targetSet.size();
        Set<T> minSet = null;
        Set<T> maxSet = null;
        if (sourceSetSize <= targetSetSize) {
            minSet = sourceSet;
            maxSet = targetSet;
        } else {
            minSet = targetSet;
            maxSet = sourceSet;

        }

        for (T t : minSet) {
            if (maxSet.contains(t)) {
                set.add(t);
            }
        }
        return set;
    }

    /**
     * 按key所对应的值进行分组 并将其值作为新map的key key2所对应的值作为list中的value
     * 
     * @param result
     * @param key
     * @param key2
     * @return Map<Object, List<String>>
     */
    public static Map<String, List<String>> classify(List<Map<String, Object>> result, String key, String key2) {
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        List<String> values = null;
        for (Map<String, Object> record : result) {
            String mapKey = String.valueOf(record.get(key));
            String mapkey2 = String.valueOf(record.get(key2));
            if (!map.containsKey(mapKey)) {
                values = new LinkedList<String>();
                values.add(mapkey2);
            } else {
                map.get(mapKey).add(mapkey2);
            }
            map.put(mapKey, values);
        }
        return map;
    }

    /**
     * 带顺序去除重复元素
     * 
     * @param list
     * @return
     */
    public static <T> List<T> removeDuplicateWithOrder(List<T> list) {
        Set<T> hashSet = new HashSet<T>();
        List<T> newlist = new ArrayList<T>();
        for (Iterator<T> iterator = list.iterator(); iterator.hasNext();) {
            T element = iterator.next();
            if (hashSet.add(element)) {
                newlist.add(element);
            }
        }
        list.clear();
        list.addAll(newlist);
        return list;
    }
    
    /** 
     * 将一个 Map 对象转化为一个 JavaBean 
     * @param type 要转化的类型 
     * @param map 包含属性值的 map 
     * @return 转化出来的 JavaBean 对象 
     * @throws IntrospectionException 如果分析类属性失败 
     * @throws IllegalAccessException 如果实例化 JavaBean 失败 
     * @throws InstantiationException 如果实例化 JavaBean 失败 
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败 
     */  
    @SuppressWarnings("rawtypes")  
    public static Object convertMap(Class type, Map map) {  
        Object obj = null;
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性  
            obj = type.newInstance(); // 创建 JavaBean 对象  
  
            // 给 JavaBean 对象的属性赋值  
            PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();  
            for (int i = 0; i< propertyDescriptors.length; i++) {  
                PropertyDescriptor descriptor = propertyDescriptors[i];  
                String propertyName = descriptor.getName();  
  
                if (map.containsKey(propertyName)) {  
                    // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。  
                    Object value = map.get(propertyName);  
  
                    Object[] args = new Object[1];  
                    args[0] = value;  
  
                    descriptor.getWriteMethod().invoke(obj, args);  
                }  
            }  
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }  
        return obj;
    }  
    
    public static void main(String[] args) {
        Map<String, String> m = new HashMap<String, String>();
        m.put("id", "1111");
        m.put("title", "您好");
        m.put("content", "sb");
        ESDto convertMap = (ESDto) convertMap(ESDto.class, m);
        System.out.println(convertMap);
        
    }
}
