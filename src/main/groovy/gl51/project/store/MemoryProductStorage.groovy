package gl51.project.store

class MemoryProductStorage implements ProductStorage {

    private Map<String, Product> store = new HashMap<>()

    @Override
    Product getByID(String id) {
        return null
    }

    @Override
    List<Product> all() {
        return store.values().toList()
    }

    @Override
    void save(Product p) {
        store.put(p.id, p);
    }

    @Override
    void update(String id, Product p) {

    }

    @Override
    void delete(String id) {

    }
}
