package com.iams;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iams.common.constant.IamsConstants;
import com.iams.common.util.IamsUtils;
import com.iams.common.util.LayResult;
import com.iams.common.util.PythonUtil;
import com.iams.core.dto.AnswerDto;
import com.iams.core.dto.CourseDto;
import com.iams.core.dto.assginment.ChoiceDtoImpl;
import com.iams.core.dto.assginment.ChoiceDto;
import com.iams.core.dto.assginment.TopicDto;
import com.iams.core.dto.assginment.TopicDtoImpl;
import com.iams.core.dto.mcq.Choice;
import com.iams.core.dto.scores.AssignmentScoresDetails;
import com.iams.core.dto.scores.StudentScoresDto;
import com.iams.core.dto.student.*;
import com.iams.core.mapper.*;
import com.iams.core.pojo.*;
import com.iams.core.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
@RunWith(SpringRunner.class)
public class IamsTest {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private GiveLessonsService giveLessonsService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private AssignmentMapper topicMapper;

    @Autowired
    private AnswerMapper answerMapper;

    @Autowired
    private AssignmentService assignmentService;


    @Autowired
    private AssignmentMapper assignmentMapper;

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentResultMapper studentResultMapper;


    @Autowired
    private UpdateResultService updateResultService;

    @Test
    public void t26() {//查询该学生的作业成绩详情2
        AssignmentScoresDetails details = updateResultService.findScores(2, "stu1v0001");
        System.out.println("单选题： ");
        details.getSingleChoiceList().forEach(System.out::println);
        System.out.println("多选题： ");
        details.getMultipleChoiceList().forEach(System.out::println);
        System.out.println("判断题： ");
        details.getJudgeTopicList().forEach(System.out::println);
        System.out.println("填空题： ");
        details.getCompletionTopicList().forEach(System.out::println);
        System.out.println("主观题： ");
        details.getSubTopicList().forEach(System.out::println);

    }

    @Test
    public void t25() {//查询该学生的作业成绩详情
        Map<String,Object> map = new HashMap<>();
        map.put("type",4);
        map.put("typeId",4);
        map.put("assignmentId",2);
        map.put("studentNumber","stu1v0001");
        List<StudentScoresDto> list = studentResultMapper.selectScoresByNumber(map);
        list.forEach(System.out::println);

    }


    @Test
    public void t24() {//查询该学生的作业答案4
        AssignmentStudentDetails details = updateResultService.find(2, "stu1v0001");
        System.out.println("单选题： ");
        details.getSingleChoiceList().forEach(System.out::println);
        System.out.println("多选题： ");
        details.getMultipleChoiceList().forEach(System.out::println);
        System.out.println("判断题： ");
        details.getJudgeTopicList().forEach(System.out::println);
        System.out.println("填空题： ");
        details.getCompletionTopicList().forEach(System.out::println);
        System.out.println("主观题： ");
        details.getSubTopicList().forEach(System.out::println);

    }

    private List<ChoiceDto> selXuanzeti(List<ChoiceDto> list,Integer type,Integer assignmentId,String studentNumber){
        List<ChoiceDto> newList = new ArrayList<>();
        List<ChoiceDtoImpl> chiceDtoList = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("type",type);
        map.put("typeId",type);
        map.put("assignmentId",assignmentId);
        map.put("studentNumber",studentNumber);
        List<StudentResultDto> resultDtos = studentResultMapper.selectTopicByNumber(map);
        for (ChoiceDto choiceDto:list) {//遍历题目选项
            for (StudentResultDto studentResultDto:resultDtos) {//遍历查询出来的答案
                if(choiceDto.getTopicId()==studentResultDto.getOsTopicId()){//是否是当前的题目答案
                    //设置答案回显
                    List<Choice> choiceList = choiceDto.getMultipleChoice().getChoices();//得到选项集合
                    for (Choice choice:choiceList) {//遍历
                        choice.setIsKey(0);//设置全部不勾选
                        if(type==1){//单选题
                            if(choice.getNumber().equals(studentResultDto.getResult())){//是这个答案就勾选
                                choice.setIsKey(1);
                            }
                        }
                        if(type==2){//多选题
                            //提取答案
                            List<String> numbers = IamsUtils.getSubString(studentResultDto.getResult());
                            for (String number:numbers) {//遍历
                                if(choice.getNumber().equals(number)){//是这个答案就勾选
                                    choice.setIsKey(1);
                                }
                            }
                        }
                    }
                    choiceDto.setScore(studentResultDto.getScore())
                            .setResult(studentResultDto.getResult());
                    newList.add(choiceDto);
                }
            }

        }
        return newList;
    }


