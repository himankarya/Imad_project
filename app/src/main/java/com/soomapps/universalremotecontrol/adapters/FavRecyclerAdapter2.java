package com.soomapps.universalremotecontrol.adapters;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import com.soomapps.universalremotecontrol.R;
import com.soomapps.universalremotecontrol.db.AppDatabase;
import com.soomapps.universalremotecontrol.dto.DataModel;
import java.util.ArrayList;

public class FavRecyclerAdapter2 extends RecyclerView.Adapter<FavRecyclerAdapter2.MyViewHolder2>{

    ArrayList<DataModel> dataModels;
    AppDatabase roomDatabase;
    private Context context;

    public FavRecyclerAdapter2(ArrayList<DataModel> dataModels,Context context){
        this.dataModels=dataModels;
        this.context = context;
    }
    @NonNull
    @Override
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fav_list_items, viewGroup, false);
        dataModels = new ArrayList<DataModel>();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                // do something...
                roomDatabase= Room.databaseBuilder(context
                ,AppDatabase.class,"fav_db").build();
                dataModels= (ArrayList<DataModel>) roomDatabase.favoriteDao().fetchAllData();
            }
        }, 10);

        return new MyViewHolder2(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder2 holder2, int i) {
        final DataModel model=dataModels.get(i);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                // do something...
                roomDatabase= Room.databaseBuilder(context
                        ,AppDatabase.class,"fav_db").build();
                dataModels= (ArrayList<DataModel>) roomDatabase.favoriteDao().fetchAllData();
            }
        }, 10);

        holder2.titleTV.setText(model.getTitle());
        holder2.imageView.setImageResource(R.drawable.ic_tv);
        holder2.delete.setTag(i);
        holder2.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos= (int) v.getTag();
                displayPopupWindow(v,dataModels.get(pos));
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataModels.size();
    }

    class MyViewHolder2 extends  RecyclerView.ViewHolder{

        TextView titleTV;
        ImageView imageView,delete;
        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            titleTV=itemView.findViewById(R.id.titleTV);
            imageView=itemView.findViewById(R.id.titleIV);
            delete=itemView.findViewById(R.id.favIV);
        }
    }

    private void displayPopupWindow(View anchorView,final DataModel scanlist){
        final PopupWindow popup = new PopupWindow(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View layout = inflater.inflate(R.layout.pop_up_layout, null);
        popup.setContentView(layout);
        // Set content width and height
        popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popup.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        // Closes the popup window when touch outside of it - when looses focus
        popup.setOutsideTouchable(true);
        popup.setFocusable(true);
        // Show anchored to button_chnl_list
        popup.setBackgroundDrawable(new BitmapDrawable());
        popup.showAsDropDown(anchorView);
        TextView rec = (TextView) layout.findViewById(R.id.rec);
        rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        // do something...
                        roomDatabase.favoriteDao().deleteRecord(scanlist);
                        dataModels= (ArrayList<DataModel>) roomDatabase.favoriteDao().fetchAllData();
                    }
                }, 10);
                dataModels.remove(scanlist);
                notifyDataSetChanged();
            }
        });
    }
}
