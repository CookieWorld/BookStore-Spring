<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>
<@c.page>
<h2>Регистрация</h2>
${message!" "}
    <@l.registration "/registration" />
</@c.page>