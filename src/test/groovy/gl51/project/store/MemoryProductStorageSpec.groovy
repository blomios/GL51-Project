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
}
