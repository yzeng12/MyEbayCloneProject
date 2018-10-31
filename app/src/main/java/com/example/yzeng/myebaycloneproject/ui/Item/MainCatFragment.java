package com.example.yzeng.myebaycloneproject.ui.Item;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.yzeng.myebaycloneproject.R;
import com.example.yzeng.myebaycloneproject.adapter.MainCategoryAdapter;
import com.example.yzeng.myebaycloneproject.adapter.SubCategoryAdapter;
import com.example.yzeng.myebaycloneproject.objecgts.MainCatItem;
import com.example.yzeng.myebaycloneproject.objecgts.SubCatItem;
import com.example.yzeng.myebaycloneproject.ui.helperclasses.SPfiles;
import com.example.yzeng.myebaycloneproject.ui.helperclasses.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainCatFragment extends Fragment {
    private RecyclerView rv_MainCat;
    private ProgressDialog progress;
    private List<MainCatItem> mainCatList;
    private List<SubCatItem> subCatList;
    private static final String TAG = "MainCatFragment";
    private  int Mainposition = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: MainCatFragment");
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        rv_MainCat = (RecyclerView) view.findViewById(R.id.rv_MainCat);
        Log.i(TAG, "onCreateView: before volly");
        getVollyData();
        Log.i(TAG, "onCreateView: finish volly");
        return view;


    }


    private void getVollyData() {
        progress = new ProgressDialog(getActivity());
        progress.setTitle("MAIN CAT LOADING");
        progress.setCancelable(false);
        progress.show();
        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progress.dismiss();
                mainCatList = new ArrayList<>();
                JSONObject jsonObject = (JSONObject) response;
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("category");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObjectInArray = jsonArray.getJSONObject(i);
                        MainCatItem mainCatItem = new MainCatItem(
                                jsonObjectInArray.getString("cid"),
                                jsonObjectInArray.getString("cname"),
                                jsonObjectInArray.getString("cdiscription"),
                                jsonObjectInArray.getString("cimagerl"));
                        mainCatList.add(mainCatItem);
                        Log.i(TAG, "getVollyData: finish add item to list");
                    }
                    MainCategoryAdapter adapter = new MainCategoryAdapter(getContext(), mainCatList);
                    //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getBaseContext());
                    RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                    rv_MainCat.setLayoutManager(layoutManager);
                    rv_MainCat.setAdapter(adapter);
                    //adapter.notifyDataSetChanged();
                    adapter.setOnItemClickListener(new MainCategoryAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            GoToSub(position);
                            Mainposition=position;
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
                Log.i(TAG, error.getMessage());
                Toast.makeText(getContext(), "Something Wrong", Toast.LENGTH_SHORT).show();
            }
        };

        JsonObjectRequest categoryRequest = Volley.getMyVolly().categorysObject(
                SPfiles.getSharePreference(getContext()).getString("apikey", null),
                SPfiles.getSharePreference(getContext()).getString("id", null),
                listener, errorListener);
        RequestQueue request1 = com.android.volley.toolbox.Volley.newRequestQueue(getActivity());
        request1.add(categoryRequest);

    }


    private void GoToSub(int position) {
        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                subCatList = new ArrayList<>();
                JSONObject jsonObject = (JSONObject) response;
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("subcategory");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        SubCatItem subCatItem = new SubCatItem(
                                jsonObject1.getString("scid"),
                                jsonObject1.getString("scname"),
                                jsonObject1.getString("scdiscription"),
                                jsonObject1.getString("scimageurl"));
                        subCatList.add(subCatItem);
                    }

                    SubCategoryAdapter adapter = new SubCategoryAdapter(getContext(), subCatList, mainCatList);
                    RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                    rv_MainCat.setLayoutManager(layoutManager);
                    rv_MainCat.setAdapter(adapter);
                    adapter.setItemClickListener(new SubCategoryAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {

                            Itemlist fragment = new Itemlist();
                            Bundle bundle = new Bundle();
                            bundle.putString("cid", mainCatList.get(Mainposition).getMainCatid());
                            bundle.putString("scid", subCatList.get(position).getSubCatid());
                           // fragment.setArguments(bundle);
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction().replace(R.id.Maincontent, fragment, "ProductFgt")
                                    .addToBackStack(null)
                                    .commit();

                            Toast.makeText(getContext(), "sub item clicked", Toast.LENGTH_SHORT).show();
                            //TODO goto item cart fragment
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
        //String id, String api_key, String user_id
        JsonObjectRequest subCategoryRequest = Volley.getMyVolly().subCategorysObject(mainCatList.get(position).getMainCatid(),
                SPfiles.getSharePreference(getContext()).getString("apikey", null),
                SPfiles.getSharePreference(getContext()).getString("id", null), listener, errorListener);
        RequestQueue request1 = com.android.volley.toolbox.Volley.newRequestQueue(getActivity());
        request1.add(subCategoryRequest);


    }

}
