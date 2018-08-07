package com.spn.swipetogridview;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.spn.ui.widget.swipetogridview.SwipeToGridViewAdapter;
import com.spn.ui.widget.swipetogridview.SwipeToGridViewLayout;

import java.util.Random;

public class MainActivity extends Activity {

  private SwipeToGridViewLayout swipeToGridViewLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);

    SeekBar seekBar = findViewById(R.id.seekBar);

    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override
      public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        swipeToGridViewLayout.setColumnsNumber(1 + i);
        TextView columnNumber = findViewById(R.id.text_column_number);
        columnNumber.setText("Columns: " + (i + 1));
      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {

      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {

      }
    });

    swipeToGridViewLayout = (SwipeToGridViewLayout)findViewById(R.id.card_view);
    swipeToGridViewLayout.setAdapter(new CardsAdapter(this, 100));
    swipeToGridViewLayout.precacheViews(50);

    swipeToGridViewLayout.setOnItemClickListener(new SwipeToGridViewLayout.OnItemClickListener() {
      @Override
      public void onItemClick(int itemIndex) {
        TextView selectedItem = findViewById(R.id.text_clicked_item);
        selectedItem.setText("Clicked Item: " + itemIndex);
      }
    });

    swipeToGridViewLayout.setOnSwipePageIndexChangedListener(new SwipeToGridViewLayout.OnSwipePageIndexChangedListener() {
      @Override
      public void onSwipePageIndexChanged(int itemIndex) {
        TextView selectedItem = findViewById(R.id.text_selected_item);
        selectedItem.setText("Selected Item: " + itemIndex);
      }
    });

  }

  private static class CardsAdapter extends SwipeToGridViewAdapter {

    private Random random = new Random(256);
    private int[] colors;

    public CardsAdapter(Context context, int count) {
      super(context);
      colors = new int[count];
      for(int i = 0; i < count; i++){
        colors[i] = 0x66000000 + random.nextInt(0xFFFFFF);
      }
    }

    @Override
    public View createView() {
      return LayoutInflater.from(getContext()).inflate(R.layout.card_view_item_layout, null);
    }

    @Override
    public void updateView(int index, View view) {
      View frame = view.findViewById(R.id.frame_layout);
      frame.setBackgroundColor(colors[index]);
      TextView textView = (TextView)view.findViewById(R.id.textView);
      textView.setText("#" + index);
    }

    @Override
    public int getCount() {
      return colors.length;
    }

  }

}
