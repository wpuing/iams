package com.iams.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Wei yz
 * @ClassName: ResultContentUtil
 * @Description: 富文本工具
 * @date 2021/5/4 14:56
 */
public class ResultContentUtil {

    /**
     * 返回去除HTML的标签等其他的样式之后的纯文本
     * @param result 富文本内容
     * @return
     */
    public static String  getText(String  result){
        return htmlRemoveTag(result);
    }

    /**
     * 返回文本的段落数，根据<p></p>标签计算
     * @param result 富文本
     * @return
     */
    public static int getParagraphs(String result){
        String regex = "<p.*?>(.*?)</p>";//正则表达式
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(result);
        int i = 0;
        while (m.find()) {
            i++;
        }
        return i;
    }

    /**
     * 返回文本中的图片数
     * @param result 文本
     * @return
     */
    public static int getImages(String result){
        List<String> imageSrcList = new ArrayList<String>();
        Pattern p = Pattern.compile(
                "<img\\b[^>]*\\bsrc\\b\\s*=\\s*('|\")?([^'\"\n\r\f>]+(\\.jpg|\\.bmp|\\.eps|\\.gif|\\.mif|\\.miff|\\.png|\\.tif|\\.tiff|\\.svg|\\.wmf|\\.jpe|\\.jpeg|\\.dib|\\.ico|\\.tga|\\.cut|\\.pic)\\b)[^>]*>",
                Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(result);
        String quote = null;
        String src = null;
        while (m.find()) {
            quote = m.group(1);
            src = (quote == null || quote.trim().length() == 0) ? m.group(2).split("\\s+")[0] : m.group(2);
            imageSrcList.add(src);
        }
        return imageSrcList.size();
    }



    private static boolean check_notEmpty_pcs(Object obj) {
        if(obj instanceof List){
            List list = (List)obj;
            return list != null && !list.isEmpty();
        }else if(obj instanceof Set){
            Set set = (Set)obj;
            return set != null && !set.isEmpty();
        }else if(obj instanceof Map){
            Map map = (Map)obj;
            return map != null && !map.isEmpty();
        }else if(obj instanceof String){
            return obj!=null&&!"".equals(obj)&&!"null".equals(obj)&&!"undefined".equals(obj);
        }else if(obj instanceof Integer){
            return obj!=null&&!"".equals(obj)&&!"null".equals(obj)&&!"undefined".equals(obj);
        }else{
            return false;
        }
    }

    private static String htmlRemoveTag(String inputString) {
        if (!check_notEmpty_pcs(inputString)) {
            return null;
        }
        String htmlStr = cleanXSS(inputString); // 含html标签的字符串
        String textStr = "";
        Pattern p_script;
        java.util.regex.Matcher m_script;
        Pattern p_style;
        java.util.regex.Matcher m_style;
        Pattern p_html;
        java.util.regex.Matcher m_html;
        try {
            // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
            // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签
            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签
            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签
            textStr = htmlStr;
            textStr = textStr.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "");
            textStr = textStr.replaceAll("[(/>)<]", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        textStr = cleanXSS(textStr);
        return textStr;// 返回文本字符串
    }

    private static String cleanXSS(String value) {
        value = value.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
        value = value.replaceAll("&#40;", "\\(").replaceAll("&#41;", "\\)");
        value = value.replaceAll("&#39;", "'");
        value = value.replaceAll("&quot;", "\"");
        value = value.replaceAll("\"\"", "[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']");
        value = value.replace("\r\n", "").replace(" ", "");
        return value;
    }



}
