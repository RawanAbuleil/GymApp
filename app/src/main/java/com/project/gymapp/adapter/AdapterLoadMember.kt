package com.project.gymapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.gymapp.R
import com.project.gymapp.databinding.AllMemberListResBinding
import com.project.gymapp.model.AllMember

class AdapterLoadMember(val arrayList:ArrayList<AllMember>):RecyclerView.Adapter<AdapterLoadMember.MyViewHolder>() {

    class MyViewHolder(val binding :AllMemberListResBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val binding = AllMemberListResBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder:MyViewHolder, position: Int) {
        with(holder){
            with(arrayList[position]){
                binding.txtAdapetName.text=this.firstname+"  "+ this.LastName
                binding.txtAdapterAge.text = "Age : "+this.age
                binding.txtAdapterWeight.text="Weight : "+this.weight
                binding.txtAdapterMobile.text="Mobile : "+this.mobile
                binding.txtAdress.text=this.address
                binding.txtExpiry.text="Epiry : "+this.expiryDate

            }
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

}