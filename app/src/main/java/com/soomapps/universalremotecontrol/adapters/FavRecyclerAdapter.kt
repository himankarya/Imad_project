package com.soomapps.universalremotecontrol.adapters
import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.soomapps.universalremotecontrol.*
import com.soomapps.universalremotecontrol.db.AppDatabase
import java.util.ArrayList
import com.soomapps.universalremotecontrol.dto.DataModel
import com.soomapps.universalremotecontrol.utils.ConnectionDetector
import kotlin.concurrent.thread
import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.soomapps.universalremotecontrol.db.MIGRATION_1_2
import com.soomapps.universalremotecontrol.db.MIGRATION_2_3


class FavRecyclerAdapter(private val items: ArrayList<DataModel>, private val context: Context) : RecyclerView.Adapter<FavRecyclerAdapter.MyViewHolder>() {

    private lateinit var roomDatabase: AppDatabase
    private var dbModel: ArrayList<DataModel>? = null

    private var adBRequest: AdRequest? = null
    private var mAdMobInterstitialAd: InterstitialAd? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.fav_list_items, parent, false)

        dbModel = ArrayList<DataModel>()
        //creating room database for favorite
        thread {
            roomDatabase = Room.databaseBuilder(context,
                    AppDatabase::class.java, "fav_db")
                    .fallbackToDestructiveMigration()
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .build()
            dbModel = roomDatabase.favoriteDao().fetchAllData() as ArrayList<DataModel>
        }
        adBRequest = AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                //.addTestDevice("0D18E7ADF186A5703273874B522EF74B")
                .build()
        mAdMobInterstitialAd = InterstitialAd(context)
        mAdMobInterstitialAd!!.adUnitId = context.getString(R.string.interstitial_full_screen)
        mAdMobInterstitialAd!!.loadAd(adBRequest)

        return MyViewHolder(v)
    }

    private val FADE_DURATION = 1000

    //view holder class
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var titleTV: TextView = view.findViewById<TextView>(R.id.titleTV) as TextView
        var imageView: TextView = view.findViewById<ImageView>(R.id.titleIV) as TextView
        var favIV: ImageView = view.findViewById<ImageView>(R.id.favIV) as ImageView

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//
        val c = items.get(position)
        thread {
            roomDatabase = Room.databaseBuilder(context,
                    AppDatabase::class.java, "fav_db")
                    .fallbackToDestructiveMigration()
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .build()
            dbModel = roomDatabase.favoriteDao().fetchAllData() as ArrayList<DataModel>
        }
        // holder.imageView.setImageResource(c.titleImg)
        holder.titleTV.text = c.title
