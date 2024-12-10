package com.example.customvoca.model

/*
class DicListAdapter(val dicListViewModel: DicListViewModel) : RecyclerView.Adapter<DicListAdapter.ViewHolder>(){
    private var itemList = listOf<Dic>()
    private var isEditMode = false
    private lateinit var binding: DiclistItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vocablist_item, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.item_dic_name.text = item.name
        holder.item_dic_num.text = item.dic_id.toString() + ". "
        holder.item_btn_delete.visibility = if(isEditMode) View.VISIBLE else View.GONE
        holder.item_btn_edit.visibility = if(isEditMode) View.VISIBLE else View.GONE
        holder.item_background.setOnLongClickListener{
            toggleEditMode()
            true
        }
        holder.item_background.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("dic_id", item.dic_id.toString())
            it.findNavController().navigate(R.id.action_dicListFragment_to_dicFragment, bundle)
        }
        holder.item_btn_delete.setOnClickListener{
            dicListViewModel.deleteDic(item)
        }
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val item_dic_name = itemView.findViewById<TextView>(R.id.item_dic_name)
        val item_background = itemView.findViewById<ConstraintLayout>(R.id.item_background)
        val item_dic_num = itemView.findViewById<TextView>(R.id.item_dic_num)
        val item_btn_delete = itemView.findViewById<ImageButton>(R.id.item_btn_delete)
        val item_btn_edit = itemView.findViewById<ImageButton>(R.id.item_btn_edit)
    }
    override fun getItemCount(): Int {
        return itemList.count()
    }
    fun updateItem(items: List<Dic>){
        itemList = items
        notifyDataSetChanged()
    }
    fun toggleEditMode(){
        isEditMode = !isEditMode
        notifyDataSetChanged()
    }
}*/