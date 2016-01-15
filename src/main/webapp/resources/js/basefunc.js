
/**
//================================功能函数列表整理======================//
// 整理日期：2006-03-10
// 整理人员：heguoqiang
序号		函数名称    			函数功能			返回值 
1		checkPrice			检查价格			boolean
2		checkPrice1			检查价格			1/0
3		checkTelephone			检查电话号码		1/0
4		checkNum			检查数字			boolean
5		time_select			年、月、日日期的下拉选择联动
6		checkRadio			检查是否选中check框	num>0已经选中
7		checkString			检测字串是否为数字或字母	1/0
8		checkNumLength			检测字串长度为是否strLength	boolean
9		checkNumber			检查数字			1/0
10		checkEmail			检测电子邮件是否正确	boolean
11		CheckBoxCheckedNum		取得checkbox选中的个数
12		CheckBoxCheckedSingleOne	只能选择一个checkbox
13		CheckBoxMustChecked		必须选择一个checkbox
14		getLength			判断一个字符的真实长度，汉字为两位
15		checkChina
16		checkPhone			判断电话号码是否合法	boolean
17		checkDouble			检查数字，保证小数点后最多两位数字
18		checkFraction			检测是否为小数或分数
19		checkChar			检测是否含有某个字符
20		Fraction			把分数转换为小数计算
21		getFloat			转换浮点数，默认为0，在JS做页面计算的时候会需要
22		round				四舍五入函数
23		formatDate			校核输入的字符是否符合日期规范 yyyy-mm-dd
24		formatDateAlert			校核输入的字符是否符合日期规范 yyyy-mm-dd（不合格有提示信息）
25		formatNum			检查数值是否符合规定的格式
26		formatNumAlert			检查数值是否符合规定的格式（不合格有提示信息）
27		getCookie			根据COOKIE名称，得到COOKIE的值
28		space				调整字符串输出长度，不够长的以空格填写，用于对<select>控件中格式处理之用
29		len				计算字符串的长度，汉字以2位表示
30		cut				字符串按照规定的长度截断，不足长度则不截断
31		justify				字符串以LN长度显示，不足加空格，多余的截断,最后以...结尾，
32		checkCharOrNum			校核字符是否为字母或者数字
33		checkCharOrNumAlert		校核字符是否为字母或者数字（不合格有提示信息）
34		checkChinese			校核字符是否含有双字节的字符
35		checkChineseAlert		校核字符是否含有双字节的字符（不合格有提示信息）
36		checkNullAndLen			校核字符是否为空或者长度是否满足要求
37		checkNullAndLenAlert		校核字符是否为空或者长度是否满足要求（不合格有提示信息）
38		trim				去掉字符串两边空格的函数
39		ltrim				去掉字符串左边空格的函数
40		rtrim				去掉字符串右边空格的函数
41		openDlgWindow			弹出对话窗口模式
42		openWindow			弹出普通窗口模式
43		getCheckedNum			取得checkbox 和 RADIO 选中的个数
44		getCheckedNumAlert		取得checkbox 和 RADIO 选中的个数（不合格有提示信息）
45		getSelectedOption		把SELECT中选中的option值返回
46		checkFloat  			检查是否为数字或小数	  true/false
47      checkNull               检查是否为空             true/false
48		validateNoEmpty         校验页面内的必填字段是否已填    true/false
49		getDateByFormat         根据str值返回对应的日期对象,用于比较开始日期和结束日期大小 
50      validateDateRange       验证起始日期与结束日期的大小关系    true/false
51      attachDateFormat        为日期类型字段添加日期格式转换与验证
* 		checkPassword           检查密码的强度
//===================================功能函数列表整理======================//
*/

/**
 * 页面基本css样式定义
 */
var styleSetComplete = false;	//页面样式统一设定完成标志
var selectedColor="#ffffcc";	//选中行背景色
var mouseOverColor="#cccccc";	//鼠标悬停行背景色




/**-----------------------------------页面样式设定函数整理-------------------
 * 
100     setTableStyle           通用表单样式设定
101     setTitleTableStyle      页面title表样式   
102     setConditionTableStyle  条件查询表单样式
103     setListTableStyle       查询结果列表样式
104     setDetailTableStyle     明细页面表单样式 
-------------------------------------------------------------------------*/



//功能：检查价格
//输入:价格字符串;价格必须为：xxx.xx格式
//返回：true / false
function checkPrice(numstr) {
	return formatNum(numstr, 2, 0, null, true);

//    var TempChar;
//    var tmp=0
//    for(i=0;i<=numstr.length-1;i++){
//        TempChar=numstr.charAt(i);
//        if(TempChar!=0 && TempChar!=1 && TempChar!=2 && TempChar!=3 && TempChar!=4 && TempChar!=5 && TempChar!=6 && TempChar!=7 && TempChar!=8 && TempChar!=9 && TempChar!="."){
//            i=-1;
//            break;
//        }else if(TempChar=="."){
//            tmp=1;
//            if((numstr.length-1-i) != 2){
//                i=-1;
//                break;
//            }
//        }
//    }
//    if((i==-1) || (tmp==0))
//        return false;
//    else
//        return true;
}
/**
 价格中可以含有数字和.
 */
function checkPrice1(numstr) {
	if (formatNum(numstr, 2, 0)) {
		return 1;
	} else {
		return 0;
	}

//    var TempChar;
//    for(i=0;i<=numstr.length-1;i++){
//        TempChar=numstr.charAt(i);
//        if(TempChar!=0 && TempChar!=1 && TempChar!=2 && TempChar!=3 && TempChar!=4 && TempChar!=5 && TempChar!=6 && TempChar!=7 && TempChar!=8 && TempChar!=9 && TempChar!='.'){
//            i=-1;
//            break;
//        }
//    }
//    if(i==-1)	return 0;
//    else	return 1;
}
/**
 电话中可以含有数字和-
 */
function checkTelephone(numstr) {
	var TempChar;
	for (i = 0; i <= numstr.length - 1; i++) {
		TempChar = numstr.charAt(i);
		if (TempChar != 0 && TempChar != 1 && TempChar != 2 && TempChar != 3 && TempChar != 4 && TempChar != 5 && TempChar != 6 && TempChar != 7 && TempChar != 8 && TempChar != 9 && TempChar != "-") {
			i = -1;
			break;
		}
	}
	if (i == -1) {
		return 0;
	} else {
		return 1;
	}
}
//功能：检查数字
//输入：数字字符串
//返回：true / false
function checkNum(numstr) {
	return formatNum(numstr, 0, 0);
//    var TempChar;
//    for(i=0;i<=numstr.length-1;i++){
//        TempChar=numstr.charAt(i);
//        if(TempChar!=0 && TempChar!=1 && TempChar!=2 && TempChar!=3 && TempChar!=4 && TempChar!=5 && TempChar!=6 && TempChar!=7 && TempChar!=8 && TempChar!=9){
//            i=-1;
//            break;
//        }
//    }
//    if(i==-1)
//        return false;
//    else
//        return true;
}
//功能：年、月、日日期的下拉选择联动函数
//输入：day_se-当前选择的日期;time_n-时间字符串;year_n,month_n,day_n－年月日的form框名字
//返回：
function time_select(year_n, month_n, day_n, time_n, day_se) {
	var i;
	var len = 31;
	var year = year_n.value;
	i = month_n.selectedIndex + 1;
    //alert("i="+i);
	if (i == 4 || i == 6 || i == 9 || i == 11) {
		len = 30;
	}
	if (i == 2) {
		len = 28;
		if ((year % 4 == 0 && year % 100 == 0 && year % 400 == 0) || (year % 4 == 0 && year % 100 != 0)) {
			len = 29;
		}
	}
    //alert(day_se);
	day_n.length = len;
	for (j = 0; j < len; j++) {
		var va;
		if (j < 9) {
			va = "0" + eval(j + 1);
		} else {
			va = eval(j + 1);
		}
		day_n.options[j].text = j + 1;
		day_n.options[j].value = va;
	}
	if (day_se > 0) {
		day_n.selectedIndex = day_se - 1;
	}
    //---
	var month = month_n.value;
	var day = day_n.value;
	time_n.value = eval(year + month + day) + "000000";
}
//功能：检查是否选中check框
//
//返回：num>0已经选中
function checkRadio(radioObj) {
	var num = -1;
	if (radioObj != null) {
        //alert(radioObj);
		for (var i = 0; i < radioObj.length; i++) {
			if ((true == radioObj[i].checked)) {
				num = i;
			}
		}
		if (true == radioObj.checked) {
			num = 1000;
		}
        //alert(num);
	}
	return num;
}

