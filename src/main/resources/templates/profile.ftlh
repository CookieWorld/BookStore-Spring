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
    <table class="table table-borderless table-hover">
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
                    <#else><th>${order.getDate()}</th>
                </#if>
                <td>${o.getBook().getName()}</td>
                <td>${o.getBook().getAuthor()}</td>
                <td>${o.getBook().getPrice()}</td>
                <td>${o.getQuantity()}</td>
            </tr>
            <#assign count = count + 1>
        </#list>
        <th>Total price: ${order.getTotalPrice()!""}</th>
    </#list>
    </tbody>
</@c.page>