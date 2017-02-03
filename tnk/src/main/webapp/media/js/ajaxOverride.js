/**
 * Created by tenderliTch on 2017/1/6.
 * 为jquery.ajax方法设置全部变量
 */
$.ajaxSetup({
    error:function(response){
        if (!response || response.status == 0)
            return;
        if (response.status == 401) { //未登录或登录超时
            App.message({
                text:"您还未登陆或登录超时,关闭提示将跳转到登录页.",
                title:'未登录',
                after_close:function(){
                    window.location.href = 'login.html';
                }
            });
        }else if (response.status == 403) { //无权限
            App.message("您无权限执行当前操作!");
        }else if (response.status == 500) { //后台异常
            if(response.responseJSON && response.responseJSON.msg){
                App.message(response.responseJSON.msg);
            }else{
                App.message("后台出错,响应详情:"+response);
            }
        }else{
            App.message("请求出错:"+this);
        }
    }
});