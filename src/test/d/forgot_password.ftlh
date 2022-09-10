<#import "parts/common.ftlh" as c>
<@c.page>
    <div>
        <h2>Забыли пароль</h2>
    </div>
    <#if error??>
        <div>
            <p class="text-danger">${error}</p>
        </div>
    </#if>
    <#if message??>
        <div>
            <p class="text-warning">${message}</p>
        </div>
    </#if>
    <form action="/forgot_password" method="post">
        <div>
            <div><p>Мы отправим ссылку на восстановление пароля на ваш почтовый адрес</p></div>
            <div>
                <p>
                    <input type="email" name="email" class="form-control" placeholder="Введите вашу почту" required>
                </p>
                <p>
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                    <input type="submit" value="Отправить" class="btn btn-primary"/>
                </p>
            </div>
        </div>
    </form>
</@c.page>