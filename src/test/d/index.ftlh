<#import "parts/common.ftlh" as c>
<#import "parts/pager.ftlh" as p>
<@c.page>
    <h2>Книжки:</h2>
    <@p.pager url books/>
    <div class="row card-deck">
        <#list books.content as book>
            <#if book.getQuantity() != 0>
                <div class="col-sm-3" style="height: 710px">
                    <div class="card mb-4 ">
                        <#if book.filename??>
                            <img height="350px" class="card-img-top" src="../img/${book.filename}"
                                 alt="Card image cap">
                        </#if>
                        <form method="post" action="/cart/addToCart" enctype="multipart/form-data">
                            <div class="card-body" style="height: 330px">
                                <h5 class="card-title">${book.name}</h5>
                                <p class="card-text">Автор: ${book.author}</p>
                                Цена: ${book.getPriceToString()}
                                <p>Количество:
                                    <input class="form-control col-md-8" type="number" name="quantity" min="1"
                                           max="${book.quantity}" value="1"/>
                                </p>
                                <p class="text-center">
                                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                    <input type="hidden" name="id" value="${book.getID()}">
                                    <button type="submit" class="btn btn-primary mt-3">Добавить в корзину</button>
                                </p>
                            </div>
                        </form>
                    </div>
                </div>
            </#if>
        <#else>
            No books
        </#list>
    </div>
    <@p.pager url books/>
</@c.page>