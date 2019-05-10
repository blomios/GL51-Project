package gl51.project.store

import gl51.project.store.exception.NotExistingProductException
import gl51.project.store.exception.WrongIdException

class MemoryProductStorage implements ProductStorage {

    private Map<String, Product> store = new HashMap<>()

    @Override
    Product getByID(String id) throws NotExistingProductException {
        if (!store.containsKey(id)) {
            throw new NotExistingProductException()
        }

        return store.get(id)
    }

    @Override
    List<Product> all() {
        return store.values().toList()
    }

    @Override
    String save(Product p) {
        p.id = UUID.randomUUID().toString()
        store.put(p.id, p)
        return p.id
    }

    @Override
    void update(String id, Product p) throws WrongIdException {
        if (p.id == null || id != p.id) {
            throw new WrongIdException()
        }

        store.put(id, p)
    }

    @Override
    void delete(String id) {
        store.remove(id)
    }
}
