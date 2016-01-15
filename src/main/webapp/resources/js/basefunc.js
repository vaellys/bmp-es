
/**
//================================���ܺ����б�����======================//
// �������ڣ�2006-03-10
// ������Ա��heguoqiang
���		��������    			��������			����ֵ 
1		checkPrice			���۸�			boolean
2		checkPrice1			���۸�			1/0
3		checkTelephone			���绰����		1/0
4		checkNum			�������			boolean
5		time_select			�ꡢ�¡������ڵ�����ѡ������
6		checkRadio			����Ƿ�ѡ��check��	num>0�Ѿ�ѡ��
7		checkString			����ִ��Ƿ�Ϊ���ֻ���ĸ	1/0
8		checkNumLength			����ִ�����Ϊ�Ƿ�strLength	boolean
9		checkNumber			�������			1/0
10		checkEmail			�������ʼ��Ƿ���ȷ	boolean
11		CheckBoxCheckedNum		ȡ��checkboxѡ�еĸ���
12		CheckBoxCheckedSingleOne	ֻ��ѡ��һ��checkbox
13		CheckBoxMustChecked		����ѡ��һ��checkbox
14		getLength			�ж�һ���ַ�����ʵ���ȣ�����Ϊ��λ
15		checkChina
16		checkPhone			�жϵ绰�����Ƿ�Ϸ�	boolean
17		checkDouble			������֣���֤С����������λ����
18		checkFraction			����Ƿ�ΪС�������
19		checkChar			����Ƿ���ĳ���ַ�
20		Fraction			�ѷ���ת��ΪС������
21		getFloat			ת����������Ĭ��Ϊ0����JS��ҳ������ʱ�����Ҫ
22		round				�������뺯��
23		formatDate			У��������ַ��Ƿ�������ڹ淶 yyyy-mm-dd
24		formatDateAlert			У��������ַ��Ƿ�������ڹ淶 yyyy-mm-dd�����ϸ�����ʾ��Ϣ��
25		formatNum			�����ֵ�Ƿ���Ϲ涨�ĸ�ʽ
26		formatNumAlert			�����ֵ�Ƿ���Ϲ涨�ĸ�ʽ�����ϸ�����ʾ��Ϣ��
27		getCookie			����COOKIE���ƣ��õ�COOKIE��ֵ
28		space				�����ַ���������ȣ����������Կո���д�����ڶ�<select>�ؼ��и�ʽ����֮��
29		len				�����ַ����ĳ��ȣ�������2λ��ʾ
30		cut				�ַ������չ涨�ĳ��Ƚضϣ����㳤���򲻽ض�
31		justify				�ַ�����LN������ʾ������ӿո񣬶���Ľض�,�����...��β��
32		checkCharOrNum			У���ַ��Ƿ�Ϊ��ĸ��������
33		checkCharOrNumAlert		У���ַ��Ƿ�Ϊ��ĸ�������֣����ϸ�����ʾ��Ϣ��
34		checkChinese			У���ַ��Ƿ���˫�ֽڵ��ַ�
35		checkChineseAlert		У���ַ��Ƿ���˫�ֽڵ��ַ������ϸ�����ʾ��Ϣ��
36		checkNullAndLen			У���ַ��Ƿ�Ϊ�ջ��߳����Ƿ�����Ҫ��
37		checkNullAndLenAlert		У���ַ��Ƿ�Ϊ�ջ��߳����Ƿ�����Ҫ�󣨲��ϸ�����ʾ��Ϣ��
38		trim				ȥ���ַ������߿ո�ĺ���
39		ltrim				ȥ���ַ�����߿ո�ĺ���
40		rtrim				ȥ���ַ����ұ߿ո�ĺ���
41		openDlgWindow			�����Ի�����ģʽ
42		openWindow			������ͨ����ģʽ
43		getCheckedNum			ȡ��checkbox �� RADIO ѡ�еĸ���
44		getCheckedNumAlert		ȡ��checkbox �� RADIO ѡ�еĸ��������ϸ�����ʾ��Ϣ��
45		getSelectedOption		��SELECT��ѡ�е�optionֵ����
46		checkFloat  			����Ƿ�Ϊ���ֻ�С��	  true/false
47      checkNull               ����Ƿ�Ϊ��             true/false
48		validateNoEmpty         У��ҳ���ڵı����ֶ��Ƿ�����    true/false
49		getDateByFormat         ����strֵ���ض�Ӧ�����ڶ���,���ڱȽϿ�ʼ���ںͽ������ڴ�С 
50      validateDateRange       ��֤��ʼ������������ڵĴ�С��ϵ    true/false
51      attachDateFormat        Ϊ���������ֶ�������ڸ�ʽת������֤
* 		checkPassword           ��������ǿ��
//===================================���ܺ����б�����======================//
*/