//检测字串是否为数字或字母
function checkString(Charstr) {
	var Tempstr;
	for (j = 0; j <= Charstr.length - 1; j++) {
		Tempstr = Charstr.charAt(j);
		if (!((Tempstr >= "0" && Tempstr <= "9") || (Tempstr >= "a" && Tempstr <= "z") || (Tempstr >= "A" && Tempstr <= "Z"))) {
			j = -1;
			break;
		}
	}
	if (j == -1) {
		return 0;
	} else {
		return 1;
	}
}

//检测字串长度为是否strLength
function checkNumLength(numStr, strLength) {
	if (numStr.length == 0) {
		return true;
	} else {
		if (numStr.length == strLength) {
			return true;
		} else {
			return false;
		}
	}
}

//检测是否为数字
function checkNumber(numstr) {
	if (formatNum(numstr, 0, 0)) {
		return 1;
	} else {
		return 0;
	}
//    var TempChar;
//    for(i=0;i<=numstr.length-1;i++){
//        TempChar=numstr.charAt(i);
//        if(TempChar!=0 && TempChar!=1 && TempChar!=2 && TempChar!=3 && TempChar!=4 && TempChar!=5 && TempChar!=6 && TempChar!=7 && TempChar!=8 && TempChar!=9){
//            i=-1;
//            break;
//        }
//    }
//    if(i==-1)	return 0;
//    else	return 1;
}

//检测电子邮件是否正确
function checkEmail(email) {
	invalid = "";
	if (!email) {
		invalid = "";
	} else {
		if ((email.indexOf("@") == -1) || (email.indexOf(".") == -1)) {
			invalid += "\n\nEmail\u5730\u5740\u4e0d\u5408\u6cd5\u3002\u5e94\u5f53\u5305\u542b'@'\u548c'.'\uff1b\u4f8b\u5982('.com')\u3002\u8bf7\u68c0\u67e5\u540e\u518d\u9012\u4ea4\u3002";
		}
		if (email.indexOf("your email here") > -1) {
			invalid += "\n\nEmail\u5730\u5740\u4e0d\u5408\u6cd5\uff0c\u8bf7\u68c0\u6d4b\u60a8\u7684Email\u5730\u5740\uff0c\u5728\u57df\u540d\u5185\u5e94\u5f53\u5305\u542b'@'\u548c'.'\uff1b\u4f8b\u5982('.com')\u3002";
		}
		if (email.indexOf("\\") > -1) {
			invalid += "\n\nEmail\u5730\u5740\u4e0d\u5408\u6cd5\uff0c\u542b\u6709\u975e\u6cd5\u5b57\u7b26(\\)\u3002";
		}
		if (email.indexOf("/") > -1) {
			invalid += "\n\nEmail\u5730\u5740\u4e0d\u5408\u6cd5\uff0c\u542b\u6709\u975e\u6cd5\u5b57\u7b26(/)\u3002";
		}
		if (email.indexOf("'") > -1) {
			invalid += "\n\nEmail\u5730\u5740\u4e0d\u5408\u6cd5\uff0c\u542b\u6709\u975e\u6cd5\u5b57\u7b26(')\u3002";
		}
		if (email.indexOf("!") > -1) {
			invalid += "\n\nEmail\u5730\u5740\u4e0d\u5408\u6cd5\uff0c\u542b\u6709\u975e\u6cd5\u5b57\u7b26(!)\u3002";
		}
		if ((email.indexOf(",") > -1) || (email.indexOf(";") > -1)) {
			invalid += "\n\n\u53ea\u8f93\u5165\u4e00\u4e2aEmail\u5730\u5740\uff0c\u4e0d\u8981\u542b\u6709\u5206\u53f7\u548c\u9017\u53f7\u3002";
		}
		if (email.indexOf("?subject") > -1) {
			invalid += "\n\n\u4e0d\u8981\u52a0\u5165'?subject=...'\u3002";
		}
		if (checkChina(email) == -1) {
			invalid += "\n\n\u4e0d\u8981\u52a0\u5165\u4e2d\u6587\u3002";
		}
		if (email.indexOf("@.") > -1 || email.indexOf(".@") > -1) {
			invalid += "\n\n\u4e0d\u8981\u52a0\u5165 @. \u8fde\u63a5\u7684\u975e\u6cd5Email\u3002";
		}
	}
	if (invalid == "") {
		return true;
	} else {
        //alert("输入的Email可能包含错误：" + invalid);
		return false;
	}
}
/**
 取得checkbox选中的个数
 */
function CheckBoxCheckedNum(form) {
	var num = 0;
	for (var i = 0; i < form.elements.length; i++) {
		if ((true == form.elements[i].checked) && (form.elements[i].type == "checkbox")) {
		   if(form.elements[i].onclick == null ||  form.elements[i].onclick.toString().indexOf('selectORClearBox')==-1)
		   {
		   	num++;
		   }	
		}
	}
    //alert(num);
	return num;
}
/**
 取得radio选中的个数
 */
function RadioCheckedNum(form) {
	var num = 0;
	for (var i = 0; i < form.elements.length; i++) {
		if ((true == form.elements[i].checked) && (form.elements[i].type == "radio")) {
			num++;
		}
	}
    //alert(num);
	return num;
}
function RadioCheckedSingleOne(form) {
	var num = 0;
	var errMsg = "";
	num = RadioCheckedNum(form);
	if (num < 1) {
		errMsg = "\u8bf7\u9009\u62e9\uff01";
	} else {
		if (num > 1) {
			errMsg = "\u53ea\u80fd\u9009\u62e9\u4e00\u4e2a\uff01";
		}
	}
	return errMsg;
}
function CheckBoxCheckedSingleOne(form) {
	var num = 0;
	var errMsg = "";
	num = CheckBoxCheckedNum(form);
	if (num < 1) {
		errMsg = "\u8bf7\u9009\u62e9\uff01";
	} else {
		if (num > 1) {
			errMsg = "\u53ea\u80fd\u9009\u62e9\u4e00\u4e2a\uff01";
		}
	}
	return errMsg;
}
function CheckBoxMustChecked(form) {
	var num = 0;
	var errMsg = "";
	num = CheckBoxCheckedNum(form);
	if (num < 1) {
		errMsg = "\u8bf7\u9009\u62e9\uff01";
	}
	return errMsg;
}
function RadioMustChecked(form) {
	var num = 0;
	var errMsg = "";
	num = RadioCheckedNum(form);
	if (num < 1) {
		errMsg = "\u8bf7\u9009\u62e9\uff01";
	}
	return errMsg;
}

//检查form框的真实长度
function getLength(formValue) {
	var length = 0;
	for (i = 0; i < formValue.length; i++) {
		if (formValue.charAt(i) > "~") {
			length += 2;
		} else {
			length++;
		}
	}
	return length;
}

//检查form框的真实长度
function checkChina(formValue) {
	var length = 0;
	for (i = 0; i < formValue.length; i++) {
		if (formValue.charAt(i) > "~") {
			length = -1;
		}
	}
	return length;
}
function checkPhone(obj) {
	if (obj == "") {
		return true;
	}
	slen = obj.length;
	for (i = 0; i < slen; i++) {
		cc = obj.charAt(i);
		if ((cc < "0" || cc > "9") && cc != "-" && cc != "+" && cc != "(" && cc != ")" && cc != "/") {
			return false;
		}
	}
	return true;
}
/**
 检查数字，保证小数点后最多两位数字
 number-要检测的数字
 jd-小数的精度，就是小数点后的位数
 */