    private List<ChoiceDtoImpl> selXuanzeti1(List<ChoiceDto> list, Integer type, Integer assignmentId, String studentNumber){
        List<ChoiceDtoImpl> chiceDtoList = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("type",type);
        map.put("typeId",type);
        map.put("assignmentId",assignmentId);
        map.put("studentNumber",studentNumber);
        List<StudentResultDto> resultDtos = studentResultMapper.selectTopicByNumber(map);
        for (ChoiceDto choiceDto:list) {//遍历题目选项
            for (StudentResultDto studentResultDto:resultDtos) {//遍历查询出来的答案
                if(choiceDto.getTopicId()==studentResultDto.getOsTopicId()){//是否是当前的题目答案
                    ChoiceDtoImpl choiceDtoImpl = new ChoiceDtoImpl();
                    //设置答案回显
                    List<Choice> choiceList = choiceDto.getMultipleChoice().getChoices();//得到选项集合
                    for (Choice choice:choiceList) {//遍历
                        choice.setIsKey(0);//设置全部不勾选
                        if(type==1){//单选题
                            if(choice.getNumber().equals(studentResultDto.getResult())){//是这个答案就勾选
                                choice.setIsKey(1);
                            }
                        }
                        if(type==2){//多选题
                            //提取答案
                            List<String> numbers = IamsUtils.getSubString(studentResultDto.getResult());
                            for (String number:numbers) {//遍历
                                if(choice.getNumber().equals(number)){//是这个答案就勾选
                                    choice.setIsKey(1);
                                }
                            }
                        }
                    }
                    choiceDto.setScore(studentResultDto.getScore())
                            .setResult(studentResultDto.getResult());
                    BeanUtils.copyProperties(choiceDto, choiceDtoImpl);//拷贝
                    choiceDtoImpl.setStudentResultId(studentResultDto.getStudentResultId())
                            .setChildId(studentResultDto.getTopicId());
                    chiceDtoList.add(choiceDtoImpl);
                }
            }

        }
        return chiceDtoList;
    }

    private List<TopicDtoImpl> panTianZhu(List<TopicDto> list,Integer type,Integer assignmentId,String studentNumber){
        List<TopicDtoImpl> topicDtos = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("type",type);
        map.put("typeId",type);
        map.put("assignmentId",assignmentId);
        map.put("studentNumber",studentNumber);
        List<StudentResultDto> resultDtos = studentResultMapper.selectTopicByNumber(map);
        for (TopicDto topicDto:list) {//遍历题目选项
            for (StudentResultDto studentResultDto:resultDtos) {//遍历查询出来的答案
                if(topicDto.getTopicId()==studentResultDto.getOsTopicId()){//是否是当前的题目答案
                    TopicDtoImpl dto = new TopicDtoImpl();
                    //设置答案回显
                    topicDto.setScore(studentResultDto.getScore())
                            .setResult(studentResultDto.getResult());
                    BeanUtils.copyProperties(topicDto, dto);//拷贝
                    dto.setStudentResultId(studentResultDto.getStudentResultId())
                            .setChildId(studentResultDto.getTopicId());
                    topicDtos.add(dto);
                }
            }

        }
        return topicDtos;
    }


