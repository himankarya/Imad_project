package com.soomapps.universalremotecontrol.adapters

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.formats.UnifiedNativeAdView
import com.soomapps.universalremotecontrol.R
import com.soomapps.universalremotecontrol.db.AppDatabase
import com.soomapps.universalremotecontrol.db.MIGRATION_1_2
import com.soomapps.universalremotecontrol.db.MIGRATION_2_3
import com.soomapps.universalremotecontrol.dto.DataModel
import kotlin.concurrent.thread

class RecyclerViewAdapter(// The list of Native ads and menu items.
        private val mRecyclerViewItems: List<Any>, // An Activity's Context.
        private val mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        private lateinit var roomDatabase: AppDatabase

    inner class MenuItemViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        val titleIV: TextView
        val favIV: ImageView
        val titleTV: TextView

        init {
            titleIV = view.findViewById<View>(R.id.titleIV) as TextView
            favIV = view.findViewById<View>(R.id.favIV) as ImageView
            titleTV = view.findViewById<View>(R.id.titleTV) as TextView
        }
    }

    override fun getItemCount(): Int {
        return mRecyclerViewItems.size
    }

    /**
     * Determines the view type for the given position.
     */
    override fun getItemViewType(position: Int): Int {

        val recyclerViewItem = mRecyclerViewItems[position]
        return if (recyclerViewItem is UnifiedNativeAd) {
            UNIFIED_NATIVE_AD_VIEW_TYPE
        } else MENU_ITEM_VIEW_TYPE
    }

    /**
     * Creates a new view for a menu item view or a Native ad view
     * based on the viewType. This method is invoked by the layout manager.
     */
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            UNIFIED_NATIVE_AD_VIEW_TYPE -> {
                val unifiedNativeLayoutView = LayoutInflater.from(
                        viewGroup.context).inflate(R.layout.ad_unified,
                        viewGroup, false)
                return UnifiedNativeAdViewHolder(unifiedNativeLayoutView)
            }
            MENU_ITEM_VIEW_TYPE -> {
                val menuItemLayoutView = LayoutInflater.from(viewGroup.context).inflate(
                        R.layout.list_items, viewGroup, false)
                return MenuItemViewHolder(menuItemLayoutView)
            }
            // Fall through.
            else -> {
                val menuItemLayoutView = LayoutInflater.from(viewGroup.context).inflate(R.layout.list_items, viewGroup, false)
                return MenuItemViewHolder(menuItemLayoutView)
            }
        }
    }

    /**
     * Replaces the content in the views that make up the menu item view and the
     * Native ad view. This method is invoked by the layout manager.
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        when (viewType) {
            UNIFIED_NATIVE_AD_VIEW_TYPE -> {
                val nativeAd = mRecyclerViewItems[position] as UnifiedNativeAd
                populateNativeAdView(nativeAd, (holder as UnifiedNativeAdViewHolder).adView)
            }
            MENU_ITEM_VIEW_TYPE -> {
                val menuItemHolder = holder as MenuItemViewHolder
                roomDatabase = Room.databaseBuilder(mContext,
                        AppDatabase::class.java, "fav_db")
                        .fallbackToDestructiveMigration()
                        .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                        .build()

                val menuItem = mRecyclerViewItems[position] as DataModel

                // Get the menu item image resource ID.


                // Add the menu item details to the menu item view.
//                menuItemHolder.titleIV.setBackgroundResource(R.drawable.ic_tv)
                menuItemHolder.titleTV.text = menuItem.title
               // menuItemHolder.favIV.setImageResource(R.drawable.s2_fav_button_2)

                thread {

                    val isExist = roomDatabase.favoriteDao().isExist(menuItem.title)
                    Log.d("TAGTAGTAG", isExist.toString())

                    if (!isExist) {
                        /* Handler().postDelayed({
                         holder.favIV.setImageResource(R.drawable.s2_fav_button_2)
                     }, 10)*/

                        Handler(Looper.getMainLooper()).post {
                            holder.favIV.setImageResource(R.drawable.border_favourite_new)
                        }
                    } else {

                        Handler(Looper.getMainLooper()).post {
                            holder.favIV.setImageResource(R.drawable.filled_favourite_new)
                        }
                        /*Handler().postDelayed({
                    holder.favIV.setImageResource(R.drawable.s2_fav_button)
                    }, 10)*/
                    }
                }

            }
            // fall through
            else -> {
                val menuItemHolder = holder as MenuItemViewHolder
                val menuItem = mRecyclerViewItems[position] as DataModel
//                menuItemHolder.titleIV.setBackgroundResource(R.drawable.ic_tv)
                menuItemHolder.titleTV.text = menuItem.title
                menuItemHolder.favIV.setImageResource(R.drawable.border_favourite_new)
            }
        }
    }

    private fun populateNativeAdView(nativeAd: UnifiedNativeAd,
                                     adView: UnifiedNativeAdView) {
        // Some assets are guaranteed to be in every UnifiedNativeAd.
        (adView.headlineView as TextView).text = nativeAd.headline
        (adView.bodyView as TextView).text = nativeAd.body
        (adView.callToActionView as Button).text = nativeAd.callToAction

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        val icon = nativeAd.icon

        if (icon == null) {
            adView.iconView.visibility = View.INVISIBLE
        } else {
            (adView.iconView as ImageView).setImageDrawable(icon.drawable)
            adView.iconView.visibility = View.VISIBLE
        }

        if (nativeAd.price == null) {
            adView.priceView.visibility = View.GONE
        } else {
            adView.priceView.visibility = View.GONE
            (adView.priceView as TextView).text = nativeAd.price
        }

        if (nativeAd.store == null) {
            adView.storeView.visibility = View.GONE
        } else {
            adView.storeView.visibility = View.GONE
            (adView.storeView as TextView).text = nativeAd.store
        }

        if (nativeAd.starRating == null) {
            adView.starRatingView.visibility = View.GONE
        } else {
            (adView.starRatingView as RatingBar).rating = nativeAd.starRating!!.toFloat()
            adView.starRatingView.visibility = View.GONE
        }

        if (nativeAd.advertiser == null) {
            adView.advertiserView.visibility = View.GONE
        } else {
            (adView.advertiserView as TextView).text = nativeAd.advertiser
            adView.advertiserView.visibility = View.GONE
        }
        // Assign native ad object to the native view.
        adView.setNativeAd(nativeAd)
    }

    companion object {
        // A menu item view type.
        private val MENU_ITEM_VIEW_TYPE = 0

        private val UNIFIED_NATIVE_AD_VIEW_TYPE = 1
    }
}

