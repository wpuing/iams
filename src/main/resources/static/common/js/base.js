/*接口地址*/
var my_localhost = 'http://localhost:8488';


/*关闭弹出框口*/
function layer_close() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}


/*删除*/
function deleted(url,title) {
    $.post(url, function (data) {
        //回调回来的数据data
        if (data.code == '200') {
            layer.alert('删除'+title+'成功', function (index) {
                var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                parent.location.reload(); //刷新父页面，注意一定要在关闭当前iframe层之前执行刷新
                parent.layer.close(index); //再执行关闭
            });
        } else {
            layer.alert('删除'+title+'失败！！！！');
        }
    });
}

/*添加或修改*/
function insertOrUpdate(url,pojo,title) {
    $.post(url, pojo, function (data) {
        //回调回来的数据data
        if (data.code == '200') {
            layer.alert(title+'成功', function (index) {
                var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                parent.location.reload(); //刷新父页面，注意一定要在关闭当前iframe层之前执行刷新
                parent.layer.close(index); //再执行关闭
            });
        } else {
            layer.alert(title+'失败！！！！,响应码：' + data.code + " ,消息：" + data.message);
        }
    });
}

/*添加或修改*/
function iuAjax(url,pojo,title) {
    $.ajax({
        type: "POST",
        contentType: "application/json;charset=UTF-8",
        url: url,
        data:pojo,
        success:function(result) {
            if (result.code == '200') {
                layer.alert(title+'成功', function (index) {
                    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    parent.location.reload(); //刷新父页面，注意一定要在关闭当前iframe层之前执行刷新
                    parent.layer.close(index); //再执行关闭
                });
            } else {
                layer.alert(title+'失败！！！！,响应码：' + result.code + " ,消息：" + result.message);
            }
        },
        error:function(e){
            layer.alert(title+'失败！！！！,响应码：' + e.code + " ,消息：" + e.message);
        }
    });
}


