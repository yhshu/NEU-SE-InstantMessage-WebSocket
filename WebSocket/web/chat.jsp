<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <meta charset="UTF-8">
    <title>聊天</title>
    <!--文档 http://www.materialscss.com -->
    <link rel="stylesheet" href="css/materialize.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="css/myCSS.css">
    <!--Import jQuery before materialize.js-->
    <script type="text/javascript" src="js/jquery-3.2.1.js"></script>
    <script type="text/javascript" src="js/materialize.js"></script>
</head>

<body class="grey lighten-4">
<nav>
    <div class="nav-wrapper white">
        <a href="#" class="brand-logo black-text"
           style="font-size:28px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Message</a>
        <ul id="nav-mobile" class="right hide-on-med-and-down">
            <li><a class="black-text">20165248@stu.neu.edu.cn</a></li>
            <li><a href="login.html"><i class="material-icons black-text" style="vertical-align:bottom;">exit_to_app</i></a>
        </ul>
    </div>
</nav>

<!-- Page Layout here -->
<div class="row">
    <div class="col s12 m4 l3"> <!-- Note that "m4 l3" was added -->
        <!-- Grey navigation panel

        This content will be:
        3-columns-wide on large screens,
        4-columns-wide on medium screens,
        12-columns-wide on small screens  -->

        <div style="border: 0;" class="collection" id="menulist">
            <a style="border: 0; " href="" class="collection-item grey lighten-4 grey-text hoverable"><i
                    class="material-icons"
                    style="vertical-align:bottom">person</i>&nbsp;&nbsp;我的好友</a>
            <a style="border: 0; " href="" class="collection-item grey lighten-4 grey-text hoverable"><i
                    class="material-icons "
                    style="vertical-align:bottom">group</i>&nbsp;&nbsp;我的群组</a>
            <a style="border: 0; " href="" class="collection-item grey lighten-4 grey-text hoverable"><i
                    class="material-icons "
                    style="vertical-align:bottom">question_answer</i>&nbsp;&nbsp;在线客服</a>
        </div>
    </div>

    <div class="col s12 m8 l9" style="padding: 20px 11.25px;"> <!-- Note that "m8 l9" was added -->
        <!-- Teal page content

        This content will be:
        9-columns-wide on large screens,
        8-columns-wide on medium screens,
        12-columns-wide on small screens  -->

        <div class="input-field col s12">
            <!--接收窗口-->
            <label for="recvText">消息</label>
            <textarea disabled id="recvText" class="materialize-textarea " type="text"
                      style="overflow: auto; height: 370px;"></textarea>

        </div>
        <script>
            $('#recvText').trigger('autoresize');
        </script>
        <div class="input-field col s12">
            <!--发送窗口-->
            <i class="material-icons prefix">mode_edit</i>
            <input placeholder="键入并按Enter发送" id="sendText" type="text" class="validate">
        </div>
    </div>

    <script>

    </script>
</div>
</body>
</html>