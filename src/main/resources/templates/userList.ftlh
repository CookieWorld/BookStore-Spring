<#import "parts/common.ftlh" as c>

<@c.page>
    <h2>Список пользователей</h2>
    <table class="table">
        <thead>
        <tr>
            <th>ID</th>
            <th>Active</th>
            <th>Логин</th>
            <th>Имя</th>
            <th>Фамилия</th>
            <th>Телефон</th>
            <th>Почта</th>
            <th>Адрес</th>
            <th>Город</th>
            <th>Роль</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <#list users as user>
            <tr>
                <td>${user.getId()}</td>
                <td>
                    <#if user.isActive()>
                        YES
                        <#else>
                        NO
                    </#if>
                </td>
                <td>${user.username!"not found"}</td>
                <td>${user.firstName!"not found"}</td>
                <td>${user.lastName!"not found"}</td>
                <td>${user.phone!"not found"}</td>
                <td>${user.email!"not found"}</td>
                <td>${user.address!"not found"}</td>
                <td>${user.country!"not found"}</td>
                <td>
                    <#list user.roles as role>
                        ${role} <#sep>,
                    </#list>
                </td>
                <td>
                    <a href="/user/${user.id}">Edit</a>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
</@c.page>