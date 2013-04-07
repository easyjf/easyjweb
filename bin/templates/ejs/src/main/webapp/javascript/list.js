//全部选中函数
function selectAll(){
	var f=document.getElementById("ListForm");
	var d=f.getElementsByTagName("input");
	for(var i=0;i<d.length;i++){
		if(d[i].type=="checkbox"&&d[i].name=="ids"){
			d[i].checked=true;
		}
	}
}
//反向选中函数
function selectInvertion(){
	var f=document.getElementById("ListForm");
	var d=f.getElementsByTagName("input");
	for(var i=0;i<d.length;i++){
		if(d[i].type=="checkbox"&&d[i].name=="ids"){
			d[i].checked==false?d[i].checked=true:d[i].checked=false;
		}
	}
}
//全部不选中函数
function selectNone(){
	var f=document.getElementById("ListForm");
	var d=f.getElementsByTagName("input");
	for(var i=0;i<d.length;i++){
		if(d[i].type=="checkbox"&&d[i].name=="ids"){
			d[i].checked=false;
		}
	}
}
//选中当前项函数
function selectOne(id){
	var f=document.getElementById("ListForm");
	var d=f.getElementsByTagName("input");
	for(var i=0;i<d.length;i++){
		if(d[i].type=="checkbox"&&d[i].name=="ids"){
			if(d[i].value==id){
				d[i].checked==false?d[i].checked=true:d[i].checked=false;
			}
		}
	}
}
//删除选中项函数
function deleteSelected(cmdname){
	var msg=arguments[1];
	var msg1=arguments[2];
	var cmdname2=arguments[3];
	if(cmdname==undefined){
		cmdname="batchDelete";
	}
	if(msg==undefined){
		msg="Are you sure to delete the items you selected?";
	}
	if(msg1==undefined){
		msg1="Nothing you selected! Please select one first!";
	}
	if(cmdname2){
		cmdname=cmdname2;
	}
	var f=document.getElementById("ListForm");
	var d=f.getElementsByTagName("input");
	f.mulitId.value="";
	for(var i=0;i<d.length;i++){
		if(d[i].type=="checkbox"&&d[i].name=="ids"){
			if(d[i].checked==true){
				f.mulitId.value+=d[i].value+",";
			}
		}
	}
	if(f.mulitId.value!=""){
	var confirm=window.confirm(msg);
		if(confirm){
			f.action="?cmd="+cmdname;
			f.submit();
		}
	}else{
		COMMON.$('application.descriptions').innerHTML=msg1;
	}
}

//回收站还原选中项函数
function recoverSelected(cmdname){
	var msg=arguments[1];
	var msg1=arguments[2];
	if(msg==undefined){
		msg="Are you sure to recover the items you selected?";
	}
	if(msg1==undefined){
		msg1="Nothing you selected! Please select one first!";
	}
	var f=document.getElementById("ListForm");
	var d=f.getElementsByTagName("input");
	f.mulitId.value="";
	for(var i=0;i<d.length;i++){
		if(d[i].type=="checkbox"&&d[i].name=="ids"){
			if(d[i].checked==true){
				f.mulitId.value+=d[i].value+",";
			}
		}
	}
	if(f.mulitId.value!=""){
	var confirm=window.confirm(msg);
		if(confirm){
			f.action="?cmd="+cmdname;
			f.submit();
		}
	}else{
		COMMON.$('application.descriptions').innerHTML=msg1;
	}
}
//页面排序函数
function doSort(byName){
	var f=document.getElementById("ListForm");
	(f.orderType.value==""||f.orderType.value=="asc")?f.orderType.value="desc":f.orderType.value="asc";
	f.orderBy.value=byName;
	f.submit();
}
//导向至页面函数
function gotoPage(n){
	var f=document.getElementById("ListForm");
	f.currentPage.value=n;
	f.submit();
}
function doUp(sq)
{
var $=document.getElementById;
$("ListForm").action="?cmd=swapSequence&sq="+sq;
$("ListForm").submit();
}
function doDown(sq)
{
var $=document.getElementById;

$("ListForm").action="?cmd=swapSequence&down=true&sq="+sq;
$("ListForm").submit();
}
function doOneCommand(command)
{
var $=document.getElementById;
if($("ListForm").easyJWebCommand)$("ListForm").easyJWebCommand.value=command;
if($("ListForm").cmd)$("ListForm").cmd.value=command;
$("ListForm").submit();
}