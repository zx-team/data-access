source_data = {
	"initialization" : {
		width : 1050,
		height : 600,
		haveHead : true,
		headBtns : [ "new", "open", "save", "undo", "redo", "reload" ],
		haveTool : true,
		headLabel : true,
		haveGroup : true,
		useOperStack : true
	},
	"base" : {
		"displayname" : "基本信息",
		display : false,
		"shape" : "",
		"picname" : "",
		"baseproperty" : {
			"id" : {
				"name" : "Id",
				"componenttype" : "text", // 组件类型
				"texttype" : "text", // 文本类型
				"display" : ""
			},
			"name" : {
				"name" : "Name",
				"componenttype" : "text",
				"texttype" : "text",
				"display" : ""
			},
			"model" : {
				"name" : "Model",
				"componenttype" : "text",
				"texttype" : "text",
				"display" : ""
			},
			"type" : {
				"name" : "Type",
				"componenttype" : "text",
				"texttype" : "text",
				"display" : ""
			},
			"left" : {
				"name" : "Left-r",
				"componenttype" : "text",
				"texttype" : "number",
				"display" : "none"
			},
			"top" : {
				"name" : "Top-r",
				"componenttype" : "text",
				"texttype" : "number",
				"display" : ""
			},
			"width" : {
				"name" : "Width",
				"componenttype" : "text",
				"texttype" : "number",
				"display" : ""
			},
			"height" : {
				"name" : "Height",
				"componenttype" : "text",
				"texttype" : "number",
				"display" : ""
			}
		}
	},
	"start" : {
		"displayname" : "入口结点",
		display : true,
		"shape" : "round",
		"picname" : "",
		"baseproperty" : {
			"obligate" : {
				"name" : "start-obligate",
				"componenttype" : "text",
				"texttype" : "text",
				"display" : ""
			}
		},
		"serviceproperty" : {
			"high_level" : {
				"name" : "start-high_level",
				"componenttype" : "text",
				"texttype" : "text",
				"display" : ""
			}
		}
	},
	"end" : {
		"displayname" : "结束结点",
		display : true,
		"shape" : "round",
		"picname" : "",
		"baseproperty" : {
			"obligate" : {
				"name" : "end-obligate",
				"componenttype" : "text",
				"texttype" : "text",
				"display" : ""
			}
		},
		"serviceproperty" : {
			"high_level" : {
				"name" : "end-high_level",
				"componenttype" : "text",
				"texttype" : "text",
				"display" : ""
			}
		}
	},
	"task" : {
		"displayname" : "任务结点",
		display : true,
		"shape" : "round",
		"picname" : "",
		"baseproperty" : {
			"obligate" : {
				"name" : "task-obligate",
				"componenttype" : "text",
				"texttype" : "text",
				"display" : ""
			}
		},
		"serviceproperty" : {
			"high_level" : {
				"name" : "task-high_level",
				"componenttype" : "text",
				"texttype" : "text",
				"display" : ""
			}
		}
	},
	"node" : {
		"displayname" : "自动结点",
		display : true,
		"shape" : "",
		"picname" : "",
		"baseproperty" : {
			"obligate" : {
				"name" : "node-obligate",
				"componenttype" : "text",
				"texttype" : "text",
				"display" : ""
			}
		},
		"serviceproperty" : {
			"high_level" : {
				"name" : "node-high_level",
				"componenttype" : "text",
				"texttype" : "text",
				"display" : ""
			}
		}
	},
	"chat" : {
		"displayname" : "决策结点",
		display : true,
		"shape" : "",
		"picname" : "gooflow_bullet.png",
		"baseproperty" : {
			"obligate" : {
				"name" : "chat-obligate",
				"componenttype" : "text",
				"texttype" : "text",
				"display" : ""
			}
		},
		"serviceproperty" : {
			"high_level" : {
				"name" : "chat-high_level",
				"componenttype" : "text",
				"texttype" : "text",
				"display" : ""
			}
		}
	},
	"complex" : {
		"displayname" : "复合结点",
		display : true,
		"shape" : "",
		"picname" : "",
		"baseproperty" : {
			"obligate" : {
				"name" : "complex-obligate",
				"componenttype" : "text",
				"texttype" : "text",
				"display" : ""
			}
		},
		"serviceproperty" : {
			"high_level" : {
				"name" : "complex-high_level",
				"componenttype" : "text",
				"texttype" : "text",
				"display" : ""
			}
		}
	},
	
	
	"jiedian" : {
		"displayname" : "节点名称",
		display : true,
		"shape" : "",
		"picname" : "gooflow_icon2.png",
		"baseproperty" : {
			"shuxing1" : {
				"name" : "属性一",
				"componenttype" : "text",
				"texttype" : "text",
				"display" : ""
			}
		},
		"serviceproperty" : {
			"shuxing2" : {
				"name" : "属性二",
				"componenttype" : "text",
				"texttype" : "text",
				"display" : ""
			}
		}
	},
	
	
	"join" : {
		"displayname" : "联合结点",
		display : true,
		"shape" : "mix",
		"picname" : "",
		"baseproperty" : {
			"obligate" : {
				"name" : "join-obligate",
				"componenttype" : "text",
				"texttype" : "text",
				"display" : ""
			}
		},
		"serviceproperty" : {
			"high_level" : {
				"name" : "join-high_level",
				"componenttype" : "text",
				"texttype" : "text",
				"display" : ""
			}
		}
	},
	"xiaodongceshi" : {
		"displayname" : "小东测试",
		display : true,
		"shape" : "round",
		"picname" : "gooflow_bullet.png",
		"baseproperty" : {
			"obligate" : {
				"name" : "小东测试属性一",
				"componenttype" : "text",
				"texttype" : "text",
				"display" : "none"
			}
		},
		"serviceproperty" : {
			"high_level" : {
				"name" : "小东测试属性二",
				"componenttype" : "text",
				"texttype" : "text",
				"display" : ""
			}
		}
	},
	"fork" : {
		"displayname" : "分支结点",
		display : true,
		"shape" : "",
		"picname" : "",
		"baseproperty" : {
			"obligate" : {
				"name" : "fork-obligate",
				"componenttype" : "text",
				"texttype" : "text",
				"display" : ""
			}
		},
		"serviceproperty" : {
			"high_level" : {
				"name" : "fork-high_level",
				"componenttype" : "text",
				"texttype" : "text",
				"display" : ""
			}
		}
	},
	"plug" : {
		"displayname" : "附加插件",
		display : true,
		"shape" : "",
		"picname" : "",
		"baseproperty" : {
			"obligate" : {
				"name" : "plug-obligate",
				"componenttype" : "text",
				"texttype" : "text",
				"display" : ""
			}
		},
		"serviceproperty" : {
			"high_level" : {
				"name" : "plug-high_level",
				"componenttype" : "text",
				"texttype" : "text",
				"display" : ""
			}
		}
	},
	"state" : {
		"displayname" : "状态结点",
		display : true,
		"shape" : "",
		"picname" : "gooflow_bullet.png",
		"baseproperty" : {
			"obligate" : {
				"name" : "state-obligate",
				"componenttype" : "text",
				"texttype" : "text",
				"display" : ""
			}
		},
		"serviceproperty" : {
			"high_level" : {
				"name" : "state-high_level",
				"componenttype" : "text",
				"texttype" : "text",
				"display" : ""
			}
		}
	},
	"line" : {
		"displayname" : "节点连线",
		display : false,
		"shape" : "",
		"picname" : "",
		"baseproperty" : {
			"id" : {
				"name" : "Id",
				"componenttype" : "text",
				"texttype" : "text",
				"display" : ""
			},
			"name" : {
				"name" : "Name",
				"componenttype" : "text",
				"texttype" : "text",
				"display" : ""
			},
			"model" : {
				"name" : "Model",
				"componenttype" : "text",
				"texttype" : "text",
				"display" : ""
			},
			"type" : {
				"name" : "Type",
				"componenttype" : "text",
				"texttype" : "text",
				"display" : ""
			},
			"from" : {
				"name" : "From",
				"componenttype" : "text",
				"texttype" : "text",
				"display" : ""
			},
			"to" : {
				"name" : "To",
				"componenttype" : "text",
				"texttype" : "text",
				"display" : ""
			}
		}
	}
}