    @Test
    public void t23() {//查询该学生的作业答案4
        AssignmentDetails assignmentDetails = new AssignmentDetails();
        //作业信息
        assignmentDetails.setAssignment(assignmentService.find(2));
        //单选题
        assignmentDetails.setSingleChoiceList(IamsUtils.convert(assignmentService.find(2, IamsConstants.TOPIC_TYPE[0])));
        //多选题
        assignmentDetails.setMultipleChoiceList(IamsUtils.convert(assignmentService.find(2, IamsConstants.TOPIC_TYPE[1])));
        //判断题
        assignmentDetails.setJudgeTopicList(assignmentService.find(2,IamsConstants.TOPIC_TYPE[2]));
        //填空题
        assignmentDetails.setCompletionTopicList(assignmentService.find(2,IamsConstants.TOPIC_TYPE[3]));
        //主观题
        assignmentDetails.setSubTopicList(assignmentService.find(2,IamsConstants.TOPIC_TYPE[4]));
        IamsUtils.spliceTitle(assignmentDetails);//拼接题目

        List<StudentResultDto> allTopic=new ArrayList<>();
        for (Integer typeId:IamsConstants.TOPIC_TYPE) {
            Map<String,Object> map = new HashMap<>();
            map.put("type",typeId);
            map.put("typeId",typeId);
            map.put("assignmentId",2);
            map.put("studentNumber","stu1v0001");
            List<StudentResultDto> list = studentResultMapper.selectTopicByNumber(map);
            allTopic.addAll(list);
        }
        allTopic.forEach(System.out::println);
    }
    @Test
    public void t22() {//查询该学生的作业答案3
        List<TopicDto> topicDtos = assignmentService.find(2,IamsConstants.TOPIC_TYPE[2]);
        System.out.println("---------------------");
        topicDtos.forEach(System.out::println);
        System.out.println("---------------------");
        List<TopicDtoImpl> topicDtoList = panTianZhu(topicDtos, 3,2, "stu1v0001");
        System.out.println("=====================");
        topicDtoList.forEach(System.out::println);
        System.out.println("=====================");

    }

    @Test
    public void t21() {//查询该学生的作业答案2
        List<StudentResultDto> allTopic=new ArrayList<>();
        for (Integer typeId:IamsConstants.TOPIC_TYPE) {
            Map<String,Object> map = new HashMap<>();
            map.put("type",typeId);
            map.put("typeId",typeId);
            map.put("assignmentId",2);
            map.put("studentNumber","stu1v0001");
            List<StudentResultDto> list = studentResultMapper.selectTopicByNumber(map);
            allTopic.addAll(list);
        }
        allTopic.forEach(System.out::println);
    }


    @Test
    public void t20() {//查询该学生的作业答案1
        Map<String,Object> map = new HashMap<>();
        map.put("type",5);
        map.put("typeId",5);
        map.put("assignmentId",2);
        map.put("studentNumber","stu1v0001");
        List<StudentResultDto> list = studentResultMapper.selectTopicByNumber(map);
        list.forEach(System.out::println);
    }

