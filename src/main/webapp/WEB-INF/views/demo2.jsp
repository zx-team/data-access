<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:v="urn:schemas-microsoft-com:vml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>流程图DEMO</title>
<!--[if lt IE 9]>
<?import namespace="v" implementation="#default#VML" ?>
<![endif]-->
<!--<link rel="stylesheet" type="text/css" href="codebase/GooFlow.css"/>-->
<style>
.myForm {
	display: block;
	margin: 0px;
	padding: 0px;
	line-height: 1.5;
	border: #ccc 1px solid;
	font: 12px Arial, Helvetica, sans-serif;
	margin: 5px 5px 0px 0px;
	border-radius: 4px;
}

.myForm .form_title {
	background: #428bca;
	padding: 4px;
	color: #fff;
	border-radius: 3px 3px 0px 0px;
}

.myForm .form_content {
	padding: 4px;
	background: #fff;
}

.myForm .form_content table {
	border: 0px
}

.myForm .form_content table td {
	border: 0px
}

.myForm .form_content table .th {
	text-align: right;
	font-weight: bold
}

.myForm .form_btn_div {
	text-align: center;
	border-top: #ccc 1px solid;
	background: #f5f5f5;
	padding: 4px;
	border-radius: 0px 0px 3px 3px;
}

#propertyForm {
	float: right;
	width: 260px
}
</style>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/gooflow0.8/GooFlow/codebase/GooFlow2.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/gooflow0.8/GooFlow/data.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/gooflow0.8/GooFlow/source_data.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/gooflow0.8/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/gooflow0.8/GooFunc.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/gooflow0.8/json2.js"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/gooflow0.8/default.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/gooflow0.8/GooFlow/codebase/GooFlow.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/gooflow0.8/GooFlow/codebase/GooFlow_color.js"></script>
<script type="text/javascript">
	var property = {};
	var remark = {};
	var demo;
	var projectname = '${pageContext.request.contextPath}';
	var jsondatastrbyserver;
	jsondatastrbyserver = '${jsondatastr}';
	function firstload() {
		demo = $.createGooFlow($("#demo"), property);
		demo.setNodeRemarks(remark);
		demo.loadData(JSON.parse(jsondatastrbyserver), source_data);
		demo.onItemFocus = function(id, model) {
			$("#peop").show();
			var obj;
			$("#custompropertytable tr").remove();
			if (model == "line") {
				obj = this.$lineData[id];
				addProperty(obj, source_data.line, true);
				$("#ele_model").val(model);
				$("#ele_type").val(obj.M);
			} else if (model == "node") {
				obj = this.$nodeData[id];
				for ( var i in source_data) {
					if ("line" != i) {
						if ("base" == i) {
							addProperty(obj, source_data[i], true);
						} else {
							if (obj.type == i || obj.type.indexOf(i) > -1) {
								addProperty(obj, source_data[i], true); 
							}
						}
					}
				}
			}
			$("#ele_model").val(model);
			$("#ele_id").val(id);
			return true;
		};
	}
	$(document).ready(function() {
		$("#peop").hide();
		catchRemark();
		catchProperty();
		catchToolBtns();
		firstload();
	});
	function addTr(tab, trHtml) {
		$('#custompropertytable > tbody:last-child').append(trHtml);
	}

	function catchRemark() {
		for ( var i in source_data) {
			var nodes = source_data[i];
			if ("base" != i && "line" != i) {

				remark[i] = nodes.displayname;
			}
		}
		remark["cursor"] = "选择指针";
		remark["direct"] = "结点连线";
		remark["group"] = "组织划分框编辑开关";
	}
	function catchProperty() {
		property = source_data.initialization;
	}

	function catchToolBtns() {
		var tempToolBtns = [];
		for ( var i in source_data) {
			var nodes = source_data[i];
			if (nodes.display) {
				if (source_data[i].shape != "") {
					tempToolBtns.push(i + " " + source_data[i].shape);
				} else {
					tempToolBtns.push(i);
				}

			}
		}
		source_data.initialization['toolBtns'] = tempToolBtns;
	}

	var out;
	function Export() {
		document.getElementById("result").value = JSON.stringify(demo
				.exportData());
	}
	function addProperty(obj, sourcedatejson, flag) {
		for ( var i in sourcedatejson) {
			if (i.indexOf("property") > -1) {
				var tempchild = sourcedatejson[i];
				for ( var j in tempchild) {
					var trHtml = '<tr style="display:'+tempchild[j].display+'"><td class="th">'
							+ tempchild[j].name
							+ '：</td><td><input type="text" style="width:120px" id="ele_'+j+'"/></td></tr>';
					addTr("custompropertytable", trHtml);
					if (obj.hasOwnProperty(j) && flag) {
						$("#ele_" + j).val(obj[j]);
					}
				}
			}

		}

	}
</script>
</head>
<body style="background: #EEEEEE">

	<div id="demo" style="margin: 5px; float: left"></div>
	<div id="peop">
		<form class="myForm" id="propertyForm">

			<div class="form_title">属性设置</div>

			<div class="form_content">
				<table id="custompropertytable">
					<tbody></tbody>
				</table>

			</div>
			<div class="form_btn_div">
				<input type="reset" value="重置" /> <input type="button" value="确定"
					onclick="saveProperty(demo.$focus)" />
			</div>
		</form>
	</div>
	<div style="clear: both">
		<input id="submit" type="button" value='导出结果' onclick="Export()" />
		<textarea id="result" row="6"></textarea>
	</div>
</body>
</html>
