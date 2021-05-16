# -*- coding:utf-8 -*-
import jieba
import math
import re
import sys

def Sim(s1, s2):
    #读入两个txt文件存入s1,s2字符串中
    #s1 = open('对比库/爬山.txt','r',encoding='utf-8').read()
    #s2 = open('对比库/生命应该燃烧.txt','r',encoding='utf-8').read()

    #利用jieba分词与停用词表，将词分好并保存到向量中
    stopwords=[]
    fstop=open(r'D:\CodeSpace\Java\IDEA\demo\iams-dev\src\main\resources\stop_words.txt','r',encoding='utf-8')
    for eachWord in fstop:
        eachWord = re.sub("\n", "", eachWord) #re.sub替换
        stopwords.append(eachWord)
    fstop.close()
    s1_cut = [i for i in jieba.cut(s1, cut_all=True) if (i not in stopwords) and i!='']#列表生成式
    s2_cut = [i for i in jieba.cut(s2, cut_all=True) if (i not in stopwords) and i!='']
    word_set = set(s1_cut).union(set(s2_cut)) #并集

    #用字典保存两篇文章中出现的所有词并编上号
    word_dict = dict()
    i = 0
    for word in word_set:
        word_dict[word] = i
        i += 1


    #根据词袋模型统计词在每篇文档中出现的次数，形成向量
    s1_cut_code = [0]*len(word_dict) 

    for word in s1_cut:
        s1_cut_code[word_dict[word]]+=1

    s2_cut_code = [0]*len(word_dict)
    for word in s2_cut:
        s2_cut_code[word_dict[word]]+=1

    # 计算余弦相似度
    sum = 0
    sq1 = 0
    sq2 = 0
    for i in range(len(s1_cut_code)):
        sum += s1_cut_code[i] * s2_cut_code[i]
        sq1 += math.pow(s1_cut_code[i], 2)    #s1_cut_code[i]^2
        sq2 += math.pow(s2_cut_code[i], 2)

    try:
        result = round(float(sum) / (math.sqrt(sq1) * math.sqrt(sq2)), 3) #round 返回浮点数的四舍五入值，可用于设置小数位数。
    except ZeroDivisionError:
        result = 0.00
    #print("\n余弦相似度为：{:.2f} %".format(result * 100))
    return result

if __name__ == "__main__":
    s1 = sys.argv[1]
    s2 = sys.argv[2]
    h = Sim(s1,s2)
    print(h)
 
