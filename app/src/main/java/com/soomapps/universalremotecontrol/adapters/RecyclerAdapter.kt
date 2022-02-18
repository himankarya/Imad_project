package com.soomapps.universalremotecontrol.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.soomapps.universalremotecontrol.R
import com.soomapps.universalremotecontrol.db.AppDatabase
import kotlin.concurrent.thread
import android.os.Looper
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.gms.ads.VideoController
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.formats.UnifiedNativeAdView
import com.soomapps.universalremotecontrol.db.MIGRATION_1_2
import com.soomapps.universalremotecontrol.db.MIGRATION_2_3
import com.soomapps.universalremotecontrol.dto.DataModel


class RecyclerAdapter( val items: MutableList<Any>, private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var roomDatabase: AppDatabase
    private val RECIPE = 0
    private val NATIVE_AD = 1


    //view holder class
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("WrongViewCast")
        var imageView: ImageView = view.findViewById<ImageView>(R.id.titleIV) as ImageView
        var favIV: ImageView = view.findViewById<ImageView>(R.id.favIV) as ImageView
        var textView: TextView = view.findViewById<TextView>(R.id.titleTV) as TextView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {


        val itemLayoutView: View
        if (viewType == RECIPE) {
            // case Content_ITEM_VIEW_TYPE:
            /*View contentItemView = LayoutInflater.from(viewGroup.getContext()).inflate(
                        R.layout.content_row_item, viewGroup, false);
                */
            // create a new view
            itemLayoutView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_items, parent, false)

            return MyViewHolder(itemLayoutView)

            // case NATIVE_EXPRESS_AD_VIEW_TYPE:
            // fall through
            // default:
        } else {
             itemLayoutView  = LayoutInflater.from(
                    parent.context).inflate(R.layout.ad_unified,
                    parent, false)
            return NativeAppInstallHolder(itemLayoutView)
        }

        /* val v = LayoutInflater.from(parent.context)
                 .inflate(R.layout.list_items, parent, false)

         return MyViewHolder(v)*/
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val viewType = getItemViewType(position)
        if (viewType == RECIPE) {
            val viewHolder = holder as MyViewHolder
            roomDatabase = Room.databaseBuilder(context,
                    AppDatabase::class.java, "fav_db")
                    .fallbackToDestructiveMigration()
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .build()
            val c = items.get(position) as DataModel
            // val c = items.get(position)

            viewHolder.textView.text = c.title
            viewHolder.imageView.setImageResource(R.drawable.ic_tv)
            //holder.favIV.setImageResource(R.drawable.s)


            thread {

                val isExist = roomDatabase.favoriteDao().isExist(c.title)
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
            // Picasso.with(context).load(c.imgurl).resize(350, 700).centerCrop().into(holder.imageView)
        } else if (viewType == NATIVE_AD) run {
            val adView = holder as NativeAppInstallHolder

            val nativeAd = items.get(position) as UnifiedNativeAd
            Log.d("TAGTAGTAG313", nativeAd.toString())
            Log.d("TAGTAGT3", items.get(position).toString())

            // The headline is guaranteed to be in every UnifiedNativeAd.
            adView.headerView.text = nativeAd.headline

            Log.d("TAGTAGTAG33", nativeAd.headline)
            // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
            // check before trying to display them.
            if (nativeAd.body == null) {
                adView.bodyView.visibility = View.INVISIBLE
            } else {
                adView.bodyView.visibility = View.VISIBLE
                (adView.bodyView as TextView).text = nativeAd.body
            }

            if (nativeAd.callToAction == null) {
                adView.callToActionView.visibility = View.INVISIBLE
            } else {
                adView.callToActionView.visibility = View.VISIBLE
                (adView.callToActionView as Button).text = nativeAd.callToAction
            }

            if (nativeAd.icon == null) {
                adView.iconView.visibility = View.GONE
            } else {
                (adView.iconView as ImageView).setImageDrawable(
                        nativeAd.icon.drawable)
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

            // This method tells the Google Mobile Ads SDK that you have finished populating your
            // native ad view with this native ad. The SDK will populate the adView's MediaView
            // with the media content from this native ad.
          //  adView.setNativeAd(nativeAd)

            // Get the video controller for the ad. One will always be provided, even if the ad doesn't
            // have a video asset.
            val vc = nativeAd.videoController

            // Updates the UI to say whether or not this ad has a video asset.
            if (vc.hasVideoContent()) {
                /* videoStatus.setText(String.format(Locale.getDefault(),
                        "Video status: Ad contains a %.2f:1 video asset.",
                        vc.getAspectRatio()));*/

                // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
                // VideoController will call methods on this object when events occur in the video
                // lifecycle.
                vc.setVideoLifecycleCallbacks(object : VideoController.VideoLifecycleCallbacks() {
                    override fun onVideoEnd() {
                        // Publishers should allow native ads to complete video playback before
                        // refreshing or replacing them with another ad in the same UI location.
                        /* refresh.setEnabled(true);
                        videoStatus.setText("Video status: Video playback has ended.");*/
                        super.onVideoEnd()
                    }
                })
            } else {
                /*videoStatus.setText("Video status: Ad does not contain a video asset.");
                refresh.setEnabled(true);*/
            }


        }

    }



    /*   override fun getItemViewType(position: Int): Int {

           return if (position > 0 && position % LIST_AD_DELTA == 0) {
               NATIVE_EXPRESS_AD_VIEW_TYPE
           } else Content_ITEM_VIEW_TYPE

           *//*if (context.toString().contains("SingleListActivity")) {
                   return (position % 3 == 0 && position != 0) ? NATIVE_EXPRESS_AD_VIEW_TYPE
                           : Content_ITEM_VIEW_TYPE;
               } else {
                   return Content_ITEM_VIEW_TYPE;
               }*//*
    }*/


    override fun getItemViewType(position: Int): Int {
        val item = items.get(position)
        return if (item is DataModel) {
            RECIPE
        } else if (item is UnifiedNativeAdView) {
            NATIVE_AD
        } else {
            -1
        }
    }

    //view holder class
    inner class NativeAppInstallHolder(adView: View) : RecyclerView.ViewHolder(adView) {
        // Set other ad assets.
        var headerView = adView.findViewById<TextView>(R.id.ad_headline)
        var bodyView = adView.findViewById<TextView>(R.id.ad_body)
        var callToActionView = adView.findViewById<Button>(R.id.ad_call_to_action)
        var iconView = adView.findViewById<ImageView>(R.id.ad_app_icon)
        var priceView = adView.findViewById<TextView>(R.id.ad_price)
        var starRatingView = adView.findViewById<RatingBar>(R.id.ad_stars)
        var storeView = adView.findViewById<TextView>(R.id.ad_store)
        var advertiserView = adView.findViewById<TextView>(R.id.ad_advertiser)

    }
    /* inner class NativeAppInstallHolder(view: View, itemView: View): RecyclerView.ViewHolder(itemView) {


             var fl_adplaceholder: FrameLayout

             init {
                 fl_adplaceholder = view.findViewById(R.id.fl_adplaceholder) as FrameLayout


             }



     }
 */

    // No of list items
    override fun getItemCount(): Int {
        return items.size
    }

}