    @Test
    public void t19() {//调用Python
        String path2 = PythonUtil.class.getResource("/").getPath();
        String s = System.getProperty("Sim.exe");
        System.out.println("路径："+s);
        String s1 = "我有一支画笔，我可以描画祖国的大好河山，可以画出青青草禾，可以画下堤岸杨柳。若是画不下来，没有关系，我还可以照下来，这是我的梦想。  那么，我的祖国呢。祖国的梦又是怎样的？  中国，一个饱经五千年风霜雨雪的悠悠古国，创造过多少绚丽的智慧，孕育过多少伟岸的精神。似乎一切都注定了要璀璨不朽。中国的地大物博、风景壮美，散发着迷人魅力。看上去是那么美好，充满生机。  但是，没有什么是永恒的。即使当年的雄心壮志，也无法预料今后隐藏在角落，无处不在的变数。变数是什么？它是人们无法解释的，无限可能。它身后有两条路，一条通向天堂，一条，是悬崖。  人们迈向小康的步伐龙骧虎步，一条条公路似乎通向美好的未来，一座座工厂似乎在制造成功……但是都是假象。时间齿轮缓缓转动，一切便显现出来了。我看到，刚刚洗出的相片，上面的一切都是那么模糊，我以为是浮尘，抬手擦拭，却发现怎样都抹不掉。  原来就是我拍的照片，那个关于祖国的照片。一个持续已久雾霾，似乎就能表明现实并不是想象的那么好。就像是在一个看起来是玻璃建造的泡沫里，眼看着风沙来临，却蜷缩着，固执的认为很安全。她轻而易举地透入薄薄的泡沫，在山水间肆意畅游，与纷纷行人玩闹。行人咒骂这位天气的不好，多么讽刺，抬头看看，那雾霾混合的都是什么？汽车排出的废气，工厂腾起的黑烟，焚烧麦秆的灰烬……一幕幕都浮现在眼前。很显然，罪魁祸首就是自己。我们的美丽中国是不是要一去不复返了呢？我摸着相片，狠狠地擦着，可又有什么用？擦破的相片，模糊的笑靥。我闭眼想象着。自己来到一个街道，空气那么清新，泛着点点花香，踏在一尘不染的地上东张西望。街道旁的一棵棵梧桐，葱绿的枝叶闪着光。树旁的居民楼，仿佛浮于绿海之上。树影投下的斑驳光影映在透明的伞上，殷波流转。姑娘如瀑布般的黑发垂至腰际，裙子飘飞似纷纷的青蝶。蓝天白云在我的头顶流动，仿佛触手可及。远处的青山，只用绿色渲染，没有勾勒，就像淡淡的水墨画，缓缓倾斜。黄昏，天空泛起迷人的涟漪。老人带着小孙子出来散步，浮出的笑容，欢畅的笑声悠扬在上空，缠绕在花间。殷红的夕阳，将祖孙俩的影子拉得很长很长……  这才是真正的中国吧。  仔细想想，对于现状，我们也应该做些什么。祖国母亲置身于雾霾的恶劣天气之中，我们不能坐视不管。现在春暖花开，时光大好。让我们迈开脚步，带着工具，到荒山去植树撒播绿色的希望，抵御风沙泥石流。节约用水，不再过度开采地下水，让罗布泊、青海湖回复原来的容貌。只要梦想没有干涸，只要种子还在泥土里，今年的夏天没有发芽，我还是会继续等待，有梦想就会有行动，有行动就会有成功，成就美好祖国就不再是空话。努力建造起坚固的玻璃，让祖国不再受侵害。这便是我的中国梦，也是每个国人的梦。  那我的相册上，笑靥会不会更加绚丽呢？我有一支画笔，我可以描画祖国的大好河山，可以画出青青草禾，可以画下堤岸杨柳。若是画不下来，没有关系，我还可以照下来，这是我的梦想。  那么，我的祖国呢。祖国的梦又是怎样的？  中国，一个饱经五千年风霜雨雪的悠悠古国，创造过多少绚丽的智慧，孕育过多少伟岸的精神。似乎一切都注定了要璀璨不朽。中国的地大物博、风景壮美，散发着迷人魅力。看上去是那么美好，充满生机。  但是，没有什么是永恒的。即使当年的雄心壮志，也无法预料今后隐藏在角落，无处不在的变数。变数是什么？它是人们无法解释的，无限可能。它身后有两条路，一条通向天堂，一条，是悬崖。  人们迈向小康的步伐龙骧虎步，一条条公路似乎通向美好的未来，一座座工厂似乎在制造成功……但是都是假象。时间齿轮缓缓转动，一切便显现出来了。我看到，刚刚洗出的相片，上面的一切都是那么模糊，我以为是浮尘，抬手擦拭，却发现怎样都抹不掉。  原来就是我拍的照片，那个关于祖国的照片。一个持续已久雾霾，似乎就能表明现实并不是想象的那么好。就像是在一个看起来是玻璃建造的泡沫里，眼看着风沙来临，却蜷缩着，固执的认为很安全。她轻而易举地透入薄薄的泡沫，在山水间肆意畅游，与纷纷行人玩闹。行人咒骂这位天气的不好，多么讽刺，抬头看看，那雾霾混合的都是什么？汽车排出的废气，工厂腾起的黑烟，焚烧麦秆的灰烬……一幕幕都浮现在眼前。很显然，罪魁祸首就是自己。我们的美丽中国是不是要一去不复返了呢？我摸着相片，狠狠地擦着，可又有什么用？擦破的相片，模糊的笑靥。我闭眼想象着。自己来到一个街道，空气那么清新，泛着点点花香，踏在一尘不染的地上东张西望。街道旁的一棵棵梧桐，葱绿的枝叶闪着光。树旁的居民楼，仿佛浮于绿海之上。树影投下的斑驳光影映在透明的伞上，殷波流转。姑娘如瀑布般的黑发垂至腰际，裙子飘飞似纷纷的青蝶。蓝天白云在我的头顶流动，仿佛触手可及。远处的青山，只用绿色渲染，没有勾勒，就像淡淡的水墨画，缓缓倾斜。黄昏，天空泛起迷人的涟漪。老人带着小孙子出来散步，浮出的笑容，欢畅的笑声悠扬在上空，缠绕在花间。殷红的夕阳，将祖孙俩的影子拉得很长很长……  这才是真正的中国吧。  仔细想想，对于现状，我们也应该做些什么。祖国母亲置身于雾霾的恶劣天气之中，我们不能坐视不管。现在春暖花开，时光大好。让我们迈开脚步，带着工具，到荒山去植树撒播绿色的希望，抵御风沙泥石流。节约用水，不再过度开采地下水，让罗布泊、青海湖回复原来的容貌。只要梦想没有干涸，只要种子还在泥土里，今年的夏天没有发芽，我还是会继续等待，有梦想就会有行动，有行动就会有成功，成就美好祖国就不再是空话。努力建造起坚固的玻璃，让祖国不再受侵害。这便是我的中国梦，也是每个国人的梦。  那我的相册上，笑靥会不会更加绚丽呢？";
        String s2 = "道德，是飘落在初春的雨，润物无声；道德，是吹拂在仲夏的风，丝丝透凉；道德，是成熟在深秋的果，丰收在望；道德，是绽放在严冬的花，傲雪凌霜……新世纪的曙光照亮了每个人的心房，人们一次又一次在历史中诉说着辉煌，在生活中创造了奇迹。可是，请别忘记：曾经我们最美好的东西——社会公德。  假设当你成为两鬓斑白的老人时，站在公交车上却无人给你让座，你的心情是怎样的呢？从班级中随地乱飞的作业本到学校中天女散花的垃圾，从马路上乱闯红灯任意穿梭的人群到街道旁随地吐痰的“潇洒”作风，从公共场所偷贵重物品的扒手到满街熟视无睹的过客，从夜深人静时满口胡言的骚扰短信到电视上说得天花乱坠的销售广告……我们总在呼唤着社会公德，然而，当你真正遇到时，却想不起以前的豪言壮语，造成了“徒有道德观，却无道德心”的悲哀。  幸而，人们总是患难见真情。不鸣则已，一鸣惊人！祖国的灾难，唤醒了我们沉睡已久美好公德——绝不能忘，汶川地震后任劳任怨的志愿者，节省零花钱纷纷捐款的孩子们；绝不能忘，献血车开来时，默默排队，无声无息站出来献血的奉献者；绝不能忘，房屋倒塌时，为了一个毫不相识的婴儿，牺牲自己的勇士们。  老子曰：“人之立身，所贵者在德。”没有道德的人是社会的耻辱，是民族的败类。天地间，没有人愿意同一个满嘴污秽，六亲不认的人多说话。中国，为何又能重回世界之巅？答案是毋庸置疑的——我们有一颗中国心，有一种道德观。俗话说：“一撇一捺写个人，一生一世学做人。”雷锋，20世纪中期人民的楷模！这个光辉的名字，在我们心中永垂不朽。他把自己最美好的东西——青春全部献给了党，献给了人民。年仅22岁，英年早逝，但他无私的道德观，高尚的情操必将在我们这一代身上不断发扬光大，他那不可磨灭的美好形象，将为华夏大地添一片流光溢彩！  还有7天，我们将迎来祖国母亲的生日，这一举国欢腾的节日。作为民族的未来和希望，我们必须从小做起，养成良好的文明礼仪，时时谨记“海内百川，有容纳大”的告诫，时时想到“处世礼为先”以此提醒，让社会成为“老有所终，幼有所养，壮有所用”的美好家园，让“五讲”，“四美”闪烁在神州大地上。朋友们，只要道德不失，我们的祖国便会一步步走向光明之巅！";
        String commandStr = "python  D:\\CodeSpace\\Java\\IDEA\\demo\\iams-dev\\src\\main\\resources\\文本余弦相似度.py "+s1+" "+s2;
        //String commandStr = "ipconfig";
        String result = PythonUtil.exeCmd(commandStr);
        System.out.println("相似度："+result);
    }

