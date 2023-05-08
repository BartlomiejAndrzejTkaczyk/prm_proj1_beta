package pl.edu.pjwstk.s22517.prm_proj1.adapters

import android.app.AlertDialog
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
import java.time.LocalDate


class TaskAdapter(
    private val db: Datasource,
    private val completeTaskTextView: TextView,
    private val taskProgress: ProgressBar
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(
        private val view: View,
        private val db: Datasource,
    ) : RecyclerView.ViewHolder(view){
        val checkTask: CheckBox = view.findViewById(R.id.checkbox_task)
        val tomorrowAlert: ImageView = view.findViewById(R.id.tomorrow_alert)
        val taskDescription: TextView = view.findViewById(R.id.task_description)

        fun bind(taskProgress: ProgressBar, completeTaskTextView: TextView, id: Int, abc: TaskAdapter) {

            checkTask.setOnCheckedChangeListener { _, isChecked ->
                db.getTask(id).isDone = isChecked

                taskProgress.incrementProgressBy(if (isChecked) 1 else -1)

                completeTaskTextView.text = view.context.getString(
                    R.string.done_to_undone_task,
                    db.loadTask().filter { it.isDone }.size,
                    db.size()
                )
            }


            taskDescription.setOnLongClickListener {
                AlertDialog.Builder(view.context).apply {
                    setTitle(R.string.delete_task)
                    setMessage(R.string.delete_task_message)
                    setPositiveButton(R.string.yes) { _, _ ->
                        db.removeById(id)
                        abc.notifyItemRemoved(adapterPosition)
                    }
                    setNegativeButton(R.string.no, null)
                    show()
                }
                return@setOnLongClickListener true
            }
        }

        fun unbind(taskProgress: ProgressBar, completeTaskTextView: TextView){
            taskProgress.incrementProgressBy(-1)
            completeTaskTextView.text = view.context.getString(
                R.string.done_to_undone_task,
                db.loadTask().filter { it.isDone }.size,
                db.size()
            )
        }

    }

    override fun onViewRecycled(holder: TaskViewHolder) {
        super.onViewRecycled(holder)
        holder.itemView.setOnLongClickListener(null)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.task_min, parent, false)

        return TaskViewHolder(adapterLayout, db)
    }

    override fun getItemCount(): Int = db.size()

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = db.getTask(position)
        val context = holder.itemView.context

        holder.bind(taskProgress, completeTaskTextView, task.id, this)

        if (task.deadline != null) {
            holder.taskDescription.text = context.getString(
                R.string.task_description_with_deadline,
                task.description,
                task.deadline.toString()
            )
        } else {
            holder.taskDescription.text = context.getString(
                R.string.task_description,
                task.description,
            )
        }

        holder.checkTask.isChecked = task.isDone

        val todayDate = LocalDate.now().plusDays(3)
        holder.tomorrowAlert.visibility =
            if (task.deadline == null || todayDate >= task.deadline) View.VISIBLE else View.INVISIBLE
    }

    override fun onViewDetachedFromWindow(holder: TaskViewHolder) {
        holder.unbind(taskProgress, completeTaskTextView)
        super.onViewDetachedFromWindow(holder)
    }

    override fun onViewAttachedToWindow(holder: TaskViewHolder) {
        super.onViewAttachedToWindow(holder)
    }
}