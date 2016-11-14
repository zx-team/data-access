<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:v="urn:schemas-microsoft-com:vml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>流程图DEMO</title>
<!--[if lt IE 9]>
<?import namespace="v" implementation="#default#VML" ?>
<![endif]-->
<!--<link rel="stylesheet" type="text/css" href="codebase/GooFlow2.css"/>-->

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/gooflow0.8/layout-default.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/gooflow0.8/jquery-1.11.1.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/gooflow0.8/jquery-ui-1.11.0.js"></script>
	<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/gooflow0.8/jquery.layout-latest.js"></script>

<script type="text/javascript">
	var myLayout;
	$(document).ready(function() {
		$("#radioset").buttonset();
		$("#button").button();
		var tabs = $("#tabs").tabs();
		tabs.find(".ui-tabs-nav").sortable({
			axis : "x",
			stop : function() {
				tabs.tabs("refresh");
			}
		});
		myLayout = $('body').layout({
			applyDefaultStyles : false,
			north : {
				size : 70,
				spacing_open : 2,
				closable : true,
				resizable : true
			},
			west : {
				size : 200,
				spacing_open : 2,
				closable : true,
				resizable : true
			},
			south : {
				size : 25,
				spacing_open : 2,
				closable : true,
				resizable : true
			}
		});
	

	});
</script>
</head>
<body>


	<DIV class="ui-layout-north">
		<table>
			<tr>
				<td><iframe src="${pageContext.request.contextPath}/queryById?tempId=1&tempType=2"></iframe></td>
			</tr>
		</table>
	</DIV>
	<DIV class="ui-layout-center">
		<table>
			<tr>
				<td ><iframe src="${pageContext.request.contextPath}/queryById?tempId=1&tempType=2" style="width:1000px;height:600px;"></iframe></td>
			</tr>
		</table>
	</DIV>


</body>
</html>