/**
 * ҳ�����css��ʽ����
 */
var styleSetComplete = false;	//ҳ����ʽͳһ�趨��ɱ�־
var selectedColor="#ffffcc";	//ѡ���б���ɫ
var mouseOverColor="#cccccc";	//�����ͣ�б���ɫ




/**-----------------------------------ҳ����ʽ�趨��������-------------------
 * 
100     setTableStyle           ͨ�ñ���ʽ�趨
101     setTitleTableStyle      ҳ��title����ʽ   
102     setConditionTableStyle  ������ѯ����ʽ
103     setListTableStyle       ��ѯ����б���ʽ
104     setDetailTableStyle     ��ϸҳ�����ʽ 
-------------------------------------------------------------------------*/



//���ܣ����۸�
//����:�۸��ַ���;�۸����Ϊ��xxx.xx��ʽ
//���أ�true / false
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
 �۸��п��Ժ������ֺ�.
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
 �绰�п��Ժ������ֺ�-
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
//���ܣ��������
//���룺�����ַ���
//���أ�true / false
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
//���ܣ��ꡢ�¡������ڵ�����ѡ����������
//���룺day_se-��ǰѡ�������;time_n-ʱ���ַ���;year_n,month_n,day_n�������յ�form������
//���أ�
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
//���ܣ�����Ƿ�ѡ��check��
//
//���أ�num>0�Ѿ�ѡ��
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

//����ִ��Ƿ�Ϊ���ֻ���ĸ
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

//����ִ�����Ϊ�Ƿ�strLength
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

//����Ƿ�Ϊ����
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

//�������ʼ��Ƿ���ȷ
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
        //alert("�����Email���ܰ�������" + invalid);
		return false;
	}
}
/**
 ȡ��checkboxѡ�еĸ���
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
 ȡ��radioѡ�еĸ���
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

//���form�����ʵ����
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

//���form�����ʵ����
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
 ������֣���֤С����������λ����
 number-Ҫ��������
 jd-С���ľ��ȣ�����С������λ��
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
//            //����
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
//����Ƿ�ΪС�������
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
    //���Ϊ���������Ϸ���
	if ((Errmsg == "") && (checkChar(numstr, "/"))) {
       //1.����һλ����Ϊ0
		if (numstr.charAt(0) == 0) {
			Errmsg = " \u5206\u5b50\u7b2c\u4e00\u4f4d\u4e0d\u80fd\u4e3a\u96f6\u3002";
		} else {
			if (numstr.charAt(0) == "/" || numstr.charAt(numstr.length - 1) == "/") {
				Errmsg = " \u5206\u6570\u8bbe\u7f6e\u4e0d\u5408\u6cd5\u3002";
			} else {
      //2.��ĸ��һλ����Ϊ0
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
//����Ƿ���ĳ���ַ�
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
//�ѷ���ת��ΪС������
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
 * ���ڣ�2006-03-10
 * ���ߣ�heguoqiang
 */
/**
   * ����:ת����������Ĭ��Ϊ0����JS��ҳ������ʱ�����Ҫ
   * @param s ��Ҫ���������
    * @return ����
   */
