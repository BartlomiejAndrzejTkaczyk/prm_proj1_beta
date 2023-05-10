package pl.edu.pjwstk.s22517.prm_proj1.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import pl.edu.pjwstk.s22517.prm_proj1.dialog.DatePicker
import pl.edu.pjwstk.s22517.prm_proj1.databinding.FragmentAddTaskBinding
import pl.edu.pjwstk.s22517.prm_proj1.datasources.FakeTaskDatasource
import pl.edu.pjwstk.s22517.prm_proj1.interfaces.Datasource
import pl.edu.pjwstk.s22517.prm_proj1.interfaces.Navigable
import pl.edu.pjwstk.s22517.prm_proj1.interfaces.TimeNeeder
import pl.edu.pjwstk.s22517.prm_proj1.models.Task
import java.time.LocalDate

class AddTask : Fragment(), TimeNeeder {
    private lateinit var binding: FragmentAddTaskBinding
    private lateinit var db: Datasource
    private lateinit var deadlineView: TextView
    var _deadline: LocalDate? = null
        set(value) {
            field = value
            deadlineView.text = value.toString()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = FakeTaskDatasource()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentAddTaskBinding.inflate(inflater, container, false).also {
            binding = it
            this.deadlineView = binding.deadline
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addDeadline.setOnClickListener { _ ->
            val dialog = DatePicker()
            dialog.timeNeedFragment = this
            dialog.show(parentFragmentManager, "datePicker")
        }

        binding.save.setOnClickListener {
            val task = Task(
                title = binding.title.text.toString(),
                desc = binding.desc.text.toString(),
                isDone = false,
                deadline = this._deadline
            )
            db.saveTask(task)

            parentFragmentManager.popBackStack()
            (activity as? Navigable)?.navigate(Navigable.Destination.List)
        }
    }

    override fun sendTime(time: LocalDate) {
        _deadline = time
    }
}