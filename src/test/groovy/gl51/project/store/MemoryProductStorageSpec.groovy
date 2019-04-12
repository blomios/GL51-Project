package gl51.project.store

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
        all.find({p -> p.name == "blblbl"}).id != null
    }

    def "deleting a product will remove it from the list"(){
        setup:
        String testID = store.save(new Product(name: "thirdTest"))
        store.delete(testID)

        when:
        def all = store.all()

        then:
        all.find({p -> p.id == testID}) == null
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

    }

    def "getting a product by its id will return it if it does exist"() {

    }
}
