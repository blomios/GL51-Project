package gl51.project.store

import io.micronaut.http.annotation.*

import javax.inject.Inject

@Controller("/product")
class ProductController {

    @Inject
    ProductStorage productStorage

    @Get("{id}")
    Product getByID(String id) {
        try {
            productStorage.getByID(id)
        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    @Get("/")
    List<Product> all() {
        try {
            productStorage.all()
        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    @Post("/")
    String create(@Body Product p) {
        try {
            productStorage.save()
        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    @Put("/{id}")
    void update(String id, @Body Product p) {
        try {
            productStorage.update(id, p)
        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    @Delete("/{id}")
    void delete(String id) {
        try {
            productStorage.delete(id)
        } catch (Exception e) {
            e.printStackTrace()
        }
    }
}
