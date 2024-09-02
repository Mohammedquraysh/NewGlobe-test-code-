package com.bridge.androidtechnicaltest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bridge.androidtechnicaltest.R
import com.bridge.androidtechnicaltest.databinding.ItemPupilBinding
import com.bridge.androidtechnicaltest.db.Pupil
import com.bumptech.glide.Glide

class PupilAdapter(private val pupils: List<Pupil>) :
    RecyclerView.Adapter<PupilAdapter.PupilViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PupilViewHolder {
        val binding = ItemPupilBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return PupilViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PupilViewHolder, position: Int) {
        val pupil = pupils[position]
        holder.bind(pupil)
    }

    override fun getItemCount(): Int = pupils.size

    inner class PupilViewHolder(binding: ItemPupilBinding) : RecyclerView.ViewHolder(binding.root) {
        private val pupilName = binding.pupilName
        private val pupilId = binding.pupilId
        private val pupilCoordinates = binding.pupilLatLong
        private val pupilImage = binding.pupilImage
        fun bind(pupil: Pupil) {
            pupilName.text = pupil.name
            pupilId.text = pupil.pupilId.toString()
            pupilCoordinates.text = "${pupil.latitude}-${pupil.longitude}"
//            Glide.with(itemView.context).load(pupil.image).into(pupilImage)
        }
    }
}
