package com.upasthit.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.atcoi.app.ui.base.RecyclerViewHolder


abstract class RecyclerBaseAdapter : RecyclerView.Adapter<RecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            RecyclerViewHolder(
                    DataBindingUtil.inflate<ViewDataBinding>(
                            LayoutInflater.from(parent.context),
                            viewType,
                            parent,
                            false
                    )
            )

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {

        getDataModel(position)
                ?.let {
                    //                    val bindingSuccess = holder.binding.setVariable(BR.dataModel, it)
//                    if (!bindingSuccess) {
//                        throw IllegalStateException("Binding ${holder.binding} viewModel variable name should be 'viewModel'")
//                    }
                }
    }

    override fun getItemViewType(position: Int) = getLayoutIdForPosition(position)

    abstract fun getLayoutIdForPosition(position: Int): Int

    abstract fun getDataModel(position: Int): Any?

    abstract fun <T> updateData(mData: List<T>)

}