function checkDouble(numstr, jd) {
	if (formatNum(numstr, 2)) {
		return 1;
	} else {
		return 0;
	}

//    var TempChar;
//    var iPoint = -1;
//    var iPointNumber = 0;
//    for(i=0;i<=numstr.length-1;i++){
//        if(iPoint>-1){
//            iPoint ++;
//        }
//        TempChar=numstr.charAt(i);
//        if(i==0 && TempChar=='-'){
//            //负数
//            continue;
//        }
//        if(TempChar!=0 && TempChar!=1 && TempChar!=2 && TempChar!=3 && TempChar!=4 && TempChar!=5 && TempChar!=6 && TempChar!=7 && TempChar!=8 && TempChar!=9 && TempChar!='.'){
//            i=-1;
//            break;
//        }else if(TempChar=='.'){
//            iPointNumber ++;
//            iPoint = 0;
//        }
//        if(iPoint>jd || iPointNumber>1){
//            i=-1;
//            break;
//        }
//    }
//    if(i==-1)
//        return 0;
//    else
//        return 1;
}
//检测是否为小数或分数
function checkFraction(numstr) {
	var TempChar;
	var hefa = 0;
	var flag = -1;
	for (i = 0; i <= numstr.length - 1; i++) {
		TempChar = numstr.charAt(i);
		if (((TempChar != "0" && TempChar != "1" && TempChar != "2" && TempChar != "3" && TempChar != "4" && TempChar != "5" && TempChar != "6" && TempChar != "7" && TempChar != "8" && TempChar != "9" && TempChar != "." && TempChar != "/")) || (TempChar == " ")) {
			flag = TempChar;
			break;
		}
		if (TempChar == "." || TempChar == "/") {
			hefa++;
		}
	}
	var Errmsg = "";
	if (flag != -1) {
		Errmsg = "\u8f93\u5165\u7684\u6570\u5b57\u542b\u6709\u975e\u6cd5\u5b57\u7b26:" + flag + "\u3002\n";
	} else {
		if (hefa > 1) {
			Errmsg += "\u8f93\u5165\u7684\u6570\u5b57\u542b\u6709\u591a\u4e2a/\u6216.,\u8bf7\u8f93\u5165\u5408\u6cd5\u7684\u5c0f\u6570\u6216\u5206\u6570\u3002\n ";
		}
	}
    //如果为分数，检查合法性
	if ((Errmsg == "") && (checkChar(numstr, "/"))) {
       //1.检查第一位不能为0
		if (numstr.charAt(0) == 0) {
			Errmsg = " \u5206\u5b50\u7b2c\u4e00\u4f4d\u4e0d\u80fd\u4e3a\u96f6\u3002";
		} else {
			if (numstr.charAt(0) == "/" || numstr.charAt(numstr.length - 1) == "/") {
				Errmsg = " \u5206\u6570\u8bbe\u7f6e\u4e0d\u5408\u6cd5\u3002";
			} else {
      //2.分母第一位不能为0
				for (i = 0; i <= numstr.length - 1; i++) {
					TempChar = numstr.charAt(i);
					if (TempChar == "/") {
						beg = i + 1;
						break;
					}
				}
				if (numstr.charAt(beg) == 0) {
					Errmsg += " \u5206\u6bcd\u7b2c\u4e00\u4f4d\u4e0d\u80fd\u4e3a\u96f6\u3002";
				}
			}
		}
	}
	return Errmsg;
}
//检测是否含有某个字符
function checkChar(numstr, Subchar) {
	var TempChar;
	for (i = 0; i <= numstr.length - 1; i++) {
		TempChar = numstr.charAt(i);
		if (TempChar == Subchar) {
			i = -1;
			break;
		}
	}
	if (i == -1) {
		return true;
	} else {
		return false;
	}
}
//把分数转换为小数计算
function Fraction(numstr) {
	var TempChar;
	var fengzi = "";
	var fengmu = "";
	var beg;
	for (i = 0; i <= numstr.length - 1; i++) {
		TempChar = numstr.charAt(i);
		if (TempChar == "/") {
			beg = i + 1;
			break;
		}
		fengzi += TempChar;
	}
	for (i = beg; i <= numstr.length - 1; i++) {
		TempChar = numstr.charAt(i);
		fengmu += TempChar;
	}
	var result = parseInt(fengzi) / parseInt(fengmu);
  //  alert("result:"+result);
	return result;
}
/**
 * 日期：2006-03-10
 * 作者：heguoqiang
 */
/**
   * 功能:转换浮点数，默认为0，在JS做页面计算的时候会需要
   * @param s 需要处理的数字
    * @return 数字
   */
function getFloat(s) {
	if (isNaN(s) || s.length == 0) {
		return 0;
	} else {
		return parseFloat(s);
	}
}
/**
   * 功能:四舍五入函数
   * @param f 需要处理的数字
   * @param n 需要保留小数点位数，为0，表明是保留到整数位数
    * @return 数字
   */
function round(f, n) { // f: float value; n:radix point
	var r = 1;
	for (i = 1; i <= n; i++) {
		r = r * 10;
	}
	f2 = Math.round(f * r) / r;
	return f2;
}
/**
   * 功能:校核输入的字符是否符合日期规范 yyyy-mm-dd
   * @param str 需要校核的日期字符串
    * @return BOOLEAN
   */
function formatDate(str) {
	if (str.length != 10) {
		return false;
	}
	var t = str.charAt(4);
	if (t != "-") {
		return false;
	}
	var t = str.charAt(7);
	if (t != "-") {
		return false;
	}
	var s = str.substring(0, 4);
	if (!formatNum(s, 0, 1000, 9999)) {
		return false;
	}
	var y = parseInt(s);
	var s = str.substring(5, 7);
	if (!formatNum(s, 0, 1, 12)) {
		return false;
	}
	var m = parseInt(s);
	var s = str.substring(8);
	if (!formatNum(s, 0, 1, 31)) {
		return false;
	}
	var d = parseInt(s);
	if ((m == 4 || m == 6 || m == 9 || m == 11) && d > 30) {
		return false;
	}
	if (m == 2 && d > 29) {
		return false;
	}
	if (m == 2 && !((y % 4 == 0 && y % 100 != 0) || y % 400 == 0) && d > 28) {
		return false;
	}
	return true;
}
/**
   * 功能:校核输入的对象所含有的数据是否符合日期规范 yyyy-mm-dd
   * @param obj 需要校核的对象
    * @return BOOLEAN
   */
function formatDateAlert(obj) {
	var s = "";
	var objFlag = false;
	if (typeof (obj) == "object") {
		var s = obj.value;
		objFlag = true;
	} else {
		s = obj;
	}
	if (!formatDate(s)) {
		alert(s + "\n\u9a8c\u8bc1\u4e0d\u5408\u683c\uff0c\u8bf7\u8f93\u5165\u6b63\u786e\u7684\u65e5\u671f\uff08YYYY-MM-DD\uff09!");
		if (objFlag) {
			obj.focus();
		}
		return false;
	}
	return true;
}
/**
   * 功能:检查数值是否符合规定的格式
   * @param str 待校核的数字
   * @param n 需要校核的小数位数
   * startNum 数字开始值
   * endNum 数字结束值
   * flag 小数位是否必须补齐
   * @return boolean
   */
function formatNum(str, n, startNum, endNum, flag) {
	var flag1 = false; //是否符合数字规范
	var flag2 = false; //是否符合小规范
	var flag3 = false; //是否符合大规范
	var flag4 = true; //是否小数位必须补齐
	if (!isNaN(str)) {
		if (n == null) {//只是验证是否为数字情况
			flag1 = true;
		} else {
			if (n == 0) {//验证是否为整数
				if (str.indexOf(".") < 0 && str.indexOf(" ") < 0) {
					flag1 = true;
				}
			} else {//验证为小数的情况，只要不超过对应的小数位数就可以了
				if (str.indexOf(".") < 0 || str.length - str.indexOf(".") <= n + 1) {
					flag1 = true;
				}
				if (flag) {
					if (str.indexOf(".") < 0 || str.length - str.indexOf(".") != n + 1) {
						flag4 = false;
					}
				}
			}
		}
		if (startNum == null) {
			flag2 = true;
		} else {
			if (parseFloat(str) >= parseFloat(startNum)) {
				flag2 = true;
			}
		}
		if (endNum == null) {
			flag3 = true;
		} else {
			if (parseFloat(str) <= parseFloat(endNum)) {
				flag3 = true;
			}
		}
	}
	if (flag1 && flag2 && flag3 && flag4) {
		return true;
	}
	return false;
}
/**
   * 功能:检查数值是否符合规定的格式
   * @param obj 待校核的对象
   * @param n 需要校核的小数位数，可以为空，表明只要是数字就可以
   * startNum 数字开始值，可以为空，表示没有大小限制
   * endNum 数字结束值，可以为空，表示没有大小限制
   * flag 小数位是否必须补齐，true 表示必须补足小数位数，false和为空，表示不需要补足
   * @return boolean
   */
