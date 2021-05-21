<#import "parts/common.ftlh" as c>
<@c.page>
    <h3>${username}</h3>
    ${message!" "}
    <form method="post">
        <div class="form-row">
            <div class="form-group col-md-6">
                <label for="inputEmail4">Email</label>
                <input type="email" class="form-control" id="inputEmail4" placeholder="Email" name="email"
                       value="${email!""}">
            </div>
            <div class="form-group col-md-6">
                <label for="inputPhone">Телефон</label>
                <input type="text" class="form-control  ${(phoneError??)?string('is-invalid', '')}" id="inputPhone"
                       placeholder="Phone" name="phone" value="${phone!""}">
                <#if phoneError??>
                    <div class="invalid-feedback mb-2">
                        ${phoneError}
                    </div>
                </#if>
            </div>
            <div class="form-group col-md-6">
                <label for="inputPassword4">Пароль</label>
                <input type="password" class="form-control" id="inputPassword4" placeholder="Password" name="password">
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <div><input class="btn btn-primary" type="submit" value="Сохранить"/></div>
    </form>

    <h2>Список заказов</h2>
    <table class="table">
    <thead>
    <tr>
        <td>Название книги</td>
        <td>Автор</td>
        <td>Цена</td>
        <td>Количество</td>
        <td></td>
        <td></td>
    </tr>
    </thead>
    <tbody>
    <#list orders as order>
        <tr>
        <td>${order.getId()!""}
        <#list order.getOrderLines() as o>
            <tr>
                <td>${o.getBook().getName()}</td>
                <td>${o.getBook().getAuthor()}</td>
                <td>${o.getBook().getPrice()}</td>
                <td>${o.getQuantity()}</td>
            </tr>
        </#list>
        </td>
        <td>Total price: ${order.getId()}</td>
        </tr>
    </#list>
    </tbody>
</@c.page>