    @Test
    public void t18() {//计算主观题成绩的预估分
        String studentResult = "<p>爱美食跑步篮球足1111111111111111球冰棒球骑马游泳爱美食跑步篮球足球冰棒球骑马游泳爱美食跑步篮球足球冰棒球骑马游</p>"
                +"爱美食跑步篮球足球冰棒球骑马游泳"
                +"<img src='/dsdsdsdlsdsdsd/sdsdsd123.jpg'/>"
                +"<img src='/dsdsdsdlsdsdsd/sdsdsd456.jpg'/>"
                +"<p><span>爱美食跑步篮球足球冰棒球骑马游泳</span></p>"
                +"<img src='/dsdsdsdlsdsdsd/sdsdsd789.jpg'/>"
                +"爱美食跑步篮球足球冰棒球骑马游泳";
        String teacherResult = "<p>爱美食跑步篮球足球冰棒球骑马游泳爱美食跑步篮球足球冰棒球骑马游泳爱美食跑步篮球足球冰棒球骑马游</p>"
                +"爱美食跑步篮球足球冰棒球骑马游泳"
                +"<img src='/dsdsdsdlsdsdsd/sdsdsd123.jpg'/>"
                +"<img src='/dsdsdsdlsdsdsd/sdsdsd456.jpg'/>"
                +"<p><span>爱美食跑步篮球足球冰棒球骑马游泳</span></p>"
                +"<img src='/dsdsdsdlsdsdsd/sdsdsd789.jpg'/>"
                +"爱美食跑步篮球足球冰棒球骑马游泳";
        Long time = 560l;
        Float score = 25f;
        Float getsScore = SubjectiveResultService.getsScore(studentResult, teacherResult, time, score);
        System.out.println("成绩预估分："+getsScore);

    }

