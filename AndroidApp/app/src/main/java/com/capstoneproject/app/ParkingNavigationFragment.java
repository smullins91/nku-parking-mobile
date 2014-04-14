package com.capstoneproject.app;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
*/        mCardView.setExternalAdapter(animationAdapter, mCardAdapter);
        updateLotInfo();
        //return inflater.inflate(R.layout.fragment_navigation, container, false);
        return v;
    }

    public boolean onMenuOptionSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.Availability:
                String getValueFromDatabase = "Available";
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Lot Status").setMessage("The lot status is : " + getValueFromDatabase);
                builder.setNeutralButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {

                    }
                });
                builder.setIcon(R.drawable.ic_launcher).show();
                break;

            case R.id.DirectionToLot:
                Toast.makeText(getActivity(), "Direction to lot has been selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ReserveASpot:
                Intent intent = new Intent(getActivity(), ParkingSpaces.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        //Toast.makeText(getActivity(), "Clicked on " + item.getTitle(), Toast.LENGTH_SHORT).show();
        return true;
    }

    private void updateLotInfo() {

        NetworkHelper.getLots(getActivity(), new HttpResponse(getActivity()) {

            @Override
            public void onSuccess(JSONArray response) {

                //JSONArray array = new JSONArray();

                try {
                    // array = response.getJSONArray("lots");

                    for(int i = 0; i < response.length(); i++) {

                        Lot lot = new Lot(response.getJSONObject(i));
                        String num = lot.getNumber();

                        LotCard card = new LotCard(getActivity(), lot);
                        //CardHeader cardHeader = new LotCardHeader(getActivity());

                        card.setTitle("Parking Lot " + num);

                        if(lot.isActive() && lot.getAvailable() > 0)
                            card.setStatus(LotCard.Status.OPEN);
                        else if(!lot.isActive())
                            card.setStatus(LotCard.Status.CLOSED);
                        else
                            card.setStatus(LotCard.Status.FULL);

                       /* cardHeader.setPopupMenu(R.menu.parking_status, new CardHeader.OnClickCardHeaderPopupMenuListener()
                        {
                            @Override
                            public void onMenuItemClick(BaseCard baseCard, MenuItem item) {
                                //Toast.makeText(getActivity(), "Clicked on " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                onMenuOptionSelected(item);
                            }
                        });

                        card.addCardHeader(cardHeader);*/
                        ParkingNavigationFragment.this.mCardAdapter.add(card);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        });

    }
}