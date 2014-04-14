package com.capstoneproject.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.base.BaseCard;

public class LotCard extends Card {

    public static enum Status { OPEN, FULL, CLOSED };
    public final static int COLOR_OPEN = Color.GREEN;
    public final static int COLOR_CLOSED = Color.RED;

    private Context mContext;
    private LotCardHeader mHeader;

    private Lot mLot;
    private String mTitle;
    private String mStatus;
    private int mColor = 0;
    private TextView mTitleView;
    private TextView mStatusTextView;
    private View mStatusView;
    private Button mButtonShowMap;
    private Button mButtonViewLot;

    public LotCard(Context context, Lot lot) {
        super(context, R.layout.lot_card);
        mContext = context;
        mLot = lot;
        init();
    }

    private void init() {
       // mHeader = new LotCardHeader(mContext);
       // addCardHeader(mHeader);

    }


    public void setStatus(Status status) {

        switch(status) {
            case OPEN:
                mColor = COLOR_OPEN;
                mStatus = "OPEN";
                break;
            case FULL:
                mColor = COLOR_CLOSED;
                mStatus = "FULL";
                break;
            case CLOSED:
                mColor = COLOR_CLOSED;
                mStatus = "CLOSED";
                break;

        }

        if(mStatusView != null)
            mStatusView.setBackgroundColor(mColor);
        if(mStatusTextView != null)
            mStatusTextView.setText(mStatus);
    }

    @Override
    public void setTitle(String title) {

        mTitle = title;

        if(mTitleView != null)
            mTitleView.setText(title);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        mTitleView = (TextView) view.findViewById(R.id.lot_card_title);
        mStatusView = view.findViewById(R.id.lot_header_status);
        mStatusTextView = (TextView) view.findViewById(R.id.lot_status_text);
        mButtonShowMap = (Button) view.findViewById(R.id.button_show_map);
        mButtonViewLot = (Button) view.findViewById(R.id.button_view_lot);

        if(mTitle != null)
            mTitleView.setText(mTitle);

        if(mStatus != null)
            mStatusTextView.setText(mStatus);

        mStatusView.setBackgroundColor(mColor);

        mButtonViewLot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ParkingSpaces.class);
                intent.putExtra("rows", mLot.getRows());
                intent.putExtra("columns", mLot.getColumns());
                intent.putExtra("title", mTitle);
                intent.putExtra("id", mLot.getId());
                mContext.startActivity(intent);
            }
        });

    }

    private class LotCardHeader extends CardHeader {

        private String mTitle;
        private String mStatus;
        private int mColor = 0;
        private TextView mTitleView;
        private TextView mStatusTextView;
        private View mStatusView;

        public LotCardHeader(Context context) {
            super(context, R.layout.lot_card);

            setPopupMenu(R.menu.parking_status, new CardHeader.OnClickCardHeaderPopupMenuListener() {
                @Override
                public void onMenuItemClick(BaseCard baseCard, MenuItem item) {
                    //Toast.makeText(getActivity(), "Clicked on " + item.getTitle(), Toast.LENGTH_SHORT).show();
                   // onMenuOptionSelected(item);
                }
            });

        }

        public void setStatus(Status status) {

            switch(status) {
                case OPEN:
                    mColor = COLOR_OPEN;
                    mStatus = "OPEN";
                    break;
                case FULL:
                    mColor = COLOR_CLOSED;
                    mStatus = "FULL";
                    break;
                case CLOSED:
                    mColor = COLOR_CLOSED;
                    mStatus = "CLOSED";
                    break;

            }

            if(mStatusView != null)
                mStatusView.setBackgroundColor(mColor);
            if(mStatusTextView != null)
                mStatusTextView.setText(mStatus);
        }

        @Override
        public void setTitle(String title) {

            mTitle = title;

            if(mTitleView != null)
                mTitleView.setText(title);
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {
            mTitleView = (TextView) view.findViewById(R.id.lot_card_title);
            mStatusView = view.findViewById(R.id.lot_header_status);
            mStatusTextView = (TextView) view.findViewById(R.id.lot_status_text);

            if(mTitle != null)
                mTitleView.setText(mTitle);

            if(mStatus != null)
                mStatusTextView.setText(mStatus);

            mStatusView.setBackgroundColor(mColor);

        }

    }

}
