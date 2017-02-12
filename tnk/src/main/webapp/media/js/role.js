/**
 * 这个是用户页面的页面脚本
 * @type {{init}}
 */
var RoleTable = function () {

    /**
     * 初始化编辑窗口多选控件
     */
    var initMultiSelector=function(pagesSelector){
        $.ajax({
            url:'page/role/getUrls',
            dataType:'json',
            success:function(data){
                if(data && data.length>0){
                    $.each(data,function(inx,group){
                        if(group){
                            var optGroup=$('<optgroup>').attr('label',group['name']);
                            if(group['urls'] && group['urls'].length>0){
                                $.each(group['urls'],function(inx,url){
                                    var option=$('<option>').val(url['sid']).html(url['name']);
                                    optGroup.append(option);
                                });
                            }
                            pagesSelector.append(optGroup);
                        }
                    });
                }
                pagesSelector.multiSelect({
                    //增加"可选区域"和"已选区域"
                    selectableHeader:'<div class="selectable-header">可选项</div>',
                    selectionHeader:'<div class="selection-header">已选项</div>',
                    //为multiselect触发单独的validate处理过程,因为jquery.validate不会监控multiselect
                    afterSelect:function(){
                        this.$element.valid();
                    },
                    afterDeselect:function(){
                        this.$element.valid();
                    }
                });
            }
        })

    };

    return {

        //main function to initiate the module
        init: function () {

            var tableElement=$('#role_editable'),
                oTable = App.dataTable(tableElement,{
                    "aoColumns": [
                        { "mDataProp":function(){
                            return '<span class="row-details row-details-close"></span>';
                        }},
                        { "mDataProp":function(){
                            return '<input type="radio" class="checkboxes" name="role-table-radio"/>';
                        },"bSortable": false},
                        { "mDataProp": "name" },
                        { "mDataProp": "description", "bSortable": false }
                    ],
                    "sAjaxSource":"page/role/findByPage",
                    //声明表格有detail区域
                    "lxTableDetail":true,
                    //格式化数据表格行的detail区域
                    "fnFormatDetails":function(oTable, nTr) {
                        var aData = oTable.fnGetData( nTr );

                        var sOut = '';
                        if(aData && aData['urls'] && aData['urls'].length>0){
                            //对URL根据group来分组显示
                            var groupedUrls={};
                            $.each(aData['urls'],function(idx,url){
                                var urlArray=groupedUrls[url['groupName']];
                                if(!urlArray){
                                    urlArray=[];
                                    groupedUrls[url['groupName']]=urlArray;
                                }
                                urlArray.push(url);
                            });
                            for(var group in groupedUrls){
                                sOut += '<h5>'+group+'</h5><table>';
                                var urls=groupedUrls[group];
                                if(urls && urls.length>0){
                                    $.each(urls,function(idx,url){
                                        sOut += '<tr><td><b>'+url['name']+'</b>:</td><td><i>'+url['url']+'</i></td><td>'+url['description']+'</td></tr>';
                                    })
                                }
                                sOut += '</table>';
                            }
                        }
                        return sOut;
                    }
                }),
                form1 = $('#role_edit_form'),
                pagesSelector=$('#role_edit_win_pages',form1),
                //编辑窗口
                role_edit_win_modal=$('#role_edit_win');

            //初始化form表单中的多选框
            initMultiSelector(pagesSelector);

            //编辑窗口的验证
            App.validateForm(form1,{
                rules: {
                    name: {
                        minlength: 1,
                        maxlength: 32,
                        required: true
                    },
                    pageSids: {
                        required: true
                    }
                },
                errorContainer:'#role_edit_win_error'
            });

            //新增按钮点击事件
            $('#role_edit_table_add_btn').click(function(e){
                e.preventDefault();
                App.resetForm(form1);
                pagesSelector.multiSelect('refresh');
                role_edit_win_modal.modal();
            });

            //修改按钮点击事件
            $('#role_edit_table_edit_btn').click(function(e){
                e.preventDefault();
                //获取选择的行数据
                var selectedRow=$(':radio:checked',tableElement).parents('tr')[0];
                if(selectedRow){
                    var aData = oTable.fnGetData(selectedRow);
                    App.resetForm(form1);
                    App.bindForm(form1,aData);
                    pagesSelector.multiSelect('refresh');
                    role_edit_win_modal.modal();
                }else{
                    App.message('请选择要修改的角色');
                }
            });

            //删除按钮点击事件
            $('#role_edit_table_del_btn').click(function(e){
                e.preventDefault();
                //获取选择的行数据
                var selectedRow=$(':radio:checked',tableElement).parents('tr')[0];
                if(selectedRow){
                    var aData = oTable.fnGetData(selectedRow);
                    $.ajax({
                        url:'page/role/destroy',
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
                    role_edit_win_modal.data('bs.modal').hide();//关闭窗口
                    App.message('保存成功');
                }
            });

            //为处在form外面的submit按钮添加触发form提交事件的功能
            App.initOuterSubmit();

        }

    };

}();