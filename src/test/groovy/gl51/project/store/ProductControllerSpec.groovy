package gl51.project.store

import io.micronaut.context.ApplicationContext
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.runtime.server.EmbeddedServer
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class ProductControllerSpec extends Specification {

    @Shared @AutoCleanup EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer)
    @Shared @AutoCleanup RxHttpClient client = embeddedServer.applicationContext.createBean(RxHttpClient, embeddedServer.getURL())

    void "create product -> product retrievable with get"() {
        setup:
        Product sentProduct = new Product(name: name, description: description, price: price, idealTemperature: idealTemperature)

        when:
        String id = client.toBlocking().retrieve(HttpRequest.POST('/product', sentProduct))
        Product getProduct = client.toBlocking().retrieve(HttpRequest.GET('/product/' + id), Argument.of(Product).type)

        then:
        id && id != ""
        getProduct != null
        getProduct.description == sentProduct.description
        getProduct.price == sentProduct.price
        getProduct.name == sentProduct.name
        getProduct.idealTemperature == sentProduct.idealTemperature


        where:
        name | description | price | idealTemperature
        "The Tasty Ball" | "The Tasty Ball is very tasty" | 666 | 90
        "Vat iz it" | "Viz iz eu produkte" | 36 | 3
    }

    void "update product -> product saved in db"() {
        setup:
        String id = client.toBlocking().retrieve(HttpRequest.POST('/product', new Product(name: name, description: description, price: price, idealTemperature: idealTemperature)))

        when:
        Product newProduct = new Product(id: id, name: name1, description: description1, price: price1, idealTemperature: idealTemperature1)
        client.toBlocking().retrieve(HttpRequest.PUT('/product/' + id, newProduct), Argument.of(HttpStatus).type)
        Product updatedProduct = client.toBlocking().retrieve(HttpRequest.GET('/product/' + id), Argument.of(Product).type)

        then:
        newProduct.description == updatedProduct.description
        newProduct.price == updatedProduct.price
        newProduct.name == updatedProduct.name
        newProduct.idealTemperature == updatedProduct.idealTemperature

        where:
        name | description | price | idealTemperature | name1 | description1 | price1| idealTemperature1
        "The Tasty Ball" | "The Tasty Ball is very tasty" | 666 | 90 | "name3" | "description3" | 35 | 32
        "Vat iz it" | "Viz iz eu produkte" | 36 | 3 | "name4" | "description4" | 455632 | 12
    }

    void "update with wrong id -> exception"() {
        setup:
        String id = client.toBlocking().retrieve(HttpRequest.POST('/product', new Product()))

        when:
        client.toBlocking().retrieve(HttpRequest.PUT('/product/blblbl', new Product()), Argument.of(HttpStatus).type)

        then:
        thrown HttpClientResponseException
    }

    void "delete product -> product deleted from db"() {
        setup:
        String id = client.toBlocking().retrieve(HttpRequest.POST('/product', new Product(name: name, description: description, price: price, idealTemperature: idealTemperature)))

        when:
        client.toBlocking().retrieve(HttpRequest.DELETE('/product/' + id))
        Product product = client.toBlocking().retrieve(HttpRequest.GET('/product/' + id), Argument.of(Product).type)

        then:
        thrown HttpClientResponseException
        !product

        where:
        name | description | price | idealTemperature
        "name1" | "desription1" | 4354 | 5
        "name2" | "desription2" | 13548 | 6
    }

    void "get product with non existing id -> NOT FOUND"() {
        when:
        Product product = client.toBlocking().retrieve(HttpRequest.GET('/product/blblbl'), Argument.of(Product).type)

        then:
        thrown HttpClientResponseException
        !product
    }

    void "getting all the products returns the full list"() {
        setup:
        Product firstProduct = new Product(name: "1", description: "1", price: 1, idealTemperature: 1)
        Product secondProduct = new Product(name: "2", description: "2", price: 2, idealTemperature: 2)
        String id1 = client.toBlocking().retrieve(HttpRequest.POST('/product', firstProduct))
        String id2 = client.toBlocking().retrieve(HttpRequest.POST('/product', secondProduct))
        firstProduct.id = id1
        secondProduct.id = id2

        when:
        List<Product> list = (List<Product>) client.toBlocking().retrieve(HttpRequest.GET('/product'), Argument.of(List).type)

        then:
        list.find {p -> p.id == id1}
        list.find {p -> p.id == id2}
    }
}
