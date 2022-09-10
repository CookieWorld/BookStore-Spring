<#import "parts/common.ftlh" as c>
<@c.page>
    <div>
        <h2>Восстановление пароля</h2>
    </div>
    <#if message??>
        <div>
            <p class="text-warning">${message}</p>
        </div>
    </#if>
    <form action="/reset_password" method="post">
        <#if token??>
            <input type="hidden" name="token" value="${token}">
        </#if>
        <div>
            <div>
                <#if error??>
                    <div>
                        <p class="text-danger">${error}</p>
                    </div>
                </#if>
                <p>
                    <input type="password" name="password" class="form-control" placeholder="Введите ваш пароль" required>
                </p>
                <p>
                    <input type="password" name="password2" class="form-control" placeholder="Подтвердите ваш пароль" required>
                </p>
                <p>
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                    <input type="submit" value="Отправить" class="btn btn-primary"/>
                </p>
            </div>
        </div>
    </form>
</@c.page>