package com.capstoneproject.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhaarman.listviewanimations.swinginadapters.prepared.ScaleInAnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardListView;
import it.gmariotti.cardslib.library.view.CardView;

public class ParkingNavigationFragment extends Fragment {

    private CardListView mCardView;
    private CardArrayAdapter mCardAdapter;
    private ArrayList<Card> mCards;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_lots, container, false);

        CardListView mCardView = (CardListView) v.findViewById(R.id.lot_list);
        mCards = new ArrayList<Card>();
        CardArrayAdapter mCardAdapter = new CardArrayAdapter(getActivity(), mCards);
        ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(mCardAdapter);
        animationAdapter.setAbsListView(mCardView);

        for(int i = 1; i <= 3; i++) {

            Card card = new Card(getActivity());
            CardHeader cardHeader = new CardHeader(getActivity());

            cardHeader.setTitle("Parking Lot " + i);

            card.addCardHeader(cardHeader);
            mCardAdapter.add(card);

        }

        mCardView.setExternalAdapter(animationAdapter, mCardAdapter);

        //return inflater.inflate(R.layout.fragment_navigation, container, false);
        return v;
    }


}