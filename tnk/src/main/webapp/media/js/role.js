/**
 * 这个是用户页面的页面脚本
 * @type {{init}}
 */
var UserTable = function () {

    return {

        /**
         * 重置表单元素的值和校验状态,隐藏额外的错误信息
         * @param form 要重置的表单
         * @param additional 要隐藏的错误信息元素,可以是数组或单个对象
         */
        resetForm: function(form,additional){
            if(form){
                //新增的时候清理输入框(因为新增和修改操作打开的是同一个form元素)
                if(form.clearForm)form.clearForm(true);
                //清理验证状态
                if(form.data && form.data('validator') && form.resetForm)form.data('validator').resetForm();
            }

            if(additional){
                if($.isArray(additional)){
                    $.each(additional,function(toHide){
                        if(toHide.hide)toHide.hide();
                    })
                }else{
                    if(additional.hide)additional.hide();
                }
            }
        },
        bindForm: function(form,data){
            $('input,textarea,select[name]',form).each(function(index,field){
                $(field).val(data[field.name]);
            });
        },

        //main function to initiate the module
        init: function () {

            var me=this,
                tableElement=$('#user_editable'),

                oTable = tableElement.dataTable({
                    "aLengthMenu": [
                        [5, 15, 20, -1],
                        [5, 15, 20, "全部"] // change per page values here
                    ],
                    // set the initial value
                    "iDisplayLength": 5,
                    "aaSorting": [],//默认情况下排序列是第一列,这里去掉第一列排序的默认值
                    "sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
                    "sPaginationType": "bootstrap",
                    "oLanguage": {
                        "oSearchInputPlaceHolder":"通过账户或邮件地址筛选",
                        "sLengthMenu": "_MENU_ 条记录每页",
                        "oPaginate": {
                            "sPrevious": "上一页",
                            "sNext": "下一页"
                        },
                        //add by tenderlitch@20170105 修改dataTable显示文字
                        "sSearch":"搜索&nbsp;&nbsp;",
                        "sInfo": "从第 _START_ 到第 _END_ 条,总计 _TOTAL_ 条",
                        "sInfoEmpty":"数据集为空",
                        "sEmptyTable":"无符合条件的数据",
                        "sZeroRecords":"无符合条件的数据",
                        "sLoadingRecords":'<i class="icon-coffee"></i>&nbsp;数据加载中...'
                    },
                    "aoColumnDefs": [{
                        'bSortable': false,
                        'aTargets': [0]
                    }],
                    "aoColumns": [
                        { "mDataProp":function(){
                            return '<input type="radio" class="checkboxes" name="user-table-radio"/>';
                        }},
                        { "mDataProp": "account" },
                        { "mDataProp": "password", "bSortable": false },
                        { "mDataProp": "email" },
                        { "mDataProp": "description", "bSortable": false }
                    ],
                    //add by tenderlitch@20170105 远程获取数据
                    "sAjaxSource":"page/user/findByPage",
                    "bServerSide":true,
                    "fnDrawCallback":function(oSetting){
                        //异步加载数据并且绘制完成之后,为数据行中的radio控件加载uniform样式
                        App.initUniform($(':radio',oSetting.nTable));
                    }
                    /*"sAjaxDataProp":"ajaxResponse.data.aaData"*/

            });
            /**add by tenderlitch@20170118 为jquery.dataTable增加"点击行选择"功能**/
            $('tbody',tableElement).on( 'click', 'tr', function () {
                var tr=$(this);
                if(tr.hasClass('selected')){
                    tr.removeClass('selected');
                    $.uniform.update($(':radio',tr).removeAttr('checked'));
                }else{
                    var trFormerSelected=$('tr.selected',tr.parent('tbody')),
                        radioFormerChecked=$(':radio',trFormerSelected);
                    trFormerSelected.removeClass('selected');
                    tr.addClass('selected');
                    $.uniform.update([$(':radio',tr).attr('checked',true),radioFormerChecked]);
                }
            } );

            var user_editable_wrapper=$('#user_editable_wrapper'),
                dataTables_filter_input=$('.dataTables_filter input',user_editable_wrapper),
                dataTables_length_select=$('.dataTables_length select',user_editable_wrapper);
            dataTables_filter_input.addClass("m-wrap medium");// modify table search input
            dataTables_length_select.addClass("m-wrap small").select2({
                minimumResultsForSearch: -1//hide search box with special css class
            });// modify table per page dropdown // initialzie select2 dropdown

            //编辑窗口的验证
            var form1 = $('#user_edit_form');
            var error1 = $('.alert-error', form1);
            var validator=form1.validate({
                errorElement: 'span', //default input error message container
                errorClass: 'help-inline', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                ignore: ".ignore",
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

                invalidHandler: function () { //display error alert on form submit
                    error1.show();
                    App.scrollTo(error1, -200);
                },

                highlight: function (element) { // hightlight error inputs
                    $(element)
                        .closest('.help-inline').removeClass('ok'); // display OK icon
                    $(element)
                        .closest('.control-group').removeClass('success').addClass('error'); // set error class to the control group
                },

                unhighlight: function (element) { // revert the change dony by hightlight
                    $(element)
                        .closest('.control-group').removeClass('success').removeClass('error'); // set error class to the control group
                },

                success: function (label) {
                    label
                        .addClass('valid').addClass('help-inline ok') // mark the current input as valid and display OK icon
                        .closest('.control-group').removeClass('error').addClass('success'); // set success class to the control group
                    //判断form的field是否都校验通过,如果是,去掉额外的提示信息栏
                    if(validator.numberOfInvalids()===0) error1.hide();
                }/*,
                //去掉这里,不然jquery.form插件无法提交表单,提交表单的代码就只能写在这里
                submitHandler: function (form) {
                    error1.hide();
                }*/
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
                me.resetForm(form1);
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
                    me.resetForm(form1);
                    //将密码框的值设置到重复密码框中去
                    aData['password2']=aData['password'];
                    //设置account输入框为不可编辑
                    $('#user_edit_win_account',form1).prop('readonly',true)
                        .addClass('ignore');//设置account不需要校验
                    me.bindForm(form1,aData);
                    user_edit_win_modal.modal();
                }else{
                    App.message('请在行的头部勾选以选择要修改的用户');
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
                    App.message('请在行的头部勾选以选择要删除的用户');
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