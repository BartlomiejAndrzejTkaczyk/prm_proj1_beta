package pl.edu.pjwstk.s22517.prm_proj1.datasources

import pl.edu.pjwstk.s22517.prm_proj1.interfaces.Datasource
import pl.edu.pjwstk.s22517.prm_proj1.models.Task
import java.time.LocalDate

class FakeTaskDatasource : Datasource {

    override fun open(): Datasource {
        return getInstance()
    }

    override fun deleteTask(id: Int?) {
        if(id != null) tasks.removeIf { it.id == id }
    }

    override fun loadTask(): MutableList<Task> {
        tasks.sortBy { it.deadline }
        return tasks
    }

    override fun getAllDone(): MutableList<Task> {
        return tasks.filter { it.isDone }.toMutableList()
    }

    override fun getTask(id: Int): Task {
        return tasks.first { it.id == id }
    }

    override fun size(): Int {
        return tasks.size
    }

    override fun removeById(id: Int): Boolean {
        return tasks.removeIf { it.id == id }
    }

    override fun saveTask(task: Task) {
        tasks.add(task.also { it.id = currentId++ })
    }

    companion object {
        private var currentId = 0
        val tasks = mutableListOf(
            Task(currentId++, "Complete math homework", "Finish exercises 1-5 in Chapter 3", false, LocalDate.of(2023, 5, 12)),
            Task(currentId++, "Buy groceries", "Milk, eggs, bread, and vegetables", true, LocalDate.of(2023, 5, 7)),
            Task(currentId++, "Schedule dentist appointment", "Call Dr. Smith's office", false, LocalDate.of(2023, 5, 20)),
            Task(currentId++, "Finish coding project", "Refactor code and write documentation", false, LocalDate.of(2023, 5, 31)),
            Task(currentId++, "Call parents", "Discuss plans for the weekend", true, LocalDate.of(2023, 5, 15)),
//            Task(currentId++, "Go for a run", "Run 5 miles in the park", false, null),
//            Task(currentId++, "Organize closet", "Sort clothes and donate unused items", false, LocalDate.of(2023, 5, 10)),
//            Task(currentId++, "Pay rent", "Transfer funds to landlord's account", true, LocalDate.of(2023, 5, 28)),
//            Task(currentId++, "Plan vacation", "Research destinations and book flights", false, LocalDate.of(2024, 6, 1)),
//            Task(currentId++, "Read book", "Finish reading 'The Great Gatsby'", false, LocalDate.of(2023, 5, 21)),
//            Task(currentId++, "Clean bathroom", "Scrub the tiles and sanitize surfaces", true, LocalDate.of(2023, 5, 8)),
//            Task(currentId++, "Take dog for a walk", "Walk for 30 minutes around the neighborhood", false, LocalDate.of(2023, 5, 14)),
//            Task(currentId++, "Write blog post", "Topic: Tips for effective time management", false, null),
//            Task(currentId++, "Watch movie", "Watch 'The Shawshank Redemption'", true, LocalDate.of(2023, 5, 16)),
//            Task(currentId++, "Prepare for presentation", "Create slides and rehearse speech", false, LocalDate.of(2023, 6, 5))
        )
        private var instance: FakeTaskDatasource? = null

        fun getInstance(): FakeTaskDatasource {
            if (instance == null) {
                instance = FakeTaskDatasource()
            }
            return instance!!
        }
    }
}