<#import "parts/common.ftlh" as c>
<@c.page>
    <h2>Список заказов пользователя ${user.getUsername()}</h2>
    <a href="/orderList">К списку заказов</a>
    <#include "parts/order.ftlh">
</@c.page>