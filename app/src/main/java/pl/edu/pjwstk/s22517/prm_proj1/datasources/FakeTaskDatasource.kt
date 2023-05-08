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
        return tasks
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
        private val tasks = mutableListOf(
            Task(currentId++, "Complete math homework", false, LocalDate.of(2023, 5, 12)),
            Task(currentId++, "Buy groceries", true, LocalDate.of(2023, 5, 7)),
            Task(currentId++, "Schedule dentist appointment", false, LocalDate.of(2023, 5, 20)),
            Task(currentId++, "Finish coding project", false, LocalDate.of(2023, 5, 31)),
            Task(currentId++, "Call parents", true, LocalDate.of(2023, 5, 15)),
            Task(currentId++, "Go for a run", false, null),
            Task(currentId++, "Organize closet", false, LocalDate.of(2023, 5, 10)),
            Task(currentId++, "Pay rent", true, LocalDate.of(2023, 5, 28)),
            Task(currentId++, "Plan vacation", false, LocalDate.of(2024, 6, 1)),
            Task(currentId++, "Read book", false, LocalDate.of(2023, 5, 21)),
            Task(currentId++, "Clean bathroom", true, LocalDate.of(2023, 5, 8)),
            Task(currentId++, "Take dog for a walk", false, LocalDate.of(2023, 5, 14)),
            Task(currentId++, "Write blog post", false, null),
            Task(currentId++, "Watch movie", true, LocalDate.of(2023, 5, 16)),
            Task(currentId++, "Prepare for presentation", false, LocalDate.of(2023, 6, 5))
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