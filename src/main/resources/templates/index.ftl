<table border="1">

<#list rowModelList as rowModel>
    <tr>
        <#list rowModel.columnList as col>
            <td>${col}</td>
        </#list>
        <td>
            <#if rowModel.whereClause??>
                ${rowModel.whereClause}
            </#if>
        </td>
    </tr>
</#list>

</table>