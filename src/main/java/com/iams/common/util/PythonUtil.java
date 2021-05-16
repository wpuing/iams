package com.iams.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author Wei yz
 * @ClassName: PythonUtil
 * @Description:  调用Python工具类
 * @date 2021/5/4 21:39
 */
public class PythonUtil {

    public static String exeCmd(String commandStr) {
        BufferedReader br = null;
        String str = null;
        try {
            Process p = Runtime.getRuntime().exec(commandStr);
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            str = sb.toString();
            System.out.println("参数："+str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            if (br != null)
            {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return str;
        }

    }

    /**
     * 文本余弦相似度对比
     * @param studentResult 学生答案
     * @param teacherResult 教师答案
     * @return 余弦相似度，浮点类型字符串
     */
    public static String sim(String studentResult,String teacherResult){
        String url = "python  D:\\CodeSpace\\Java\\IDEA\\demo\\iams-dev\\src\\main\\resources\\文本余弦相似度.py ";
        String commandStr = url+studentResult+" "+teacherResult;
        return exeCmd(commandStr);
    }

}
