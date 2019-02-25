<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>贷款超市后台管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <style>
        body{
            padding: 0px;
            margin: 0px;
        }
        iframe{
            border-style: none;
            width: 100%;
            height: 100%;
            overflow-x: hidden;
            overflow-y: hidden;
        }
    </style>
    <script>
        window.onload = function () {
            var oAjax;
            if (window.XMLHttpRequest){
                oAjax = new XMLHttpRequest();
            }else{
                oAjax = new ActiveXObject("Microsoft.XMLHTTP");
            }
            oAjax.open("GET", "/html?t='+new Date().getTime()", true);
            oAjax.send();
            oAjax.onreadystatechange = function () {
                if (oAjax.readyState === 4) {
                    if (oAjax.status === 200){
                        var result = JSON.parse(oAjax.responseText);
                        if(result.data){
                            document.body.innerHTML='<iframe src="index.html"></iframe>';
                        }else{
                            document.body.innerHTML='<iframe src="page/login/login.html"></iframe>';
                        }
                    }else {
                        //访问失败
                    }
                }
            };
        };
    </script>
</head>
<body>
</body>
</html>
