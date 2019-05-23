package gl51.project.store

class ProductControllerSpec extends Specification {

    @Shared @AutoCleanup EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer)
    @Shared @AutoCleanup RxHttpClient client = embeddedServer.applicationContext.createBean(RxHttpClient, embeddedServer.getURL())

    void "test create/get fct"() {
        setup:
        Product sentProduct = new Product(name: name, description: description, price: price, idealTemperature: idealTemperature)

        when:
        String id = client.toBlocking().retrieve(HttpRequest.POST('/store/product', sentProduct))
        Product getProduct = client.toBlocking().retrieve(HttpRequest.GET('/store/product/'+id), Argument.of(Product).type)

        then:
        id != ""
        getProduct != null
        getProduct.equals(sentProduct)

        where:
        name | description | price | idealTemperature
        "name1" | "desription1" | 99905 | 15
        "name2" | "descritption2" | 560 | 3
    }

    void "test update fct"() {
        setup:
        String id = client.toBlocking().retrieve(HttpRequest.POST('/store/product', new Product(name: name, description: description, price: price, idealTemperature: idealTemperature)))

        when:
        Product newProduct = new Product(name: name1, description: description1, price: price1, idealTemperature: idealTemperature1)
        HttpStatus status = client.toBlocking().retrieve(HttpRequest.PATCH('/store/product/'+id, newProduct), Argument.of(HttpStatus).type)
        Product afterProduct = client.toBlocking().retrieve(HttpRequest.GET('/store/product/'+id), Argument.of(Product).type)

        then:
        status.equals(HttpStatus.OK)
        newProduct.equals(afterProduct)

        where:
        name | description | price | idealTemperature | name1 | description1 | price1| idealTemperature1
        "name1" | "desription1" | 562 | 32 | "name3" | "description3" | 35 | 32
        "name2" | "desription2" | 2000 | 5 | "name4" | "description4" | 455632 | 12
    }

    void "test delete fct"() {
        setup:
        String id = client.toBlocking().retrieve(HttpRequest.POST('/store/product', new Product(name: name, description: description, price: price, idealTemperature: idealTemperature)))

        when:
        HttpStatus status = client.toBlocking().retrieve(HttpRequest.DELETE('/store/product/'+id), Argument.of(HttpStatus).type)
        Product product = client.toBlocking().retrieve(HttpRequest.GET('/store/product/'+id), Argument.of(Product).type)

        then:
        status.equals(HttpStatus.OK)
        thrown HttpClientResponseException
        product == null

        where:
        name | description | price | idealTemperature
        "name1" | "desription1" | 4354 | 5
        "name2" | "desription2" | 13548 | 6
    }
}
