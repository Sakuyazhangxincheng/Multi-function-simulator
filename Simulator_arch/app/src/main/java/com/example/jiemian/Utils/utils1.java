package com.example.jiemian.Utils;

import com.example.jiemian.bean.Kuaijie;

import java.util.ArrayList;
import java.util.List;

public class utils1  {
    public static int ID=0;
public static String passname="";
public static String passpwd="";
public static List<Integer> list=new ArrayList<>();
    public static List<Kuaijie> goodStbList(){//商品信息，可以自己添加
        List<Kuaijie> list = new ArrayList<>();
        list.add(new Kuaijie("原神 横屏","102","520114514","五列横过来元神"));
        list.add(new Kuaijie("街霸五","12","zbq12345","街霸"));
        list.add(new Kuaijie("哔哩哔哩快捷键","99","15797953568","哔哩哔哩快捷键"));
        list.add(new Kuaijie("小米电视机遥控器","18","1579792323","小米电视机遥控器"));
        list.add(new Kuaijie("九宫格数字","102","520114514","九宫格数字"));
        return list;
    }
}
