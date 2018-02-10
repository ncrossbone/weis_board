var mapBoard = (function() {
	var spot = [{isBo:false,name:"임진강4",id:"1023A25",isAll:true,wSys:"hanRiver"},
	    		{isBo:false,name:"가양",id:"1019A05",isAll:true,wSys:"hanRiver"},
			    {isBo:false,name:"노량진",id:"1018A52",isAll:true,wSys:"hanRiver"},
				{isBo:false,name:"팔당댐",id:"1017A10",isAll:true,wSys:"hanRiver"},
				{isBo:false,name:"의암댐",id:"1013A40",isAll:true,wSys:"hanRiver"},
				{isBo:false,name:"경안천5",id:"1016A70",isAll:true,wSys:"hanRiver"},
				{isBo:false,name:"소양강2",id:"1012A40",isAll:true,wSys:"hanRiver"},
				{isBo:false,name:"삼봉리",id:"1015A60",isAll:true,wSys:"hanRiver"},
				{isBo:false,name:"강상",id:"1007A75",isAll:true,wSys:"hanRiver"},
				{isBo:true,name:"이포보",id:"1007A60",isAll:true,wSys:"hanRiver",fiveText:'1.5'},
				{isBo:true,name:"여주보",id:"1007A27",isAll:true,wSys:"hanRiver",fiveText:'1.0'},
				{isBo:true,name:"강천보",id:"1007A20",isAll:true,wSys:"hanRiver",fiveText:'2.5'},
				{isBo:false,name:"섬강4-1",id:"1006A80",isAll:true,wSys:"hanRiver"},
				{isBo:false,name:"달천4",id:"1004A60",isAll:true,wSys:"hanRiver"},
				{isBo:false,name:"충주댐",id:"1003A74",isAll:true,wSys:"hanRiver"},
				{isBo:false,name:"영월2",id:"1003A05",isAll:true,wSys:"hanRiver"},
				{isBo:false,name:"안성천3",id:"1101A25",isAll:true,wSys:"hanRiver"},
				{isBo:false,name:"안동1",id:"2001A60",isAll:true,wSys:"nakdongRiver"},
				{isBo:false,name:"반변천2-1",id:"2002A50",isAll:true,wSys:"nakdongRiver"},
				{isBo:false,name:"안동3",id:"2003A30",isAll:true,wSys:"nakdongRiver"},
				{isBo:false,name:"내성천3-1",id:"2004A90",isAll:true,wSys:"nakdongRiver"},
				{isBo:true,name:"상주보",id:"2007A25",isAll:true,wSys:"nakdongRiver",fiveText:'3.0'},
				{isBo:true,name:"낙단보",id:"2009A05",isAll:true,wSys:"nakdongRiver",fiveText:'5.5'},
				{isBo:false,name:"상주3",id:"2009A10",isAll:true,wSys:"nakdongRiver"},
				{isBo:true,name:"구미보",id:"2009A30",isAll:true,wSys:"nakdongRiver",fiveText:'4.5'},
				{isBo:true,name:"칠곡보",id:"2011A25",isAll:true,wSys:"nakdongRiver",fiveText:'6.5'},
				{isBo:false,name:"왜관",id:"2011A30",isAll:true,wSys:"nakdongRiver"},
				{isBo:true,name:"강정고령보",id:"2011A55",isAll:true,wSys:"nakdongRiver",fiveText:'5.5'},
				{isBo:false,name:"강정고령보1",id:"2011A551",isAll:false,wSys:"nakdongRiver"},//추가
				{isBo:false,name:"강정고령보2-1",id:"2011A552",isAll:false,wSys:"nakdongRiver"},//추가
				{isBo:false,name:"강정고령보2",id:"2011A553",isAll:false,wSys:"nakdongRiver"},//추가
				{isBo:false,name:"강정고령보3",id:"2011A554",isAll:false,wSys:"nakdongRiver"},//추가
				{isBo:false,name:"금호강6",id:"2012A70",isAll:true,wSys:"nakdongRiver"},
				{isBo:false,name:"고령",id:"2014A20",isAll:true,wSys:"nakdongRiver"},
				{isBo:true,name:"달성보",id:"2014A25",isAll:true,wSys:"nakdongRiver",fiveText:'3.5'},
				{isBo:false,name:"달성보1",id:"2014A251",isAll:false,wSys:"nakdongRiver"},//추가
				{isBo:false,name:"달성보2-1",id:"2014A252",isAll:false,wSys:"nakdongRiver"},//추가
				{isBo:false,name:"달성보2",id:"2014A253",isAll:false,wSys:"nakdongRiver"},//추가
				{isBo:false,name:"달성보3",id:"2014A254",isAll:false,wSys:"nakdongRiver"},//추가
				{isBo:true,name:"합천창녕보",id:"2014A70",isAll:true,wSys:"nakdongRiver",fiveText:'2.5'},
				{isBo:false,name:"합천창녕보1",id:"2014A701",isAll:false,wSys:"nakdongRiver"},//추가
				{isBo:false,name:"합천창녕보2-1",id:"2014A702",isAll:false,wSys:"nakdongRiver"},//추가
				{isBo:false,name:"합천창녕보2",id:"2014A703",isAll:false,wSys:"nakdongRiver"},//추가
				{isBo:false,name:"합천창녕보3",id:"2014A704",isAll:false,wSys:"nakdongRiver"},//추가
				{isBo:false,name:"남강4-1",id:"2019A80",isAll:true,wSys:"nakdongRiver"},
				{isBo:false,name:"남지",id:"2020A10",isAll:true,wSys:"nakdongRiver"},
				{isBo:true,name:"창녕함안보",id:"2020A32",isAll:true,wSys:"nakdongRiver",fiveText:'3.5'},
				{isBo:false,name:"창녕함안보1",id:"2020A321",isAll:false,wSys:"nakdongRiver"},//추가
				{isBo:false,name:"창녕함안보2-1",id:"2020A322",isAll:false,wSys:"nakdongRiver"},//추가
				{isBo:false,name:"창녕함안보2",id:"2020A323",isAll:false,wSys:"nakdongRiver"},//추가
				{isBo:false,name:"창녕함안보3",id:"2020A324",isAll:false,wSys:"nakdongRiver"},//추가
				{isBo:false,name:"창녕함안보하류",id:"2020A325",isAll:false,wSys:"nakdongRiver"},//추가
				{isBo:false,name:"물금",id:"2022A10",isAll:true,wSys:"nakdongRiver"},
				{isBo:false,name:"구포",id:"2022A35",isAll:true,wSys:"nakdongRiver"},
				{isBo:false,name:"학성",id:"2201A48",isAll:true,wSys:"nakdongRiver"},
				{isBo:false,name:"형산강4",id:"2101A60",isAll:true,wSys:"nakdongRiver"},
				{isBo:false,name:"옥천",id:"3006A10",isAll:true,wSys:"geumRiver"},
				{isBo:false,name:"대청댐",id:"3008A40",isAll:true,wSys:"geumRiver"},
				{isBo:false,name:"갑천5-1",id:"3009A80",isAll:true,wSys:"geumRiver"},
				{isBo:false,name:"청원-1",id:"3010A20",isAll:true,wSys:"geumRiver"},
				{isBo:false,name:"미호천6-1",id:"3011A97",isAll:true,wSys:"geumRiver"},
				{isBo:true,name:"세종보",id:"3012A07",isAll:true,wSys:"geumRiver",fiveText:'1.0'},
				{isBo:false,name:"공주1",id:"3012A20",isAll:true,wSys:"geumRiver"},
				{isBo:true,name:"공주보",id:"3012A32",isAll:true,wSys:"geumRiver",fiveText:'4.5'},
				{isBo:false,name:"공주보1",id:"3012A321",isAll:false,wSys:"geumRiver"},//추가
				{isBo:false,name:"공주보2-1",id:"3012A322",isAll:false,wSys:"geumRiver"},//추가
				{isBo:false,name:"공주보2",id:"3012A323",isAll:false,wSys:"geumRiver"},//추가
				{isBo:false,name:"공주보3",id:"3012A324",isAll:false,wSys:"geumRiver"},//추가
				{isBo:false,name:"공주보하류1",id:"3012A325",isAll:false,wSys:"geumRiver"},//추가
				{isBo:false,name:"공주보하류2",id:"3012A326",isAll:false,wSys:"geumRiver"},//추가
				{isBo:true,name:"백제보",id:"3012A42",isAll:true,wSys:"geumRiver",fiveText:'3.0'},
				{isBo:false,name:"부여1",id:"3012A60",isAll:true,wSys:"geumRiver"},
				{isBo:false,name:"논산천4",id:"3013A50",isAll:true,wSys:"geumRiver"},
				{isBo:false,name:"곡교천2",id:"3101A60",isAll:true,wSys:"geumRiver"},
				{isBo:false,name:"김제",id:"3301A55",isAll:true,wSys:"geumRiver"},
				{isBo:false,name:"동진강3",id:"3302A40",isAll:true,wSys:"geumRiver"},
				{isBo:false,name:"적성",id:"4004A10",isAll:true,wSys:"yeongsanRiver"},
				{isBo:false,name:"주암댐",id:"4007A40",isAll:true,wSys:"yeongsanRiver"},
				{isBo:false,name:"보성천-1",id:"4008A20",isAll:true,wSys:"yeongsanRiver"},
				{isBo:false,name:"구례",id:"4009A30",isAll:true,wSys:"yeongsanRiver"},
				{isBo:false,name:"하동",id:"4009A50",isAll:true,wSys:"yeongsanRiver"},
				{isBo:false,name:"담양",id:"5001A10",isAll:true,wSys:"yeongsanRiver"},
				{isBo:false,name:"우치",id:"5001A20",isAll:true,wSys:"yeongsanRiver"},
				{isBo:false,name:"광주1",id:"5001A40",isAll:true,wSys:"yeongsanRiver"},
				{isBo:false,name:"황룡강3-1",id:"5002A40",isAll:true,wSys:"yeongsanRiver"},
				{isBo:false,name:"지석천4",id:"5003A60",isAll:true,wSys:"yeongsanRiver"},
				{isBo:true,name:"승촌보",id:"5004A10",isAll:true,wSys:"yeongsanRiver",fiveText:'1.0'},
				{isBo:false,name:"나주",id:"5004A20",isAll:true,wSys:"yeongsanRiver"},
				{isBo:true,name:"죽산보",id:"5004A35",isAll:true,wSys:"yeongsanRiver",fiveText:'0.8'},
				{isBo:false,name:"죽산보1",id:"5004A351",isAll:false,wSys:"yeongsanRiver"},//추가
				{isBo:false,name:"죽산보2-1",id:"5004A352",isAll:false,wSys:"yeongsanRiver"},//추가
				{isBo:false,name:"죽산보2",id:"5004A353",isAll:false,wSys:"yeongsanRiver"},//추가
				{isBo:false,name:"죽산보3",id:"5004A354",isAll:false,wSys:"yeongsanRiver"},//추가
				{isBo:false,name:"죽산보하류1",id:"5004A355",isAll:false,wSys:"yeongsanRiver"},//추가
				{isBo:false,name:"죽산보하류2",id:"5004A356",isAll:false,wSys:"yeongsanRiver"},//추가
				{isBo:false,name:"무안2",id:"5008A10",isAll:true,wSys:"yeongsanRiver"},
				{isBo:false,name:"탐진강3",id:"5101A55",isAll:true,wSys:"yeongsanRiver"}];
	var timerObj = null;
	var gridObj = null;
	var isAutoPlay = true;
	var gblSpotId = null;
	var gblFilter = "bo";
	var gblAutoList = null;
	var gblAutoCnt = 0;
	var gblWaterSys = "all";
	var configObj = null;	
	var pageConfig = [{id:"group1",title:"수질현황",gridGroup:[{text:"보 대표지점(보 상류500m)",queryTask:"ag"},
	                                                       	   {text:"일반측정지점",queryTask:"basic"},
	                                                       	   {text:"수심별 정밀조사",queryTask:"dep"},
	                                                       	   {text:"수생태 계",queryTask:"eco"}]
					 },
                     {id:"group2",title:"수질현황",gridGroup:[{text:"보 운영현황",queryTask:"bomst"},
			                                              	  {text:"퇴적물 측정망 운영",queryTask:"sdm"},
			                                              	  {text:"자동측정망",queryTask:"five"}]
					 },
					 {id:"group3",title:"촬영사진",gridGroup:[{text:"경관 드론 촬영",queryTask:"drone"},
	                                                          {text:"경관 항공 촬영",queryTask:"flight"}]
					 }];
	var pageCnt = 0;
	var gridPageCnt = 1;
	var gblGridPageMax = 0;
	var gblTitleBtn = pageConfig[0].id;
	var init = function(){
		
		$('.test01[usemap]').rwdImageMaps();
		
		pxToPer();
		
		var config = $.ajax({url : "/weis_board/js/map/config.json",dataType:"json",async: false});
		configObj = JSON.parse(config.responseText);
		
		gblAutoList = setFilterList('bo', 'all');
		//초기값 - 이포보
		$('#1007A60').find('div').show();
		showGrid(gblAutoList[gblAutoCnt].id,1,pageConfig[pageCnt]);
		autoPlay();
	};
	
	var pxToPer = function(){
		
		for(var i = 0; i<$('.point>li, .point2>li, .point_lv2>a').length; i++){
				$('.point>li, .point2>li, .point_lv2>a')[i].style.top = parseInt($('.point>li, .point2>li, .point_lv2>a')[i].style.top.split('px')[0])/1080*100 + "%";
				$('.point>li, .point2>li, .point_lv2>a')[i].style.left = parseInt($('.point>li, .point2>li, .point_lv2>a')[i].style.left.split('px')[0])/1000*100 + 1 + "%"; 			
		}
	};
	
	var getGridData = function(param){
		var ajaxObj = $.ajax({
								type:"GET",
								url:"/weis_board/egov/main/site/boardInfo",
								datatype:'application/json',
								data:param,
								async:false,
								cache: false
							});
		return ajaxObj;
	};
	
	var autoPlay = function(){		
		timerObj = setInterval(play, 1000 * 5);
	};
	
	var play = function(){
		if(gblAutoCnt < gblAutoList.length - 1){
			gblAutoCnt++;
		}else{
			gblAutoCnt = 0;
		}
		
		if(gblWaterSys=="all"){
			$(".point>li").find('div').hide();
			$(".point2>li").find('div').hide();
			$("#" + gblAutoList[gblAutoCnt].id).find('div').show();
			$("#" + gblAutoList[gblAutoCnt].id).siblings().find('div').hide();
		}else{
			$(".point_lv2").find('div').hide();
			$("#" + gblAutoList[gblAutoCnt].id + "_lv2").find('div').show();
			$("#" + gblAutoList[gblAutoCnt].id + "_lv2").siblings().find('div').hide();
		}
			
		showGrid(gblAutoList[gblAutoCnt].id, 1,pageConfig[pageCnt]);
	};
	
	var setFilterList = function(flag,wSys){
		
		var autoSpot = null;
		
		if(wSys=="all"){
			if(flag=="bo"){
				autoSpot = spot.filter(function (el){
			        return el.isBo == true && el.isAll == true;
			    });
			}else{
				autoSpot = spot.filter(function (el){
			        return el.isAll == true;
			    });
			}
		}else{
			if(flag=="bo"){
				autoSpot = spot.filter(function (el){
					return el.wSys == wSys && el.isBo == true;
				});  
			}else{
				autoSpot = spot.filter(function (el){
					return el.wSys == wSys;
				});
			}
		}
		
		return autoSpot;
	};
	
	var setListCnt = function(spotId){
		
		var cnt = gblAutoList.map(function (el){
	        return el.id
	    }).indexOf(spotId);
		
		return cnt;
	};
	
	var showGrid = function(id,gridPage,titlePage){
		var apiDeferred = [];
		var apiTasks = [];
		gridPageCnt = gridPage;
		gblGridPageMax = 0;
		gblTitleBtn = titlePage.id;
		gblSpotId = id;
		$('#gridTitle').text(titlePage.title);
		
		for(var i=0; i<titlePage.gridGroup.length; i++){
			
			var gridObj = getPreGridData(id,titlePage.gridGroup[i].queryTask);
			
			if(gridObj == null){
				apiTasks.push(titlePage.gridGroup[i]);
				apiDeferred.push(getGridData({ptNo:id,gubun:titlePage.gridGroup[i].queryTask}));
			}else{
				setGridDatas(gridObj, false, id, gridPage, titlePage.gridGroup[i]);
			}
		}
		if(apiDeferred.length > 0){
			$.when.apply($,apiDeferred).then(function(){
				if(arguments[1]!="success"){
					if(arguments.length != apiTasks.length){
						return;
					}
					for(var i=0; i<arguments.length; i++){
						setGridDatas(arguments[i][0], true, id, 1, apiTasks[i]);
					}
				}else{
					setGridDatas(arguments[0], true, id, 1, apiTasks[0]);
				}
				
				setGridPage(gridPage);
			});
		}else{
			setGridPage(gridPage);
		}
	};
	
	var setGridDatas = function(gridData, newFlag, id, gridPage, gridGroup){
		var pageFilterObj = gridData.filter(function (el){
			return el.PAGE == gridPage;
		});
		
		if(gridData.length>gblGridPageMax){
			gblGridPageMax = gridData.length;
		}
		
		var target = spot.filter(function (el){
			return el.id == id;
		});
		
		if(newFlag){
			target[0][gridGroup.queryTask] = gridData;
			target[0].gridDataTime = new Date();	
		}
		
		var html = "";
		
		$("#spotTitle").text(target[0].name);
		if(target[0].fiveText!=undefined&&gridGroup.queryTask=='five'){
			html += '<h3 class="MgT0" style="margin-top: 10px !important;">' + gridGroup.text + ' (보 상류 ' +target[0].fiveText +'Km)</h3>';
		}else{
			html += '<h3 class="MgT0" style="margin-top: 10px !important;">' + gridGroup.text + '</h3>';
		}
		
		if(pageFilterObj.length>0){
			$('#' + gridGroup.queryTask + "Grid").show();
			
			if(gridGroup.queryTask=='flight'||gridGroup.queryTask=='drone'){
				$('#noImage').hide();
				html += '<img src="/weis_board/egov/main/site/getImage?paths='+pageFilterObj[0].PATHS+'&fileName='+pageFilterObj[0].FILENAME+'" width="530px" height="350px"/>';
				html += '<div style="position: relative; top: -40px;">' + pageFilterObj[0].POTOGRF_DE + '</div>';
			}else if(gridGroup.queryTask=='bomst'||gridGroup.queryTask=='sdm'||gridGroup.queryTask=='five'){
				gblGridPageMax = 5;
				var bottomVal = pageFilterObj.length / 2;
				
				var timeText = "관측일시";
				var th1 = "";
				var td1 = "";
				var th2 = "";
				var td2 = "";
				
				var rowSpanText = "";
				gridGroup.queryTask!='sdm'?rowSpanText='<br/>' + pageFilterObj[0].TIMES:rowSpanText;
				
				html += '<table class="tst07">';
				for(var i = 0; i<pageFilterObj.length; i++){
					if(i < bottomVal){
						th1 += '<th>' + configObj[pageFilterObj[i].ITEM_NAME].text;
						if(configObj[pageFilterObj[i].ITEM_NAME].unit!='not'){
							th1 += '<br/>(' + configObj[pageFilterObj[i].ITEM_NAME].unit + ')';
						}
						th1 += '</th>';
						td1 += '<td>' + toFloat(pageFilterObj[i].ITEM_VALUE,configObj[pageFilterObj[i].ITEM_NAME].digit) + '</td>';	
					}else{
						th2 += '<th>' + configObj[pageFilterObj[i].ITEM_NAME].text;
						if(configObj[pageFilterObj[i].ITEM_NAME].unit!='not'){
							th2 += '<br/>(' + configObj[pageFilterObj[i].ITEM_NAME].unit + ')';
						}
						td2 += '<td>' + toFloat(pageFilterObj[i].ITEM_VALUE,configObj[pageFilterObj[i].ITEM_NAME].digit) + '</td>';
					}
				}
				html += '<tr><th>' + timeText + '</th>' + th1 + '</tr>';
				html += '<tr><td rowspan="3" class="st3">' + pageFilterObj[0].DATES + rowSpanText + '</td>' + td1 + '</tr>';
				html += '<tr>' + th2 + '</tr>';
				html += '<tr>' + td2 + '</tr>'; 
				html += '</table>';
				
			}else if(gridGroup.queryTask=="ag"||gridGroup.queryTask=="basic"){
				gblGridPageMax = 5;
				html += '<table class="tst06">';
				html += '<thead><tr><th>기준일</th><th>측정항목</th><th>측정값</th></tr></thead>';
				html += '<tbody>';
				for(var i = 0; i<pageFilterObj.length; i++){
					if(i%2==0){
						html+='<tr>';
					}else{
						html+='<tr class="st2">';
					}
					if(i==0){
						html += '<th rowspan="7">' + pageFilterObj[i].DATES + '</th>';
					}
					html += '<td>' + configObj[pageFilterObj[i].ITEM_NAME].text + '('+ configObj[pageFilterObj[i].ITEM_NAME].unit +')</td>';
					html += '<td>' + toFloat(pageFilterObj[i].ITEM_VALUE,configObj[pageFilterObj[i].ITEM_NAME].digit) + '</td>';
					
					html+= '</tr>';
				}
				html += '</tbody>';
				html += '</table>';
			}else if(gridGroup.queryTask=="dep"){
				gblGridPageMax = 5;
				html += '<table class="tst06">';
				html += '<thead><tr><th>기준일</th><th>수심<br/>(m)</th><th>수온(℃)</th><th>pH</th><th>DO<br/>(mg/L)</th><th>전기전도도<br/>(μS/㎝)</th><th>클로로필-a<br/>(mg/m3)</th></tr></thead>';
				html += '<tbody>';
				for(var i = 0; i < pageFilterObj.length; i++){
					if(i%2==0){
						html+='<tr>';
					}else{
						html+='<tr class="st2">';
					}
					
					if(i==0){
						html += '<th rowspan="' + pageFilterObj.length + '">' + pageFilterObj[i].DATES + '</th>';
					}
					
					html += '<td>' + toFloat(pageFilterObj[i].WMDEP,1) + '</td>';
					html += '<td>' + toFloat(pageFilterObj[i].ITEM_TEMP,1) + '</td>';
					html += '<td>' + toFloat(pageFilterObj[i].ITEM_PH,1) + '</td>';
					html += '<td>' + toFloat(pageFilterObj[i].ITEM_DOC,1) + '</td>';
					html += '<td>' + toFloat(pageFilterObj[i].ITEM_EC,0) + '</td>';
					html += '<td>' + toFloat(pageFilterObj[i].ITEM_CLOA,1) + '</td>';
					
					html+='</tr>';
				}
				html += '</tbody>';
				html += '</table>';
			}else if(gridGroup.queryTask=='eco'){
				html += '<table class="tst06">';
                html +='<thead>                   ';
                html +='<tr>                      ';
                html +='<th>기준일</th>           ';
                html +='<th>구분</th>             ';
                html +='<th colspan="5">측정값</th>';
                html +='</tr>                     ';
                html +='</thead>                  ';
				html+=" <tr>                                                                                              ";
				html+="    <th rowspan='2'>" + pageFilterObj[0].WMYMD + "</th>                         ";
				html+="    <td rowspan='2' style='border-bottom: 1px solid #2a2b34;'>" + pageFilterObj[0].ITEM_NM + "</td>   ";
				html+="    <td>종수</td>                                                                             ";
				html+="    <td>개체수</td>                                                                          ";
				html+="    <td>우점종(비율)</td>                                                                          ";
				html+="    <td>아우점종(비율)</td>                                                                             ";
				html+="    <td>비고</td>                                                                             ";
				html+="</tr>                                                                                              ";
				html+="<tr class='st2' style='border-bottom: 1px solid #2a2b34;'>                        ";
				html+="    <td>" + pageFilterObj[0].ITEM_VALUE_1 + "</td>                                                                               ";
				html+="    <td>" + pageFilterObj[0].ITEM_VALUE_2 + "</td>                                                                                 ";
				html+="    <td>" + pageFilterObj[0].ITEM_VALUE_3 + "</td>                                                                             ";
				html+="    <td>" + pageFilterObj[0].ITEM_VALUE_4 + "</td>                                                                 ";
				html+="    <td>" + pageFilterObj[0].ITEM_VALUE_5 + "</td>                                                                 ";
				html+="</tr>                                                                                              ";
				html+="<tr>                                                                                               ";
				html+="    <th rowspan='2'>" + pageFilterObj[1].WMYMD + "</th>                         ";
				html+="    <td rowspan='2'>" + pageFilterObj[1].ITEM_NM + "</td>                                                      ";
				html+="    <td>종수</td>                                                                             ";
				html+="    <td>개체밀도(개체/m<sup>2</sup>)</td>                                        ";
				html+="    <td>우점종(비율)</td>                                                                          ";
				html+="    <td>아우점종(비율)</td>                                                                          ";
				html+="    <td>비고</td>                                                                             ";
				html+="</tr>                                                                                              ";
				html+="<tr class='st2'>                                                                                 ";
				html+="    <td>" + pageFilterObj[1].ITEM_VALUE_1 + "</td>                                                                               ";
				html+="    <td>" + pageFilterObj[1].ITEM_VALUE_2 + "</td>                                                                                 ";
				html+="    <td>" + pageFilterObj[1].ITEM_VALUE_3 + "</td>                                                                             ";
				html+="    <td>" + pageFilterObj[1].ITEM_VALUE_4 + "</td>                                                                         ";
				html+="    <td>" + pageFilterObj[1].ITEM_VALUE_5 + "</td>                                                                         ";
				html+="</tr>";
				html += '</table>';
			}
			
			$('#' +  gridGroup.queryTask + "Grid").html(html);
		}else{
			$('#' +  gridGroup.queryTask + "Grid").html(html);
			$('#' +  gridGroup.queryTask + "Grid").hide();
			
			if(gridGroup.queryTask=="drone"||gridGroup.queryTask=="flight"){
				if($('#droneGrid').css('display')=="none" && $('#flightGrid').css('display')=="none"){
					$('#noImage').show();
				}
			}
		}
		
	};
	
	var mouseOverEvent = function(spotId){
		stopPlay();
		showGrid(spotId, 1, pageConfig[pageCnt]);
		gblSpotId = spotId;
	};
	
	var stopPlay = function(){
		
		clearInterval(timerObj);
	};
	
	var mouseOutEvent = function(){
		
		var cnt = setListCnt(gblSpotId);
		if(cnt!=-1){
			gblAutoCnt = cnt;
		}
		
		if(isAutoPlay){
			autoPlay();
		}
	};
	
	var onClickFilter = function(flag){
		
		gblAutoList = setFilterList(flag,gblWaterSys);
		gblAutoCnt = 0;
		gblFilter = flag;
		
		if(gblWaterSys=="all"){
			$(".point>li").find('div').hide();
			$(".point2>li").find('div').hide();
			$("#" + gblAutoList[gblAutoCnt].id).find('div').show();
			$("#" + gblAutoList[gblAutoCnt].id).siblings().find('div').hide();
		}else{
			$(".point_lv2").find('div').hide();
			$("#" + gblAutoList[gblAutoCnt].id + "_lv2").find('div').show();
		    $("#" + gblAutoList[gblAutoCnt].id + "_lv2").siblings().find('div').hide();
		}
		
		showGrid(gblAutoList[gblAutoCnt].id, 1, pageConfig[pageCnt]);
	};
	
	var onClickPlay = function(obj){
		
		if(isAutoPlay){
			isAutoPlay = false;
			$(obj).removeClass("btn_stop");
			$(obj).addClass("btn_play");
			stopPlay();
		}else{
			isAutoPlay = true;
			$(obj).removeClass("btn_play");
			$(obj).addClass("btn_stop");
			autoPlay();
		}
	};
	
	var onClickMap = function(flag){
		
		gblAutoCnt = 0;
		gblWaterSys = flag;
		gblAutoList = setFilterList(gblFilter,flag);
		
		$("#legendImg").attr("src","/weis_board/images/legend02.png");
		$("#legend").css("bottom","20px");
		
		$(".point_lv2").find('div').hide();
		$("#" + gblAutoList[0].id + "_lv2").find('div').show();
		$("#" + gblAutoList[0].id + "_lv2").siblings().find('div').hide();
		
		showGrid(gblAutoList[gblAutoCnt].id, 1, pageConfig[pageCnt]);
	};
	
	var onClickAllMapView = function(){
		
		gblWaterSys = "all";
		gblAutoCnt = 0;
		gblAutoList = setFilterList(gblFilter,"all");
		
		$(".point>li").find('div').hide();
		$(".point2>li").find('div').hide();
		$("#" + gblAutoList[0].id).find('div').show();
		$("#" + gblAutoList[0].id).siblings().find('div').hide();
		
		showGrid(gblAutoList[gblAutoCnt].id, 1, pageConfig[pageCnt]);
	};
	
	var getPreGridData = function(id,gridId){
		var obj = spot.filter(function (el){
			return el.id == id;
		});
		
		if(obj[0][gridId] != null){
			if(checkDateTime(obj[0].gridDataTime)){
				return obj[0][gridId];
			}else{
				return null;
			}
		}else{
			return;
		}
	};
	
	var checkDateTime = function(srcTime){
		var date = new Date();
		var calcHours = ((date.getHours() * 60) + date.getMinutes()) - ((srcTime.getHours() * 60) + srcTime.getMinutes());
		if(calcHours >= 60){
			return false;
		}else{
			return true;
		}
	};
	
	var onClickTitleBtn = function(flag){
		if(flag=="titleNextBtn"){
			if(pageCnt < pageConfig.length - 1){
				pageCnt ++;
			}else{
				pageCnt = 0;
			}
		}else{
			if(pageCnt <= 0){
				pageCnt = pageConfig.length - 1;
			}else{
				pageCnt --;
			}
		}
		
		$('.gridGroup').hide();
		showGrid(gblAutoList[gblAutoCnt].id,1,pageConfig[pageCnt]);
	};
	
	var onClickGridPreNextBtn = function(flag){
		
		flag=="preBtn"?gridPageCnt++:gridPageCnt--;
		
		showGrid(gblSpotId,gridPageCnt,pageConfig[pageCnt]);
	};
	
	
	var setGridPage = function(pageNum){
		if(gblGridPageMax>pageNum){
			$('#preBtn').show();
		}else{
			$('#preBtn').hide();
		}
		
		if(pageNum>1){
			$('#nextBtn').show();
		}else{
			$('#nextBtn').hide();
		}
	};
	
	var toFloat = function(val,digit){
		if(isNaN(parseFloat(val))){
			return val;
		}else{
			return parseFloat(val).toFixed(digit);
		}
	};
  return {
    init: function(){
    	init();
    },
    mouseOverEvent: function(spotId){
    	mouseOverEvent(spotId);
    },
    mouseOutEvent: function(){
    	mouseOutEvent();
    },
    onClickFilter: function(flag){
    	onClickFilter(flag);
    },
    onClickPlay: function(obj){
    	onClickPlay(obj);
    },
    onClickMap: function(flag){
    	onClickMap(flag);
    },
    onClickAllMapView: function(){
    	onClickAllMapView();
    },
    onClickTitleBtn: function(flag){
    	onClickTitleBtn(flag);
    },
    onClickGridPreNextBtn: function(flag){
    	onClickGridPreNextBtn(flag);
    },
    getGblFilter: function(){
    	return gblFilter;
    },
    getTilteBtn: function(){
    	return gblTitleBtn;
    }
  };   
})();