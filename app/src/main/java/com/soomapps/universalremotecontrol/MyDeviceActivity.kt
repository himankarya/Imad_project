package com.soomapps.universalremotecontrol

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.gms.ads.AdListener
import com.soomapps.universalremotecontrol.adapters.FavRecyclerAdapter
import com.soomapps.universalremotecontrol.db.AppDatabase
import com.soomapps.universalremotecontrol.utils.ConnectionDetector
import com.soomapps.universalremotecontrol.utils.RecyclerItemClickListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.soomapps.universalremotecontrol.db.MIGRATION_1_2
import com.soomapps.universalremotecontrol.db.MIGRATION_2_3
import com.soomapps.universalremotecontrol.dto.DataModel
import pl.droidsonroids.gif.GifImageView
import java.util.ArrayList
import kotlin.concurrent.thread

class MyDeviceActivity : AppCompatActivity() {

    // Ui variables
    //  private var mAdView: AdView? = null
    private var adBRequest: AdRequest? = null
    private var mAdMobInterstitialAd: InterstitialAd? = null
    private var recyclerView: RecyclerView? = null
    private var txt: TextView? = null
    private var add_button: Button? = null
    private var recyclerAdapter: FavRecyclerAdapter? = null
    private var gif_video: GifImageView? = null
    private var alertDB: AlertDialog.Builder? = null
    private var dbModel: ArrayList<DataModel>? = null
    private lateinit var roomDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.demo_center_layout)

        supportActionBar!!.title = getString(R.string.my_devices)
                //"My Devices"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.elevation = 0F

        dbModel = ArrayList<DataModel>()
        alertDB = AlertDialog.Builder(this@MyDeviceActivity)

        //creating room database for favorite
        roomDatabase = Room.databaseBuilder(applicationContext,
                AppDatabase::class.java, "fav_db")
                .fallbackToDestructiveMigration()
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                .build()

        //code for ads
        //  mAdView = findViewById<AdView>(R.id.adView) as AdView
        adBRequest = AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                //.addTestDevice("0D18E7ADF186A5703273874B522EF74B")
                .build()
               // mAdView!!.loadAd(adBRequest)

              //Interstitial Ads
        mAdMobInterstitialAd = InterstitialAd(this)
        mAdMobInterstitialAd!!.adUnitId = getString(R.string.interstitial_full_screen)
        mAdMobInterstitialAd!!.loadAd(adBRequest)

        //setup recyclerView
        // 1. get a reference to recyclerView
        recyclerView = findViewById<RecyclerView>(R.id.recycleView) as RecyclerView
        add_button = findViewById<Button>(R.id.add_button) as Button
        gif_video = findViewById<GifImageView>(R.id.gif_video) as GifImageView
        txt = findViewById<TextView>(R.id.txt) as TextView

        // 2. set layoutManger
        val mLayoutManager = GridLayoutManager(applicationContext, 1)

        recyclerView!!.layoutManager = mLayoutManager
        // smooth scrolling
        recyclerView!!.isNestedScrollingEnabled = false
        // 5. set item animator to DefaultAnimator
        recyclerView!!.itemAnimator = DefaultItemAnimator()

