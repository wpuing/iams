/*此文件为上传功能界面所需*/

/*弹出上传界面框*/
function uploadPage(giveId) {
    layer.open({
        type: 1,
        area: ['400px', '200px'],
        fix: false, //不固定
        maxmin: true,
        shade: 0.4,
        title: '上传窗口',
        content: '<input  style=" margin: 20px 10px 20px 20px;" type="file" multiple name="file_0" id="input-file" class="input-file">',
        btn: ['上传', '取消'],
        yes: function(index, layero) {
            uplaodFile(giveId);//上传操作
            layer.close(index);
        },
        btn2: function(index, layero) {
            layer.close(index);
        }
    });

}

/*判断文件类型*/
function isExcel() {
    var fileInput = document.getElementById("input-file").value;
    //判断文件是否为空
    if (fileInput == null || fileInput.trim() == '') {
        //layer.msg('请上传文件！');
        return false;
    } else {
        //判断文件是否为excel文件
        var reg = /^.*\.(?:xls|xlsx)$/;
        if (!reg.test(fileInput)) {
            //layer.msg('请上传Excel文件！');
            return false;
        } else {
            return true;
        }
    }
}
/*文件上传*/
function uplaodFile(giveId) {
    //当文件符合标准的时候才上传
    if (isExcel()) {
        //将上传组件添加进临时表单
        var form = new FormData();
        form.append("file", document.getElementById("input-file").files[0]);
        //提交表单
        $.ajax({
            url: "http://localhost:8488/giveLessons/upload",
            data: form,
            cache: false,
            async: false,
            type: "POST",
            dataType: 'json',
            processData: false,
            contentType: false,
            success: function(data) {
                if (data.code == '200') {
                    layer_show("课程学生学号", my_localhost + "/giveLessons/studentNumbers/"+data.data+"/"+giveId, 800, 450);
                } else {
                    layer.alert("文件解析失败！");
                    layer_close();
                }
            },
            error: function(err) {
                layer.alert("文件上传失败！");
                layer_close();
            }
        });
    }
}