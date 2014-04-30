package com.capstoneproject.app;


import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;
import com.nhaarman.listviewanimations.swinginadapters.prepared.ScaleInAnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardExpand;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.view.CardListView;
import it.gmariotti.cardslib.library.view.CardView;

public class ParkingNavigationFragment extends Fragment implements SearchView.OnQueryTextListener {

    private CardListView mCardView;
    private CardArrayAdapter mCardAdapter;
    private ArrayList<Card> mCards;
    private ArrayList<Lot> mLots;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_lots, container, false);
        ((MainActivity)getActivity()).setLotFragment(this);

        CardListView mCardView = (CardListView) v.findViewById(R.id.lot_list);
        mCards = new ArrayList<Card>();
        mCardAdapter = new CardArrayAdapter(getActivity(), mCards);
        ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(mCardAdapter);
        animationAdapter.setAbsListView(mCardView);

        //initializeCardInformation();

        /*String[] parkingLotNames = {"Student Parking Lot 1","Student Parking Lot 2","Faculty Parking Lot 1","Faculty Parking Lot 2","Visitor Parking Lot 1"};

        for(int i = 0; i < parkingLotNames.length; i++)
        {
            Card card = new Card(getActivity());
            CardHeader cardHeader = new CardHeader(getActivity());

            cardHeader.setTitle(parkingLotNames[i]);
            cardHeader.setPopupMenu(R.menu.parking_status, new CardHeader.OnClickCardHeaderPopupMenuListener()
            {
                @Override
                public void onMenuItemClick(BaseCard baseCard, MenuItem item) {
                    //Toast.makeText(getActivity(), "Clicked on " + item.getTitle(), Toast.LENGTH_SHORT).show();
                    onMenuOptionSelected(item);
                }
            });

            card.addCardHeader(cardHeader);
            mCardAdapter.add(card);
        }
*/      mCardView.setExternalAdapter(animationAdapter, mCardAdapter);
        updateLotInfo();
        //return inflater.inflate(R.layout.fragment_navigation, container, false);
        return v;
    }

/*
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

        super.onCreateOptionsMenu(menu, inflater);
        //return true;
    }
*/

    public void updateLotInfo() {

        NetworkHelper.getLots(getActivity(), new HttpResponse(getActivity()) {

            @Override
            public void onSuccess(JSONArray response) {

                mCards.clear();

                //JSONArray array = new JSONArray();
                mLots = new ArrayList<Lot>();

                try {
                    // array = response.getJSONArray("lots");

                    for(int i = 0; i < response.length(); i++) {

                        Lot lot = new Lot(response.getJSONObject(i));
                        mLots.add(lot);
                        addLot(lot);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mCardAdapter.notifyDataSetChanged();

            }

        });

    }

    private void addLot(Lot lot) {

        String num = lot.getNumber();
        LotCard card = new LotCard(getActivity(), lot);

        card.setTitle("Parking Lot " + num);

        if(lot.isActive() && lot.getAvailable() > 0)
            card.setStatus(LotCard.Status.OPEN);
        else if(!lot.isActive())
            card.setStatus(LotCard.Status.CLOSED);
        else
            card.setStatus(LotCard.Status.FULL);

        switch(lot.getType()) {
            case 1: card.setType("Faculty"); break;
            case 2: card.setType("Student"); break;
            case 3: card.setType("Visitor"); break;
            default: card.setType("Student");
        }

        mCardAdapter.add(card);

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {

        if(s.trim().length() > 0) {

            s = s.replaceAll(",", " ").replaceAll("/\\s{2,}/", " ").toLowerCase();
            String[] tokens = s.split(" ");
            boolean[] added = new boolean[mLots.size()];

            mCards.clear();

            for (int i = 0; i < tokens.length; i++) {

                String t = tokens[i];

                for (int j = 0; j < mLots.size(); j++) {

                    if(!added[j]) {
                        Lot lot = mLots.get(j);

                        if(lot.getNumber().toLowerCase().equals(t)) {
                            addLot(lot);
                            added[j] = true;
                        } else if(lot.getAvailable() > 0 && t.equals("open")) {
                            addLot(lot);
                            added[j] = true;
                        } else if(lot.getAvailable() == 0 && t.equals("full")) {
                            addLot(lot);
                            added[j] = true;
                        } else if(!lot.isActive() && t.equals("closed")) {
                            addLot(lot);
                            added[j] = true;
                        }
                    }

                }
            }

        } else {

            mCards.clear();
            for (int i = 0; i < mLots.size(); i++)
                addLot(mLots.get(i));

        }

        mCardAdapter.notifyDataSetChanged();

        return false;
    }
}