//        recyclerView!!.addOnItemTouchListener(
//                RecyclerItemClickListener(
//                        this@MyDeviceActivity,
//                        object : RecyclerItemClickListener.OnItemClickListener {
//                            override fun onLongItemClick(view: View?, position: Int) {
//                                Log.d("TTTT88", "CLICKED")
//
////                        alertDB!!.setMessage("Remove From List...")
////                        alertDB!!.setTitle("Are You Sure ?")
////                        alertDB!!.setCancelable(false)
////
////                        alertDB!!.setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, id ->
////                            thread {
////                                roomDatabase.favoriteDao().deleteRecord(dbModel!![position])
////
////                                Log.d("TTTTTU7I", "Deleted")
////                            }
////                            runOnUiThread {
////                                recyclerAdapter!!.notifyDataSetChanged()
////                                finish()
////                                val fav = Intent(this@MyDeviceActivity, MyDeviceActivity::class.java)
////                                startActivity(fav)
////                                // recyclerAdapter = FavRecyclerAdapter(dbModel!!, this@FavListActivity)
////
////
////                            }
////                        })
////                        alertDB!!.setNegativeButton("Cancel", DialogInterface.OnClickListener({ dialog, id ->
////                            //alertDB!!.
////                            //finish()
////                        }))
////                        alertDB!!.create()
////                        alertDB!!.show()
//
//                            }
//
//                            @RequiresApi(Build.VERSION_CODES.M)
//                            override fun onItemClick(view: View?, position: Int) {
//                                // TODO Handle item click
//                                val c = dbModel!!.get(position)
//
//                                var titleTV: TextView = view!!.findViewById<TextView>(R.id.titleTV) as TextView
//                                var imageView: ImageView = view!!.findViewById<ImageView>(R.id.titleIV) as ImageView
//                                var favIV: ImageView = view!!.findViewById<ImageView>(R.id.favIV) as ImageView
//
//
//                                favIV.setOnClickListener(View.OnClickListener { v ->
//                                    displayPopupWindow(v, c)
//                                })
//
////                                titleTV.setOnClickListener(View.OnClickListener { v ->
////
////
////                                })
//                                //displayPopupWindow(v, user_dataLists.get(pos))
//                                if (ConnectionDetector.checkInternetConnection(this@MyDeviceActivity)) {
//                                    if (mAdMobInterstitialAd!!.isLoaded) {
//                                        mAdMobInterstitialAd!!.show()
//                                    }
//
//                                    mAdMobInterstitialAd!!.adListener = object : AdListener() {
//                                        override fun onAdLoaded() {
//                                            super.onAdLoaded()
//                                        }
//
//                                        override fun onAdFailedToLoad(p0: Int) {
//                                            super.onAdFailedToLoad(p0)
//                                        }
//
//                                        override fun onAdOpened() {
//                                            super.onAdOpened()
//                                        }
//
//                                        override fun onAdLeftApplication() {
//                                            super.onAdLeftApplication()
//                                        }
//
//                                        override fun onAdClosed() {
//                                            mAdMobInterstitialAd!!.loadAd(adBRequest)
//                                            if ((dbModel!![position].totalFragments) > 1 && (dbModel!![position].totalFragments) != 0) {
//                                                val intent = Intent(this@MyDeviceActivity, MultipleRemoteActivity::class.java)
//                                                val dataOnThePosition = dbModel!![position]
//                                                intent.putExtra("Single", dataOnThePosition)
//                                                intent.putExtra("Position", position)
//                                                intent.putExtra("Object", dbModel)
//                                                startActivity(intent)
////                            Handler().postDelayed({
////
////                            }, 5)
//                                            } else if ((dbModel!![position].totalFragments) == 0) {
//                                                val intent = Intent(this@MyDeviceActivity, WifiTVRemoteActivity::class.java)
//                                                val dataOnThePosition = dbModel!![position]
//                                                intent.putExtra("Single", dataOnThePosition)
//                                                intent.putExtra("Position", position)
//                                                intent.putExtra("Object", dbModel)
//                                                startActivity(intent)
//
//                                            } else {
//                                                val intent = Intent(this@MyDeviceActivity, SingleRemoteActivity::class.java)
//                                                val dataOnThePosition = dbModel!![position]
//                                                intent.putExtra("Single", dataOnThePosition)
//                                                intent.putExtra("Position", position)
//                                                intent.putExtra("Object", dbModel)
//                                                startActivity(intent)
////                            Handler().postDelayed({
////
////                            }, 5)
//                                            }
//                                        }
//                                    }
//                                } else {
//                                    if ((dbModel!![position].totalFragments) > 1 && (dbModel!![position].totalFragments) != 0) {
//                                        val intent = Intent(this@MyDeviceActivity, MultipleRemoteActivity::class.java)
//                                        val dataOnThePosition = dbModel!![position]
//                                        intent.putExtra("Single", dataOnThePosition)
//                                        intent.putExtra("Position", position)
//                                        intent.putExtra("Object", dbModel)
//                                        startActivity(intent)
////                    Handler().postDelayed({
////
////                    }, 5)
//                                    } else if ((dbModel!![position].totalFragments) == 0) {
//                                        val intent = Intent(this@MyDeviceActivity, WifiTVRemoteActivity::class.java)
//                                        val dataOnThePosition = dbModel!![position]
//                                        intent.putExtra("Single", dataOnThePosition)
//                                        intent.putExtra("Position", position)
//                                        intent.putExtra("Object", dbModel)
//                                        startActivity(intent)
//
//                                    } else {
//                                        val intent = Intent(this@MyDeviceActivity, SingleRemoteActivity::class.java)
//                                        val dataOnThePosition = dbModel!![position]
//                                        intent.putExtra("Single", dataOnThePosition)
//                                        intent.putExtra("Position", position)
//                                        intent.putExtra("Object", dbModel)
//                                        startActivity(intent)
////                    Handler().postDelayed({
////
////                    }, 5)
//                                    }
//                                }
//                            }
//                        }))

        thread {
            dbModel = roomDatabase.favoriteDao().fetchAllData() as ArrayList<DataModel>

            runOnUiThread {

                // resultTextView.text = FlipTableConverters.fromIterable(customers, Customers::class.java)

                if (dbModel!!.size == 0) {

                      gif_video!!.visibility=View.VISIBLE
                      add_button!!.visibility=View.VISIBLE
                      txt!!.visibility=View.VISIBLE
//                    alertDB!!.setMessage(getString(R.string.plz_add_to_fav))
//                    alertDB!!.setTitle(getString(R.string.sorry))
//                    //"Sorry..."
//                    alertDB!!.setCancelable(false)
//
//                    alertDB!!.setPositiveButton(getString(R.string.ok), DialogInterface.OnClickListener { dialog, id -> finish() })
//                    //"Ok"
//                    alertDB!!.create()
//                    alertDB!!.show()
                } else {
//                    Toast.makeText(applicationContext, getString(R.string.my_fav_list), Toast.LENGTH_SHORT).show()
//                    recyclerAdapter = FavRecyclerAdapter(dbModel!!, this@MyDeviceActivity)
                    recyclerAdapter = FavRecyclerAdapter(dbModel!!, this@MyDeviceActivity)
                    add_button!!.visibility=View.GONE
                    gif_video!!.visibility=View.GONE
                    txt!!.visibility=View.GONE
                    recyclerView!!.adapter = recyclerAdapter
                    recyclerAdapter!!.notifyDataSetChanged()
                }
            }
            // println("customers Table =\n ${FlipTableConverters.fromIterable(customers, Customers::class.java)}")
        }

        add_button!!.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@MyDeviceActivity, TVListActivity::class.java)
            startActivity(intent)
            finish()
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun displayPopupWindow(view: View, model: DataModel) {
        val popup = PopupWindow(this@MyDeviceActivity)
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
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
            recyclerAdapter!!.notifyDataSetChanged()
            //notifyItemRangeChanged(0, dbModel!!.size)
        }
    }
}