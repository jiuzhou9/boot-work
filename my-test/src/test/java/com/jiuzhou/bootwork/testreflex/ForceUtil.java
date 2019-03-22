package com.jiuzhou.bootwork.testreflex;

import com.jiuzhou.bootwork.beans.Person;
import org.springframework.util.StringUtils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2019/03/19
 */
public class ForceUtil {

    /**
     * 暴力获取字段
     * 获取model对象中字段为val的字段名
     *
     * @param val 指定的值
     * @param model 被检测的对象
     * @param fields 定义好的字段顺序
     * @return
     */
    public static String getField(Object val, Object model, List<String> fields){
        try {
            for (String field : fields) {
                Class<?> objClass = model.getClass();
                Field declaredField = objClass.getDeclaredField(field);
                declaredField.setAccessible(true);
                Object o = declaredField.get(model);
                if (val.equals(o)){
                    return field;
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取指定model中指定范围field对应的字段值
     * @param model
     * @param fields
     * @return
     */
    public static Map<String, Integer> getFieldValues(Object model, List<String> fields){
        Map<String, Integer> map = new LinkedHashMap<>();
        try {
            for (String field : fields) {
                Class<?> objClass = model.getClass();
                Field declaredField = objClass.getDeclaredField(field);
                declaredField.setAccessible(true);
                Integer o = Integer.valueOf((String) declaredField.get(model));
                if (o>0) {
                    map.put(field, o);
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return map;
    }


    /**
     * 获取指定model中指定范围field对应的字段值
     * @param model
     * @param fields
     * @return
     */
    public static Map<String, Object> getFieldObjValues(Object model, List<String> fields){
        Map<String, Object> map = new LinkedHashMap<>();
        try {
            for (String field : fields) {
                Class<?> objClass = model.getClass();
                Field declaredField = objClass.getDeclaredField(field);
                declaredField.setAccessible(true);
                Object o = declaredField.get(model);
                map.put(field, o);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 暴力获取一个对象中的所有属性
     * @param model
     * @return
     */
    public static List<String> getFieldNames(Object model, String start){
        List<String> list = new LinkedList();
        Class<?> objClass = model.getClass();
        Field[] fields = objClass.getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            if (!StringUtils.isEmpty(start)){
                if (name.startsWith(start)) {
                    list.add(name);
                }
            }else {
                list.add(name);
            }
        }
        return list;
    }

    /**
     * 给对象指定的字段设置指定的值
     * @param t
     * @param val
     * @param fieldName
     * @param <T>
     */
    public static <T> void setField(T t, Object val, String fieldName){
        try {
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, t.getClass());
            Method writeMethod = propertyDescriptor.getWriteMethod();
            writeMethod.invoke(t, val);
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        Person person = new Person();
        person.setId(12);
        person.setName("12");

        List<String> fieldNames = getFieldNames(new Person(), null);
        System.out.print("{");
        fieldNames.forEach(name->{
            System.out.print("\"" + name + "\",");
        });
        System.out.print("}");
    }
}