function formatNumAlert(obj, n, startNum, endNum, flag) {
	var s = "";
	var objFlag = false;
	if (typeof (obj) == "object") {
		var s = obj.value;
		objFlag = true;
	} else {
		s = obj;
	}
	var message = "";
	if (n == 0) {
		message += "\u5fc5\u987b\u662f\u6574\u6570!";
	}
	if (n > 0 && flag) {
		message += "\u5fc5\u987b\u8865\u8db3 " + n + " \u4f4d\u5c0f\u6570!";
	}
	if (startNum != null && endNum != null) {
		if (startNum == endNum) {
			message += "\u6570\u503c\u5fc5\u987b\u7b49\u4e8e " + startNum + " !";
		} else {
			message += "\u6570\u503c\u5fc5\u987b\u5728 " + startNum + " \u548c " + endNum + " \u4e4b\u95f4!";
		}
	} else {
		if (startNum != null) {
			message += "\u6570\u503c\u5fc5\u987b\u5927\u4e8e\u6216\u8005\u7b49\u4e8e " + startNum + "!";
		}
		if (endNum != null) {
			message += "\u6570\u503c\u5fc5\u987b\u5c0f\u4e8e\u6216\u8005\u7b49\u4e8e " + endNum + "!";
		}
	}
	if (!formatNum(s, n, startNum, endNum, flag)) {
		alert(s + "\n\u9a8c\u8bc1\u4e0d\u5408\u683c!" + message);
		if (objFlag) {
			obj.focus();
		}
		return false;
	}
	return true;
}
/**
   * 功能:根据COOKIE名称，得到COOKIE的值
   * @param name 需要取值的cookie名称
   * @return string cookie的值，无对应COOKIE则返回 "";
   */
function getCookie(name) {
	var cookieFound = false;
	var start = 0;
	var end = 0;
	var s = document.cookie;
	var i = 0;
// LOOK FOR name IN CookieString
	while (i <= s.length) {
		start = i;
		end = start + name.length;
		if (s.substring(start, end) == name) {
			cookieFound = true;
			break;
		}
		i++;
	}
  //CHECK IF NAME WAS FOUND
	if (cookieFound) {
		start = end + 1;
		end = s.indexOf(";", start);
		if (end < start) {
			end = s.length;
		}
		return (unescape(s.substring(start, end)));
	}
  //NAME WAS NOT FOUND
	return "";
}
/**
   * 功能:调整字符串输出长度，不够长的以空格填写，用于对<select>控件中格式处理之用
   * @param n 需要补足空格的位数
   * @return string 以空格组成的字符串
   */
function space(n) {
	var i = 0;
	var s = "";
	for (i = 0; i < n; i++) {
		s += " ";
	}
	return s;
}
/**
   * 功能:计算字符串的长度，汉字以2位表示
   * @param s 待处理的字符串
   * @return 字符串的长度
   */
function len(s) {
	var ln = 0, i = 0;
	for (i = 0; i < s.length; i++) {
		c = s.charAt(i);
		if (c >= " " && c <= "~") { // 所有单字节ASCII
			ln += 1;
		} else {
			ln += 2;
		}
	}
	return ln;
}
/**
   * 功能:字符串按照规定的长度截断，不足长度则不截断
   * @param s 待处理的字符串
   * @param xlen 字符串的长度
   * @return string
   */
function cut(s, xlen) {
	var ln = 0, i = 0;
	var t = "";
	for (i = 0; i < s.length; i++) {
		c = s.charAt(i);
		if (c >= " " && c <= "~") { // 所有单字节ASCII
			ln += 1;
		} else {
			ln += 2;
		}
		if (ln == xlen) {
			i++;
			t = s.substring(0, i);
			break;
		} else {
			if (ln > xlen) {
				t = s.substring(0, i) + " ";
				break;
			}
		}
	}
	return t;
}
/**
   * 功能:字符串以LN长度显示，不足加空格，多余的截断,最后以...结尾，
   * @param s 待处理的字符串
   * @param ln 字符串的长度
   * @return string
   */
function justify(s, ln) {
	var sLen = len(s);
	var t = "";
	var i = 0;
	if (sLen <= ln) {
		t = s + space(ln - sLen);
	} else {
		t = cut(s, ln - 3) + "...";
	}
	return t;
}
/**
   * 校核字符是否为字母或者数字
   * @param s
   * @return boolean
   */
function checkCharOrNum(s) {
	var ln = 0, i = 0;
	var t = "";
	if (s.length != len(s)) {
		return false;
	}
	for (i = 0; i < s.length; i++) {
		c = s.charAt(i);
		if (c >= "0" && c <= "9") {
			continue;
		}
		if (c >= "A" && c <= "Z") {
			continue;
		}
		if (c >= "a" && c <= "z") {
			continue;
		}
		return false;
	}
	return true;
}
/**
   * 校核字符是否为字母或者数字
   * @param obj 需要验证的对象
   * @return boolean
   */
function checkCharOrNumAlert(obj) {
	var s = "";
	var objFlag = false;
	if (typeof (obj) == "object") {
		var s = obj.value;
		objFlag = true;
	} else {
		s = obj;
	}
	if (!checkCharOrNum(s)) {
		alert(s + " \n\u9a8c\u8bc1\u4e0d\u5408\u683c\uff0c\u542b\u6709\u975e\u5b57\u6bcd\u548c\u6570\u5b57\u7684\u6570\u503c!");
		if (objFlag) {
			obj.focus();
		}
		return false;
	}
	return true;
}
/**
   * 校核字符是否含有双字节的字符
   * @param s
   * @return boolean
   */
function checkChinese(s) {
	var i = 0;
	for (i = 0; i < s.length; i++) {
		c = s.charAt(i);
		if (c >= " " && c <= "~") {
			continue;
		}
		return true;
	}
	return false;
}
/**
   * 校核字符是否含有双字节的字符
   * @param obj待验证的对象
   * @return boolean
   */
function checkChineseAlert(obj) {
	var s = "";
	var objFlag = false;
	if (typeof (obj) == "object") {
		var s = obj.value;
		objFlag = true;
	} else {
		s = obj;
	}
	if (!checkChinese(s)) {
		alert(s + " \n\u9a8c\u8bc1\u4e0d\u5408\u683c\uff0c\u6ca1\u6709\u542b\u4e2d\u6587!");
		if (objFlag) {
			obj.focus();
		}
		return false;
	}
	return true;
}
/**
   * 校核字符是否为空或者长度是否满足要求
   * @param s 待校核的字符串
   * @param minLen 必须满足的长度，可以为空
   * @param maxLen 不能超过的长度，可以为空
   * @return boolean
   */
function checkNullAndLen(s, minLen, maxLen) {
	if (s == null || trim(s).length == 0) {
		return false;
	}
	if (minLen != null) {
		if (parseInt(minLen) > parseInt(len(s))) {
			return false;
		}
	}
	if (maxLen != null) {
		if (parseInt(maxLen) < parseInt(len(s))) {
			return false;
		}
	}
	return true;
}
/**
   * 校核字符是否为空或者长度是否满足要求
   * @param obj 待校核的对象
   * @param minLen 必须满足的长度，可以为空
   * @param maxLen 不能超过的长度，可以为空
   * @return boolean
   */
