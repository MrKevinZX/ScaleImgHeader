package com.bmsr.scaleheaderdemo

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_test.view.*

class TestAdapter(val mDatas : List<String>) : RecyclerView.Adapter<TestHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestHolder {
        return TestHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_test, parent, false))
    }

    override fun getItemCount(): Int {
        return mDatas.size
    }

    override fun onBindViewHolder(holder: TestHolder, position: Int) {
        holder.itemView.item_desc.setText(mDatas[position])
        holder.itemView.btn1.setOnClickListener {
            it.context.startActivity(Intent(it.context,ActivityB::class.java))}
        }
}




class TestHolder(conteiner:View) : RecyclerView.ViewHolder (conteiner)
