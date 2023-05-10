package pl.edu.pjwstk.s22517.prm_proj1.interfaces

import pl.edu.pjwstk.s22517.prm_proj1.models.Task

interface Datasource {
    fun loadTask(): MutableList<Task>
    fun open(): Datasource
    fun deleteTask(id: Int?)
    fun getTask(id: Int): Task
    fun getAllDone(): MutableList<Task>
    fun size(): Int
    fun removeById(id: Int): Boolean
    fun saveTask(task: Task)
}