function checkNullAndLenAlert(obj, minLen, maxLen) {
	var s = "";
	var objFlag = false;
	if (typeof (obj) == "object") {
		var s = obj.value;
		objFlag = true;
	} else {
		s = obj;
	}
	var message = "";
	if (minLen != null && maxLen != null) {
		if (minLen == maxLen) {
			message += "\u6570\u503c\u957f\u5ea6\u5fc5\u987b\u7b49\u4e8e " + minLen + " !";
		} else {
			message += "\u6570\u503c\u957f\u5ea6\u5fc5\u987b\u5728 " + minLen + " \u548c " + maxLen + " \u4e4b\u95f4!";
		}
	} else {
		if (minLen != null) {
			message += "\u6570\u503c\u957f\u5ea6\u5fc5\u987b\u5927\u4e8e\u6216\u8005\u7b49\u4e8e " + minLen + "!";
		} else {
			if (maxLen != null) {
				message += "\u6570\u503c\u957f\u5ea6\u5fc5\u987b\u5c0f\u4e8e\u6216\u8005\u7b49\u4e8e " + maxLen + "!";
			} else {
				message += "\u6570\u503c\u4e0d\u80fd\u4e3a\u7a7a\u548c\u5168\u90e8\u4e3a\u7a7a\u683c\uff01";
			}
		}
	}
	if (!checkNullAndLen(s, minLen, maxLen)) {
		alert(s + " \n\u9a8c\u8bc1\u4e0d\u5408\u683c!" + message + "\u4e00\u4e2a\u6c49\u5b57\u7b97\u4e24\u4e2a\u5b57\u7b26\uff01");
		if (objFlag) {
			obj.focus();
		}
		return false;
	}
	return true;
}
/**
   * 去掉字符串两边空格的函数
   * @param s 待处理的字符串
   * @return 处理完毕后的字符串
   */
function trim(s) {
	return rtrim(ltrim(s));
}
/**
   * 去掉字符串左边空格的函数
   * @param s 待处理的字符串
   * @return 处理完毕后的字符串
   */
function ltrim(s) {
	if (s == null || s.length == 0) {
		return "";
	}
	while (s.length > 0 && s.charAt(0) == " ") { //去除左边空格
		s = s.substring(1);
	}
	return s;
}
/**
   * 去掉字符串右边空格的函数
   * @param s 待处理的字符串
   * @return 处理完毕后的字符串
   */
function rtrim(s) {
	if (s == null || s.length == 0) {
		return "";
	}
	while (s.length > 0 && s.charAt(s.length - 1) == " ") {//去除右边空格
		s = s.substring(0, s.length - 1);
	}
	return s;
}
/**
   * 弹出对话窗口模式
   * @param ctrlobj 待返回数据的控件名称
   * @param url 弹出窗口所调用的URL地址，不含http://ip:8080/web/
   * @param width,height 弹出窗口的宽和高
   * @return boolean(操作有数值返回为true)
   */
function openDlgWindow(ctrlobj, url, width, height) {
	var secondSlash = 0;
	var path = "";
	var codebase = "";
	if ((secondSlash = (path = window.location.pathname).indexOf("/", 1)) != -1) {
		codebase = path.substring(0, secondSlash);
	}
	var d = new Date();
	url = url + "&temp_seq=" + d.getTime();
	retval = window.showModalDialog(codebase + "/" + url, "", "dialogWidth:" + width + "px; dialogHeight:" + height + "px; status:no; directories:yes;scrollbars:auto;Resizable=yes; ");
	if (retval != null) {
		ctrlobj.value = retval;
		return true;
	} else {
		return false;
	}
}
/**
   * 弹出普通窗口模式
   * @param url 弹出窗口所调用的URL地址，不含http://ip:8080/web/
   * @param width,height 弹出窗口的宽和高
   * @return boolean(操作有数值返回为true)
   */
function openWindow(url, width, height) {
	var secondSlash = 0;
	var path = "";
	var codebase = "";
	if ((secondSlash = (path = window.location.pathname).indexOf("/", 1)) != -1) {
		codebase = path.substring(0, secondSlash);
	}
	var para = "Resizable=yes,scrollbars=yes,width=" + width + "px,height=" + height + "px";
	window.open(codebase + "/" + url, "", para);
}
/**
 取得checkbox 和 RADIO 选中的个数
  * @param obj checkbox对象
   * @return num 个数
 */
function getCheckedNum(obj) {
	var num = 0;
	if (obj.length > 1) {//对象数组情况
		for (var i = 0; i < obj.length; i++) {
			if (true == obj[i].checked) {
				num++;
			}
		}
	} else {//单个对象情况
		if (true == obj.checked) {
			num++;
		}
	}
	return num;
}
/**
 取得checkbox 和 RADIO 选中的个数
  * @param obj checkbox对象
   * @return num 个数
 */
function getCheckedNumAlert(obj, num) {
	if (typeof (obj) != "object") {//供调试使用
		alert("\u65e0\u8bb0\u5f55\u6216\u8005\u5bf9\u8c61\u9519\u8bef\uff01");
		return false;
	}
	var num2 = getCheckedNum(obj);
	var flag = false;//判断是否是数组
	var flag2 = false;//判断是否有错误
	if (obj.length > 1) {//对象数组情况
		flag = true;
	}
	if (num2 < 1) {
		alert("\u8bf7\u9009\u62e9\uff01");
		flag2 = true;
	} else {
		if (num != null && num2 != num) {
			alert("\u5fc5\u987b\u9009\u62e9 " + num + " \u4e2a\uff01");
			flag2 = true;
		}
	}
	if (flag2) {
		if (flag) {
			obj[0].focus();
			return false;
		} else {
			obj.focus();
			return false;
		}
	}
	return true;
}
/**
   * 功能:字符串替换函数
   * @param s
   * @return
   */
function strReplace(s, sourceStr, replaceStr) {
	if (s == null || s.length == 0 || sourceStr == null || sourceStr.length == 0 || replaceStr == null) {
		return s;
	}
	var index = 0, startIndex = 0;
	var length = sourceStr.length;
	var length1 = replaceStr.length;
	while (!((index = s.indexOf(sourceStr, startIndex)) < 0)) {
		startIndex = index - length + length1;
		s = s.substring(0, index) + replaceStr + s.substring(index + length);
	}
	return s;
}
/**
   * 功能:把SELECT 的所有选项都选中
   * @param s
   * @return
   */
function selectAllOptions(obj) {
	if (typeof (obj) != "object") {//供调试使用
		return false;
	}
	for (var i = 0; i < obj.length; i++) {
		obj[i].selected = true;
	}
	return true;
}
/**
   * 功能:把SELECT中选中的option值返回
   * @param s
   * @return string
   */
function getSelectedOption(obj) {
	for (var i = 0; i < obj.length; i++) {
		if (obj[i].selected == true) {
			return obj[i].value;
		}
	}
	return "";
}
/**
   * 功能:  检查是否为数字或小数
   * @param s
   * @return  true/false
   */
function checkFloat(numstr) {
	var TempChar;
	for (i = 0; i <= numstr.length - 1; i++) {
		TempChar = numstr.charAt(i);
		if (TempChar != 0 && TempChar != 1 && TempChar != 2 && TempChar != 3 && TempChar != 4 && TempChar != 5 && TempChar != 6 && TempChar != 7 && TempChar != 8 && TempChar != 9 && TempChar != ".") {
			i = -1;
			break;
		}
	}
	if (i == -1) {
		return false;
	} else {
		return true;
	}
}
/**
   * 功能:  检查是否为空
   * @param s
   * @return  true/false
   */
function checkNull(obj) {
	if (obj.value == null || obj.value == "") {
		alert("\u8bf7\u8f93\u5165\u503c\uff01");
		return false;
	} else {
		return true;
	}
}

	// 弹出日期选择框
function fPopUpCalendarDlg(ctrlobj) {
	showx = event.screenX - event.offsetX - 4 - 210; // + deltaX;
	showy = event.screenY - event.offsetY + 18; // + deltaY;
	newWINwidth = 210 + 4 + 18;
	retval = window.showModalDialog("../../../js/date.htm",'', "dialogWidth:197px; dialogHeight:210px; dialogLeft:" + showx + "px; dialogTop:" + showy + "px; status:no; directories:yes;scrollbars:no;Resizable=no; ");
	if (retval != null) {
		ctrlobj.value = retval;
	} else {
			//alert("canceled");
	}
}

	// 弹出日期选择框
	function fPopUpCalendarDlg1(ctrlobj){
		showx = event.screenX - event.offsetX - 4 - 210 ; // + deltaX;
		showy = event.screenY - event.offsetY + 18; // + deltaY;
		newWINwidth = 210 + 4 + 18;
	
		retval = window.showModalDialog("../../js/date.htm", "", "dialogWidth:197px; dialogHeight:210px; dialogLeft:"+showx+"px; dialogTop:"+showy+"px; status:no; directories:yes;scrollbars:no;Resizable=no; "  );
		if( retval != null ){
			ctrlobj.value = retval;
		}else{
			//alert("canceled");
		}
	}

	//数据库表定位
