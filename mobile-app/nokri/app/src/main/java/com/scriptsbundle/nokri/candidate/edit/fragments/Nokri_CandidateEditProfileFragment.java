package com.scriptsbundle.nokri.candidate.edit.fragments;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.scriptsbundle.nokri.candidate.dashboard.models.Nokri_CandidateDashboardModel;
import com.scriptsbundle.nokri.R;
import com.scriptsbundle.nokri.candidate.edit.Repositories.CandidateAvailabilityRepository;
import com.scriptsbundle.nokri.candidate.edit.ViewModel.CandidateAvailabilityViewModel;
import com.scriptsbundle.nokri.custom.Nokri_ViewPagerAdapter;
import com.scriptsbundle.nokri.custom.TabLayoutNoAutoScroll;
import com.scriptsbundle.nokri.manager.Nokri_DialogManager;
import com.scriptsbundle.nokri.manager.Nokri_FontManager;
import com.scriptsbundle.nokri.manager.Nokri_GoogleAnalyticsManager;
import com.scriptsbundle.nokri.manager.Nokri_RequestHeaderManager;
import com.scriptsbundle.nokri.manager.Nokri_SharedPrefManager;
import com.scriptsbundle.nokri.manager.Nokri_ToastManager;
import com.scriptsbundle.nokri.network.Nokri_ServiceGenerator;
import com.scriptsbundle.nokri.rest.RestService;
import com.scriptsbundle.nokri.utils.Nokri_Config;
import com.scriptsbundle.nokri.utils.Nokri_Globals;
import com.scriptsbundle.nokri.utils.Nokri_Utils;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Nokri_CandidateEditProfileFragment extends Fragment implements TabLayout.OnTabSelectedListener, View.OnClickListener {
    private ViewPager viewPager;
    private TabLayoutNoAutoScroll tabLayout;
    private final String tabTitles[] = new String[10];
    private TextView nextStepTextView, nextStepTextViewData;
    private Nokri_FontManager fontManager;
    Call<ResponseBody> myCall;
    private View overlay;
    private TextView totalStepsTextView;
    private ImageButton nextArrow;
    private Nokri_CandidateDashboardModel candidateDashboardModel;
    public static JSONObject personalInfo;
    public static JSONObject certificationsGet;
    public static JSONObject certificationsGetMore;
    public static JSONObject educationGet;
    public static JSONObject educationGetMore;
    public static JSONObject professionGet;
    public static JSONObject professionGetMore;
    public static JSONObject candidateAvailability;
    public static JSONObject skills;
    public static JSONObject portfolio;
    public static JSONObject socialLink;
    public static JSONObject updateLocation;
    public static JSONObject resumeList;
    ShimmerFrameLayout container1;
    RelativeLayout mainLayout;
    @Override
    public void onResume() {
        super.onResume();
        Nokri_GoogleAnalyticsManager.getInstance().trackScreenView(getClass().getSimpleName());
    }

    public Nokri_CandidateEditProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.v("opt1",getClass().getSimpleName() + " : onActivityCreated");

        mainLayout = getView().findViewById(R.id.mainLayout);
        container1 = (ShimmerFrameLayout) getView().findViewById(R.id.shimmer_view_container);


        candidateDashboardModel = Nokri_SharedPrefManager.getCandidateSettings(getContext());
        if (Nokri_Globals.IS_RTL_ENABLED) {
            tabTitles[9] = candidateDashboardModel.getTabPersonal();
            tabTitles[7] = candidateDashboardModel.getTabResume();
            tabTitles[6] = candidateDashboardModel.getTabEducation();
            tabTitles[8] = "Candidate Availability";
            tabTitles[5] = candidateDashboardModel.getTabProfessional();
            tabTitles[4] = candidateDashboardModel.getTabCertification();
            tabTitles[3] = candidateDashboardModel.getTabSkills();
            tabTitles[2] = candidateDashboardModel.getTabPortfolio();
            tabTitles[1] = candidateDashboardModel.getTabSocial();
            tabTitles[0] = candidateDashboardModel.getTabLocation();
        } else {
            tabTitles[0] = candidateDashboardModel.getTabPersonal();
            tabTitles[1] = candidateDashboardModel.getTabResume();
            tabTitles[2] = candidateDashboardModel.getTabEducation();
            tabTitles[3] = "Candidate Availability";
            tabTitles[4] = candidateDashboardModel.getTabProfessional();
            tabTitles[5] = candidateDashboardModel.getTabCertification();
            tabTitles[6] = candidateDashboardModel.getTabSkills();
            tabTitles[7] = candidateDashboardModel.getTabPortfolio();
            tabTitles[8] = candidateDashboardModel.getTabSocial();
            tabTitles[9] = candidateDashboardModel.getTabLocation();
        }
        nokri_initialize();
        try {
            CandidateAvailabilityViewModel viewModel = ViewModelProviders.of(getActivity()).get(CandidateAvailabilityViewModel.class);
            viewModel.init();
            getData();
        }catch (Exception e){
            e.printStackTrace();
        }
        nextStepTextViewData.setText("01");
        if(Nokri_Globals.IS_RTL_ENABLED){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            tabLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }}
        tabLayout.setSmoothScrollingEnabled(true);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(this);

