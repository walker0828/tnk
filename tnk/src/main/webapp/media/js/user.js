/**
 * 这个是用户页面的页面脚本
 * @type {{init}}
 */
var UserTable = function () {

    return {

        //main function to initiate the module
        init: function () {

            var tableElement=$('#user_editable'),

                oTable =App.dataTable(tableElement,{
                    "aoColumns": [
                        { "mDataProp":function(){
                            return '<input type="radio" class="checkboxes" name="user-table-radio"/>';
                        }},
                        { "mDataProp": "account" },
                        { "mDataProp": "password", "bSortable": false },
                        { "mDataProp": "email" },
                        { "mDataProp": "description", "bSortable": false }
                    ],
                    "sAjaxSource":"page/user/findByPage"
                });

            //编辑窗口的验证
            var form1 = $('#user_edit_form');

            //编辑窗口的验证
            App.validateForm(form1,{
                rules: {
                    account: {
                        minlength: 2,
                        maxlength: 32,
                        required: true,
                        remote:'page/user/accountValid'
                    },
                    password: {
                        minlength: 6,
                        maxlength: 32,
                        required: true
                    },
                    password2:{
                        equalTo: '#user_edit_win_password',
                        //下面这些也需要,不然当密码和重复密码都不填的时候,重复密码部分的验证会是"正确"
                        minlength: 6,
                        maxlength: 32,
                        required: true
                    },
                    email: {
                        required: true,
                        email: true
                    }
                },
                errorContainer:'#user_edit_win_error'
            });
            //为重复密码输入框添加验证,因为这个数据不需要提交,所以没有name属性,所以不能写在上面的配置中
            //这里还是放到rules声明中去,因为下面的声明方式的触发时机是失去焦点,而表单提交事件不会触发下面的验证
            //TODO 可是试一下jQuery.validator.addClassRules( rules )
            /*$( "#user_edit_win_account2" ,form1).rules( "add", {
                equalTo: '#user_edit_win_password',
                //下面这些也需要,不然当密码和重复密码都不填的时候,重复密码部分的验证会是"正确"
                minlength: 6,
                maxlength: 32,
                required: true
            });*/

            //编辑窗口
            var user_edit_win_modal=$('#user_edit_win');
            //新增按钮点击事件
            $('#user_edit_table_add_btn').click(function(e){
                e.preventDefault();
                App.resetForm(form1);
                //设置account输入框为可编辑
                $('#user_edit_win_account',form1).removeProp('readonly')
                    .removeClass('ignore');//设置account不需要校验
                user_edit_win_modal.modal();
            });

            //修改按钮点击事件
            $('#user_edit_table_edit_btn').click(function(e){
                e.preventDefault();
                //获取选择的行数据
                var selectedRow=$(':radio:checked',tableElement).parents('tr')[0];
                if(selectedRow){
                    var aData = oTable.fnGetData(selectedRow);
                    App.resetForm(form1);
                    //将密码框的值设置到重复密码框中去
                    aData['password2']=aData['password'];
                    //设置account输入框为不可编辑
                    $('#user_edit_win_account',form1).prop('readonly',true)
                        .addClass('ignore');//设置account不需要校验
                    App.bindForm(form1,aData);
                    user_edit_win_modal.modal();
                }else{
                    App.message('请选择要修改的用户');
                }
            });

            //删除按钮点击事件
            $('#user_edit_table_del_btn').click(function(e){
                e.preventDefault();
                //获取选择的行数据
                var selectedRow=$(':radio:checked',tableElement).parents('tr')[0];
                if(selectedRow){
                    var aData = oTable.fnGetData(selectedRow);
                    $.ajax({
                        url:'page/user/destroy',
                        type:'POST',
                        data:{
                            sid:aData.sid,
                            version:aData.version
                        },
                        success:function(){
                            oTable.fnDraw(true);//刷新表格
                            App.message('操作成功');
                        }
                    });
                }else{
                    App.message('请选择要删除的用户');
                }
            });

            //ajax form插件
            form1.ajaxForm({
                type:'post',
                success:function(){
                    oTable.fnDraw(true);//刷新表格
                    user_edit_win_modal.data('bs.modal').hide();//关闭窗口
                    App.message('保存成功');
                }
            });

            //为处在form外面的submit按钮添加触发form提交事件的功能
            App.initOuterSubmit();

        }

    };

}();