function dbTabLocation(id, name, link_id) {
	if (link_id == null || link_id.length == 0) {
		alert("\u6570\u636e\u5e93\u8fde\u63a5\u4e3a\u7a7a!");
		return;
	}
	var d = new Date();
	var f = document.forms(0);
	var urlStr = "t07_metadata_tab_location_do.do";
	urlStr = urlStr + "?link_id=" + link_id + "&id=" + id + "&name=" + name + "&temp_seq_ram=" + d.getTime();
	var retval = window.showModalDialog(urlStr, "", "dialogWidth:600px; dialogHeight:600px; status:no;");
	if (retval != null && retval.length > 0) {
		var a = retval.split(",");
		if (a[0] == null || a[0].length == 0) {
			a[0] = "";
		}
		if (a[1] == null || a[1].length == 0) {
			a[1] = "";
		}
		var obj = eval("document.forms[0]." + id);
		obj.value = a[0];
		var obj = eval("document.forms[0]." + name);
		obj.value = a[1];
	}
}


  //数据库表定位
function metaTabLocation(id, name, id2, name2) {
	var d = new Date();
	var f = document.forms(0);
	var urlStr = "/etl/metadata/t07_metadata_tab/t07_metadata_tab_location_query.do";
	urlStr = urlStr + "?id2=" + id2 + "&name2=" + name2 + "&id=" + id + "&name=" + name + "&temp_seq_ram=" + d.getTime();
	var retval = window.showModalDialog(urlStr, "", "dialogWidth:800px; dialogHeight:600px; status:no;");
	if (retval != null && retval.length > 0) {
		var a = retval.split(",");
		if (a[0] == null || a[0].length == 0) {
			a[0] = "";
		}
		if (a[1] == null || a[1].length == 0) {
			a[1] = "";
		}
		if (a[2] == null || a[2].length == 0) {
			a[2] = "";
		}
		if (a[3] == null || a[3].length == 0) {
			a[3] = "";
		}
		var obj = eval("document.forms[0]." + id);
		obj.value = a[0];
		var obj = eval("document.forms[0]." + name);
		obj.value = a[1];
		if (id2 != null) {
			var obj = eval("document.forms[0]." + id2);
			obj.value = a[2];
		}
		if (name2 != null) {
			var obj = eval("document.forms[0]." + name2);
			obj.value = a[3];
		}
	}
}

	  // 任务定位
function taskLocation(id, name) {
	var d = new Date();
	var f = document.forms(0);
	var urlStr = "/etl/etl/t07_etl_base/v07_etl_base_list_location.do";
	urlStr = urlStr + "?newsearchflag=1&id=" + id + "&name=" + name + "&temp_seq_ram=" + d.getTime();
	var retval = window.showModalDialog(urlStr, "", "dialogWidth:800px; dialogHeight:600px; status:no;");
	if (retval != null && retval.length > 0) {
		var a = retval.split(",");
		if (a[0] == null || a[0].length == 0) {
			a[0] = "";
		}
		if (a[1] == null || a[1].length == 0) {
			a[1] = "";
		}
		var obj = eval("document.forms[0]." + id);
		obj.value = a[0];
		var obj = eval("document.forms[0]." + name);
		obj.value = a[1];
	}
}

  //规则库定位
function rulesDbLocation(id, name) {
	var d = new Date();
	var f = document.forms(0);
	var urlStr = "/etl/rules/t07_rules_db/t07_rules_db_location.do";
	urlStr = urlStr + "?newsearchflag=1&id=" + id + "&name=" + name + "&temp_seq_ram=" + d.getTime();
	var retval = window.showModalDialog(urlStr, "", "dialogWidth:400px; dialogHeight:600px; status:no;");
	if (retval != null && retval.length > 0) {
		var a = retval.split(",");
		if (a[0] == null || a[0].length == 0) {
			a[0] = "";
		}
		if (a[1] == null || a[1].length == 0) {
			a[1] = "";
		}
		var obj = eval("document.forms[0]." + id);
		obj.value = a[0];
		var obj = eval("document.forms[0]." + name);
		obj.value = a[1];
	}
}

function changcol(box) {
	var r = box.parentElement.parentElement;
	if (box.checked ) {		
		r.style.backgroundColor = selectedColor;
	} else {
		r.style.backgroundColor = "";
	}
}
function TableMouseOver(Obj, id) {		
	if (Obj.style.backgroundColor != selectedColor) {		
		Obj.style.backgroundColor = mouseOverColor;
	}
}
function TableMouseOut(Obj, id) {		
	if (Obj.style.backgroundColor != selectedColor) {		
		Obj.style.backgroundColor = "";
	}
}      



//================表单样式统一设定js begin: @confessor.w======================





/**
 *  查询条件表格样式设定[[公共方法---->供外部調用]]
 *  @param: tableID ----表格的ID
 */

function setConditionTableStyle(tableID) {
	var table = document.getElementById(tableID);
	table.className="condition";
	var startColomn = "1";	//默认起始列
	var endColomn = "3";	//默认结束列
	var startRow = "0";		//默认起始行
	var endRow = table.rows.length;		//表实际行数
	var columnNum = startColomn + ";" + endColomn;
	var rows = startRow + ";" + endRow;
	commonTableSet(tableID, columnNum, rows, "0");
}
/**
 * 结果明细表格样式设定[[公共方法---->供外部調用]]
 * @param: tableID ----表格的ID
 */
function setDetailTableStyle(tableID) {
	var table = document.getElementById(tableID);
	table.className="detail";
	setConditionTableStyle(tableID);
}
/**
 * 模块Title样式设定[[公共方法---->供外部調用]]
 * @param: tableID ----表格的ID
 */
function setTitleTableStyle(tableID) {
	var table = document.getElementById(tableID);	
	table.className="title";
	var leftStyle = "BORDER-RIGHT: #000000 0px solid;BORDER-TOP: #000000 0px solid;BORDER-LEFT: #000000 1px solid;BORDER-BOTTOM: #000000 1px solid;CURSOR: default;BACKGROUND-COLOR: #EEEEEE;";
	var rightStyle = "BORDER-RIGHT: #000000 1px solid;BORDER-TOP: #000000 0px solid;BORDER-LEFT: #000000 0px solid;BORDER-BOTTOM: #000000 1px solid;CURSOR: default;BACKGROUND-COLOR: #EEEEEE;";
	table.rows[0].cells[0].style.cssText = leftStyle;
	table.rows[0].cells[1].style.cssText = rightStyle;
	setInputStyle();
}
/**
 * 查询结果列表样式设定[[公共方法---->供外部調用]]
 * @param: tableID ----表格的ID
 */
function setListTableStyle(tableID) {	
	var table = document.getElementById(tableID);
	table.className="list";
	listStyleSetNoAlternation(tableID);
}
/**
 * 通用表单样式设定:采用facade模式设计 [[公共方法---->供外部調用]]
 * @param tableID 需要设定样式的表单id
 * @param style   需要设定的样式名称
 * 						("condition"----条件查询表单样式;"detail"----明细页面表单样式;
 * 						"title"----页面title表样式;     "list"----查询结果列表样式)
 */
function setTableStyle(tableID,style){	
	switch(style){
		case "condition":
			setConditionTableStyle(tableID);
			break;
		case "detail":
			setDetailTableStyle(tableID);
			break;
		case "title":
			setTitleTableStyle(tableID);
			break;
		case "list":		
		default:
			setListTableStyle(tableID);
			break;
	}
}


/**
	 * 查询结果列表样式设置[[內部方法---->仅供js內部調用]]
	 * 无间隔行样式
	 * @param tableId----表的id(字符串)
	 * */
