function upload(url,fName){
    var formData = new FormData($("#uploadForm")[0])  //创建一个forData
    formData.append(fName, $('#aFile')[0].files[0]) //把file添加进去  name命名为img
    $.ajax({
        url: my_localhost+url,
        data: formData,
        type: "POST",
        async: false,
        cache: false,
        contentType: false,
        processData: false,
        success: function(data) {
            if(data.code=='200'){
                $("#assfile").val(data.data);
            }else {
                layer.alert("上传文件失败："+data.message);
            }
        },
        error: function(data) {
            layer.alert("上传文件失败："+data.message);
        }
    })
}

function uploadPage(url,name) {
    layer.open({
        type: 1,
        area: ['400px', '200px'],
        fix: false, //不固定
        maxmin: true,
        shade: 0.4,
        title: '上传窗口',
        content:
            '<div class="layui-card">'+
            '<div class="layui-card-header">上传文件（非必要上传）</div>'+
            '<div class="layui-card-body">'+
            '<form enctype="multipart/form-data" id="uploadForm">'+
            '<input type="file" name="'+name+'" id="aFile"/>'+
            ' </form>'+
            '</div>'+
            '</div>'
        ,
        btn: ['上传', '取消'],
        yes: function(index, layero) {
            upload(url,name);
            layer.close(index);
        },
        btn2: function(index, layero) {
            layer.close(index);
        }
    });
}


