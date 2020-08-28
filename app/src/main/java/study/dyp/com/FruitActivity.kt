package study.dyp.com

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_fruit.*
import study.dyp.com.entity.Fruit

class FruitActivity : AppCompatActivity() {
    val fruits = listOf(
        Fruit(R.drawable.apple, R.string.key_apple),
        Fruit(R.drawable.cherry, R.string.key_cherry),
        Fruit(R.drawable.grape, R.string.key_grape),
        Fruit(R.drawable.orange, R.string.key_orange),
        Fruit(R.drawable.peach, R.string.key_peach)
    )

    val fruitList = ArrayList<Fruit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fruit)

        initFruit()

        rv_main.adapter = FruitAdapter(this, fruitList)
        rv_main.layoutManager = GridLayoutManager(this, 2)
    }

    private fun initFruit() {
        fruitList.clear()
        repeat(50) {
            val index = (fruits.indices).random()
            fruitList.add(fruits[index])
        }
    }
}

class FruitAdapter(private val context: Context, private val list: List<Fruit>) :
    RecyclerView.Adapter<FruitAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivIcon: ImageView = itemView.findViewById(R.id.iv_icon)
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.fruit_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        Glide.with(context).load(item.icon).into(holder.ivIcon)
        holder.tvName.setText(item.name)
    }
}