function listStyleSetNoAlternation(tableID) {	
	var table = document.getElementById(tableID);
	table.rows[0].style.cssText = "background-color:#A3C0EE;height:22px";
	var rowStyle_0 = "background-color:#ffffff;"	//初始化行样式
	for (i = 1; i < table.rows.length; i++) {		
		table.rows[i].style.cssText = rowStyle_0;
		table.rows[i].onmouseover = function () {
			var e = event.srcElement;
			if (e == this) {
				return;
			}
			while (e.tagName != "TR") {
				e = e.parentElement;
			}
			if(e.style.backgroundColor!=selectedColor){
				e.style.backgroundColor = mouseOverColor;
			}
			
		};
		table.rows[i].onmouseout = function () {
			var e = event.srcElement;
			if (e == this) {
				return;
			}
			while (e.tagName != "TR") {
				e = e.parentElement;
			}
			if(e.style.backgroundColor!=selectedColor){
				e.style.backgroundColor = "";
			}			
		};
	}
}



/**
 * 设定页面内所有输入框的样式[[內部方法---->仅供js內部調用]]
 */
function setInputStyle() {
	if (!styleSetComplete) {
		var inputs = document.getElementsByTagName("input");
		var textArea = document.getElementsByTagName("textarea");
		setElementCssStyle(inputs);
		setElementCssStyle(textArea);
		setSelectStyle();	//设定下拉框样式
		styleSetComplete = true;
	}
}
/**
 * 设定页面内文本输入框、textarea框样式[[內部方法---->仅供js內部調用]]
 */
function setElementCssStyle(inputs) {
	var buttonClassName = "input";
	var activeStyle = " FONT-SIZE: 9pt;BORDER-RIGHT: #333333 1px solid;BORDER-TOP: #333333 1px solid;SCROLLBAR-FACE-COLOR: #f0f9ff;SCROLLBAR-HIGHLIGHT-COLOR: lightgrey;BORDER-LEFT: #333333 1px solid;SCROLLBAR-SHADOW-COLOR: gray;SCROLLBAR-3DLIGHT-COLOR: #f0f9ff;BORDER-BOTTOM: #333333 1px solid;SCROLLBAR-DARKSHADOW-COLOR: #f0f9ff;BACKGROUND-COLOR: #e0f0ff;";
	var inactiveStyle = "BORDER-BOTTOM:#000000 1px solid;BORDER-LEFT:#000000 1px solid;BORDER-RIGHT:#000000 1px solid;BORDER-TOP: #000000 1px solid;BACKGROUND:#ffffff;COLOR:#000000;font-size:12px";
	for (var i = 0, j = inputs.length; i < j; i++) {
		if (inputs[i].type == "text" || inputs[i].type == "password" || inputs[i].type == "textarea") {
			inputs[i].className = "text_white";
			inputs[i].onfocus = function () {
				var e = event.srcElement;
				e.style.cssText = activeStyle;
			};
			inputs[i].onblur = function () {
				var e = event.srcElement;
				e.style.cssText = inactiveStyle;
			};
		}else if (inputs[i].type=="button"&&!inputs[i].className){
			inputs[i].className = buttonClassName;
		}
		
	}
}
/**
 * 设定页面内下拉框样式[[內部方法---->仅供js內部調用]]
 * @param {Object} event
 */
function setSelectStyle() {
	var sStyle = "\tpadding-left: 16px; padding-right: 16px; height: 20px;font-size: 9pt; background-color: #ffffff; ";
	var select = document.getElementsByTagName("select");
	if (select != null && select.length > 0) {
		for (var i = 0, j = select.length; i < j; i++) {
//			if (select[i].className == undefined) {
			select[i].style.cssText = sStyle;
//			}
		}
	}
}

function commonTableSet(tableId, columnNum, Row, hasTitle, specialStyle) {
	var table = document.getElementById(tableId);
	columns = columnNum.split(";");
	for (j = 0; j < columns.length; j++) {
		columns[j] = parseInt(columns[j]) - 1;
	}
	var R = Row.split(";");
	firR = parseInt(R[0]);
	endR = parseInt(R[1]);
	var flag = true;
	if (hasTitle == 1) {
		table.rows[0].style.cssText = "border-right:#c3dfff solid 1px;border-bottom:#c3dfff solid 1px;background-color:#6699cc;color:#ebf7ff;font-weight:bold;height:25px;padding-left:4px;padding-right:4px;";
	}
	for (j = firR; j < endR; j++) {
		flag = table.rows[j] != null;
		if (flag) {
			for (i = 0; i < table.rows[j].cells.length; i++) {
				if (i == columns[0] || i == columns[1]) {
					table.rows[j].cells[i].style.cssText = "background-color:#ebf7ff";
				} else {
					table.rows[j].cells[i].style.cssText = "background-color:#F6F9FF;";
					table.rows[j].cells[i].setAttribute("align", "left");
				}
			}
		}
	}
	setInputStyle();
}

//================表单样式统一设定js end: @confessor.w======================

function selectORClearBox(_form,_setval){
	var isFirstRow = true;
   for( var i=0; i < _form.elements.length; i++ ){
        if (_form.elements[i].type == 'checkbox' ){        	
            _form.elements[i].checked = _setval;
            if(!isFirstRow){	//控制表格title行不改背景色
            	changcol(_form.elements[i]);
            }
            isFirstRow = false;
        }
   }
}

/**
 * @author confessor.w
 * @version 1.0
 * 验证页面内的全部必填字段-->支持对文本输入框,radio,checkbox框,select下拉框验证
 * 要求：页面内需要必填的字段存储在ID为"NotPermitEmptyIDS"的隐含字段的value中,
 * 填写格式为: "文本必填字段中文名,必填字段name,最大长度;其他类型必填字段中文名,必填字段name;..."
 * @return boolean值-->true：全部必填字段已符合要求;
 * 					   false：有必填字段待录入
 * */
function validateNoEmpty(columns){
	var hiddenID = columns.length>0?columns:document.getElementById("NotPermitEmptyIDS").value;
	var str = hiddenID.length>0?hiddenID.split(";"):"";
	if(str!=""&&str.length>0){		
		for(var i=0,j=str.length;i<j;i++){
			var column = str[i].split(",");
			var temp =  document.getElementsByName(column[1])[0];
			var errMsg = column[0]+"不能为空!";						
			if(temp.type=="checkbox"||temp.type=="radio"){
				var box = document.getElementsByName(column[1]);
				var isChecked=false;
				for(var m=0,n=box.length;m<n;m++){
					if(box[m].checked==true){
						isChecked=true;
						continue;
					}
				}				
				if(!isChecked){
					alert(errMsg);
					temp.focus();
					return false;	
				}				
			}else if((temp.type=="select-one")&&temp.value==""){
				alert(errMsg);				
				temp.focus();
				return false;						
			}else if(temp.type=="text"){
				if(temp.value==""){
					alert(errMsg);				
					temp.focus();
					return false;
				}else if(column[2]!= undefined ){
					if(len(temp.value)>column[2]){
						alert(column[0]+"长度不能超过"+column[2]+"位(每个中文字符占两位)!");
						temp.focus();
						return false;	
					}
				}
			}
		}		
	}
	return true;
}




/**
 * @author confessor.w
 * @version 1.0
 * 根据日期格式，将字符串转换成Date对象-----用于比较日期字符串大小
 *	格式：yyyy-年，MM-月，dd-日，HH-时，mm-分，ss-秒。
 *	（格式必须写全，例如:yy-M-d，是不允许的，否则返回null；格式与实际数据不符也返回null。）
	默认格式：yyyy-MM-dd HH:mm:ss,yyyy-MM-dd。
 */
