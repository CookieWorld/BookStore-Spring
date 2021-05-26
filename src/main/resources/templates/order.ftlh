<#import "parts/common.ftlh" as c>
<@c.page>
    <h2>Заказ №${orders.getId()}</h2>
    <a href="/orderList">Назад</a>
    <table class="table">
        <thead>
        <tr>
            <th>Дата заказа</th>
            <th>Пользователь</th>
            <th>Заказанные книги</th>
            <th>Цена</th>
            <th>Сумма заказа</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>${orders.getDate()}</td>
            <td>${orders.getUser().getUsername()}
            <br><a href="/orders/${orders.getUser().getId()}">Посмотреть все заказы пользователя</a>
            </td>
            <td>
                <#list orders.getOrderLines() as o>
                    <#if o.getBook()??>
                        <p>${o.getBook().getAuthor()}
                            <br><b>${o.getBook().getName()} - (${o.getQuantity()})</b>
                        </p>
                    <#else>
                        <p>
                            ${o.getName()}
                            <br>${o.getAuthor()}
                        </p>
                    </#if>
                </#list>
            </td>
            <td>
                <div class = "d-flex flex-column justify-content-start align-items-center">
                <#list orders.getOrderLines() as o>
                    <div class="d-flex flex-column justify-content-start">
                        <p>${o.getPrice()}</p>
                        <br>
                    </div>
                </#list>
                </div>
            </td>
            <td>${orders.getTotalPrice()}</td>
        </tr>
        </tbody>
    </table>
</@c.page>