package com.newth.scorehelper.util;


import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

public class SpinerListUtil {
    public static List<String> weekList=new ArrayList<>(Arrays.asList("第1周","第2周","第3周","第4周","第5周","第6周","第7周","第8周",
            "第9周","第10周","第11周","第12周","第13周","第14周","第15周","第16周","第17周"));
    public static List<String> scoreList=new ArrayList<>(Arrays.asList("10","20","30","40","50","60","70","80","90","100"));
    public SpinerListUtil(){

    }

    public static List<String>  getWeekList(){
        return weekList;
    }
    public static List<String> getScoreList(){
        return scoreList;
    }
}
