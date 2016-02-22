<table border="1">

<#list rowModelList as rowModel>
    <tr>
        <#list rowModel.columnList as col>
            <td>${col}</td>
        </#list>
    </tr>
</#list>

</table>