package gl51.project.store

import gl51.project.store.exception.NotExistingProductException
import gl51.project.store.exception.WrongIdException
import spock.lang.Specification

class MemoryProductStorageSpec extends Specification {

    ProductStorage store = new MemoryProductStorage()

    def "empty storage returns empty list"() {
        expect:
        store.all() == []
    }

    def "adding a product returns the product in the list"() {
        setup:
        String name = "dat iste goude producte"
        store.save(new Product(name: name))

        when:
        def all = store.all()

        then:
        all.size() == 1
        all.first().name == name
    }

    def "adding a product will generate a new id"() {
        setup:
        store.save(new Product(name: "blblbl"))

        when:
        def all = store.all()

        then:
        all.find({p -> p.name == "blblbl"}).id
    }

    def "deleting a product will remove it from the list"(){
        setup:
        String testID = store.save(new Product(name: "thirdTest"))
        store.delete(testID)

        when:
        def all = store.all()

        then:
        !all.find({p -> p.id == testID})
    }

    def "updating a product which has a different id than the given one will throw a WrongIdException"() {
        setup:
        String pID = store.save(new Product())
        Product p = new Product(id: "blblbl")

        when:
        store.update(pID, p)

        then:
        thrown WrongIdException
    }

    def "modifying a product will change it in the list"() {
        setup:
        String pName = "The Tasty Ball"
        String pDesc = "The tasty ball is very tasty"
        String pID = store.save(new Product(name: pName))
        Product newVersion = new Product(id: pID, name: pName, description: pDesc)

        when:
        store.update(pID, newVersion)

        then:
        Product p = store.all().first()
        p.id == pID
        p.name == pName
        p.description == pDesc
    }

    def "getting a product by its id will throw a NotExistingProductException if it does not exits"() {
        setup:
        String pID = "blblbl"

        when:
        store.getByID(pID)

        then:
        thrown NotExistingProductException
    }

    def "getting a product by its id will return it if it does exist"() {
        setup:
        Product p = new Product()
        String pID = store.save(p)

        when:
        Product pGet = store.getByID(pID)

        then:
        p == pGet
    }
}