function getDateByFormat(str){
	var dateReg,format;	
	var y,M,d,H,m,s,yi,Mi,di,Hi,mi,si;	
	if((arguments[1] + "") == "undefined") 
		format = "yyyy-MM-dd HH:mm:ss";
	else 
		format = arguments[1];			
	yi = format.indexOf("yyyy");	
	Mi = format.indexOf("MM");	
	di = format.indexOf("dd");	
	Hi = format.indexOf("HH");	
	mi = format.indexOf("mm");
	si = format.indexOf("ss");	
	if(yi == -1 || Mi == -1 || di == -1)
		 return null;	
	else{	
		y = parseInt(str.substring(yi, yi+4));	
		var Mon=str.substring(Mi, Mi+2);
		Mon = Mon=="08"?"8":Mon;
		Mon = Mon=="09"?"9":Mon;	
		M = parseInt(Mon);		
		var day = str.substring(di, di+2);
		day = day=="08"?"8":day;
		day = day=="09"?"9":day;
		d = parseInt(day);					
	}	
	if(isNaN(y) || isNaN(M) || isNaN(d))
		 return null;	
	if(Hi == -1 || mi == -1 || si == -1) 
		return new Date(y, M-1, d);	
	else{	
		H = str.substring(Hi, Hi+4);		
		m = str.substring(mi, mi+2);		
		s = str.substring(si, si+2);	
	}	
	if(isNaN(parseInt(y)) || isNaN(parseInt(M)) || isNaN(parseInt(d))) 
		return new	Date(y, M-1, d);	
	else 
		return new Date(y, M-1, d,H, m, s);
}
/**
 * 验证起始日期与截至日期的大小关系
 * @param startDateName  页面内的起始日期字段名称;
 *        endDateName    页面内截止日期字段名称
 * 		
 */
function validateDateRange(startDateName,endDateName){
	var valFlag=true;
	var start =  document.getElementsByName(startDateName)[0];
	var end = document.getElementsByName(endDateName)[0];
	if(start.value!="" && end.value != ""){
		var s = getDateByFormat(start.value);
		var e = getDateByFormat(end.value);
		if(s>e){
			valFlag=false;
			alert("起始日期不能大于结束日期!");
			start.focus();
		}
	}
	return valFlag;
}	

/*
功能：判断是否为日期(格式:yyyy年MM月dd日,yyyy-MM-dd,yyyy/MM/dd,yyyyMMdd)
提示信息：未输入或输入的日期格式错误！
使用：f_check_date(obj)
返回：bool
*/
function f_check_date(obj) {	
	obj = event.srcElement;	
	var date =  trim(obj.value);
	if(date.length==0){
		return true;
	}
	//var dtype = "()"//obj.eos_datatype;
	//var format = dtype.substring(dtype.indexOf("(") + 1, dtype.indexOf(")")); //
	var format ;	//日期格式
	var isNeedFormat = false;
    if(date.length>0&&date.indexOf('-')!=-1){
		format = "yyyy-MM-dd";	//日期格式
	}else if(date.length==8){
		format = "yyyyMMdd";
		isNeedFormat = true;
	}	
	
	var year, month, day, datePat, matchArray;
	if (/^(y{4})(-|\/)(M{1,2})\2(d{1,2})$/.test(format)) {
		datePat = /^(\d{4})(-|\/)(\d{1,2})\2(\d{1,2})$/;		
	} else {
		if (/^(y{4})(年)(M{1,2})(月)(d{1,2})(日)$/.test(format)) {
			datePat = /^(\d{4})年(\d{1,2})月(\d{1,2})日$/;
		} else {
			if (format == "yyyyMMdd") {
				datePat = /^(\d{4})(\d{2})(\d{2})$/;				
			} else {
				f_alert(obj, "\u65e5\u671f\u683c\u5f0f\u4e0d\u5bf9");
				return false;
			}
		}
	}
	if (date.length > 0) {
		matchArray = date.match(datePat);
		if (matchArray == null) {
			f_alert(obj, "\u65e5\u671f\u957f\u5ea6\u4e0d\u5bf9,\u6216\u65e5\u671f\u4e2d\u6709\u975e\u6570\u5b57\u7b26\u53f7");
			return false;
		}
		if (/^(y{4})(-|\/)(M{1,2})\2(d{1,2})$/.test(format)) {
			year = matchArray[1];
			month = matchArray[3];
			day = matchArray[4];
		} else {
			year = matchArray[1];
			month = matchArray[2];
			day = matchArray[3];
		}
		if (month < 1 || month > 12) {
			f_alert(obj, "\u6708\u4efd\u5e94\u8be5\u4e3a1\u523012\u7684\u6574\u6570");
			return false;
		}
		if (day < 1 || day > 31) {
			f_alert(obj, "\u6bcf\u4e2a\u6708\u7684\u5929\u6570\u5e94\u8be5\u4e3a1\u523031\u7684\u6574\u6570");
			return false;
		}
		if ((month == 4 || month == 6 || month == 9 || month == 11) && day == 31) {
			f_alert(obj, "\u8be5\u6708\u4e0d\u5b58\u572831\u53f7");
			return false;
		}
		if (month == 2) {
			var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
			if (day > 29) {
				f_alert(obj, "2\u6708\u6700\u591a\u670929\u5929");
				return false;
			}
			if ((day == 29) && (!isleap)) {
				f_alert(obj, "\u95f0\u5e742\u6708\u624d\u670929\u5929");
				return false;
			}
		}
		if(isNeedFormat){
			changeFormat(obj);
		}
		return true;
	}
}
function f_alert(obj, alertInfo) {	
	alert(alertInfo + "\uff01");
	obj.select();
	if (isVisible(obj) && checkPrVis(obj)) {
		obj.focus();
	}
}
function isVisible(obj) {
	var visAtt, disAtt;
	try {
		disAtt = obj.style.display;
		visAtt = obj.style.visibility;
	}
	catch (e) {
	}
	if (disAtt == "none" || visAtt == "hidden") {
		return false;
	}
	return true;
}
function checkPrVis(obj) {
	var pr = obj.parentNode;
	do {
		if (pr == undefined || pr == "undefined") {
			return true;
		} else {
			if (!isVisible(pr)) {
				return false;
			}
		}
	} while (pr = pr.parentNode);
	return true;
}

function changeFormat(obj){
	var cdate = obj.value;	
	if(cdate.length=8&&cdate.indexOf('-')==-1){
		obj.value = cdate.substring(0,4)+'-'+cdate.substring(4,6)+'-'+cdate.substring(6,8);
	}
}
/**
 * 为日期格式的输入框添加日期格式校验和自动转换
 * @param dateStr  支持";"分隔的字符串    
 * @调用方式：在</body>与</html>之间插入js脚本调用。例如：attachDateFormat("start_date;end_date");
 */
function attachDateFormat(dateStr){
    var str = dateStr.split(";");
    if(str.length>0){
    	for(var i=0,j=str.length;i<j;i++){
    		var obj = document.getElementsByName(str[i])[0];
    		if(obj!=undefined){
    			obj. attachEvent("onblur", f_check_date, false);
    		}
    	}	  
    }
}

/**
 * 参数1：密码
 * 参数2：密码长度校验
 * 参数3：密码所含字母最少个数
 */ 
function checkPassword(Charstr,pass_min_str,pass_min_len) {
		var Tempstr;
		var numLen = 0;
		var charLen = 0;
		var haveNum =0;
		var haveChr =0;
		var msg = "";

			for (j = 0; j <= Charstr.length - 1; j++) {
				Tempstr = Charstr.charAt(j);	
				if(Tempstr >= "0" && Tempstr <= "9"){
					haveNum = 1;
					numLen += 1;
				}else if((Tempstr >= "a" && Tempstr <= "z") || (Tempstr >= "A" && Tempstr <= "Z")){
					haveChr = 1;
					charLen += 1;
				}				
			}
			if(charLen < pass_min_str){
				msg="密码至少含有"+pass_min_str+"个字母!";
			}
			if(haveNum==0){
				msg = "密码安全性不足，必须含有数字！";
				}
			if(haveChr==0){
				msg = "密码安全性不足，必须含有字母！";
			}	
			
		return msg;
	}


function setTextAreaShowNumber(sidname, textarea){
	var value= textarea.value;
	if  (textarea.readOnly ==false) {
		var length = 0;
		for (i = 0; i < value.length; i++) {
			length += (value.charAt(i) > '~')?2:1; 
		}                
        document.getElementById(sidname).innerText=Math.floor((length+1)/2); 
     }
}

function setTextAreaClearNumber(sidname){
	  document.getElementById(sidname).innerText='';
}