    @Test
    public void t17() {//查询该作业所有题目
        AssignmentDetails assignmentDetails = new AssignmentDetails();
        //作业信息
        assignmentDetails.setAssignment(assignmentService.find(2));
        //单选题
        assignmentDetails.setSingleChoiceList(IamsUtils.convert(assignmentService.find(2, IamsConstants.TOPIC_TYPE[0])));
        //多选题
        assignmentDetails.setMultipleChoiceList(IamsUtils.convert(assignmentService.find(2, IamsConstants.TOPIC_TYPE[1])));
        //判断题
        assignmentDetails.setJudgeTopicList(assignmentService.find(2,IamsConstants.TOPIC_TYPE[2]));
        //填空题
        assignmentDetails.setCompletionTopicList(assignmentService.find(2,IamsConstants.TOPIC_TYPE[3]));
        //主观题
        assignmentDetails.setSubTopicList(assignmentService.find(2,IamsConstants.TOPIC_TYPE[4]));
        IamsUtils.spliceTitle(assignmentDetails);//拼接题目
        System.out.println("作业信息："+assignmentDetails.getAssignment());
        assignmentDetails.getSingleChoiceList().forEach(System.out::println);
        assignmentDetails.getMultipleChoiceList().forEach(System.out::println);
        assignmentDetails.getJudgeTopicList().forEach(System.out::println);
        assignmentDetails.getCompletionTopicList().forEach(System.out::println);
        assignmentDetails.getSubTopicList().forEach(System.out::println);

    }
    @Test
    public void t16() {//查询学生所有课程已完成
        QueryWrapper<Assignment> query = Wrappers.<Assignment>query();
        // AND (a.limiting_time > NOW() OR a.limiting_time IS NULL);
        query.eq("a.course_id","I04BS254_02");
        query.eq("st.student_id","stu1v0001");
        query.and(a->a.apply("date_format(a.limiting_time,'%Y-%m-%d')<={0}", new Date())
                        .or()
                        .isNotNull("a.limiting_time"));
        List<StudentTaskDto> list = assignmentMapper.findStudentAssignment(query);
        list.forEach(System.out::println);
    }
    @Test
    public void t15() {//查询学生所有课程待完成
        QueryWrapper<Assignment> query = Wrappers.<Assignment>query();
        // AND (a.limiting_time > NOW() OR a.limiting_time IS NULL);
        query.eq("a.course_id","I04BS254_02");
        query.eq("st.student_id","stu1v0001");
        query.and(a->a.apply("date_format(a.limiting_time,'%Y-%m-%d')>{0}", new Date())
                        .or()
                        .isNull("a.limiting_time"));
        List<StudentTaskDto> list = assignmentMapper.findStudentAssignment(query);
        list.forEach(System.out::println);
    }


    @Test
    public void t14() {//查询学生所有课程
        LayResult result = courseService.findCourseByStudentId(null, "stu1v0001", 1, 10);
        System.out.println("数据："+result);
    }

    @Test
    public void t13() {//查询已删除的数据

    }

    @Test
    public void t12() {//根据角色查询所有权限树
//        List<MenuResult> list = permissionMapper.findRolePermissionByRoleId(3);
//        list.forEach(System.out::println);
//        System.out.println("-------------");

    }

