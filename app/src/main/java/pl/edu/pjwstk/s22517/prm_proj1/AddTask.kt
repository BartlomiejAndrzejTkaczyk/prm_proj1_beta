package pl.edu.pjwstk.s22517.prm_proj1

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.edu.pjwstk.s22517.prm_proj1.databinding.FragmentAddTaskBinding
import pl.edu.pjwstk.s22517.prm_proj1.datasources.FakeTaskDatasource
import pl.edu.pjwstk.s22517.prm_proj1.interfaces.Datasource
import pl.edu.pjwstk.s22517.prm_proj1.interfaces.Navigable
import pl.edu.pjwstk.s22517.prm_proj1.models.Task
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

class AddTask : Fragment() {
    private lateinit var binding: FragmentAddTaskBinding
    private lateinit var db: Datasource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = FakeTaskDatasource()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentAddTaskBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.notDeadline.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.deadline.background = ColorDrawable(Color.parseColor("#FFFFFF"))
            } else {
                binding.deadline.background = ColorDrawable(Color.parseColor("#E0E0E0"))
            }
           binding.deadline.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

        binding.save.setOnClickListener {
            var deadline: LocalDate? = null

            if (binding.notDeadline.isChecked) {
                deadline = Date(binding.deadline.date)
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
            }

            val task = Task(
                description = binding.description.text.toString(),
                isDone = false,
                deadline = deadline
            )
            db.saveTask(task)

            parentFragmentManager.popBackStack()
            (activity as? Navigable)?.navigate(Navigable.Destination.List)
        }
    }

}