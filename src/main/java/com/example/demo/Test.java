package com.example.demo;

import com.alibaba.fastjson.JSON;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;

public class Test {

    public static void main(String[] args) {
        HashMap hashMap = new HashMap();
        hashMap.put("x","xx");
        ArrayList arrayList = new ArrayList();
        arrayList.add("asd");
        hashMap.put("asd",arrayList);

        HashMap<String,String> hashMap1 = JSON.parseObject(JSON.toJSONString(hashMap), HashMap.class);

        System.out.println(((ParameterizedType)hashMap1.getClass().getGenericSuperclass()).getActualTypeArguments()[1]);

        HashMap<String,String> hashMap2 = new HashMap<>();
    }

}
