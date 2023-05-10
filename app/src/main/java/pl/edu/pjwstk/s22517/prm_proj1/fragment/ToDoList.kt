package pl.edu.pjwstk.s22517.prm_proj1.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.edu.pjwstk.s22517.prm_proj1.R
import pl.edu.pjwstk.s22517.prm_proj1.adapters.TaskAdapter
import pl.edu.pjwstk.s22517.prm_proj1.databinding.FragmentToDoListBinding
import pl.edu.pjwstk.s22517.prm_proj1.datasources.FakeTaskDatasource
import pl.edu.pjwstk.s22517.prm_proj1.interfaces.Datasource
import pl.edu.pjwstk.s22517.prm_proj1.interfaces.Navigable


class ToDoList : Fragment() {
    private lateinit var binding: FragmentToDoListBinding
    private lateinit var db: Datasource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = FakeTaskDatasource()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentToDoListBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val doneTask = db.loadTask().filter { it.isDone }.size
        binding.apply {
            completeTask.text = resources.getString(
                R.string.done_to_undone_task,
                doneTask,
                db.size()
            )

            taskProgress.max = db.size()
            taskProgress.setProgress(0, true)

            toDoList.adapter = TaskAdapter(db, completeTask, taskProgress)
            toDoList.setHasFixedSize(true)

            addTaskBtn.setOnClickListener {
                (activity as? Navigable)?.navigate(Navigable.Destination.Add)
            }

        }
    }
}