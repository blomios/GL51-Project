package gl51.project

import gl51.project.Student
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("/student")
class StudentController {

    @Get("/")
    List<Student> index() {
        [new Student(firstName: "José", lastName: "Paldire")]
    }
}