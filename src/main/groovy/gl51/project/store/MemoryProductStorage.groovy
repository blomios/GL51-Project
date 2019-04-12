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
    String save(Product p) {
        p.id = UUID.randomUUID().toString()
        store.put(p.id, p)
        return p.id
    }

    @Override
    void update(String id, Product p) {

    }

    @Override
    void delete(String id) {
        store.remove(id);
    }
}
