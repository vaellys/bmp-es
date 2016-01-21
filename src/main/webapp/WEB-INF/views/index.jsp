<%@ page contentType="text/html; charset=utf-8" %>
<%@ page isELIgnored="false"%>
<html>
    <body>
        <div style="width:100%; text-align:center;">
            <form action="check.do" method="post"  >  
                
                <textarea name="docA" rows="10" cols="100">${docA}</textarea>
                <br/>
                <br/>
                <textarea name="docB" rows="10" cols="100">${docB}</textarea>
                <br/>
                <br/>
                  <input type="submit" value="检查"/>
            </form>
            相似度：${result}
        </div>
    </body>
</html>