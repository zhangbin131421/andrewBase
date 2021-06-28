package com.andrew.library.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.andrew.library.listener.MyOnClickListener
import com.orhanobut.logger.Logger
import java.util.*

abstract class AndrewRecyclerViewAdapter<T>(val mContext: Context) :
    RecyclerView.Adapter<BaseRecyclerViewHolder>() {

    abstract val layoutId: Int

    var mItemClickListener: MyOnClickListener<T>? = null
    var mItemLongClickListener: MyOnClickListener<T>? = null
    var arrayList = ArrayList<T>()
    protected var clickPosition = -1

    /**
     * @param holder
     * @param position
     * @param t        holder.getBinding().setVariable(BR.item, t);
     * holder.getBinding().executePendingBindings();
     */
    protected abstract fun mOnBindViewHolder(holder: BaseRecyclerViewHolder, position: Int, t: T)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder {
        return BaseRecyclerViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                layoutId,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: BaseRecyclerViewHolder, position: Int) {
        val t: T = arrayList[position]
        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener { v: View? ->
                clickPosition = position
                mItemClickListener?.onClick(t)
            }
        }
        if (mItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener { v: View? ->
                clickPosition = position
                mItemLongClickListener?.onClick(t)
                true
            }
        }
        mOnBindViewHolder(holder, position, t)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    open fun getItem(position: Int): T {
        return arrayList[position]
    }

    open fun addAll(list: List<T>?, isFirst: Boolean) {
        list?.let {
            if (isFirst) {
                arrayList.clear()
            }
            arrayList.addAll(it)
        }
    }

    open fun addAllNotify(list: List<T>?, isFirst: Boolean) {
        list?.let {
            if (isFirst) {
                arrayList.clear()
            }
            arrayList.addAll(it)
            notifyDataSetChanged()
        }

    }

    open fun add(t: T) {
        arrayList.add(t)
    }

    open fun addNotify(t: T) {
        arrayList.add(t)
        notifyDataSetChanged()
    }

    open fun updatePosition(position: Int, t: T) {
        arrayList[position] = t
    }

    open fun updatePosition(t: T) {
        updatePosition(clickPosition, t)
    }

    open fun updatePositionNotify(position: Int, t: T) {
        arrayList[position] = t
        notifyDataSetChanged()
    }

    open fun updatePositionNotify(t: T) {
        updatePositionNotify(clickPosition, t)
    }

    open fun clear() {
        arrayList.clear()
        notifyDataSetChanged()
    }


    open fun deleteNotify() {
        if (clickPosition > -1 && clickPosition < itemCount) {
            arrayList.removeAt(clickPosition)
            clickPosition = -1
            notifyDataSetChanged()
        } else {
            Logger.e("删除失败clickPosition=$clickPosition")
        }
    }
}