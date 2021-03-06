﻿<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>流程图DEMO</title>
<!--[if lt IE 9]>
<?import namespace="v" implementation="#default#VML" ?>
<![endif]-->
<!--<link rel="stylesheet" type="text/css" href="codebase/GooFlow2.css"/>-->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/gooflow0.8/GooFlow/codebase/GooFlow2.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/gooflow0.8/GooFlow/data.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/gooflow0.8/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/gooflow0.8/GooFunc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/gooflow0.8/json2.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/gooflow0.8/default.css"/>
<script type="text/javascript" src= "${pageContext.request.contextPath}/resources/gooflow0.8/GooFlow/codebase/GooFlow.js"></script>
<script type="text/javascript">
var property={
	width:1200,
	height:600,
	toolBtns:["start round","end round","task round","node","chat","state","plug","join","fork","complex mix"],
	haveHead:true,
	headBtns:["new","open","save","undo","redo","reload"],//如果haveHead=true，则定义HEAD区的按钮
	haveTool:true,
	headLabel:true,
	haveGroup:true,
	useOperStack:true
};
var remark={
	cursor:"选择指针",
	direct:"结点连线",
	start:"入口结点",
	"end":"结束结点",
	"task":"任务结点",
	node:"自动结点",
	chat:"决策结点",
	state:"状态结点",
	plug:"附加插件",
	fork:"分支结点",
	"join":"联合结点",
	"complex mix":"复合结点",
	group:"组织划分框编辑开关"
};
var demo;
var bbb;
bbb = '{"title":"13386网状流程","nodes":{"demo_node_9":{"name":"桂中区","left":10,"top":10,"type":"start round","width":24,"height":24,"alt":true},"demo_node_10":{"name":"桂北区","left":10,"top":81,"type":"start round","width":24,"height":24,"alt":true},"demo_node_11":{"name":"桂西区","left":9,"top":143,"type":"start round","width":24,"height":24,"alt":true},"demo_node_12":{"name":"桂北区","left":11,"top":290,"type":"start round","width":24,"height":24,"alt":true},"demo_node_14":{"name":"桂东区","left":7,"top":339,"type":"start round","width":24,"height":24,"alt":true},"demo_node_5":{"name":"停机判断","left":107,"top":47,"type":"fork","width":100,"height":24,"alt":true}},"lines":{"demo_line_6":{"type":"lr","M":81.5,"from":"demo_node_9","to":"demo_node_5","name":""},"demo_line_7":{"type":"lr","M":81.5,"from":"demo_node_10","to":"demo_node_5","name":""},"demo_line_9":{"type":"lr","M":81.5,"from":"demo_node_11","to":"demo_node_5","name":""}},"areas":{},"initNum":61}';
function arr(){
	demo=$.createGooFlow($("#demo"),property);
	demo.setNodeRemarks(remark);
	//demo.onItemDel=function(id,type){
	//	return confirm("确定要删除该单元吗?");
	//}
	demo.loadData(JSON.parse(bbb));
}

$(document).ready(function(){
	arr();
});
var out;
function Export(){
    document.getElementById("result").value=JSON.stringify(demo.exportData());
}
</script>
</head>
<body>
<div id="demo" style="margin:10px"></div>
<input id="submit" type="button" value='导出结果' onclick="Export()"/>
<textarea id="result" row="6"></textarea>
<input   type="hidden" id="writeForderPath"  name="hidForderPath"   value="2"/>
<input   type="hidden" id="readForderPath"  name="hidForderPath"   value="2"/>
<input   type="hidden" id="readFileName"  name="hidForderPath"   value="2"/>
文件存储路径：<input type="text" name ="tempwriteForderPath"></text>
文件读取路径：<input type="text" name ="tempreadForderPath"></text>
文件读取名称：<input type="text" name ="tempreadFileName"></text>
</body>
</html>
