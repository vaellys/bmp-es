<%@ page contentType="text/html; charset=utf-8" %>
<%@ page isELIgnored="false"%>
<html>
    <body>
        <div style="width:100%; text-align:center;">
            <form action="upload.do" method="post"  enctype="multipart/form-data" >  
                
                <input name="fileA" type="file" />
                <br/>
                <br/>
                <input name="fileB" type="file" />
                <br/>
                <br/>
                  <input type="submit" value="检查"/>
            </form>
            <font color="red">相似度：${result}</font>
        </div>
    </body>
</html>