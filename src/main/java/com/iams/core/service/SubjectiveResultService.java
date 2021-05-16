package com.iams.core.service;

import com.iams.common.util.PythonUtil;
import com.iams.common.util.ResultContentUtil;
import com.iams.common.util.Utils;

/**
 * @author Wei yz
 * @ClassName: SubjectiveResultService
 * @Description:  主观题答案业务类
 * @date 2021/5/4 14:45
 */
public class SubjectiveResultService {

    /**
     * 返回系统判断过后的评估分
     * @param studentResult 学生答案
     * @param time 作答时间
     * @param score 题目分值
     * @return
     */
    public static Float getsScore(String studentResult,String teacherResult,Long time,Float score){
        Float[] scoreProportion = {0.1f,0.4f,0.3f,0.2f}; //分值比例：图片10%、字数40%、时间30%、段落20%
        Integer[] limitValue = {1,200,60,3};//限定值：图片1张、字数200、时间1h、段落3
        // 1.计算总字数
        String studentText = ResultContentUtil.getText(studentResult);//得到学生纯文本
        String teacherText = ResultContentUtil.getText(teacherResult);//得到教师纯文本
        Integer textLength=limitValue[1];
        if(Utils.isEmpty(teacherText)){//如果教师的答案纯文本不为空则作为字数限定
            textLength=teacherText.length();
        }
        Float textScore=calculateText(studentText,scoreProportion[1],textLength,score);//计算得到答案字数预估分
        // 2.计算图片数
        int images = ResultContentUtil.getImages(studentResult);//得到图片数
        Float imagesScore=calculateImages(images,scoreProportion[0],limitValue[0],score);//计算得到图片数预估分
        // 3.计算段落数
        int paragraphs = ResultContentUtil.getParagraphs(studentResult);//得到段落数
        Float paragraphsScore=calculateImages(paragraphs,scoreProportion[3],limitValue[3],score);//计算得到段落数预估分
        // 4.答题时间
        Float timeScore=calculateTime(time,scoreProportion[2],limitValue[2],score);//计算得到作答时间预估分
        return (textScore+imagesScore+paragraphsScore+timeScore);
    }

    /**
     * 计算文本总数并按照限定值标准得出成绩：比例*分值
     * @param text 文本
     * @param proportion 分值比例
     * @param limitValue 限定值
     * @param score 成绩
     * @return
     */
    private static Float calculateText(String text,Float proportion,Integer limitValue,Float score){
        if(text.length()>=limitValue && text.length()<=(limitValue*1.2)){//如：>=200 且 <=240
            return (float)Math.rint(score*proportion);//满分
        }
        if(text.length()<=limitValue && text.length()>=(limitValue*0.7)){//如：<=200 且 >=140
            return (float)Math.rint(score*0.7*proportion);//分数的70%，后四舍五入
        }
        return 0.0f;
    }

    /**
     * 计算图片数并按照限定值标准得出成绩：比例*分值
     * @param images 图片数
     * @param proportion 分值比例
     * @param limitValue 限定值
     * @param score 成绩
     * @return
     */
    private static Float calculateImages(Integer images,Float proportion,Integer limitValue,Float score){
        if(images==limitValue && images==(limitValue+1)){//如：==1 且 ==2
            return (float)Math.rint(score*proportion);//满分
        }
        return 0.0f;
    }

    /**
     * 计算段落数并按照限定值标准得出成绩：比例*分值
     * @param paragraphs 段落数
     * @param proportion 分值比例
     * @param limitValue 限定值
     * @param score 成绩
     * @return
     */
    private static Float calculateParagraphs(Integer paragraphs,Float proportion,Integer limitValue,Float score){
        if(paragraphs>=limitValue && paragraphs<=(limitValue*2)){//如：>=3 且 <=6
            return (float)Math.rint(score*proportion);//满分
        }
        if(paragraphs<limitValue && paragraphs>=1){//如：<3 且 >=1
            return (float)Math.rint(score*proportion*0.7);//70%
        }
        return 0.0f;
    }


    /**
     * 计算作答时间并按照限定值标准得出成绩：比例*分值
     * @param time 作答时间
     * @param proportion 分值比例
     * @param limitValue 限定值
     * @param score 成绩
     * @return
     */
    private static Float calculateTime(Long time,Float proportion,Integer limitValue,Float score){
        Long limit = limitValue*60l;//60分钟*60秒
        if(time<=limit && time>=(limit*0.1)){//如：<=3600秒,1h 且 >=360秒,6分钟
            return (float)Math.rint(score*proportion);//满分
        }
        return 0.0f;
    }

    /**
     * 计算文本相似度
     * @param studentResult 学生答案
     * @param teacherResult 教师答案
     * @param score 成绩
     * @return
     */
    public static Float sim(String studentResult,String teacherResult,Float score){
        String studentText = ResultContentUtil.getText(studentResult);//得到学生纯文本
        String teacherText = ResultContentUtil.getText(teacherResult);//得到教师纯文本
        String similarity = PythonUtil.sim(studentText,teacherText);//得到相似度
        double sim = Double.parseDouble(similarity);//转型
        if(sim>=0.3){
            return (float)Math.rint(score*sim);
        }
        return 0.0f;
    }

}
