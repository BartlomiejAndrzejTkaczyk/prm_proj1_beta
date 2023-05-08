package pl.edu.pjwstk.s22517.prm_proj1


import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pl.edu.pjwstk.s22517.prm_proj1.interfaces.Navigable

class MainActivity : AppCompatActivity(), Navigable {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toDoListFragment = ToDoList()
        supportFragmentManager.beginTransaction()
            .add(R.id.container, toDoListFragment, toDoListFragment.javaClass.name)
            .commit()
    }

    override fun navigate(to: Navigable.Destination) {
        supportFragmentManager.beginTransaction().apply {
            when(to){
                Navigable.Destination.List -> {

                    replace(R.id.container, ToDoList(), ToDoList::class.java.name)
                }
                Navigable.Destination.Add -> {
                    replace(R.id.container, AddTask(), AddTask::class.java.name)
                    addToBackStack(AddTask::class.java.name)
                }
                Navigable.Destination.Edit -> TODO()
            }
        }.commit()
    }

}