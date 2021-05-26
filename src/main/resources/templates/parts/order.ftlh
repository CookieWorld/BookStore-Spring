<table class="table table-hover">
    <thead class="thead-light">
    <tr>
        <th>Дата заказа</th>
        <th>Название книги</th>
        <th>Автор</th>
        <th>Цена</th>
        <th>Количество</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <#list orders as order>
        <#assign count = 0>
        <#list order.getOrderLines() as o>
            <tr>
                <#if count gte 1>
                    <th></th>
                <#else>
                    <th>${order.getDate()}</th>
                </#if>
                <#if o.getBook()??>
                    <td>${o.getBook().getName()}</td>
                    <td>${o.getBook().getAuthor()}</td>
                    <td>${o.getBook().getPrice()}</td>
                <#else>
                    <td>${o.getName()}</td>
                    <td>${o.getAuthor()}</td>
                    <td>${o.getPrice()}</td>
                </#if>
                <td>${o.getQuantity()}</td>
            </tr>
            <#assign count = count + 1>
        </#list>
        <th>Сумма заказа: ${order.getTotalPrice()!""}</th>
    </#list>
    </tbody>
</table>