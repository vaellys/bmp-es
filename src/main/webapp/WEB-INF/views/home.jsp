<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<HEAD>
    <META http-equiv=Content-Type content="text/html; charset=utf-8">

<link id="skinb" href="${pageContext.request.contextPath}/resources/style/awp_base.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resources/style/selectCss.css" rel="stylesheet" type="text/css" />

 
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.6.min.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib.js"></script>



    
    <title>原型示例</title>
    
    <SCRIPT LANGUAGE="JavaScript">



    jQuery.noConflict();
    (function($){ 
      $(function(){
        $(document).ready(function(){   


            $('input[name="checname"]').removeAttr('checked');
           
            $('input#inaction').focus(function(){ if($(this).attr('value')=='请输入要搜索的内容......'){$(this).removeClass().addClass('stextin').attr('value','');$(this).parent().removeClass().addClass('stextinbg');} }).blur(function(){ if($(this).attr('value')==''){ $(this).removeClass().addClass('stext').attr('value','请输入要搜索的内容......');$(this).parent().removeClass().addClass('stextbg'); }});
            
            //查询按钮静态动作，程序页面不需要下段
            /*
            $('input.sbtn').click(function(){
                var sk = $('input#inaction').attr('value');
                if(sk==undefined || sk=='请输入要搜索的内容......'){
                    $('.searchtip').show().html('抱歉，您还未输入查询内容');
                    $('.awp_search_list').hide();
                }else{
                    $('.awp_search_list').show();
                    $('.searchtip').hide();
                }
            });  */
            
        });
    // All End
      });
    })(jQuery);


   function buttonFun2(){
       jQuery('input[name="checname"]').removeAttr('checked');
       var  s = jQuery('#searchG');
        //jQuery('#searchG').show();
       //alert(s.is(":visible"));
       if(s.is(":visible")){
            jQuery('#searchG').hide();
       }else{
            jQuery('#searchG').show();
       }
      
   }



   function checkclick(val){
      if("1"==val){
        jQuery('#ch1').attr('checked','checked');
        jQuery('#ch2').attr('checked','checked');
        jQuery('#ch5').attr('checked','checked');
      }else if("2"==val){
        jQuery('#ch2').attr('checked','checked');
        jQuery('#ch3').attr('checked','checked');
        jQuery('#ch4').attr('checked','checked');
      }else if("3"==val){
        jQuery('#ch1').attr('checked','checked');
        jQuery('#ch2').attr('checked','checked');
        jQuery('#ch3').attr('checked','checked');
      }else if("4"==val){
        jQuery('#ch1').attr('checked','checked');
        jQuery('#ch2').attr('checked','checked');
      }else{
        jQuery('#ch2').attr('checked','checked');
        jQuery('#ch4').attr('checked','checked');
        jQuery('#ch4').attr('checked','checked');
      }
   }
    

 
            
 
</SCRIPT>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/basefunc.js"></script>
<style>
.input_label{clear:both;}
.input_label input,.input_label label{float:left;height:34px;line-height:34px;margin-right:5px;}
.input_label label{margin-right:10px;}
</style>
</HEAD>

<body>
 <!-- 系统总体框架，占页面宽度的98％ -->
  <div id='content'>
