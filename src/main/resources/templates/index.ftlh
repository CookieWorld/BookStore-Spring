<#import "parts/common.ftlh" as c>
<@c.page>
    <h2>Книжки:</h2>
    <div class="row row-cols-1 row-cols-md-3 g-4">
        <#list books as book>
            <#if book.getQuantity() != 0>
                <div class="col">
                    <div class="card h-100">
                        <#if book.filename??>
                            <img class="card-img-top" src="../img/${book.filename}" alt="Card image cap">
                        </#if>
                        <form method="post" action="/cart/addToCart" enctype="multipart/form-data">
                            <div class="card-body">
                                <h5 class="card-title">${book.name}</h5>
                                <p class="card-text">Автор: ${book.author}</p>
                                <ul class="list-group list-group-flush">
                                    <li class="list-group-item">Цена: ${book.price}</li>
                                    <label class="list-group-item">Количество:
                                        <input class="form-control col-md-4" type="number" name="quantity" min="1"
                                               max="${book.quantity}" value="1"/>
                                    </label>
                                </ul>
                                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                <input type="hidden" name="id" value="${book.getId()}">
                                <button type="submit" class="btn btn-primary mt-3">Добавить в корзину</button>
                            </div>
                        </form>
                    </div>
                </div>
            </#if>
        <#else>
            No books
        </#list>
    </div>
</@c.page>