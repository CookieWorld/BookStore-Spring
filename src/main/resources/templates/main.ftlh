<#import "parts/common.ftlh" as c>
<@c.page>
    <div class="form-row">
        <div class="form-group">
            <form method="get" action="/main" class="form-inline">
                <input class="form-control" type="text" name="filter" value="${filter!""}"
                       placeholder="Автор"/>
                <div class="input-group-append">
                    <button class="btn btn-outline-success ml-2" type="submit">Найти</button>
                </div>
            </form>
        </div>
    </div>
    <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false"
       aria-controls="collapseExample">
        Добавить новую книжку
    </a>
    <div class="collapse <#if book??>show</#if>" id="collapseExample">
        <div class="form-group mt-3">
            <form method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <input class="form-control mt-2 col-md-6 ${(nameError??)?string('is-invalid', '')}" type="text"
                           value="<#if book??>${book.name}</#if>" name="name" placeholder="Введите название книги"/>
                    <#if nameError??>
                        <div class="invalid-feedback mb-2">
                            ${nameError}
                        </div>
                    </#if>
                </div>
                <div class="form-group">
                    <input class="form-control col-md-6 ${(authorError??)?string('is-invalid', '')}" type="text"
                           value="<#if book??>${book.author}</#if>" name="author" placeholder="Автор"/>
                    <#if authorError??>
                        <div class="invalid-feedback mb-2">
                            ${authorError}
                        </div>
                    </#if>
                </div>
                <div class="form-group">
                    <input class="form-control col-md-6 ${(priceError??)?string('is-invalid', '')}" type="text"
                           value="<#if book??>1</#if>" name="price" placeholder="Цена"/>
                    <#if priceError??>
                        <div class="invalid-feedback mb-2">
                            ${priceError}
                        </div>
                    </#if>
                </div>
                <div class="form-group">
                    <input class="form-control col-md-6 ${(quantityError??)?string('is-invalid', '')}" type="text"
                           value="<#if book??>0</#if>" name="quantity" placeholder="Количество"/>
                    <#if quantityError??>
                        <div class="invalid-feedback mb-2">
                            ${quantityError}
                        </div>
                    </#if>
                </div>
                <div class="form-group">
                    <div class="custom-file col-md-6">
                        <input id="custom-file" type="file" name="file">
                        <label class="custom-file-label" for="custom-file">Choose file</label>
                    </div>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <div class="form-group">
                    <button class="btn btn-primary ml-3" type="submit">Добавить</button>
                </div>
            </form>
        </div>
    </div>
    <div class="row row-cols-1 row-cols-md-3 g-4">
    <#list books as book>
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
                        <button type="submit" class="btn btn-primary mt-3">Edit</button>
                    </div>
                </form>
            </div>
        </div>
    <#else>
        No books
    </#list>
</@c.page>