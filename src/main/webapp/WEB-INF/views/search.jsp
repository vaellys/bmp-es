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
             jQuery('input[name="checname"]').removeAttr('checked');
           
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
    <!-- <form name="demoForm" method="post" action="" target=""> -->
  
    
 
    <div class="awp_search"  id="awpid">
     <form action='<spring:url value="/es/search.do" htmlEscape="true"/>' method="get">
        <!--查询输入框-->
        <table>
 
         <tr > 
            <td align="right"><div class="stextbg"><input type="text" class="stext" id="inaction" name="keywords" value="请输入要搜索的内容......" /></div></td>
            <td align="left"><input type="submit" class="sbtn" value=" "/></td>
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

 

        
      <div style="" id="contentdiv"> 
       <div class="awp_search_list">
          <c:forEach items="${esDtos}" var="dto">
          <dl style="border:0px;">
            <dt><a href="${pageContext.request.contextPath}/resources/doc/${dto.path}" target="_blank">${dto.name}</a></dt>
            <dd>${dto.content}</dd>
          </dl>
          </c:forEach>

           

 

          
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


 
 <!--  </form> -->
  </div>
  
</body>
</html>