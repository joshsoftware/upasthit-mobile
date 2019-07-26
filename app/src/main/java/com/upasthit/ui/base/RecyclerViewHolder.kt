package com.upasthit.ui.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/*
* Base RecyclerView ViewHolder
* */
open class RecyclerViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)