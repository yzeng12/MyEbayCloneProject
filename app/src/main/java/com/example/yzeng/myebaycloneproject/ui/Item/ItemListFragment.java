package com.example.yzeng.myebaycloneproject.ui.Item;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.yzeng.myebaycloneproject.R;
import com.example.yzeng.myebaycloneproject.adapter.ItemListAdapter;
import com.example.yzeng.myebaycloneproject.objects.ListItem;
import com.example.yzeng.myebaycloneproject.ui.helperclasses.SPfiles;
import com.example.yzeng.myebaycloneproject.ui.helperclasses.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ItemListFragment extends Fragment {
    RecyclerView rv_Item_list;
    List<ListItem> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        rv_Item_list = view.findViewById(R.id.rv_Item_list);

        getVollyData();
        return view;
    }

    private void getVollyData() {
        String mainid = getArguments().getString("mainid");
        String subid = getArguments().getString("subid");

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                list = new ArrayList<>();
                try {
                    JSONObject jsonObject = response;
                    JSONArray jsonArray = jsonObject.getJSONArray("products");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        ListItem listItem = new ListItem(
                                jsonObject1.getString("id"),
                                jsonObject1.getString("pname"),
                                jsonObject1.getString("quantity"),
                                jsonObject1.getString("prize"),
                                jsonObject1.getString("discription"),
                                jsonObject1.getString("image"));
                        list.add(listItem);
                    }
                    ItemListAdapter adapter = new ItemListAdapter(getContext(), list);
                    //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false);
                    RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                    rv_Item_list.setLayoutManager(layoutManager);
                    rv_Item_list.setAdapter(adapter);

                    adapter.setOnItemClickListener(new ItemListAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View v, int position) {

                            ItemDetail itemDetail = new ItemDetail();
                            Bundle bundle = new Bundle();
                            bundle.putString("image", list.get(position).getImage());
                            bundle.putString("name", list.get(position).getName());
                            bundle.putString("price", list.get(position).getPrice());
                            bundle.putString("description", list.get(position).getDiscription());
                            bundle.putString("id", list.get(position).getId());

                            itemDetail.setArguments(bundle);

                            getFragmentManager().beginTransaction()
                                    .replace(R.id.Maincontent, itemDetail)
                                    .addToBackStack(null)
                                    .commit();
                            Toast.makeText(getContext(), "real item clicked", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };

        JsonObjectRequest jsonObjectRequest = Volley.getMyVolly().ItemListRequest(mainid, subid,
                SPfiles.getSharePreference(getContext()).getString("apikey", null),
                SPfiles.getSharePreference(getContext()).getString("id", null), listener, errorListener);
        RequestQueue requestQueue = com.android.volley.toolbox.Volley.newRequestQueue(getActivity());

        requestQueue.add(jsonObjectRequest);
    }


}
