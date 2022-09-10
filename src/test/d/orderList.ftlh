<#import "parts/common.ftlh" as c>
<@c.page>
    <table class="table">
        <thead>
        <tr>
            <th>Дата заказа</th>
            <th>Номер заказа</th>
            <th>Пользователь</th>
            <th>Сумма заказа</th>
            <th>Статус заказа</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <#list ordersList as order>
            <tr>
                <th>${order.getDate()}</th>
                <th><a href="/orderList/${order.getID()}">${order.getID()}</a></th>
                <th>ID пользователя - ${order.getUser().getId()}
                    <br>Логин - ${order.getUser().getUsername()}
                    <br>ФИ - ${order.getUser().getLastName()} ${order.getUser().getFirstName()}
                </th>
                <th>${order.getTotalPrice()}</th>
                <th>${order.getStatus().name()}</th>
                <th>
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
                    <#if order.getStatus().ordinal() lt 2>
                        <a href="/order/cancel/${order.getID()}?profile=false&userId=${order.getUser().getId()}">Отменить</a>
                    </#if>
                </th>
            </tr>
        </#list>
        </tbody>
    </table>
</@c.page>