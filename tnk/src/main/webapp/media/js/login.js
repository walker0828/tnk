var Login = function () {
    
    return {
        //main function to initiate the module
        init: function () {
			//add by tenderlitch@21070105 登陆事件处理函数
			var loginForm=$('.login-form');
			/*function login(form){
				$.ajax({
					url:form.action,
					dataType:'json',
					data:{
						account
					},
					success:function(data){
						if(data){
							var result=data['status'];
							if(result==0){
								//未登陆状态
								window.location.href='login.html';
								return;
							}else if(result==1){
								window.location.href='index.html';
								return;
							}else if(result==2){
								$('.wrong-info-error', $('.login-form')).show();
								return;
							}
						}
						window.location.href='error.html';
					}
				});
			}*/
			loginForm.ajaxForm({
				type:'POST',
				dataType:'json',
				success:function(data){
					if(data){
						var result=data['status'];
						if(result==0){
							//未登陆状态
							window.location.href='login.html';
							return;
						}else if(result==1){
							window.location.href='index.html';
							return;
						}else if(result==2){
							$('.wrong-info-error', $('.login-form')).text(data['msg']).show();
							return;
						}
					}else{
						window.location.href='error.html';
					}
				}
			});

			loginForm.validate({
	            errorElement: 'label', //default input error message container
	            errorClass: 'help-inline', // default input error message class
	            focusInvalid: false, // do not focus the last invalid input
	            rules: {
	                username: {
	                    required: true
	                },
	                password: {
	                    required: true
	                },
	                remember: {
	                    required: false
	                }
	            },

	            messages: {
	                username: {
	                    required: "请在此键入您的账户名称."
	                },
	                password: {
	                    required: "请在此键入您的密码."
	                }
	            },

	            invalidHandler: function (event, validator) { //display error alert on form submit   
	                $('.alert-error', loginForm).show();
	            },

	            highlight: function (element) { // hightlight error inputs
	                $(element)
	                    .closest('.control-group').addClass('error'); // set error class to the control group
	            },

	            success: function (label) {
	                label.closest('.control-group').removeClass('error');
	                label.remove();
	            },

	            errorPlacement: function (error, element) {
	                error.addClass('help-small no-left-padding').insertAfter(element.closest('.input-icon'));
	            }/*,
				//remove by tenderlitch@20170123 因为注册了ajaxform,所以验证完成后不需要做其他动作
	            submitHandler: function (form) {
					form.submit();
	                //window.location.href = "index.html";
	            }*/
	        });

	        $('input',loginForm).keypress(function (e) {
	            if (e.which == 13) {
	                /*if ($('.login-form').validate().form()) {
	                    //window.location.href = "index.html";
						login(form);
	                }*/
					loginForm.submit();
	                return false;
	            }
	        });

	        $('.forget-form').validate({
	            errorElement: 'label', //default input error message container
	            errorClass: 'help-inline', // default input error message class
	            focusInvalid: false, // do not focus the last invalid input
	            ignore: "",
	            rules: {
	                email: {
	                    required: true,
	                    email: true
	                }
	            },

	            messages: {
	                email: {
	                    required: "请在此键入您的Email地址.",
						email: "请键入格式正确的Email地址"
	                }
	            },

	            invalidHandler: function (event, validator) { //display error alert on form submit   

	            },

	            highlight: function (element) { // hightlight error inputs
	                $(element)
	                    .closest('.control-group').addClass('error'); // set error class to the control group
	            },

	            success: function (label) {
	                label.closest('.control-group').removeClass('error');
	                label.remove();
	            },

	            errorPlacement: function (error, element) {
	                error.addClass('help-small no-left-padding').insertAfter(element.closest('.input-icon'));
	            },

	            submitHandler: function (form) {
	                window.location.href = "index.html";
	            }
	        });

	        $('.forget-form input').keypress(function (e) {
	            if (e.which == 13) {
	                if ($('.forget-form').validate().form()) {
	                    window.location.href = "index.html";
	                }
	                return false;
	            }
	        });

	        jQuery('#forget-password').click(function () {
	            jQuery('.login-form').hide();
	            jQuery('.forget-form').show();
	        });

	        jQuery('#back-btn').click(function () {
	            jQuery('.login-form').show();
	            jQuery('.forget-form').hide();
	        });

	        $('.register-form').validate({
	            errorElement: 'label', //default input error message container
	            errorClass: 'help-inline', // default input error message class
	            focusInvalid: false, // do not focus the last invalid input
	            ignore: "",
	            rules: {
	                username: {
	                    required: true
	                },
	                password: {
	                    required: true
	                },
	                rpassword: {
	                    equalTo: "#register_password",
						required: true
	                },
	                email: {
	                    required: true,
	                    email: true
	                },
	                tnc: {
	                    required: true
	                }
	            },

	            messages: { // custom messages for radio buttons and checkboxes
					username: {
						required: "请在此设置您的账户名称"
					},
					password: {
						required: "请在此设置您的密码"
					},
					rpassword: {
						equalTo: "两次键入的密码不一致,请重新输入",
						required: "请再次键入您的密码"
					},
					email: {
						required: "请在此键入您的Email地址",
						email: "请键入格式正确的Email地址"
					},
	                tnc: {
	                    required: "如要注册,请先接受服务条款和保密协议."
	                }
	            },

	            invalidHandler: function (event, validator) { //display error alert on form submit   

	            },

	            highlight: function (element) { // hightlight error inputs
	                $(element)
	                    .closest('.control-group').addClass('error'); // set error class to the control group
	            },

	            success: function (label) {
	                label.closest('.control-group').removeClass('error');
	                label.remove();
	            },

	            errorPlacement: function (error, element) {
	                if (element.attr("name") == "tnc") { // insert checkbox errors after the container                  
	                    error.addClass('help-small no-left-padding').insertAfter($('#register_tnc_error'));
	                } else {
	                    error.addClass('help-small no-left-padding').insertAfter(element.closest('.input-icon'));
	                }
	            },

	            submitHandler: function (form) {
	                window.location.href = "index.html";
	            }
	        });

	        jQuery('#register-btn').click(function () {
	            jQuery('.login-form').hide();
	            jQuery('.register-form').show();
	        });

	        jQuery('#register-back-btn').click(function () {
	            jQuery('.login-form').show();
	            jQuery('.register-form').hide();
	        });
        }

    };

}();