package br.com.matheus.lima.trabalhofinalpospuc.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.matheus.lima.trabalhofinalpospuc.R
import br.com.matheus.lima.trabalhofinalpospuc.constants.ReportStatusEnum
import br.com.matheus.lima.trabalhofinalpospuc.databinding.ItemMyReportsListBinding
import br.com.matheus.lima.trabalhofinalpospuc.domain.model.MyReport

class MyReportsListAdapter(private val mItemClick: (report: MyReport) -> Unit) :
    ListAdapter<MyReport, RecyclerView.ViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<MyReport>() {
        override fun areItemsTheSame(oldItem: MyReport, newItem: MyReport): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MyReport, newItem: MyReport): Boolean {
            return oldItem == newItem
        }
    }

    class MyReportsViewHolder(private var mBinding: ItemMyReportsListBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

        companion object {
            fun from(parent: ViewGroup): MyReportsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemMyReportsListBinding.inflate(layoutInflater, parent, false)
                return MyReportsViewHolder(binding)
            }
        }

        fun bind(
            report: MyReport,
            itemClick: (report: MyReport) -> Unit
        ) {
            mBinding.tvReportTitle.text = report.title
            mBinding.tvReportSubtitle.text = report.description
            val statusIcon = when (report.status) {
                ReportStatusEnum.PENDING -> R.drawable.ic_pending
                ReportStatusEnum.IN_PROGRESS -> R.drawable.ic_in_progress
                ReportStatusEnum.FINISHED -> R.drawable.ic_check
                ReportStatusEnum.DENIED -> R.drawable.ic_denied
            }
            mBinding.ivReportStatus.setImageResource(statusIcon)
            mBinding.root.setOnClickListener { itemClick(report) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        MyReportsViewHolder.from(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let { report ->
            (holder as MyReportsViewHolder).bind(
                report
            ) { mItemClick(report) }
        }
    }
}
