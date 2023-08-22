package com.example.wonderwoman.post

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.wonderwoman.R
import com.example.wonderwoman.model.post.GetSelectedBuilding

class BuildingBtnRecyclerAdapter(
    private val buildingList: List<String>,
    val context: Context,
    private val getSelectedBuilding: GetSelectedBuilding
): RecyclerView.Adapter<BuildingBtnRecyclerAdapter.CustomViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomViewHolder {
        //뷰홀더에 전달
        val view = LayoutInflater.from(parent.context).inflate(R.layout.building, parent, false)
        Log.d("adapter", buildingList.toString())
        return CustomViewHolder(view)
    }

    //각 아이템들에 대한 매칭이 이뤄지는 곳
    override fun onBindViewHolder(
        holder: CustomViewHolder,
        position: Int
    ) {
        holder.building.text = buildingList[position]

        holder.building.setOnClickListener{
            //postactivity로 선택한 빌딩 보내주
            getSelectedBuilding.selectedBuilding(holder.building.text.toString())
            Log.d("adapter", holder.building.text.toString())

//            Intent(context, PostActivity::class.java).apply {
//                putExtra("data", holder.building.text)
//                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            }.run { context.startActivity(this) }
        }
    }

    override fun getItemCount(): Int {
        return buildingList.size
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var building = itemView.findViewById<Button>(R.id.buildingBtn)
    }

}