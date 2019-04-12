package gl51.project.store


interface ProductStorage {

    /**
     * Get and return the product with a given id
     * @param id the id of the wanted product
     * @return the product with the given id
     */
    Product getByID(String id);

    /**
     * Get and return all the products from the store
     * @return a list with all the products of the store
     */
    List<Product> all();

    /**
     * Store a new product
     * @param product the product store
     * @return the id of the product
     */
    String save(Product p);

    /**
     * Update a product in the store
     * @param id the id of the product to update
     * @param product the product to update
     */
    void update(String id, Product p);

    /**
     * Delete a product from the store
     * @param id the id of the product to delete
     */
    void delete(String id);
}