function getFloat(s) {
	if (isNaN(s) || s.length == 0) {
		return 0;
	} else {
		return parseFloat(s);
	}
}
/**
   * ����:�������뺯��
   * @param f ��Ҫ���������
   * @param n ��Ҫ����С����λ����Ϊ0�������Ǳ���������λ��
    * @return ����
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
   * ����:У��������ַ��Ƿ�������ڹ淶 yyyy-mm-dd
   * @param str ��ҪУ�˵������ַ���
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
   * ����:У������Ķ��������е������Ƿ�������ڹ淶 yyyy-mm-dd
   * @param obj ��ҪУ�˵Ķ���
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
   * ����:�����ֵ�Ƿ���Ϲ涨�ĸ�ʽ
   * @param str ��У�˵�����
   * @param n ��ҪУ�˵�С��λ��
   * startNum ���ֿ�ʼֵ
   * endNum ���ֽ���ֵ
   * flag С��λ�Ƿ���벹��
   * @return boolean
   */
function formatNum(str, n, startNum, endNum, flag) {
	var flag1 = false; //�Ƿ�������ֹ淶
	var flag2 = false; //�Ƿ����С�淶
	var flag3 = false; //�Ƿ���ϴ�淶
	var flag4 = true; //�Ƿ�С��λ���벹��
	if (!isNaN(str)) {
		if (n == null) {//ֻ����֤�Ƿ�Ϊ�������
			flag1 = true;
		} else {
			if (n == 0) {//��֤�Ƿ�Ϊ����
				if (str.indexOf(".") < 0 && str.indexOf(" ") < 0) {
					flag1 = true;
				}
			} else {//��֤ΪС���������ֻҪ��������Ӧ��С��λ���Ϳ�����
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
   * ����:�����ֵ�Ƿ���Ϲ涨�ĸ�ʽ
   * @param obj ��У�˵Ķ���
   * @param n ��ҪУ�˵�С��λ��������Ϊ�գ�����ֻҪ�����־Ϳ���
   * startNum ���ֿ�ʼֵ������Ϊ�գ���ʾû�д�С����
   * endNum ���ֽ���ֵ������Ϊ�գ���ʾû�д�С����
   * flag С��λ�Ƿ���벹�룬true ��ʾ���벹��С��λ����false��Ϊ�գ���ʾ����Ҫ����
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
   * ����:����COOKIE���ƣ��õ�COOKIE��ֵ
   * @param name ��Ҫȡֵ��cookie����
   * @return string cookie��ֵ���޶�ӦCOOKIE�򷵻� "";
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
   * ����:�����ַ���������ȣ����������Կո���д�����ڶ�<select>�ؼ��и�ʽ����֮��
   * @param n ��Ҫ����ո��λ��
   * @return string �Կո���ɵ��ַ���
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
   * ����:�����ַ����ĳ��ȣ�������2λ��ʾ
   * @param s ��������ַ���
   * @return �ַ����ĳ���
   */
function len(s) {
	var ln = 0, i = 0;
	for (i = 0; i < s.length; i++) {
		c = s.charAt(i);
		if (c >= " " && c <= "~") { // ���е��ֽ�ASCII
			ln += 1;
		} else {
			ln += 2;
		}
	}
	return ln;
}
/**
   * ����:�ַ������չ涨�ĳ��Ƚضϣ����㳤���򲻽ض�
   * @param s ��������ַ���
   * @param xlen �ַ����ĳ���
   * @return string
   */
function cut(s, xlen) {
	var ln = 0, i = 0;
	var t = "";
	for (i = 0; i < s.length; i++) {
		c = s.charAt(i);
		if (c >= " " && c <= "~") { // ���е��ֽ�ASCII
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
   * ����:�ַ�����LN������ʾ������ӿո񣬶���Ľض�,�����...��β��
   * @param s ��������ַ���
   * @param ln �ַ����ĳ���
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
   * У���ַ��Ƿ�Ϊ��ĸ��������
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
   * У���ַ��Ƿ�Ϊ��ĸ��������
   * @param obj ��Ҫ��֤�Ķ���
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
   * У���ַ��Ƿ���˫�ֽڵ��ַ�
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
   * У���ַ��Ƿ���˫�ֽڵ��ַ�
   * @param obj����֤�Ķ���
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
   * У���ַ��Ƿ�Ϊ�ջ��߳����Ƿ�����Ҫ��
   * @param s ��У�˵��ַ���
   * @param minLen ��������ĳ��ȣ�����Ϊ��
   * @param maxLen ���ܳ����ĳ��ȣ�����Ϊ��
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
   * У���ַ��Ƿ�Ϊ�ջ��߳����Ƿ�����Ҫ��
   * @param obj ��У�˵Ķ���
   * @param minLen ��������ĳ��ȣ�����Ϊ��
   * @param maxLen ���ܳ����ĳ��ȣ�����Ϊ��
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
   * ȥ���ַ������߿ո�ĺ���
   * @param s ��������ַ���
   * @return ������Ϻ���ַ���
   */
function trim(s) {
	return rtrim(ltrim(s));
}
/**
   * ȥ���ַ�����߿ո�ĺ���
   * @param s ��������ַ���
   * @return ������Ϻ���ַ���
   */
function ltrim(s) {
	if (s == null || s.length == 0) {
		return "";
	}
	while (s.length > 0 && s.charAt(0) == " ") { //ȥ����߿ո�
		s = s.substring(1);
	}
	return s;
}
/**
   * ȥ���ַ����ұ߿ո�ĺ���
   * @param s ��������ַ���
   * @return ������Ϻ���ַ���
   */
function rtrim(s) {
	if (s == null || s.length == 0) {
		return "";
	}
	while (s.length > 0 && s.charAt(s.length - 1) == " ") {//ȥ���ұ߿ո�
		s = s.substring(0, s.length - 1);
	}
	return s;
}
/**
   * �����Ի�����ģʽ
   * @param ctrlobj ���������ݵĿؼ�����
   * @param url �������������õ�URL��ַ������http://ip:8080/web/
   * @param width,height �������ڵĿ�͸�
   * @return boolean(��������ֵ����Ϊtrue)
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
   * ������ͨ����ģʽ
   * @param url �������������õ�URL��ַ������http://ip:8080/web/
   * @param width,height �������ڵĿ�͸�
   * @return boolean(��������ֵ����Ϊtrue)
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
 ȡ��checkbox �� RADIO ѡ�еĸ���
  * @param obj checkbox����
   * @return num ����
 */
function getCheckedNum(obj) {
	var num = 0;
	if (obj.length > 1) {//�����������
		for (var i = 0; i < obj.length; i++) {
			if (true == obj[i].checked) {
				num++;
			}
		}
	} else {//�����������
		if (true == obj.checked) {
			num++;
		}
	}
	return num;
}
/**
 ȡ��checkbox �� RADIO ѡ�еĸ���
  * @param obj checkbox����
   * @return num ����
 */
function getCheckedNumAlert(obj, num) {
	if (typeof (obj) != "object") {//������ʹ��
		alert("\u65e0\u8bb0\u5f55\u6216\u8005\u5bf9\u8c61\u9519\u8bef\uff01");
		return false;
	}
	var num2 = getCheckedNum(obj);
	var flag = false;//�ж��Ƿ�������
	var flag2 = false;//�ж��Ƿ��д���
	if (obj.length > 1) {//�����������
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
   * ����:�ַ����滻����
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
   * ����:��SELECT ������ѡ�ѡ��
   * @param s
   * @return
   */
function selectAllOptions(obj) {
	if (typeof (obj) != "object") {//������ʹ��
		return false;
	}
	for (var i = 0; i < obj.length; i++) {
		obj[i].selected = true;
	}
	return true;
}
/**
   * ����:��SELECT��ѡ�е�optionֵ����
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
   * ����:  ����Ƿ�Ϊ���ֻ�С��
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
   * ����:  ����Ƿ�Ϊ��
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

	// ��������ѡ���
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

	// ��������ѡ���
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

	//���ݿ��λ
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


  //���ݿ��λ
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

	  // ����λ
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

  //����ⶨλ
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



//================����ʽͳһ�趨js begin: @confessor.w======================





/**
 *  ��ѯ���������ʽ�趨[[��������---->���ⲿ�{��]]
 *  @param: tableID ----����ID
 */

function setConditionTableStyle(tableID) {
	var table = document.getElementById(tableID);
	table.className="condition";
	var startColomn = "1";	//Ĭ����ʼ��
	var endColomn = "3";	//Ĭ�Ͻ�����
	var startRow = "0";		//Ĭ����ʼ��
	var endRow = table.rows.length;		//��ʵ������
	var columnNum = startColomn + ";" + endColomn;
	var rows = startRow + ";" + endRow;
	commonTableSet(tableID, columnNum, rows, "0");
}
/**
 * �����ϸ�����ʽ�趨[[��������---->���ⲿ�{��]]
 * @param: tableID ----����ID
 */
function setDetailTableStyle(tableID) {
	var table = document.getElementById(tableID);
	table.className="detail";
	setConditionTableStyle(tableID);
}
/**
 * ģ��Title��ʽ�趨[[��������---->���ⲿ�{��]]
 * @param: tableID ----����ID
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
 * ��ѯ����б���ʽ�趨[[��������---->���ⲿ�{��]]
 * @param: tableID ----����ID
 */
function setListTableStyle(tableID) {	
	var table = document.getElementById(tableID);
	table.className="list";
	listStyleSetNoAlternation(tableID);
}
/**
 * ͨ�ñ���ʽ�趨:����facadeģʽ��� [[��������---->���ⲿ�{��]]
 * @param tableID ��Ҫ�趨��ʽ�ı�id
 * @param style   ��Ҫ�趨����ʽ����
 * 						("condition"----������ѯ����ʽ;"detail"----��ϸҳ�����ʽ;
 * 						"title"----ҳ��title����ʽ;     "list"----��ѯ����б���ʽ)
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
	 * ��ѯ����б���ʽ����[[�Ȳ�����---->����js�Ȳ��{��]]
	 * �޼������ʽ
	 * @param tableId----���id(�ַ���)
	 * */
function listStyleSetNoAlternation(tableID) {	
	var table = document.getElementById(tableID);
	table.rows[0].style.cssText = "background-color:#A3C0EE;height:22px";
	var rowStyle_0 = "background-color:#ffffff;"	//��ʼ������ʽ
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
 * �趨ҳ����������������ʽ[[�Ȳ�����---->����js�Ȳ��{��]]
 */
function setInputStyle() {
	if (!styleSetComplete) {
		var inputs = document.getElementsByTagName("input");
		var textArea = document.getElementsByTagName("textarea");
		setElementCssStyle(inputs);
		setElementCssStyle(textArea);
		setSelectStyle();	//�趨��������ʽ
		styleSetComplete = true;
	}
}
/**
 * �趨ҳ�����ı������textarea����ʽ[[�Ȳ�����---->����js�Ȳ��{��]]
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
 * �趨ҳ������������ʽ[[�Ȳ�����---->����js�Ȳ��{��]]
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

//================����ʽͳһ�趨js end: @confessor.w======================

function selectORClearBox(_form,_setval){
	var isFirstRow = true;
   for( var i=0; i < _form.elements.length; i++ ){
        if (_form.elements[i].type == 'checkbox' ){        	
            _form.elements[i].checked = _setval;
            if(!isFirstRow){	//���Ʊ��title�в��ı���ɫ
            	changcol(_form.elements[i]);
            }
            isFirstRow = false;
        }
   }
}

/**
 * @author confessor.w
 * @version 1.0
 * ��֤ҳ���ڵ�ȫ�������ֶ�-->֧�ֶ��ı������,radio,checkbox��,select��������֤
 * Ҫ��ҳ������Ҫ������ֶδ洢��IDΪ"NotPermitEmptyIDS"�������ֶε�value��,
 * ��д��ʽΪ: "�ı������ֶ�������,�����ֶ�name,��󳤶�;�������ͱ����ֶ�������,�����ֶ�name;..."
 * @return booleanֵ-->true��ȫ�������ֶ��ѷ���Ҫ��;
 * 					   false���б����ֶδ�¼��
 * */
function validateNoEmpty(columns){
	var hiddenID = columns.length>0?columns:document.getElementById("NotPermitEmptyIDS").value;
	var str = hiddenID.length>0?hiddenID.split(";"):"";
	if(str!=""&&str.length>0){		
		for(var i=0,j=str.length;i<j;i++){
			var column = str[i].split(",");
			var temp =  document.getElementsByName(column[1])[0];
			var errMsg = column[0]+"����Ϊ��!";						
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
						alert(column[0]+"���Ȳ��ܳ���"+column[2]+"λ(ÿ�������ַ�ռ��λ)!");
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
 * �������ڸ�ʽ�����ַ���ת����Date����-----���ڱȽ������ַ�����С
 *	��ʽ��yyyy-�꣬MM-�£�dd-�գ�HH-ʱ��mm-�֣�ss-�롣
 *	����ʽ����дȫ������:yy-M-d���ǲ�����ģ����򷵻�null����ʽ��ʵ�����ݲ���Ҳ����null����
	Ĭ�ϸ�ʽ��yyyy-MM-dd HH:mm:ss,yyyy-MM-dd��
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
 * ��֤��ʼ������������ڵĴ�С��ϵ
 * @param startDateName  ҳ���ڵ���ʼ�����ֶ�����;
 *        endDateName    ҳ���ڽ�ֹ�����ֶ�����
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
			alert("��ʼ���ڲ��ܴ��ڽ�������!");
			start.focus();
		}
	}
	return valFlag;
}	

/*
���ܣ��ж��Ƿ�Ϊ����(��ʽ:yyyy��MM��dd��,yyyy-MM-dd,yyyy/MM/dd,yyyyMMdd)
��ʾ��Ϣ��δ�������������ڸ�ʽ����
ʹ�ã�f_check_date(obj)
���أ�bool
*/
function f_check_date(obj) {	
	obj = event.srcElement;	
	var date =  trim(obj.value);
	if(date.length==0){
		return true;
	}
	//var dtype = "()"//obj.eos_datatype;
	//var format = dtype.substring(dtype.indexOf("(") + 1, dtype.indexOf(")")); //
	var format ;	//���ڸ�ʽ
	var isNeedFormat = false;
    if(date.length>0&&date.indexOf('-')!=-1){
		format = "yyyy-MM-dd";	//���ڸ�ʽ
	}else if(date.length==8){
		format = "yyyyMMdd";
		isNeedFormat = true;
	}	
	
	var year, month, day, datePat, matchArray;
	if (/^(y{4})(-|\/)(M{1,2})\2(d{1,2})$/.test(format)) {
		datePat = /^(\d{4})(-|\/)(\d{1,2})\2(\d{1,2})$/;		
	} else {
		if (/^(y{4})(��)(M{1,2})(��)(d{1,2})(��)$/.test(format)) {
			datePat = /^(\d{4})��(\d{1,2})��(\d{1,2})��$/;
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
 * Ϊ���ڸ�ʽ�������������ڸ�ʽУ����Զ�ת��
 * @param dateStr  ֧��";"�ָ����ַ���    
 * @���÷�ʽ����</body>��</html>֮�����js�ű����á����磺attachDateFormat("start_date;end_date");
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
 * ����1������
 * ����2�����볤��У��
 * ����3������������ĸ���ٸ���
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
				msg="�������ٺ���"+pass_min_str+"����ĸ!";
			}
			if(haveNum==0){
				msg = "���밲ȫ�Բ��㣬���뺬�����֣�";
				}
			if(haveChr==0){
				msg = "���밲ȫ�Բ��㣬���뺬����ĸ��";
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