    @Test
    public void t11() {//根据父i的和类型查询该菜单的全部权限
//        QueryWrapper<Permission> wrapper = Wrappers.<Permission>query();
//        wrapper.eq("ftd.fId",3);
//        List<PermissionDto> permissionDtoList = permissionMapper.findRolePermissionInfo(wrapper);
//        permissionDtoList.forEach(System.out::println);
//        System.out.println("-------------");

    }

    @Test
    public void t10() {//根据父i的和类型查询该菜单的全部权限
        List<Permission> permissionList = permissionService.find(null, "permission");
        permissionList.forEach(System.out::println);

    }

    @Test
    public void t9() {//查询授课学生
        GiveLessons g = new GiveLessons()
                .setTeacherId("th1v0002")
                .setCourseId("I04BS254_02");
        List<GiveLessons> giveLessonsList = giveLessonsService.find(g, false);
        giveLessonsList.forEach(System.out::println);
        System.out.println("---------------------------------");
        List<String> studentNumbers = giveLessonsList.stream().map(GiveLessons::getStudentId).collect(Collectors.toList());
        studentNumbers.forEach(System.out::println);

    }


    @Test
    public void t1() {
        List<Employee> employeeList = employeeMapper.selectList(null);
        List<Integer> list = employeeList.stream().map(Employee::getId).collect(Collectors.toList());
        list.forEach(System.out::println);
    }

    @Test
    public void t2() {
        QueryWrapper<Course> wrapper = Wrappers.<Course>query();
        wrapper.like("c.name", "J");
        Page<Course> page = new Page<>(1, 2, true);
        IPage<CourseDto> iPage = courseMapper.selectCourseDtoPage(page, wrapper);
        System.out.println("总记录数：" + iPage.getTotal());
        List<CourseDto> courseDtoList = iPage.getRecords();
        courseDtoList.forEach(System.out::println);
    }

    @Test
    public void t2_1() {
        System.out.println("基本目录：" + IamsConstants.ASSIGNMENT_FILE_PATH);
    }

    @Test
    public void t3() {
        QueryWrapper<Course> wrapper = Wrappers.<Course>query();
        //wrapper.like("c.name","J");
        Page<Course> page = new Page<>(1, 2, true);
        IPage<CourseDto> iPage = courseMapper.selCourseAndStuPage(page, wrapper);
        System.out.println("总记录数：" + iPage.getTotal());
        List<CourseDto> courseDtoList = iPage.getRecords();
        courseDtoList.forEach(System.out::println);
    }

    @Test
    public void t4() {
        LayResult layResult = courseService.find("", "I04BS255_02", 1, 1);
        System.out.println("数据：" + layResult);
    }


    @Test
    public void t5() {
        QueryWrapper<Assignment> query = Wrappers.<Assignment>query();
        // a.assignment_id=2 AND a.type_id=4
        query.eq("a.assignment_id", 2)
                .eq("a.type_id", 3);
        List<TopicDto> list = topicMapper.selectAllOTopic(query);
        System.out.println("查询的题目信息：");
        list.forEach(System.out::println);
    }

    @Test
    public void t6() {
        List<TopicDto> olist = assignmentService.find(2, null, true);
        System.out.println("查询的客观题目信息：");
        olist.forEach(System.out::println);
        List<TopicDto> slist = assignmentService.find(2, 5, false);
        System.out.println("查询的主观题目信息：");
        slist.forEach(System.out::println);
    }

    @Test
    public void t7() {
        Map<String, Object> map = new HashMap<>();
        map.put("type", "obj");
        map.put("assignmentId", "2");
        map.put("typeId", "5");
        Float score = topicMapper.findScore(map);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("type", "sub");
        map2.put("assignmentId", "2");
        map2.put("typeId", "5");
        Float score2 = topicMapper.findScore(map2);
        System.out.println("客观题：" + score);
        System.out.println("主观题：" + score2);
        System.out.println("总共：" + (score + score2));

    }

    @Test
    public void t8() {
        QueryWrapper<Answer> wrapper = Wrappers.<Answer>query();
        wrapper.eq("f.assignment_id", 2)
                .isNull("f.parent_id");
        List<AnswerDto> list = answerMapper.findByTopicId(2);
        list.forEach(System.out::println);

    }


}
