package pl.edu.pjwstk.s22517.prm_proj1.adapters

import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.edu.pjwstk.s22517.prm_proj1.R
import pl.edu.pjwstk.s22517.prm_proj1.interfaces.Datasource
import pl.edu.pjwstk.s22517.prm_proj1.models.Task
import java.time.LocalDate
import java.time.temporal.ChronoUnit


class TaskAdapter(
    private val db: Datasource,
    private val completeTaskTextView: TextView,
    private val taskProgress: ProgressBar
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(
        private val view: View,
        private val db: Datasource,
    ) : RecyclerView.ViewHolder(view) {
        val checkTask: CheckBox = view.findViewById(R.id.checkbox_task)
        val tomorrowAlert: ImageView = view.findViewById(R.id.tomorrow_alert)
        val taskDescription: TextView = view.findViewById(R.id.task_description)


        fun bind(
            taskProgress: ProgressBar,
            completeTaskTextView: TextView,
            id: Int,
            adapter: TaskAdapter,
            posOnList: Int
        ) {
            checkTask.setOnCheckedChangeListener { _, isChecked ->
                db.getTask(id).isDone = isChecked
                val doneTasksCount =  db.getAllDone().size

                taskProgress.setProgress(doneTasksCount, true)

                completeTaskTextView.text = view.context.getString(
                    R.string.done_to_undone_task,
                    doneTasksCount,
                    db.size()
                )
            }


            taskDescription.setOnLongClickListener {
                AlertDialog.Builder(view.context).apply {
                    setTitle(R.string.delete_task)
                    setMessage(R.string.delete_task_message)
                    setPositiveButton(R.string.yes) { _, _ ->
                        adapter.notifyItemRemoved(id)
                        db.removeById(id)

                        val doneTasksCount =  db.loadTask().filter { it.isDone }.size
                        adapter.taskProgress.setProgress(doneTasksCount, true)
                        adapter.taskProgress.max = db.size()
                        completeTaskTextView.text = view.context.getString(
                            R.string.done_to_undone_task,
                            doneTasksCount,
                            db.size()
                        )
                    }
                    setNegativeButton(R.string.no, null)
                    show()
                }

                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        LayoutInflater.from(parent.context)
            .inflate(R.layout.task_min, parent, false)
            .also {
                return TaskViewHolder(it, db)
            }
    }

    override fun getItemCount(): Int = db.size()

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = db.loadTask()[position]
        val context = holder.itemView.context

        holder.bind(taskProgress, completeTaskTextView, task.id, this, position)
        holder.checkTask.isChecked = task.isDone


        if (task.deadline == null) {
            holder.taskDescription.text = context.getString(
                R.string.task_description,
                task.title,
            )
            return
        }

        val daysToDeadline = ChronoUnit.DAYS.between(LocalDate.now(), task.deadline)
        if (daysToDeadline < 0L) {
            holder.taskDescription.text = context.getString(
                R.string.task_description_after_deadline,
                task.title,
                (-1 * daysToDeadline).toString(),
            )
        } else if (daysToDeadline > 0L) {
            holder.taskDescription.text = context.getString(
                R.string.task_description_before_deadline,
                task.title,
                daysToDeadline.toString(),
            )
        } else {
            holder.taskDescription.text = context.getString(
                R.string.task_description_on_deadline,
                task.title,
            )
        }


        val todayDate = LocalDate.now().plusDays(3)
        holder.tomorrowAlert.visibility =
            if (task.deadline != null && todayDate >= task.deadline) View.VISIBLE else View.INVISIBLE
    }

}