//        holder.imageView.setImageResource(R.drawable.ic_tv)
//        holder.favIV.visibility=View.GONE

        holder.favIV.setTag(position)

        holder.favIV.setOnClickListener { v ->
            displayPopupWindow(v, c)
        }

        holder.titleTV.setOnClickListener { v ->

            if (ConnectionDetector.checkInternetConnection(context)) {
                if (mAdMobInterstitialAd!!.isLoaded) {
                    mAdMobInterstitialAd!!.show()
                }else{
                    mAdMobInterstitialAd!!.loadAd(adBRequest)
                    if ((dbModel!![position].totalFragments) > 1 && (dbModel!![position].totalFragments) != 0) {
                        val intent = Intent(context, MultipleRemoteActivity::class.java)
                        val dataOnThePosition = dbModel!![position]
                        intent.putExtra("Single", dataOnThePosition)
                        intent.putExtra("Position", position)
                        intent.putExtra("Object", dbModel)
                        context.startActivity(intent)
//                            Handler().postDelayed({
//
//                            }, 5)
                    } else if ((dbModel!![position].totalFragments) == 0) {
                        val intent = Intent(context, WifiTVRemoteActivity::class.java)
                        val dataOnThePosition = dbModel!![position]
                        intent.putExtra("Single", dataOnThePosition)
                        intent.putExtra("Position", position)
                        intent.putExtra("Object", dbModel)
                        context.startActivity(intent)

                    } else {
                        val intent = Intent(context, SingleRemoteActivity::class.java)
                        val dataOnThePosition = dbModel!![position]
                        intent.putExtra("Single", dataOnThePosition)
                        intent.putExtra("Position", position)
                        intent.putExtra("Object", dbModel)
                        context.startActivity(intent)
//                            Handler().postDelayed({
//
//                            }, 5)
                    }
                }

                mAdMobInterstitialAd!!.adListener = object : AdListener() {
                    override fun onAdLoaded() {
                        super.onAdLoaded()
                    }

                    override fun onAdFailedToLoad(p0: Int) {
                        super.onAdFailedToLoad(p0)
                    }

                    override fun onAdOpened() {
                        super.onAdOpened()
                    }

                    override fun onAdLeftApplication() {
                        super.onAdLeftApplication()
                    }

                    override fun onAdClosed() {
                        mAdMobInterstitialAd!!.loadAd(adBRequest)
                        if ((dbModel!![position].totalFragments) > 1 && (dbModel!![position].totalFragments) != 0) {
                            val intent = Intent(context, MultipleRemoteActivity::class.java)
                            val dataOnThePosition = dbModel!![position]
                            intent.putExtra("Single", dataOnThePosition)
                            intent.putExtra("Position", position)
                            intent.putExtra("Object", dbModel)
                            context.startActivity(intent)
//                            Handler().postDelayed({
//
//                            }, 5)
                        } else if ((dbModel!![position].totalFragments) == 0) {
                            val intent = Intent(context, WifiTVRemoteActivity::class.java)
                            val dataOnThePosition = dbModel!![position]
                            intent.putExtra("Single", dataOnThePosition)
                            intent.putExtra("Position", position)
                            intent.putExtra("Object", dbModel)
                            context.startActivity(intent)

                        } else {
                            val intent = Intent(context, SingleRemoteActivity::class.java)
                            val dataOnThePosition = dbModel!![position]
                            intent.putExtra("Single", dataOnThePosition)
                            intent.putExtra("Position", position)
                            intent.putExtra("Object", dbModel)
                            context.startActivity(intent)
//                            Handler().postDelayed({
//
//                            }, 5)
                        }
                    }
                }
            } else {
                if ((dbModel!![position].totalFragments) > 1 && (dbModel!![position].totalFragments) != 0) {
                    val intent = Intent(context, MultipleRemoteActivity::class.java)
                    val dataOnThePosition = dbModel!![position]
                    intent.putExtra("Single", dataOnThePosition)
                    intent.putExtra("Position", position)
                    intent.putExtra("Object", dbModel)
                    context.startActivity(intent)
//                    Handler().postDelayed({
//
//                    }, 5)
                } else if ((dbModel!![position].totalFragments) == 0) {
                    val intent = Intent(context, WifiTVRemoteActivity::class.java)
                    val dataOnThePosition = dbModel!![position]
                    intent.putExtra("Single", dataOnThePosition)
                    intent.putExtra("Position", position)
                    intent.putExtra("Object", dbModel)
                    context.startActivity(intent)

                } else {
                    val intent = Intent(context, SingleRemoteActivity::class.java)
                    val dataOnThePosition = dbModel!![position]
                    intent.putExtra("Single", dataOnThePosition)
                    intent.putExtra("Position", position)
                    intent.putExtra("Object", dbModel)
                    context.startActivity(intent)
//                    Handler().postDelayed({
//
//                    }, 5)
                }
            }
        }

        Log.d("TTTTTUUrUUI", c.title)
        //  setFadeAnimation(holder.itemView);
        // setScaleAnimation(holder.itemView)
    }
    // No of list items
    override fun getItemCount(): Int {
        return items.size
    }

    private fun displayPopupWindow(view: View, model: DataModel) {
        val popup = PopupWindow(context)
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout = inflater.inflate(R.layout.pop_up_layout, null)
        popup.contentView = layout
        popup.height = WindowManager.LayoutParams.WRAP_CONTENT
        popup.width = WindowManager.LayoutParams.WRAP_CONTENT
        popup.isOutsideTouchable = true
        popup.isFocusable = true
        popup.setBackgroundDrawable(BitmapDrawable())
        popup.showAsDropDown(view)
        val rec = layout.findViewById(R.id.rec) as TextView
        rec.setOnClickListener {
            popup.dismiss()
            thread {
                roomDatabase.favoriteDao().deleteRecord(model)
            }
//            val db = Databasehandlerclass(context)
//            db.removeSingleHistory(scanlist.getDate())
            dbModel!!.remove(model)
            updateReceiptsList(dbModel!!)
            notifyItemRangeChanged(0, dbModel!!.size)
        }
    }

    private fun updateReceiptsList(newdbModel: ArrayList<DataModel>) {

        dbModel!!.clear()
        dbModel!!.addAll(newdbModel)
        notifyDataSetChanged()
        context.startActivity(Intent(context,MyDeviceActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
        (context as Activity).finish()
    }

}