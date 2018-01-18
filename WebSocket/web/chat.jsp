<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <meta charset="UTF-8">
    <title>MyMessage</title>
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
           style="font-size:28px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;MyMessage</a>
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
            <textarea disabled id="recvText" class="materialize-textarea black-text" type="text"
                      style="overflow: auto; height: 370px;" placeholder="暂无信息"></textarea>
        </div>

        <div class="input-field col s12">
            <!--发送窗口-->
            <i class="material-icons prefix">mode_edit</i>
            <input placeholder="键入并按Enter发送" id="sendText" type="text" class="validate">
        </div>
    </div>

    <script type="application/javascript">
        var Chat = {};
        Chat.socket = null;
        Chat.connect = (function (host) {
            if ('WebSocket' in window) {
                Chat.socket = new WebSocket(host);
            } else if ('MozWebSocket' in window) {
                Chat.socket = new MozWebSocket(host);
            }
            else {
                Console.log('错误：WebSocket 不支持您的浏览器');
                return;
            }

            Chat.socket.onopen = function () {
                Console.log('信息：WebSocket 连接已建立');
                document.getElementById('sendText').onkeydown = function (event) {
                    if (event.keyCode === 13)
                        Chat.sendMessage();
                };
            };

            Chat.socket.onclose = function () {
                document.getElementById('sendText').onkeydown = null;
                Console.log('信息：WebSocket 已关闭');
            };

            Chat.socket.onmessage = function (message) {
                console.log(message.data);
            };
        });

        Chat.initialize = function () {
            if (window.location.protocol === 'http:') {
                Chat.connect('ws://' + window.location.host + '/WebSocket/ChatSocket');
            } else {
                Chat.connect('wss://' + window.location.host + '/WebSocket/ChatSocket');
            }
        };

        Chat.sendMessage = (function () {
            var message = document.getElementById("sendText").value;
            if (message !== '') {
                Chat.socket.send(message);
                document.getElementById('sendText').value = "";
            }
        });

        var Console = {};

        Console.log = (function (message) {
            var console = document.getElementById("recvText");
            console.innerText += message + "\n";
            console.trigger('autoresize');
        });

        Chat.initialize();
    </script>

</div>
</body>
</html>