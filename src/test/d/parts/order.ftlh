<#include "security.ftlh">
<table class="table">
    <thead class="thead-light">
    <tr>
        <th>Дата заказа</th>
        <th>Название книги</th>
        <th>Автор</th>
        <th>Цена</th>
        <th>Количество</th>
        <th>Статус заказа</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <#list orders as order>
        <#assign count = 0>
        <#list order.getOrderLines() as o>
            <tr>
                <#if count lt 1>
                    <th scope="row" rowspan="${order.getOrderLines()?size}" class="align-middle">${order.getDate()}
                    </th>
                </#if>
                <#if o.getBook()??>
                    <td>${o.getBook().getName()}</td>
                    <td>${o.getBook().getAuthor()}</td>
                    <td>${o.getBook().getPrice()}</td>
                <#else>
                    <td>${o.getName()}</td>
                    <td>${o.getAuthor()}</td>
                    <td class="text-center">${o.getPrice()}</td>
                </#if>
                <td class="text-center">${o.getQuantity()}</td>
                <#if count lt 1>
                    <th rowspan="${order.getOrderLines()?size}"
                        class="text-center align-middle">${order.getStatus().name()}</th>
                </#if>
                <#if count lt 1>
                    <th rowspan="${order.getOrderLines()?size}" class="text-center align-middle">
                        <#if isAdmin || isManager>
                            <#if order.getStatus().ordinal() == 1>
                                <a data-toggle="collapse" href="#collapseExample">Отправлен</a>
                                <div class="collapse" id="collapseExample">
                                    <form method="post" action="/order/send">
                                        <label>
                                            Введите трек-код:</label>
                                        <br><input type="text" name="trackCode"/>
                                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                        <input type="hidden" name="id" value="${order.getID()}">
                                        <button class="btn btn-primary mt-2" type="submit">Отправить</button>
                                    </form>
                                </div>
                            </#if>
                        </#if>
                        <#if order.getStatus().ordinal() lt 2>
                            <#if order.getUser().getId() == user.getId()>
                                <a href="/order/cancel/${order.getID()}?profile=true">Отменить</a>
                            <#else>
                                <a href="/order/cancel/${order.getID()}?profile=false&userId=${order.getUser().getId()}">Отменить</a>
                            </#if>
                        </#if>
                        <#if order.getStatus().ordinal() == 2>
                            <#if order.getTrackCode()??>
                                Трек-код: ${order.getTrackCode()}
                                <br>
                            </#if>
                            <a href="/order/delivered/${order.getID()}">Заказ получен</a>
                        </#if>
                    </th>
                </#if>
            </tr>
            <#assign count = count + 1>
        </#list>
        <th>Сумма заказа: ${order.getTotalPrice()!""}</th>
    </#list>
    </tbody>
</table>