<!--     <form name="demoForm" method="post" action="" target=""> -->
  
    
 
    <div class="awp_search"  id="awpid">
        <form action='<spring:url value="/es/search.do" htmlEscape="true"/>' method="get">
        <!--查询输入框-->
        <table>
 
         <tr > 
            <td align="right"><div class="stextbg"><input type="text" class="stext" id="inaction" name="keywords" value="请输入要搜索的内容......" /></div></td>
            <td align="left"><input type="submit" class="sbtn" value=""/></td>
           <!--  <td align="left"><input type="button" class="sbtn1" value=" " name=""  onclick="buttonFun2();"/></td> -->
          </tr>
         </form>  
        </table>

        <table  class="11111 input_label" style="display:none;width:700px;"  id="searchG">
            <tr height="20px;">
                 <td>搜索类型：</td>  
                 <td style="height:20px;">
                    <input type="checkbox" name="checname" onclick="checkclick(this.value);" value="1"></input><label>全文搜索</label> 
                    <input type="checkbox" name="checname" onclick="checkclick(this.value);" value="2"></input><label>通配符搜索</label>
                    <input type="checkbox" name="checname" onclick="checkclick(this.value);" value="3"></input><label>范围搜索</label>  
                    <input type="checkbox" name="checname" onclick="checkclick(this.value);" value="4"></input><label>模糊搜索</label> 
                    <input type="checkbox" name="checname" onclick="checkclick(this.value);" value="5"></input><label>前缀搜索</label> 
                 </td>
              <tr>

              <tr height="20px;">
                 <td>数据来源：</td>  
                 <td style="height:20px;">
                    <input type="checkbox"  name="checname" id="ch1"></input><label>英国财政部</label> 
                    <input type="checkbox"  name="checname" id="ch2"></input><label>美国财政部</label>
                    <input type="checkbox"  name="checname" id="ch3"></input><label>欧盟理事会</label>  
                    <input type="checkbox"  name="checname" id="ch4"></input><label>联合国制裁部</label> 
                    <input type="checkbox"  name="checname" id="ch5"></input><label>香港金管局</label> 
                 </td>
              <tr>
        </table>
    </div>

 

        
      <div style="display:none;" id="contentdiv"> 
       <div class="awp_search_list">

          <dl style="border:0px;">
            <dt><a href="classification_disp_organ_2.html">Europ<font color='red'>e</font>an Union 欧盟制裁名单，匹配度：95%</a></dt>
            <dd>北京能宝贸易中心，北京市企业信用网 ，110108005240143 ,海淀分局,  </br> 当事人：北京能宝贸易中心 住 所：北京市海淀区北土城西路103号A座1... </dd>
          </dl>

          <dl style="border:0px;">
            <dt><a href="full_disp_2.html">Europ<font color='red'>e</font>an Union 欧盟制裁名单，匹配度：95%</a></dt>
            <dd>European Union 欧盟制裁名单，个人、团体和实体受欧盟金融制裁的综合名单  </br> Tanchon Commercial Bank  ...</dd>
          </dl>

          <dl style="border:0px;">
            <dt><a href="classification_disp_person_2.html">UK Her Maj<font color='red'>e</font>sty''s Tr<font color='red'>e</font>asury 英国财政部制裁名单，匹配度：90%</a></dt>
            <dd>UK Her Majesty''s Treasury 英国财政部制裁名单 ,</br> The Cuban government and official media publicly condemned acts of terrorism by al-Qa’ida and affiliates, </br>while at the same time remaining critical of the U.S. approach to combating international terrorism...</dd>
          </dl>

          <dl style="border:0px;">
            <dt><a href="classification_disp_organ_2.html">Unit<font color='red'>e</font>d Nations 联合国制裁名单，匹配度：99%</a></dt>
            <dd>United Nations 联合国制裁名单 ,</br>The Organization of Al-Qaida in the Islamic Maghreb (alias (a) Al Qada au Maghreb islamique (AQMI),</br> (b) LeGroupe Salafiste pour la Prédication et le Combat (GSPC), (c) Salafist Group For Call and Combat. </br>Other infor-mation: ...</dd>
          </dl>

          

          <dl style="border:0px;">
            <dt><a href="full_disp_1.html">HKMA香港金管局制裁名单，匹配度：95%</a></dt>
            <dd>HKMA香港金管局制裁名单，北京市企业信用网，香港斯迪有限公司北京代表处  </br> 市局 ...</dd>
          </dl>

           

 

          
       </div>
    



     <div class='awp_page_bottom' >
        <!--首页p_first，上一页p_prev；首页不可点击样式p_first01，上一页不可点击样式p_prev01-->
        <span class="p_first01"></span>
        <span class="p_prev01"></span>
        <span></span>
        <span class="p_text">
            共 <strong>5</strong> 条记录 共 <strong>1</strong> 页 当前第 <strong>1</strong> 页
        </span>
        <span></span>
        <!--尾页p_last，下一页p_next；尾页不可点击样式p_last01，下一页不可点击样式p_next01-->
        <span class="p_next"></span>
        <span class="p_last"></span>
        <span class="p_select">
            跳到
            <select onchange="goPage()" name="intPage">
              <option selected="" value="1">1</option>
            </select>
        </span>
      </div>

    </div>

            <SCRIPT LANGUAGE="JavaScript">
            function buttonFun(){
                //jQuery('#contentdiv').show();
                document.getElementById('contentdiv').style.display = '';
                
            }
            </SCRIPT>


 
<!--   </form> -->
  </div>
  
</body>
</html>