//        getView().setFocusableInTouchMode(true);
//        getView().requestFocus();
//        getView().setOnKeyListener( new View.OnKeyListener()

//            @Override
//            public boolean onKey( View v, int keyCode, KeyEvent event )
//            {
//                if( keyCode == KeyEvent.KEYCODE_BACK )
//                {
//                    getActivity().onBackPressed();
//                }
//                return false;
//            }
//        } );


    }

    private void nokri_initialize() {
        fontManager = new Nokri_FontManager();

        viewPager = getView().findViewById(R.id.viewpager);
        tabLayout = getView().findViewById(R.id.tabs);
        nextStepTextView = getView().findViewById(R.id.txt_next_step);
        nextStepTextView.setText(Nokri_Globals.NEXT_STEP);
        nextStepTextViewData = getView().findViewById(R.id.txt_next_step_data);
        overlay = getView().findViewById(R.id.ovelay);
        totalStepsTextView = getView().findViewById(R.id.txt_total_steps);

        nextArrow = getView().findViewById(R.id.txt_next_arrow);
        overlay.setOnClickListener(this);
        Nokri_CandidateDashboardModel model = Nokri_SharedPrefManager.getCandidateSettings(getContext());
        TextView toolbarTitleTextView = getActivity().findViewById(R.id.toolbar_title);

        toolbarTitleTextView.setText(model.getEdit());
        getView().findViewById(R.id.bottom_container).setBackgroundColor(Color.parseColor(Nokri_Config.APP_COLOR));
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor(Nokri_Config.APP_COLOR));
    }


    private void nokri_setupViewPager() {
        Nokri_ViewPagerAdapter pagerAdapter = new Nokri_ViewPagerAdapter(getChildFragmentManager());


        if (Nokri_Globals.IS_RTL_ENABLED) {
            pagerAdapter.addFragment(new Nokri_LocationAndAddressFragment(), candidateDashboardModel.getTabLocation());
            pagerAdapter.addFragment(new Nokri_SocialLinksFragment(), candidateDashboardModel.getTabSocial());
            pagerAdapter.addFragment(new Nokri_AddPortfolioFragment(true), candidateDashboardModel.getTabPortfolio());
            pagerAdapter.addFragment(new Nokri_AddSkillsFragment(), candidateDashboardModel.getTabSkills());
            pagerAdapter.addFragment(new Nokri_CertificationsDetailsFragment(), candidateDashboardModel.getTabCertification());
            pagerAdapter.addFragment(new Nokri_ProfessionalsDetailsFragment(), candidateDashboardModel.getTabProfessional());
            pagerAdapter.addFragment(new CandidateAvailability(), "Candidate Availability");
            pagerAdapter.addFragment(new Nokri_EducationalDetailsFragment(), candidateDashboardModel.getTabEducation());
            pagerAdapter.addFragment(new Nokri_AddResumeFragment(), candidateDashboardModel.getTabResume());
            pagerAdapter.addFragment(new Nokri_PersonalInfoFragment(), candidateDashboardModel.getTabPersonal());


        } else {
            pagerAdapter.addFragment(new Nokri_PersonalInfoFragment(), candidateDashboardModel.getTabPersonal());
            pagerAdapter.addFragment(new Nokri_AddResumeFragment(), candidateDashboardModel.getTabResume());
            pagerAdapter.addFragment(new Nokri_EducationalDetailsFragment(), candidateDashboardModel.getTabEducation());
            pagerAdapter.addFragment(new CandidateAvailability(), "Candidate Availability");
            pagerAdapter.addFragment(new Nokri_ProfessionalsDetailsFragment(), candidateDashboardModel.getTabProfessional());
            pagerAdapter.addFragment(new Nokri_CertificationsDetailsFragment(), candidateDashboardModel.getTabCertification());
            pagerAdapter.addFragment(new Nokri_AddSkillsFragment(), candidateDashboardModel.getTabSkills());
            pagerAdapter.addFragment(new Nokri_AddPortfolioFragment(true), candidateDashboardModel.getTabPortfolio());
            pagerAdapter.addFragment(new Nokri_SocialLinksFragment(), candidateDashboardModel.getTabSocial());
            pagerAdapter.addFragment(new Nokri_LocationAndAddressFragment(), candidateDashboardModel.getTabLocation());
        }
        viewPager.setAdapter(pagerAdapter);


    }

    private void nokri_setFonts() {


        fontManager.nokri_setMonesrratSemiBioldFont(nextStepTextView, getActivity().getAssets());
        fontManager.nokri_setMonesrratSemiBioldFont(nextStepTextViewData, getActivity().getAssets());
        fontManager.nokri_setMonesrratSemiBioldFont(totalStepsTextView, getActivity().getAssets());
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            //noinspection ConstantConditions
            RelativeLayout relativeLayout = (RelativeLayout)
                    LayoutInflater.from(getContext()).inflate(R.layout.custom_tab, tabLayout, false);
            TextView tv = relativeLayout.findViewById(R.id.tab_title);
            relativeLayout.findViewById(R.id.divider).setVisibility(View.GONE);
            if (i == 0) {
                tv.setText(tabTitles[0]);
                tv.setTextColor(Color.parseColor(Nokri_Config.APP_COLOR));
            } else if (i > 0) {
                tv.setText(tabTitles[i]);

            }
            fontManager.nokri_setOpenSenseFontTextView(tv, getActivity().getAssets());
            tabLayout.getTabAt(i).setCustomView(relativeLayout);

        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("opt1",getClass().getSimpleName() + " : onCreateView");
        return inflater.inflate(R.layout.fragment_nokri_candidate_edit_profile, container, false);

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        TextView custom = view.findViewById(R.id.tab_title);
        custom.setTextColor(Color.parseColor(Nokri_Config.APP_COLOR));
        int currentItem = tab.getPosition() + 1;
        String text = "0" + Integer.toString(currentItem);

        nextStepTextViewData.setText(text);
        totalStepsTextView.setText("/0" + viewPager.getAdapter().getCount());
        if (viewPager.getCurrentItem() + 1 == viewPager.getAdapter().getCount())
            nextArrow.setVisibility(View.INVISIBLE);
        else
            nextArrow.setVisibility(View.VISIBLE);

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        TextView custom = view.findViewById(R.id.tab_title);
        custom.setTextColor(getResources().getColor(R.color.black));


    }


    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onClick(View view) {
        int currentItem = viewPager.getCurrentItem() + 1;
        totalStepsTextView.setText("/0" + viewPager.getAdapter().getCount());
        String text = "0" + Integer.toString(currentItem);
        nextStepTextViewData.setText(text);
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);

        if (viewPager.getCurrentItem() + 1 == viewPager.getAdapter().getCount())
            nextArrow.setVisibility(View.INVISIBLE);
        else
            nextArrow.setVisibility(View.VISIBLE);

    }

    public void getData() {
        container1.setVisibility(View.VISIBLE);
        Nokri_Utils.isCallRunning = true;
        container1.startShimmer();
        Nokri_DialogManager dialogManager = new Nokri_DialogManager();
        RestService restService = Nokri_ServiceGenerator.createService(RestService.class, Nokri_SharedPrefManager.getEmail(getContext()), Nokri_SharedPrefManager.getPassword(getContext()), getContext());



        if (Nokri_SharedPrefManager.isSocialLogin(getContext())) {
            myCall = restService.getCandidateEditDetails( Nokri_RequestHeaderManager.addSocialHeaders());
        } else {
            myCall = restService.getCandidateEditDetails(Nokri_RequestHeaderManager.addHeaders());
        }


        myCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> responseObject) {
                container1.hideShimmer();
                Nokri_Utils.isCallRunning = false;
                container1.setVisibility(View.GONE);
                mainLayout.setVisibility(View.VISIBLE);
                if (responseObject.isSuccessful()) {
                    try {
                        JSONObject response = new JSONObject(responseObject.body().string());
                        Log.v("response", responseObject.message());
                        if (response.getBoolean("success")) {
                            JSONObject mainData = response.getJSONObject("data");
                            personalInfo = mainData.getJSONObject("update_personal_info");
                            certificationsGet = mainData.getJSONObject("certifications_get");
                            certificationsGetMore = mainData.getJSONObject("certifications_get_more");
                            educationGet = mainData.getJSONObject("education_get");
                            educationGetMore = mainData.getJSONObject("education_get_more");
                            professionGet = mainData.getJSONObject("profession_get");
                            professionGetMore = mainData.getJSONObject("profession_get_more");
                            candidateAvailability = mainData.getJSONObject("scheduled_hours");

                            skills = mainData.getJSONObject("skills_get ");
                            portfolio = mainData.getJSONObject("portfolio_get");
                            socialLink = mainData.getJSONObject("social_link_get");
                            updateLocation = mainData.getJSONObject("update_location_get");
                            resumeList = mainData.getJSONObject("resumes_list_get");
                            try{
                                nokri_setupViewPager();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            viewPager.setOffscreenPageLimit(9);
                            nokri_setFonts();

                        } else {
                            Nokri_Utils.enableActions(getActivity());
                            Nokri_ToastManager.showShortToast(getActivity(),response.getString("message"));
                        }

                    } catch (Exception e) {
                        container1.hideShimmer();
                        Nokri_Utils.isCallRunning = false;

                        Nokri_Utils.enableActions(getActivity());
                        Nokri_ToastManager.showLongToast(getContext(), e.getMessage());
                        dialogManager.hideAfterDelay();
                        e.printStackTrace();
                    }
                } else {
                    container1.hideShimmer();
                    Nokri_Utils.isCallRunning = false;
                    Nokri_ToastManager.showLongToast(getContext(), responseObject.message());
                    Nokri_Utils.enableActions(getActivity());
                    dialogManager.hideAfterDelay();


                }

//                skeleton.hide();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                container1.hideShimmer();
                Nokri_Utils.isCallRunning = false;
            }
        });

    }


}
