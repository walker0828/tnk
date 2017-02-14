/**
 * Created by tenderliTch on 2017/1/6.
 * 为jquery.ajax方法设置全部变量
 */
$.ajaxSetup({
    error:function(response){
        //移除loading状态(不管有没有)
        App.unblockUI($('.page-content'));

        if (response.status == 401) { //未登录或登录超时
            App.message({
                text:"您还未登陆或登录超时,关闭提示将跳转到登录页.",
                title:'未登录',
                after_close:function(){
                    window.location.href = 'login.html';
                }
            });
        }else if (response.status == 403) { //无权限
            var resourceString=(response.responseText)? $.parseJSON(response.responseText)['msg']:'';
            App.message("访问资源: "+resourceString+" 被拒绝!");
        }else if (response.status == 500) { //后台异常
            var msgString=(response.responseText)? $.parseJSON(response.responseText)['msg']:'';
            if(msgString){
                App.message(msgString);
            }else{
                App.message("后台出错,响应详情:"+response);
            }
        }else{
            App.message("请求出错:"+this);
        }
    }
});